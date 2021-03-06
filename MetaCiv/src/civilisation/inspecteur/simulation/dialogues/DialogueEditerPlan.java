package civilisation.inspecteur.simulation.dialogues;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import I18n.I18nList;

import civilisation.inspecteur.simulation.GPlan;

public class DialogueEditerPlan extends JDialog implements ActionListener, PropertyChangeListener{
	
	JTextField nom;
	GPlan gPlan;
    JOptionPane optionPane;
    JCheckBox isAuto, isBirth;
    JLabel labcolor;
    JColorChooser choixCouleur;

	public DialogueEditerPlan(Frame f , boolean modal, GPlan gPlan){
		super(f,modal);
		this.gPlan = gPlan;

		

		nom = new JTextField(20);
		nom.setText(gPlan.getPlan().getNom());

		isAuto = new JCheckBox(I18nList.CheckLang("Auto-Plan"));
		isAuto.setSelected(gPlan.getPlan().getIsSelfPlan());
		isAuto.setToolTipText(I18nList.CheckLang("Every agents will run this plan every tick if this box is checked. You could use this features to create automatic cognitons transmissions,") +
				I18nList.CheckLang(" or change attributes (need for food...)"));
		
		isBirth = new JCheckBox(I18nList.CheckLang("Birth-Plan"));
		isBirth.setSelected(gPlan.getPlan().getIsBirthPlan());
		isBirth.setToolTipText(I18nList.CheckLang("Every agents will run this plan at birth."));
		
		this.setTitle(I18nList.CheckLang("Edite plan"));

		labcolor = new JLabel(I18nList.CheckLang("Plan custom color : "));
		choixCouleur = new JColorChooser();
		
		/*Proviens du tutorial Java Sun*/
	    Object[] array = {nom , isAuto , isBirth, choixCouleur};
	       
	    //Create an array specifying the number of dialog buttons
	    //and their text.
	    Object[] options = {"OK" , "Cancel"};
	 
	    //Create the JOptionPane
	    optionPane = new JOptionPane(array,
	                                    JOptionPane.QUESTION_MESSAGE,
	                                    JOptionPane.YES_NO_OPTION,
	                                    null,
	                                    options,
	                                    options[0]); 
	    //Make this dialog display it.
	    setContentPane(optionPane);
	        
	    optionPane.addPropertyChangeListener(this);
	        
		ImageIcon icone = new ImageIcon(System.getProperty("user.dir")+"/civilisation/graphismes/LogoMedium.png");
		optionPane.setIcon(icone);
		this.pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		System.out.println(optionPane.getValue());
		if (isVisible() && (optionPane.getValue().equals("OK") || optionPane.getValue().equals("Cancel"))){
			if (optionPane.getValue().equals("OK")){
				gPlan.getPlan().setNom(nom.getText());
				gPlan.getPlan().setIsSelfPlan(isAuto.isSelected());
				gPlan.getPlan().setIsBirthPlan(isBirth.isSelected());
				gPlan.setCouleur(choixCouleur.getColor());
			}		
	        setVisible(false);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
