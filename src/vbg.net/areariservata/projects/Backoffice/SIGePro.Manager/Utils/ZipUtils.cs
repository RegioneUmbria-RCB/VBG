using ICSharpCode.SharpZipLib.Zip;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Utils
{
    public class ZipUtils
    {
        ILog log = LogManager.GetLogger(typeof(ZipUtils));

        public ZipUtils()
        {

        }

        public IEnumerable<AttachmentInfo> GetAttachmentsInfo(MemoryStream stream, string nomeFileZip, int indice, bool allocaFile)
        {
            var attachmentList = new List<AttachmentInfo>();

            using (var zip = new ZipInputStream(stream))
            {
                ZipEntry currentEntry;
                while ((currentEntry = zip.GetNextEntry()) != null)
                {
                    var fileSize = currentEntry.Size;

                    if (allocaFile)
                    {
                        var tmp = new byte[256];
                        using (var outFile = new MemoryStream())
                        {
                            var tmpSize = zip.Read(tmp, 0, tmp.Length);

                            while (tmpSize > 0)
                            {
                                fileSize += tmpSize;
                                outFile.Write(tmp, 0, tmpSize);

                                tmpSize = zip.Read(tmp, 0, tmp.Length);
                            }

                            attachmentList.Add(new AttachmentInfo($"[({nomeFileZip})] {currentEntry.Name}", outFile.ToArray(), indice));
                        }
                    }
                    else
                    {
                        attachmentList.Add(new AttachmentInfo($"[({nomeFileZip})] {currentEntry.Name}", new byte[0], indice));
                    }

                    indice++;
                }
            }

            return attachmentList;
        }

    }
}
