package classesGeo;

import java.util.ArrayList;

/**
 * Comarca generated by hbm2java
 */
public class Comarca implements java.io.Serializable {

	private static final long serialVersionUID = 3380553519569552746L;
	private String nomC;
	private String provincia;
	private ArrayList<Poblacio> poblacions = new ArrayList<Poblacio>();

	public Comarca() {
	}

	public Comarca(String nomC) {
		this.nomC = nomC;
	}

	public Comarca(String nomC, String provincia, ArrayList<Poblacio> poblacions) {
		this.nomC = nomC;
		this.provincia = provincia;
		this.poblacions = poblacions;
	}

	public String getNomC() {
		return this.nomC;
	}

	public void setNomC(String nomC) {
		this.nomC = nomC;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public ArrayList<Poblacio> getPoblacions() {
		return this.poblacions;
	}

	public void setPoblacions(ArrayList<Poblacio> poblacions) {
		this.poblacions = poblacions;
	}

}
