using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Siena
{
    public interface ISienaAccessoAttiProxy
    {
        IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe);
        void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, int codiceIStanza);
    }
}
