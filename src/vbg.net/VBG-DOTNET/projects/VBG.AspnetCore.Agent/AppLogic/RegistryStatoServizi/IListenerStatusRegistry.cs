namespace VBG.AspnetCore.Agent.AppLogic.RegistryStatoServizi
{
    public enum ListenerStatusEnum
    {
        Unknown,
        Online,
        Offline
    }

    public record class ChannelStatus(ListenerStatusEnum StatusEnum, string Reason);

    public record struct ServiceStatus(string Name, ChannelStatus Status);


    public interface IListenerStatusRegistry
    {
        void NotifyOnline(string serviceName);
        void NotifyOffline(string serviceName, string reason);
        void DumpStatus();

        IEnumerable<ServiceStatus> GetOfflineServices();
        void ResetListenerStatus();
    }
}
