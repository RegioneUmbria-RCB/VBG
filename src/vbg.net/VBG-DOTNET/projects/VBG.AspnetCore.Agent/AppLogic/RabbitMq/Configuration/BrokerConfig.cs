namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Configuration
{
    public class BrokerConfig
    {
        public string? Server { get; set; }
        public ushort Port { get; set; } = 5672;
        public string? VirtualHost { get; set; }
        public string? Username { get; set; }
        public string? Password { get; set; }
    }
}
