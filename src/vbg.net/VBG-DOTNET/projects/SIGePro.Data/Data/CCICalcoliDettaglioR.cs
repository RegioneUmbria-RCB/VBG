using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLI_DETTAGLIOR")]
    [Serializable]
    public partial class CCICalcoliDettaglioR : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza { get; set; }

        [DataField("QTA", Type = DbType.Decimal)]
        public int? Qta { get; set; }

        [DataField("LUNG", Type = DbType.Decimal)]
        public decimal? Lung { get; set; }

        [DataField("LARG", Type = DbType.Decimal)]
        public decimal? Larg { get; set; }

        [isRequired]
        [DataField("FK_CCICDT_ID", Type = DbType.Decimal)]
        public int? FkCcicdtId { get; set; }

        [isRequired]
        [DataField("SU", Type = DbType.Decimal)]
        public decimal? Su { get; set; }

    }
}
