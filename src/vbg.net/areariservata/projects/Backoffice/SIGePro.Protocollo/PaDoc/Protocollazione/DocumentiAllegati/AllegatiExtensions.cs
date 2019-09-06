using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using System.Security.Cryptography;
using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Verticalizzazioni;
using System.Web;

namespace Init.SIGePro.Protocollo.PaDoc.Protocollazione.DocumentiAllegati
{
    public static class AllegatiExtensions
    {
        private static class Constants
        {
            public const string Url = "URL";
            public const string Uncompressed = "Uncompressed";
        }

        public static metadigitDoc GetMetadigitDoc(this ProtocolloAllegati allegato, string sequenceNumber, string baseUrl, string idComuneAlias, string software)
        {
            string mac = Md5Utils.GetMd5(String.Concat(idComuneAlias, allegato.CODICEOGGETTO, "file", "secret"));
            string nomeFile = Uri.EscapeDataString(allegato.NOMEFILE);

            var hrefFile = String.Format("{0}/{1}/f/{2}/mac/{3}/{4}", baseUrl, idComuneAlias, allegato.CODICEOGGETTO, mac, nomeFile);
            var nomenclatura = allegato.Descrizione;

            var vertFsCmis = new VerticalizzazioneFilesystemCmis(idComuneAlias, software);
            if (vertFsCmis.Attiva && !String.IsNullOrEmpty(allegato.Percorso) && allegato.Percorso.StartsWith("workspace://"))
            {
                string[] segmentPath = allegato.Percorso.Split('/');

                if (segmentPath.Length > 0)
                    nomenclatura = String.Format("node_id={0}", segmentPath[segmentPath.Length - 1]);
            }
                

            return new metadigitDoc
            {
                sequence_number = sequenceNumber,
                nomenclature = nomenclatura,
                usage = "2",
                file = new metadigitDocFile { href = hrefFile, Location = Constants.Url },
                filesize = allegato.OGGETTO.Length.ToString(),
                md5 = Md5Utils.GetMd5(allegato.OGGETTO),
                format = new metadigitDocFormat
                {
                    name = allegato.NOMEFILE,
                    mime = allegato.MimeType,
                    compression = Constants.Uncompressed
                },
                datetimecreated = DateTime.Now.ToString("yyyy-MM-ddTHH:mm:ssK"),
                note = ""
            };
        }
    }
}
