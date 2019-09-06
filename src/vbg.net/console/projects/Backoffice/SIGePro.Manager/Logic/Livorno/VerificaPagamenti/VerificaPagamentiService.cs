using Init.SIGePro.Authentication;
using Init.SIGePro.Manager.Verticalizzazioni;
using log4net;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti
{
    public class VerificaPagamentiService
    {
        AuthenticationInfo _authenticationInfo;
        string _software;
        VerticalizzazionePagamentiLivorno _verticalizzazione;
        ILog _log = LogManager.GetLogger(typeof(VerificaPagamentiService));

        public VerificaPagamentiService(AuthenticationInfo authenticationInfo, string software)
        {
            this._authenticationInfo = authenticationInfo;
            this._software = software;

            this._verticalizzazione = new VerticalizzazionePagamentiLivorno(this._authenticationInfo.Alias, this._software);

            if (!this._verticalizzazione.Attiva)
            {
                throw new ConfigurationException("il modulo " + this._verticalizzazione.Nome + " non è attivo");
            }
        }

        public EsitoVerificaPagamento VerificaPagamento(string strCodiceIstanza, string codicePagamentoElettronico)
        {
            // todo: leggere dalla verticalizzazione l'ip del server e l'id del campo dinamico da verificare
            this._log.DebugFormat("Verifica di pagamento del codice {0}", codicePagamentoElettronico);

            try
            {
                var esitoVerificaWs = VerificaPagamentodaWebService(codicePagamentoElettronico);

                if (!esitoVerificaWs.Esito)
                {
                    this._log.ErrorFormat("Info verifica del codice pagamento {0} fallita, ragione: {1}", codicePagamentoElettronico, esitoVerificaWs.DescrizioneErrore);

                    return esitoVerificaWs;
                }

                var esitoVerificaSchede = VerificaUtilizzoCodiceSuSchede(strCodiceIstanza, codicePagamentoElettronico);

                if (!esitoVerificaSchede)
                {
                    this._log.ErrorFormat("Verifica del codice pagamento {0} fallita, ragione: il codice pagamento è utilizzato in una o più schede dinamiche", codicePagamentoElettronico);

                    return new EsitoVerificaPagamento
                    {
                        Esito = false,
                        Importo = 0.0f,
                        DescrizioneErrore = "Codice pagamento già utilizzato in un'altra domanda"
                    };
                }

                return esitoVerificaWs;
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante la verifica del codice pagamento {0}: {1}", codicePagamentoElettronico, ex.ToString());

                throw;
            }
        }

        private bool VerificaUtilizzoCodiceSuSchede(string strCodiceIstanza, string codicePagamentoElettronico)
        {
            var idCampoDinamico = this._verticalizzazione.IdCampoDinamico;
            var codiceIstanza = Convert.ToInt32(strCodiceIstanza);

            if (!idCampoDinamico.HasValue)
            {
                return true;
            }

            using (var db = this._authenticationInfo.CreateDatabase())
            {
                var mgr = new IstanzeDyn2DatiMgr(db);

                var codiciIstanza = mgr.GetCodiciIstanzaByCodicecampoEValore(this._authenticationInfo.IdComune, idCampoDinamico.Value, codicePagamentoElettronico);

                return codiciIstanza.Where(x => x != codiceIstanza).Count() == 0;
            }
        }

        private EsitoVerificaPagamento VerificaPagamentodaWebService(string codicePagamentoElettronico)
        {
            var ws = new PagamentiServiceProxy(this._verticalizzazione.WsUrl);

            var esito = ws.VerificavaliditaPagamento(codicePagamentoElettronico);

            if (esito.Pagamento == null || esito.Pagamento.Esiste != "SI")
            {
                return new EsitoVerificaPagamento
                {
                    Esito = false,
                    Importo = 0.0f,
                    DescrizioneErrore = "Codice pagamento non valido"
                };
            }

            return new EsitoVerificaPagamento
            {
                Esito = true,
                Importo = esito.Pagamento.ImportoAsFloat,
                DescrizioneErrore = String.Empty
            };
        }
    }
}
