package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class DataOdierna extends SegnapostoTestualeBaseConValoreSingolo {

    @Override
    public String getNome() {

	return "DATAODIERNA";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	return FormatUtils.dateFormat(new Date());
    }
}
