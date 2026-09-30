using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("O_ICALCOLOCONTRIBT_BTO")]
    [Serializable]
    public class OICalcoloContribTBTO : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = true, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza { get; set; }

        [isRequired]
        [DataField("FK_OICCT_ID", Type = DbType.Decimal)]
        public int? FkOicctId { get; set; }

        [isRequired]
        [DataField("FK_BTO_ID", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string FkBtoId { get; set; }

        [DataField("COSTOTOT", Type = DbType.Decimal)]
        public decimal? Costotot { get; set; }

    }
}
