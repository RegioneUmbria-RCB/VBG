using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.SIGePro.Manager.DTO.IntegrazioneLDP;
using log4net;
using System;
using System.Linq;


namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.AreeUsoPubblicoLivorno
{
    public class AreeUsoPubblicoLivornoService : IAreeUsoPubblicoLivornoService
    {
        private readonly IConfigurazione<ParametriIntegrazioneLDP> _cfg;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly ILocalizzazioniService _localizzazioniService;
        private readonly LivornoSITServiceProxy _ldpServiceProxy;
        private readonly IntegrazioneLDPServiceCreator _areaRiservataServiceCreator;
        private readonly ITokenResolver _tokenResolver;
        private readonly ILog _log = LogManager.GetLogger(typeof(AreeUsoPubblicoLivornoService));

        public AreeUsoPubblicoLivornoService(IConfigurazione<ParametriIntegrazioneLDP> cfg, ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
                                        LivornoSITServiceProxy ldpServiceProxy,
                                        IntegrazioneLDPServiceCreator areaRiservataServiceCreator, ILocalizzazioniService localizzazioniService,
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
            var ldpCfg = this.GetDatiIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);
            var ldpDolqString = "";
            if (ldpCfg != null)
            {
                ldpDolqString = ldpCfg.LdpDolQString;
            }

            var sostituzioni = this.GetSostituzioni(domanda, ldpCfg);

            var url = sostituzioni.ApplicaA(this._cfg.Parametri.UrlCompilazioneDomanda);

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

        private ConfigurazioneAlberoprocLDPDto GetDatiIntervento(int idIntervento)
        {
            using (var ws = this._areaRiservataServiceCreator.CreateClient())
            {
                return ws.Service.GetConfigurazioneAlberoprocLDP(ws.Token, idIntervento);
            }
        }

        private ConfigurazioneAlberoprocLDPDto GetDatiInterventoDaCodiceIstanza(int codiceIstanza)
        {
            using (var ws = this._areaRiservataServiceCreator.CreateClient())
            {
                return ws.Service.GetConfigurazioneAlberoprocLDPDaCodiceIstanza(ws.Token, codiceIstanza);
            }
        }

        public string GetUrlCompilazioneMovimento(int codiceIstanza, string returnTo)
        {
            if (String.IsNullOrEmpty(returnTo))
            {
                throw new InvalidOperationException("Non è possibile utilizzare AreeUsoPubblicoLivornoService.GetUrlCompilazioneMovimento senza specificare il parametro returnTo");
            }

            var ldpCfg = this.GetDatiInterventoDaCodiceIstanza(codiceIstanza);
            var ldpDolqString = "";
            if (ldpCfg != null)
            {
                ldpDolqString = ldpCfg.LdpDolQString;
            }

            var sostituzioni = this.GetSostituzioniIntegrazioni(codiceIstanza, ldpCfg, returnTo);
            var url = sostituzioni.ApplicaA(this._cfg.Parametri.UrlPresentazioneIntegrazione);

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

        private SostituzioniUrlLDP GetSostituzioniIntegrazioni(int codiceIstanza, ConfigurazioneAlberoprocLDPDto ldpCfg, string returnTo)
        {
            var retVal = new SostituzioniUrlLDP
            {
                IdDomanda = codiceIstanza.ToString(),
                Token = this._tokenResolver.Token,
                ReturnTo = returnTo
            };

            if (ldpCfg != null)
            {
                retVal.TipologiaOccupazione = ldpCfg.TipologiaOccupazione.Codice;
                retVal.TipologiaPeriodo = ldpCfg.TipologiaPeriodo.Codice;
                retVal.TipologiaGeometria = ldpCfg.TipologiaGeometria.Codice;

            }

            return retVal;
        }

        private SostituzioniUrlLDP GetSostituzioni(DomandaOnline domanda, ConfigurazioneAlberoprocLDPDto ldpCfg)
        {
            return this.GetSostituzioni(domanda, ldpCfg, null);
        }

        private SostituzioniUrlLDP GetSostituzioni(DomandaOnline domanda, ConfigurazioneAlberoprocLDPDto ldpCfg, string returnTo)
        {


            var retVal = new SostituzioniUrlLDP
            {
                Alias = domanda.DataKey.IdComune,
                Token = this._tokenResolver.Token,
                TokenPartnerApp = String.Empty,    // Non più necessario perchè il comune usa l'sso
                IdDomanda = domanda.DataKey.IdPresentazione.ToString(),
                IdDomandaEsteso = domanda.DataKey.ToSerializationCode(),
                ReturnTo = returnTo,
                TipologiaGeometria = String.Empty,
                TipologiaOccupazione = String.Empty,
                TipologiaPeriodo = String.Empty
            };

            var localizzazione = domanda.ReadInterface.Localizzazioni.Indirizzi.FirstOrDefault();
            if (localizzazione != null)
            {

                var codiceViario = localizzazione.CodiceViario;

                if (string.IsNullOrEmpty(codiceViario))
                {
                    var strada = this._localizzazioniService.GetById(localizzazione.CodiceStradario);
                    codiceViario = strada.CodViario;
                }
                retVal.CodiceStrada = codiceViario;
                retVal.Civico = localizzazione.Civico;
            }

            if (domanda.ReadInterface.AltriDati.Intervento != null)
            {


                if (ldpCfg != null)
                {
                    retVal.TipologiaGeometria = ldpCfg.TipologiaGeometria.Codice;
                    retVal.TipologiaOccupazione = ldpCfg.TipologiaOccupazione.Codice;
                    retVal.TipologiaPeriodo = ldpCfg.TipologiaPeriodo.Codice;

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

            return retVal;
        }
    }
}
