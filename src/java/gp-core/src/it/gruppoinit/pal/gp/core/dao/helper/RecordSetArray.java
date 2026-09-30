package it.gruppoinit.pal.gp.core.dao.helper;

import java.sql.ResultSet;


public class RecordSetArray {
    
    String name;
    String SQL;
    String SQLRtf;
    ResultSet resultSet;
    
    public String getName() {
    
        return name;
    }
    
    public void setName(String name) {
    
        this.name = name;
    }
    
    public String getSQL() {
    
        return SQL;
    }
    
    public void setSQL(String sQL) {
    
        SQL = sQL;
    }
    
    public String getSQLRtf() {
    
        return SQLRtf;
    }
    
    public void setSQLRtf(String sQLRtf) {
    
        SQLRtf = sQLRtf;
    }
    
    public ResultSet getResultSet() {
    
        return resultSet;
    }
    
    public void setResultSet(ResultSet resultSet) {
    
        this.resultSet = resultSet;
    }
}
