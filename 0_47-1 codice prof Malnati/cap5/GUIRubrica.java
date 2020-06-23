import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class GUIRubrica extends JTabbedPane implements ActionListener, ListSelectionListener {
	private RubricaTelefonica rt;
	private JTextField testo, nome, numero, nome2, numero2;
	private JList elenco;
	private JButton trovaNome, trovaNumero, inserisci, modifica, cancella;
	private VoceRubrica[] voci;
	private VoceRubrica voceSelezionata;
	
	public GUIRubrica(RubricaTelefonica rt) {
		this.rt=rt;
		voci=new VoceRubrica[0];
		voceSelezionata=null;

		//Pannello per la ricerca
		JPanel p1= new JPanel();		
		p1.setLayout(new GridBagLayout());
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.weightx=100;
		gbc.weighty=100;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=4;
		gbc.gridheight=1;
		testo=new JTextField(20);
		p1.add(testo,gbc);
		
		trovaNome=new JButton("Trova nome");
		trovaNome.addActionListener(this);
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		p1.add(trovaNome,gbc);
		
		trovaNumero= new JButton("Trova numero");
		trovaNumero.addActionListener(this);
		gbc.gridx=2;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		p1.add(trovaNumero,gbc);
		
		elenco=new JList();
		elenco.addListSelectionListener(this);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=4;
		gbc.gridheight=4;
		JScrollPane jsp= new JScrollPane(elenco);
		jsp.setPreferredSize(new Dimension(300,100));
		p1.add(jsp,gbc);

		addTab("Ricerca",p1);

		//Pannello per l'inserimento
		Box b1= Box.createVerticalBox();			
		Box b2= Box.createHorizontalBox();
		b2.add(Box.createHorizontalGlue());
		b2.add(new JLabel("Nome:"));
		b2.add(Box.createHorizontalStrut(5));
		nome=new JTextField(20);
		nome.setMaximumSize(new Dimension(220,20));
		b2.add(nome);
		b2.add(Box.createHorizontalStrut(10));
		b1.add(b2);
		b1.add(Box.createVerticalStrut(5));
		
		b2=Box.createHorizontalBox();
		b2.add(Box.createHorizontalGlue());
		b2.add(new JLabel("Numero:"));
		b2.add(Box.createHorizontalStrut(5));
		numero=new JTextField(20);
		numero.setMaximumSize(new Dimension(220,20));
		b2.add(numero);
		b2.add(Box.createHorizontalStrut(10));
		b1.add(b2);
		b1.add(Box.createVerticalStrut(5));
		
		b1.add(Box.createVerticalGlue());

		b2=Box.createHorizontalBox();
		b2.add(Box.createHorizontalGlue());
		inserisci=new JButton("Inserisci");
		inserisci.addActionListener(this);
		b2.add(inserisci);
		b2.add(Box.createHorizontalGlue());
		b1.add(b2);
		
		b1.add(Box.createVerticalStrut(10));
		
		addTab("Inserimento",b1);
		
		//Pannello per la modifica		
		JPanel p3= new JPanel();
		
		p3.add(new JLabel("   Nome:"));
		nome2=new JTextField(20);
		p3.add(nome2);
		
		p3.add(new JLabel("Numero:"));
		numero2=new JTextField(20);
		p3.add(numero2);

		modifica=new JButton("Modifica");
		modifica.addActionListener(this);
		p3.add(modifica);
		cancella=new JButton("Elimina");
		cancella.addActionListener(this);
		p3.add(cancella);
		
		addTab("Modifica",p3);
		setEnabledAt(2,false);
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object o=ae.getSource();
		if (o instanceof JButton) {
			if (o==trovaNome) {
				voci =rt.trovaNome(testo.getText());
				elenco.setListData(voci);
			}
			else if (o==trovaNumero) {
				voci =rt.trovaNumero(testo.getText());
				elenco.setListData(voci);
			}
			else if (o==inserisci) {
				VoceRubrica v=new VoceRubrica(nome.getText(),numero.getText());
				rt.inserisci(v);
			}
			else if (o==modifica) {
				VoceRubrica v=new VoceRubrica(nome2.getText(),numero2.getText());
				try {
					rt.modifica(voceSelezionata,v);
				}
				catch (Exception e) {}
			}
			else if (o==cancella) {
				try {
					rt.cancella(voceSelezionata);
				}
				catch (Exception e) {}
			}
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		int i=elenco.getSelectedIndex();
		if (i<0 || i>= voci.length) voceSelezionata=null;
		else voceSelezionata= voci[i];
		if (voceSelezionata!=null) {
			nome2.setText(voceSelezionata.getNome());
			numero2.setText(voceSelezionata.getNumero());
			setEnabledAt(2,true);
		} else {
			nome2.setText("");
			numero2.setText("");
			setEnabledAt(2,false);
		}
			
	}
	
	public static void main(String[] args) {
		GUIRubrica gui= new GUIRubrica(new RubricaSemplice());
		JFrame f= new JFrame("Rubrica Telefonica");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.setSize(320,240);
		f.setResizable(false);
		f.getContentPane().add(gui,"Center");
		f.setVisible(true);
	}
	
}

