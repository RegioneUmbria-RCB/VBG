using Init.Sigepro.FrontEnd.AppLogic.AllegatiDomanda;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PresentazionePraticheEdilizieSiena;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDocumenti.LogicaSincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using log4net;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneIntegrazioneLDP.PraticheEdilizieSiena
{
    public class PraticheEdilizieSienaService : IPraticheEdilizieSienaService
    {
        IConfigurazione<ParametriIntegrazioneLDP> _cfg;
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        IAliasResolver _aliasResolver;
        LDPServiceProxy _ldpServiceProxy;
        IDownloadPdfDomanda _downloadPdfDomanda;
        IAllegatiDomandaFoRepository _allegatiDomandaFoRepository;
        ILogicaSincronizzazioneAllegatiIntervento _logicaSincronizzazioneAllegatiIntervento;
        AreaRiservataServiceCreator _areaRiservataServiceCreator;

        ILog _log = LogManager.GetLogger(typeof(PraticheEdilizieSienaService));

        internal PraticheEdilizieSienaService(IConfigurazione<ParametriIntegrazioneLDP> cfg, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, 
                                        LDPServiceProxy ldpServiceProxy, IDownloadPdfDomanda downloadPdfDomanda, 
                                        IAllegatiDomandaFoRepository allegatiDomandaFoRepository, 
                                        ILogicaSincronizzazioneAllegatiIntervento logicaSincronizzazioneAllegatiIntervento,
                                        AreaRiservataServiceCreator areaRiservataServiceCreator,
                                        IAliasResolver aliasResolver)
        {
            this._cfg = cfg;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._ldpServiceProxy = ldpServiceProxy;
            this._downloadPdfDomanda = downloadPdfDomanda;
            this._allegatiDomandaFoRepository = allegatiDomandaFoRepository;
            this._logicaSincronizzazioneAllegatiIntervento = logicaSincronizzazioneAllegatiIntervento;
            this._areaRiservataServiceCreator = areaRiservataServiceCreator;
            this._aliasResolver = aliasResolver;
        }

        public string GetUrlCompilazioneDomanda(string token, int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var sostituzioni = new SostituzioniUrlLDP();

            sostituzioni.Alias = domanda.DataKey.IdComune;
            sostituzioni.Token = token;
            // sostituzioni.TokenPartnerApp = this._vbgAuthenticationService.GetTokenPartnerApp(token);
            sostituzioni.TokenPartnerApp = String.Empty;    // Non più necessario perchè il comune usa l'sso
            sostituzioni.IdDomanda = domanda.DataKey.IdPresentazione.ToString();
            sostituzioni.IdDomandaEsteso = domanda.DataKey.ToSerializationCode();
            sostituzioni.TipologiaGeometria = String.Empty;
            sostituzioni.TipologiaOccupazione = String.Empty;
            sostituzioni.TipologiaPeriodo = String.Empty;

            if (domanda.ReadInterface.AltriDati.Intervento != null)
            {
                var ldpCfg = GetDatiIntervento(domanda.ReadInterface.AltriDati.Intervento.Codice);

                if (ldpCfg != null)
                {
                    sostituzioni.TipologiaGeometria = ldpCfg.TipologiaGeometria.Codice;
                    sostituzioni.TipologiaOccupazione = ldpCfg.TipologiaOccupazione.Codice;
                    sostituzioni.TipologiaPeriodo = ldpCfg.TipologiaPeriodo.Codice;

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

            return url;
        }

        public void AggiornaDatiLocalizzazione(int idDomanda)
        {
            try
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
                var idLocalizzazioni = domanda.ReadInterface.Localizzazioni.Indirizzi.Select(x => x.Id).ToArray();

                for (int i = 0; i < idLocalizzazioni.Count(); i++)
                {
                    domanda.WriteInterface.Localizzazioni.EliminaLocalizzazione(idLocalizzazioni[i]);
                }

                var indirizzi = this._ldpServiceProxy.GetDatiPratica(domanda.DataKey.ToSerializationCode());

                for (int i = 0; i < indirizzi.DatiLocalizzativi.Count(); i++)
                {
                    var ind = indirizzi.DatiLocalizzativi.ElementAt(i);

                    domanda.WriteInterface.Localizzazioni.AggiungiLocalizzazioneConRiferimentiCatastali(ind.Localizzazione, ind.RiferimentiCatastali);
                }

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Si è verificato un errore durante la lettura dei dati della domanda {1}: {0}", ex.ToString(), idDomanda);

                throw;
            }
        }

        public void AllegaPdfADomanda(int idDomanda, string nomeFilePdf)
        {
            if (String.IsNullOrEmpty(nomeFilePdf))
            {
                throw new ArgumentException("Non è stata specificata la descrizione del file che dovrà contenere il riepilogo dati");
            }

            try
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

                this._logicaSincronizzazioneAllegatiIntervento.Sincronizza(domanda);

                var documentiIntervento = domanda.ReadInterface.Documenti.Intervento.Documenti.Where( x => x.Descrizione == nomeFilePdf);
                var documentiEndo = domanda.ReadInterface.Documenti.Endo.Documenti.Where(x => x.Descrizione == nomeFilePdf);
                var listaDocumenti = documentiIntervento.Union(documentiEndo);
                
                // Se presente elimino il vecchio file
                foreach (var doc in listaDocumenti.Where(x => x.AllegatoDellUtente != null))
                {
                    domanda.WriteInterface.Documenti.EliminaAllegatoADocumentoDaCodiceOggetto(doc.AllegatoDellUtente.CodiceOggetto);
                }

                // Allego il nuovo file
                var allegato = this._downloadPdfDomanda.FromIdentificativoTemporaneo(domanda.DataKey.ToSerializationCode());

                foreach(var doc in listaDocumenti)
                {
                    var result = this._allegatiDomandaFoRepository.SalvaAllegato(idDomanda, allegato, false);

                    domanda.WriteInterface.Documenti.AllegaFileADocumento(doc.Id, result.CodiceOggetto, result.NomeFile, result.FirmatoDigitalmente);
                }

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante i ltentativo di allegare il riepilogo in pdf dei dati LDP. Nome allegato: {0}, Errore: {1}", nomeFilePdf, ex.ToString());

                throw;
            }
        }

        private ConfigurazioneAlberoprocLDP GetDatiIntervento(int idIntervento)
        {
            using (var ws = this._areaRiservataServiceCreator.CreateClient(this._aliasResolver.AliasComune))
            {
                return ws.Service.GetConfigurazioneAlberoprocLDP(ws.Token, idIntervento);
            }
        }

    }
}
