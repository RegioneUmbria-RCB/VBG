using System.IO;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.LeggiAttoString
{
    internal class LeggiAttoStringWSResponse
    {
        public AttoOut Atto { get; private set; }

        internal static LeggiAttoStringWSResponse FromXMLLeggiAttoString(string responseeString)
        {
            using (var stringReader = new StringReader(responseeString))
            {
                var serializer = new XmlSerializer(typeof(AttoOut));

                return new LeggiAttoStringWSResponse
                {
                    Atto = serializer.Deserialize(stringReader) as AttoOut
                };
            }
        }
    }
}
