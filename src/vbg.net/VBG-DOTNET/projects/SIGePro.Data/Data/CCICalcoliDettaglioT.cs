using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ICALCOLI_DETTAGLIOT")]
    [Serializable]
    public class CCICalcoliDettaglioT : BaseDataClass
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
        [DataField("ORDINE", Type = DbType.Decimal)]
        public int? Ordine { get; set; }

        [isRequired]
        [DataField("FK_CCTS_ID", Type = DbType.Decimal)]
        public int? FkCctsId { get; set; }

        [DataField("FK_CCDS_ID", Type = DbType.Decimal)]
        public int? FkCcdsId { get; set; }

        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [isRequired]
        [DataField("SU", Type = DbType.Decimal)]
        public decimal? Su { get; set; }

        [isRequired]
        [DataField("FK_CCIC_ID", Type = DbType.Decimal)]
        public int? FkCcicId { get; set; }

        [isRequired]
        [DataField("ALLOGGI", Type = DbType.Decimal)]
        public int? Alloggi { get; set; }
    }
}
