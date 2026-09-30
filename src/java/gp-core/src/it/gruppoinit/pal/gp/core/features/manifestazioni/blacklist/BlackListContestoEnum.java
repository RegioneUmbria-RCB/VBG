package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;


public enum BlackListContestoEnum {
    PRESENZE,
    BOLLETTAZIONE;
    
    public static BlackListContestoEnum getByContesto(String contesto){
	if("presenze".equals(contesto)) return PRESENZE;
	if("bollettazione".equals(contesto)) return BOLLETTAZIONE;
	throw new RuntimeException("Not valid contesto: " + contesto);
    }
}
