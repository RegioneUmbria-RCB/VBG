using MimeKit;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Utils
{
    public class EmlUtils
    {
        public EmlUtils()
        {

        }

        public IEnumerable<AttachmentInfo> GetAttachmentsInfo(Stream stream, string nomeFileEml, string[] fileDaEscludere, int indice, string[] zipExtensions, bool allocaFile)
        {
            var mm = MimeMessage.Load(stream);

            var attachmentsList = new List<AttachmentInfo>();

            foreach (var item in mm.BodyParts)
            {
                var fileName = item.ContentType.Name;

                using (var memory = new MemoryStream())
                {
                    if (item is MimePart)
                    {
                        ((MimePart)item).ContentObject.DecodeTo(memory);
                    }
                    else
                    {
                        ((MessagePart)item).Message.WriteTo(memory);
                    }

                    if (Path.GetExtension(item.ContentType.Name) == ".eml")
                    {
                        memory.Position = 0;
                        var otherAttach = this.GetAttachmentsInfo(memory, item.ContentType.Name, fileDaEscludere, indice, zipExtensions, allocaFile);
                        attachmentsList.AddRange(otherAttach);
                        indice = otherAttach.Max(x => x.Index) + 1;
                    }
                    else if (zipExtensions.Contains(Path.GetExtension(item.ContentType.Name)))
                    {
                        memory.Position = 0;

                        var zipUtils = new ZipUtils();
                        var attachmentInfo = zipUtils.GetAttachmentsInfo(memory, item.ContentType.Name, indice, allocaFile);
                        attachmentsList.AddRange(attachmentInfo);
                        indice = attachmentInfo.Max(x => x.Index) + 1;
                    }
                    else
                    {
                        var bytes = memory.ToArray();

                        if (!item.IsAttachment)
                        {
                            if (String.IsNullOrEmpty(fileName))
                            {
                                if (String.IsNullOrEmpty(((TextPart)item).Text))
                                {
                                    continue;
                                }

                                fileName = ((TextPart)item).IsHtml ? "Body.html" : "Body.txt";
                            }
                        }

                        if (fileDaEscludere != null)
                        {
                            if (fileDaEscludere.Where(x => x == fileName).Count() > 0)
                            {
                                continue;
                            }
                        }

                        attachmentsList.Add(new AttachmentInfo($"[({nomeFileEml})] {fileName}", bytes, indice));
                        indice++;
                    }
                }
            }

            return attachmentsList;
        }
    }
}
