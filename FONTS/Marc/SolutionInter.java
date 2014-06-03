import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import java.lang.Math.*;


/* Per dibuixar el teclat necessito:
	- Els dos arrays pos i chars.. (noms ?)
	- Noms de les tecles
	- Coordenades de cada posició
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
        this.setMinimumSize(new Dimension(300, 200));
        this.setPreferredSize(this.getMinimumSize());
        this.setResizable(true);
        // Posicion y operaciones por defecto
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) this.getContentPane();
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
    
    /*private void setListeners(){
        alpha_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                Alphabet A = Alphabet.getInstance();
                setVisible(false);
                setEnabled(false);
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
                NewKeyboard nk = NewKeyboard.getInstance();
                setVisible(false);
                setEnabled(false);
            }
        });
    }*/
    
    private void initialize(){
        initializeFrame();
        initializeButtonPanel();
        //setListeners();
        //setEnabled(true);
        //setVisible(true);
        this.pack();
        this.setVisible(true);
        this.setEnabled(true);
    }
    
    private void initializeButtonPanel(){
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

	        DrawPanel() {
	            // set a preferred size for the custom panel.
	            setPreferredSize(new Dimension(420,420));
	        }

	        @Override
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);

	            drawKeys(g);
	            //g.drawString("BLAH", 20, 20);
	            //g.drawRect(200, 200, 200, 200);
	            //g.fillRect(50,50,100,100);
	            System.out.println("wola!");
	        }

	        private void drawKeys(Graphics g) {
	        	for (int i = 0; i < rels.size(); ++i) {
	        		int x = Math.round(coords.get(rels.get(i)).x);
	        		int y = Math.round(coords.get(rels.get(i)).y);
	        		g.fillRect(x, y, 100,100); // 100 key size, may change
	        		g.drawString(chars.get(rels.get(i)), x+10, y+10);
	        	}
	        }
	    }    

	public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            	Vector<String> new_chars = new Vector<String>(0);
            	Vector<Integer> new_rels = new Vector<Integer>(0);
            	Vector<Position> new_coords = new Vector<Position>(0);
                //SolutionInter sI = new SolutionInter.getInstance(new_chars, new_rels, new_coords);
                SolutionInter sI = new SolutionInter(new_chars, new_rels, new_coords);
            }
        });
    }
}