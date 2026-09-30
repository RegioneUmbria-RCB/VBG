package movimentiallegati;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;

public class MovimentiallegatiServiceFake implements MovimentiallegatiService {

    @Override
    public void update(Movimentiallegati entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public void delete(Movimentiallegati entity) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Movimentiallegati> findAll(Integer firstResult, Integer maxResult) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati findById(PkId id) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati bindDomainObject(Movimentiallegati entity, Class<?> idClass, String idPath) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Movimentiallegati entity) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Movimentiallegati> findByIstanza(int codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Movimentiallegati> findByIstanzaOggetto(int codiceIstanza, int codicemovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Movimentiallegati> findByMovimento(int codiceMovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Movimentiallegati> findByMovimento(int codiceMovimento, boolean escludiAllegatiSenzaOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insert(Movimentiallegati movimentiallegati) {

	// TODO Auto-generated method stub
    }

    @Override
    public int countByMovimento(Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public List<Movimentiallegati> findProvenientiDaSTC(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isExistAllegatiProvenientiDaSTC(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean existsProvenientiDaSTCPerMovimenti(Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void insertTrasformaInPdf(Integer codice) {

    }

    @Override
    public void insertTrasformaInPdf(Integer codiceMovimentoAllegato, Integer codiceOggetto) {

	// TODO Auto-generated method stub
    }

    @Override
    public void salvaDocumentoProtocollo(String idBase, Integer codiceMovimento, String descrizioneFile, boolean chekFileIsEsistente) {

	// TODO Auto-generated method stub
    }

    @Override
    public void salvaDocumentiProtocollo(Map<Integer, String> mappaGiaSalvati, List<AllegatoResponseType> allegatos, Integer codiceMovimento) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<Movimentiallegati> findByIstanzaAndExcludeMoviemento(Integer codiceIstanza, Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati findByOggetto(Integer codice) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati findUltimoMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati findMovimentiallegatiConOggettoByMovimenti(Integer codiceMovimento, Integer codiceoggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer createDocumentoConLink(Movimenti movimento, Integer codiceLetteraTipo, DocumentiHelper documentiHelper, Boolean isZipLogico) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceIstanza, Integer codiceMovFiglio,
	    String codiceTipoMovAllegati) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer createAndInsertMovimentoAllegato(Letteretipo lettera, Integer codiceIstanza, Integer codiceMovimento) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void flush() {

	// TODO Auto-generated method stub
    }

    @Override
    public Integer insertMultiFile(Movimentiallegati movimentiallegati, List<MultipartFile> lMultipartFiles) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Integer insertSingoloOrMultiFile(Movimentiallegati movimentiallegati, List<MultipartFile> lMultipartFiles) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Movimentiallegati findbyMessageId(String idmessage) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Oggetti applicaLayerProtocolloPdf(Movimenti mov, Oggetti o) throws FunzioneBusinessRemotaException, Exception {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void insertCreaAllegatoXmlDomandaSuapRegistroImprese(Integer codiceMovimento) {

	// TODO Auto-generated method stub
    }

    @Override
    public Oggetti applicaAnnotazioneProtocolloPdf(Istanze istanza, Movimenti mov, Oggetti o) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void updateApplicaQRCode(Integer codiceMovimentiAllegati) {

	// TODO Auto-generated method stub
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanzaNonInDocAutorizzazione(Integer codiceIstanza, Integer codiceAut) {

	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean isPresenteInDocAut(Integer codiceMovAllegato, Integer codiceAutorizzazione) {

	// TODO Auto-generated method stub
	return false;
    }

    public List<Movimentiallegati> findListByOggettoResult = new ArrayList<Movimentiallegati>();

    @Override
    public List<Movimentiallegati> findListByOggetto(Integer codice) {

	return findListByOggettoResult;
    }

    @Override
    public Set<String> findSoftwareByCodiceOggetto(Integer codiceOggetto) {

	return null;
    }

    @Override
    public TempLinkallegati populateTempLinkallegati(Integer codiceOggetto, String nomeFile, String UUID, String descrizioneDocumento) {

	// TODO Auto-generated method stub
	return null;
    }
}
