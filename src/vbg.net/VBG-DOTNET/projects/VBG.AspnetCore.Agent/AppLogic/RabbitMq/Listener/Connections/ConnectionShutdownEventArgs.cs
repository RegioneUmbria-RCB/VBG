using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace VBG.AspnetCore.Agent.AppLogic.RabbitMq.Listener.Connections
{
    public class ConnectionShutdownEventArgs
    {
        public bool RequestedByUser { get; }
        public ShutdownEventArgs ShutdownArgs { get; }

        public ConnectionShutdownEventArgs(bool requestedByUser, ShutdownEventArgs shutdownArgs)
        {
            this.RequestedByUser = requestedByUser;
            this.ShutdownArgs = shutdownArgs;
        }
    }
}
