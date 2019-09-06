using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.Pagamenti.ESED;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using log4net;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.ESED
{
    public class PagamentiESEDService
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string KO = "KO";
            public const string ESED = "ESED";
        }

        AreaRiservataServiceCreator _serviceCreator;
        PayServerClientWrapperESED _pagamentiService;

        ILog _log = LogManager.GetLogger(typeof(PagamentiESEDService));

        internal PagamentiESEDService(AreaRiservataServiceCreator serviceCreator, PayServerClientWrapperESED pagamentiService)
        {
            this._serviceCreator = serviceCreator;
            this._pagamentiService = pagamentiService;
        }

        public ESEDCommitMessage NotificaPagamento(string buffer, string idDomanda)
        {
            _log.InfoFormat("Creazione dati di notifica pagamento, buffer: {0}, id domanda: {1}", buffer, idDomanda);
            var datiPagamento = _pagamentiService.GetDatiDaNotificaPagamento(buffer);

            try
            {
                string xmlDatiPagamento = datiPagamento.ToXmlString();
                _log.Info("creazione del client web service per inserimento dati di notifica");
                using (var ws = _serviceCreator.CreateClient())
                {
                    _log.InfoFormat("chiamata a SalvaNotificaPagamentoESED, token: {0}, id domanda: {1}, numero operazione: {2}, xmldatipagamento: {3}, esito: {4}, dataoraordine: {5}", ws.Token, idDomanda, datiPagamento.NumeroOperazione, xmlDatiPagamento, datiPagamento.Esito, datiPagamento.DataOraOrdine);
                    var response = ws.Service.SalvaNotificaPagamentoESED(ws.Token, idDomanda, datiPagamento.NumeroOperazione, xmlDatiPagamento, datiPagamento.Esito, datiPagamento.DataOraOrdine, datiPagamento.IDOrdine, datiPagamento.IDTransazione, Constants.ESED);
                    if (response.Esito == Constants.KO)
                    {
                        _log.InfoFormat("chiamata a SalvaNotificaPagamentoESED non andata a buon fine, chiamata a GetDatiNotifica per vedere se i dati sono già presenti, numero operazione: {0}", datiPagamento.NumeroOperazione);
                        var responseDatiNotifica = ws.Service.GetDatiNotifica(ws.Token, datiPagamento.NumeroOperazione);

                        if (responseDatiNotifica.Esito == Constants.KO)
                        {
                            _log.InfoFormat("chiamata a GetDatiNotifica non andata a buon fine, numero operazione: {0}", datiPagamento.NumeroOperazione);
                            var errore = String.Format("ERRORE GENERATO DURANTE LA CHIAMATA CHE EFFETTUA IL SALVATAGGIO DELLA NOTIFICA, ERRORE: {0}", response.Errore);
                            _log.Error(errore);
                            throw new Exception(errore);
                        }
                        _log.InfoFormat("chiamata a GetDatiNotifica andata a buon fine, dati già presenti, numero operazione: {0}", datiPagamento.NumeroOperazione);
                    }
                }
                _log.Info("Creazione del messaggio di commit da inserire nella response");
                
                var commitMsg = datiPagamento.ToCommitMessage();

                return commitMsg;
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("ERRORE GENERATO DURANTE LA NOTIFICA: {0}", ex.ToString());
                return datiPagamento.ToCommitMessageError();
            }
        }


    }
}
