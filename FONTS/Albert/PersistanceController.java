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
            try {
                s.put("name",lines.get(0));
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadAlphabet: reading name");
            }
            try {
                s.put("number",lines.get(2));
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadAlphabet: reading number");
            }
            String aux = lines.get(4);
            for (int i = 5; i < lines.size(); ++i) {
                aux = lines.get(i) + aux;
            }
            JSONArray a = new JSONArray();
            String foo = "";
            for (int i = 0; i < aux.length(); i = ++i) {
                 if (aux.charAt(i) != ' ') {
                     foo += aux.charAt(i);
                 }
                 else {
                     a.put(foo);
                     foo = "";
                 }
            }
            a.put(foo);
            try {
                s.put("characters",a);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadAlphabet: reading characters");
            }
            return s.toString();
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

            /* PRINT NAME */
            try {
                print_line.println(s.get("name").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: writing name");
            }
            print_line.println("");
            
            /* PRINT NUMBER */
            try {
                print_line.println(s.get("number").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: writing number");
            }
            print_line.println("");
            
            /* PRINT CHARACTERS */
            try {
                for (int i = 0; i < (s.getDouble("number")); ++i) {
                    print_line.print(s.getJSONArray("characters").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: writing characters");
            }
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

        /* LOAD NAME */
        try {
            s.put("name", lines.get(0));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading name");
        }

        /* LOAD TOPOLOGY */
        try {
            s.put("topology", lines.get(2));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading topology");
        }

        /* LOAD SPECIAL CHARS */
        String aux = new String();
        String next = new String();
        int cont = 4;        
        next = lines.get(cont);
        while (!next.equals("")) {
            aux += lines.get(cont);
            ++cont;
            next = lines.get(cont);
        }
        ++cont;
        JSONArray a = new JSONArray();
        String solution = new String();
        for (int i = 0; i < aux.length(); ++i) {
            if (aux.charAt(i) != ' ') {
                solution += aux.charAt(i);
            }
            else {
                a.put(solution);
                solution = new String();
            }
        }
        a.put(solution);
        try {
            s.put("specialChars", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading special characters");
        }

        /* LOAD heigth */
        try {
            s.put("heigth", lines.get(cont));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading heigth");
        }
        cont += 2;

        /* LOAD WIDTH */
        try {
            s.put("width", lines.get(cont));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading width");
        }
        cont += 2;

        /* LOAD REFERENCES */
        aux = new String();
        next =new String();
        next = lines.get(cont);
        a = new JSONArray();
        while (!next.equals("")) {
            a.put(lines.get(cont));
            ++cont;
            next = lines.get(cont);
        }
        ++cont;
        try {
            s.put("references", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading references");
        }


        /* LOAD RELATIONS */
        aux = new String();
        next =new String();
        solution = new String();
        next = lines.get(cont);
        while (!next.equals("")) {
            aux += lines.get(cont);
            ++cont;
            next = lines.get(cont);
        }
        ++cont;
        a = new JSONArray();
        for (int i = 0; i < aux.length(); ++i) {
            if (aux.charAt(i) != ' ') {
                solution += aux.charAt(i);
            }
            else {
                a.put(solution);
                solution = new String();
            }
        }
        a.put(solution);
        try {
            s.put("relations", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading relations");
        }

        /* LOAD CHARACTERS */
        aux = new String();
        next =new String();
        solution = new String();
        next = lines.get(cont);
        while (!next.equals("")) {
            aux += lines.get(cont);
            ++cont;
            next = lines.get(cont);
        }
        ++cont;
        a = new JSONArray();
        for (int i = 0; i < aux.length(); ++i) {
            if (aux.charAt(i) != ' ') {
                solution += aux.charAt(i);
            }
            else {
                a.put(solution);
                solution = new String();
            }
        }
        a.put(solution);
        try {
            s.put("characters", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading characters");
        }

        /* LOAD ASSIGMENTS */
        aux = new String();
        next =new String();
        solution = new String();
        next = lines.get(cont);
        boolean end = false;
        while (!next.equals("") && !end) {
            aux += lines.get(cont);
            ++cont;
            if (cont < lines.size()) {
                next = lines.get(cont);
            }
            else 
                end = true;
        }
        ++cont;
        a = new JSONArray();
        for (int i = 0; i < aux.length(); ++i) {
            if (aux.charAt(i) != ' ') {
                solution += aux.charAt(i);
            }
            else {
                a.put(solution);
                solution = new String();
            }
        }
        a.put(solution);
        try {
            s.put("assigments", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading assigments");
        }

        return s.toString();
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
            try {
                s = new JSONObject(str);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: JSON string bad format");
            }
            try {
                pth = s.get("path").toString();
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: path to string");
            }
            try {
                write = new FileWriter(pth,false);
            } catch (IOException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: path");
            }
            PrintWriter print_line = new PrintWriter(write);   
            
            /* PRINT NAME */
            try {
                print_line.println(s.get("name").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing name");
            }
            print_line.println();

            /* PRINT TOPOLOGY */
            try {
                print_line.println(s.get("topology").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing topology");
            }
            print_line.println("");

            /* PRINT SPECIAL CHARACTERS */
            try {
                for (int i = 0; i < (s.getJSONArray("characters").length()); ++i) {
                    print_line.print(s.getJSONArray("characters").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing special characters");
            }
            print_line.println("");
            print_line.println("");
            
            /* PRINT HEIGHT */
            try {
                print_line.println(s.get("heigth").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing height");
            }
            print_line.println("");

            /* PRINT WIDTH */
            try {
                print_line.println(s.get("width").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing width");
            }
            print_line.println("");

            /* PRINT REFERENCES */
            try {
                for (int i = 0; i < (s.getJSONArray("references").length()); ++i) {
                    print_line.println(s.getJSONArray("references").getString(i));
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing references");
            }
           print_line.println("");

            /* PRINT RELATIONS */
            /* Estan ordenades de tal manera que el primer valor de la relació serà
            el [0,0], el segon el [0,1]...
            */
            try {
                for (int i = 0; i < (s.getJSONArray("relations").length()); ++i) {
                    print_line.print(s.getJSONArray("relations").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing relations");
            }
            print_line.println("");
            print_line.println("");

            /* PRINT CHARACTERS */
            try {
                for (int i = 0; i < (s.getJSONArray("characters").length()); ++i) {
                    print_line.print(s.getJSONArray("characters").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing characters");
            }
            print_line.println("");
            print_line.println("");

            /* PRINT ASSIGMENTS */
            try {
                for (int i = 0; i < (s.getJSONArray("assigments").length()); ++i) {
                    print_line.print(s.getJSONArray("assigments").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing assigments");
            }
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





