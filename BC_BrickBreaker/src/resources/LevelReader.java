package resources;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Sphere;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LevelReader {
	private HashMap<String, Resource> resources = new HashMap<String, Resource>();
	private HashMap<String, SpriteHolder> SpriteSheets = new HashMap<String, SpriteHolder>();
	String dir;
	
	public LevelReader(String dir){
		
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
		String file=node.getTextContent();
		String type=node.getAttributes().getNamedItem("type").getTextContent();
		String name=node.getAttributes().getNamedItem("name").getTextContent();
		if(type.equals("image")){
			try {
				this.resources.put(name, new Resource(dir,file,type));
			} catch (IOException e) {
				System.err.println("could not load resource:"+dir+file+"("+type+")");
			}
		}else if(type.equals("jar")){
			URL url;
			try {
				//TODO learn what this actually does.
				url = new File("/Users/prog/git/classes.jar").toURI().toURL();
				URLClassLoader loader = new URLClassLoader(new URL[]{url});
				Class<?> cls =loader.loadClass("extensions."+name);
				
				this.resources.put(name, new Resource(cls,name));
				
			} catch (MalformedURLException e) {
				System.err.println("could not load resource:"+dir+file+"("+type+")");
			} catch (ClassNotFoundException e) {
				System.err.println("could find specified class resource:"+dir+file+"("+type+")");
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
		
		NodeList sprites=spriteSheet.getChildNodes();
		for(int i=0;i<sprites.getLength();i++){
			if(sprites.item(i).getNodeName()!="#text"){
				parseSprite(sprites.item(i));
			}
		}
		
		System.out.println(spriteSheet.getNodeName());
		this.printLevel(spriteSheet);
	}
	private Sprite parseSprite(Node sprite){
		return null;
		
	}
		
	
	
	//this will probably never be needed
	private void parseSpecialDefs(NodeList defs){
		throw new UnsupportedOperationException("files do not yet support added class defs");
	}
	
	private void parseLevels(NodeList defs){
		throw new UnsupportedOperationException("files do not yet support loading levels(odd as thats the main point of this class)");
		
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

}
/*NodeList links = doc.getElementsByTagName("links").item(0).getChildNodes();
//this.loadLinks(links , dir);
NodeList sheets = doc.getElementsByTagName("spriteSheets").item(0).getChildNodes();
//this.loadSpriteSheets(sheets);
NodeList objects = doc.getElementsByTagName("objects").item(0).getChildNodes();
//this.loadObjects(objects,model);
/**/