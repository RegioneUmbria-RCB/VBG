using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ITABELLA2")]
    [Serializable]
    public class CCITabella2 : BaseDataClass
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
        [DataField("FK_CCIC_ID", Type = DbType.Decimal)]
        public int? FkCcicId { get; set; }

        [isRequired]
        [DataField("SUPERFICIE", Type = DbType.Decimal)]
        public decimal? Superficie { get; set; }

        [DataField("FK_CCDS_ID", Type = DbType.Decimal)]
        public int? FkCcdsId { get; set; }

        [ForeignKey("Idcomune,FkCcdsId", "Idcomune, Id")]
        public CCDettagliSuperficie? DettagliSuperficie { get; set; }
    }
}
