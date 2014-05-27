/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FONTS.Samuel;

/**
 *
 * @author samuel.bryan.pierno
 */
public class InterfaceController {
    private static InterfaceController instance;
    private InterfaceController(){
    }
    public static InterfaceController getInstance(){
        if(instance == null){
            instance = new InterfaceController();
        }
        return instance;
    }
    
}
