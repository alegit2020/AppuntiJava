import java.applet.*;
import java.awt.*;
import javax.swing.*;

public class AppletRubrica extends JApplet {
	RubricaTelefonica rt;

	public void init() {
		rt= new RubricaSemplice();
		GUIRubrica gr= new GUIRubrica(rt);
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(gr);
		int i=1;
		String nome, numero;
		while ((nome=getParameter("nome"+i))!=null) {
			numero=getParameter("numero"+i);
			if (numero!=null)
				rt.inserisci(new VoceRubrica(nome,numero));
			i++;
		}
	}
	
	public void start() {
	}
	
	public void stop() {
	}
	
	public void destroy() {
	}
}