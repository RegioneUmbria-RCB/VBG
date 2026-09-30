using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneCodeMessaggi.Configurazione
{
    public class ConfigurazioneRabbitMQ : IParametriConfigurazione
    {
        public readonly string Url;
        public readonly int Port;
        public readonly string Username;
        public readonly string Password;
        public readonly string ExchangeName;
        public readonly bool MessaggiRabbitAbilitati;

        public ConfigurazioneRabbitMQ(bool messaggiRabbitAbilitati, string url, int port, string username, string password, string exchangeName)
        {
            this.MessaggiRabbitAbilitati = messaggiRabbitAbilitati;

            if (messaggiRabbitAbilitati)
            {
                if (port <= 0)
                {
                    throw new System.ArgumentException($"Porta di Rabbit non configurata correttamente: {port}", nameof(port));
                }

                this.Url = !string.IsNullOrEmpty(url) ? url : throw new System.ArgumentException($"Hostname di Rabbit non configurato", nameof(url));
                this.Port = port;
                this.Username = !string.IsNullOrEmpty(username) ? username : throw new System.ArgumentException($"Username di Rabbit non configurata", nameof(username));
                this.Password = !string.IsNullOrEmpty(password) ? password : throw new System.ArgumentException($"Password di Rabbit non configurata", nameof(password));
                this.ExchangeName = !string.IsNullOrEmpty(exchangeName) ? exchangeName : throw new System.ArgumentException($"ExchangeName di Rabbit non configurato", nameof(exchangeName));
            }
        }


    }
}
