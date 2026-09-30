using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ELENCOINAILBASE")]
    [Serializable]
    [DataContract]
    public class ElencoInailBase : BaseDataClass
    {
        [XmlElement(Order = 0)]
        [DataMember]
        [KeyField("CODICE", Size = 6, Type = DbType.String)]
        public string Codice { get; set; }

        [XmlElement(Order = 1)]
        [DataMember]
        [DataField("DESCRIZIONE", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string Descrizione { get; set; }
    }
}
