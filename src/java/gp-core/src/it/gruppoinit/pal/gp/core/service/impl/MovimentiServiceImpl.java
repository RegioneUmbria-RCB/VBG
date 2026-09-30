package it.gruppoinit.pal.gp.core.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.rest.v2.MovimentoRestBean;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;

@Service
public class MovimentiServiceImpl extends MovimentiBaseServiceImpl implements MovimentiService {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ResponsabiliService responsabileService;

    @Override
    public MovimentoRestBean insertRest(MovimentoRestBean movimentoBean) {

	if (movimentoBean == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo MovimentiServiceImpl.insertRest senza passare il movimento da inserire");
	}
	if (movimentoBean.getCodice_istanza() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo MovimentiServiceImpl.insertRest: nei dati del movimento passato manca il riferimento all'istanza");
	}
	if (StringUtils.isBlank(movimentoBean.getTipomovimento())) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo MovimentiServiceImpl.insertRest: nei dati del movimento passato manca il riferimento al tipomovimento");
	}
	//1. converto il bean in entity Movimenti
	Movimenti movimento = this.movimentoRestBeanToMovimenti(movimentoBean);
	//2. converto la entity Movimenti in bean
	this.insert(movimento);
	return movimento.toMovimentoRestBean();
    }

    private Movimenti movimentoRestBeanToMovimenti(MovimentoRestBean bean) {

	Movimenti movimento = new Movimenti();
	Istanze istanza = this.istanzeService.findById(new PkId(bean.getCodice_istanza()));
	movimento.setIstanza(istanza);
	Tipimovimento tipomovimento = this.tipiMovimentoService.findById(new TipimovimentoId(bean.getTipomovimento()));
	movimento.setTipomovimento(tipomovimento);
	movimento.setMovimento(StringUtils.isBlank(bean.getMovimento()) ? tipomovimento.getMovimento() : bean.getMovimento());
	if (bean.getCodice_inventario() != null) {
	    Inventarioprocedimenti inventarioprocedimenti = this.inventarioprocedimentiService.findById(new PkId(bean.getCodice_inventario()));
	    movimento.setEndoprocedimento(inventarioprocedimenti);
	}
	if (bean.getCodice_amministrazione() != null) {
	    Amministrazioni amministrazione = this.amministrazioniService.findById(new PkId(bean.getCodice_amministrazione()));
	    movimento.setAmministrazioni(amministrazione);
	}
	movimento.setData(bean.getData());
	movimento.setParere(bean.getParere());
	movimento.setEsito(bean.getEsito());
	movimento.setNote(bean.getNote());
	movimento.setPubblica(bean.getPubblica());
	movimento.setNumeroprotocollo(bean.getNumeroprotocollo());
	movimento.setDataprotocollo(bean.getDataprotocollo());
	if (bean.getCodice_responsabile() != null) {
	    Responsabili responsabile = this.responsabileService.findById(new PkId(bean.getCodice_responsabile()));
	    movimento.setResponsabile(responsabile);
	}
	movimento.setPubblicaparere(bean.getPubblicaparere());
	movimento.setDataScadenza(bean.getDataScadenza());
	return movimento;
    }
}
