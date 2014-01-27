/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
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
public class Console extends JScrollPane {
    private innerConsole inner=new innerConsole();
    public InputStream in;//text entry
    public PrintStream out;//textDidsplay
	
	public Console(){
		in=inner.in;
		out=inner.out;
		this.setViewportView(inner);
	}
	
	
	
	
	
	
	private class innerConsole extends JTextArea implements KeyListener {
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
    
    public innerConsole(){
    	super(5,20);
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
        ConsolePrintListener=new Thread(new Runnable(){
            @Override
            public void run() {listen();}
            
        });
        ConsolePrintListener.start();
        ConsoleEventDispatcher=new Thread(new Runnable(){
            @Override
            public void run() {sendEvents();}

            
        });
        ConsoleEventDispatcher.start();
    }

    private void listen(){
        System.out.println("started listening");
        Scanner console=new Scanner(toDisplay);
        while(console.hasNext()){
            
            String der=console.nextLine();
            this.append(der+"\n");
            resetCaret();
        }
        System.out.println("stopped listening");
    }
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
    
    
    
    public void addConsoleListener(ConsoleListener cl){
        listeners.add(cl);
    }
    public void removeConsoleListener(ConsoleListener cl){
        listeners.remove(cl);
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

}
