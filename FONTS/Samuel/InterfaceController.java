/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

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
    
    public static void setText(String text){}
    
    public static void setSettings(String Settings){}
    
    public static void loadFrequencies(String path){}
    
    public static void loadKeyboard(String path){}
    
    public static void loadText(String path){}
    
    public static void loadAlphabet(String path){}
    
    public static void saveKeyboard(String path){}
    
    public static void saveText(String path){}
    
    public static void saveAlphabet(String path){}
    
    public static void saveFrequencies(String path){}
    
}
