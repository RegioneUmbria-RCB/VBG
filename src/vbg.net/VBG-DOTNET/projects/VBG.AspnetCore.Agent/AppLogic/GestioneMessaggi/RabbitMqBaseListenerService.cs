using System.Text;
using System.Text.Json;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Configuration;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi
{
    public interface IMessageMap
    {
        void MapMessage<T>(string routingKey) where T : class;
    }

    public abstract class RabbitMqBaseListenerService : RabbitMqService, IMessageMap
    {
        private record class RoutingKeyMap(string RoutingKey, string Regex, Func<string, byte[], Task<bool>> Handler);

        private readonly RabbitSettings _rabbitSettings;
        private readonly IServiceScopeFactory _serviceScopeFactory;
        private readonly ILogger _logger;
        private IRabbitListener? _listener;
        private readonly List<RoutingKeyMap> _routingKeyMappings = new();

        public RabbitMqBaseListenerService(RabbitSettings rabbitSettings, IServiceScopeFactory serviceScopeFactory, IConnectionInstance connection, ILogger logger, IListenerStatusRegistry statusRegistry)
            : base(connection, logger, statusRegistry)

        {
            this._rabbitSettings = rabbitSettings;
            this._serviceScopeFactory = serviceScopeFactory;
            this._logger = logger;
        }

        protected abstract string QueueName { get; }


        protected abstract void ConfigureRoutingKeys(IMessageMap map);

        protected async Task<bool> OnMessageReceivedAsync<T>(string messageId, byte[] body) where T : class
        {
            using (this._logger.BeginScope("Gestione del messaggio con id {@messageId} di tipo {@tipo} ", messageId, typeof(T)))
            {
                try
                {
                    var messaggio = this.DeserializzaMessaggioRabbit<T>(messageId, body);

                    if (messaggio == null)
                    {
                        this._logger.LogError("Impossibile serializzare il messaggio con id {@messageId}, il messaggio verrà ignorato", messageId);

                        return true;
                    }

                    using (var scope = this._serviceScopeFactory.CreateScope())
                    {
                        var svc = scope.ServiceProvider.GetService<IMessageHandler<T>>();

                        if (svc is null)
                        {
                            this._logger.LogError("Non è stato trovato nessun handler in grado di gestire il messaggio di tipo {@tipoMessaggio}", nameof(T));

                            throw new ApplicationException($"Non è stato trovato nessun handler in grado di gestire il messaggio di tipo {nameof(T)}");
                        }

                        return await svc.HandleAsync(messageId, messaggio);
                    }
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore durante la gestione del messaggio {@tipoMessaggio}: {@ex}. Dati del messaggio: {@messaggio}", nameof(T), ex, Encoding.UTF8.GetString(body));

                    return false;
                }
            }
        }

        public async Task StartAsync()
        {
            this.ConfigureRoutingKeys(this);

            var options = new RabbitListenerOptions
            {
                AutoAck = RabbitMQConstants.AutoAck,
                ExchangeName = this._rabbitSettings.ExchangeName,
                ExchangeType = RabbitMQConstants.ExchangeType,
                IsExchangeDurable = RabbitMQConstants.IsExchangeDurable,
                QueueName = this.QueueName,
                RoutingKeys = this._routingKeyMappings.Select(x => x.RoutingKey).ToArray()
            };

            this._listener = await this.CreateListenerAsync(options);

            var actionsMap = this._routingKeyMappings.ToDictionary(x => x.Regex, x => x.Handler);

            this._listener.MapRoutingKeyActions(actionsMap);

            await this._listener.StartListeningAsync();
        }

        protected MessaggioRabbit<T>? DeserializzaMessaggioRabbit<T>(string messageId, byte[] body) where T : class
        {
            try
            {
                var messageString = Encoding.UTF8.GetString(body);

                if (string.IsNullOrEmpty(messageString))
                {
                    this._logger.LogError("Il messaggio ricevuto è vuoto (messageId={@messageId})", messageId);

                    return null;
                }

                this._logger.LogInformation("Deserializzazione del messaggio {@messaggio}", messageString);

                var messaggio = JsonSerializer.Deserialize<MessaggioRabbit<T>>(messageString, new JsonSerializerOptions { PropertyNameCaseInsensitive = true });

                if (messaggio == null)
                {
                    this._logger.LogError("Impossibile deserializzare il messaggio con id {@messageId}. MessageBody={@messageString}", messageId, messageString);

                    return null;
                }

                if (messaggio.Body == null)
                {
                    this._logger.LogError("Il Body del messaggio con id id {@messageId} è vuoto . MessageBody={@messageString}", messageId, messageString);

                    return null;
                }

                return messaggio;
            }
            catch (Exception ex)
            {
                this._logger.LogError("Errore durante la deserializzazione del messaggio con id id {@messageId}: {@ex}. messageBody={@body}", messageId, ex, body);
            }

            return null;
        }

        public void MapMessage<T>(string routingKey) where T : class
        {
            var regex = this.MapRoutingKeyToRegex(routingKey);
            var handler = this.OnMessageReceivedAsync<T>;

            using (var scope = this._serviceScopeFactory.CreateScope())
            {
                using var loggingScope = this._logger.BeginScope("Gestione della routing key {@routingKey} per il messaggio {@tipoMessaggio}", routingKey, typeof(T));
                var svc = scope.ServiceProvider.GetService<IMessageHandler<T>>();

                if (svc is null)
                {
                    throw new ApplicationException($"Nessun handler configurato per gestire il messaggio {typeof(T)} per la routing key {routingKey} (implementare o configurare l'interfaccia (IMessageHandler<{typeof(T)}>)");
                }
            }

            this._routingKeyMappings.Add(new RoutingKeyMap(routingKey, regex, handler));
        }

        private string MapRoutingKeyToRegex(string routingKey)
        {
            var tmp = routingKey.Replace(".", @"\.");
            tmp = tmp.Replace("*", @"[\w-]+");

            return $"^{tmp}$";
        }
    }
}
