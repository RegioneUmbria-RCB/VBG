package it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo;

import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;

public interface ICalcoloCostoPosteggiService {

    /**
     * Metodo che calcola il costo del posteggio ritornando una struttura di tipo {@link PosteggioImportoHelper} che
     * individua che contiene le informazioni di costo del posteggio
     * 
     */
    public PosteggioImportoHelper calcolaCostoPosteggio(MercatipresenzeD presenza, MercatiD posteggio, List<MercatiCfgAttivita> listMcfgAttivita,
	    Integer anno, int giorni, VwConcessioniattive concessioneAttiva, String contesto, String codiceIstatCoefficenteMerceologico,
	    Integer mercatiUsoId, Date dataDiRiferimento, MercatiFormuleCalcoloContestoEnum contestoFormula);
}
