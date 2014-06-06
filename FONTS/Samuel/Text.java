
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.swing.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 *
 * @author samuel.bryan.pierno
 */
public class Text {
    private JFrame frame = new JFrame("Gestionar text");
    private JPanel buttons_panel = new JPanel();
    @SuppressWarnings("FieldMayBeFinal")
    private JTextArea text = new JTextArea(15,35);
    private JButton afegir_text = new JButton("Afegir text");
    private JButton load = new JButton("Carregar arxiu");
    private JButton save = new JButton("Guardar arxiu");
    private JButton cancel = new JButton("Fet");
    private JLabel text_label = new JLabel();
    private JScrollPane scroll = new JScrollPane(text);
    private static Text instance;
    private String callback;
    private String currentTextPath = new String();
    
    private JCheckBox freqs = new JCheckBox();
    private JLabel texts = new JLabel("Freqüències:");
    
    private Text(){
        initialize();
    }
    public static Text getInstance(String s){
        if(instance == null)instance = new Text();
        instance.frame.setLocationRelativeTo(null);
        instance.setEnabled(true);
        instance.setVisible(true);
        if(!"e".equals(s)) instance.callback=s;
        return instance;
    }
    private void initializeFrame() {
        // Tamanyo
        frame.setMinimumSize(new Dimension(450, 400));
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
        afegir_text.setVisible(visible);
        load.setVisible(visible);
        save.setVisible(visible);
        cancel.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    public void setEnabled(boolean enabled){
        frame.setEnabled(enabled);
        afegir_text.setEnabled(enabled);
        load.setEnabled(enabled);
        save.setEnabled(enabled);
        cancel.setEnabled(enabled);
        text.setEnabled(enabled);
    }
    
    private void setListeners(){ 
        afegir_text.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
               InterfaceController.getInstance().addText(currentTextPath);
            }
        });
        load.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Explorer.getInstance("t");
                setEnabled(false);
            }
        });
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if(freqs.isSelected())
                    Explorer.getInstance("gtq");
                else
                    Explorer.getInstance("gt");
                InterfaceController.getInstance().setText(text.getText());
                setEnabled(false);
            }
        });
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if (callback.equals("k")) {
                    NewKeyboard.getInstance();
                }
                else if (callback.equals("m")) {
                    MainWindow.getInstance();
                }
                setEnabled(false);
                setVisible(false);
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeText();
        initializeButtonsPanel();
        setListeners();
        frame.pack();
        frame.setVisible(true);
    }
    
    private void initializeText(){
        text_label.setText("Text:");
        text.setLineWrap(true);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    }
    private void initializeButtonsPanel(){
        buttons_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        c.gridwidth=2;
        buttons_panel.add(text_label,c);
        c.gridy=1;
        c.gridheight=5;
        buttons_panel.add(scroll,c);
        c.gridwidth=1;
        c.gridheight=1;
        c.gridy=6;
        buttons_panel.add(texts,c);
        buttons_panel.add(freqs,c);
        c.gridy=7;
        buttons_panel.add(afegir_text,c);
        c.gridx=1;
        buttons_panel.add(load,c);
        c.gridy=8;
        c.gridx=0;
        buttons_panel.add(save,c);
        c.gridx=1;
        buttons_panel.add(cancel,c);
    }

    public void setText(String new_text, String path) {
        text.setText(new_text);
        currentTextPath = path;
    }
}
