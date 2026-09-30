package it.gruppoinit.pal.gp.core.domain.web;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;


public class CcIcalcoliHelper {
    
    private CcIcalcoli totali;
    
    private List<CcItabella1> tab1 = new ArrayList<CcItabella1>();
    
    private List<CcItabella2> tab2 = new ArrayList<CcItabella2>();
    
    private List<CcItabella3> tab3 = new ArrayList<CcItabella3>();
    
    private List<CcItabella4> tab4 = new ArrayList<CcItabella4>();

    
    /**
     * @return the totali
     */
    public CcIcalcoli getTotali() {
    
        return totali;
    }

    
    /**
     * @param totali the totali to set
     */
    public void setTotali(CcIcalcoli totali) {
    
        this.totali = totali;
    }

    
    /**
     * @return the tab1
     */
    public List<CcItabella1> getTab1() {
    
        return tab1;
    }

    
    /**
     * @param tab1 the tab1 to set
     */
    public void setTab1(List<CcItabella1> tab1) {
    
        this.tab1 = tab1;
    }

    
    /**
     * @return the tab2
     */
    public List<CcItabella2> getTab2() {
    
        return tab2;
    }

    
    /**
     * @param tab2 the tab2 to set
     */
    public void setTab2(List<CcItabella2> tab2) {
    
        this.tab2 = tab2;
    }

    
    /**
     * @return the tab3
     */
    public List<CcItabella3> getTab3() {
    
        return tab3;
    }

    
    /**
     * @param tab3 the tab3 to set
     */
    public void setTab3(List<CcItabella3> tab3) {
    
        this.tab3 = tab3;
    }

    
    /**
     * @return the tab4
     */
    public List<CcItabella4> getTab4() {
    
        return tab4;
    }

    
    /**
     * @param tab4 the tab4 to set
     */
    public void setTab4(List<CcItabella4> tab4) {
    
        this.tab4 = tab4;
    }
    
    
}
