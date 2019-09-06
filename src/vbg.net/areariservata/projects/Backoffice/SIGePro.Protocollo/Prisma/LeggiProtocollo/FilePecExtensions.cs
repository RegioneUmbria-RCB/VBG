using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Prisma.Allegati;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Prisma.LeggiProtocollo
{
    public static class FilePecExtensions
    {
        public static AllOut ToAllOutPec(this FileOutXml fileOut, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            var allegato = serviceAllegati.Download(fileOut.IdDocumento, fileOut.IdOggettoFile);

            logs.Info("DESERIALIZZAZIONE DEL FILE daticert.xml LETTO DALLA PEC");
            var daticertXml = Encoding.UTF8.GetString(allegato);
            var daticert = serializer.Deserialize<PostacertXML>(daticertXml);
            logs.Info($"DESERIALIZZAZIONE DEL FILE daticert.xml LETTO DALLA PEC AVVENUTO CON SUCCESSO, TIPO: {daticert.Tipo}");

            return new AllOut
            {
                IDBase = String.Join(",", new[] { fileOut.IdOggettoFile, fileOut.IdDocumento }),
                Commento = $"{daticert.Tipo}.xml",
                Serial = $"{daticert.Tipo}.xml"
            };
        }
    }
}
