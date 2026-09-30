package it.gruppoinit.visualizer;

public class Ramo {
	
	private String origine;
	private String destinazione;
	private String label;
	
	public Ramo(String origine, String destinazione, String label) {
		this.origine = origine;
		this.destinazione = destinazione;
		this.label = label;
	}
	
	public String getOrigine() {
		return origine;
	}
	
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	
	public String getDestinazione() {
		return destinazione;
	}
	
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toString() {
		return "(origine:"+origine+",destinazione:"+destinazione+",label:"+label+")";
	}

}
