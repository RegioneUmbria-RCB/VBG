using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ITABELLA4")]
    [Serializable]
    public class CCITabella4 : BaseDataClass
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
        [DataField("FK_CCTC_ID", Type = DbType.Decimal)]
        public int? FkCctcId { get; set; }

        [isRequired]
        [DataField("INCREMENTO", Type = DbType.Decimal)]
        public decimal? Incremento { get; set; }

        [isRequired]
        [DataField("SELEZIONATA", Type = DbType.Decimal)]
        public int? Selezionata { get; set; }

        [DataField("FK_CCIC_ID", Type = DbType.Decimal)]
        public int? FkCcicId { get; set; }
    }
}
