using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLO_TCONTRIBUTO")]
    [Serializable]
    public class CCICalcoloTContributo : BaseDataClass
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
        [DataField("FK_CCICT_ID", Type = DbType.Decimal)]
        public int? FkCcictId { get; set; }

        [isRequired]
        [DataField("STATO", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string Stato { get; set; }

        [isRequired]
        [DataField("COSTOC_EDIFICIO", Type = DbType.Decimal)]
        public decimal? CostocEdificio { get; set; }

        [DataField("FK_CCIC_ID", Type = DbType.Decimal)]
        public int? FkCcicId { get; set; }

        [isRequired]
        [DataField("COEFFICIENTE", Type = DbType.Decimal)]
        public decimal? Coefficiente { get; set; }

        [DataField("FK_CCDE_ID", Type = DbType.Decimal)]
        public int? FkCcdeId { get; set; }

        [DataField("RIDUZIONEPERC", Type = DbType.Decimal)]
        public decimal? Riduzioneperc { get; set; }

        [DataField("NOTERIDUZIONE", Type = DbType.String, CaseSensitive = false, Size = 1000)]
        public string Noteriduzione { get; set; }

        public CCICalcoli? Calcoli { get; set; }

        public decimal GetQuotaSenzaRiduzioni()
        {
            return ((this.CostocEdificio.Value / 100.0m) * this.Coefficiente.Value);
        }

        public decimal GetQuotaConRiduzioni()
        {
            return this.GetQuotaSenzaRiduzioni() + this.Riduzioneperc.Value;
        }
    }
}
