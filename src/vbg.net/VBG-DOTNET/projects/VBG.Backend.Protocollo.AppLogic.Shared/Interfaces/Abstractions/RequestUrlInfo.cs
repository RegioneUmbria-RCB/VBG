
namespace VBG.Backend.Protocollo.AppLogic.Shared.Interfaces.Abstractions
{
    public class RequestUrlInfo
    {
        public string Scheme { get; set; }
        public string Host { get; set; }
        public int Port { get; set; }
        public string ApplicationPath { get; set; }
    }
}
