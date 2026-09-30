package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.MassiveParametri;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtMetadati;
import it.gruppoinit.pal.gp.core.domain.MassiveTProtocollo;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametroConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ResocontoOperazioniMassive;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@Service
public class CreazioneMassiveTestataServiceImpl implements ICreazioneMassiveTestataService {

    @Autowired
    private IComunicazioniMassiveDAO massiveDao;
    @Autowired
    private MailtipoService mailTipoService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ICurrentDateService currentDateService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ComuniService comuniService;

    @Override
    public int insert(IConfigurazioneComunicazione configurazioneComunicazione) {

	ConfigurazioneFlyweight cfg = configurazioneComunicazione.getParametriPerDb();
	MassiveTestata testata = new MassiveTestata();
	testata.setDescrizione(cfg.getDescrizione());
	testata.setCodiceresponsabile((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	testata.setDataComunicazione(currentDateService.getCurrentDate());
	if (cfg.getParametriProtocollazione() != null) {
	    testata.setProtMailtipo(this.mailTipoService.findById(new PkId(cfg.getParametriProtocollazione().getCodiceMailtipo())));
	}
	if (cfg.getConfigurazioneMail() != null) {
	    Mailtipo mailTipo = this.mailTipoService.findById(new PkId(cfg.getConfigurazioneMail().getIdMailTipo()));
	    MailConfig mailConfig = this.mailConfigService.findById(new PkId(cfg.getConfigurazioneMail().getSenderAccount()));
	    testata.setSenderAccount(mailConfig);
	    testata.setFkidMailtipo(mailTipo);
	}
	massiveDao.insert(testata);
	Integer idTestata = testata.getId().getCodice();
	if (cfg.getParametriProtocollazione() != null) {
	    for (ParametriProtocolloPerEnte pc : cfg.getParametriProtocollazione().getParametriPerEnte()) {
		MassiveTProtocollo p = new MassiveTProtocollo();
		p.setMassiveTestata(testata);
		p.setAmministrazioni(amministrazioniService.findById(new PkId(pc.getCodiceAmministrazione())));
		p.setClassifica(pc.getClassifica());
		p.setTipodocumento(pc.getTipodocumento());
		if (StringUtils.isNotBlank(pc.getCodiceComune())) {
		    p.setComuni(comuniService.findById(pc.getCodiceComune()));
		}
		if (pc.getMetadati() != null && !pc.getMetadati().isEmpty()) {
		    for (MetadatiBean metadato : pc.getMetadati()) {
			MassiveTProtMetadati m = new MassiveTProtMetadati();
			m.setId(new PkId());
			m.setChiave(metadato.getChiave());
			m.setValore(metadato.getValore());
			p.getMetadati().add(m);
		    }
		}
		this.massiveDao.insertParametriProtocollo(p);
	    }
	}
	for (ParametroConfigurazioneComunicazione parametro : cfg.getParametri()) {
	    MassiveParametri par = new MassiveParametri();
	    par.setChiave(parametro.getChiave());
	    par.setValore(parametro.getValore());
	    par.setMassiveTestata(testata);
	    this.massiveDao.insert(par);
	}
	for (AllegatoComunicazione allegati : cfg.getAllegatiFissi()) {
	    this.massiveDao.insertAllegatoFisso(idTestata, allegati.getCodiceOggetto());
	}
	for (LetteraComunicazione allegati : cfg.getLettereComunicazione()) {
	    this.massiveDao.insertLettera(idTestata, allegati.getCodiceLettera());
	}
	for (Integer idSoggetto : cfg.getSoggettiFirmatari()) {
	    this.massiveDao.insertSoggettoFirmatario(idTestata, idSoggetto);
	}
	return idTestata;
    }

    @Override
    public List<Integer> findByIdBollettazione(Integer idBollettazione) {

	return massiveDao.findByIdBollettazione(idBollettazione);
    }

    @Override
    public List<ResocontoOperazioniMassive> findResocontoOperazioniMassiveById(Integer idTestata) {

	return massiveDao.findResocontoOperazioniMassiveById(idTestata);
    }

    @Override
    public List<Integer> findByIdCommissioni(Integer idCommissioni) {

	return massiveDao.findByIdCommissioni(idCommissioni);
    }

    @Override
    public List<Integer> findByIdMercato(Integer idMercato) {

	return this.massiveDao.findByIdMercato(idMercato);
    }
    
    @Override
    public List<Integer> findIdTestataByGen(String sql, Object[] params, String fkscalar) {

	return this.massiveDao.findIdTestataByGen(sql, params, fkscalar);
    }

}
