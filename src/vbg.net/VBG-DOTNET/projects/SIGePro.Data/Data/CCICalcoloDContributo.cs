using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLO_DCONTRIBUTO")]
    [Serializable]
    public class CCICalcoloDContributo : BaseDataClass
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

        [DataField("FK_CCTI_ID", Type = DbType.Decimal)]
        public int? FkCctiId { get; set; }

        [DataField("FK_CCCA_ID", Type = DbType.Decimal)]
        public int? FkCccaId { get; set; }

        [DataField("FK_AREE_CODICEAREA", Type = DbType.Decimal)]
        public int? FkAreeCodicearea { get; set; }

        [isRequired]
        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public decimal? Coefficiente { get; set; }

    }
}
