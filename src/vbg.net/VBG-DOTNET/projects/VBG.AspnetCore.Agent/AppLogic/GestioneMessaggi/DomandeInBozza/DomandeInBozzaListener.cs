using VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza.Contracts;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Configuration;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;


namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi.DomandeInBozza
{
    public class DomandeInBozzaListener :
        RabbitMqBaseListenerService,
        IMessageHandler<DomandaInBozzaEliminataMessageBody>
    {
        private readonly ILogger<DomandeInBozzaListener> _logger;
        private readonly IDomandeInBozzaService _domandeInBozzaService;

        public DomandeInBozzaListener(RabbitSettings rabbitSettings, IServiceScopeFactory serviceScopeFactory, IConnectionInstance connection, ILogger<DomandeInBozzaListener> logger, IListenerStatusRegistry statusRegistry, IDomandeInBozzaService domandeInBozzaService) :
            base(rabbitSettings, serviceScopeFactory, connection, logger, statusRegistry)
        {
            this._logger = logger;
            this._domandeInBozzaService = domandeInBozzaService;
        }

        protected override string QueueName => "aspnet-backend-domanda-in-bozza";

        protected override void ConfigureRoutingKeys(IMessageMap map)
        {
            map.MapMessage<DomandaInBozzaEliminataMessageBody>(DomandaInBozzaEliminataMessageBody.ListeningRoutingKey);
        }

        public async Task<bool> HandleAsync(string messageId, MessaggioRabbit<DomandaInBozzaEliminataMessageBody> messaggio)
        {
            try
            {
                var alias = messaggio.Header.Alias;
                var idDomanda = messaggio.Body.IdDomanda;

                await this._domandeInBozzaService.EliminaDomandaAsync(alias, idDomanda.Value);

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}
