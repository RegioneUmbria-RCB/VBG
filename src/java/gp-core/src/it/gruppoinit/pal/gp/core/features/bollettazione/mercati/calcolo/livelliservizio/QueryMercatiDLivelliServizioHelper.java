package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryMercatiDLivelliServizioHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryMercatiDLivelliServizioHelper.class);

    public String buildQuery() {

	String sql = "select" + //
		" mercati_d_livello_servizio.fk_mercato_d," + //
		" mercati_livello_servizio.fk_mercato_uso," + //
		" mercati_livello_servizio.data_inizio_validita," + //
		" mercati_livello_servizio.data_fine_validita," + //
		" mercati_d_livello_servizio.data_inizio," + //
		" mercati_d_livello_servizio.data_fine," + //
		" livello_servizio.segnaposto," + //
		" mercati_livello_servizio.tariffa," + //
		" sum(case mercati_d_livello_servizio.usa_mq_posteggio when 1 then mercati_d.superficie else mercati_d_livello_servizio.fattore_moltiplicativo end ) as quantita " + //
		"from" + //
		" mercati_d_livello_servizio" + //
		"  inner join mercati_d on" + //
		"    mercati_d.idcomune = mercati_d_livello_servizio.idcomune and" + //
		"    mercati_d.idposteggio = mercati_d_livello_servizio.fk_mercato_d" + //
		"  inner join mercati_livello_servizio on" + //
		"    mercati_livello_servizio.idcomune = mercati_d_livello_servizio.idcomune and" + //
		"    mercati_livello_servizio.id = mercati_d_livello_servizio.fk_merc_servizio" + //
		"  inner join livello_servizio on" + //
		"    livello_servizio.idcomune = mercati_livello_servizio.idcomune and" + //
		"    livello_servizio.id = mercati_livello_servizio.fk_servizio " + //
		"where" + //
		" mercati_d_livello_servizio.idcomune = ? and" + //
		" mercati_livello_servizio.attivo = ? and" + //
		" (" + //
		"   mercati_livello_servizio.data_inizio_validita <= ? and" + //
		"   mercati_livello_servizio.data_fine_validita >= ?" + //
		" ) and" + //
		" (" + //
		"   mercati_d_livello_servizio.data_inizio <= ? and" + //
		"   mercati_d_livello_servizio.data_fine >= ?" + //
		" ) " + //
		"group by" + //
		" mercati_d_livello_servizio.fk_mercato_d," + //
		" mercati_livello_servizio.fk_mercato_uso," + //
		" mercati_livello_servizio.data_inizio_validita," + //
		" mercati_livello_servizio.data_fine_validita," + //
		" mercati_d_livello_servizio.data_inizio," + //
		" mercati_d_livello_servizio.data_fine," + //
		" livello_servizio.segnaposto," + //
		" mercati_livello_servizio.tariffa";
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
