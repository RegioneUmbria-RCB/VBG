using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("CC_CONFIGURAZIONE")]
    [Serializable]
    public class CCConfigurazione : BaseDataClass
    {
        public const int CALCSUP_DETTAGLIUI = 0;
        public const int CALCSUP_MODELLO = 2;

        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; }

        [KeyField("SOFTWARE", Type = DbType.String, Size = 2)]
        public string Software { get; set; }


        [isRequired]
        [DataField("TAB1_FK_TS_ID", Type = DbType.Decimal)]
        public int? Tab1FkTsId { get; set; }

        [isRequired]
        [DataField("TAB2_FK_TS_ID", Type = DbType.Decimal)]
        public int? Tab2FkTsId { get; set; }

        [isRequired]
        [DataField("ART9SU_FK_TS_ID", Type = DbType.Decimal)]
        public int? Art9suFkTsId { get; set; }

        [isRequired]
        [DataField("ART9SA_FK_TS_ID", Type = DbType.Decimal)]
        public int? Art9saFkTsId { get; set; }

        [DataField("FK_CO_ID", Type = DbType.Decimal)]
        public int? FkCoId { get; set; }

        [DataField("FK_TIPIAREE_CODICE", Type = DbType.Decimal)]
        public int? FkTipiareeCodice { get; set; }

        [DataField("USADETTAGLIOSUP", Type = DbType.Decimal)]
        public int? Usadettagliosup { get; set; }

        [DataField("FK_SE_CODICESETTORE", Type = DbType.String, Size = 6)]
        public string FkSeCodicesettore { get; set; }
    }
}
