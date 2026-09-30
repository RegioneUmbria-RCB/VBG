using RabbitMQ.Client;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections
{
    public interface IConnectionInstance : IAsyncDisposable
    {
        event EventHandler<ConnectionShutdownEventArgs> ConnectionShutdown;

        Task<IChannel> CreateChannelAsync();
        Task ShutdownAsync();
    }
}
