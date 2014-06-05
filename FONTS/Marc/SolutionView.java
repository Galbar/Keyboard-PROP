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

/* TO DO
    Al utilitzar singleton no puc fer el dibuix quan es construeix la classe. Haig de fer un metode apart
    i cridar-lo quan creo la classe i quan em torno a fer visible. -> setVisible(true);
    Aquest metode ha d'elminiar (buidar) els atributs keys i selected_keys, i tambe chars, rels i coords,
    i tornar a crear-los tots i cridar la funció draw.
*/

/*

*/	

public class SolutionView extends JFrame {

    //private DrawPanel draw_panel = new DrawPanel();
    private JPanel draw_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JButton save_button = new JButton("Guardar");
    private JButton close_button = new JButton("Tancar");
    private JButton swap_button = new JButton("Swap");
    private JButton export_image_button = new JButton("Exportar Imatge");

    private Vector<String> chars;
    private Vector<Integer> rels;
    private Vector<Position> coords; // temp
    private HashMap<JLabel, Integer> keys;
    private HashMap<JLabel, Integer> selected_keys; // Potser no sera vector

    private static SolutionView instance;

    public static SolutionView getInstance(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords) {
        if(instance == null) instance = new SolutionView(new_chars, new_rels, new_coords);
        else {
            instance.reinitiate(new_chars, new_rels, new_coords);
        }
        return instance;
    }

    public static SolutionView getInstance() {
        if(instance == null) System.out.println("ERROOR... Esper-ho i Desitjo que mai vegis aquesta linia...");
        return instance;
    }

    private SolutionView(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords) {
    	chars = new_chars;
    	rels = new_rels;
    	coords = new_coords;
        keys = new HashMap<JLabel, Integer>();
        selected_keys = new HashMap<JLabel, Integer>();
        initialize();
    }

    private void initializeFrame() {
        this.setMinimumSize(new Dimension(500, 300));
        this.setPreferredSize(this.getMinimumSize());
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        
        contentPane.add(draw_panel);
        contentPane.add(button_panel);
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
                    setVisible(false);
                    dispose();
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
                    InterfaceController.swap(ids[0], ids[1]); // NOT DONE YET!
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
        initializeButtonPanel();
        initializeDrawPanel();
        setListeners();
        this.pack();
        this.setVisible(true);
        this.setEnabled(true);
    }

    public void reinitiate(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords) {
        chars = new_chars;
        rels = new_rels;
        coords = new_coords;
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
            int x = Math.round(coords.get(rels.get(keys.get(label))).x);
            int y = Math.round(coords.get(rels.get(keys.get(label))).y);
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


	//-----------------
        /*
	    class DrawPanel extends JPanel {
            private BufferedImage image = new BufferedImage(450, 200, BufferedImage.TYPE_3BYTE_BGR);
            Vector<JLabel> keys = new Vector<JLabel>();

	        DrawPanel() {
	            setPreferredSize(new Dimension(450,200));
                setLayout(null);
                setOpaque(true);
                setBackground(Color.WHITE);
                
	        }

	        @Override
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            drawKeys(g);
	        }

	        private void drawKeys(Graphics g) {
	        	for (int i = 0; i < rels.size(); ++i) {
                    System.out.println(i);
	        		int x = Math.round(coords.get(rels.get(i)).x);
	        		int y = Math.round(coords.get(rels.get(i)).y);
                    //g.setColor(Color.BLACK);
	        		//g.fillRect(x, y, 50,20); // 100 key size, may change
                    //g.setColor(Color.RED);
	        		//g.drawString(chars.get(rels.get(i)), x+10, y+10);
                    
                    JLabel label = new JLabel(chars.get(rels.get(i)), JLabel.CENTER);
                    label.setOpaque(true);
                    label.setForeground(Color.black);
                    label.setBackground(Color.darkGray);
                    keys.add(label);
                    this.add(label);
                    label.setLocation(x, y);
                    label.setSize(50, 20);
	        	}
                setListeners();
	        }

            public BufferedImage getImage() {
                return image;
            }

            private void setListeners() {
                System.out.println("Mouse ininin");
                for (int i = 0; i < keys.size(); ++i) {
                     System.out.println("Mforforforforlick");

                    keys.get(i).addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent event){
                            //if (event.isPopupTrigger()) {
                                // code
                                //if (swapping) {
                                    //keys.get(i).setBackground(Color.red);

                                //}
                            //}
                            //setBackground(Color.red);
                            System.out.println("Mouse Click");
                        }

                        // @Override
                        //public void mouseEntered(MouseEvent event) {
                            //Font font_original = keys.get(i).getFont();
                            //Map attributes = font_original.getAttributes();
                            //attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            //keys.get(i).setFont(font_original.deriveFont(attributes));;
                        //}

                        //@Override
                        //public void mouseExited(MouseEvent event) {
                            //Font font_original = keys.get(i).getFont();
                            //Map attributes = font_original.getAttributes();
                            //attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_OFF);
                            //keys.get(i).setFont(font_original.deriveFont(attributes));;
                        //}
                    });
                }
            }


	    }
        */

	public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Vector<String> new_chars = new Vector<String>(0);
            	Vector<Integer> new_rels = new Vector<Integer>(0);
            	Vector<Position> new_coords = new Vector<Position>(0);

                new_chars.addElement("a");
                new_chars.addElement("b");
                new_chars.addElement("c");

                new_rels.addElement(0);
                new_rels.addElement(1);
                new_rels.addElement(2);

                Position p1 = new Position(10.0f, 10.0f);
                Position p2 = new Position(80.0f, 10.0f);
                Position p3 = new Position(70.0f, 50.0f);
                new_coords.addElement(p1);
                new_coords.addElement(p2);
                new_coords.addElement(p3);

                for (int i = 0; i < new_coords.size(); ++i) {
                    System.out.println(new_coords.get(i).x);
                    System.out.println(new_coords.get(i).y);
                    System.out.println("---");
                }
                System.out.println("Start");

                //SolutionView sI = new SolutionView.getInstance(new_chars, new_rels, new_coords);
                SolutionView sI = new SolutionView(new_chars, new_rels, new_coords);
            }
        });
    }
}