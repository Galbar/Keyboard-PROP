/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Vector;


import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author albert.trelis
 */
public class PersistanceController {
    private static PersistanceController instance;
    private PersistanceController(){
    }
    public static PersistanceController getInstance(){
        if(instance == null){
            instance = new PersistanceController();
        }
        return instance;
    }



    /**
	* Carrega Alfabet.
	* @param pth És el path on hi ha le fitxer a carregar.
	* @return String amb totes les dades del fitxer.
	*/
    public String loadAlphabet(String pth) throws PROPKeyboardException {
        try{
            JSONObject s = new JSONObject();
            Path path = Paths.get(pth);
            List<String> lines = null;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error loadAlphabet: Path");
            } 
            String ret = new String();
            for (int i = 0; i < lines.size(); ++i) {
                ret += lines.get(i);
            }
            //return s.toString();
            return ret;
        } catch (Exception e) {
            throw new PROPKeyboardException("Error loadAlphabet: Error Intern");
        }
    }
   
    /**
	* Guarda Alfabet
	* @param str Estructura on hi ha tots els paràmetres a guardar.
	*/	 
    public void saveAlphabet(String str) throws PROPKeyboardException{
        try {
            FileWriter write = null;
            String pth = null;
            JSONObject s = null;
            
            try {
                s = new JSONObject(str);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: JSON string bad format");
            }
            try {
                pth = s.get("path").toString();
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: path to string");
            }
            try {
                write = new FileWriter(pth,false);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: path");
            }
            PrintWriter print_line = new PrintWriter(write);
            
            print_line.print(str);
            print_line.close();
        }catch (Exception e) {
            throw new PROPKeyboardException("Error loadAlphabet: Error Intern");
        }
    }

    public void saveKeyboardImage(String str) throws PROPKeyboardException {
        try {
            String pth = null;
            String image_string = null;
            JSONObject s = null;
            try {
                s = new JSONObject(str);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboardImage: JSON string bad format");
            }
            try {
                pth = s.get("path").toString();
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboardImage: path to string");
            }
            try {
                image_string = s.get("image_string").toString();
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboardImage: image_string to string");
            }

            BufferedImage image;
            byte[] imageByte;
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(image_string);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
            ImageIO.write(image, "jpg", new File(pth));
        } catch (IOException e){
                e.printStackTrace();
        }
    }


    /**
        * Carrega Teclat
        * @param pth És el path on hi ha le fitxer a carregar.
        * @return String amb totes les dades del fitxer.
        */	    
    public String loadKeyboard(String pth) throws PROPKeyboardException {
        Path path = Paths.get(pth);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: Path");
        }
        JSONObject s = new JSONObject();
        JSONObject keyboard = new JSONObject();

        String aux = new String();
        for (int i = 0; i < lines.size(); ++i) {
            aux += lines.get(i);
        }
        return aux;

    } 
    
    
    
    
    /**
	* Guarda Teclat
	* @param str Estructura on hi ha tots els paràmetres a guardar.
	*/
    public void saveKeyboard(String str) throws PROPKeyboardException {
        try {
            FileWriter write = null;
            String pth = null;
            JSONObject s = null;
            JSONObject keyboard = null;
            try {
                s = new JSONObject(str);
            } catch (JSONException ex) {
                System.out.println(ex.getCause());
                throw new PROPKeyboardException("Error saveKeyboard: JSON string bad format");
            }
            try {
                pth = s.get("path").toString();
            } catch (JSONException ex) {
                System.out.println(ex.getCause());
                throw new PROPKeyboardException("Error saveKeyboard: path to string");
            }
            try {
                write = new FileWriter(pth,false);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: path");
            }
            PrintWriter print_line = new PrintWriter(write);   
            
            print_line.print(str);
            print_line.close();
        } catch (Exception e) {
            throw new PROPKeyboardException("Error loadAlphabet: Error Intern");
        }    
    }
    
    /**
	* Carrega Text.
	* @param pth És el path on hi ha le fitxer a carregar.
	* @return String amb totes les dades del fitxer.
	*/
    public String loadText(String pth) throws PROPKeyboardException {
        try {
            Path path = Paths.get(pth);
            List<String> lines = null;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error loadText: Path");
            }
            String s = lines.get(0);
            for (int i = 1; i < lines.size(); ++i) {
                s = s + lines.get(i);
            }
            return s;
        }catch (Exception e) {
            throw new PROPKeyboardException("Error loadAlphabet: Error Intern");
        }
    }
    
    /**
	* Guarda Text
	* @param str Estructura on hi ha tots els paràmetres a guardar.
	*/
    public void saveText(String str) throws PROPKeyboardException {
        try {
            String pth = null;
            JSONObject s = null;
            try {
                s = new JSONObject(str);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveText: JSON String bad format");
            }
            try {
                pth = s.get("path").toString();
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveText: path to string");
            }
            FileWriter write = null;
            try {
                write = new FileWriter(pth,false);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error saveText: open file");
            }
            PrintWriter print_line = new PrintWriter(write);
            try {
                print_line.print(s.get("text").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveText: writting text");
            }
            print_line.close();
        }catch (Exception e) {
            throw new PROPKeyboardException("Error loadAlphabet: Error Intern");
        }
    }
    
}





