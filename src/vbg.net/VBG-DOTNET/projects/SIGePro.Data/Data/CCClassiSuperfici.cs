using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CLASSISUPERFICI")]
    [Serializable]
    public class CCClassiSuperfici : BaseDataClass
    {

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [useSequence]
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id { get; set; }

        [isRequired]
        [DataField("CLASSE", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Classe { get; set; }

        [isRequired]
        [DataField("INCREMENTO", Type = DbType.Decimal)]
        public decimal? Incremento { get; set; }

        [isRequired]
        [DataField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [isRequired]
        [DataField("DA", Type = DbType.Decimal)]
        public int? Da { get; set; }

        [isRequired]
        [DataField("A", Type = DbType.Decimal)]
        public int? A { get; set; }

        [DataField("ALIQUOTA_CALCOLO_CC", Type = DbType.Decimal)]
        public decimal? AliquotaCalcoloCostoCostruzione { get; set; }

        public override string ToString()
        {
            return this.Classe;
        }
    }
}
