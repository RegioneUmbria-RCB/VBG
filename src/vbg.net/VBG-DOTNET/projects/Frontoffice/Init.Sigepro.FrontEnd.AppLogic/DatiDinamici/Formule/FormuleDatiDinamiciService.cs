using Init.Sigepro.FrontEnd.AppLogic.WsVbgDatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Formule
{
    // ATTENZIONE, questa classe è utilizzata dalle formule (principalmente nel porto di Livorno)
    // Anche se i metodi risultano essere non referenziati sono tutti utilizzati
    public class FormuleDatiDinamiciService
    {
        private readonly IDatiDinamiciService _service;

        public FormuleDatiDinamiciService(IDatiDinamiciService service)
        {
            this._service = service;

        }

        public IEnumerable<IstanzeDyn2Dati> GetDyn2DatiByCodiceIstanza(int idDomanda)
        {
            return this._service.GetDyn2DatiByCodiceIstanza(idDomanda);
        }

        public IEnumerable<DecodificaDto> GetDecodificheAttive(string tabella)
        {
            return this._service.GetDecodificheAttive(tabella);
        }

        public void RecuperaDocumentiIstanzaCollegata(int codiceIstanzaOrigine, int idDomandaDestinazione)
        {
            this._service.RecuperaDocumentiIstanzaCollegata(codiceIstanzaOrigine, idDomandaDestinazione);
        }
    }
}
