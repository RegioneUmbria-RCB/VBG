package it.gruppoinit.pal.gp.core.domain.cart;


public class MessaggioErrore {
    
    private String message;
    private int[] rowIndex = new int[0];
    
    public MessaggioErrore(){
	this.message = "";
    }
    
    public MessaggioErrore(String error){
	this.message = error;
    }
    
    public MessaggioErrore(String error, int[] rowIndex){
	
	this.message = error;
	if(rowIndex != null){
	    this.rowIndex = rowIndex;
	}
    }
    
    public MessaggioErrore(String error, Integer[] rowIndex){
	
	this.message = error;
	if(rowIndex != null){
	    this.rowIndex = new int[rowIndex.length];
	    for (int i = 0; i < rowIndex.length; i++) {
		this.rowIndex[i] = rowIndex[i] != null ? rowIndex[i].intValue() : -1;
	    }
	}
    }
    
    public String getMessage() {
    
        return message;
    }
    
    public void setMessage(String message) {
    
        this.message = message;
    }
    
    public int[] getRowIndex() {
    
        return rowIndex;
    }
    
    public void setRowIndex(int[] rowIndex) {
    
        this.rowIndex = rowIndex;
    }
    
    
}
