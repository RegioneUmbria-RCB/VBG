using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CONDIZIONI_ATTIVITA")]
    [Serializable]
    public class CCCondizioniAttivita : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("FK_AT_CODICEISTAT", Type = DbType.String, CaseSensitive = false, Size = 15)]
        public string FkAtCodiceistat { get; set; }

        [DataField("CONDIZIONEWHERE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Condizionewhere { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [ForeignKey("Idcomune, FkAtCodiceistat", "IDCOMUNE, CodiceIstat")]
        public Attivita? Attivita { get; set; }
    }
}
