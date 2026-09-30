package it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.CommedrDocist;
import it.gruppoinit.pal.gp.core.domain.CommedrIstall;
import it.gruppoinit.pal.gp.core.domain.CommedrMovAll;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.CommissioniDettaglioDocumentiPratica;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.models.RiferimentiDocumentiSelezionati;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils.IFunzioneRaggruppamentoDocumenti;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils.RaggruppamentoIstanzeAllegatiDTO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.utils.RaggruppamentoMovimentiAllegatiDTO;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

@SuppressWarnings("rawtypes")
@Repository
public class CommissioniDocumentiPraticheDAOImpl extends BaseDAOImpl implements ICommissioniDocumentiPraticheDAO {

    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiallegatiService movimentiAllegatiService;
    @Autowired
    private IstanzeallegatiService istanzeAllegatiService;
    @Autowired
    private DocumentiistanzaService documentiIstanzaService;

    @Override
    public Istanze getIstanzaById(int codiceIstanza) {

	return this.istanzeService.findById(new PkId(codiceIstanza));
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommissioniedilizieT getCommissioneByIdCommissioneR(int idCommissioneR) {

	String hql = "select distinct commt " + //
		" from  CommissioniedilizieT commt " + //
		"  inner join commt.commissioniedilizieRs commr " + //
		" where commr.id.idcomune = ? " + //
		"  and commr.id.codice = ?";
	//
	List<CommissioniedilizieT> commt = getHibernateTemplate().find(hql, new Object[] { //
		ORMHelper.getIdcomune(), //
		idCommissioneR, //
	});
	return commt == null || commt.isEmpty() ? null : commt.get(0);
    }

    @Override
    public Collection<CommissioniDettaglioDocumentiPratica.RiferimentiDocumento> getDocumentiIstanzaByCodiceIstanza(int codiceIstanza) {

	List<DocumentiistanzaDTO> docIstDTO = new ArrayList<DocumentiistanzaDTO>();
	docIstDTO.addAll(documentiIstanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, Boolean.FALSE));
	docIstDTO.addAll(documentiIstanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, Boolean.TRUE));
	List<CommissioniDettaglioDocumentiPratica.RiferimentiDocumento> documenti = new ArrayList<CommissioniDettaglioDocumentiPratica.RiferimentiDocumento>();
	for (DocumentiistanzaDTO dto : docIstDTO) {
	    documenti.add(new CommissioniDettaglioDocumentiPratica.RiferimentiDocumento(dto.getId().getCodice(), dto.getDocumento(),
		    dto.getNomeFile(), dto.getCodiceOggetto()));
	}
	return documenti;
    }

    @Override
    public Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getIstanzeAllegatiByCodiceIstanza(int codiceIstanza) {

	List<IstanzeallegatiDTO> dtoList = this.istanzeAllegatiService.findIstanzeallegatiDTOByIstanza(codiceIstanza);
	return raggruppa(dtoList, new RaggruppamentoIstanzeAllegatiDTO());
    }

    @Override
    public Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> getMovimentiAllegatiByCodiceIstanza(int codiceIstanza) {

	List<MovimentiallegatiDTO> dtoList = movimentiAllegatiService.findMovimentiallegatiDTOByIstanza(codiceIstanza);
	return raggruppa(dtoList, new RaggruppamentoMovimentiAllegatiDTO());
    }

    private <T> Collection<CommissioniDettaglioDocumentiPratica.RaggruppamentoDocumenti> raggruppa(List<T> valori,
	    IFunzioneRaggruppamentoDocumenti<T> funzioneRaggruppamento) {

	return funzioneRaggruppamento.getGroup(valori);
    }

    @Override
    public RiferimentiDocumentiSelezionati getDocumentiSelezionatiByIdCommissioneRCodiceIstanza(int idCommissioneR, int codiceIstanza) {

	RiferimentiDocumentiSelezionati riferimenti = new RiferimentiDocumentiSelezionati();
	fillDocumentiIstanza(idCommissioneR, codiceIstanza, riferimenti);
	fillDocumentiEndo(idCommissioneR, codiceIstanza, riferimenti);
	fillDocumentiMovimenti(idCommissioneR, codiceIstanza, riferimenti);
	return riferimenti;
    }

    @SuppressWarnings("unchecked")
    private void fillDocumentiMovimenti(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati riferimenti) {

	String hql = "select movall.id.codice " + //
		"from CommedrMovAll allegati " + //
		" inner join allegati.commissioniedilizieR commr " + //
		" inner join allegati.istanze ist " + //
		" inner join allegati.movimentiallegati movall " + //
		"where allegati.id.idcomune = ? " + //
		" and commr.id.codice = ? " + //
		" and ist.id.codice = ? ";
	List<Integer> listaId = (List<Integer>) getHibernateTemplate().find(hql, new Object[] { //
		ORMHelper.getIdcomune(), //
		idCommissioneR, //
		codiceIstanza //
	});
	riferimenti.setDocumentiMovimenti(listaId);
    }

    @SuppressWarnings("unchecked")
    private void fillDocumentiEndo(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati riferimenti) {

	String hql = "select istall.id.codice " + //
		"from CommedrIstall allegati " + //
		" inner join allegati.commissioniedilizieR commr " + //
		" inner join allegati.istanze ist " + //
		" inner join allegati.istanzeallegati istall " + //
		"where allegati.id.idcomune = ? " + //
		" and commr.id.codice = ? " + //
		" and ist.id.codice = ? ";
	//
	List<Integer> listaId = (List<Integer>) getHibernateTemplate().find(hql, new Object[] { //
		ORMHelper.getIdcomune(), //
		idCommissioneR, //
		codiceIstanza //
	});
	riferimenti.setDocumentiEndo(listaId);
    }

    @SuppressWarnings("unchecked")
    private void fillDocumentiIstanza(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati riferimenti) {

	String hql = "select di.id.codice " + //
		"from CommedrDocist allegati " + //
		" inner join allegati.commissioniedilizieR commr " + //
		" inner join allegati.istanze ist " + //
		" inner join allegati.documentiistanza di " + //
		"where allegati.id.idcomune = ? " + //
		" and commr.id.codice = ? " + //
		" and ist.id.codice = ? ";
	List<Integer> listaId = (List<Integer>) getHibernateTemplate().find(hql, new Object[] { //
		ORMHelper.getIdcomune(), //
		idCommissioneR, //
		codiceIstanza //
	});
	riferimenti.setDocumentiIstanza(listaId);
    }

    @Override
    public Class getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void impostaDocumentiSelezionati(int idCommissioneR, int codiceIstanza, RiferimentiDocumentiSelezionati documentiSelezionati) {

	this.eliminaDocumenti(idCommissioneR);
	// Documenti dell'Istanza
	for (Integer id : documentiSelezionati.getDocumentiIstanza()) {
	    CommedrDocist c = CommedrDocist.createNewForInsert(idCommissioneR, codiceIstanza, id);
	    this.insert(c);
	}
	// Documenti degli endo
	for (Integer id : documentiSelezionati.getDocumentiEndo()) {
	    CommedrIstall c = CommedrIstall.createNewForInsert(idCommissioneR, codiceIstanza, id);
	    this.insert(c);
	}
	// Documenti dei movimenti
	for (Integer id : documentiSelezionati.getDocumentiMovimenti()) {
	    CommedrMovAll c = CommedrMovAll.createNewForInsert(idCommissioneR, codiceIstanza, id);
	    this.insert(c);
	}
    }

    @Override
    public int countDocumentiDellaCommissione(int idCommissioneR) {

	int count = 0;
	Session session = getSession();
	// Documenti dell'Istanza
	String sql = "select count(*) from commedr_docist where idcomune=? and fk_commedilizier=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrDocist.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	count += Integer.parseInt(q.uniqueResult().toString());
	// Documenti degli endo
	sql = "select count(*) from commedr_istall where idcomune=? and fk_commedilizier=?";
	q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrIstall.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	count += Integer.parseInt(q.uniqueResult().toString());
	// Documenti dei movimenti
	sql = "select count(*) from commedr_movall where idcomune=? and fk_commedilizier=?";
	q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrMovAll.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	count += Integer.parseInt(q.uniqueResult().toString());
	return count;
    }

    @Override
    public void eliminaDocumenti(int idCommissioneR) {

	Session session = getSession();
	// Documenti dell'Istanza
	String sql = "delete from commedr_docist where idcomune=? and fk_commedilizier=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrDocist.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	q.executeUpdate();
	// Documenti degli endo
	sql = "delete from commedr_istall where idcomune=? and fk_commedilizier=?";
	q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrIstall.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	q.executeUpdate();
	// Documenti dei movimenti
	sql = "delete from commedr_movall where idcomune=? and fk_commedilizier=?";
	q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrMovAll.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissioneR, new IntegerType());
	q.executeUpdate();
    }

    @Override
    public boolean existsByMovimentiAllegati(int idMovimentiAllegati) {

	Session session = getSession();
	String sql = "select count(*) from commedr_movall where idcomune=? and fk_movimentiallegati=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrMovAll.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idMovimentiAllegati, new IntegerType());
	return Integer.parseInt(q.uniqueResult().toString()) > 0;
    }

    @Override
    public boolean existsByDocumentiIstanza(int idDocumentiIstanza) {

	Session session = getSession();
	String sql = "select count(*) from commedr_docist where idcomune=? and fk_documentiistanza=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrMovAll.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idDocumentiIstanza, new IntegerType());
	return Integer.parseInt(q.uniqueResult().toString()) > 0;
    }

    @Override
    public boolean existsByIstanzeallegati(int idIstanzeAllegati) {

	Session session = getSession();
	String sql = "select count(*) from commedr_istall where idcomune=? and fk_istanzeallegati=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedrMovAll.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idIstanzeAllegati, new IntegerType());
	return Integer.parseInt(q.uniqueResult().toString()) > 0;
    }
}
