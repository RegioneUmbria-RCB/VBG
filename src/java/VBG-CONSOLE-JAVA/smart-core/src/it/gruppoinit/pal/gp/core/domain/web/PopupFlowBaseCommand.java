/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.web;


/**
 * Superclasse per i command che devono gestire le informazioni per il flusso di finestre in popup che devono restituire l'id di un nuovo record creato ad una pagina chiamante anch'essa in popup
 * @author francol
 *
 */
public abstract class PopupFlowBaseCommand <E> extends BaseCommand {
    
    protected Integer callerId;
    protected Integer newId;
    protected Boolean popup;
    protected E entity;
    
    public Integer getCallerId() {
    
        return callerId;
    }
    
    public void setCallerId(Integer callerId) {
    
        this.callerId = callerId;
    }
    
    public Integer getNewId() {
    
        return newId;
    }
    
    public void setNewId(Integer newId) {
    
        this.newId = newId;
    }
    
    public Boolean getPopup() {
    
        return popup;
    }
    
    public void setPopup(Boolean popup) {
    
        this.popup = popup;
    }
    
    public E getEntity() {
    
        return entity;
    }
    
    public void setEntity(E entity) {
    
        this.entity = entity;
    }
    
    
    
}
