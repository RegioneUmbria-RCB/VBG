package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao;

import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public interface CommissioniToCdsDAO extends BaseDAO {

    public static enum TIPO_SOGGETTO_INVITATO {
	AMMINISTRAZIONE,
	ANAGRAFICA_RESPONSABILI
    }

    public Set<String> findInvitatiCommissioniPerIstanza(IUsefulDataForPlaceholderReplacement data, TIPO_SOGGETTO_INVITATO anagrafica);

    public Set<String> findIndirizziInvitatiCommissioniPerIstanza(IUsefulDataForPlaceholderReplacement data, TIPO_SOGGETTO_INVITATO amministrazione,
	    TipoFileEnum tipoFile);

    /**
     * Di cds ce ne può essere solamente una se ne trovo più allora prendo quella legata al movimento più recente
     * 
     * @param data
     * @return
     */
    public CommissioniedilizieT findUltimaCommissionePerIstanza(IUsefulDataForPlaceholderReplacement data);
    
    /**
     * Di cds ce ne può essere solamente una se ne trovo più allora prendo quella legata al movimento più recente
     * 
     * @param data
     * @return
     */
    public DataOraBean findConvocazioneUltimaCommissionePerIstanza(IUsefulDataForPlaceholderReplacement data);
}
