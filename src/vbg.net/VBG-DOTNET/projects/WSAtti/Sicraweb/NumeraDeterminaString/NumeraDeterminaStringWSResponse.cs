using SicrawebServiceReference;
using System.IO;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.NumeraDeterminaString
{
    internal class NumeraDeterminaStringWSResponse
    {
        public NumerazioneDeterminaOut Numerazione { get; set; }

        internal static NumeraDeterminaStringWSResponse FromXMLNumeraDeterminaString(string responseString)
        {
            using (var stringReader = new StringReader(responseString))
            {
                var serializer = new XmlSerializer(typeof(NumerazioneDeterminaOut));

                return new NumeraDeterminaStringWSResponse
                {
                    Numerazione = serializer.Deserialize(stringReader) as NumerazioneDeterminaOut
                };
            }
        }
    }
}
