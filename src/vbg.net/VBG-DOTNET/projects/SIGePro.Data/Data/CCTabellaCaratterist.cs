using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_TABELLA_CARATTERIST")]
    [Serializable]
    public class CCTabellaCaratterist : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("PERC", Type = DbType.Decimal)]
        public decimal? Perc { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }
    }
}
