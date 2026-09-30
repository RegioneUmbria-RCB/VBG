using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.ESED;
using log4net;
using System;
using VBG.Pagamenti.Legacy.ESED;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED
{
    public class GetStatoPagamentoESED : IGetStatoPagamento
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string KO = "KO";
        }

        private readonly ESEDServiceCreator _serviceCreator;
        private readonly ILog _log = LogManager.GetLogger(typeof(GetStatoPagamentoESED));

        public GetStatoPagamentoESED(ESEDServiceCreator serviceCreator)
        {
            this._serviceCreator = serviceCreator;
        }

        public string GetDatiPagamento(string numeroOperazione)
        {

            try
            {
                using (var ws = this._serviceCreator.CreateClient())
                {
                    this._log.InfoFormat("chiamata a GetDatiNotifica, numero operazione: {0}", numeroOperazione);
                    var response = ws.Service.GetDatiNotifica(ws.Token, numeroOperazione);
                    this._log.InfoFormat("Esito risposta web method GetDatiNotifica con parametro numero operazione = {0}: {1}", numeroOperazione, response.Esito);
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
                this._log.Error(ex.Message);
                throw new Exception(ex.Message);
            }
        }
    }
}
