using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("TIPI_LOCALIZZAZIONI")]
    [Serializable]
    [DataContract]
    public class TipiLocalizzazioni : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string IdComune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        public int? Id { get; set; }

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 50)]
        [DataMember]
        public string Descrizione { get; set; }
    }
}
