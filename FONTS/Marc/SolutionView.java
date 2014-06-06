package classes;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math.*;
import java.awt.Color;
import javax.swing.event.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.font.TextAttribute;
import javax.swing.border.*;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;



public class SolutionView extends JFrame {

    //private DrawPanel draw_panel = new DrawPanel();
    private JPanel draw_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JPanel info_panel = new JPanel();
    private JButton save_button = new JButton("Guardar");

    private JButton close_button = new JButton("Tancar");
    private JButton swap_button = new JButton("Swap");
    private JButton export_image_button = new JButton("Exportar Imatge");

    JLabel scoreLabel = new JLabel("Puntuació: 0.0");
    JTextArea refsArea = new JTextArea(80, 60);

    private Vector<String> chars;
    private Vector<Integer> rels;
    private Vector<Position> coords; // temp
    private float score;
    private String[] references;
    private HashMap<JLabel, Integer> keys;
    private HashMap<JLabel, Integer> selected_keys; // Potser no sera vector

    private static SolutionView instance;

    public static SolutionView getInstance(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords, float scre, String[] references) {
        NewKeyboard.getInstance().setEnabled(false);
        NewKeyboard.getInstance().setVisible(false);
        Loader.getInstance().setVisible(false);
        if(instance == null) instance = new SolutionView(new_chars, new_rels, new_coords, scre, references);
        else {
            instance.reinitiate(new_chars, new_rels, new_coords, scre, references);
        }
        return instance;
    }

    public static SolutionView getInstance() {
        NewKeyboard.getInstance().setEnabled(false);
        NewKeyboard.getInstance().setVisible(false);
        Loader.getInstance().setVisible(false);
        if(instance == null) System.out.println("ERROOR... Esper-ho i Desitjo que mai vegis aquesta linia...");
        return instance;
    }

    private SolutionView(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords, float scre, String[] references) {
    	chars = new_chars;
    	rels = new_rels;
    	coords = new_coords;
        score = scre;
        this.references = references;
        keys = new HashMap<JLabel, Integer>();
        selected_keys = new HashMap<JLabel, Integer>();
        initialize();
    }

