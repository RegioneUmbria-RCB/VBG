namespace VBG.AspnetCore.Agent.AppLogic.GestioneMessaggi
{
    public static class RabbitMessageVersion
    {
        public const string Version1_0 = "1.0";
    }

    public class Header
    {
        public required string Versione { get; set; } = RabbitMessageVersion.Version1_0;
        public required string Alias { get; set; }
        public required string Software { get; set; }
    }

    public class MessaggioRabbit<T>
    {
        public required Header Header { get; set; }
        public required T Body { get; set; }
    }
}