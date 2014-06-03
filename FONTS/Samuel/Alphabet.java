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
public class Alphabet {
    private JFrame frame = new JFrame("Gestionar alfabet");
    private JPanel buttons_panel = new JPanel();
    private JTextField character = new JTextField();
    private JButton add = new JButton("Afegir caracters"); //DEPRECATED?
    private JButton load = new JButton("Carregar alfabet");
    private JButton save = new JButton("Guardar alfabet");
    private JButton cancel = new JButton("Cancel·la");
    private JLabel alphabet = new JLabel();
    private String callback;
    private static Alphabet instance;
    
    private Alphabet(){
        initialize();
    }
    public static Alphabet getInstance(String called){
        if(instance == null)instance = new Alphabet();
        instance.frame.setLocationRelativeTo(null);
        instance.setEnabled(true);
        instance.setVisible(true);
        if(called != "e")instance.callback=called;
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
        add.setVisible(visible);
        alphabet.setText("Alfabet: ");
        character.setColumns(30);
        load.setVisible(visible);
        save.setVisible(visible);
        cancel.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        add.setEnabled(enabled);
        load.setEnabled(enabled);
        save.setEnabled(enabled);
        cancel.setEnabled(enabled);
        character.setEnabled(enabled);
    }
    
    private void setListeners(){ 
        add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                
            }
        });
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Explorer.getInstance("a");
                setVisible(false);
                setEnabled(false);
            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Explorer.getInstance("ga"+callback);
                setVisible(false);
                setEnabled(false);
            }
        });
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if(callback=="m")
                    MainWindow.getInstance();
                else
                    NewKeyboard.getInstance();
                setEnabled(false);
                setVisible(false);
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeButtonsPanel();
        setListeners();
        character.setToolTipText("Introduir caracters separats per espais");
        frame.pack();
        frame.setVisible(true);
    }
    
    private void initializeButtonsPanel(){
        buttons_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        c.gridwidth=2;
        buttons_panel.add(alphabet,c);
        c.gridy=1;
        buttons_panel.add(character,c);
        c.gridwidth=1;
        c.gridy=2;
        buttons_panel.add(add,c);
        c.gridx=1;
        buttons_panel.add(load,c);
        c.gridy=3;
        c.gridx=0;
        buttons_panel.add(save,c);
        c.gridx=1;
        buttons_panel.add(cancel,c);
    }
    
   
}
