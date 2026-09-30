using Init.SIGePro.Manager.Utils;
using System;
using System.IO;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.GestioneDocumentale.LeggiDocumento.Allegati
{
    public static class AllegatiExtensions
    {
        private static class Constants
        {
            public const string NomeDocumento = "DOCNAME";
            public const string DescrizioneDocumento = "ABSTRACT";
        }

        public static AllegatoResponseType ToAllOut(this string idDocumento, GestioneDocumentaleService wrapper)
        {
            var response = wrapper.LeggiDocumento(idDocumento);
            var dic = response.ToDictionary(x => x.key, y => y.value);

            return new AllegatoResponseType
            {
                Commento = dic[Constants.NomeDocumento],
                IDBase = idDocumento,
                Serial = dic[Constants.NomeDocumento]
            };
        }

        public static AllegatoResponseType ToAllOutToDownload(this string idDocumento, GestioneDocumentaleService wrapper, string indice, byte[] buffer, bool estraiEml, string[] filesDaEscludere, bool estraiZip, string[] zipExtensions)
        {
            var response = wrapper.LeggiDocumento(idDocumento);
            var dic = response.ToDictionary(x => x.key, y => y.value);

            var image = buffer;
            var nomeFile = dic[Constants.NomeDocumento];

            if (estraiEml && Path.GetExtension(dic[Constants.NomeDocumento]).ToLower() == ".eml")
            {
                var emlUtils = new EmlUtils();
                using (var stream = new MemoryStream(buffer))
                {
                    var attachmentInfo = emlUtils.GetAttachmentsInfo(stream, dic[Constants.NomeDocumento], filesDaEscludere, 0, zipExtensions, true);

                    if (attachmentInfo != null)
                    {
                        var allegato = attachmentInfo.Where(x => x.Index == Convert.ToInt32(indice)).First();
                        image = allegato.Image;
                        nomeFile = allegato.FileName;
                    }
                }
            }

            //var ext = new[] {
            //    ".zip",
            //    ".rar",
            //    ".7z"
            //};


            if (estraiZip && zipExtensions.Contains(Path.GetExtension(dic[Constants.NomeDocumento]).ToLower()))
            {
                var zipUtils = new ZipUtils();
                using (var stream = new MemoryStream(buffer))
                {
                    var attachmentInfo = zipUtils.GetAttachmentsInfo(stream, dic[Constants.NomeDocumento], 0, true);

                    if (attachmentInfo != null)
                    {
                        var allegato = attachmentInfo.Where(x => x.Index == Convert.ToInt32(indice)).First();
                        image = allegato.Image;
                        nomeFile = allegato.FileName;
                    }
                }
            }

            return new AllegatoResponseType
            {
                Commento = nomeFile,
                IDBase = idDocumento,
                Serial = nomeFile,
                Image = image
            };
        }
    }
}
