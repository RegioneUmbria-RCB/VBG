using SicrawebServiceReference;
using System.IO;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.InserisciDeterminaString
{
    public class InserisciDeterminaStringWSResponse
    {
        public AttoInseritoOut AttoInserito { get; set; }

        internal static InserisciDeterminaStringWSResponse FromXMLInserisciDeterminaString(string responseString)
        {
            using (var stringReader = new StringReader(responseString))
            {
                var serializer = new XmlSerializer(typeof(AttoInseritoOut));

                return new InserisciDeterminaStringWSResponse
                {
                    AttoInserito = serializer.Deserialize(stringReader) as AttoInseritoOut
                };
            }
        }
    }
}