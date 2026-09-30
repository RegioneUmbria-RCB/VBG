using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_COEFFCONTRIBUTO")]
    [Serializable]
    public partial class CCCoeffContributo : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("FK_CCVC_ID", Type = DbType.Decimal)]
        public int? FkCcvcId { get; set; }

        [isRequired]
        [DataField("FK_CCDE_ID", Type = DbType.Decimal)]
        public int? FkCcdeId { get; set; }

        [DataField("FK_CCCA_ID", Type = DbType.Decimal)]
        public int? FkCccaId { get; set; }

        [DataField("FK_CCTI_ID", Type = DbType.Decimal)]
        public int? FkCctiId { get; set; }

        [DataField("FK_AREE_CODICEAREA", Type = DbType.Decimal)]
        public int? FkAreeCodicearea { get; set; }

        [isRequired]
        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public double? Coefficiente { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

    }
}
