package it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.migrazione.UpgrRiferimentiDettPosizione;

public interface IUpgrPosizioniDebitorieDAO extends BaseDAO {

    List<UpgrRiferimentiDettPosizione> getElencoPosizioniSenzaCodiceComuneSoftware();

    void aggiornaComuneESoftwarePosizioneDebitoria(UpgrRiferimentiDettPosizione posizione, ISoftwareComuneData softwareAndcomune);

    SoftwareComuneDataBean findInfoDettaglioPosizioneDebitoria(String idcomune, Integer idDettPosizioneDebitoria);

    SoftwareComuneDataBean findInfoByIdDettPosizioneDebitoriaManifestazioni(String idcomune, Integer idDettPosizioneDebitoria);
}
