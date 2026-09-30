using log4net;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Terni
{
    public class TerniAnagrafeSearcher : AnagrafeSearcherBase
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(TerniAnagrafeSearcher));

        private static class Constants
        {
            public const string NomeAnagrafeSearcherSigepro = "DEFAULTANAGRAFESEARCHER";
            public const string WebServiceBindingName = "defaultHttpBinding";


            internal static class ChiaviVerticalizzazioni
            {
                public const string WebServiceUrl = "WS_URL";
                public const string Username = "USERNAME";
                public const string Password = "PASSWORD";
                public const string Ente = "ENTE";
                public const string UsaAnagrafeSigeproSeAnagraficaNonTrovata = "USA_ANAGRAFE_SIGEPRO";
            }
        }


        private string _webServiceUrl = String.Empty;
        private string _username = String.Empty;
        private string _password = String.Empty;
        private string _ente = String.Empty;

        private Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher _sigeproAnagrafeSearcher;


        public TerniAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory)
            : base(verticalizzazioniFactory, "TERNI")
        {
        }

        public override void Init()
        {
            this._webServiceUrl = this.Configuration[Constants.ChiaviVerticalizzazioni.WebServiceUrl].ToString();
            this._username = this.Configuration[Constants.ChiaviVerticalizzazioni.Username].ToString();
            this._password = this.Configuration[Constants.ChiaviVerticalizzazioni.Password].ToString();
            this._ente = this.Configuration[Constants.ChiaviVerticalizzazioni.Ente].ToString();

            if (this.Configuration[Constants.ChiaviVerticalizzazioni.UsaAnagrafeSigeproSeAnagraficaNonTrovata] == "1")
            {
                this._sigeproAnagrafeSearcher = new Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher(this._verticalizzazioniFactory, Constants.NomeAnagrafeSearcherSigepro);
                this._sigeproAnagrafeSearcher.InitParams(this.IdComune, this.Alias, this.SigeproDb);
            }
        }

        public override Init.SIGePro.Data.Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            return this.ByCodiceFiscaleImp(global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica, codiceFiscale);
        }

        public override Init.SIGePro.Data.Anagrafe ByCodiceFiscaleImp(Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona tipoPersona, string codiceFiscale)
        {
            using (var ws = this.CreateClient())
            {
                this._log.DebugFormat("Invocazione di ByCodiceFiscaleImp con codiceFiscale={0} e tipo persona={1}", codiceFiscale, tipoPersona);

                var tipoPersonaCivilia = tipoPersona == global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica ?
                                         AnagrafeCiviliaService.TipoPersona.PersonaFisica :
                                         AnagrafeCiviliaService.TipoPersona.PersonaGiuridica;

                AnagrafeCiviliaService.Anagrafe anagrafeCivilia = null;

                try
                {
                    anagrafeCivilia = ws.ByCodiceFiscaleETipoPersona(this._username,
                                                                           this._password,
                                                                           this._ente,
                                                                           tipoPersonaCivilia,
                                                                           codiceFiscale);
                }
                catch (Exception ex)
                {
                    // il web service solleva un'eccezione se l'anagrafica non è stata trovata
                    this._log.ErrorFormat("Eccezione sollevata dal web service di Civilia: {0}", ex.Message);
                }


                if (anagrafeCivilia != null)
                {
                    anagrafeCivilia.TIPOANAGRAFE = tipoPersona == global::Init.SIGePro.Manager.Logic.RicercheAnagrafiche.TipoPersona.PersonaFisica ? "F" : "G";
                }

                if (anagrafeCivilia == null && this._sigeproAnagrafeSearcher != null)
                {
                    this._log.Debug("La ricerca non ha restituito risultati, verrà effettuata una ricerca tra le anagrafiche di sigepro");

                    var anagrafeSigepro = this._sigeproAnagrafeSearcher.ByCodiceFiscaleImp(codiceFiscale);

                    if (anagrafeSigepro != null)
                    {
                        this._log.Debug("L'anagrafica è stata trovata in Sigepro");

                        return anagrafeSigepro;
                    }

                    this._log.Debug("L'anagrafica non è stata trovata in Sigepro");
                }


                return this.Adatta(anagrafeCivilia);
            }
        }

        public override Init.SIGePro.Data.Anagrafe ByPartitaIvaImp(string partitaIva)
        {
            using (var ws = this.CreateClient())
            {
                this._log.DebugFormat("Invocazione di ByPartitaIvaImp con partitaIva={0}", partitaIva);

                var anagrafeCivilia = ws.ByPartitaIva(this._username,
                                                        this._password,
                                                        this._ente,
                                                        partitaIva);

                if (anagrafeCivilia == null && this._sigeproAnagrafeSearcher != null)
                {
                    this._log.Debug("La ricerca non ha restituito risultati, verrà effettuata una ricerca tra le anagrafiche di sigepro");

                    var anagrafeSigepro = this._sigeproAnagrafeSearcher.ByPartitaIvaImp(partitaIva);

                    if (anagrafeSigepro != null)
                    {
                        this._log.Debug("L'anagrafica è stata trovata in Sigepro");

                        return anagrafeSigepro;
                    }

                    this._log.Debug("L'anagrafica non è stata trovata in Sigepro");
                }

                return this.Adatta(anagrafeCivilia);
            }
        }

        public override List<Init.SIGePro.Data.Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            throw new NotImplementedException();
        }

        private AnagrafeCiviliaService.WsAnagrafePortTypeClient CreateClient()
        {
#if NET48_OR_GREATER
            var binding = new BasicHttpBinding(Constants.WebServiceBindingName);
#endif
#if NET9_0_OR_GREATER
            var binding = new BasicHttpBinding();
#endif

            var endpoint = new EndpointAddress(this._webServiceUrl);

            var client = new AnagrafeCiviliaService.WsAnagrafePortTypeClient(binding, endpoint);

            return client;
        }

        private Init.SIGePro.Data.Anagrafe Adatta(AnagrafeCiviliaService.Anagrafe anagrafeCivilia)
        {
            if (anagrafeCivilia == null)
                return null;

            if (!String.IsNullOrEmpty(anagrafeCivilia.COMUNERESIDENZA))
                anagrafeCivilia.COMUNERESIDENZA = this.CodiceComuneDaCodiceIstat(anagrafeCivilia.COMUNERESIDENZA);

            if (!String.IsNullOrEmpty(anagrafeCivilia.CODCOMNASCITA))
                anagrafeCivilia.CODCOMNASCITA = this.CodiceComuneDaCodiceIstat(anagrafeCivilia.CODCOMNASCITA);

            var rVal = new Init.SIGePro.Data.Anagrafe
            {
                NOMINATIVO = anagrafeCivilia.NOMINATIVO,
                NOME = anagrafeCivilia.NOME,
                INDIRIZZO = anagrafeCivilia.INDIRIZZO,
                CAP = anagrafeCivilia.CAP,
                CODICEFISCALE = anagrafeCivilia.CODICEFISCALE,
                PARTITAIVA = anagrafeCivilia.PARTITAIVA,
                DATANASCITA = anagrafeCivilia.DATANASCITA,
                SESSO = anagrafeCivilia.SESSO,
                COMUNERESIDENZA = anagrafeCivilia.COMUNERESIDENZA,
                CODCOMNASCITA = anagrafeCivilia.CODCOMNASCITA,
                TIPOANAGRAFE = anagrafeCivilia.TIPOANAGRAFE
            };

            return rVal;

        }

        private string CodiceComuneDaCodiceIstat(string codiceIstat)
        {
            var comuniMgr = new ComuniMgr(this.SigeproDb);

            var comune = comuniMgr.GetByCodiceIstat(codiceIstat);

            if (comune == null)
            {
                this._log.ErrorFormat("Impossibile trovare il codice istat " + codiceIstat + " letto durante la richiesta anagrafica");
                return string.Empty;
            }

            return comune.CODICECOMUNE;
        }
    }
}
