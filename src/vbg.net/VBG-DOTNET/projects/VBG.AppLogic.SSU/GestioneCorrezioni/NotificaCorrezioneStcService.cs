using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.STC.Adapter;
using Init.Sigepro.FrontEnd.AppLogic.STC.Service;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Microsoft.Extensions.Logging;
using Ninject.Activation;
using VBG.AppLogic.SSU.Configurazione;


namespace VBG.AppLogic.SSU.GestioneCorrezioni
{
    public class NotificaCorrezioneStcService
    {
        private class Constants
        {
            public const string CorrezionePratica = "CORREZIONE_PRATICA";
            public const string IntegrazionePratica = "INTEGRAZIONE_PRATICA";
        }

        private readonly ILogger<NotificaCorrezioneStcService> _logger;
        private readonly IIstanzaStcAdapter _istanzaStcAdapter;
        private readonly IOggettiService _oggettiService;
        private readonly IStcService _stcService;
        private readonly IConfigurazione<ParametriSsu> _configurazione;
        

        public NotificaCorrezioneStcService(ILogger<NotificaCorrezioneStcService> logger, IIstanzaStcAdapter istanzaStcAdapter, IOggettiService oggettiService, IStcService stcService, IConfigurazione<ParametriSsu> configurazione)
        {
            this._logger = logger;
            this._istanzaStcAdapter = istanzaStcAdapter;
            this._oggettiService = oggettiService;
            this._stcService = stcService;
            this._configurazione = configurazione;
        }

        public async Task<bool> NotificaIntegrazioneByIdDomandaAsync(DomandaOnline domandaAr)
        {
            try
            {
                this._logger.LogDebug($"Notifico a STC l'integrazione della domanda con id {domandaAr.DataKey.ToSerializationCode()}");

                var domandaStc = this._istanzaStcAdapter.Adatta(domandaAr);

                var domandaXml = domandaStc.ToXmlByteArray();

                var codiceOggettoXml = await this._oggettiService.InserisciOggettoAsync($"integrazione_domanda_{domandaAr.DataKey.ToSerializationCode()}.xml", "application/xml", domandaXml);

                var request = new NotificaAttivitaRequest
                {
                    datiAttivita = new DettaglioAttivitaType
                    {
                        idPratica = domandaAr.DataKey.ToSerializationCode(),
                        idAttivita = Constants.IntegrazionePratica,
                        tipoAttivita = new TipoAttivitaType
                        {
                            codice = Constants.IntegrazionePratica,
                            descrizione = Constants.IntegrazionePratica,
                        },
                        documenti =
                        [
                            new DocumentiType
                            {
                                id = codiceOggettoXml.ToString(),
                                documento = "Integrazione domanda",
                                tipoDocumento = "IntegrazioneDomanda",
                                allegati = new AllegatiType
                                {
                                    id = codiceOggettoXml.ToString(),
                                    allegato = $"integrazione_domanda_{domandaAr.DataKey.ToSerializationCode()}.xml"
                                }
                            }
                        ]
                    }
                };

                return NotificaAttivita(request);
            }
            catch (Exception ex)
            {
                this._logger.LogError($"Errore durante la notifica dell'integrazione a STC per la domanda con id {domandaAr.DataKey.ToSerializationCode()}: {ex}");
                return false;
            }

        }

        public async Task<bool> NotificaCorrezioneByIdDomandaAsync(DomandaOnline domandaAr) 
        {
            try
            {
                this._logger.LogDebug($"Notifico a STC la correzione della domanda con id {domandaAr.DataKey.ToSerializationCode()}");
                
                var domandaStc = this._istanzaStcAdapter.Adatta(domandaAr);

                var domandaXml = domandaStc.ToXmlByteArray();

                var codiceOggettoXml = await this._oggettiService.InserisciOggettoAsync($"correzione_domanda_{domandaAr.DataKey.ToSerializationCode()}.xml", "application/xml", domandaXml);

                var request = new NotificaAttivitaRequest
                {
                    datiAttivita = new DettaglioAttivitaType
                    {
                        idPratica = domandaAr.DataKey.ToSerializationCode(),
                        idAttivita = Constants.CorrezionePratica,
                        tipoAttivita = new TipoAttivitaType
                        {
                            codice = Constants.CorrezionePratica,
                            descrizione = Constants.CorrezionePratica,
                        },
                        documenti =
                        [
                            new DocumentiType
                            {
                                id = codiceOggettoXml.ToString(),
                                documento = "Correzione domanda",
                                tipoDocumento = "CorrezioneDomanda",
                                allegati = new AllegatiType
                                {
                                    id = codiceOggettoXml.ToString(),
                                    allegato = $"correzione_domanda_{domandaAr.DataKey.ToSerializationCode()}.xml"
                                }
                            }
                        ]
                    }
                };

                return NotificaAttivita(request);
            }
            catch (Exception ex)
            {
                this._logger.LogError($"Errore durante la notifica della correzione a STC per la domanda con id {domandaAr.DataKey.ToSerializationCode()}: {ex}");
                return false;
            }

        }

        private bool NotificaAttivita(NotificaAttivitaRequest request)
        {
            var response = this._stcService.NotificaAttivita(request, (sportello) =>
            {
                var sportelloDestinatario = this._configurazione.Parametri.GetSportelloDestinatario();
                sportello.idNodo = sportelloDestinatario!.idNodo;
                sportello.idSportello = sportelloDestinatario.idSportello;
                sportello.idEnte = sportelloDestinatario.idEnte;
            });

            if (response.Items.Any(x => x is ErroreType))
            {
                foreach (var errore in response.Items.OfType<ErroreType>())
                {
                    this._logger.LogError("La chiamata a NotificaAttivita ha restituito il seguente errore: numero errore {0}, descrizione: {1}", errore.numeroErrore, errore.descrizione);
                }
                return false;
            }

            return true;
        }
    }
}
