package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;


public class CampoDinamicoFvgHelper {
    
    
    
    private String id;
    private List<ValoreCampoDinamicoFvgHelper> valoreCampoDinamicoFvgHelper =new ArrayList<ValoreCampoDinamicoFvgHelper>();
    
    public String getId() {
    
        return id;
    }
    
    public void setId(String id) {
    
        this.id = id;
    }
    
    public List<ValoreCampoDinamicoFvgHelper> getValoreCampoDinamicoFvgHelper() {
    
        return valoreCampoDinamicoFvgHelper;
    }
    
    public void setValoreCampoDinamicoFvgHelper(List<ValoreCampoDinamicoFvgHelper> valoreCampoDinamicoFvgHelper) {
    
        this.valoreCampoDinamicoFvgHelper = valoreCampoDinamicoFvgHelper;
    }
    
    
    
    
    

    
    
    
    
}
