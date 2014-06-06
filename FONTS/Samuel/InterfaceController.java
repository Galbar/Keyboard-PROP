package classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.json.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.Color;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.font.TextAttribute;
import javax.swing.border.*;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author samuel.bryan.pierno
 */
public class InterfaceController {
    private String text = "";
    private String alphabet_chars = "";
    private String alphabet_name = "";
    private String alphabet_path = "";
    private Vector<String> references = new Vector<String>();
    private String frequencyFilepPath = new String();
    private static InterfaceController instance;
    private InterfaceController(){
    }
    public static InterfaceController getInstance(){
        if(instance == null){
            instance = new InterfaceController();
        }
        return instance;
    }
    
    public void setText(String new_text) {
        text = new_text;
    }

    public void setAlphabet(String new_alphabet_chars, String new_alphabet_name) {
        alphabet_name = new_alphabet_name;
        alphabet_chars = new_alphabet_chars;
    }
    
    public void setSettings(String settings) {}// Jo no la faria servir, cridaria directament calculateKeyborad(params);

    public void calculateKeyboard(String settings, String keyboard_name, boolean force) {
        System.out.println("calculate");
        try {
            JSONObject j = new JSONObject();
            j.put("topology", settings);
            j.put("alphabet_path", alphabet_path);
            j.put("name", keyboard_name);
            JSONArray txtArr = new JSONArray();
            for (int i = 0; i < references.size(); ++i) {
                txtArr.put(references.get(i));
            }
            j.put("texts", txtArr);
            j.put("force_BB", force);
            j.put("frequency_file", frequencyFilepPath);
            String res = DomainController.getInstance().calculateKeyboard(j.toString()); // retorna Json
            JSONObject jinfo = new JSONObject(res);
            JSONObject k = jinfo.getJSONObject("keyboard");
            // UN COP TINC LA RESPOSTA:

            //String topology_type = j.getString("topology");
            JSONArray jchars = k.getJSONArray("characters");
            JSONArray jpos = k.getJSONArray("positions");
            JSONArray jassig = k.getJSONArray("assignments");

            Vector<String> chars = new Vector<String>();//jchars.length());
            Vector<Integer> rels = new Vector<Integer>();//jassig.length());
            Vector<Position> coords = new Vector<Position>();//jpos.length());
            System.out.println(jchars.length());
            for (int i = 0; i < jchars.length(); ++i) {
                
                chars.add(jchars.getString(i));
                Position pos = new Position(new Float(jpos.getJSONObject(i).getDouble("x")), new Float(jpos.getJSONObject(i).getDouble("y")));
                System.out.println(i);
                coords.add(pos);
                rels.add(jassig.getInt(i));
            }
            float score = (float)k.getDouble("score");
            SolutionView.getInstance(chars, rels, coords, score);

        } catch (JSONException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al calcular el teclat.", "Error", JOptionPane.ERROR_MESSAGE);            
        }
        catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al calcular el teclat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static String loadFrequencies(String path) {
        String result = "error";
        try {
            result = DomainController.getInstance().loadFrequency(path);
        } catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al carregar les freqüències.", "Error", JOptionPane.ERROR_MESSAGE);            
        }
        return result;
    }
    
    public static String loadKeyboard(String path) {
        String result = "error";
        try {
            result = DomainController.getInstance().loadKeyboard(path);
        } catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al carregar el teclat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public String loadText(String path) {
        String result = "error";
        try {
            result = DomainController.getInstance().loadText(path);
            Text.getInstance("e").setText(result);
        } catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al carregar el text.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public String loadAlphabet(String path) {
        alphabet_path = path;
        String result = "error";
        try {
            result = DomainController.getInstance().loadAlphabet(path);
            JSONObject j = new JSONObject(result);
            String name = j.getString("name");
            JSONArray jchars  = j.getJSONArray("characters");
            System.out.println(jchars.toString());
            
            alphabet_chars = "";
            for (int i = 0; i < jchars.length(); ++i) {
                String ch = jchars.getString(i);
                if (i != 0) alphabet_chars += ",";
                if (ch.equals(",")) ch = "\\,";
                alphabet_chars += ch;
            }
            AlphabetView.getInstance("e").setText(alphabet_chars, name);
        } catch (PROPKeyboardException ex) {
            System.out.println("save");
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al tractar l'alfabet.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (JSONException ex) {
            System.out.println("save");
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al tractar l'alfabet.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public static void saveKeyboard(String path) {
        try {
            DomainController.getInstance().saveKeyboard(path);
        } catch (PROPKeyboardException e) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al guardar el teclat.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void saveImage(String path, String image_string) {
        // Save Image
        try {
            JSONObject i = new JSONObject();
            i.put("path", path);
            i.put("image_string", image_string);
            DomainController.getInstance().saveKeyboardImage(i.toString());
        } catch (JSONException ex) {
            
        } catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al guardar l'imatge.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    public void saveText(String path) {
        try {
            JSONObject i = new JSONObject();
            i.put("path", path);
            i.put("text", text);
            DomainController.getInstance().saveText(i.toString());
        } catch (JSONException ex) {
            
        } catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
             JOptionPane.showMessageDialog(frame,"Error al guardar el text.", "Error", JOptionPane.ERROR_MESSAGE);           
        }
    }
    
    public void saveAlphabet(String path) {
        try {
            JSONObject s = new JSONObject();
            try {
                s.put("name", alphabet_name);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: reading name");
            }

            try {
                s.put("path", path);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: reading path");
            }
            
            JSONArray a = new JSONArray();
            String foo = "";
            int num_chars = 0;
            for (int i = 0; i < alphabet_chars.length(); i = ++i) {
                 if (alphabet_chars.charAt(i) != ',') {
                    if (alphabet_chars.charAt(i) == '\\') {
                        ++i;
                        foo += alphabet_chars.charAt(i);
                    }
                    else
                        foo += alphabet_chars.charAt(i);
                 }
                 else {
                     a.put(foo);
                     foo = "";
                     ++num_chars;
                 }
            }
            a.put(foo);
            ++num_chars;
            //foo = " ";
            //a.put(foo);
            //++num_chars;

            try {
                s.put("characters",a);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: reading characters");
            }

            try {
                s.put("number", num_chars);
            } catch (JSONException ex) {
                throw new PROPKeyboardException("Error saveAlphabet: reading number");
            }
            DomainController.getInstance().saveAlphabet(s.toString());
        }
        //} catch (Exception e) {
        //    throw new PROPKeyboardException("Error saveAlphabet: Error Intern");
        //}
        catch (PROPKeyboardException ex) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al tractar l'alfabet.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void saveFrequencies(String path){}

    public static float swap(int id1, int id2) {
        try {
            JSONObject j = new JSONObject();
            j.put("id1", id1);
            j.put("id2", id2);
            return (float)(new JSONObject(DomainController.getInstance().swap(j.toString()))).getDouble("score");
        } catch (JSONException e) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al fer el swap.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (PROPKeyboardException e) {
            Frame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame,"Error al fer el swap.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0f;
    }

    public addText(String new_text) {
        references.add(new_text); 
    }

    public setFrequencyFile(String path) {
        frequencyFilepPath = path;
    }
}
