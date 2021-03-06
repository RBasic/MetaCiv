package civilisation.individu.plan.action;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import turtlekit.kernel.Patch;
import civilisation.Configuration;
import civilisation.ItemPheromone;
import civilisation.amenagement.Amenagement;
import civilisation.amenagement.TypeAmenagement;
import civilisation.group.Group;
import civilisation.individu.Human;
import civilisation.inventaire.Objet;

public class L_CompareNombreObjetsInGroupAmenagement extends LAction{
	
	Objet objet;
	Comparator comp;
	Double d;
	TypeAmenagement am;
	
	@Override
	public Action effectuer(Human h) {
		
		if (nextAction != null) h.getEsprit().getActions().push(nextAction);
		Action a;
		
		int numberObjectsToCompare=0;
		
		boolean doAction = false;
		Amenagement ame=null;
		if (!h.getEsprit().getGroups().isEmpty())
		{
			for (Group grp : h.getEsprit().getGroups().keySet())
			{
				for (Human grpH : grp.getMembers())
					if (grpH.getBuildings().containsKey(am.getNom()))
					{
						ame = grpH.getBuildings().get(am.getNom());
						doAction = true;
						break;
					}
				if (doAction)
					break;
			}
		}
		
		if(ame != null && ame.getInventaire().getListeObjets().get(objet.getNom())!= null)
			numberObjectsToCompare= ame.getInventaire().getListeObjets().get(objet.getNom()).get(1);
		
		if (comp.compare((double) numberObjectsToCompare, d)) {
			if(listeActions.size() > 0){
				a = listeActions.get(0).effectuer(h);
			}else{
				a = new A_DoNothing().effectuer(h);
			}
		} else {
			if(listeActions.size() > 1){
				a = listeActions.get(1).effectuer(h);
			}else{
				a = new A_DoNothing().effectuer(h);
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
		return super.getInfo() + " Compare the number of a specific kind of object stored in a group facility.<html>";
	}
	
	@Override
	public void parametrerOption(OptionsActions option){
		super.parametrerOption(option);

		if (option.getParametres().get(0).getClass().equals(Objet.class)){
			objet = (Objet) option.getParametres().get(0);
		} else
		if (option.getParametres().get(0).getClass().equals(Comparator.class)){
			comp = (Comparator) option.getParametres().get(0);
		} else
		if (option.getParametres().get(0).getClass().equals(Double.class)){
			d = (Double) option.getParametres().get(0);
		} else
		if (option.getParametres().get(0).getClass() == TypeAmenagement.class)
			am = (TypeAmenagement) option.getParametres().get(0);
		
		
	}
	
	@Override
	public ArrayList<String[]> getSchemaParametres(){
		
		if (schemaParametres == null){
			schemaParametres = new ArrayList<String[]>();
			
			
			String[] attr = {"**Objet**" , "objectToCompare"};
			String[] comp = {"**Comparator**" , "comparator"};		
			String[] val = {"**Double**" , "n", "-100.0" , "100.0" , "1.0", "100"};
			String[] cog = {"**Amenagement**" , "amenagement du groupe"};
			
			schemaParametres.add(cog);
			schemaParametres.add(attr);
			schemaParametres.add(comp);
			schemaParametres.add(val);

		}
		return schemaParametres;	
	}
	
	public boolean internActionsAreLinked() {
		return false;
	}
}
