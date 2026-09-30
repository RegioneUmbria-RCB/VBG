using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using log4net;
using System;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.DomandeInBozza
{
    public class EventiDomandeInBozzaService : IEventiDomandeInBozzaService
    {
        private readonly IRabbitChannelFactory _rabbitChannelFactory;
        private readonly IConfigurazione<ConfigurazioneRabbitMQ> _configurazione;
        private readonly IProvenienzaDomandaInBozzaService _provenienzaDomandaInBozzaService;
        private readonly ILog _log = LogManager.GetLogger(typeof(EventiDomandeInBozzaService));

        public bool RabbitAbilitato => this._configurazione.Parametri.MessaggiRabbitAbilitati;

        public EventiDomandeInBozzaService(IRabbitChannelFactory rabbitChannelFactory, IConfigurazione<ConfigurazioneRabbitMQ> configurazione, IProvenienzaDomandaInBozzaService provenienzaDomandaInBozzaService)
        {
            this._rabbitChannelFactory = rabbitChannelFactory;
            this._configurazione = configurazione;
            this._provenienzaDomandaInBozzaService = provenienzaDomandaInBozzaService;
        }

        public void DomandaInBozzaModificata(string codiceFiscaleUtente, IDomandaOnlineReadInterface domanda)
        {
            if (!this._configurazione.Parametri.MessaggiRabbitAbilitati)
            {
                return;
            }

            this._log.Info($"Invio messaggio DomandaInBozzaModificata per la domanda {domanda.AltriDati.IdentificativoDomanda}");

            var messaggio = new MessaggioDomandaInBozzaModificata
            {
                Header = new Infrastructure.RabbitMessageHeader
                {
                    Alias = domanda.AltriDati.AliasComune,
                    // IdComune = this._aliasToIdComuneRepository.GetIdComuneDaAliasComune(domanda.AltriDati.AliasComune),
                    Software = domanda.AltriDati.Software,
                },
                Body = new DomandaInBozzaModificataMessageBody
                {
                    IdDomanda = domanda.AltriDati.IdPresentazione,
                    IdentificativoDomanda = domanda.AltriDati.IdentificativoDomanda,
                    CodiceFiscaleUtente = codiceFiscaleUtente,
                    Oggetto = domanda.AltriDati.Intervento?.Descrizione ?? "",
                    Provenienza = this._provenienzaDomandaInBozzaService.Provenienza,
                    Richiedente = domanda.Anagrafiche.GetRichiedente()?.ToString() ?? "",
                    TipoIntervento = domanda.AltriDati.DescrizioneLavori,
                    UltimaModifica = DateTime.Now,
                    Eliminabile = domanda.DomandaEliminabile,
                    Tags = domanda.Oneri.GetWarningsPagamenti().Select(x => new DomandaInBozzaTag
                    {
                        Tipo = "Info",
                        Messaggio = x
                    }).ToArray()
                }
            };

            var publisher = this._rabbitChannelFactory.CreatePublisher(this._configurazione.Parametri);

            publisher.Publish(MessaggioDomandaInBozzaModificata.TopicKey, messaggio);

        }

        public void DomandaInBozzaPresentata(IDomandaOnlineReadInterface domanda)
        {
            if (!this._configurazione.Parametri.MessaggiRabbitAbilitati)
            {
                return;
            }

            this._log.Info($"Invio messaggio MessaggioDomandaInBozzaPresentata per la domanda {domanda.AltriDati.IdentificativoDomanda}");

            var messaggio = new MessaggioDomandaInBozzaPresentata
            {
                Header = new Infrastructure.RabbitMessageHeader
                {
                    Alias = domanda.AltriDati.AliasComune,
                    // IdComune = this._aliasToIdComuneRepository.GetIdComuneDaAliasComune(domanda.AltriDati.AliasComune),
                    Software = domanda.AltriDati.Software,
                },
                Body = new DomandaInBozzaPresentataMessageBody
                {
                    IdDomanda = domanda.AltriDati.IdPresentazione,
                    IdentificativoDomanda = domanda.AltriDati.IdentificativoDomanda,
                    Provenienza = this._provenienzaDomandaInBozzaService.Provenienza
                }
            };

            var publisher = this._rabbitChannelFactory.CreatePublisher(this._configurazione.Parametri);

            publisher.Publish(MessaggioDomandaInBozzaPresentata.TopicKey, messaggio);
        }

        public void DomandaInBozzaEliminata(IDomandaOnlineReadInterface domanda)
        {
            if (!this._configurazione.Parametri.MessaggiRabbitAbilitati)
            {
                return;
            }

            this._log.Info($"Invio messaggio DomandaInBozzaEliminata per la domanda {domanda.AltriDati.IdentificativoDomanda}");

            var messaggio = new MessaggioDomandaInBozzaEliminata
            {
                Header = new RabbitMessageHeader
                {
                    Alias = domanda.AltriDati.AliasComune,
                    // IdComune = this._aliasToIdComuneRepository.GetIdComuneDaAliasComune(domanda.AltriDati.AliasComune),
                    Software = domanda.AltriDati.Software,
                },
                Body = new DomandaInBozzaEliminataMessageBody
                {
                    IdDomanda = domanda.AltriDati.IdPresentazione,
                    IdentificativoDomanda = domanda.AltriDati.IdentificativoDomanda,
                    Provenienza = this._provenienzaDomandaInBozzaService.Provenienza
                }
            };

            var publisher = this._rabbitChannelFactory.CreatePublisher(this._configurazione.Parametri);

            publisher.Publish(MessaggioDomandaInBozzaEliminata.TopicKey, messaggio);
        }
    }
}
