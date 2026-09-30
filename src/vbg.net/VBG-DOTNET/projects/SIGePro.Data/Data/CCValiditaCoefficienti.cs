using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_VALIDITACOEFFICIENTI")]
    [Serializable]
    public partial class CCValiditaCoefficienti : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("DATAINIZIOVALIDITA", Type = DbType.DateTime)]
        public DateTime? Datainiziovalidita { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [isRequired]
        [DataField("COSTOMQ", Type = DbType.Decimal)]
        public double? Costomq { get; set; }

    }
}
