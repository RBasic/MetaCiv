package civilisation.inspecteur.simulation.environnement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import civilisation.Civilisation;
import civilisation.Communaute;
import civilisation.Configuration;
import civilisation.DefinePath;
import civilisation.Initialiseur;
import civilisation.TurtleGenerator;
import civilisation.inspecteur.animations.*;
import civilisation.inspecteur.simulation.PanelModificationSimulation;
import civilisation.world.Terrain;


public class PanelEnvironnement extends JJPanel{

	PanelModificationSimulation panelPrincipal;
	
	/*Parametres de la carte*/
	ArrayList<ArrayList<PseudoPatch>> carte;
	int largeur = 120;
	int hauteur = 120;
	int taillePseudoPatch = 2;
	GCarte gCarte;
	
	/*Parametres d'affichage*/
	int espacementVerticalCarte = 80;
	int espacementHorizontalCarte = 80;
	
	/*Mode de dessin*/
	int dessin = 0; /*0 = crayon , 1 = pot de peinture, 2 = pinceau*/


	
	public PanelEnvironnement(PanelModificationSimulation panel){
		this.setLayout(null);

		panelPrincipal = panel;
		carte = new ArrayList<ArrayList<PseudoPatch>>();
		redimensionner();
		gCarte = new GCarte(this, espacementHorizontalCarte, espacementVerticalCarte, this);
		gCarte.setLocation(40,40);
		this.add(gCarte);

	}
	
	
	public void redimensionner(){

		Terrain terrainParDefaut = Configuration.terrains.get(0);

		while(carte.size() > hauteur){
			carte.remove(carte.size()-1);
		}
		while(carte.size() < hauteur){
			carte.add(new ArrayList<PseudoPatch>());
		}
		for (int i = 0; i < hauteur; i++){
			while(carte.get(i).size() > largeur){
				carte.get(i).remove(carte.get(i).size()-1);
			}
			while(carte.get(i).size() < largeur){
				carte.get(i).add(new PseudoPatch(terrainParDefaut,carte.get(i).size(),i));
			}
		}
		
	}
	
	/**
	 * Get the patch in the pseudo map at (x,y)
	 */
	public PseudoPatch getPatch(int x , int y) {
		return carte.get(y).get(x);
	}

	public void sauvegarderEnvironnement(String nomDeSauvegarde){
		File environnements = new File(DefinePath.pathToEnvironnements);
	if(!environnements.isDirectory()) environnements.mkdir();
	
	PrintWriter out;
	try {
		
		
		out = new PrintWriter(new FileWriter(DefinePath.pathToEnvironnements+"/"+nomDeSauvegarde+Configuration.getExtension()));
		out.println("Nom : " + nomDeSauvegarde);
		out.println("Largeur : " + largeur);
		out.println("Hauteur : " + hauteur);
		
		for (int i = 0; i < Configuration.civilisations.size(); i++){
			if (gCarte.getStartingPositions().containsKey(Configuration.civilisations.get(i))){
				out.println("Civilisation : " + Configuration.civilisations.get(i).getNom() + "," + gCarte.getStartingPositions().get(Configuration.civilisations.get(i)).getX() + "," + gCarte.getStartingPositions().get(Configuration.civilisations.get(i)).getY());
			}
		}
		
		out.println();
		for (int i = 0;i < Configuration.terrains.size(); i++){
			out.println("Terrain : " + Configuration.terrains.get(i).getNom());
		}
		for (int i = 0; i < this.getCarte().size()  ; i++){
			out.print("Rang : ");
			for (int j = 0; j < this.getCarte().get(i).size()  ; j++){
				Terrain t = this.getCarte().get(i).get(j).getTerrain();
				int k = 0;
				while (!t.equals(Configuration.terrains.get(k))){
					k++;
				}
				out.print(k);
				if (j != this.getCarte().get(i).size()-1){
					out.print(",");	
				}
			}
			out.println();
		}
		
		out.close();
	} catch (IOException ex) {
		// TODO Auto-generated catch block
		ex.printStackTrace();
	} 
	}

	public void generationEnvironnementViaImage(BufferedImage img){
		largeur = img.getWidth();
		hauteur = img.getHeight();
		redimensionner();
		
		for (int i = 0; i < hauteur; i++){
			System.out.println(i/(double)hauteur *100. +"%");
			for (int j = 0; j < largeur; j++){
				carte.get(i).get(j).setTerrain(getTerrainSemblable(new Color(img.getRGB(j, i))));
			}
		}
		
		gCarte.actualiser();
	}
	
