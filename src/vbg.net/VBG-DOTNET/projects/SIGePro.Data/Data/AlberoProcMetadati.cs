using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("ALBEROPROC_METADATI")]
    [Serializable]
    public class AlberoProcMetadati : BaseDataClass
    {
        #region Key Fields

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string Idcomune { get; set; }


        [KeyField("FK_SCID", Size = 10, Type = DbType.Decimal)]
        public int? FkScId { get; set; }

        [KeyField("CHIAVE", Size = 100, Type = DbType.String)]
        public string Chiave { get; set; }

        #endregion
        [isRequired(MSG = "ALBEROPROC_METADATI.VALORE obbligatorio")]
        [DataField("VALORE", Size = 4000, Type = DbType.String)]
        public string Valore { get; set; }
    }
}