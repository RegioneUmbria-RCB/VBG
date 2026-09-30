package it.gruppoinit.pal.gp.core.documentiistanza;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCompareHelper;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.helper.DocumentiIstanzaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.RiepilogoHelper;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

public class DocumentiistanzaServiceFake implements DocumentiistanzaService {

    @Override
    public void insert(Documentiistanza entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void update(Documentiistanza entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Documentiistanza entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Documentiistanza> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Documentiistanza findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Documentiistanza bindDomainObject(Documentiistanza entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Documentiistanza entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Documentiistanza> findByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Documentiistanza> findByIstanzaOggetto(Integer codiceistanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void deleteDocumentiistanzas(List<Documentiistanza> documentiistanzas) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Documentiistanza> findProvenientiDaSTC(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void salvaDocumentoProtocollo(String idBase, Integer codiceIstanza, String descrizioneFile, boolean chekFileIsEsistente) {

	// TODO Auto-generated method stub
    }

    @Override
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceIstanza) {

	// TODO Auto-generated method stub
    }

    @Override
    public void updatePresente(Integer codiceDocIstanza, Boolean isCheked) {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateNecessario(Integer codiceDocIstanza, boolean necessario) {

	// TODO Auto-generated method stub
    }

    @Override
    public void updateAllineaDocumenti(Istanze istanza, List<CambioInterventoCompareHelper> docs) {

	// TODO Auto-generated method stub
    }

    @Override
    public void insertAllegatoInIstanza(Integer codiceIst, Integer codOggetto) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Documentiistanza> findByNome(Integer codiceIstanza, String nameSearch, FieldOperationsEnum searchMode) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public ByteArrayOutputStream downloadDocumentiZip(DocumentiHelper documentiHelper) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Documentiistanza> findByIstanzaAndOggetto(Integer codiceIstanza, Integer codiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer insertMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer insertSingoloOrMultiFile(Documentiistanza entity, List<MultipartFile> multipartFiles) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Documentiistanza updateAggiornaRiepilogo(Istanze istanza, byte[] content, String nomeFile) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer findOggettoArConsole(String valoreDaRicercare, Integer codice) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza, Boolean flgDaModelloDinamico, Boolean isCodiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanzaNonInDocAutorizzazione(Integer codiceistanza, Integer codiceautorizzazione) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Documentiistanza> findByIstanzaNomeFile(String nomefile, Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    private List<Documentiistanza> findByCodiceOggettoResult = null;

    public void setFindByCodiceOggettoResult(List<Documentiistanza> value) {

	this.findByCodiceOggettoResult = value;
    }

    @Override
    public List<Documentiistanza> findByCodiceOggetto(Integer codiceOggetto) {

	return this.findByCodiceOggettoResult;
    }

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	return null;
    }

    @Override
    public Integer insertDocumentoDaHelper(Integer codiceIstanza, DocumentiIstanzaRestHelper helper, InputStream documento) throws IOException {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<DocumentiistanzaDTO> findDocumentiistanzaDTOByIstanza(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public BaseEsitoOperazione updateSpostaCopiaDocumenti(Integer codiceIstanza, Integer codiceIstanzaDest, boolean isCopia,
	    Set<Integer> docIstanzaId) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public RiepilogoHelper rigeneraRiepilogo(Integer codiceistanza) throws Exception {

	// TODO Auto-generated method stub
	return null;
    }
}
