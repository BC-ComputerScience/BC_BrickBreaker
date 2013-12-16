package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Brick;
import model.Collision_Simulator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelLoader {
	private HashMap<String, Resource> resources = new HashMap<String, Resource>();
	private HashMap<String, SpriteSheet> SpriteSheets = new HashMap<String, SpriteSheet>();
	
	public LevelLoader(String dir, Collision_Simulator model){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			
			File file= new File(dir+"Level.xml");
			System.out.println ("Loading File: "+file.getAbsolutePath());
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList links = doc.getElementsByTagName("links").item(0).getChildNodes();
			this.loadLinks(links , dir);
			NodeList sheets = doc.getElementsByTagName("spriteSheets").item(0).getChildNodes();
			this.loadSpriteSheets(sheets);
			NodeList objects = doc.getElementsByTagName("objects").item(0).getChildNodes();
			this.loadObjects(objects,model);
			
		} catch (ParserConfigurationException e1) {
			System.err.println("couldn't create document builder in LevelLoader");
			e1.printStackTrace();
		} catch (SAXException e) {
			System.err.println("xml document was inavlid");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void loadObjects(NodeList objects,Collision_Simulator model) {
		for (int i = 0; i < objects.getLength(); ++i){
			Node item=objects.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE){
				String type=item.getNodeName();
				if(type.equals("Brick")){
					model.addGameObject(this.makeBrick(item));
				}
			}
		}
		
	}
	
	

	private Brick makeBrick(Node node) {
		System.out.println("creating a brick");
		NodeList attributes=node.getChildNodes();
		SpriteSheet sheet=null;
		int health=0,y=0,x=0,width=0,height=0;
		
		for (int i = 0; i < attributes.getLength(); ++i){
			Node item=attributes.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE){
				String name=item.getNodeName();
				if(name=="spriteset"){
					health=Integer.parseInt(item.getAttributes().getNamedItem("health").getTextContent());
					String sheetname=item.getTextContent().trim();
					sheet=this.SpriteSheets.get(sheetname);
					
				}else if(name=="health"){
					health=Integer.parseInt(item.getTextContent());
				}else if(name=="x"){
					x=Integer.parseInt(item.getTextContent());
				}else if(name=="y"){
					y=Integer.parseInt(item.getTextContent());
				}else if(name=="width"){
					width=Integer.parseInt(item.getTextContent());
				}else if(name=="height"){
					height=Integer.parseInt(item.getTextContent());
				}
			}
		}
		if(sheet!=null){
			return new Brick(x,y,width,height,health,sheet);
		}
		return new Brick(x,y,width,height,health);
		
	}

	private void loadSpriteSheets(NodeList sheets){
		for (int i = 0; i < sheets.getLength(); ++i){
			Node item=sheets.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE){
				Node nameNode=item.getAttributes().getNamedItem("name");
				if(nameNode!=null){
					System.out.println("Creating SpriteSheet: "+nameNode.getTextContent());
					SpriteSheet sheet= new SpriteSheet(item.getChildNodes().getLength()/2);
					this.fillSheet(sheet, item.getChildNodes());
					if(sheet.isUsable()){
						this.SpriteSheets.put(nameNode.getTextContent().trim(), sheet);
					}else{
						System.err.print("Stylesheet "+nameNode.getTextContent()+" didn't load properly");
					}
				}
			}
			
		}
	}
	
	
	private void fillSheet(SpriteSheet sheet, NodeList sprites) {
		for (int i = 0; i < sprites.getLength(); ++i){
			Node item=sprites.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE){
				Node timeNode=item.getAttributes().getNamedItem("time");
				Node sourceNode=item.getAttributes().getNamedItem("source");
				try{
					if(timeNode!=null&&sourceNode!=null){
						int frames=Integer.parseInt(timeNode.getTextContent());
						NodeList data = item.getChildNodes();
						int x=0,y=0,width=0,height=0;
						for (int j = 0; j < data.getLength(); ++j){
							Node temp=data.item(j);
							if(temp.getNodeName()=="x"){
								x=Integer.parseInt(temp.getTextContent());
							}else if(temp.getNodeName()=="y"){
								y=Integer.parseInt(temp.getTextContent());
							}else if(temp.getNodeName()=="width"){
								width=Integer.parseInt(temp.getTextContent());
							}else if(temp.getNodeName()=="height"){
								height=Integer.parseInt(temp.getTextContent());
							}
							
						}
						Resource res=this.resources.get(sourceNode.getTextContent());
						if(res!=null){
							sheet.addSprite(res.getAt(x, y, width, height), frames);
						}
					}
				}catch(NumberFormatException e){
					System.err.println("An integer was incorrectly formateed in the level file");
					e.printStackTrace();
					
				}
			}
		}
		
	}

	private void loadLinks(NodeList links, String dir){
		for (int i = 0; i < links.getLength(); ++i){
			Node item=links.item(i);
			if(item.getNodeType()==Node.ELEMENT_NODE){
				Node nameNode=item.getAttributes().getNamedItem("name");
				Node typeNode=item.getAttributes().getNamedItem("type");
				if(nameNode!=null&&typeNode!=null){
					Resource res= this.loadResource(dir+item.getTextContent(), typeNode.getTextContent());
					if(res!=null){
						this.resources.put(nameNode.getTextContent(), res);
					}
				}
			}
		}
	}
	
	private Resource loadResource(String name, String type){
		System.out.println("loading: "+name+"  type: "+type);
		try{
			return new Resource(name, type);
		}catch(IOException e){
			System.err.print("problem loading image:"+name+" type:"+type);
			e.printStackTrace();
			return null;
		}
		
	}
	
	//currently only holds images
	private class Resource{
		BufferedImage image;
		String type;
		public Resource(String name, String type) throws IOException{
			this.type=type;
			image=ImageIO.read(new File(name));
		}
		public BufferedImage getAt(int x, int y, int width, int height){
			return image.getSubimage(x, y, width, height);
		}
		public String getType(){
			return type;
		}
		
	}
	
}
