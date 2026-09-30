using ProtocolloKibernetesV2Service;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Kibernetes
{
    public class LeggiAllegatoResponse
    {
        public byte[] Buffer { get; private set; }
        public string FileName { get; private set; }

        internal static LeggiAllegatoResponse FromExportResult(ExportResult exportResult)
        {
            return new LeggiAllegatoResponse
            {
                Buffer = exportResult.Buffer,
                FileName = exportResult.FileName
            };
        }

        internal AllegatoResponseType ToAllOut()
        {
            return new AllegatoResponseType
            {
                Serial = this.FileName,
                Commento = this.FileName,
                TipoFile = Path.GetExtension(this.FileName),
                Image = this.Buffer
            };
        }
    }
}
