package it.gruppoinit.pal.gp.core.domain.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


public class AlberoEndo {
    
    //private SortedMap<String, List<EndoTipo1>> endoPerCategoria;
    private SortedMap<String, SortedMap<String, List<EndoFACCT>>> endosTree = new TreeMap<String, SortedMap<String,List<EndoFACCT>>>();
    
    public SortedMap<String, SortedMap<String, List<EndoFACCT>>> getEndosPerFamigliaCategoria() {
    
        return endosTree;
    }
    
    public Set<String> getFamiglieKeySet(){
	
	return this.endosTree.keySet();
    }
    
    public SortedMap<String, List<EndoFACCT>> getCategoriePerFamiglia(String famiglia) {
	    
        return this.endosTree.get(famiglia);
    }
    
    public Set<String> getCategorieKeySetPerFamiglia(String famiglia){
	Set<String> retCategorie = null;
	SortedMap<String, List<EndoFACCT>> catForFam = getCategoriePerFamiglia(famiglia);
	if(null != catForFam){
	    retCategorie = catForFam.keySet();
	}
	return retCategorie;
    }

    public void addEndo(EndoFACCT endo, String categoria, String famiglia){
	
	if(endo != null){
	    SortedMap<String, List<EndoFACCT>> endoForCat = this.endosTree.get(famiglia);
	    List<EndoFACCT> endos = null;
	    if(endoForCat == null){
		endoForCat = new TreeMap<String, List<EndoFACCT>>();
		this.endosTree.put(famiglia, endoForCat);
		//endos = new ArrayList<EndoTipo1>();
	    }
	    else{
		endos = endoForCat.get(categoria);
	    }
	    if(endos == null){
		endos = new ArrayList<EndoFACCT>();
		endoForCat.put(categoria, endos);
	    }
	    if(!endos.contains(endo)){
		endos.add(endo);
	    }
	}
    }
    
    public List<EndoFACCT> getElencoEndoPerFamigliaCategoria(String famiglia, String categoria){
	List<EndoFACCT> retList = null;
	SortedMap<String, List<EndoFACCT>> catForFam = getCategoriePerFamiglia(famiglia);
	if(null != catForFam){
	    retList = catForFam.get(categoria);
	}
	return retList;
    }
}
