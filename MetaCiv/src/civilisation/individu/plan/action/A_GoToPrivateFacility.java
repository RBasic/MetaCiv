package civilisation.individu.plan.action;

import java.util.ArrayList;

import turtlekit.kernel.Patch;
import civilisation.amenagement.Amenagement;
import civilisation.amenagement.TypeAmenagement;
import civilisation.individu.Human;

/**
 * 
 * @author DTEAM
 * 
 * L'agent se dirige vers l'am�nagement qu'il poss�de
 * 
 * @param Created Amenagement l'am�nagement cible
 *
 */

public class A_GoToPrivateFacility extends Action{

	TypeAmenagement amenagement;
	public Action effectuer(Human h)
	{	

		if(h.targetMove == null){
				if(h.getPrivateFacilities().containsKey(amenagement.getNom())){
					h.targetMove = h.getPrivateFacilities().get(amenagement.getNom());
				}
			
		}
		if(h.targetMove != null)
		{
		 	if(h.targetMove.xcor() == h.xcor() && h.targetMove.ycor() == h.ycor())
		 	{
		 		h.targetMove = null;
		 		return nextAction;
		 	}
		 	else
		 	{
		 		h.moveTowards(h.targetMove);
		 		h.targetMove = null;
		 	}
		}
		return nextAction;
	}
	
	@Override
	public void parametrerOption(OptionsActions option){
		super.parametrerOption(option);

		if (option.getParametres().get(0).getClass() == TypeAmenagement.class) {
			amenagement = (TypeAmenagement) option.getParametres().get(0);
		//	System.out.println(objet);
		}

	}
	

	/**
	 * Retourne la structure des param_tres.
	 * Permet de d_terminer la pr_sentation de la fen_tre de r_glages.
	 */
	@Override
	public ArrayList<String[]> getSchemaParametres(){
		
		if (schemaParametres == null){
			schemaParametres = new ArrayList<String[]>();
			String[] attrName = {"**Amenagement**" , "Facility"};

			schemaParametres.add(attrName);

		}
		return schemaParametres;	
	}
}
