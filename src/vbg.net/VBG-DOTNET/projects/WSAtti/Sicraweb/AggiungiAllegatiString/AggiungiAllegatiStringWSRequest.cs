using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.AggiungiAllegatiString
{
    internal class AggiungiAllegatiStringWSRequest
    {
        public string NuoviAllegatiStr { get; internal set; }

        internal static AggiungiAllegatiStringWSRequest FromWSAttiAggiungiAllegatoRequest(WSAttiAggiungiAllegatoRequest request)
        {
            if (request == null)
            {
                throw new Exception("Impossibile utilizzare il metodo FromWSAttiAggiungiAllegatoRequest senza passare una request valorizzata");
            }

            var nuovoAllegato = new NuoviAllegatiStr
            {
                IdDoc = request.IdDocumento,
                AnnoProt = request.Anno,
                NumProt = request.Numero,
                Utente = request.Utente,
                Allegati = new List<Allegato>
                {
                    new Allegato
                    {
                        TipoFile = request.TipoFile,
                        Image = Convert.FromBase64String( request.Image),
                        NomeAllegato = request.NomeAllegato,
                        TipoAllegato = request.AllegatoPrincipale ? "1" : "2",
                        Commento = request.Serial
                    }
                }
            };

            var nuovoAllegaoInStr = "";
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
                    var serializer = new XmlSerializer(typeof(NuoviAllegatiStr));
                    serializer.Serialize(writer, nuovoAllegato, ns);
                    nuovoAllegaoInStr = stringwriter.ToString();
                }
            }

            return new AggiungiAllegatiStringWSRequest
            {
                NuoviAllegatiStr = nuovoAllegaoInStr
            };
        }
    }
}