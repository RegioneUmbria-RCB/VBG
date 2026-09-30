using Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici
{
    public interface IDatiDinamiciService
    {
        int? GetIdModelloDaCodice(string codiceModello);
        int? GetIdCampoDaNome(string nomeCampo);
        IEnumerable<IstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza(int idDomanda);
        IEnumerable<DecodificaDto> GetDecodificheAttive(string tabella);
        void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione);
        bool VerificaEsistenzaModelloDinamico(int idModello);
    }
}
