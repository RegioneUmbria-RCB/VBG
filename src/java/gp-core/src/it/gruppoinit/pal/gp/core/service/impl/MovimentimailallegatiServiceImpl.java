package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.MovimentimailallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Movimentimailallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.MovimentimailallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.MetadatiFunzioneEnum;

/**
 * 
 * @author francescop
 */
@Service
public class MovimentimailallegatiServiceImpl extends BaseServiceImpl<Movimentimailallegati, PkId> implements MovimentimailallegatiService {

    private DocumentiHelperService documentiHelperService;
    private MovimentimailService movimentimailService;
    private MovimentimailallegatiDAO movimentimailallegatiDAO;
    private OggettiService oggettiService;
    private MovimentiZipLogicoService movimentiZipLogicoService;

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMovimentimailallegatiDAO(MovimentimailallegatiDAO movimentimailallegatiDAO) {

	this.movimentimailallegatiDAO = movimentimailallegatiDAO;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    protected Class<Movimentimailallegati> getEntityClass() {

	return Movimentimailallegati.class;
    }

    @Override
    public List<Movimentimailallegati> findAll(Integer firstResult, Integer maxResult) {

	return movimentimailallegatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Movimentimailallegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    movimentimailallegatiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public Movimentimailallegati findById(PkId id) {

	return movimentimailallegatiDAO.findById(id);
    }

    @Override
    public void update(Movimentimailallegati entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    movimentimailallegatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Movimentimailallegati entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    movimentimailallegatiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public Set<Movimentimailallegati> findAllegatiMovimentiMailDaInviare(DocumentiHelper documentiHelper, Movimentimail movimentimail) {

	Set<Movimentimailallegati> movimentimailallegatis = new HashSet<Movimentimailallegati>();
	Movimentimailallegati movimentimailallegati = null;
	documentiHelper = documentiHelperService.findDocumentiInvioTrue(documentiHelper);
	// Bonifico la lista dei documenti del movimento
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _movDocChiaveValoreBeans = documentiHelper.getDocumentiMovimentoList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : _movDocChiaveValoreBeans) {
	    List<MovimentiallegatiDTO> movimentiallegatis = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(movimentiallegati.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(movimentiallegati.getDescrizione());
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	// Bonifico la lista dei documenti di altri mov
	List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> _altriMovDocChiaveValoreBeans = documentiHelper.getDocumentiAltriMovimentiList();
	for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> chiaveValoreBean : _altriMovDocChiaveValoreBeans) {
	    List<MovimentiallegatiDTO> movimentiallegatis = chiaveValoreBean.getValore();
	    for (MovimentiallegatiDTO movimentiallegati : movimentiallegatis) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(movimentiallegati.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(movimentiallegati.getDescrizione());
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	// Bonifico lista documenti istanza
	List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> _istanzaDocChiaveValoreBeans = documentiHelper.getDocumentiIstanzaList();
	for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> chiaveValoreBean : _istanzaDocChiaveValoreBeans) {
	    List<DocumentiistanzaDTO> documentiistanzas = chiaveValoreBean.getValore();
	    for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(documentiistanza.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(documentiistanza.getDocumento());
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	// Bonifico lista documenti endo
	List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> _endoDocChiaveValoreBeans = documentiHelper.getDocumentiEndoprocedimentiList();
	for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> chiaveValoreBean : _endoDocChiaveValoreBeans) {
	    List<IstanzeallegatiDTO> istanzeallegatis = chiaveValoreBean.getValore();
	    for (IstanzeallegatiDTO istanzeallegati : istanzeallegatis) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(istanzeallegati.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(istanzeallegati.getAllegatoextra());
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	// Bonifico lista documenti anagrafiche
	List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> _anagrafeDocChiaveValoreBeans = documentiHelper.getDocumentiAnagrafeList();
	for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> chiaveValoreBean : _anagrafeDocChiaveValoreBeans) {
	    List<AnagrafedocumentiDTO> anagrafedocumentis = chiaveValoreBean.getValore();
	    for (AnagrafedocumentiDTO anagrafedocumenti : anagrafedocumentis) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(anagrafedocumenti.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		if (anagrafedocumenti.getDocumento() != null) {
		    movimentimailallegati.setDocumento(anagrafedocumenti.getDocumento());
		} else {
		    if (anagrafedocumenti.getCodiceOggetto() != null) {
			movimentimailallegati.setDocumento(anagrafedocumenti.getNomeFile());
		    }
		}
		if (StringUtils.isBlank(movimentimailallegati.getDocumento())) {
		    movimentimailallegati.setDocumento("NON DEFINITO");
		}
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	// Bonifico la lista documenti delle procure
	List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> _procureDocChiaveValoreBeans = documentiHelper.getIstanzeprocureList();
	for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> chiaveValoreBean : _procureDocChiaveValoreBeans) {
	    List<IstanzeprocureDTO> istanzeprocures = chiaveValoreBean.getValore();
	    for (IstanzeprocureDTO istanzeprocureDTO : istanzeprocures) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(istanzeprocureDTO.getCodiceOggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(istanzeprocureDTO.getNomeFile());
		if (StringUtils.isBlank(movimentimailallegati.getDocumento())) {
		    movimentimailallegati.setDocumento("NON DEFINITO");
		}
		movimentimailallegatis.add(movimentimailallegati);
		if (istanzeprocureDTO.getCodiceOggettoDocId() != null) {
		    movimentimailallegati = new Movimentimailallegati();
		    movimentimailallegati.setMovimentimail(movimentimail);
		    o = oggettiService.findById(new PkId(istanzeprocureDTO.getCodiceOggettoDocId()));
		    movimentimailallegati.setOggetto(o);
		    movimentimailallegati.setDocumento(istanzeprocureDTO.getNomeFileDocId());
		    if (StringUtils.isBlank(movimentimailallegati.getDocumento())) {
			movimentimailallegati.setDocumento("NON DEFINITO");
		    }
		    movimentimailallegatis.add(movimentimailallegati);
		}
	    }
	}
	// Bonifico la lista documenti delle cds
	List<ChiaveValoreBean<String, List<CdsattiDTO>>> _cdsDocChiaveValoreBeans = documentiHelper.getCdsattiList();
	for (ChiaveValoreBean<String, List<CdsattiDTO>> chiaveValoreBean : _cdsDocChiaveValoreBeans) {
	    List<CdsattiDTO> cdss = chiaveValoreBean.getValore();
	    for (CdsattiDTO cdsDTO : cdss) {
		movimentimailallegati = new Movimentimailallegati();
		movimentimailallegati.setMovimentimail(movimentimail);
		Oggetti o = oggettiService.findById(new PkId(cdsDTO.getCodiceoggetto()));
		movimentimailallegati.setOggetto(o);
		movimentimailallegati.setDocumento(cdsDTO.getNomefile());
		if (StringUtils.isBlank(movimentimailallegati.getDocumento())) {
		    movimentimailallegati.setDocumento("NON DEFINITO");
		}
		movimentimailallegatis.add(movimentimailallegati);
	    }
	}
	return movimentimailallegatis;
    }

    @Override
    protected void fixMergeEntityProperties(Movimentimailallegati entity) {

	Movimentimail movimentimail = movimentimailService.bindDomainObject(entity.getMovimentimail(), PkId.class, "id.codice");
	entity.setMovimentimail(movimentimail);
	populateMetadataFunzione(entity, movimentimail);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
    }

    private void populateMetadataFunzione(Movimentimailallegati entity, Movimentimail movimentimail) {

	if (movimentimail != null && movimentimail.getMovimento() != null) {
	    Movimenti movimento = movimentimail.getMovimento();
	    if (movimento != null && movimento.getId() != null && movimento.getId().getCodice() != null && entity.getOggetto() != null) {
		List<MetadatiBean> s = null;
		if (entity.getOggetto().getMetadatiTransient() != null) {
		    s = entity.getOggetto().getMetadatiTransient();
		}
		if (s == null) {
		    s = new ArrayList<MetadatiBean>();
		}
		MetadatiBean mdb = new MetadatiBean();
		mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_MOVIMENTO.getValue());
		mdb.setValore(String.valueOf(movimento.getId().getCodice()));
		s.add(mdb);
		if (movimento.getIstanza() != null && movimento.getIstanza().getId() != null && movimento.getIstanza().getId().getCodice() != null) {
		    mdb = new MetadatiBean();
		    mdb.setChiave(MetadatiFunzioneEnum.CREA_MD_ISTANZA.getValue());
		    mdb.setValore(String.valueOf(movimento.getIstanza().getId().getCodice()));
		    s.add(mdb);
		}
		entity.getOggetto().setMetadatiTransient(s);
	    }
	}
    }

    private void dataIntegration(Movimentimailallegati entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("MovimentimailallegatiService#dataIntegration: movimentimailallegati è nullo");
	}
	fixMergeEntityProperties(entity);
    }
}
