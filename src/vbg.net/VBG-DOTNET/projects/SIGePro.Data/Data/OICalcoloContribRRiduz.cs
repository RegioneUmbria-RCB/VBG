
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("O_ICALCOLOCONTRIBR_RIDUZ")]
    [Serializable]
    public class OICalcoloContribRRiduz : BaseDataClass
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
        [DataField("FK_OICCR_ID", Type = DbType.Decimal)]
        public int? FkOiccrId { get; set; }

        [DataField("FK_OCRR_ID", Type = DbType.Decimal)]
        public int? FkOcrrId { get; set; }

        [DataField("RIDUZIONEPERC", Type = DbType.Decimal)]
        public decimal? Riduzioneperc { get; set; }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string? Note { get; set; }

        [ForeignKey("Idcomune, FkOcrrId", "Idcomune, Id")]
        public OCausaliRiduzioniR? Riduzione { get; set; }

    }
}
