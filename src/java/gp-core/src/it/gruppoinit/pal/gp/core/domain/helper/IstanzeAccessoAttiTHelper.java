package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.List;

/**
 * Questa classe di appoggio permette di gestire la visualizzazione delle IstanzeAccessoAttiDHelper nella view
 * addcollegamento.jsp. Il campo istanzaaccessoattiDHelper è l'oggetto principale, cioè quello che seleziono quando
 * voglio aggiungere l'istanza accesso atti d. Il campo List<IstanzeAccessoAttiDHelper> istanzaaccessoattiDHelpers è la
 * lista delle istanze collegate al campo precedente, e saranno visualizzate in una tabella sotto l'oggetto principale.
 * 
 * @author simone.vernata
 *
 */
public class IstanzeAccessoAttiTHelper {

    private Integer codiceIstanzeAttiT;
    // oggetto principale della catena delle istanze collegate
    private IstanzeAccessoAttiDHelper istanzaaccessoattiDHelper;
    // lista oggetti istanze collegate
    private List<IstanzeAccessoAttiDHelper> istanzaaccessoattiDHelpers;

    public Integer getCodiceIstanzeAttiT() {

	return codiceIstanzeAttiT;
    }

    public void setCodiceIstanzeAttiT(Integer codiceIstanzeAttiT) {

	this.codiceIstanzeAttiT = codiceIstanzeAttiT;
    }

    public IstanzeAccessoAttiDHelper getIstanzaaccessoattiDHelper() {

	return istanzaaccessoattiDHelper;
    }

    public void setIstanzaaccessoattiDHelper(IstanzeAccessoAttiDHelper istanzaaccessoattiDHelper) {

	this.istanzaaccessoattiDHelper = istanzaaccessoattiDHelper;
    }

    public List<IstanzeAccessoAttiDHelper> getIstanzaaccessoattiDHelpers() {

	return istanzaaccessoattiDHelpers;
    }

    public void setIstanzaaccessoattiDHelpers(List<IstanzeAccessoAttiDHelper> istanzaaccessoattiDHelpers) {

	this.istanzaaccessoattiDHelpers = istanzaaccessoattiDHelpers;
    }
}
