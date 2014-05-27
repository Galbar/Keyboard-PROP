/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author samuel.bryan.pierno
 */
public class MainWindow {
    private JFrame frame = new JFrame("Vista Principal");//("Main Window");
    private JPanel buttons_panel = new JPanel();
    private JButton alpha_button = new JButton("Gestionar alfabet");//("Manage alphabet");
    private JButton text_button = new JButton("Gestionar text");//("Manage text");
    private JButton create_button = new JButton("Crear teclat");//("Create keyboard");
    private JButton load_button = new JButton("Carregar teclat");//("Load keyboard");
    
    public MainWindow(){
        initialize();
    }
    private void initializeFrame() {
        // Tamanyo
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(false);
        // Posicion y operaciones por defecto
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.add(buttons_panel);
    }
    
    public void setVisible(boolean visible){
        alpha_button.setVisible(visible);
        text_button.setVisible(visible);
        create_button.setVisible(visible);
        load_button.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        alpha_button.setEnabled(enabled);
        text_button.setEnabled(enabled);
        create_button.setEnabled(enabled);
        load_button.setEnabled(enabled);
    }
    
    private void setListeners(){
        alpha_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                //InterfaceController.getInstance().mainToAlphabet();
            }
        }); 
        text_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
               // InterfaceController.getInstance().mainToText();
            }
        });
        create_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                //InterfaceController.getInstance().mainToCreate();
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeButtonsPanel();
        setListeners();
        setEnabled(true);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void initializeButtonsPanel(){
        buttons_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        buttons_panel.add(alpha_button,c);
        buttons_panel.add(text_button,c);
        c.gridy=1;
        buttons_panel.add(create_button,c);
        buttons_panel.add(load_button,c);
    }
}
