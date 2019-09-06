using Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti;
using System;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Siena
{
    public class SienaAccessoAttiService
    {
        private readonly ISienaAccessoAttiProxy _proxy;

        public SienaAccessoAttiService(ISienaAccessoAttiProxy proxy)
        {
            _proxy = proxy;
        }

        public IEnumerable<PraticaAccessoAtti> GetListaPratiche(int codiceAnagrafe)
        {
            return _proxy.GetListaPratiche(codiceAnagrafe);
        }

        public void LogAccessoPratica(int codiceAnagrafe, int idAccessoAtti, int codiceIStanza)
        {
            _proxy.LogAccessoPratica(codiceAnagrafe, idAccessoAtti, codiceIStanza);
        }
    }
}
