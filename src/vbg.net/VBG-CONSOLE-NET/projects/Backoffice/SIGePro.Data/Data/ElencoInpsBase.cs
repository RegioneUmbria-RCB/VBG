using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ELENCOINAILBASE")]
    [Serializable]
    public class ElencoInpsBase : BaseDataClass
    {
        [XmlElement(Order = 0)]
        [KeyField("CODICE", Size = 6, Type = DbType.String)]
        public string Codice { get; set; }

        [XmlElement(Order = 1)]
        [DataField("DESCRIZIONE", Size = 50, Type = DbType.String, CaseSensitive = false)]
        public string Descrizione { get; set; }
    }
}
