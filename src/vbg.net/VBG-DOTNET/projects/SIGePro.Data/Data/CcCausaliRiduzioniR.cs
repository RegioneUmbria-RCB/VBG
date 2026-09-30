
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CAUSALIRIDUZIONIR")]
    [Serializable]
    public class CcCausaliRiduzioniR : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("FK_CCCRT_ID", Type = DbType.Decimal)]
        public int? FkCccrtId { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 50)]
        public string Descrizione { get; set; }

        [DataField("RIDUZIONEPERC", Type = DbType.Decimal)]
        public double? Riduzioneperc { get; set; }

    }
}
