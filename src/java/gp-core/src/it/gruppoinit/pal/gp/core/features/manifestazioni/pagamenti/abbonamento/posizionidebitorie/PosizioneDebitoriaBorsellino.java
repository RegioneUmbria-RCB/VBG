package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.posizionidebitorie;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.paevolution.ws.pagamenti_types.ImportoPagamentoWsInType;
import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.PosizioneDebitoriaWsInType;
import com.paevolution.ws.pagamenti_types.RegistrazioneContabileWsInType;

import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiRicaricheModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi.MessaggioRicaricaNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.SoggettoDebitoreDaAnagrafe;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class PosizioneDebitoriaBorsellino {

    private Borsellino borsellino;
    private String cfEnteCreditore;
    private BigDecimal importo;
    private RipartizioneContiHelper ripartizione;

    public PosizioneDebitoriaBorsellino(Borsellino borsellino, String cfEnteCreditore, BigDecimal importo, RipartizioneContiHelper ripartizione) {

	this.borsellino = borsellino;
	this.cfEnteCreditore = cfEnteCreditore;
	this.importo = importo;
	this.ripartizione = ripartizione;
    }

    public InserisciPosizioniDebitorieType toInsericiPosizioneDebitoria() {

	InserisciPosizioniDebitorieType pos = new InserisciPosizioniDebitorieType();
	pos.setAccorpaPosizioni(false);
	pos.setCfEnteCreditore(cfEnteCreditore);
	RegistrazioneContabileWsInType rc = new RegistrazioneContabileWsInType();
	rc.setSoggettoDebitore(new SoggettoDebitoreDaAnagrafe(borsellino.getAnagrafe()).toSoggettoDebitoreType());
	rc.setData(Utilities.getToday());
	rc.setAnno(rc.getData().getYear());
	rc.setDescrizione(new MessaggioRicaricaNodoPagamenti(borsellino).getTestoMessaggio());
	PosizioneDebitoriaWsInType r = new PosizioneDebitoriaWsInType();
	Date d = Utilities.addDays(Calendar.getInstance().getTime(), 365);
	r.setDataScadenza(Utilities.getXMLGregorianCalendar(d));
	r.setNumeroRata(BigInteger.valueOf(1));
	r.setDescrizione(rc.getDescrizione());
	r.getRiferimentiClient().add("abbonamento-" + borsellino.getUuid());
	List<RipartizioneContiRicaricheModel> listaConti = ripartizione.getListaConti();
	for (RipartizioneContiRicaricheModel rip : listaConti) {
	    ImportoPagamentoWsInType impo = new ImportoPagamentoWsInType();
	    impo.setCodiceMappatura(rip.getConto().getMappaturanodopag());
	    impo.setImporto(rip.calcolaImportoDaTotale(importo));
	    r.getImporti().add(impo);
	}
	rc.getRate().add(r);
	pos.getRegistrazione().add(rc);
	return pos;
    }
}
