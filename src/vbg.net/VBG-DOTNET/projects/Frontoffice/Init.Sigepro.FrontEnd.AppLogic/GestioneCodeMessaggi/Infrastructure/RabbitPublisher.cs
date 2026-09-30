using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using log4net;
using RabbitMQ.Client;
using System;
using System.Text;
using System.Text.Json;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure
{
    public class RabbitPublisher : IRabbitPublisher
    {
        private readonly IConnection _rabbitConnection;
        private readonly ConfigurazioneRabbitMQ _configurazione;
        private readonly ILog _log = LogManager.GetLogger(typeof(RabbitPublisher));

        internal RabbitPublisher(IConnection rabbitConnection, ConfigurazioneRabbitMQ configurazione)
        {
            this._rabbitConnection = rabbitConnection;
            this._configurazione = configurazione;
        }

        public void Publish<T>(string topic, T data)
        {

            try
            {
                using (var channel = this._rabbitConnection.CreateModel())
                {
                    this._log.Debug("Canale verso Rabbit creato con successo");

                    channel.ExchangeDeclare(this._configurazione.ExchangeName, type: ExchangeType.Topic, durable: true);

                    var serialized = JsonSerializer.Serialize(data, new JsonSerializerOptions
                    {
                        PropertyNamingPolicy = JsonNamingPolicy.CamelCase
                    });

                    this._log.DebugFormat("Invio del messaggio con topic {0}, dati del messaggio: {1}", topic, serialized);

                    IBasicProperties props = channel.CreateBasicProperties();
                    props.MessageId = Guid.NewGuid().ToString();

                    channel.BasicPublish(
                        exchange: this._configurazione.ExchangeName,
                         routingKey: topic,
                         basicProperties: props,
                         body: Encoding.UTF8.GetBytes(serialized));

                    this._log.DebugFormat("Messaggio con topic {0}, inviato correttamente", topic);
                }
            }
            catch (Exception ex)
            {
                // L'operazione fallisce silenziosamente se ci sono errori di comunicazione con Rabbut.
                // In futuro va implementata una gestione dell'outbox per ovviare a questo problema
                this._log.Error($"Errore durante l'invio del messaggio con topic {topic} e corpo {data.ToJsonString()}: {ex}");
            }
        }
    }
}
