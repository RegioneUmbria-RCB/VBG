using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using log4net;
using System.Collections.Generic;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.GestioneMessaggiRabbit.PosizioniDebitorie
{
    public class EventiPagamentiService : IEventiPagamentiService
    {
        private readonly IRabbitChannelFactory _rabbitChannelFactory;
        private readonly ILog _log = LogManager.GetLogger(typeof(EventiPagamentiService));
        private readonly IConfigurazione<ConfigurazioneRabbitMQ> _configurazione;

        public EventiPagamentiService(IConfigurazione<ConfigurazioneRabbitMQ> configurazione, IRabbitChannelFactory rabbitChannelFactory)
        {
            this._configurazione = configurazione;
            this._rabbitChannelFactory = rabbitChannelFactory;
        }

        public void DestinatariPendenzaAggiornati(PresentazioneIstanzaDataKey dataKey, string codiceFiscaleEnteCreditore, IEstremiPosizioneDebitoriaServer posizione, EstremiDomandaNodoPagamenti estremiDomanda)
        {
            if (!this._configurazione.Parametri.MessaggiRabbitAbilitati)
            {
                return;
            }

            this._log.Info($"Invio messaggio DestinatariPendenzaAggiornati per la domanda {dataKey.CodiceUnivocoDomanda}");

            var partitaIva = estremiDomanda.IntestatoAPersonaGiuridica ? estremiDomanda.SoggettoDebitore.Cfpi : "";

            var messaggio = new MessaggioDestinatariPendenzaAggiornati
            {
                Header = new RabbitMessageHeader
                {
                    Alias = dataKey.IdComune,
                    Software = dataKey.Software
                },
                Body = new DestinatariPendenzaAggiornatiMessageBody
                {
                    CfEnteCreditore = codiceFiscaleEnteCreditore,
                    RiferimentoClient = posizione.RiferimentoClient,
                    Uuid = posizione.UuidNodoPagamenti,
                    PartitaIVA = partitaIva,
                    Provenienza = "DOMANDA-ON-LINE",
                    CodiciFiscaliDestinatari = new List<string>()
                    {
                        dataKey.CodiceUtente
                    }
                }
            };

            if (this._log.IsDebugEnabled)
            {
                this._log.Debug($"Dati del messaggio: {messaggio.ToJsonString()}");
            }

            var publisher = this._rabbitChannelFactory.CreatePublisher(this._configurazione.Parametri);

            publisher.Publish(MessaggioDestinatariPendenzaAggiornati.TopicKey, messaggio);
        }


    }
}
