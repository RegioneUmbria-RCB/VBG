package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.service.exception.RemoteCallException;
import it.gruppoinit.pal.gp.core.service.helper.SitCampiAmmessi;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.FiltroRicercaListaVie;
import it.gruppoinit.sigepro.backoffice.ws.sit.stub.Sit;

public interface SitService extends BaseService<Sit, String> {

    public List<String> getListaValori(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException;

    public Sit validaValore(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException;

    public Map<String, String> getDetailField(String token, SitCampiAmmessi campo, Istanzestradario filter) throws RemoteCallException;

    public Set<String> getCampiGestiti(String token, String software);

    boolean effettuaValidazioneFormale(String token, String software, Istanzestradario filter) throws RemoteCallException;

    public Map<String, String> getCampoDettaglioGestito(String token, String software);

    public List<Stradario> getListaVie(String token, FiltroRicercaListaVie filtroRicercaListaVie, List<String> codiciComuni)
	    throws RemoteCallException;

    void resetObjectCached();

    public Map<String, String> getBoFeatures(String token, String software) throws RemoteCallException;
}
