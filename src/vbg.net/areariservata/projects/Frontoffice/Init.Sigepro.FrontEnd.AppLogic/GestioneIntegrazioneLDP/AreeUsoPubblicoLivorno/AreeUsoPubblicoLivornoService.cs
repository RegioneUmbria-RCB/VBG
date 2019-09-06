using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using System;
using System.Linq;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class AreeUsoPubblicoLivornoService : IAreeUsoPubblicoLivornoService
    {
        IConfigurazione<ParametriIntegrazioneLDP> _cfg;
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        ILocalizzazioniService _localizzazioniService;
        LivornoSITServiceProxy _ldpServiceProxy;
        AreaRiservataServiceCreator _areaRiservataServiceCreator;
        ITokenResolver _tokenResolver;

        ILog _log = LogManager.GetLogger(typeof(AreeUsoPubblicoLivornoService));

        internal AreeUsoPubblicoLivornoService(IConfigurazione<ParametriIntegrazioneLDP> cfg, ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
                                        LivornoSITServiceProxy ldpServiceProxy,
                                        AreaRiservataServiceCreator areaRiservataServiceCreator, ILocalizzazioniService localizzazioniService,
                                        ITokenResolver tokenResolver)
        {
            this._cfg = cfg;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._ldpServiceProxy = ldpServiceProxy;
            this._areaRiservataServiceCreator = areaRiservataServiceCreator;
            this._localizzazioniService = localizzazioniService;
            this._tokenResolver = tokenResolver;
        }

        public string GetUrlCompilazioneDomanda(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var sostituzioni = new SostituzioniUrlLDP();

            sostituzioni.Alias = domanda.DataKey.IdComune;
            sostituzioni.Token = this._tokenResolver.Token;
            // sostituzioni.TokenPartnerApp = this._vbgAuthenticationService.GetTokenPartnerApp(token);
            sostituzioni.TokenPartnerApp = String.Empty;    // Non più necessario perchè il comune usa l'sso
            sostituzioni.IdDomanda = domanda.DataKey.IdPresentazione.ToString();
            sostituzioni.IdDomandaEsteso = domanda.DataKey.ToSerializationCode();
            sostituzioni.TipologiaGeometria = String.Empty;
            sostituzioni.TipologiaOccupazione = String.Empty;
            sostituzioni.TipologiaPeriodo = String.Empty;
            var localizzazione = domanda.ReadInterface.Localizzazioni.Indirizzi.FirstOrDefault();
            if (localizzazione != null)
            {

                var codiceViario = localizzazione.CodiceViario;

                if (string.IsNullOrEmpty(codiceViario))
                {
                    var strada = this._localizzazioniService.GetById(localizzazione.CodiceStradario);
                    codiceViario = strada.CodViario;
                }
                sostituzioni.CodiceStrada = codiceViario;
                sostituzioni.Civico = localizzazione.Civico;
            }

            string ldpDolqString = "";

            if (domanda.ReadInterface.AltriDati.Intervento != null)
            {
                var ldpCfg = GetDatiIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);

                if (ldpCfg != null)
                {
                    sostituzioni.TipologiaGeometria = ldpCfg.TipologiaGeometria.Codice;
                    sostituzioni.TipologiaOccupazione = ldpCfg.TipologiaOccupazione.Codice;
                    sostituzioni.TipologiaPeriodo = ldpCfg.TipologiaPeriodo.Codice;

                    ldpDolqString = ldpCfg.LdpDolQString;

                    this._log.DebugFormat("Parametri di integrazione LDP correnti: TipologiaGeometria={0}, TipologiaOccupazione={1}, TipologiaPeriodo={2}",
                        ldpCfg.TipologiaGeometria.Codice,
                        ldpCfg.TipologiaOccupazione.Codice,
                        ldpCfg.TipologiaPeriodo.Codice);
                }
                else
                {
                    this._log.DebugFormat("Per l'intervento selezionato ({0})non sono configurati parametri", domanda.ReadInterface.AltriDati.Intervento.Codice);
                }
            }
            else
            {
                this._log.DebugFormat("L'istanza non ha un intervento. Nell'url non verranno riportati i dati relativi all'integrazione di Livorno");
            }

            var url = sostituzioni.ApplicaA(_cfg.Parametri.UrlCompilazioneDomanda);

            if (!String.IsNullOrEmpty(ldpDolqString))
            {
                if (!ldpDolqString.StartsWith("&"))
                {
                    ldpDolqString = "&" + ldpDolqString;
                }

                url += ldpDolqString;
            }

            return url;
        }

        public void AggiornaDatiOccupazione(AggiornaDatiOccupazioneCommand cmd)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(cmd.IdDomanda);


            var datiLDP = this._ldpServiceProxy.GetDatiOccupazione(domanda.DataKey.ToSerializationCode());
            var datiOccupazione = datiLDP.IntervalloOccupazione;
            var stringaRipetizioni = datiLDP.StringaRipetizioni;

            // Elimino i vecchi valori
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.DataInizio.IdCampoData);
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.DataInizio.IdCampoOra);
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.DataFine.IdCampoData);
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.DataFine.IdCampoOra);
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.IdCampoDescrizioneOccupazione);
            domanda.WriteInterface.DatiDinamici.EliminaValoriCampo(cmd.IdCampoFrequenzaOccupazione);

            if (cmd.IdCampoFrequenzaOccupazione != -1)
            {
                domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.IdCampoFrequenzaOccupazione, 0, 0, stringaRipetizioni, stringaRipetizioni, "OSP_FREQUENZA_OCCUPAZIONE");
            }


            for (var i = 0; i < datiOccupazione.Count(); i++)
            {
                var dato = datiOccupazione.ElementAt(i);

                domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.DataInizio.IdCampoData, 0, i, dato.Inizio.ToString("yyyyMMdd"), dato.Inizio.ToString("dd/MM/yyyy"), "DATA_INIZIO_OCCUPAZIONE");
                domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.DataInizio.IdCampoOra, 0, i, dato.Inizio.ToString("HH:mm"), dato.Inizio.ToString("HH:mm"), "ORA_INIZIO_OCCUPAZIONE");

                domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.DataFine.IdCampoData, 0, i, dato.Fine.ToString("yyyyMMdd"), dato.Fine.ToString("dd/MM/yyyy"), "DATA_FINE_OCCUPAZIONE");
                domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.DataFine.IdCampoOra, 0, i, dato.Fine.ToString("HH:mm"), dato.Fine.ToString("HH:mm"), "ORA_FINE_OCCUPAZIONE");

                if (cmd.IdCampoDescrizioneOccupazione != -1)
                {
                    domanda.WriteInterface.DatiDinamici.AggiungiDatoDinamico(cmd.IdCampoDescrizioneOccupazione, 0, i, dato.Descrizione, dato.Descrizione, "DESCRIZIONE_OCCUPAZIONE");
                }
            }

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        private ConfigurazioneAlberoprocLDP GetDatiIntervento(int idIntervento)
        {
            using (var ws = this._areaRiservataServiceCreator.CreateClient())
            {
                return ws.Service.GetConfigurazioneAlberoprocLDP(ws.Token, idIntervento);
            }
        }
    }
}
