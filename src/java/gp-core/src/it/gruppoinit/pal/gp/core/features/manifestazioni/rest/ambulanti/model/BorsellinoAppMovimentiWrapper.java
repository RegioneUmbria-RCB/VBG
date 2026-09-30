package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BorsellinoAppMovimentiWrapper {
    
    @XmlElement
    private List<BorsellinoAppMovimenti> movimenti;
    @XmlElement
    private Integer page;
    @XmlElement
    private Integer maxresult;
    @XmlElement
    private Integer totalsize;
    
    public List<BorsellinoAppMovimenti> getMovimenti() {
    
        return movimenti;
    }
    
    public void setMovimenti(List<BorsellinoAppMovimenti> movimenti) {
    
        this.movimenti = movimenti;
    }
    
    public Integer getPage() {
    
        return page;
    }
    
    public void setPage(Integer page) {
    
        this.page = page;
    }            
    
    public Integer getMaxresult() {
    
        return maxresult;
    }
    
    public void setMaxresult(Integer maxresult) {
    
        this.maxresult = maxresult;
    }

    public Integer getTotalsize() {
    
        return totalsize;
    }
    
    public void setTotalsize(Integer totalsize) {
    
        this.totalsize = totalsize;
    }    
    
}
