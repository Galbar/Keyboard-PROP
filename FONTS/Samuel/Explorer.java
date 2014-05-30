
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 *
 * @author samuel.bryan.pierno
 */
public class Explorer {
    private JFrame frame = new JFrame("Gestionar alfabet");
    private JPanel buttons_panel = new JPanel();
    JButton openButton, saveButton;
    JTextArea log;
    JFileChooser explore;
    private static Explorer instance;
    
    private Explorer(){
    }
    public static Explorer getInstance(){
        if(instance == null)instance = new Explorer();
        instance.initialize();
        return instance;
    }
    private void initializeFrame() {
        // Tamanyo
        frame.setMinimumSize(new Dimension(450, 200));
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
               // if (ae.getSource() == openButton) {
                    int returnVal = explore.showOpenDialog(frame);
                    System.out.print(ae.getSource());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = explore.getSelectedFile();
                        System.out.print("Opening: " + file.getName() + ".\n" );
                    } else {
                        System.out.print("Open command cancelled by user.\n");
                    }
                }            
            //}
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeButtonsPanel();
        initializeLog();
        initializeExplore();
        setListeners();
        setEnabled(true);
        setVisible(true);
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
    }
    
    private void initializeExplore(){
        explore = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
   
}
