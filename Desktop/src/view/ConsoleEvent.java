/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author prog
 */
public class ConsoleEvent {
    private String command;
    ConsoleEvent(String command) {
        this.command=command;
    }
    public String getCommand(){
        return this.command;
    }
    
}
