package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;

public class BorsellinoMovimentiToStringFactoryTests {

    private BorsellinoMovimenti movimentoStorno() {

	//Mercato
	Mercati mercato = new Mercati();
	mercato.setDescrizione("Fiera dell'est");
	//Posteggio
	MercatiD posteggio = new MercatiD();
	posteggio.setMercati(mercato);
	posteggio.setCodiceposteggio("001");
	//Presenza
	GregorianCalendar data = new GregorianCalendar(1983, 6, 26, 16, 16, 16);
	MercatipresenzeT presenza = new MercatipresenzeT();
	presenza.setDataRegistrazione(data.getTime());
	//Autorizzazione
	GregorianCalendar autorizdata = new GregorianCalendar(2021, 6, 26, 16, 16, 16);
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setAutoriznumero("128/2021");
	autorizzazione.setAutorizdata(autorizdata.getTime());
	VwEntilocali autorizcomune = new VwEntilocali();
	autorizcomune.setCodicecomune("C990");
	autorizcomune.setComune("CORCIANO");
	autorizzazione.setAutorizcomune(autorizcomune);
	Tipologiaregistri registro = new Tipologiaregistri();
	registro.setTrDescrizione("Commercio su aree pubbliche");
	autorizzazione.setTipologiaregistro(registro);
	//Movimento
	BorsellinoMovimenti movimento = new BorsellinoMovimenti();
	movimento.setTipo("STORNO");
	movimento.setMercatiD(posteggio);
	movimento.setMercatipresenzeT(presenza);
	movimento.setAutorizzazione(autorizzazione);
	return movimento;
    }

    private BorsellinoMovimenti movimentoUscita() {

	//Mercato
	Mercati mercato = new Mercati();
	mercato.setDescrizione("Fiera dell'est");
	//Posteggio
	MercatiD posteggio = new MercatiD();
	posteggio.setMercati(mercato);
	posteggio.setCodiceposteggio("001");
	//Presenza
	GregorianCalendar data = new GregorianCalendar(1983, 6, 26, 16, 16, 16);
	MercatipresenzeT presenza = new MercatipresenzeT();
	presenza.setDataRegistrazione(data.getTime());
	//Autorizzazione
	GregorianCalendar autorizdata = new GregorianCalendar(2021, 6, 26, 16, 16, 16);
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setAutoriznumero("128/2021");
	autorizzazione.setAutorizdata(autorizdata.getTime());
	VwEntilocali autorizcomune = new VwEntilocali();
	autorizcomune.setCodicecomune("C990");
	autorizcomune.setComune("CORCIANO");
	autorizzazione.setAutorizcomune(autorizcomune);
	Tipologiaregistri registro = new Tipologiaregistri();
	registro.setTrDescrizione("Commercio su aree pubbliche");
	autorizzazione.setTipologiaregistro(registro);
	//Movimento
	BorsellinoMovimenti movimento = new BorsellinoMovimenti();
	movimento.setTipo("USCITA");
	movimento.setMercatiD(posteggio);
	movimento.setMercatipresenzeT(presenza);
	movimento.setAutorizzazione(autorizzazione);
	return movimento;
    }

    private BorsellinoMovimenti movimentoRicarica() {

	//Mercato
	Mercati mercato = new Mercati();
	mercato.setDescrizione("Fiera dell'est");
	//Posteggio
	MercatiD posteggio = new MercatiD();
	posteggio.setMercati(mercato);
	posteggio.setCodiceposteggio("001");
	//Presenza
	GregorianCalendar data = new GregorianCalendar(1983, 6, 26, 16, 16, 16);
	MercatipresenzeT presenza = new MercatipresenzeT();
	presenza.setDataRegistrazione(data.getTime());
	//Autorizzazione
	GregorianCalendar autorizdata = new GregorianCalendar(2021, 6, 26, 16, 16, 16);
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setAutoriznumero("128/2021");
	autorizzazione.setAutorizdata(autorizdata.getTime());
	VwEntilocali autorizcomune = new VwEntilocali();
	autorizcomune.setCodicecomune("C990");
	autorizcomune.setComune("CORCIANO");
	autorizzazione.setAutorizcomune(autorizcomune);
	Tipologiaregistri registro = new Tipologiaregistri();
	registro.setTrDescrizione("Commercio su aree pubbliche");
	autorizzazione.setTipologiaregistro(registro);
	//Posizione debitoria
	GregorianCalendar dataRegistrazione = new GregorianCalendar(2021, 6, 26, 16, 16, 16);
	DettPosizioneDebitoria posizione = new DettPosizioneDebitoria(125);
	posizione.setDescrizioneCausale("Canone di occupazione");
	posizione.setDataRegistrazione(dataRegistrazione.getTime());
	posizione.setIdPosizioneDebitoria(1004);
	posizione.setIuv("001IUV100");
	//Movimento
	BorsellinoMovimenti movimento = new BorsellinoMovimenti();
	movimento.setTipo("RICARICA");
	movimento.setMercatiD(posteggio);
	movimento.setMercatipresenzeT(presenza);
	movimento.setAutorizzazione(autorizzazione);
	movimento.setDettPosizioneDebitoria(posizione);
	return movimento;
    }

    @Test(expected = NotImplementedException.class)
    public void borsellinoMovimentiToStringConMovimentoNull() {

	BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(null);
    }

    @Test
    public void borsellinoMovimentiStornoToString() {

	String expected = "STORNO: Fiera dell'est, 26/07/1983, Posteggio 001, Autorizzazione 128/2021, 26/07/2021, CORCIANO";
	BorsellinoMovimentiToStringFactory factory = BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(this.movimentoStorno());
	String result = factory.toString();
	Assert.assertEquals(expected, result);
    }

    @Test
    public void borsellinoMovimentiUscitaToString() {

	String expected = "USCITA: Fiera dell'est, 26/07/1983, Posteggio 001, Autorizzazione 128/2021, 26/07/2021, CORCIANO";
	BorsellinoMovimentiToStringFactory factory = BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(this.movimentoUscita());
	String result = factory.toString();
	Assert.assertEquals(expected, result);
    }

    @Test
    public void borsellinoMovimentiRicaricaToString() {

	StringBuilder sb = new StringBuilder();
	sb.append("RICARICA: (");
	sb.append("Canone di occupazione, ");
	sb.append("data registrazione: 26/07/2021 - 16:16, ");
	sb.append("IUV: 001IUV100");
	sb.append(")");
	BorsellinoMovimentiToStringFactory factory = BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(this.movimentoRicarica());
	String result = factory.toString();
	Assert.assertEquals(sb.toString(), result);
    }
}
