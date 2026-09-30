using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections
{

    public class ConnectionInstance : IConnectionInstance
    {
        public event EventHandler<ConnectionShutdownEventArgs>? ConnectionShutdown;

        private IConnection? _connection;
        private readonly ILogger<ConnectionInstance> _logger;
        private readonly IListenerStatusRegistry _statusRegistry;
        private bool _shuhtdownRequestedByUser = false;

        public ConnectionInstance(IConnection connection, ILogger<ConnectionInstance> logger, IListenerStatusRegistry statusRegistry)
        {
            this._connection = connection;
            this._logger = logger;
            this._statusRegistry = statusRegistry;
            this._connection.ConnectionShutdownAsync += this.OnConnectionShutdownAsync;
            this._connection.RecoveringConsumerAsync += (sernder, e) =>
            {
                this._logger.LogInformation("Connessione recuperata con successo");
                // Quando viene ripristinata una connessione anche tutti i listener veengono riattivati
                this._statusRegistry.ResetListenerStatus();

                return Task.CompletedTask;
            };
        }

        private Task OnConnectionShutdownAsync(object? sender, ShutdownEventArgs e)
        {
            if (!this._shuhtdownRequestedByUser)
            {
                this._logger.LogError("Connessione interrotta in maniera non attesa per il tipo {@typeName}: Exception={@exception}, Initiator={@initiator}, ReplyText={@replyText}",
                    this.GetType(), e.Exception, e.Initiator, e.ReplyText);
            }
            else
            {
                this._logger.LogInformation("Connessione interrotta su richiesta del chiamante per il tipo {@typeName}: Exception={@exception}, Initiator={@initiator}, ReplyText={@replyText}",
                    this.GetType(), e.Exception, e.Initiator, e.ReplyText);
            }

            this.ConnectionShutdown?.Invoke(sender, new ConnectionShutdownEventArgs(this._shuhtdownRequestedByUser, e));

            return Task.CompletedTask;
        }

        public async Task<IChannel> CreateChannelAsync()
        {
            if (this._connection is null)
            {
                throw new ObjectDisposedException(nameof(this._connection));
            }

            var model = await this._connection.CreateChannelAsync();

            return model;
        }

        public async Task ShutdownAsync()
        {
            this._shuhtdownRequestedByUser = true;

            if (this._connection != null && this._connection.IsOpen)
            {
                await this._connection.CloseAsync();
                this._connection.Dispose();
            }

            this._connection = null;
        }

        public async ValueTask DisposeAsync()
        {
            if (this._connection is not null)
            {
                await this.ShutdownAsync();
            }
        }
    }
}
