using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti;
using Init.SIGePro.Manager.WsParmaPagamenti;
using log4net;
using PersonalLib2.Sql;
using SIGePro.Manager.Verticalizzazioni;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.PagamentiParma
{
    public class PagamentiParmaService
    {
        public static class Constants
        {
            public const int EsitoEmissioneAvvisoPagoPaOk = 1;
        }

        public string OverrideWebServiceUrl { get; set; } = "";
        public string OverrideWebServiceUser { get; set; } = "";
        public string OverrideWebServicePass { get; set; } = "";

        private readonly IVerticalizzazioniFactory _verticalizzazioniFactory;
        private readonly AuthenticationInfo _authInfo;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiParmaService));

        // Attenzione: questa classe viene utilizzata dalle formule! NON modificare il costruttore o i metodi senza sapere quello che si fa
        public PagamentiParmaService(IVerticalizzazioniFactory verticalizzazioniFactory, AuthenticationInfo authInfo)
        {
            this._verticalizzazioniFactory = verticalizzazioniFactory;
            this._authInfo = authInfo;
        }

        // Attenzione: questa classe viene utilizzata dalle formule! NON modificare il costruttore o i metodi senza sapere quello che si fa
        public DtoOutInserimentoEmissioni NotificaEmissioni(TipologiaCosapParma tipologia, int codiceIstanza, PeriodoCosap periodo, int totaleGioni, string datiOccupazione, IEnumerable<RataCosap> rate)
        {
            if (this._log.IsInfoEnabled)
            {
                var logMsg = $"Invocazione del servizio di notifica emissioni. tipologia={tipologia}, codiceIstanza={codiceIstanza}, periodo={periodo}, rate={{";

                rate.ToList().ForEach(r => logMsg += $"\r\nimporto={r.Importo}, data={r.DataScadenza}");

                logMsg += "}";

                this._log.Info(logMsg);
            }

            var istanza = this.CaricaDatiIstanza(codiceIstanza);

            var rateizzazione = new RateizzazioneEmissioniParma(tipologia, istanza, periodo, totaleGioni, datiOccupazione, rate);

            var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneCosapParma>(this._authInfo.Alias, istanza.SOFTWARE);
            var proxy = new PagamentiParmaProxy(verticalizzazione, this.OverrideWebServiceUrl, this.OverrideWebServiceUser, this.OverrideWebServicePass);

            return rateizzazione.Notifica(proxy);

        }

        private Istanze CaricaDatiIstanza(int codiceIstanza)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var istanza = new IstanzeMgr(db).GetById(this._authInfo.IdComune, codiceIstanza, useForeignEnum.Yes);
                istanza.Stradario = new IstanzeStradarioMgr(db).GetByCodiceIstanza(this._authInfo.IdComune, codiceIstanza);

                return istanza;
            }
        }

        // Attenzione: questa classe viene utilizzata dalle formule! NON modificare il costruttore o i metodi senza sapere quello che si fa
        public InserisciEmissionePagoPaResult InserisciEmissionePagoPa(TipologiaCosapParmaPagoPA tipologia, int codiceIstanza, PeriodoCosap periodo, int totaleGioni, string datiOccupazione, IEnumerable<RataCosap> rate, bool usaAzienda = false)
        {
            try
            {
                if (this._log.IsInfoEnabled)
                {
                    var logMsg = $"Invocazione del servizio di notifica emissioni pago PA. tipologia={tipologia}, codiceIstanza={codiceIstanza}, periodo={periodo}, rate={{";

                    rate.ToList().ForEach(r => logMsg += $"\r\nimporto={r.Importo}, data={r.DataScadenza}");

                    logMsg += "}";

                    this._log.Info(logMsg);
                }

                var istanza = this.CaricaDatiIstanza(codiceIstanza);

                var rateizzazione = new RateizzazioneEmissioniParmaPagoPA(tipologia, istanza, periodo, totaleGioni, datiOccupazione, rate, usaAzienda);

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneCosapParma>(this._authInfo.Alias, istanza.SOFTWARE);
                var proxy = new PagamentiParmaProxy(verticalizzazione, this.OverrideWebServiceUrl, this.OverrideWebServiceUser, this.OverrideWebServicePass);

                var esito = rateizzazione.NotificaPagoPa(proxy);

                this._log.Debug($"esito della chiamata: {esito.Esito} - {esito.descrizione}");

                if (esito.Esito != Constants.EsitoEmissioneAvvisoPagoPaOk)
                {
                    return new InserisciEmissionePagoPaResult(esito.Esito, esito.descrizione);
                }

                this._log.Debug($"Lettura del pdf generato dalla notifica");

                var reader = new EsitoNotificaPagoPAReader(esito);
                var service = new DocumentiIstanzaServiceFactory().CreateService(this._authInfo);
                var codiceOggetto = service.Allega(codiceIstanza, reader.DescrittoreFile, reader.FileData);

                return new InserisciEmissionePagoPaResult(reader);
            }
            catch (Exception ex)
            {
                this._log.Error($"Si è verificato un errore durante la notifica dell'emissione: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug("InserisciEmissionePagoPa terminato");
            }
        }

        // Attenzione: questa classe viene utilizzata dalle formule! NON modificare il costruttore o i metodi senza sapere quello che si fa
        public InserisciEmissionePagoPaResult InserisciEmissioneImpiantiPubblicitari(TipologiaCosapParmaPagoPA tipologia, int codiceIstanza, string descrizione, IEnumerable<RataCosap> rate, bool usaAzienda = false)
        {
            try
            {
                if (this._log.IsInfoEnabled)
                {
                    var logMsg = $"Invocazione del servizio di notifica emissioni Impianti pubblicitari. tipologia={tipologia}, descrizione={descrizione}, codiceIstanza={codiceIstanza}, rate=[";

                    rate.ToList().ForEach(r => logMsg += $"\r\n{{importo:{r.Importo}, data:{r.DataScadenza}}}");

                    logMsg += "]";

                    this._log.Info(logMsg);
                }

                var istanza = this.CaricaDatiIstanza(codiceIstanza);

                var rateizzazione = new RateizzazioneEmissioniParmaImpiantiPubblicitari(tipologia, istanza, descrizione, rate, usaAzienda);

                var verticalizzazione = this._verticalizzazioniFactory.Create<VerticalizzazioneCosapParma>(this._authInfo.Alias, istanza.SOFTWARE);
                var proxy = new PagamentiParmaProxy(verticalizzazione, this.OverrideWebServiceUrl, this.OverrideWebServiceUser, this.OverrideWebServicePass);

                var esito = rateizzazione.NotificaPagoPa(proxy);

                this._log.Debug($"esito della chiamata: {esito.Esito} - {esito.descrizione}");

                if (esito.Esito != Constants.EsitoEmissioneAvvisoPagoPaOk)
                {
                    return new InserisciEmissionePagoPaResult(esito.Esito, esito.descrizione);
                }

                this._log.Debug($"Lettura del pdf generato dalla notifica");

                var reader = new EsitoNotificaPagoPAReader(esito);
                var service = new DocumentiIstanzaServiceFactory().CreateService(this._authInfo);
                var codiceOggetto = service.Allega(codiceIstanza, reader.DescrittoreFile, reader.FileData);

                return new InserisciEmissionePagoPaResult(reader);
            }
            catch (Exception ex)
            {
                this._log.Error($"Si è verificato un errore durante la notifica dell'emissione: {ex}");

                throw;
            }
            finally
            {
                this._log.Debug("InserisciEmissionePagoPa terminato");
            }
        }
    }
}
