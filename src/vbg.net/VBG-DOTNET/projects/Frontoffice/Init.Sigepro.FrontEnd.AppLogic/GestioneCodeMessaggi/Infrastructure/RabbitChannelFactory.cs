using Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione;
using log4net;
using RabbitMQ.Client;
using System;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Infrastructure
{
    public class RabbitChannelFactory : IRabbitChannelFactory, IDisposable
    {
        private static class Constants
        {
            public const string ClientName = "domanda-on-line";
        }

        private static IConnection _rabbitConnection = null;
        private static readonly Object _lock = new Object();
        private readonly ILog _log = LogManager.GetLogger(typeof(RabbitPublisher));

        public IRabbitPublisher CreatePublisher(ConfigurazioneRabbitMQ configurazione)
        {
            if (_rabbitConnection == null)
            {
                lock (_lock)
                {
                    if (_rabbitConnection == null)
                    {
                        this.CreateConnection(configurazione);
                    }
                }
            }

            return new RabbitPublisher(_rabbitConnection, configurazione);
        }

        public void Dispose()
        {
            if (_rabbitConnection != null)
            {
                lock (_lock)
                {
                    _rabbitConnection.Close();
                    _rabbitConnection.Dispose();
                    _rabbitConnection = null;
                }
            }
        }

        private void CreateConnection(ConfigurazioneRabbitMQ configurazione)
        {
            this._log.Debug("Creazione di una nuova connessione verso Rabbit con i parametri: " +
                        $"HostName = {configurazione.Url}, " +
                        $"Port = {configurazione.Port}, " +
                        $"UserName = {configurazione.Username}, " +
                        $"AutomaticRecoveryEnabled = true");

            var factory = new ConnectionFactory
            {
                HostName = configurazione.Url,
                Port = configurazione.Port,
                UserName = configurazione.Username,
                Password = configurazione.Password,
                AutomaticRecoveryEnabled = true,
                ClientProvidedName = Constants.ClientName
            };

            _rabbitConnection = factory.CreateConnection();

            this._log.Debug("Connessione a Rabbit creata con successo");
        }
    }
}
