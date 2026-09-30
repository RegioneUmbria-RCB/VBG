using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLO_DCONTRIBATTIV")]
    [Serializable]
    public class CCICalcoloDContribAttiv : BaseDataClass
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
        [DataField("FK_CCICTC_ID", Type = DbType.Decimal)]
        public int? FkCcictcId { get; set; }

        [isRequired]
        [DataField("FK_CCCCA_ID", Type = DbType.Decimal)]
        public int? FkCcccaId { get; set; }

        [isRequired]
        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public decimal? Coefficiente { get; set; }

    }
}
