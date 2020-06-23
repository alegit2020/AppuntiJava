import java.awt.*;
import java.awt.event.*;

public class Poligoni extends Canvas implements ActionListener {
	
	private int[] xs,ys; 
	private Color[] colori;
	
	public Poligoni() {
		xs=new int[7];
		ys=new int[7];
		colori= new Color[7];
		colori[0]=Color.black;
		colori[1]=Color.black;
		colori[2]=new Color(0,255,0);
		colori[3]=new Color(0,192,64);
		colori[4]=new Color(0,128,128);
		colori[5]=new Color(0,64,192);
		colori[6]=new Color(0,0,255);
	}
	
	public void paint(Graphics g) {
		Dimension d=getSize();
		int x1=0;
		int y1=0;
		int x2=(int)d.getWidth();
		int y2=(int)d.getHeight();
		int j;
		int i;
		for (j=3; j<=xs.length; j++) {
			double xcentro= Math.random()*x2;
			double ycentro= Math.random()*y2;
			double phi=Math.random();
			for (i=0;i<j; i++) {
				xs[i]=(int) (Math.cos(2*Math.PI*i/j+phi)*x2/8+xcentro);
				ys[i]=(int) (Math.sin(2*Math.PI*i/j+phi)*y2/8+ycentro);
			}
			g.setColor(colori[j-1]);
			g.fillPolygon(xs,ys,j);
		}
	}
	
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}
		
	public static void main(String[] args) {
		Frame f= new Frame();
		f.setLocation(100,100);
		f.setSize(400,400);
		
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(1);
			}	
		});

		Poligoni p= new Poligoni();
		Button b= new Button("Ridisegna");
		b.addActionListener(p);
		
		f.add(p,"Center");
		f.add(b,"South");
		f.setVisible(true);
	}

}