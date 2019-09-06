using Init.SIGePro.Attributes;
using PersonalLib2.Sql;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP.DataAccess
{
    [DataTable("LDP_DECODIFICHE")]
    public class LdpDecodifiche : DataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [XmlElement(Order = 0)]
        public string IdComune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [XmlElement(Order = 1)]
        public int? Id { get; set; }

        [DataField("SOFTWARE", Size = 2, Type = DbType.String)]
        [XmlElement(Order = 2)]
        public string Software { get; set; }

        [DataField("CONTESTO", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 3)]
        public string Contesto { get; set; }

        [DataField("CODICE", Size = 16, Type = DbType.String)]
        [XmlElement(Order = 4)]
        public string Codice { get; set; }

        [DataField("DESCRIZIONE", Size = 64, Type = DbType.String)]
        [XmlElement(Order = 5)]
        public string Descrizione { get; set; }
    }
}
