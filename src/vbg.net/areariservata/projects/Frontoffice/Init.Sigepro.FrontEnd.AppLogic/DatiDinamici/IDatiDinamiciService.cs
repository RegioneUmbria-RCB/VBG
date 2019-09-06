using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IDatiDinamiciService
    {
        IEnumerable<IIstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza(int idDomanda);
        IEnumerable<DecodificaDTO> GetDecodificheAttive(string tabella);
        void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione);
    }
}
