package classesGeoHib;
// Generated 19/02/2018 10:04:50 by Hibernate Tools 5.2.5.Final

/**
 * Institut generated by hbm2java
 */
public class Institut implements java.io.Serializable {

	private String codi;
	private Poblacio poblacio;
	private String nom;
	private String adreca;
	private String numero;
	private Integer codpostal;

	public Institut() {
	}

	public Institut(String codi) {
		this.codi = codi;
	}

	public Institut(String codi, Poblacio poblacio, String nom, String adreca, String numero, Integer codpostal) {
		this.codi = codi;
		this.poblacio = poblacio;
		this.nom = nom;
		this.adreca = adreca;
		this.numero = numero;
		this.codpostal = codpostal;
	}

	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public Poblacio getPoblacio() {
		return this.poblacio;
	}

	public void setPoblacio(Poblacio poblacio) {
		this.poblacio = poblacio;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdreca() {
		return this.adreca;
	}

	public void setAdreca(String adreca) {
		this.adreca = adreca;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getCodpostal() {
		return this.codpostal;
	}

	public void setCodpostal(Integer codpostal) {
		this.codpostal = codpostal;
	}

}