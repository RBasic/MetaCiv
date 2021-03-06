package civilisation.individu.plan.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import civilisation.Configuration;
import civilisation.amenagement.Amenagement;
import civilisation.amenagement.TypeAmenagement;
import civilisation.individu.Human;
import civilisation.inventaire.Objet;

public class L_IsPrivateFacilityHere extends LAction{

	TypeAmenagement objet;

	
	@Override
	public Action effectuer(Human h) {
		if (nextAction != null) h.getEsprit().getActions().push(nextAction);
		Action a;
		
		a = listeActions.get(1).effectuer(h);
		ArrayList<Amenagement> facilities = h.getFacilitiesOfTypeHere(objet);
		for(Amenagement f : facilities){
			if(f.getPossesseur().hashCode() == h.hashCode()){
				a = listeActions.get(0).effectuer(h);
			}
		}
	
		return a;
		
	}

	@Override
	public ImageIcon getIcon(){
		return Configuration.getIcon("processor.png");
	}
	
	@Override
	public int getNumberActionSlot(){
		return 2;
	}
	
	@Override
	public String getInfo() {
		return super.getInfo() + " Play first action if the patch own an amenagement,<br> the second otherwise.<html>";
	}
	
	@Override
	public void parametrerOption(OptionsActions option){
		super.parametrerOption(option);
		
		if (option.getParametres().get(0).getClass() == TypeAmenagement.class) {
			objet = (TypeAmenagement) option.getParametres().get(0);
			System.out.println(objet);
		}
	}
	
	@Override
	public ArrayList<String[]> getSchemaParametres(){
		
		if (schemaParametres == null){
			schemaParametres = new ArrayList<String[]>();
			
			
			String[] cog = {"**Amenagement**" , "facility"};
			
			schemaParametres.add(cog);


		}
		return schemaParametres;	
	}
	
	public boolean internActionsAreLinked() {
		return false;
	}
}
