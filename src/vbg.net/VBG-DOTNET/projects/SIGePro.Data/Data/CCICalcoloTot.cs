using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLOTOT")]
    [Serializable]
    public class CCICalcoloTot : BaseDataClass
    {
        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = true, Size = 6)]
        public string Idcomune { get; set; }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza { get; set; }

        [isRequired]
        [DataField("DATA", Type = DbType.DateTime)]
        public DateTime? Data { get; set; }

        [isRequired]
        [DataField("FK_CCVC_ID", Type = DbType.Decimal)]
        public int? FkCcvcId { get; set; }

        [isRequired]
        [DataField("FK_OCCBTI_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbtiId { get; set; }

        [isRequired]
        [DataField("FK_OCCBDE_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbdeId { get; set; }

        [isRequired]
        [DataField("FK_BCCTC_ID", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string FkBcctcId { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [DataField("fk_intervento_dett_id", Type = DbType.Decimal)]
        public int? FkInterventoDettaglioId { get; set; }

        [DataField("fk_destinazione_dett_id", Type = DbType.Decimal)]
        public int? FkDestinazioneDettaglioId { get; set; }

        [isRequired]
        [DataField("QUOTACONTRIB_TOTALE", Type = DbType.Decimal)]
        public decimal? QuotacontribTotale { get; set; }

        public CCICalcoloTContributo StatoDiProgetto { get; set; }

        public CCICalcoloTContributo StatoAttuale { get; set; }
    }
}
