package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.jar.JarInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mathematics.Vector;
import model.gameObjects.Sphere;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import view.Sprite;

public class LevelReader {
	private HashMap<String, Resource> resources = new HashMap<String, Resource>();
	private HashMap<String, SpriteSheet> SpriteSheets = new HashMap<String, SpriteSheet>();
	private ResourceLoader rl;
	String dir;
	
	public LevelReader(String dir, ResourceLoader rl){
		if(dir.charAt(dir.length()-1)!='/'){
			dir+='/';
		}
		this.dir=dir;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			File file= new File(dir+"level.xml");
			System.out.println ("Loading File: "+file.getAbsolutePath());
			System.out.println();
			this.rl=rl;
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			
			doc.getDocumentElement().normalize();
			parseDoc(doc);
			
		} catch (ParserConfigurationException e1) {
			System.err.println("couldn't create document builder in LevelLoader");
			e1.printStackTrace();
		} catch (SAXException e) {
			System.err.println("xml document was inavlid");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO failure, probably file location or permissions");
			e.printStackTrace();
		}finally{
			//make sure that memory is cleared
			//resources=null;
		}
	}
	
	private void parseDoc(Document doc){
		NodeList levelSet=doc.getElementsByTagName("bc:levelSet").item(0).getChildNodes();
		for(int i=0;i<levelSet.getLength();i++){
			if(levelSet.item(i).getNodeName()!="#text"){
				if(levelSet.item(i).getNodeName()=="bc:links"){
					parseLinks(levelSet.item(i).getChildNodes());
				}else if(levelSet.item(i).getNodeName()=="bc:spriteSheets"){
					parseSpriteSheets(levelSet.item(i).getChildNodes());
				}else if(levelSet.item(i).getNodeName()=="bc:specialDefs"){
					parseSpecialDefs(levelSet.item(i).getChildNodes());
				}else if(levelSet.item(i).getNodeName()=="bc:levels"){
					parseLevels(levelSet.item(i).getChildNodes());
				}
			}
		}
	}
	private void parseLinks(NodeList links){
		for(int i=0;i<links.getLength();i++){
			if(links.item(i).getNodeName()!="#text"){
				parseLink(links.item(i));
			}
		}
	}
	
	
	private void parseLink(Node node){
		String file=node.getTextContent().trim();
		String type=node.getAttributes().getNamedItem("type").getTextContent();
		String name=node.getAttributes().getNamedItem("name").getTextContent();
		
		System.out.println("Loading Resource: "+dir+file);
		
		if(type.equals("image")){
			try {
				Resource res=rl.loadResource(type, dir+file);
				this.resources.put(name, res);
			} catch (IOException e) {
				System.err.println("could not load resource:"+dir+file+"("+type+")");
				e.printStackTrace();
			}
		}else if(type.equals("jar")){
			
			try{
				ClassResource res=rl.LoadClassResource(dir+file);
				this.resources.put(name, res);
			}catch (IOException e) {
				System.err.println("could not load resource:"+dir+file+"("+type+")");
				e.printStackTrace();
			}
			
		}else{
			System.err.println("unknown type:"+type);
		}
	}
	
	private void parseSpriteSheets(NodeList spriteSheets){
		for(int i=0;i<spriteSheets.getLength();i++){
			if(spriteSheets.item(i).getNodeName()!="#text"){
				parseSpriteSheet(spriteSheets.item(i));
			}
		}
		//throw new UnsupportedOperationException("files do not yet support spritesheets");
	}
	
	private void parseSpriteSheet(Node spriteSheet){
		ArrayList<Sprite> spritea=new ArrayList<Sprite>();
		NodeList sprites=spriteSheet.getChildNodes();
		for(int i=0;i<sprites.getLength();i++){
			if(sprites.item(i).getNodeName().equals("bc:sprite")){
				spritea.add(parseSprite(sprites.item(i)));
			}
		}
		SpriteSheet sheet=new SpriteSheet(spritea,"test");
		this.SpriteSheets.put(spriteSheet.getAttributes().getNamedItem("name").getTextContent().trim(),sheet);
	}
	private Sprite parseSprite(Node sprite){
		String name=sprite.getAttributes().getNamedItem("resource_name").getTextContent().trim();
		NodeList children=sprite.getChildNodes();
		int x=0,y=0,width=0,height=0;
		for(int i=0;i<children.getLength();i++){
			if(children.item(i).getNodeName().equals("bc:x")){
				x=Integer.parseInt(children.item(i).getTextContent());
			}else if(children.item(i).getNodeName().equals("bc:y")){
				y=Integer.parseInt(children.item(i).getTextContent());
			}else if(children.item(i).getNodeName().equals("bc:width")){
				width=Integer.parseInt(children.item(i).getTextContent());
			}else if(children.item(i).getNodeName().equals("bc:height")){
				height=Integer.parseInt(children.item(i).getTextContent());
			}
		}
		Resource res=this.resources.get(name);
		
		if(res instanceof ImageResource){
			//System.out.println("getting image at: "+x+","+y+","+width+","+height);
			return ((ImageResource)res).createSprite(x, y, width, height);
		}
		
		System.err.println((res)+""+res.getClass());
		throw new IllegalArgumentException(name+" is not an image resource!");
		
	}
		
	
	
	//this will probably never be needed
	private void parseSpecialDefs(NodeList defs){
		//throw new UnsupportedOperationException("files do not yet support added class defs");
	}
	
	private void parseLevels(NodeList defs){
		//throw new UnsupportedOperationException("files do not yet support loading levels(odd as thats the main point of this class)");
		
	}
	
	
	
	
	private void printLevel(Node n){
		printLevel(n.getChildNodes());
	}

	private void printLevel(NodeList childNodes) {
		//childNodes.i
		for(int i=0;i<childNodes.getLength();i++){
			System.out.println(childNodes.item(i));
			
		}
		
	}
	private void print(Object... os){
		if(os.length>0){
			System.out.println("[");
			for(Object o:os){
				System.out.println('\t'+o.toString());
			}
			System.out.println("]");
		}else{
			System.out.println("~");
		}
	}
	private void printres(){
		for(String d:this.resources.keySet()){
			System.out.println(d+"->"+resources.get(d));
		}
	}

	public Sphere createball(int x, int y) {
		
		Sphere s=new Sphere(x,y,new Vector(0,0),32*32);
		s.addSpriteSet(this.SpriteSheets.get("brick_explode").getSpriteSheet());
		return s;
	
		// TODO Auto-generated method stub
		
	}

}
/*NodeList links = doc.getElementsByTagName("links").item(0).getChildNodes();
//this.loadLinks(links , dir);
NodeList sheets = doc.getElementsByTagName("spriteSheets").item(0).getChildNodes();
//this.loadSpriteSheets(sheets);
NodeList objects = doc.getElementsByTagName("objects").item(0).getChildNodes();
//this.loadObjects(objects,model);
/**/