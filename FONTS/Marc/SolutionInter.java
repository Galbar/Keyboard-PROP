import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import java.lang.Math.*;
import java.awt.Color;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

// <testing>
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
// </testing>

/* Per dibuixar el teclat necessito:
	- Els dos arrays pos i chars.. (noms ?)
	- Noms de les tecles
	- Coordenades de cada posició
*/

/* TO DO
    - Recalculate DONE
    - Save        Guardar Imatge fet, Falta el explorer que no compila. Necessito que el explorer em retorni l'string del path triat i despres JO crido al InterfaceController.
    - Detectar click Key -> Swap: Primer s'apreta el boto, apareix text indicatiu  (label) i despres es cliquen les dos tecles.
*/

/*
	Array [String] Chars, array amb els caracters de cada tecla. Accedir per index.
	Array [] Coords, array amb les coordenades de cada tecla. Accedir per index.
	Array [int] Rel, array que relacion l'array Chars amb l'array Coords: Rel[i] = j -> Chars[i] va amb Coords[j] (O al reves ¿?)
*/	

public class SolutionInter extends JFrame {

    //private Painting painting = new Painting();
    private DrawPanel draw_panel = new DrawPanel();
    private JPanel button_panel = new JPanel();
    private JButton save_button = new JButton("Guardar");//("Manage alphabet");
    private JButton close_button = new JButton("Tancar");//("Manage text");
    private JButton recalculate_button = new JButton("Recalcular");//("Create keyboard");
    //private JButton load_button = new JButton("Carregar teclat");//("Load keyboard");

    protected Vector<String> chars;
    protected Vector<Integer> rels;
    protected Vector<Position> coords; // temp
   	// protected Matriu?? Vector de Pairs?? coords;

    //private static SolutionInter instance;
    
    private SolutionInter(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords) {
    	chars = new_chars;
    	rels = new_rels;
    	coords = new_coords;
        initialize();
    }

    /*
    public static SolutionInter getInstance(Vector<String> new_chars, Vector<Integer> new_rels, Vector<Position> new_coords) {
        if(instance == null) instance = new SolutionInter(new_chars, new_rels, new_coords);
        instance.setEnabled(true);
        instance.setVisible(true);
        return instance;
    }
    */

    private void initializeFrame() {
        // Tamanyo
        this.setMinimumSize(new Dimension(500, 300));
        this.setPreferredSize(this.getMinimumSize());
        this.setResizable(true);
        // Posicion y operaciones por defecto
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        //contentPane.setOpaque(false);
        contentPane.add(draw_panel);
        contentPane.add(button_panel);
    }
    
    /*public void setVisible(boolean visible){
        //alpha_button.setVisible(visible);
        //text_button.setVisible(visible);
        //create_button.setVisible(visible);
        //load_button.setVisible(visible);
        this.pack();
        this.setVisible(visible);
    }*/
    
    /*public void setEnabled(boolean enabled){
        this.setEnabled(enabled);
        //alpha_button.setEnabled(enabled);
        //text_button.setEnabled(enabled);
        //create_button.setEnabled(enabled);
        //load_button.setEnabled(enabled);
    }*/
    
    private void setListeners(){
        save_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.out.println("Guardar Solució");
                //Explorer e = new Explorer();

                BufferedImage image = ScreenImage.createImage(draw_panel);
                InterfaceController.saveKeyboard("aaa", image);
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
        recalculate_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                System.out.println("Recalcular Solucio");
                Frame frame = new JFrame("Recalcular Solucio");
                 int result = JOptionPane.showConfirmDialog(
                frame,
                "Segur que vols recalcular la solució?\nSi no s'ha guardat es perdrà!",
                "Recalcular Solució?",
                JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    InterfaceController.recalculate();
                    setVisible(false);
                    dispose();
                }
            }
        });
    }
    
    private void initialize(){
        initializeFrame();
        initializeButtonPanel();
        setListeners();
        //setEnabled(true);
        //setVisible(true);
        this.pack();
        this.setVisible(true);
        this.setEnabled(true);
    }
    
    private void initializeButtonPanel(){
        button_panel.setPreferredSize(new Dimension(450,100));
        //button_panel.setOpaque(true);
        //setBackground(Color.BLUE);

        button_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=1;
        c.weightx=1;
        c.gridx=0;
        button_panel.add(save_button,c);
        c.gridx=1;
        button_panel.add(close_button,c);
        c.gridx=2;
        button_panel.add(recalculate_button,c);
    }


	//-----------------

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
                    /*g.setColor(Color.BLACK);
	        		g.fillRect(x, y, 50,20); // 100 key size, may change
                    g.setColor(Color.RED);
	        		g.drawString(chars.get(rels.get(i)), x+10, y+10);
                    */
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

            private void setListeners(){
                                            System.out.println("Mouse ininin");

                for (int i = 0; i < keys.size(); ++i) {
                                                System.out.println("Mforforforforlick");

                    keys.get(i).addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent event){
                            //if (event.isPopupTrigger()) {
                                // code
                                /*if (swapping) {
                                    keys.get(i).setBackground(Color.red);

                                }*/
                            //}
                            //setBackground(Color.red);
                            System.out.println("Mouse Click");
                        }

                        /* @Override
                        public void mouseEntered(MouseEvent event) {
                            Font font_original = keys.get(i).getFont();
                            Map attributes = font_original.getAttributes();
                            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                            keys.get(i).setFont(font_original.deriveFont(attributes));;
                        }

                        @Override
                        public void mouseExited(MouseEvent event) {
                            Font font_original = keys.get(i).getFont();
                            Map attributes = font_original.getAttributes();
                            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_OFF);
                            keys.get(i).setFont(font_original.deriveFont(attributes));;
                        }*/
                    });
                }
            }


	    }    

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

                //SolutionInter sI = new SolutionInter.getInstance(new_chars, new_rels, new_coords);
                SolutionInter sI = new SolutionInter(new_chars, new_rels, new_coords);
            }
        });
    }
}