using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_DETERMTIPOCALCOLO")]
    [Serializable]
    public class CCDetermTipoCalcolo : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("FK_OCCBTI_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbtiId { get; set; }

        [isRequired]
        [DataField("FK_OCCBDE_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbdeId { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [isRequired]
        [DataField("FK_CCBTC_ID", Type = DbType.String, CaseSensitive = false, Size = 3)]
        public string FkCcbtcId { get; set; }

        [isRequired]
        [DataField("PRIORITA", Type = DbType.SByte, Size = 1)]
        public int? Priorita { get; set; }

        [ForeignKey(/*typeof(OCCBaseTipoIntervento),*/ "FkOccbtiId", "Id")]
        public OCCBaseTipoIntervento? BaseTipoIntervento { get; set; }

        [ForeignKey(/*typeof(OCCBaseDestinazioni),*/ "FkOccbdeId", "Id")]
        public OCCBaseDestinazioni? BaseTipoDestinazione { get; set; }

        [ForeignKey(/*typeof(CCBaseTipoCalcolo),*/ "FkCcbtcId", "Id")]
        public CCBaseTipoCalcolo? BaseTipoCalcolo { get; set; }

    }
}
