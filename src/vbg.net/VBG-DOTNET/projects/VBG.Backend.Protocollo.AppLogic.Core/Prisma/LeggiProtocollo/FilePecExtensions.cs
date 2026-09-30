using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Allegati;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public static class FilePecExtensions
    {
        public static AllegatoResponseType ToAllOutPec(this FileOutXml fileOut, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs, IProtocolloSerializer serializer)
        {
            logs.Info($"INIZIO RICHIESTA ALLEGATO idDocumento: {fileOut.IdDocumento}, IdOggettoFile: {fileOut.IdOggettoFile} ");
            var allegato = serviceAllegati.Download(fileOut.IdDocumento, fileOut.IdOggettoFile);
            logs.Info($"FINE RICHIESTA ALLEGATO idDocumento: {fileOut.IdDocumento}, IdOggettoFile: {fileOut.IdOggettoFile} ");

            logs.Info("DESERIALIZZAZIONE DEL FILE daticert.xml LETTO DALLA PEC");
            var daticertXml = Encoding.UTF8.GetString(allegato);
            var daticert = serializer.Deserialize<PostacertXML>(daticertXml);
            logs.Info($"DESERIALIZZAZIONE DEL FILE daticert.xml LETTO DALLA PEC AVVENUTO CON SUCCESSO, TIPO: {daticert.Tipo}");
           

            return new AllegatoResponseType
            {
                


                IDBase = String.Join(",", new[] { fileOut.IdOggettoFile, fileOut.IdDocumento }),
                Commento = $"{daticert.Tipo}.xml",
                Serial = $"{daticert.Tipo}.xml"
            };
        }
    }
}