    private void initializeFrame() {
        this.setMinimumSize(new Dimension(500, 400));
        this.setPreferredSize(this.getMinimumSize());
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        contentPane.add(draw_panel);
        contentPane.add(button_panel);
        contentPane.add(info_panel);
    }
    
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    private void setListeners(){
        export_image_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.out.println("Guardar Imatge");
                BufferedImage image = ScreenImage.createImage(draw_panel);
                String image_string = encodeToString(image, "jpeg");
                System.out.println(image_string);
                Explorer e = Explorer.getInstance("i", image_string);
                setEnabled(false);
            }
               
        });
        close_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.out.println("Tancar Solucio");
                Frame frame = new JFrame("Tancar Solucio");
                 int result = JOptionPane.showConfirmDialog(
                frame,
                "Segur que vols tancar la solució?\nSi no s'ha guardat es perdrà!",
                "Tancar Solució?",
                JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0); 
                }
            }
        });
        swap_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                if (selected_keys.size() < 2) {
                    JOptionPane.showMessageDialog(null, "Selecciona dos tecles per fer el swap!");
                }
                else {
                    System.out.println("Swap Tecles");
                    int[] ids = new int[2];
                    JLabel[] labels = new JLabel[2];
                    int i = 0; 
                    for (Map.Entry<JLabel, Integer> entry : selected_keys.entrySet()) {
                        labels[i] = entry.getKey();
                        labels[i].setBackground(Color.LIGHT_GRAY);
                        ids[i] = selected_keys.get(labels[i]);
                        ++i;
                    }
                    score = InterfaceController.swap(ids[0], ids[1]); // NOT DONE YET!
                    info();
                    int aux = rels.get(ids[0]);
                    rels.set(ids[0], rels.get(ids[1]));
                    rels.set(ids[1], aux);
                    //keys.remove(labels[0]);
                    //keys.remove(labels[1]);
                    //keys.put(labels[0], ids[1]);
                    //keys.put(labels[1], ids[0]);
                    selected_keys.clear();
                    draw();
                }
            }
               
        });

        save_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.out.println("Guardar Solució");
                setEnabled(false);
                Explorer e = Explorer.getInstance("s");
                //String path = "aaa"; //e.getPath();
                //InterfaceController.saveKeyboard(path);
            }
               
        });



        for (Map.Entry<JLabel, Integer> entry : keys.entrySet()) {
            entry.getKey().addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent event){

                    JLabel label = (JLabel)event.getSource();
                    System.out.println("Select Key");

                    if (selected_keys.containsKey(label)) {
                        selected_keys.remove(label);
                        label.setBackground(Color.LIGHT_GRAY);
                    }
                    else {
                        if (selected_keys.size() == 2) {
                            selected_keys.remove(label);
                            label.setBackground(Color.LIGHT_GRAY);
                        }
                        else {
                            selected_keys.put(label, keys.get(label));
                            label.setBackground(Color.red);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent event) {
                    JLabel label = (JLabel)event.getSource();
                    Border border = BorderFactory.createLineBorder(Color.BLUE);
                    label.setBorder(border);
                }

                @Override
                public void mouseExited(MouseEvent event) {
                    JLabel label = (JLabel)event.getSource();
                    Border border = BorderFactory.createLineBorder(Color.BLACK);
                    label.setBorder(border);
                }
            });
        }
    }
    
    private void initialize(){
        initializeFrame();
        initializeDrawPanel();
        initializeInfoPanel();
        initializeButtonPanel();
        setListeners();
        this.pack();
        this.setVisible(true);
        this.setEnabled(true);
    }

    public void reinitiate(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords, float scre, String[] references) {
        chars = new_chars;
        rels = new_rels;
        coords = new_coords;
        score = scre;
        this.references = references;
        keys.clear();
        selected_keys.clear();
        draw_panel.removeAll();
        initializeDrawPanel();
    }
    
    private void initializeButtonPanel(){
        button_panel.setPreferredSize(new Dimension(450,100));
        button_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1;
        c.weightx = 1;
        c.gridx = 0;
        button_panel.add(swap_button, c);
        c.gridx = 1;
        button_panel.add(export_image_button, c);
        c.gridx = 2;
        button_panel.add(save_button, c);
        c.gridx = 3;
        button_panel.add(close_button, c);
    }

    private void initializeInfoPanel() {
        info_panel.setPreferredSize(new Dimension(450,100));
        info_panel.setOpaque(true);
        info_panel.add(scoreLabel);
        info_panel.add(refsArea);
        info();
    }

    private void info() {
        scoreLabel.setText("Puntuació: "+score);
        String s = "Referencies: ";
        for (int i = 0; i < references.length; ++i) {
            s += references[i];
        }
        refsArea.setText(s);
    }

    private void initializeDrawPanel() {
        draw_panel.setPreferredSize(new Dimension(450,200));
        draw_panel.setLayout(null);
        draw_panel.setOpaque(true);
        draw_panel.setBackground(Color.WHITE);


        for (int i = 0; i < rels.size(); ++i) {
            System.out.println(i);
            JLabel label = new JLabel(chars.get(rels.get(i)), JLabel.CENTER);
            keys.put(label, i);
            draw_panel.add(label);
        }
        draw();
    }

    private void draw() {

        for (Map.Entry<JLabel, Integer> entry : keys.entrySet()) {
            JLabel label = entry.getKey();

            // Set Colors
            label.setOpaque(true);
            label.setForeground(Color.black);
            label.setBackground(Color.LIGHT_GRAY);
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            label.setBorder(border);

            // Set Location
            int x = Math.round(coords.get(rels.get(keys.get(label))).y * 50);
            int y = Math.round(coords.get(rels.get(keys.get(label))).x * 20);
            System.out.println(x + " - " + y);
            label.setLocation(x, y);
            
            // Set Size
            label.setSize(50, 20);
        }
    }

    public void setVisiblePublic(Boolean visible) {
        this.setVisible(visible);
    }

    public void setEnabledPublic(Boolean enabled) {
        this.setEnabled(enabled);
    }
}