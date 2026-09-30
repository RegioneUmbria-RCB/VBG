package it.gruppoinit.pal.gp.core.domain.cart;


public class FileUpdateInfo extends FileInfo {

    public enum FileUpdateOperation {
	NONE, INSERT, UPDATE, DELETE;

	public String value() {

	    return name();
	}

	public static FileUpdateOperation fromValue(String v) {

	    return valueOf(v);
	}
    }
    
    private String dbOperation = FileUpdateOperation.NONE.value();
    private String idSemantico = "";
    private int rowIndex = -1;

    public FileUpdateInfo(){
	super();
    }
    
    public FileUpdateInfo(FileInfo fileInfo){
	super();
	if (null != fileInfo) {
	    this.setIdOggetto(fileInfo.getIdOggetto());
	    this.setNomeFile(fileInfo.getNomeFile());
	}
    }

    /**
     * @return the dbOperation
     */
    public String getDbOperation() {
    
        return dbOperation;
    }

    
    /**
     * @param dbOperation the dbOperation to set
     */
    public void setDbOperation(FileUpdateOperation dbOperation) {
    
        this.dbOperation = dbOperation.value();
    }

    /**
     * @param dbOperation the dbOperation to set
     */
    public void setDbOperation(String dbOperation) {
    
        this.dbOperation = dbOperation;
    }
    
    /**
     * @return the idSemantico
     */
    public String getIdSemantico() {
    
        return idSemantico;
    }

    
    /**
     * @param idSemantico the idSemantico to set
     */
    public void setIdSemantico(String idSemantico) {
    
        this.idSemantico = idSemantico;
    }

    
    /**
     * @return the rowIndex
     */
    public int getRowIndex() {
    
        return rowIndex;
    }

    
    /**
     * @param rowIndex the rowIndex to set
     */
    public void setRowIndex(int rowIndex) {
    
        this.rowIndex = rowIndex;
    }
}
