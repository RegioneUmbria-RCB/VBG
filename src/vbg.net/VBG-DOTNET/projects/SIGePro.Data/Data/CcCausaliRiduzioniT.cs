
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CAUSALIRIDUZIONIT")]
    [Serializable]
    public partial class CcCausaliRiduzioniT : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software { get; set; }
    }
}
