
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        if(instance == null) instance = new Explorer();
        switch (b) {
            case "a":
                instance.frame.setTitle("Carregar alfabet");
                break;
            case "m":
                instance.frame.setTitle("Carregar teclat");
                break;
            case "t":
                instance.frame.setTitle("Carregar text");
                break;
            case "tq":
                instance.frame.setTitle("Carregar frequències");
                break;
            case "ga":
                instance.frame.setTitle("Guardar alfabet");
                b = "a";
                break;
            case "gt":
                instance.frame.setTitle("Guardar text");
                break;
            case "gtq":
                instance.frame.setTitle("Guardar frequències");
                break;
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
                switch (callback) {
                    case "a":
                        Alphabet.getInstance("e");
                        break;
                    case "m":
                        MainWindow.getInstance();
                        break;
                    case "t": case "tq": case "gt":case "gtq":
                        Text.getInstance("e");
                        break;
                    case "k":
                        NewKeyboard.getInstance();
                }
                if("ApproveSelection".equals(ae.getActionCommand())){
                    e_path=explore.getSelectedFile();
                    explore.accept(e_path);
                    log.append("\n"+e_path.getAbsolutePath());
                    switch (callback) {
                        case "a":
                            InterfaceController.loadAlphabet(e_path.getAbsolutePath());
                            break;
                        case "m":
                            InterfaceController.loadKeyboard(e_path.getAbsolutePath());
                            break;
                        case "t":
                            InterfaceController.loadText(e_path.getAbsolutePath());
                            break;
                        case "tq":
                            InterfaceController.loadFrequencies(e_path.getAbsolutePath());
                            break;
                        case "gt":
                            InterfaceController.saveText(e_path.getAbsolutePath());
                            break;
                        case "gtq":
                            InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                            break;
                        case "gtm":
                            MainWindow.getInstance();
                            InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                            break;
                        case "gtk":
                            NewKeyboard.getInstance();
                            InterfaceController.saveFrequencies(e_path.getAbsolutePath());
                            break;
                        case "gam":
                            MainWindow.getInstance();
                            InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                            break;
                        case "gak":
                            NewKeyboard.getInstance();
                            InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                            break;
                        case "ga":
                            InterfaceController.saveAlphabet(e_path.getAbsolutePath());
                            break;
                    }
                }else{
                    switch(callback){
                        case "gak": case "gam":
                            Alphabet.getInstance("e");
                            break;
                        case "gtm": case "gtk":
                            Text.getInstance("e");
                            break;
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
