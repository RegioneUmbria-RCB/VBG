
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLOTCONTRIBUTO_RIDUZ")]
    [Serializable]
    public partial class CcICalcoloTContributoRiduz : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
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

        [DataField("FK_CCCRR_ID", Type = DbType.Decimal)]
        public int? FkCccrrId { get; set; }

        [DataField("RIDUZIONEPERC", Type = DbType.Decimal)]
        public decimal? Riduzioneperc { get; set; }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string Note { get; set; }

        [ForeignKey("Idcomune, FkCccrrId", "Idcomune, Id")]
        public CcCausaliRiduzioniR? CausaleRiduzione { get; set; }
    }
}
