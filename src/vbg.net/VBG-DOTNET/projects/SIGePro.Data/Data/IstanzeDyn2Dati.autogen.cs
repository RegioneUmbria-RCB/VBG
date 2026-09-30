
using System;
using System.Data;
using PersonalLib2.Sql.Attributes;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ISTANZEDYN2DATI")]
    [Serializable]
    [DataContract]
    public partial class IstanzeDyn2Dati : BaseDataClass
    {
        [DataMember]
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("CODICEISTANZA", Type = DbType.Decimal)]
        [DataMember]
        public int? Codiceistanza { get; set; }

        [KeyField("FK_D2C_ID", Type = DbType.Decimal)]
        [DataMember]
        public int? FkD2cId { get; set; }

        [KeyField("INDICE", Type = DbType.Decimal)]
        [DataMember]
        public int? Indice { get; set; }

        [KeyField("INDICE_MOLTEPLICITA", Type = DbType.Decimal)]
        [DataMember]
        public int? IndiceMolteplicita { get; set; }

        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        public string Valore { get; set; }

        [DataField("VALOREDECODIFICATO", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        [DataMember]
        public string Valoredecodificato { get; set; }
    }
}
