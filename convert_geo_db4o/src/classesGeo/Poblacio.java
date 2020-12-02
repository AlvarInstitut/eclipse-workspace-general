package classesGeo;

import java.util.ArrayList;


public class Poblacio implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private int codM;
	private Comarca comarca;
	private String nom;
	private Integer habitants;
	private Float extensio;
	private Integer altura;
	private String longitud;
	private String latitud;
	private Character llengua;
	private ArrayList<Institut> instituts = new ArrayList<Institut>();

	public Poblacio() {
	}

	public Poblacio(int codM, String nom) {
		this.codM = codM;
		this.nom = nom;
	}

	public Poblacio(int codM, Comarca comarca, String nom, Integer habitants, Float extensio, Integer altura,
			String longitud, String latitud, Character llengua, ArrayList<Institut> instituts) {
		this.codM = codM;
		this.comarca = comarca;
		this.nom = nom;
		this.habitants = habitants;
		this.extensio = extensio;
		this.altura = altura;
		this.longitud = longitud;
		this.latitud = latitud;
		this.llengua = llengua;
		this.instituts = instituts;
	}

	public int getCodM() {
		return this.codM;
	}

	public void setCodM(int codM) {
		this.codM = codM;
	}

	public Comarca getComarca() {
		return this.comarca;
	}

	public void setComarca(Comarca comarca) {
		this.comarca = comarca;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getHabitants() {
		return this.habitants;
	}

	public void setHabitants(Integer habitants) {
		this.habitants = habitants;
	}

	public Float getExtensio() {
		return this.extensio;
	}

	public void setExtensio(Float extensio) {
		this.extensio = extensio;
	}

	public Integer getAltura() {
		return this.altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public String getLongitud() {
		return this.longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getLatitud() {
		return this.latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public Character getLlengua() {
		return this.llengua;
	}

	public void setLlengua(Character llengua) {
		this.llengua = llengua;
	}

	public ArrayList<Institut> getInstituts() {
		return this.instituts;
	}

	public void setInstituts(ArrayList<Institut> instituts) {
		this.instituts = instituts;
	}

}
