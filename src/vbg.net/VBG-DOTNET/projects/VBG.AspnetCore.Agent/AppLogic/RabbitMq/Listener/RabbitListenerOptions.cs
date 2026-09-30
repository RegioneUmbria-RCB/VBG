namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener
{
    public class RabbitListenerOptions
    {
        public required string ExchangeName { get; init; }
        public required string ExchangeType { get; init; }
        public required bool IsExchangeDurable { get; init; }
        public required string QueueName { get; init; }
        public required IEnumerable<string> RoutingKeys { get; init; } = Enumerable.Empty<string>();
        public required bool AutoAck { get; init; }
    }
}
