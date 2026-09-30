using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLI")]
    [Serializable]
    public class CCICalcoli : BaseDataClass
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
        [DataField("SU", Type = DbType.Decimal)]
        public decimal? Su { get; set; }

        [isRequired]
        [DataField("SNR", Type = DbType.Decimal)]
        public decimal? Snr { get; set; }

        [isRequired]
        [DataField("SC", Type = DbType.Decimal)]
        public decimal? Sc { get; set; }

        [isRequired]
        [DataField("ST", Type = DbType.Decimal)]
        public decimal? St { get; set; }

        [isRequired]
        [DataField("SA", Type = DbType.Decimal)]
        public decimal? Sa { get; set; }

        [isRequired]
        [DataField("SU_ART9", Type = DbType.Decimal)]
        public decimal? SuArt9 { get; set; }

        [isRequired]
        [DataField("I1", Type = DbType.Decimal)]
        public decimal? I1 { get; set; }

        [isRequired]
        [DataField("I2", Type = DbType.Decimal)]
        public decimal? I2 { get; set; }

        [isRequired]
        [DataField("I3", Type = DbType.Decimal)]
        public decimal? I3 { get; set; }

        [DataField("FK_CCTCE_ID", Type = DbType.Decimal)]
        public int? FkCctceId { get; set; }

        [isRequired]
        [DataField("MAGGIORAZIONE", Type = DbType.Decimal)]
        public decimal? Maggiorazione { get; set; }

        [isRequired]
        [DataField("COSTOCMQ", Type = DbType.Decimal)]
        public decimal? Costocmq { get; set; }

        [isRequired]
        [DataField("COSTOCMQ_MAGGIORATO", Type = DbType.Decimal)]
        public decimal? CostocmqMaggiorato { get; set; }



        public List<CCITabella1> Tabella1 { get; set; } = new List<CCITabella1>();
    }
}
