using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_TABELLA3")]
    [Serializable]
    public partial class CCTabella3 : BaseDataClass
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

        [DataField("FK_CCDS_ID", Type = DbType.Decimal)]
        public int? FkCcDsId { get; set; }
        [isRequired]
        [DataField("RAPPORTO_SU_SNR_DA", Type = DbType.Decimal)]
        public int? RapportoSuSnrDa { get; set; }

        [isRequired]
        [DataField("RAPPORTO_SU_SNR_A", Type = DbType.Decimal)]
        public int? RapportoSuSnrA { get; set; }

        [ForeignKey(/*typeof(CCDettagliSuperficie),*/ "Idcomune,FkCcDsId", "Idcomune,Id")]
        public CCDettagliSuperficie? DettagliSuperficie { get; set; }

        [ForeignKey(/*typeof(CCDettagliSuperficie),*/ "Idcomune,FkCcDsId", "Idcomune,Id")]
        public CCDettagliSuperficie? DettaglioSuperficie { get; set; }
    }
}
