package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EtichettaApp;

/**
 * @author riccardo.bocci
 *
 */
public class GiornataMercatoRestBean {

    private Integer id;
    private String data;
    private String nome;
    private String nomeAnnoPrecedente;
    private boolean appelloTerminato;
    private boolean giornataChiusa;
    private boolean nodoPagamentiAttivo;
    private boolean flagPopolaConcessionari = true;
    private boolean flagSegnaPresAssenze = true;
    private boolean flagNascondiPannelloTuttiPresenti = false;
    private boolean flagVerificaPagamentoConcessionari = false;
    private boolean mappaPresente = false;
    private boolean bloccaAssegnazioneCreditoInsufficiente;
    private boolean bloccaChiusuraGiornataSePosteggiNonAssegnati;
    private boolean mostraTerminaAppello;
    private String messaggioCheckChiusuraGiornataSePosteggiNonAssegnati;
    private String messaggioChiusuraGiornata;
    private List<GiornataMercatoFaseRestBean> fasi;
    private List<GiornataMercatoPosteggioRestBean> posteggi;
    private List<GiornataMercatoSpuntistaRestBean> spuntisti;
    private List<FasciaMercatoBean> listaFasceMercato;
    private List<EtichettaApp> etichettaApps;
    private Boolean isNascondiInserimentoSpuntista;
    private Boolean nascondiPagamentoEffettuato;
    //
    private List<CodiceDescrizioneBean> categorieMerceologicheGiornataMercato;

    public GiornataMercatoRestBean(boolean flagNascondiPannelloTuttiPresenti) {

	this.setFlagNascondiPannelloTuttiPresenti(flagNascondiPannelloTuttiPresenti);
    }

    public Integer getId() {

	return id;
    }

    public boolean getFlagNascondiPannelloTuttiPresenti() {

	return flagNascondiPannelloTuttiPresenti;
    }

    public void setFlagNascondiPannelloTuttiPresenti(boolean flagNascondiPannelloTuttiPresenti) {

	this.flagNascondiPannelloTuttiPresenti = flagNascondiPannelloTuttiPresenti;
    }

    public boolean getFlagVerificaPagamentoConcessionari() {

	return this.flagVerificaPagamentoConcessionari;
    }

    public void setFlagVerificaPagamentoConcessionari(boolean flagVerificaPagamentoConcessionari) {

	this.flagVerificaPagamentoConcessionari = flagVerificaPagamentoConcessionari;
    }

    public boolean getMappaPresente() {

	return this.mappaPresente;
    }

    public void setMappaPresente(boolean mappaPresente) {

	this.mappaPresente = mappaPresente;
    }

    public boolean getBloccaAssegnazioneCreditoInsufficiente() {

	return bloccaAssegnazioneCreditoInsufficiente;
    }

