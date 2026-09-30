using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("O_ICALCOLOCONTRIBR")]
    [Serializable]
    public class OICalcoloContribR : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string? Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza { get; set; }

        [isRequired]
        [DataField("FK_OICCT_ID", Type = DbType.Decimal)]
        public int? FkOicctId { get; set; }

        [isRequired]
        [DataField("FK_ODE_ID", Type = DbType.Decimal)]
        public int? FkOdeId { get; set; }

        [isRequired]
        [DataField("FK_OTO_ID", Type = DbType.Decimal)]
        public int? FkOtoId { get; set; }

        [DataField("COSTOTOT", Type = DbType.Decimal)]
        public decimal? Costotot { get; set; }

        [DataField("COSTOM", Type = DbType.Decimal)]
        public decimal? Costom { get; set; }

        [DataField("SUPERFICIE_CUBATURA", Type = DbType.Decimal)]
        public decimal? SuperficieCubatura { get; set; }

        [DataField("RIDUZIONEPERC", Type = DbType.Decimal)]
        public decimal? Riduzioneperc { get; set; }

        [DataField("RIDUZIONE", Type = DbType.Decimal)]
        public decimal? Riduzione { get; set; }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 4000)]
        public string? Note { get; set; }
    }
}
