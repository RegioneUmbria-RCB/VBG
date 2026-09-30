package it.gruppoinit.service;

import java.io.File;
import java.util.List;

import it.gov.impresainungiorno.schema.suap.ente.AllegatoCooperazione;
import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gruppoinit.domain.nla.Allegato;

public interface GestioneAllegatoPraticaService {

    public Allegato findFileSUAP_XML(List<AllegatoCooperazione> allegatiCooperazione, String prefisso) throws Exception;

    public Allegato findFileSUAP_XML_From_Allegati(List<Allegato> allegati, String prefisso);

    public Allegato findFile(List<AllegatoCooperazione> allegatiCooperazione, String prefisso, String suffisso, String estesione) throws Exception;

    public List<Allegato> loadFileS(List<AllegatoCooperazione> allegatiCooperazione) throws Exception;

    public List<Allegato> findFileMDAFILE_XML_From_Allegati(List<Allegato> allegati);

    public File generateFilComunicazioneSuapEnte(String comunicazioneSuapEnteStringa, CooperazioneSUAPEnte cooperazioneSUAPEnte);

    public String saveComunicazioneSuapEnteInString(CooperazioneSUAPEnte cooperazioneSUAPEnte);
}
