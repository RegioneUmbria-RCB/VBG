using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("O_ICALCOLOCONTRIBT")]
    [Serializable]
    public class OICalcoloContribT : BaseDataClass
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
        [DataField("FK_OCCBDE_ID", Type = DbType.String, CaseSensitive = false, Size = 1)]
        public string FkOccbdeId { get; set; }

        [DataField("FK_AREE_CODICEAREA_ZTO", Type = DbType.Decimal)]
        public int? FkAreeCodiceareaZto { get; set; }

        [DataField("FK_AREE_CODICEAREA_PRG", Type = DbType.Decimal)]
        public int? FkAreeCodiceareaPrg { get; set; }

        [DataField("FK_OIT_ID", Type = DbType.Decimal)]
        public int? FkOitId { get; set; }

        [DataField("FK_OIN_ID", Type = DbType.Decimal)]
        public int? FkOinId { get; set; }

        [DataField("FK_OIN_ID_TABD", Type = DbType.Decimal)]
        public int? FkOinIdTabd { get; set; }

        [DataField("FK_OICT_ID", Type = DbType.Decimal)]
        public int? FkOictId { get; set; }

        [DataField("FK_OCLA_ID", Type = DbType.Decimal)]
        public int? FkOclaId { get; set; }

        public bool PrimoInserimento
        {
            get
            {
                return (
                    !this.FkAreeCodiceareaZto.HasValue &&
                    !this.FkAreeCodiceareaPrg.HasValue &&
                    !this.FkOitId.HasValue &&
                    !this.FkOinId.HasValue &&
                    !this.FkOclaId.HasValue);
            }
        }
    }
}
