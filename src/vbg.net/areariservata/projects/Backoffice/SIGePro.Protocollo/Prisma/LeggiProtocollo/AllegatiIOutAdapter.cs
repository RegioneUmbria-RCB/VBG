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
    public class AllegatiIOutAdapter
    {
        public AllegatiIOutAdapter()
        {

        }

        public AllOut[] Adatta(FilePrincipaleOutXml filePrincipale, AllegatiOutXml allegati, LeggiPecOutXML responsePec, AllegatiServiceWrapper serviceAllegati, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {

            if (filePrincipale == null || filePrincipale.File == null)
            {
                return null;
            }

            var retVal = new List<AllOut>();

            retVal.Add(new AllOut
            {
                IDBase = String.Join(",", new[] { filePrincipale.File.IdOggettoFile, filePrincipale.File.IdDocumento }),
                Commento = filePrincipale.File.FileName,
                Serial = filePrincipale.File.FileName
            });

            if (allegati != null && allegati.Allegato != null && allegati.Allegato.Count() > 0)
            {
                var allegatiSecondari = allegati.Allegato.Select(x => new AllOut
                {
                    IDBase = String.Join(",", new[] { x.FileAllegati.File[0].IdOggettoFile, x.FileAllegati.File[0].IdDocumento }),
                    Commento = x.FileAllegati.File[0].FileName,
                    Serial = x.FileAllegati.File[0].FileName
                }).ToArray();

                retVal.AddRange(allegatiSecondari);
            }

            if (retVal.Count() == 0)
            {
                return null;
            }

            var files = responsePec.GetFiles();

            if (files != null)
            {
                var filesPec = files.Select(x => x.ToAllOutPec(serviceAllegati, logs, serializer)); //new AllOut
                //{
                //    IDBase = String.Join(",", new[] { x.IdOggettoFile, x.IdDocumento }),
                //    Commento = x.FileName,
                //    Serial = x.FileName
                //});

                retVal.AddRange(filesPec);
            }

            return retVal.ToArray();
        }
    }
}
