using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CONFIGURAZIONE_SETTORI")]
    [Serializable]
    public partial class CCConfigurazioneSettori : BaseDataClass
    {
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }

        [KeyField("FK_SE_CODICESETTORE", Type = DbType.String, Size = 6)]
        public string FkSeCodicesettore { get; set; }

        [ForeignKey("Idcomune, FkSeCodicesettore", "IDCOMUNE, CODICESETTORE")]
        public Settori? Settore { get; set; }
    }
}
