package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author gianpaolot
 * 
 */
public class IstanzeprocedimentiCommand extends BaseCommand {

    private Istanzeprocedimenti entity;
    // il campo mi permette di mostrare in inserimento sugli endo attivabili la data odierna
    private Date dataattivazioneCommnad;
    private String insertAutorizzazione;
    private String insertAcquisto;
    private String insertAmmissione;
    private String note;
    private List<Inventarioprocedimenti> inventarioprocedimentis = new ArrayList<Inventarioprocedimenti>();

    public IstanzeprocedimentiCommand() {

	super();
	this.entity = new Istanzeprocedimenti();
	this.dataattivazioneCommnad = Calendar.getInstance().getTime();
    }

    public Istanzeprocedimenti getEntity() {

	return entity;
    }

    public void setEntity(Istanzeprocedimenti entity) {

	this.entity = entity;
    }

    public List<Inventarioprocedimenti> getInventarioprocedimentis() {

	return inventarioprocedimentis;
    }

    public void setInventarioprocedimentis(List<Inventarioprocedimenti> inventarioprocedimentis) {

	this.inventarioprocedimentis = inventarioprocedimentis;
    }

    public Date getDataattivazioneCommnad() {

	return dataattivazioneCommnad;
    }

    public void setDataattivazioneCommnad(Date dataattivazioneCommnad) {

	this.dataattivazioneCommnad = dataattivazioneCommnad;
    }

    public String getInsertAutorizzazione() {

	return insertAutorizzazione;
    }

    public void setInsertAutorizzazione(String insertAutorizzazione) {

	this.insertAutorizzazione = insertAutorizzazione;
    }

    public String getInsertAcquisto() {

	return insertAcquisto;
    }

    public void setInsertAcquisto(String insertAcquisto) {

	this.insertAcquisto = insertAcquisto;
    }

    public String getInsertAmmissione() {

	return insertAmmissione;
    }

    public void setInsertAmmissione(String insertAmmissione) {

	this.insertAmmissione = insertAmmissione;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
