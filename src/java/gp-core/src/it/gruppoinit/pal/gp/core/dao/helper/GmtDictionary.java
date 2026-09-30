package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Map;


public class GmtDictionary {
    private String Nome;
    private String SQL;
    private Map<String,Map<String, String>> Items;
    
    public String getNome() {
    
        return Nome;
    }
    
    public void setNome(String nome) {
    
        Nome = nome;
    }
    
    public String getSQL() {
    
        return SQL;
    }
    
    public void setSQL(String sQL) {
    
        SQL = sQL;
    }
    
    public Map<String, Map<String, String>> getItems() {
    
        return Items;
    }
    
    public void setItems(Map<String, Map<String, String>> items) {
    
        Items = items;
    }
    

}
