package it.gruppoinit.visualizer;

import org.jgraph.graph.DefaultGraphCell;

public class VertexWrapper extends DefaultGraphCell {

	private static final long serialVersionUID = 1L;
	private String id;
	
	public VertexWrapper(String id, String name){
		super(name);
		this.id = id;
	}
		
	public String getId() {
		return id;
	}

	
}
