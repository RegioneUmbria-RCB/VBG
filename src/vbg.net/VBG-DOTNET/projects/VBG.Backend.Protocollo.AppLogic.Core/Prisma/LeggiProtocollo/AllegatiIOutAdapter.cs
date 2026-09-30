using VBG.Backend.Protocollo.AppLogic.Core.Prisma.Allegati;
using log4net;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Utils;

namespace VBG.Backend.Protocollo.AppLogic.Core.Prisma.LeggiProtocollo
{
    public class AllegatiIOutAdapter
    {
        private readonly IProtocolloSerializer _serializer;
        private readonly ILog _log = LogManager.GetLogger(typeof(AllegatiIOutAdapter));

        public AllegatiIOutAdapter(IProtocolloSerializer serializer)
        {
            this._serializer = serializer;
        }

        public AllegatoResponseType[] Adatta(FilePrincipaleOutXml filePrincipale, AllegatiOutXml allegati, LeggiPecOutXML responsePec, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs)
        {

            logs.Debug($"Inizio richiesta allegati, file principale null? {(filePrincipale == null || filePrincipale.File == null)}");

            if (filePrincipale?.File == null)
            {
                return null;
            }

            var retVal = new List<AllegatoResponseType>();

            retVal.Add(new AllegatoResponseType
            {
                IDBase = String.Join(",", new[] { filePrincipale.File.IdOggettoFile, filePrincipale.File.IdDocumento }),
                Commento = filePrincipale.File.FileName,
                Serial = filePrincipale.File.FileName
            });

            if (allegati?.Allegato?.Any() ?? false)
            {
                var allegatiSecondari = new List<AllegatoResponseType>();

                foreach (var x in allegati.Allegato)
                {
                    if (x.FileAllegati?.File == null)
                    {
                        this._log.Error($"L'allegato \"{x.DescTipoAllegato}\" non contiene un file: {x.ToXmlString()}");
                        continue;
                    }

                    allegatiSecondari.Add(new AllegatoResponseType
                    {
                        IDBase = String.Join(",", new[] { x.FileAllegati.File[0].IdOggettoFile, x.FileAllegati.File[0].IdDocumento }),
                        Commento = x.FileAllegati.File[0].FileName,
                        Serial = x.FileAllegati.File[0].FileName
                    });
                }

                retVal.AddRange(allegatiSecondari);
            }

            if (retVal.Count() == 0)
            {
                return null;
            }

            var files = responsePec.GetFiles();

            if (files?.Any() ?? false)
            {
                logs.Debug($"File da richiedere: {files.Count()}");

                var filesPec = files.Select(x => x.ToAllOutPec(serviceAllegati, logs, this._serializer)); //new AllOut
                //{
                //    IDBase = String.Join(",", new[] { x.IdOggettoFile, x.IdDocumento }),
                //    Commento = x.FileName,
                //    Serial = x.FileName
                //});

                retVal.AddRange(filesPec);
            }

            logs.Debug($"Fine richiesta allegati");

            return retVal.ToArray();
        }
    }
}
