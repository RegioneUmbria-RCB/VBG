package it.gruppoinit.pal.gp.core.features.suapxml;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.suapxml.exceptions.GenerazioneSuapXmlException;
import it.gruppoinit.pal.gp.core.features.suapxml.upgr.VecchioParametroSuapXml;

public interface ISuapXmlService {

    /**
     * ATTENZIONE SEGNATURA DICHIARATA ANCHE IN APPLICATIONCONTEXT.XML per rollback
     * 
     * @param codiceComune
     * @param codiceIstanza
     * @return
     * @throws GenerazioneSuapXmlException
     */
    byte[] generaSuapXML(String codiceComune, Integer codiceIstanza) throws GenerazioneSuapXmlException;

    List<VecchioParametroSuapXml> recuperaVecchiParametriPerUPGR();

    boolean isDocPresenteSuMovimento(Integer codiceMovimento);
}
