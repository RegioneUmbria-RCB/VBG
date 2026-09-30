using AllegatiPrismaService;
using ProtocolloPrismaService;
using System;
using System.Xml.Serialization;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;
using VBG.Backend.Protocollo.AppLogic.Shared.Validation;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma
{
    public class PrismaSerializer : ProtocolloSerializer
    {
        public PrismaSerializer(ProtocolloLogs protocolloLog, ProtocolloValidation protocolloValidation) : base(protocolloLog, protocolloValidation)
        {
        }

        public override XmlAttributeOverrides GetOverrides()
        {
            return new OverrideXml()
                    .Override<inserimentoRequest>()
                    .Member("strAttachment").XmlIgnore()
                    .Override<smistamentoActionRequest>()
                    .Member("strAttachment").XmlIgnore()
                    .Override<protocollazioneRequest>()
                    .Member("strAttachment").XmlIgnore()
                    .Override<aggiungiAllegatoRequest>()
                    .Member("strAttachment").XmlIgnore()
                    .Override<sostituisciDocumentoPrincipaleRequest>()
                    .Member("strAttachment").XmlIgnore()
                    .Override<DownloadAttachResponse>()
                    .Member("contentFile").XmlIgnore()
                    .Override<insertAttach>()
                    .Member("file").XmlIgnore()
                    .Commit();
        }

        public override string Serialize(string sFileName, object pProtocollo, string messaggio)
        {
            var newFileNAme = $"{DateTime.Now:HHmmss}-{sFileName}";

            return base.Serialize(newFileNAme, pProtocollo, messaggio);
        }
    }
}
