using ProtocolloKibernetesV2Service;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes.V2
{
    internal class InviaMailRequest
    {
        public SoggettoInterno Mittente { get; internal set; }
        public Soggetto[] Destinatari { get; internal set; }
        public string CorpoMessaggio { get; internal set; }
        public string OggettoMessaggio { get; internal set; }
        public DataToImport[] Allegati { get; internal set; }
        public string MittenteEmail { get; internal set; }
        public string VoceTitolario { get; internal set; }
    }
}