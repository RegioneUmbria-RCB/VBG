using System;
using System.IO;
using System.Xml;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.InserisciDeterminaString
{
    internal class InserisciDeterminaStringWSRequest
    {
        public string DeterminaInStr { get; internal set; }
        public string CodiceAmministrazione { get; internal set; }
        public string CodiceAOO { get; internal set; }

        internal static InserisciDeterminaStringWSRequest FromInserisciDeterminaRequest(WSAttiInserisciDeterminaRequest request)
        {
            if (request == null)
            {
                throw new Exception("Impossibile utilizzare il metodo FromInserisciDeterminaRequest senza passare una request valorizzata");
            }

            var determinaIn = new DeterminaInStr
            {
                Oggetto = request.Oggetto,
                Trattamento = request.Trattamento,
                Proponente = request.Proponente,
                Dirigente = request.Dirigente,
                DataDocumento = request.DataDocumento.ToString("dd/MM/yyyy"),
                Classifica = request.Classifica,
                DaPubblicare = request.Pubblicare ? "S" : "N",
                Utente = request.Utente,
                Ruolo = request.Ruolo,
                Note = request.Note,
            };

            var determinaInStr = "";
            var settings = new XmlWriterSettings
            {
                Indent = true,
                OmitXmlDeclaration = true
            };

            var ns = new XmlSerializerNamespaces(new[] { XmlQualifiedName.Empty });

            using (var stringwriter = new StringWriter())
            {
                using (var writer = XmlWriter.Create(stringwriter, settings))
                {
                    var serializer = new XmlSerializer(typeof(DeterminaInStr));
                    serializer.Serialize(writer, determinaIn, ns);
                    determinaInStr = stringwriter.ToString();
                }
            }


            return new InserisciDeterminaStringWSRequest
            {
                DeterminaInStr = determinaInStr,
                CodiceAmministrazione = request.CodiceAmministrazione,
                CodiceAOO = request.CodiceAOO
            };
        }
    }
}
