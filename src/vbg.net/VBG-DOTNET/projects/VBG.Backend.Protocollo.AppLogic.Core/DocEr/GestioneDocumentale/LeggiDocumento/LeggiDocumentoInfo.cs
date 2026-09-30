

using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.LeggiDocumento
{
    public class LeggiDocumentoInfo
    {
        public ProtocolloSerializer Serializer { get; private set; }
        public ProtocolloLogs Logs { get; private set; }
        public GestioneDocumentaleService Wrapper { get; private set; }
        public string UnitaDocumentale { get; private set; }
        public bool EstraiEml { get; private set; }
        public string[] EscludiFilesDaEml { get; private set; }
        public bool EstraiZip { get; private set; }
        public string[] ZipExtensions { get; private set; }

        public LeggiDocumentoInfo(GestioneDocumentaleService wrapper, string unitaDocumentale, ProtocolloLogs logs, ProtocolloSerializer serializer, bool estraiEml, string[] escludiFilesDaEml, bool estraiZip, string[] zipExtensions)
        {
            this.Wrapper = wrapper;
            this.Serializer = serializer;
            this.Logs = logs;
            this.UnitaDocumentale = unitaDocumentale;
            this.EstraiEml = estraiEml;
            this.EscludiFilesDaEml = escludiFilesDaEml;
            this.EstraiZip = estraiZip;
            this.ZipExtensions = zipExtensions;
        }
    }
}
