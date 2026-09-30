using System.Collections.Concurrent;

namespace VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi
{
    public class ListenerStatusRegistry : IListenerStatusRegistry
    {


        private readonly ConcurrentDictionary<string, ChannelStatus> _registry = new();
        private readonly ILogger<ListenerStatusRegistry> _logger;

        public ListenerStatusRegistry(ILogger<ListenerStatusRegistry> logger)
        {
            this._logger = logger;
        }

        public void DumpStatus()
        {
            Console.WriteLine($"{this._registry.Count} Listeners registrati");
            Console.WriteLine($"--------------------------------");

            int maxlen = this._registry.Select(x => x.Key.Length).Max() + 2;

            foreach (var item in this._registry)
            {
                int pads = Math.Max(maxlen - item.Key.Length, 0);

                Console.Write(item.Key);
                Console.Write(":".PadRight(pads));

                this.WriteStatus(item.Value.StatusEnum, item.Value.Reason);
            }
        }

        private void WriteStatus(ListenerStatusEnum status, string reason)
        {
            ConsoleColor oldColor = Console.ForegroundColor;

            Console.ForegroundColor = status switch
            {
                ListenerStatusEnum.Online => ConsoleColor.Green,
                ListenerStatusEnum.Offline => ConsoleColor.Red,
                _ => oldColor
            };

            Console.WriteLine(status);

            if (!String.IsNullOrEmpty(reason))
            {
                Console.WriteLine($" ({reason})");
            }

            Console.ForegroundColor = oldColor;
        }

        public void NotifyOffline(string serviceName, string reason)
        {
            this._logger.LogError("Il servizio {@serviceName} è passato allo stato offline: {@reason}", serviceName, reason);

            this.NotifyStatusChange(serviceName, ListenerStatusEnum.Offline, reason);
        }

        public void NotifyOnline(string serviceName)
        {
            this.NotifyStatusChange(serviceName, ListenerStatusEnum.Online);
        }

        private void NotifyStatusChange(string name, ListenerStatusEnum newStatus, string reason = "")
        {
            this._registry[name] = new ChannelStatus(newStatus, reason);

            Console.Write($"{name} è ");

            this.WriteStatus(newStatus, reason);
        }

        public IEnumerable<ServiceStatus> GetOfflineServices()
        {
            foreach (var service in this._registry)
            {
                if (service.Value.StatusEnum != ListenerStatusEnum.Online)
                {
                    yield return new ServiceStatus(service.Key, new ChannelStatus(service.Value.StatusEnum, service.Value.Reason));
                }
            }
        }

        public void ResetListenerStatus()
        {
            foreach (var service in this._registry)
            {
                this.NotifyStatusChange(service.Key, ListenerStatusEnum.Online, "Connection restored");
            }
        }
    }
}
