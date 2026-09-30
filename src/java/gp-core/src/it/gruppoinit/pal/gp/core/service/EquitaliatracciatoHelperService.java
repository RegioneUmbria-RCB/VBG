package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.helper.EquitaliatracciatoHelper;

public interface EquitaliatracciatoHelperService {

    public String createRecordM00(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String progressivoMinutaAnno, String importoTotaleArticoloDiRuolo,
	    String nomeResponsabile, String cognomeResponsabile);

    public String createRecordM99(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String progressivoMinutaAnno, String totaleImponibile,
	    String importoTotaleArticoloDiRuolo, Integer numRecordM10, Integer numRecordM40, Integer numRecordM20, Integer numRecordM21,
	    Integer numRecordM22, Integer numRecordM23, Integer numRecordM30, Integer numRecordM50, Integer numRecordM51);

    public EquitaliatracciatoHelper createRecordM20(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanze, Anagrafe anagrafe, Integer progressivoRecord,
	    Integer numeroPartita, String presenzaCoobligati);

    public EquitaliatracciatoHelper createRecordM30(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanze, Integer progressivoRecord, Integer numeroPartita);

    public EquitaliatracciatoHelper createRecordM40(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanza, Integer progressivoRecord, Integer numeroRecordIstanza);

    public EquitaliatracciatoHelper createRecordM50(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String codiceTipoOnere, String codiceTipologiaSanzioneEquitalia,
	    Istanze istanza, Integer progressivoRecord, Integer numeroPartita, Integer progressivoOnere,boolean calcoladataDecorrenzaInteressi);

    /**
     * genera la stringa comune a tutti i record M20,M21,M30,M40,50,M51
     * 
     * @param equitaliaTracciatiCfg
     * @param tipoRecord
     * @param progressivo
     * @return
     */
    public String identificativoPartitaPerRecord(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanza, String tipoRecord, String progressivo,
	    String numeroPartita);

    public Movimenti isMovimentoPresente(String codiceTipoMovimento, Integer codiceIstanza);
}
