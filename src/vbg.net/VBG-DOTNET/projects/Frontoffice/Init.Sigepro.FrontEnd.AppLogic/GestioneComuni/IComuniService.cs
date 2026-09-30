using Init.SIGePro.Manager.DTO.Comuni;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneComuni
{
    public interface IComuniService
    {
        DatiProvinciaCompatto GetProvinciaDaCodiceComune(string codiceComune);
        string GetPecComuneAssociato(string software, string codiceComune);
        [Obsolete("Utilizzare GetByCodiceComune")]
        DatiComuneCompatto GetDatiComune(string codiceComune);
        DatiComuneCompatto GetByCodiceComune(string codiceComune);
        IEnumerable<DatiComuneCompatto> FindComuneDaMatchParziale(string matchComune);
        IEnumerable<DatiProvinciaCompatto> FindProvinciaDaMatchParziale(string matchProvincia);
        DatiProvinciaCompatto GetDatiProvincia(string siglaProvincia);
        DatiComuneCompatto FindComuneDaNomeComune(string matchComune);
        DatiComuneCompatto? GetByCodiceIstat(string codiceIstat);
    }
}