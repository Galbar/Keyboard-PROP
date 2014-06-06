
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JButton cancel_button = new JButton("Cancel·la");
    private JLabel nom = new JLabel("Nom:");
    private JLabel forcexp = new JLabel("Forçar B&B:");
    private JTextField name = new JTextField();
    private JFormattedTextField width = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JFormattedTextField heigth = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JComboBox topology = new JComboBox();
    private JCheckBox force = new JCheckBox();
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
        frame.setMinimumSize(new Dimension(700, 250));
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
        force.setVisible(visible);
        forcexp.setVisible(visible);
        name.setColumns(10);
        nom.setVisible(visible);
        name.setVisible(visible);
        alpha_button.setVisible(visible);
        text_button.setVisible(visible);
        create_button.setVisible(visible);
        cancel_button.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        forcexp.setEnabled(enabled);
        force.setEnabled(enabled);
        nom.setEnabled(enabled);
        name.setEnabled(enabled);
        alpha_button.setEnabled(enabled);
        text_button.setEnabled(enabled);
        create_button.setEnabled(enabled);
        cancel_button.setEnabled(enabled);
        topology.setEnabled(enabled);
    }
    
    private void setListeners(){
        alpha_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                AlphabetView.getInstance("k");
                setEnabled(false);
            }
        }); 
        text_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
               Text.getInstance("k");
               setEnabled(false);
            }
        });
        create_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Loader.getInstance();
                setEnabled(false);
                InterfaceController.getInstance().calculateKeyboard(topology.getSelectedItem().toString(),name.getText(), force.isSelected()); 
                setVisible(false);
            }
        });
        cancel_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                MainWindow.getInstance();
                setVisible(false);
                setEnabled(false);
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
        buttons_panel.add(forcexp,c);
        buttons_panel.add(force,c);
        c.gridy=1;        
        buttons_panel.add(topology,c);
        buttons_panel.add(nom,c);
        buttons_panel.add(name,c);
        c.gridy=2;
        c.gridx=1;
        buttons_panel.add(create_button,c);
        c.gridx=3;
        buttons_panel.add(cancel_button,c);         
    }
    
    private void initializeTopology(){
        topology.addItem("Rectangular");
        topology.addItem("Circular");
    }
}
