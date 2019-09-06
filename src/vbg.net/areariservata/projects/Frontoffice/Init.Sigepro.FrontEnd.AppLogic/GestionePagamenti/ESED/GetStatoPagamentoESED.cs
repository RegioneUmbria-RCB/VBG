using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Pagamenti.ESED;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED
{
    public class GetStatoPagamentoESED : IGetStatoPagamento
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string KO = "KO";
        }

        AreaRiservataServiceCreator _serviceCreator;
        ILog _log = LogManager.GetLogger(typeof(GetStatoPagamentoESED));

        internal GetStatoPagamentoESED(AreaRiservataServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public string GetDatiPagamento(string numeroOperazione)
        {

            try
            {
                using (var ws = _serviceCreator.CreateClient())
                {
                    _log.InfoFormat("chiamata a GetDatiNotifica, numero operazione: {0}", numeroOperazione);
                    var response = ws.Service.GetDatiNotifica(ws.Token, numeroOperazione);
                    _log.InfoFormat("Esito risposta web method GetDatiNotifica con parametro numero operazione = {0}: {1}", numeroOperazione, response.Esito);
                    if (response.Esito != Constants.OK)
                    {
                        var errore = String.Format("errore generato durante la chiamata che ottiene i dati di notifica dell'operazione {0}, errore: {1}", numeroOperazione, response.Errore);
                        throw new Exception(errore);
                    }

                    return response.Messaggio;
                }
            }
            catch (Exception ex)
            {
                _log.Error(ex.Message);
                throw new Exception(ex.Message);
            }
        }
    }
}
