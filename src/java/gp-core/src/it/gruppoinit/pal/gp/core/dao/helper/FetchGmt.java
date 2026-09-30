package it.gruppoinit.pal.gp.core.dao.helper;


public class FetchGmt {
    String name;
    int posStart;
    int posEnd;
    String testo;
    String testoconintestazioni;
    String rigaTestata;
    String OtherWhereClause;
    
    public String getName() {
    
        return name;
    }
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    public int getPosStart() {
    
        return posStart;
    }
    
    public void setPosStart(int posStart) {
    
        this.posStart = posStart;
    }
    
    public int getPosEnd() {
    
        return posEnd;
    }
    
    public void setPosEnd(int posEnd) {
    
        this.posEnd = posEnd;
    }
    
    public String getTesto() {
    
        return testo;
    }
    
    public void setTesto(String testo) {
    
        this.testo = testo;
    }
    
    public String getTestoconintestazioni() {
    
        return testoconintestazioni;
    }
    
    public void setTestoconintestazioni(String testoconintestazioni) {
    
        this.testoconintestazioni = testoconintestazioni;
    }
    
    public String getRigaTestata() {
    
        return rigaTestata;
    }
    
    public void setRigaTestata(String rigaTestata) {
    
        this.rigaTestata = rigaTestata;
    }
    
    public String getOtherWhereClause() {
    
        return OtherWhereClause;
    }
    
    public void setOtherWhereClause(String otherWhereClause) {
    
        OtherWhereClause = otherWhereClause;
    }

}
