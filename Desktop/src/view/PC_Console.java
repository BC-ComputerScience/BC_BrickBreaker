/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author prog
 */
public class PC_Console extends JScrollPane implements Console{
    private InnerConsole inner=new InnerConsole();
    public InputStream in;//text entry
    public PrintStream out;//textDidsplay
	
	public PC_Console(){
		in=inner.in;
		//out=inner.out;
		this.setViewportView(inner);
		
		out=new PrintStream(new InnerConsoleStream(inner));
		/*System.out.println("starting test");
		temp.println("1234566");
		temp.println("hello how are you");
		temp.println("\\__\n\r-123#%&^*$%&");
		temp.flush();
		System.out.println("ending test");/**/
	}
	
	
	
	private class InnerConsoleStream extends OutputStream{
		private InnerConsole ic;
		public InnerConsoleStream(InnerConsole ic){
			this.ic=ic;
		}
		public void close()throws IOException{
			//do nothing
		}
		public void flush()throws IOException{
			//do nothing(as of yet)
		}
		public void write(byte[] b)throws IOException{
			this.write(b,0,b.length);
			
		}
		public void write(byte[] b, int off, int len)throws IOException{
			byte[] temp=new byte[len];
			System.arraycopy(b, off, temp, 0, len);
			ic.append(new String(temp));
			ic.resetCaret();
		}
		@Override
		public void write(int arg) throws IOException {
			this.write(new byte[]{(byte)(arg&(0xFF))},0,1);
		}
		
	}
	
	
	
	private class InnerConsole extends JTextArea implements KeyListener {
    private ArrayList<view.ConsoleListener> listeners=new ArrayList<view.ConsoleListener>();
    private ArrayBlockingQueue<ConsoleEvent> events=new ArrayBlockingQueue<ConsoleEvent>(10);
    
    private Thread ConsolePrintListener;//thread to display text printed to console to user;
    private Thread ConsoleEventDispatcher;
    
    
    
    
    public static final int MAX_COMMAND_LENGTH=500;
    
    private PipedOutputStream rawIn;//pipes to the in stream(text entry)
    public  PipedOutputStream rawout;//pipes to the out string(text display)
    private InputStream toDisplay;
    private InputStream in;//text entry
    private PrintStream out;//textDidsplay
    
    private int lastIndex;
    
    public InnerConsole(){
    	super(8,20);
    	this.setFont(Font.getFont(Font.MONOSPACED));
    	this.setMargin(new Insets(5,5,5,5));
        //setColumns(20);
        //setRows(5);
        //this.setPreferredSize(new Dimension(50,50));
        //this.setMinimumSize(new Dimension(50,50));
        this.addKeyListener(this);
        //out.println("Console Open:");
        lastIndex=this.getCaretPosition();
        rawIn=new PipedOutputStream();
        rawout=new PipedOutputStream();
        out=new PrintStream(rawout);
        
        try {
            toDisplay=new BufferedInputStream(new PipedInputStream(rawout));
        } catch (IOException ex) {
            System.err.println("COULD NOT Print to graphical console");
        }
        
        
        try {
            in=new PipedInputStream(rawIn);//XXX see if this is  nessescary
            
        } catch (IOException ex) {
            System.err.println("COULD NOT Read from graphical console");
        }
       /* ConsolePrintListener=new Thread(new Runnable(){
            @Override
            public void run() {listen();}
            
        });
        ConsolePrintListener.start();/**/
        this.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
        //System.out.println(Font.getFont("monospaced",Font.SANS_SERIF));
        ConsoleEventDispatcher=new Thread(new Runnable(){
            @Override
            public void run() {sendEvents();}

            
        });
        ConsoleEventDispatcher.start();
    }

    /*private void listen(){
        System.out.println("started listening");
        
        BufferedReader reader=new BufferedReader(new InputStreamReader(toDisplay));
        //Scanner console=new Scanner(toDisplay);
        //System.out.println(toDisplay.)
        String thisLine;
        //Thread.currentThread().setDaemon(true);
        
        try {
			while((thisLine = reader.readLine()) != null){
			   /* if(reader.ready()){
			    	
			    }
				Thread.sleep(Long.MAX_VALUE);
			    
				if(console.hasNext(".*\n")){
			    	System.out.println("12312 here");
			    	String der=console.nextLine();
			    	this.append(der+"\n");
			    	System.out.print(der+"\n");
			    }else{
			    	System.out.println("not here");
			    }*//*
				this.append(thisLine);
			    resetCaret();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("stopped listening");
    }
    /**/
    private void sendEvents() {
        while (true){
            try {
                ConsoleEvent e=events.take();
                for(ConsoleListener c:listeners){
                    c.CommandRecieved(e);
                }
            } catch (InterruptedException ex) {System.err.println("events.take failed in console");
                }
        }
    }
    
    public void last_command(){
        System.err.println("last command not implemented");
    }
    public void next_command(){
        System.err.println("next command not implemented");        
    }
    
    
    
   

    
    
    //########
    public void resetCaret(){
        lastIndex=this.getDocument().getLength();
        ValidateCaret();
    }
    public boolean ValidateCaret(){
        try{
        int currentIndex=this.getCaretPosition(); 
        if(currentIndex<lastIndex){
            this.setCaretPosition(lastIndex);
            return false;
        }
        return true;
        }catch(IllegalArgumentException E){
            this.append("\n");
            this.setCaretPosition(lastIndex);
            return false;
        }
        
    }
    @Override
    public void keyTyped(KeyEvent ke) {
        if(!ValidateCaret()){
            ke.consume();
        }
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        if(!ValidateCaret()){
            ke.consume();
        }
        if(ke.getKeyCode()==KeyEvent.VK_UP){
            
            last_command();
            ke.consume();
        }else if(ke.getKeyCode()==KeyEvent.VK_DOWN){
            next_command();
            ke.consume();
        }else if(ke.getKeyCode()==KeyEvent.VK_LEFT){
            if(this.getCaretPosition()<=lastIndex){
                this.setCaretPosition(lastIndex);
                ke.consume();
            }
        }
    }   
    @Override
    public void keyReleased(KeyEvent ke) {

        ValidateCaret();
        
        int currentIndex=this.getCaretPosition();
        if(ke.getKeyChar()=='\n'){
            int length=this.getDocument().getLength()-lastIndex;
            try {
                String command=(this.getText(lastIndex, length));
                rawIn.write(command.getBytes());
                rawIn.flush();
                events.add(new ConsoleEvent(command));
                
                
            } catch (IOException ex) {
                System.err.println("Could not put console command into stream");
            } catch (BadLocationException ex) {System.err.println("Console command start position is illegal");}
            lastIndex=currentIndex;
        }
        
        
    }
	}



	@Override
	public PrintStream out() {
		// TODO Auto-generated method stub
		return out;
	}



	@Override
	public InputStream in() {
		// TODO Auto-generated method stub
		return in;
	}



	 public void addConsoleListener(ConsoleListener cl){
	    //    listeners.add(cl);
	    }
	    public void removeConsoleListener(ConsoleListener cl){
	    //    listeners.remove(cl);
	    }


}
