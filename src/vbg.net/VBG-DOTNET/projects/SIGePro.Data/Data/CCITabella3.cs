using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ITABELLA3")]
    [Serializable]
    public class CCITabella3 : BaseDataClass
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
        [DataField("FK_CCT3_ID", Type = DbType.Decimal)]
        public int? FkCct3Id { get; set; }

        [isRequired]
        [DataField("IPOTESICHERICORRE", Type = DbType.Decimal)]
        public int? Ipotesichericorre { get; set; }

        [isRequired]
        [DataField("INCREMENTO", Type = DbType.Decimal)]
        public decimal? Incremento { get; set; }

        [ForeignKey(/*typeof(CCTabella3),*/ "Idcomune,FkCct3Id", "Idcomune,Id")]
        public CCTabella3? Tabella3 { get; set; }
    }
}
