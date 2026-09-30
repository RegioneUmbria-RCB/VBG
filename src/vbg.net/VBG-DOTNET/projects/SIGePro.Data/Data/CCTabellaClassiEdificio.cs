using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_TABELLA_CLASSIEDIFICIO")]
    [Serializable]
    public class CCTabellaClassiEdificio : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = true, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("DA", Type = DbType.Decimal)]
        public int? Da { get; set; }

        [isRequired]
        [DataField("A", Type = DbType.Decimal)]
        public int? A { get; set; }

        [isRequired]
        [DataField("MAGGIORAZIONE", Type = DbType.Decimal)]
        public decimal? Maggiorazione { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, CaseSensitive = false, Size = 2)]
        public string Software { get; set; }
    }
}
