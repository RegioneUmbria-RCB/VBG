using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_ITABELLA1")]
    [Serializable]
    public class CCITabella1 : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, CaseSensitive = true, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("ID", Type = DbType.Decimal)]
        [useSequence]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CODICEISTANZA", Type = DbType.Decimal)]
        public int? Codiceistanza { get; set; }

        /// <summary>
        /// Id della testata del calcolo
        /// </summary>
        [isRequired]
        [DataField("FK_CCIC_ID", Type = DbType.Decimal)]
        public int? FkCcicId { get; set; }

        /// <summary>
        /// Id della classe di superficie
        /// </summary>
        [isRequired]
        [DataField("FK_CCCS_ID", Type = DbType.Decimal)]
        public int? FkCccsId { get; set; }

        [isRequired]
        [DataField("ALLOGGI", Type = DbType.Decimal)]
        public int? Alloggi { get; set; }

        [isRequired]
        [DataField("SU", Type = DbType.Decimal)]
        public decimal? Su { get; set; }

        [isRequired]
        [DataField("RAPPORTO_SU", Type = DbType.Decimal)]
        public decimal? RapportoSu { get; set; }

        [isRequired]
        [DataField("INCREMENTO", Type = DbType.Decimal)]
        public decimal? Incremento { get; set; }

        [isRequired]
        [DataField("INCREMENTOXCLASSI", Type = DbType.Decimal)]
        public decimal? Incrementoxclassi { get; set; }

        [ForeignKey("Idcomune,FkCccsId", "Idcomune,Id")]
        public CCClassiSuperfici? ClassiSuperfici { get; set; }

    }
}
