using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener;
using VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi
{
    public abstract class RabbitMqService
    {
        private readonly IConnectionInstance _connection;
        private readonly ILogger _logger;
        private readonly IListenerStatusRegistry _statusRegistry;
        private IChannel? _channel;

        protected RabbitMqService(IConnectionInstance connection, ILogger logger, IListenerStatusRegistry statusRegistry)
        {
            this._connection = connection;
            this._logger = logger;
            this._statusRegistry = statusRegistry;
        }

        protected async Task<IRabbitListener> CreateListenerAsync(RabbitListenerOptions options)
        {
            await this.CreateChannelAsync();

            this._statusRegistry.NotifyOnline(this.GetType().Name);

            this._channel.ChannelShutdownAsync += this._channel_ModelShutdownAsync;

            return new RabbitListener(this._channel, options, this._logger);
        }

        private async Task CreateChannelAsync()
        {
            if (this._channel is not null)
            {
                throw new Exception("Channel già inizializzato");
            }

            this._channel = await this._connection.CreateChannelAsync();
        }

        private Task _channel_ModelShutdownAsync(object? sender, ShutdownEventArgs e)
        {
            this._logger.LogInformation("Canale terminato per il tipo {@typeName}: Exception={@exception}, Initiator={@initiator}, ReplyText={@replyText}",
                    this.GetType(), e?.Exception, e?.Initiator, e?.ReplyText);

            this.OnShutdownInternal(e);

            return Task.CompletedTask;
        }

        private void OnShutdownInternal(ShutdownEventArgs e)
        {
            this._logger.LogError("Il canale {@typeName} passerà allo stato offline: Exception={@exception}, Initiator={@initiator}, ReplyText={@replyText}",
                    this.GetType(), e.Exception, e.Initiator, e.ReplyText);

            this._statusRegistry.NotifyOffline(this.GetType().Name, e?.ReplyText ?? "Non disponibile");

            this.OnShutdown();
        }

        /// <summary>
        /// Implementare questo metodo per rilasciare risorse interne o per effettuare operazioni
        /// prima che venga effettuato lo shutdown del canale
        /// </summary>
        protected virtual void OnShutdown() { }

        public async ValueTask ShutdownAsync()
        {
            if (this._channel != null && this._channel.IsOpen)
            {
                await this._channel.CloseAsync();
                this._channel.Dispose();
            }

            this._channel = null;
        }

    }
}
