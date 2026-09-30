using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_DETTAGLISUPERFICIE")]
    [Serializable]
    public partial class CCDettagliSuperficie : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [isRequired]
        [DataField("FK_CCTS_ID", Type = DbType.Decimal)]
        public int? FkCcTsId { get; set; }

        [isRequired]
        [DataField("DESCRIZIONE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Descrizione { get; set; }

        [DataField("NOTE", Type = DbType.String, CaseSensitive = false, Size = 500)]
        public string Note { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [ForeignKey("Idcomune,FkCcTsId", "Idcomune,Id")]
        public CCTipiSuperficie? TipoSuperficie { get; set; }

        public override string ToString()
        {
            return this.Descrizione;
        }
    }
}
