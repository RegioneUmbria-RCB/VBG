using System.IO;
using System.Xml.Serialization;

namespace WSAtti.Sicraweb.AggiungiAllegatiString
{
    internal class AggiungiAllegatiStringWSResponse
    {
        public AllegatiInseritiResponse AllegatiInseriti { get; set; }

        internal static AggiungiAllegatiStringWSResponse FromXMLAggiungiAllegatiString(string responseString)
        {
            using (var stringReader = new StringReader(responseString))
            {
                var serializer = new XmlSerializer(typeof(AllegatiInseritiResponse));

                return new AggiungiAllegatiStringWSResponse
                {
                    AllegatiInseriti = serializer.Deserialize(stringReader) as AllegatiInseritiResponse
                };
            }
        }
    }
}
