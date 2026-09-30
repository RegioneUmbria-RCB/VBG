package it.gruppoinit.pal.gp.cartfacct.web.command;


public class FakeLoginCommand {
    
    private String token;
    private Integer idAttivita;
    private String idComuneAlias;
    
    /**
     * @return the token
     */
    public String getToken() {
    
        return token;
    }
    
    /**
     * @param token the token to set
     */
    public void setToken(String token) {
    
        this.token = token;
    }
    
    /**
     * @return the idAttivita
     */
    public Integer getIdAttivita() {
    
        return idAttivita;
    }
    
    /**
     * @param idAttivita the idAttivita to set
     */
    public void setIdAttivita(Integer idAttivita) {
    
        this.idAttivita = idAttivita;
    }
    
    /**
     * @return the idComuneAlias
     */
    public String getIdComuneAlias() {
    
        return idComuneAlias;
    }
    
    /**
     * @param idComuneAlias the idComuneAlias to set
     */
    public void setIdComuneAlias(String idComuneAlias) {
    
        this.idComuneAlias = idComuneAlias;
    }
    
    
}
