using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.ESED;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using log4net;
using System;
using VBG.Pagamenti.Legacy.ESED;

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

        private readonly ESEDServiceCreator _serviceCreator;
        private readonly PayServerClientWrapperESED _pagamentiService;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiESEDService));

        internal PagamentiESEDService(ESEDServiceCreator serviceCreator, PayServerClientWrapperESED pagamentiService)
        {
            this._serviceCreator = serviceCreator;
            this._pagamentiService = pagamentiService;
        }

        public ESEDCommitMessage NotificaPagamento(string buffer, string idDomanda)
        {
            this._log.InfoFormat("Creazione dati di notifica pagamento, buffer: {0}, id domanda: {1}", buffer, idDomanda);
            var datiPagamento = this._pagamentiService.GetDatiDaNotificaPagamento(buffer);

            try
            {
                string xmlDatiPagamento = datiPagamento.ToXmlString();
                this._log.Info("creazione del client web service per inserimento dati di notifica");
                using (var ws = this._serviceCreator.CreateClient())
                {
                    this._log.InfoFormat("chiamata a SalvaNotificaPagamentoESED, token: {0}, id domanda: {1}, numero operazione: {2}, xmldatipagamento: {3}, esito: {4}, dataoraordine: {5}", ws.Token, idDomanda, datiPagamento.NumeroOperazione, xmlDatiPagamento, datiPagamento.Esito, datiPagamento.DataOraOrdine);
                    var response = ws.Service.SalvaNotificaPagamentoESED(ws.Token, idDomanda, datiPagamento.NumeroOperazione, xmlDatiPagamento, datiPagamento.Esito, datiPagamento.DataOraOrdine, datiPagamento.IDOrdine, datiPagamento.IDTransazione, Constants.ESED);
                    if (response.Esito == Constants.KO)
                    {
                        this._log.InfoFormat("chiamata a SalvaNotificaPagamentoESED non andata a buon fine, chiamata a GetDatiNotifica per vedere se i dati sono già presenti, numero operazione: {0}", datiPagamento.NumeroOperazione);
                        var responseDatiNotifica = ws.Service.GetDatiNotifica(ws.Token, datiPagamento.NumeroOperazione);

                        if (responseDatiNotifica.Esito == Constants.KO)
                        {
                            this._log.InfoFormat("chiamata a GetDatiNotifica non andata a buon fine, numero operazione: {0}", datiPagamento.NumeroOperazione);
                            var errore = String.Format("ERRORE GENERATO DURANTE LA CHIAMATA CHE EFFETTUA IL SALVATAGGIO DELLA NOTIFICA, ERRORE: {0}", response.Errore);
                            this._log.Error(errore);
                            throw new Exception(errore);
                        }
                        this._log.InfoFormat("chiamata a GetDatiNotifica andata a buon fine, dati già presenti, numero operazione: {0}", datiPagamento.NumeroOperazione);
                    }
                }
                this._log.Info("Creazione del messaggio di commit da inserire nella response");

                var commitMsg = datiPagamento.ToCommitMessage();

                return commitMsg;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("ERRORE GENERATO DURANTE LA NOTIFICA: {0}", ex.ToString());
                return datiPagamento.ToCommitMessageError();
            }
        }


    }
}
