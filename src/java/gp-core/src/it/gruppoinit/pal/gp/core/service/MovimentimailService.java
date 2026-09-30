package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;

import java.util.List;

public interface MovimentimailService extends BaseService<Movimentimail, PkId> {

    public List<Movimentimail> findByIstanza(Istanze istanza);

    public List<Movimentimail> findByMovimento(Movimenti movimento);

    public List<Movimentimail> findByCodiceMovimento(Integer codicemovimento);

    public List<Movimentimail> findByCodiceIstanza(Integer codiceistanza);

    /**
     * ricerca su movimentimail per idcomune e messageid
     * 
     * @author fabrizioc
     * @param messageId
     * @return
     */
    public List<Movimentimail> findByMessageId(String messageId);

    /**
     * inserisce un record(child) in MOVIMENTIMAIL collegato per ID_PADRE al record padre(entity)
     * 
     * @param entity
     * @param child
     */
    public void insertChildMessage(Movimentimail entity, Movimentimail child);

    /*    @Deprecated
        public void sendMail(Movimentimail entity, Boolean flgInvialinkallmail, Integer codiceLetteraTipoAllegati);*/
    public String sendMail2(Movimentimail entity, Boolean flgInvialinkallmail, Boolean flgZipLogico, Integer codiceLetteraTipoAllegati,
	    Integer accountId, String codiceComune) throws FunzioneBusinessRemotaException;

    /*    @Deprecated
    public void sendMail(Movimentimail entity);*/
    public String sendMail2(Movimentimail entity, Integer accountId, String codiceComune) throws FunzioneBusinessRemotaException;

    public boolean existsByMovimento(Integer codiceMovimento);

    /**
     * Recupera la lista delle mail collegate alla mail padre movimentimailPadre.id.codice = codiceMovimentoMailPadre
     * 
     * @param movimentimail
     * @return
     */
    public List<Movimentimail> findByMailPadre(Integer codiceMovimentoMailPadre);

    public List<Movimentimail> findByAccountId(Integer idAccount);

    public int countByAccountId(Integer idAccount);
}
