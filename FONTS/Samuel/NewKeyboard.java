
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author samuel.bryan.pierno
 */
public class NewKeyboard {
    private JFrame frame = new JFrame("Crear teclat");
    private JPanel buttons_panel = new JPanel();
    private JButton alpha_button = new JButton("Gestionar alfabet");//("Manage alphabet");
    private JButton text_button = new JButton("Gestionar text");//("Manage text");
    private JButton create_button = new JButton("Crear teclat");//("Create keyboard");
    private JComboBox topology = new JComboBox();
    private static NewKeyboard instance;
    
    private NewKeyboard(){
        initialize();
    }
    public static NewKeyboard getInstance(){
        if(instance == null)instance = new NewKeyboard();
        instance.frame.setLocationRelativeTo(null);
        instance.setEnabled(true);
        instance.setVisible(true);
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
        alpha_button.setVisible(visible);
        text_button.setVisible(visible);
        create_button.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        alpha_button.setEnabled(enabled);
        text_button.setEnabled(enabled);
        create_button.setEnabled(enabled);
        topology.setEnabled(enabled);
    }
    
    private void setListeners(){
        alpha_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                AlphabetView.getInstance("k");
                setVisible(false);
                setEnabled(false);
            }
        }); 
        text_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
               Text.getInstance("k");
               setVisible(false);
               setEnabled(false);
            }
        });
        create_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Loader.getInstance();
                setVisible(false);
                setEnabled(false);
                InterfaceController.setSettings(topology.getSelectedItem().toString());
                Loader.done();
                //InterfaceController.createKeyboard(); // Falten els parametres.
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeTopology();
        initializeButtonsPanel();
        setListeners();
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
        buttons_panel.add(topology,c);
        c.gridy=2;
        c.gridx=2;
        buttons_panel.add(create_button,c);
    }
    
    private void initializeTopology(){
        topology.addItem("Rectangular");
        topology.addItem("Circular");
    }
}
