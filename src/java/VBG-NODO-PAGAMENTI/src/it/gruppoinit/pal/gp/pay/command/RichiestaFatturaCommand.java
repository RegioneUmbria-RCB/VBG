package it.gruppoinit.pal.gp.pay.command;

import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.ws.schema.DatiFatturaType;

public class RichiestaFatturaCommand {

    private PayPosizioniDebitorie posizioneDebitoria;
    private DatiFatturaType datiRichiestaFattura;

    public RichiestaFatturaCommand() {

    }

    public RichiestaFatturaCommand(PayPosizioniDebitorie pos) {

	this.posizioneDebitoria = pos;
    }

    public PayPosizioniDebitorie getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public void setPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria) {

	this.posizioneDebitoria = posizioneDebitoria;
    }

    public DatiFatturaType getDatiRichiestaFattura() {

	return datiRichiestaFattura;
    }

    public void setDatiRichiestaFattura(DatiFatturaType datiRichiestaFattura) {

	this.datiRichiestaFattura = datiRichiestaFattura;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((posizioneDebitoria == null) ? 0 : posizioneDebitoria.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RichiestaFatturaCommand other = (RichiestaFatturaCommand) obj;
	if (posizioneDebitoria == null) {
	    if (other.posizioneDebitoria != null)
		return false;
	} else if (!posizioneDebitoria.equals(other.posizioneDebitoria))
	    return false;
	return true;
    }
}
