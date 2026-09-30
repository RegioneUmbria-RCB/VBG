using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text.RegularExpressions;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener
{
    public interface IRabbitListener
    {
        Task StartListeningAsync();
        void MapRoutingKeyActions(Dictionary<string, Func<string, byte[], Task<bool>>> routingKeyActions);
    }

    public class RabbitListener : IRabbitListener
    {
        private readonly IChannel _channel;
        private readonly RabbitListenerOptions _options;
        private readonly ILogger _logger;
        private bool _started = false;
        private Dictionary<string, Func<string, byte[], Task<bool>>> _routingKeyActions = new();
        //{
        //    { @"^[\w-]+\.pratiche\.nuova", this.OnMessageReceivedAsync<NuovaPraticaRabbitMessage> },
        //    { @"^[\w-]+\.pratiche\.aggiornata", this.OnMessageReceivedAsync<PraticaAggiornataRabbitMessage> },
        //    { @"^[\w-]+\.pratiche\.eliminata", this.OnMessageReceivedAsync<PraticaEliminataRabbitMessage> },
        //    { @"^[\w-]+\.pratiche\.destinatari-aggiornati", this.OnMessageReceivedAsync<DestinatariPraticaAggiornatiRabbitMessage> }
        //};

        public bool AutoAck { get; private set; } = false;

        public RabbitListener(IChannel channel, RabbitListenerOptions options, ILogger logger)
        {
            this._channel = channel;
            this._options = options;
            this._logger = logger;
        }

        public async Task StartListeningAsync()
        {
            if (string.IsNullOrEmpty(this._options.ExchangeName))
            {
                throw new ArgumentException("Il valore non può essere vuoto", nameof(this._options.ExchangeName));
            }

            if (string.IsNullOrEmpty(this._options.ExchangeType))
            {
                throw new ArgumentException("Il valore non può essere vuoto", nameof(this._options.ExchangeType));
            }

            if (string.IsNullOrEmpty(this._options.QueueName))
            {
                throw new ArgumentException("Il valore non può essere vuoto", nameof(this._options.ExchangeType));
            }

            if (!this._options.RoutingKeys.Any())
            {
                throw new ArgumentException("Non sono state definite routing keys per il listener", nameof(this._options.RoutingKeys));
            }

            await this._channel.ExchangeDeclareAsync(
                exchange: this._options.ExchangeName,
                type: this._options.ExchangeType,
                durable: this._options.IsExchangeDurable);

            // "durable: true" e "exclusive: false" servono per lasciare i messaggi all'interno della coda in attesa che un consumer si colleghi ad essa (così facendo i messaggi inviati nella coda non vengono droppati)
            // "autoDelete: false" serve per evitare che la coda venga eliminata quando il consumer si chiude improvvisamente. Così i messaggi per cui non è stato inviato ack vengono rimessi nella coda
            await this._channel.QueueDeclareAsync(queue: this._options.QueueName, durable: true, exclusive: false, autoDelete: false);

            foreach (var key in this._options.RoutingKeys)
            {
                await this._channel.QueueBindAsync(
                    queue: this._options.QueueName,
                    exchange: this._options.ExchangeName,
                    routingKey: key);
            }

            this.AutoAck = this._options.AutoAck;

            var consumer = new AsyncEventingBasicConsumer(this._channel);
            consumer.ReceivedAsync += this.OnReceivedAsync;

            await this._channel.BasicConsumeAsync(
                    queue: this._options.QueueName,
                    autoAck: this.AutoAck,
                    consumer: consumer
            );

            this._started = true;
        }

        public void MapRoutingKeyActions(Dictionary<string, Func<string, byte[], Task<bool>>> routingKeyActions)
        {
            if (this._started)
            {
                throw new InvalidOperationException("Le RoutingKeyActions non possono essere configurate su un listener già avviato");
            }

            this._routingKeyActions = routingKeyActions;
        }

        private async Task OnReceivedAsync(object? sender, BasicDeliverEventArgs e)
        {
            byte[]? body = null;
            var routingKey = e.RoutingKey;
            var deliveryTag = e.DeliveryTag;
            var messageId = e.BasicProperties.MessageId;

            using (this._logger.BeginScope("Gestione del messaggio con id {@messageId} (RoutingKey: {@RoutingKey})", e.BasicProperties.MessageId, routingKey))
            {
                try
                {
                    try
                    {
                        body = e.Body.ToArray();
                    }
                    catch (Exception ex)
                    {
                        this._logger.LogError("Impossibile leggere il byte[] del body del messaggio: {@ex}", ex);

                        // TODO: spostare il messaggio in una coda di messaggi con errore?
                    }

                    if (body == null)
                    {
                        this._logger.LogInformation("Il body del messaggio è null, il messaggio con deliveryTag={@messageId} e routingKey={@routingKey} verrà ignorato", deliveryTag, routingKey);

                        await this.SendAckMessageAsync(e.DeliveryTag, e.BasicProperties.MessageId);

                        return;
                    }
                    else
                    {
                        this._logger.LogDebug("corpo messaggio serializzato {@body}", body);
                    }

                    foreach (var kvp in this._routingKeyActions)
                    {
                        if (Regex.IsMatch(routingKey, kvp.Key))
                        {
                            var result = await kvp.Value(messageId, body);

                            if (result)
                            {
                                await this.SendAckMessageAsync(deliveryTag, messageId);
                            }
                            else
                            {
                                this._logger.LogError("Il messaggio con deliveryTag={@deliverytag} e messageId={@messageId} non ha ottenuto un risultato positivo, verrà inviato un nack. Probabilmente si è verificato un errore, verificare i logs", deliveryTag, messageId);

                                await this.NackMessageAsync(e.DeliveryTag, e.BasicProperties.MessageId);
                            }

                            return;
                        }
                    }

                    this._logger.LogInformation("Non è stato trovato un gestore per il messaggio con routingKey {@routingKey}, il messaggio verrà ignorato", routingKey);
                    await this.SendAckMessageAsync(e.DeliveryTag, e.BasicProperties.MessageId);
                }
                catch (Exception ex)
                {
                    this._logger.LogError("Errore nel messaggio con id {@messageId} (RoutingKey={@RoutingKey}), lo coda potrebbe bloccarsi: {@exception}", e.BasicProperties.MessageId, routingKey, ex);

                    await this.NackMessageAsync(e.DeliveryTag, e.BasicProperties.MessageId);
                }
            }
        }

        protected async Task SendAckMessageAsync(ulong deliveryTag, string messageId)
        {
            var autoAck = this.AutoAck;

            if (!autoAck)
            {
                await this.BasicAckAsync(deliveryTag);

                this._logger.LogInformation("ack del messaggio (MessageId {@MessageId})(deliveryTag {@deliveryTag})", messageId, deliveryTag);
            }
        }

        private async Task BasicAckAsync(ulong deliveryTag)
        {
            if (this._channel is null || !this._channel.IsOpen)
            {
                this._logger.LogError("Tentativo di inviare un ack su un canale chiuso deliveryTag={@deliveryTag}", deliveryTag);
                return;
            }

            await this._channel.BasicAckAsync(deliveryTag: deliveryTag, multiple: false);
            this._logger.LogInformation("Ack per il messaggio con deliveryTag={@deliveryTag} inviato", deliveryTag);
        }

        private async Task NackMessageAsync(ulong deliveryTag, string messageId)
        {
            this._logger.LogError("Nack del messaggio con deliveryTag={@deliveryTag}, messageId={@messageId}. Il messaggio verrà riaccodato", deliveryTag, messageId);
            await this._channel.BasicNackAsync(deliveryTag: deliveryTag, multiple: true, true);
        }
    }
}
