using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg
{
    public interface IVbgAccessoAttiProxy
    {
        IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe);
        void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, string uuidIstanza);
        int GetLivelloAccessoDocumenti(int idAccessoAtti, string uuidIstanza);
    }
}
