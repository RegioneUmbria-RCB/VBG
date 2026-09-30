package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoMovimentoCancellato implements IEvent {

    private String uuid;
    private String tipomovimento;
    private Integer codiceIstanza;
    private String software;
    private boolean scadenza;
    private Integer codiceMovimento;

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public boolean isScadenza() {

	return scadenza;
    }

    public String getUuid() {

	return uuid;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public String getSoftware() {

	return software;
    }

    private EventoMovimentoCancellato(Integer codiceMovimento, String uuid, Integer codiceIstanza, boolean scadenza, String tipomovimento,
	    String software) {

	super();
	this.codiceMovimento = codiceMovimento;
	this.uuid = uuid;
	this.codiceIstanza = codiceIstanza;
	this.software = software;
	this.scadenza = scadenza;
	this.tipomovimento = tipomovimento;
    }

    public static EventoMovimentoCancellato fromEntity(Integer codiceMovimento, MovimentiDAO movimentiDAO) {

	Movimenti mov = movimentiDAO.findById(new PkId(codiceMovimento));
	String uuid = movimentiDAO.getUuid(codiceMovimento);
	Integer codiceIstanza = mov.getIstanza().getId().getCodice();
	return new EventoMovimentoCancellato(codiceMovimento, uuid, codiceIstanza, mov.getData() == null,
		mov.getTipomovimento().getId().getTipomovimento(), mov.getIstanza().getSoftware().getCodice());
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
