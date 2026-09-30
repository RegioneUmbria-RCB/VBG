package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public interface IIncapsulaPlaceholderValueByIfElseService {

    SostituzioneNelModello esegui(String placeholder, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData,
	    TipoFileEnum typeLettereTipoEnum);
}
