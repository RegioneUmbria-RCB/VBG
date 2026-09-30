using System.Xml.Serialization;

namespace WSAtti.Sicraweb
{
    [XmlRoot("Allegato")]
    public class Allegato
    {
        [XmlElement("Serial")]
        public string Serial { get; set; }

        [XmlElement("IDBase")]
        public string IDBase { get; set; }

        [XmlElement("Versione")]
        public string Versione { get; set; }

        [XmlElement("TipoFile")]
        public string TipoFile { get; set; }

        [XmlElement("ContentType")]
        public string ContentType { get; set; }

        [XmlElement("Image")]
        public byte[] Image { get; set; }

        [XmlElement("Commento")]
        public string Commento { get; set; }

        [XmlElement("IdAllegatoPrincipale")]
        public string IdAllegatoPrincipale { get; set; }

        [XmlElement("Schema")]
        public string Schema { get; set; }

        [XmlElement("NomeAllegato")]
        public string NomeAllegato { get; set; }

        [XmlElement("hash")]
        public string Hash { get; set; }

        [XmlElement("repositoryName")]
        public string RepositoryName { get; set; }

        [XmlElement("TipoAllegato")]
        public string TipoAllegato { get; set; }

        [XmlElement("uri")]
        public string uri { get; set; }

        [XmlIgnore]
        public int? CodiceOggetto
        {
            get
            {
                return int.TryParse(this.Commento, out int codiceOggetto) ? codiceOggetto : (int?)null;
            }
        }

        [XmlIgnore]
        public bool Principale
        {
            get
            {
                return this.TipoAllegato == "1";
            }
        }
    }
}
