
using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("DYN2_CAMPI")]
    [Serializable]
    [DataContract]
    public partial class Dyn2Campi : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        [DataMember]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        [DataMember]
        public int? Id { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        public string Software { get; set; }

        [isRequired]
        [DataField("NOMECAMPO", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        public string Nomecampo { get; set; }

        [DataField("ETICHETTA", Type = DbType.String, CaseSensitive = false, Size = 2000)]
        [DataMember]
        public string Etichetta { get; set; }

        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        [DataMember]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("TIPODATO", Type = DbType.String, CaseSensitive = false, Size = 20)]
        [DataMember]
        public string Tipodato { get; set; }

        [DataField("OBBLIGATORIO", Type = DbType.Decimal)]
        [DataMember]
        public int? Obbligatorio { get; set; }

        [DataField("FK_D2BC_ID", Type = DbType.String, CaseSensitive = false, Size = 2)]
        [DataMember]
        public string FkD2bcId { get; set; }
    }
}