    public void setBloccaAssegnazioneCreditoInsufficiente(boolean bloccaAssegnazioneCreditoInsufficiente) {

	this.bloccaAssegnazioneCreditoInsufficiente = bloccaAssegnazioneCreditoInsufficiente;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getNomeAnnoPrecedente() {

	return nomeAnnoPrecedente;
    }

    public void setNomeAnnoPrecedente(String nomeAnnoPrecedente) {

	this.nomeAnnoPrecedente = nomeAnnoPrecedente;
    }

    public boolean getAppelloTerminato() {

	return appelloTerminato;
    }

    public void setAppelloTerminato(boolean appelloTerminato) {

	this.appelloTerminato = appelloTerminato;
    }

    public boolean getGiornataChiusa() {

	return giornataChiusa;
    }

    public void setGiornataChiusa(boolean giornataChiusa) {

	this.giornataChiusa = giornataChiusa;
    }

    public List<GiornataMercatoFaseRestBean> getFasi() {

	if (fasi == null) {
	    fasi = new ArrayList<GiornataMercatoFaseRestBean>();
	}
	return fasi;
    }

    public void setFasi(List<GiornataMercatoFaseRestBean> fasi) {

	this.fasi = fasi;
    }

    public List<GiornataMercatoPosteggioRestBean> getPosteggi() {

	if (null == posteggi) {
	    posteggi = new ArrayList<GiornataMercatoPosteggioRestBean>();
	}
	return posteggi;
    }

    public void setPosteggi(List<GiornataMercatoPosteggioRestBean> posteggi) {

	this.posteggi = posteggi;
    }

    public List<GiornataMercatoSpuntistaRestBean> getSpuntisti() {

	if (null == spuntisti) {
	    spuntisti = new ArrayList<GiornataMercatoSpuntistaRestBean>();
	}
	return spuntisti;
    }

    public void setSpuntisti(List<GiornataMercatoSpuntistaRestBean> spuntisti) {

	this.spuntisti = spuntisti;
    }

    public List<CodiceDescrizioneBean> getCategorieMerceologicheGiornataMercato() {

	if (null == categorieMerceologicheGiornataMercato) {
	    categorieMerceologicheGiornataMercato = new ArrayList<CodiceDescrizioneBean>();
	}
	return categorieMerceologicheGiornataMercato;
    }

    public void setCategorieMerceologicheGiornataMercato(List<CodiceDescrizioneBean> categorieMerceologicheGiornataMercato) {

	this.categorieMerceologicheGiornataMercato = categorieMerceologicheGiornataMercato;
    }

    public boolean getNodoPagamentiAttivo() {

	return nodoPagamentiAttivo;
    }

    public void setNodoPagamentiAttivo(boolean nodoPagamentiAttivo) {

	this.nodoPagamentiAttivo = nodoPagamentiAttivo;
    }

    public List<FasciaMercatoBean> getListaFasceMercato() {

	if (null == listaFasceMercato) {
	    listaFasceMercato = new ArrayList<FasciaMercatoBean>();
	}
	return listaFasceMercato;
    }

    public void setListaFasceMercato(List<FasciaMercatoBean> listaFasceMercato) {

	this.listaFasceMercato = listaFasceMercato;
    }

    public boolean getFlagPopolaConcessionari() {

	return flagPopolaConcessionari;
    }

    public void setFlagPopolaConcessionari(boolean flagPopolaConcessionari) {

	this.flagPopolaConcessionari = flagPopolaConcessionari;
    }

    public boolean getFlagSegnaPresAssenze() {

	return flagSegnaPresAssenze;
    }

    public void setFlagSegnaPresAssenze(boolean flagSegnaPresAssenze) {

	this.flagSegnaPresAssenze = flagSegnaPresAssenze;
    }

    public boolean getBloccaChiusuraGiornataSePosteggiNonAssegnati() {

	return bloccaChiusuraGiornataSePosteggiNonAssegnati;
    }

    public void setBloccaChiusuraGiornataSePosteggiNonAssegnati(boolean bloccaChiusuraGiornataSePosteggiNonAssegnati) {

	this.bloccaChiusuraGiornataSePosteggiNonAssegnati = bloccaChiusuraGiornataSePosteggiNonAssegnati;
    }

    public String getMessaggioCheckChiusuraGiornataSePosteggiNonAssegnati() {

	return messaggioCheckChiusuraGiornataSePosteggiNonAssegnati;
    }

    public void setMessaggioCheckChiusuraGiornataSePosteggiNonAssegnati(String messaggioCheckChiusuraGiornataSePosteggiNonAssegnati) {

	this.messaggioCheckChiusuraGiornataSePosteggiNonAssegnati = messaggioCheckChiusuraGiornataSePosteggiNonAssegnati;
    }

    public String getMessaggioChiusuraGiornata() {

	return messaggioChiusuraGiornata;
    }

    public void setMessaggioChiusuraGiornata(String messaggioChiusuraGiornata) {

	this.messaggioChiusuraGiornata = messaggioChiusuraGiornata;
    }

    public boolean getMostraTerminaAppello() {

	return mostraTerminaAppello;
    }

    public void setMostraTerminaAppello(boolean mostraTerminaAppello) {

	this.mostraTerminaAppello = mostraTerminaAppello;
    }

    public List<EtichettaApp> getEtichettaApps() {

	return etichettaApps;
    }

    public void setEtichettaApps(List<EtichettaApp> etichettaApps) {

	this.etichettaApps = etichettaApps;
    }

    public Boolean getIsNascondiInserimentoSpuntista() {

	return isNascondiInserimentoSpuntista;
    }

    public void setIsNascondiInserimentoSpuntista(Boolean isNascondiInserimentoSpuntista) {

	this.isNascondiInserimentoSpuntista = isNascondiInserimentoSpuntista;
    }

    public Boolean getNascondiPagamentoEffettuato() {

	return nascondiPagamentoEffettuato;
    }

    public void setNascondiPagamentoEffettuato(Boolean nascondiPagamentoEffettuato) {

	this.nascondiPagamentoEffettuato = nascondiPagamentoEffettuato;
    }
}
