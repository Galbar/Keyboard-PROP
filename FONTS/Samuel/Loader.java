
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
public class Loader {
    private JFrame frame = new JFrame("Carregant...");
    private JPanel pane = new JPanel();
    private JLabel label = new JLabel("S'està calculant la solució");
    private static Loader instance;
    private Loader(){
        initialize();
    }
    public static Loader getInstance(){
        if(instance == null) instance = new Loader();
        instance.frame.setLocationRelativeTo(null);
        instance.setVisible(true);
        return instance;
    }
    private void initializeFrame() {
        // Tamanyo
        frame.setMinimumSize(new Dimension(200, 100));
        frame.setPreferredSize(frame.getMinimumSize());
        frame.setResizable(true);
        // Posicion y operaciones por defecto
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.add(pane);
    }
    
    public void setVisible(boolean visible){
        pane.setVisible(visible);
        frame.pack();
        frame.setVisible(visible);
    }
    
    private void initialize(){
        initializeFrame();
        initializePanel();
        frame.pack();
        frame.setVisible(true);
    }
    
    private void initializePanel(){
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        pane.add(label);
    }
    
    public static void done(){
        instance.setVisible(false);
    }
}
