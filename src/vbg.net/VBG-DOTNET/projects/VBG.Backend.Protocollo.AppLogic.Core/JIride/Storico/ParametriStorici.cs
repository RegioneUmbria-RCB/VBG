using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.JIride.Storico
{
    public class ParametriStorici
    {
        public bool IsCopia { get; internal set; }
        public ProtocolloLogs Logger { get; internal set; }
        public ProtocolloSerializer Serializer { get; internal set; }
        public short AnnoProtocollo { get; internal set; }
        public int? IdProtocollo { get; internal set; }
        public int NumeroProtocollo { get; internal set; }
        public string Operatore { get; internal set; }
        public string Ruolo { get; internal set; }
        public string CodiceAmministrazione { get; internal set; }
        public string CodiceAOO { get; internal set; }
        public string Url { get; internal set; }
    }
}
