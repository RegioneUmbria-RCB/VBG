using VBG.SecurityLibrary.Services;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Configuration
{
    public class RabbitSettings
    {
        private readonly ISecurityService _securityService;

        private readonly string _host;
        private readonly string _port;
        private readonly string _username;
        private readonly string _password;

        public string ExchangeName { get; }

        public RabbitSettings(ISecurityService securityService)
        {
            this._securityService = securityService;

            this._host = this._securityService.GetParameter("RABBIT_HOSTNAME")!;
            this._port = this._securityService.GetParameter("RABBIT_PORT")!;
            this._username = this._securityService.GetParameter("RABBIT_USERNAME")!;
            this._password = this._securityService.GetParameter("RABBIT_PASSWORD")!;
            this.ExchangeName = this._securityService.GetParameter("RABBIT_EXCHANGE_NAME")!;
        }

        public BrokerConfig GetBrokerConfig()
        {
            var port = this._securityService.GetParameter("RABBIT_PORT");

            return new BrokerConfig()
            {
                Server = this._host,
                Port = !string.IsNullOrEmpty(this._port) ? ushort.Parse(this._port) : (ushort)0,
                Username = this._username,
                Password = this._password,
            };

        }
    }
}
