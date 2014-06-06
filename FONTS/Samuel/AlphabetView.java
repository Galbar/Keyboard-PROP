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
public class AlphabetView {
    private JFrame frame = new JFrame("Gestionar alfabet");
    private JPanel buttons_panel = new JPanel();
    private JTextField character = new JTextField();
    private JButton load = new JButton("Carregar alfabet");
    private JButton save = new JButton("Guardar alfabet");
    private JButton cancel = new JButton("Cancel·la");
    private JLabel alphabet = new JLabel("Alfabet:");
    private JTextField name = new JTextField();
    private JLabel nom = new JLabel("Nom:");
    private String callback;
    private static AlphabetView instance;
    
    private AlphabetView(){
        initialize();
    }
    public static AlphabetView getInstance(String called){
        if(instance == null)instance = new AlphabetView();
        instance.frame.setLocationRelativeTo(null);
        instance.setEnabled(true);
        instance.setVisible(true);
        if(called != "e")instance.callback=called;
        return instance;
    }
    private void initializeFrame() {
        // Tamanyo
        frame.setMinimumSize(new Dimension(550, 200));
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
        name.setVisible(visible);
        nom.setVisible(visible);
        character.setColumns(45);
        load.setVisible(visible);
        save.setVisible(visible);
        cancel.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        name.setEnabled(enabled);
        nom.setEnabled(enabled);
        frame.setEnabled(enabled);
        load.setEnabled(enabled);
        save.setEnabled(enabled);
        cancel.setEnabled(enabled);
        character.setEnabled(enabled);
    }
    
    private void setListeners(){ 
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Explorer.getInstance("a");
                setEnabled(false);
            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Explorer.getInstance("ga");
                InterfaceController.getInstance().setAlphabet(character.getText(),name.getText());
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
        name.setToolTipText("nom del alfabet");
        name.setColumns(8);
        buttons_panel.add(nom,c);
        buttons_panel.add(name,c);
        c.gridy=1;
        c.gridwidth=3;
        buttons_panel.add(alphabet,c);
        c.gridy=2;
        buttons_panel.add(character,c);
        c.gridwidth=1;
        c.gridy=3;
        buttons_panel.add(load,c);
        buttons_panel.add(save,c);
        c.gridx=2;
        buttons_panel.add(cancel,c);
    }

    public void setText(String new_text, String new_name) {
        name.setText(new_name);
        character.setText(new_text);
    }
    
   
}
