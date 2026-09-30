using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.Logic.GestioneOneri;
using Init.SIGePro.Manager.WsRateizzazioniService;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.CalcoloOneri.Rateizzazioni
{
    public class ParametriRateizzazione
    {
        /*

        */
        public int TipoRateizzazione { get; }
        public DateTime DataInizioRateizzazione { get; }
        public DateTime? DataInizioInteressiLegali { get; }
        public decimal SpeseRateizzazioni { get; }
        public int CodiceIstanza { get; }
        public int IdIstanzeOneri { get; }

        public ParametriRateizzazione(int tipoRateizzazione, DateTime dataInizioRateizzazione, DateTime? dataInizioInteressiLegali, decimal speseRateizzazioni,
                                        int codiceIstanza, int idIstanzeOneri)
        {
            this.TipoRateizzazione = tipoRateizzazione;
            this.DataInizioRateizzazione = dataInizioRateizzazione;
            this.DataInizioInteressiLegali = dataInizioInteressiLegali;
            this.SpeseRateizzazioni = speseRateizzazioni;
            this.CodiceIstanza = codiceIstanza;
            this.IdIstanzeOneri = idIstanzeOneri;
        }
    }



    public class RateizzazioniService
    {
        private static class Constants
        {
            public const string BindingName = "rateizzazioniHttpBinding";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(RateizzazioniService));
        private readonly IBindingFactory _bindingFactory;
        private readonly IAuthenticationManager _authenticationManager;

        public RateizzazioniService(IBindingFactory bindingFactory, IAuthenticationManager authenticationManager)
        {
            this._bindingFactory = bindingFactory;
            this._authenticationManager = authenticationManager;
        }

        private RateizzazioniClient CreateClient()
        {
            this._log.DebugFormat("Inizializzazione del servizio di rateizzazione all'indirizzo {0} utilizzando il binding {1}", ParametriConfigurazione.Get.WsRateizzazioniServiceUrl, Constants.BindingName);

            var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsRateizzazioniServiceUrl);
            var binding = this._bindingFactory.CreateAndConfigure(Constants.BindingName);

            return new RateizzazioniClient(binding, endpoint);
        }

        public bool RateizzaOnere(string token, ParametriRateizzazione parametriRateizzazione, decimal importo, decimal importoIstruttoria)
        {
            var authInfo = this._authenticationManager.CheckToken(token);

            using (var db = authInfo.CreateDatabase())
            {
                var io = new IstanzeOneriMgr(db, authInfo).GetById(authInfo.IdComune, parametriRateizzazione.IdIstanzeOneri);
                var tipoCausale = Convert.ToInt32(io.FKIDTIPOCAUSALE);
                var flagEntrataUscita = io.FLENTRATAUSCITA;
                var dataOnere = io.DATA.GetValueOrDefault(DateTime.MinValue);
                var numeroDocumento = io.NR_DOCUMENTO;

                var tipoRateizzazione = parametriRateizzazione.TipoRateizzazione;
                var dataInizioRateizzazione = parametriRateizzazione.DataInizioRateizzazione;
                var dataInizioInteressiLegali = parametriRateizzazione.DataInizioInteressiLegali;
                var speseRateizzazioni = parametriRateizzazione.SpeseRateizzazioni;

                var rate = this.CalcolaRate(token, tipoRateizzazione, dataInizioRateizzazione, dataInizioInteressiLegali, importo, speseRateizzazioni);
                var rateIstruttoria = this.CalcolaRate(token, tipoRateizzazione, dataInizioRateizzazione, dataInizioInteressiLegali, importoIstruttoria, speseRateizzazioni);

                List<IstanzeOneri> listRate = new List<IstanzeOneri>();

                for (var i = 0; i < rate.Count(); i++)
                {
                    var elem = rate.ElementAt(i);

                    var istOneri = new IstanzeOneri
                    {
                        PREZZO = Convert.ToDouble(elem.Prezzo),
                        PREZZOISTRUTTORIA = Convert.ToDouble(rateIstruttoria.ElementAt(i).Prezzo),
                        DATASCADENZA = elem.DataScadenza,
                        CODICEISTANZA = parametriRateizzazione.CodiceIstanza.ToString(),
                        FKIDTIPOCAUSALE = tipoCausale.ToString(),
                        IDCOMUNE = authInfo.IdComune,
                        NR_DOCUMENTO = numeroDocumento,
                        FLENTRATAUSCITA = flagEntrataUscita,
                        DATA = dataOnere,
                        FlagOnereRateizzato = "1",
                        ImportoInteressi = elem.Interesse
                    };

                    listRate.Add(istOneri);
                }

                this.UpdateIstanzeOneri(authInfo, parametriRateizzazione, tipoCausale, listRate);

                return true;

            }
        }

        private void UpdateIstanzeOneri(AuthenticationInfo authInfo, ParametriRateizzazione parametriRateizzazione, int tipoCausale, IEnumerable<IstanzeOneri> listRate)
        {
            var oneriService = new OneriService(authInfo);

            using (var database = authInfo.CreateDatabase())
            {
                //Database.BeginTransaction();
                //Verifico se l'onere era collegato ad un record nella tabella ISTANZECALCOLOCANONI_O (ISTANZEONERI_CANONI  non viene gestita)
                var idTestataCanoni = new IstanzeCalcoloCanoniOMgr(database).GetIdTestataCanoneDaIdOnere(authInfo.IdComune, parametriRateizzazione.IdIstanzeOneri);

                IstanzeOneriMgr istanzeOneriMgr = new IstanzeOneriMgr(database, authInfo);
                //Cancello l'onere che viene rateizzato ed eventuali record collegati
                oneriService.Elimina(parametriRateizzazione.IdIstanzeOneri);

                listRate = oneriService.Inserisci(listRate);

                if (idTestataCanoni.HasValue)
                {
                    //Inserisco le rate in ISTANZEONERI ed ISTANZECALCOLOCANONI_O
                    new IstanzeCalcoloCanoniOMgr(database).InserisciOneri(authInfo.IdComune, idTestataCanoni.Value, listRate);
                }

                //Numerazione delle rate per data scadenza crescente
                //Lista delle rate da rinumerare
                //var ds = mgrio.GetListaOneri(authInfo.IdComune, parametriRateizzazione.CodiceIstanza, tipoCausale);
                var listaId = istanzeOneriMgr.GetListaIdNonPagatiDaTipoCausale(authInfo.IdComune, parametriRateizzazione.CodiceIstanza, tipoCausale);

                //Lista delle rate pagate
                var ioNumeroRata = new IstanzeOneri();
                ioNumeroRata.IDCOMUNE = authInfo.IdComune;
                ioNumeroRata.CODICEISTANZA = parametriRateizzazione.CodiceIstanza.ToString();
                ioNumeroRata.FKIDTIPOCAUSALE = tipoCausale.ToString();
                ioNumeroRata.OthersWhereClause.Add("DATAPAGAMENTO is not null");

                List<IstanzeOneri> list = istanzeOneriMgr.GetList(ioNumeroRata);

                var numeroRata = 0;

                if (list != null && list.Count > 0)
                {
                    numeroRata = list.Max(elem => String.IsNullOrEmpty(elem.NUMERORATA) ? 0 : Convert.ToInt32(elem.NUMERORATA));
                }

                foreach (var idOnere in listaId)
                {
                    numeroRata++;

                    istanzeOneriMgr.UpdateNumeroRata(authInfo.IdComune, idOnere, numeroRata);
                }
            }
        }


        public IEnumerable<DatiRateizzazione> CalcolaRate(string token, int tipoRateizzazione, DateTime dataRateizzazione, DateTime? dataInizioInteressiLegali, decimal importo, decimal speseRateizzazione)
        {
            try
            {
                using (var client = this.CreateClient())
                {

                    var rateizzazioniRequest = new RateizzazioniRequest
                    {
                        codiceOneriTipiRateizzazione = tipoRateizzazione,
                        data = dataRateizzazione,
                        dataInizio = dataInizioInteressiLegali,
                        importo = importo,
                        token = token
                    };

                    var result = client.Rateizzazioni(rateizzazioniRequest);

                    if (result == null || result.Length == 0)
                        return new List<DatiRateizzazione>();

                    var esitoList = new List<DatiRateizzazione>();
                    var esito = new EsitoRateizzazioni(result, importo, speseRateizzazione);

                    return esito.Rate;
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'invocazione del metodo Rateizzazioni del servizio di rateizzazione: {0}", ex.ToString());

                throw;
            }
        }
    }
}