	/*
	 * Retourne le terrain le plus proche en fonction de la couleur pass_e en param_tre
	 */
	private Terrain getTerrainSemblable(Color c){
		int variation = 10000;
		int var;
		Terrain t = null;
		for (int i = 0; i < Configuration.terrains.size(); i++){
			var = 0;
			Color ref = Configuration.terrains.get(i).getCouleur();
			var += Math.abs(c.getRed() - ref.getRed());
			var += Math.abs(c.getBlue() - ref.getBlue());
			var += Math.abs(c.getGreen() - ref.getGreen());
			if (var < variation){
				t = Configuration.terrains.get(i);
				variation = var;
			}

		}
		return t;
	}
	
	public void unmarkAllPatch() {
		for (int i = 0; i < this.getCarte().size()  ; i++){
			for (int j = 0; j < this.getCarte().get(i).size()  ; j++){
				getCarte().get(i).get(j).setMark(false);;
			}
		}	
	}
	
	public ArrayList<ArrayList<PseudoPatch>> getCarte() {
		return carte;
	}


	public void setCarte(ArrayList<ArrayList<PseudoPatch>> carte) {
		this.carte = carte;
	}


	public int getLargeur() {
		return largeur;
	}


	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}


	public int getHauteur() {
		return hauteur;
	}


	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}


	public int getTaillePseudoPatch() {
		return taillePseudoPatch;
	}


	public void setTaillePseudoPatch(int taillePseudoPatch) {
		this.taillePseudoPatch = taillePseudoPatch;
	}


	public PanelModificationSimulation getPanelPrincipal() {
		return panelPrincipal;
	}

    public void zoom(int z){
    	taillePseudoPatch += z;
		gCarte.actualiser();

		this.revalidate();
    }


	public int getTypeDessin() {
		return dessin;
	}


	public void setTypeDessin(int dessin) {
		this.dessin = dessin;
	}


	public void loadMap(String env) {
	
		System.out.println("load map");
		
		int x = Integer.parseInt(Initialiseur.getChamp("Largeur", new File(DefinePath.pathToEnvironnements+"/"+env))[0]);
		int y = Integer.parseInt(Initialiseur.getChamp("Hauteur", new File(DefinePath.pathToEnvironnements+"/"+env))[0]);

		System.out.println(x + " " + y);
		
		largeur = x;
		hauteur = y;
		redimensionner();
		
		HashMap<Integer,Terrain> typeTerrains = new HashMap<Integer,Terrain>();
		ArrayList<String[]> listeTerrains = Initialiseur.getListeChamp("Terrain", new File(DefinePath.pathToEnvironnements+"/"+env));
		for (int i = 0; i < listeTerrains.size(); i++){
			//System.out.println("hash "+i+" "+listeTerrains.get(i)[0]+" "+Configuration.getTerrainByName(listeTerrains.get(i)[0]));
			Terrain t = Configuration.getTerrainByName(listeTerrains.get(i)[0]);
			if (t == null) {
				t = new Terrain("missing_patch_type_"+Configuration.terrains.size());
				Configuration.terrains.add(t);
				panelPrincipal.getPanelTerrains().addTerrain(t);
			}
			typeTerrains.put(i,t);
			//System.out.println(typeTerrains.get(i) + typeTerrains.get(i).getCouleur().toString());
		}
	       
		ArrayList<String[]> terrains = Initialiseur.getListeChamp("Rang", new File(DefinePath.pathToEnvironnements+"/"+env));
		for (int i = 0; i < x; i++){
			for (int j = 0; j < y; j++){
				Terrain t = typeTerrains.get(Integer.parseInt(terrains.get(j)[i]));
				this.getPatch(i,j).setTerrain(t);
			}
		}	
		
		ArrayList<String[]> listeCivs = Initialiseur.getListeChamp("Civilisation", new File(DefinePath.pathToEnvironnements+"/"+Configuration.environnementACharger+Configuration.getExtension()));
		for (int i = 0; i < listeCivs.size(); i++){
	    	   
			int u = Integer.parseInt(listeCivs.get(i)[1]);
			int v = Integer.parseInt(listeCivs.get(i)[2]);
			
			gCarte.addStartingPosition(Configuration.getCivilisationByName(listeCivs.get(i)[0]), getPatch(u,v));

		}
	       
		this.revalidate();
	}


	public GCarte getgCarte() {
		return gCarte;
	}


	public void setgCarte(GCarte gCarte) {
		this.gCarte = gCarte;
	}
    
	
    
}
