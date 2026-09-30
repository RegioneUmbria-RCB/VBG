package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.mercati;

import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;

public interface ReplaceVariabiliMercatoDAO {

    VariabiliMercatoResultBean sostituisciSegnaposto(Integer codiceIstanza, TipoFileEnum tipoFile);
}
