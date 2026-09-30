using Init.SIGePro.Data;
using PersonalLib2.Sql.Attributes;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace SIGePro.Data.Data
{
    [DataTable("AUTORIZZAZIONI_METADATI")]
    [DataContract]
    public class AutorizzazioniMetadati
    {
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        [XmlElement(Order = 10)]
        public string IdComune { get; set; }

        [KeyField("FKIDAUTORIZZAZIONE", Type = DbType.Decimal)]
        [DataMember]
        [XmlElement(Order = 20)]
        public int? CodiceIstanza { get; set; }

        [KeyField("CHIAVE", Type = DbType.String, Size = 60)]
        [DataMember]
        [XmlElement(Order = 30)]
        public string Chiave { get; set; }

        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        [XmlElement(Order = 40)]
        public string Valore { get; set; }
    }
}
