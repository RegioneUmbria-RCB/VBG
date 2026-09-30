package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;

import java.util.ArrayList;
import java.util.List;

public class AlberoprocBolkesteinCommand extends BaseCommand {

    private Integer codiceAlberoproc;
    private Mercati entity;
    private MercatiUso mercatiUso;
    private List<AlberoprocBolkesteinHelper> posteggis = new ArrayList<AlberoprocBolkesteinHelper>();

    public Integer getCodiceAlberoproc() {

	return codiceAlberoproc;
    }

    public void setCodiceAlberoproc(Integer codiceAlberoproc) {

	this.codiceAlberoproc = codiceAlberoproc;
    }

    public Mercati getEntity() {

	return entity;
    }

    public void setEntity(Mercati entity) {

	this.entity = entity;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public List<AlberoprocBolkesteinHelper> getPosteggis() {

	return posteggis;
    }

    public void setPosteggis(List<AlberoprocBolkesteinHelper> posteggis) {

	this.posteggis = posteggis;
    }
}
