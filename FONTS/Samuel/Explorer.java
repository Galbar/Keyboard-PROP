
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.*;
/**
 *
 * @author samuel.bryan.pierno
 */
public class Explorer {
    private JFrame frame ;
    private JPanel buttons_panel = new JPanel();
    JTextArea log;
    JFileChooser explore;
    private File l_path;
    private File e_path;
    private String callback;
    
    private static Explorer instance;
    
    private Explorer(){
        initialize();
    }
    public static Explorer getInstance(String b){
        if(instance == null)instance = new Explorer();
        if (b.equals("a")) {
            instance.frame.setTitle("Carregar alfabet");
        }
        else if (b.equals("m")) {
            instance.frame.setTitle("Carregar teclat");
        }
        else if (b.equals("t")) {
            instance.frame.setTitle("Carregar text");
        }
        else if (b.equals("tq")) {
            instance.frame.setTitle("Carregar frequències");
        }
        else if (b.equals("gam") || b.equals("gak")) {
            instance.frame.setTitle("Guardar alfabet");
            b = "a";
        }
        else if (b.equals("gtm") || b.equals("gtk")) {
            instance.frame.setTitle("Guardar text");
        }
        else if (b.equals("gtq")) {
            instance.frame.setTitle("Guardar frequències");
        }
        instance.explore.setCurrentDirectory(instance.l_path);
        instance.explore.setSelectedFile(null);
        instance.frame.setLocationRelativeTo(null);
        instance.setEnabled(true);
        instance.setVisible(true);
        instance.callback=b;
        return instance;
    }
    private void initializeFrame() {
        frame=new JFrame();
        // Tamanyo
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(true);
        // Posicion y operaciones por defecto
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.add(buttons_panel);
    }
    
    public void setVisible(boolean visible){
        explore.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        explore.setEnabled(enabled);
    }
    
    private void setListeners(){ 
        explore.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                instance.setEnabled(false);
                instance.setVisible(false);
                if (callback.equals("a")) {
                    AlphabetView.getInstance("e");
                }
                else if (callback.equals("m")) {
                    MainWindow.getInstance();
                }
                else if (callback.equals("t") || callback.equals("tq") || callback.equals("gt") || callback.equals("gtq")) {
                    Text.getInstance("e");
                }
                else if (callback.equals("k")) {
                    NewKeyboard.getInstance();
                }
                
                if("ApproveSelection".equals(ae.getActionCommand())){
                    e_path=explore.getSelectedFile();
                    explore.accept(e_path);
                    log.append("\n"+e_path.getAbsolutePath());
                    if (callback.equals("a")) {
                        InterfaceController.loadAlphabet(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("m")) {
                        InterfaceController.loadKeyboard(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("t")) {
                        InterfaceController.loadText(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("tq")) {
                        InterfaceController.loadFrequencies(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gt")) {
                        InterfaceController.saveText(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gtq")) {
                        InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gtm")) {
                        MainWindow.getInstance();
                        InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gtk")) {
                        NewKeyboard.getInstance();
                        InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gam")) {
                        MainWindow.getInstance();
                        InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("gak")) {
                        NewKeyboard.getInstance();
                        InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                    }
                    else if (callback.equals("ga")) {
                        InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                    }
                }else{
                    if (callback.equals("gak") || callback.equals("gam")){
                        AlphabetView.getInstance("e");
                    }
                    else if (callback.equals("gtm") || callback.equals("gtk")){
                        Text.getInstance("e");
                    }
                }
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeLog();
        initializeExplore();
        setListeners();
        initializeButtonsPanel();
        frame.pack();
        frame.setVisible(true);
    }
    
    private void initializeButtonsPanel(){
        buttons_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        buttons_panel.add(explore,c);
    }
    
    private void initializeLog(){
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
        logScrollPane.setVisible(true);
        //buttons_panel.add(logScrollPane); //uncomment to add log to the western part of the frame in order to debug filechooser
    }
    
    private void initializeExplore(){
        explore = new JFileChooser();
        explore.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        explore.setApproveButtonText("Acceptar");
        explore.setApproveButtonToolTipText(frame.getTitle());
    }
   
}
