using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_BASETIPOCALCOLO")]
    [Serializable]
    public class CCBaseTipoCalcolo : BaseDataClass
    {
        [KeyField("ID", Type = DbType.String, Size = 3)]
        public string Id { get; set; }

        [isRequired]
        [DataField("TIPOCALCOLO", Type = DbType.String, CaseSensitive = false, Size = 200)]
        public string Tipocalcolo { get; set; }

        public override string ToString()
        {
            return this.Tipocalcolo;
        }
    }
}
