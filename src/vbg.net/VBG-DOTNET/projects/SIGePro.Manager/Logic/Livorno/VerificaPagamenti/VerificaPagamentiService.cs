using Init.SIGePro.Manager.Authentication;
using log4net;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti
{
    public class VerificaPagamentiService
    {
        private readonly AuthenticationInfo _authenticationInfo;
        private readonly string _software;
        private readonly VerticalizzazionePagamentiLivorno _verticalizzazione;
        private readonly ILog _log = LogManager.GetLogger(typeof(VerificaPagamentiService));

        public VerificaPagamentiService(IVerticalizzazioniFactory verticalizzazioniFactory, AuthenticationInfo authenticationInfo, string software)
        {
            this._authenticationInfo = authenticationInfo;
            this._software = software;

            this._verticalizzazione = verticalizzazioniFactory.Create<VerticalizzazionePagamentiLivorno>(this._authenticationInfo.Alias, this._software);

            if (!this._verticalizzazione.Attiva)
            {
                throw new InvalidOperationException("il modulo " + this._verticalizzazione.NomeVerticalizzazione + " non è attivo");
            }
        }

        public EsitoVerificaPagamento VerificaPagamento(string strCodiceIstanza, string codicePagamentoElettronico)
        {
            // todo: leggere dalla verticalizzazione l'ip del server e l'id del campo dinamico da verificare
            this._log.DebugFormat("Verifica di pagamento del codice {0}", codicePagamentoElettronico);

            try
            {
                var esitoVerificaWs = this.VerificaPagamentodaWebService(codicePagamentoElettronico);

                if (!esitoVerificaWs.Esito)
                {
                    this._log.ErrorFormat("Info verifica del codice pagamento {0} fallita, ragione: {1}", codicePagamentoElettronico, esitoVerificaWs.DescrizioneErrore);

                    return esitoVerificaWs;
                }

                var esitoVerificaSchede = this.VerificaUtilizzoCodiceSuSchede(strCodiceIstanza, codicePagamentoElettronico);

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
