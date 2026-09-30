package it.gruppoinit.pal.gp.core.domain.helper;

import it.eng.suap.xengine.model.modulistica.ItemType;


public class ContainerWrapper {
    
    private String containerId;
    private ItemType container;
    
    
    public ContainerWrapper(){
	
    }
    
    public ContainerWrapper(ItemType container, String uniqueId){
	
	this.container = container;
	this.containerId = uniqueId;
    }
    /**
     * @return the containerId
     */
    public String getContainerId() {
    
        return containerId;
    }
    
    /**
     * @param containerId the containerId to set
     */
    public void setContainerId(String containerId) {
    
        this.containerId = containerId;
    }
    
    /**
     * @return the container
     */
    public ItemType getContainer() {
    
        return container;
    }
    
    /**
     * @param container the container to set
     */
    public void setContainer(ItemType container) {
    
        this.container = container;
    }
    
    
}
