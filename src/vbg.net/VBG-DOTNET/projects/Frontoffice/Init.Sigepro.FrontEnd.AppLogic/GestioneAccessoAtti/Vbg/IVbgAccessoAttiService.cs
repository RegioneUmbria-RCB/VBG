using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg
{
    public interface IVbgAccessoAttiService
    {
        // event Action OnChange;

        IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe);
        void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, string uuidIstanza);
        int GetLivelloAccessoDocumenti(int idAccessoAtti, string uuidIstanza);
        IEnumerable<int> GetCodiciOggettoScaricabiliComeZip(int idAccessoAtti, string uuidIstanza);
        //bool IsAllegatoValido(IDocumentoIstanzaOggettoDiVerifica doc, int? livelloAccessoDocumenti);
        //void SetAllegatiValidi(List<IDocumentoIstanzaOggettoDiVerifica> documenti, int? livelloAccessoDocumenti);
        //IEnumerable<string> GetAllegatiValidi();
    }
}