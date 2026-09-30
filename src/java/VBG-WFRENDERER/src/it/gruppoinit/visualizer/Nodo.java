package it.gruppoinit.visualizer;

public class Nodo {
	
	private String id;
	private String name;
	private String color;
	
	//serve solo per sigepro
	private String label;
	
	public Nodo(String id, String name, String color){
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public Nodo(String id, String name, String color, int label){
		this.id = id;
		this.name = name;
		this.color = color;
		switch (label) {
		case 0:
			this.label = "";
			break;
		case 1:
			this.label = "Esito negativo";
			break;
		case 2:
			this.label = "Esito positivo";
			break;
		default:
			break;
		}
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String toString() {
		return "(id:"+id+",name:"+name+",color:"+color+",esito="+label+")";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	

}
