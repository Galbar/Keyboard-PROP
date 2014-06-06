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
        
        /* LOAD NAME */
        try {
            keyboard.put("name", lines.get(0));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading name");
        }
        
        /* LOAD TOPOLOGY */
        try {
            keyboard.put("topology", lines.get(2));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading topology");
        }
        
        int cont = 4;        
        String aux = new String();
        String next = new String();
        JSONArray a = new JSONArray();
        String solution = new String();        
        
        
        /* LOAD SCORE */
        try {
            keyboard.put("score", lines.get(cont));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading score");
        }
        cont += 2;
        
        /* LOAD ALPHABET_NAME */
        try {
            keyboard.put("alphabet_name", lines.get(cont));
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading alphabet_name");
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
            keyboard.put("references", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading references");
        }
        
        /* LOAD POSITIONS */
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
            keyboard.put("positions", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading positions");
        }
        try {
            s.put("keyboard", keyboard);
        } catch (JSONException ex) {
            throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
        }
        
        /* LOAD ASSIGMENTS */
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
            keyboard.put("assigments", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading assigments");
        }
        try {
            s.put("keyboard", keyboard);
        } catch (JSONException ex) {
            throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
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
        for (int i = 0; i < aux.length()-1; ++i) {
            if (aux.charAt(i) == '\\') {
                solution += ' ';
                ++i;
            }
            else if (aux.charAt(i) != ' ') {
                solution += aux.charAt(i);
            }
            else {
                a.put(solution);
                solution = new String();
            }
        }
        a.put(solution);
        try {
            keyboard.put("characters", a);
        } catch (JSONException ex) {
                throw new PROPKeyboardException("Error loadKeyboard: reading characters");
        }
        try {
            s.put("keyboard", keyboard);
        } catch (JSONException ex) {
            throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
        }
        
        /* LOAD AFFINITIES */
        aux = new String();
        next =new String();
        solution = new String();
        next = lines.get(cont);
        Vector v = new Vector();
        while (!next.equals("")) {
            v.add(lines.get(cont));
            ++cont;
            next = lines.get(cont);
        }
        ++cont;
     
        int n = v.size();
        JSONArray jmat = new JSONArray();
        for (int i = 0; i < n; ++i) {
            jmat.put(new JSONArray());
            int l = jmat.length()-1;
            aux = (String) v.get(i);
            for (int j = 0; j < aux.length(); ++j) {
                if (j%2 == 0) {
                    try {
                        jmat.getJSONArray(l).put(aux.charAt(j)-'0');
                    } catch (JSONException ex) {
                        throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
                    }
                } 
            }
        }
        try {
            s.put("affinities", jmat);
        } catch (JSONException ex) {
            throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
        }
        
        /* LOAD DISTANCES */
        aux = new String();
        next =new String();
        solution = new String();
        next = lines.get(cont);
        v = new Vector();
        boolean end = false;
        while (!next.equals("") && !end) {
            v.add(lines.get(cont));
            ++cont;
            if (cont < lines.size()) {
                next = lines.get(cont);
            }
            else 
                end = true;
        }
        ++cont;
     
        n = v.size();
        jmat = new JSONArray();
        for (int i = 0; i < n; ++i) {
            jmat.put(new JSONArray());
            int l = jmat.length()-1;
            aux = (String) v.get(i);
            for (int j = 0; j < aux.length(); ++j) {
                if (j%2 == 0) {
                    System.out.println("aux: "+aux.charAt(j));
                    try {
                        jmat.getJSONArray(l).put(aux.charAt(j)-'0');
                    } catch (JSONException ex) {
                        throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
                    }
                } 
            }
        }
        try {
            s.put("distances", jmat);
        } catch (JSONException ex) {
            throw new PROPKeyboardException("Error loadKeyboard: put keyboard");
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
            JSONObject keyboard = null;
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
                keyboard = (JSONObject) s.get("keyboard");
                print_line.println(keyboard.get("name").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing name");
            }
            print_line.println();

            /* PRINT TOPOLOGY */
            try {
                print_line.println(keyboard.get("topology").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing topology");
            }
            print_line.println("");

            
            
            /* PRINT SCORE */
            try {
                print_line.println(keyboard.get("score").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing score");
            }
            print_line.println("");
            
            /* PRINT ALPHABET_NAME */
            try {
                print_line.println(keyboard.get("alphabet_name").toString());
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing alphabet_name");
            }
            print_line.println("");

            /* PRINT REFERENCES */
            try {
                for (int i = 0; i < (keyboard.getJSONArray("references").length()); ++i) {
                    print_line.println(keyboard.getJSONArray("references").getString(i));
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing references");
            }
           print_line.println("");

           /* PRINT POSITIONS */
            try {
                for (int i = 0; i < (keyboard.getJSONArray("positions").length()); ++i) {
                    print_line.print(keyboard.getJSONArray("positions").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing positions");
            }
           print_line.println("");
           print_line.println("");
           
           /* PRINT ASSIGMENTS */
            try {
                for (int i = 0; i < (keyboard.getJSONArray("assigments").length()); ++i) {
                    print_line.print(keyboard.getJSONArray("assigments").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing assigments");
            }
           print_line.println("");
           print_line.println("");
           
           /* PRINT CHARACTERS */
            try {
                for (int i = 0; i < (keyboard.getJSONArray("characters").length()); ++i) {
                    if(keyboard.getJSONArray("characters").getString(i).equals(" ")) {
                        print_line.print("\\");
                    }
                    print_line.print(keyboard.getJSONArray("characters").getString(i));
                    print_line.print(" ");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing characters");
            }
            print_line.println("");
            print_line.println("");
            
            
            /* PRINT RELATIONS */
            /* Estan ordenades de tal manera que el primer valor de la relació serà
            el [0,0], el segon el [0,1]...
            */
            JSONArray affinities = s.getJSONArray("affinities");
            try {
                
                int n = affinities.length();
                JSONArray jmat = new JSONArray();
                for (int i = 0; i < n; ++i) {
                    jmat = affinities.getJSONArray(i);
                    for (int j = 0; j < n; ++j) {
                        print_line.print(jmat.get(j));
                        print_line.print(" ");
                    }
                    print_line.println("");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing affinities");
            }
            print_line.println("");

            
            /* PRINT ASSIGMENTS */
            JSONArray distances = s.getJSONArray("distances");
            try {
               
                int n = distances.length();
                JSONArray jmat = new JSONArray();
                for (int i = 0; i < n; ++i) {
                    jmat = distances.getJSONArray(i);
                    for (int j = 0; j < n; ++j) {
                        print_line.print(jmat.get(j));
                        print_line.print(" ");
                    }
                    print_line.println("");
                }
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveKeyboard: writing distances");
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





