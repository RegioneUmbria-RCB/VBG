using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("ANAGRAFEDYN2DATI")]
    [Serializable]
    [DataContract]
    public partial class AnagrafeDyn2Dati : BaseDataClass
    {
        #region Key Fields

        [DataMember]
        [KeyField("IDCOMUNE", Type = DbType.String, Size = 6)]
        public string Idcomune { get; set; } = null;

        [DataMember]
        [KeyField("CODICEANAGRAFE", Type = DbType.Decimal)]
        public int? Codiceanagrafe { get; set; } = null;

        [DataMember]
        [KeyField("FK_D2C_ID", Type = DbType.Decimal)]
        public int? FkD2cId { get; set; } = null;

        [DataMember]
        [KeyField("INDICE", Type = DbType.Decimal)]
        public int? Indice { get; set; } = null;

        [DataMember]
        [KeyField("INDICE_MOLTEPLICITA", Type = DbType.Decimal)]
        public int? IndiceMolteplicita { get; set; } = null;

        #endregion

        #region Data fields

        [DataMember]
        [DataField("VALORE", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        public string Valore { get; set; } = null;

        [DataMember]
        [DataField("VALOREDECODIFICATO", Type = DbType.String, CaseSensitive = false, Size = 2147483647)]
        public string Valoredecodificato { get; set; } = null;

        #endregion
    }
}
