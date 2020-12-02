package TravasInformacio;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PortarAKeme {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pantalla finestra = new Pantalla();
        finestra.iniciar();
	}
	

}

class Pantalla extends JFrame implements ActionListener {
	
	public void iniciar() {
        getContentPane().setLayout(new BorderLayout());
        setSize(100,100);
        /*JPanel p_prin = new JPanel();
        p_prin.setLayout(new BoxLayout(p_prin, BoxLayout.Y_AXIS));
        // contenidor per als elements
        JPanel panell1 = new JPanel(new GridLayout(0,2));
        panell1.add(etNom);
        qNom.setEditable(false);
        panell1.add(qNom);
        panell1.add(etDesn);
        qDesn.setEditable(false);
        panell1.add(qDesn);
        panell1.add(etDesnAcum);
        qDesnAcum.setEditable(false);
        panell1.add(qDesnAcum);
        panell1.add(etPunts);

        JPanel panell2 = new JPanel(new GridLayout(0,1));
        punts.setEnabled(false);
        JScrollPane scroll = new JScrollPane(punts);
        panell2.add(scroll, null);

        JPanel panell5 = new JPanel(new FlowLayout());
        panell5.add(primer);
        panell5.add(anterior);
        panell5.add(seguent);
        panell5.add(ultim);
        
        getContentPane().add(p_prin);
        p_prin.add(panell1);
        p_prin.add(panell2);
        p_prin.add(panell5); */
        
        setVisible(true);
        //pack();
       /* primer.addActionListener(this);
        anterior.addActionListener(this);
        seguent.addActionListener(this);
        ultim.addActionListener(this);

        inicialitzar();
        VisRuta();*/
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
