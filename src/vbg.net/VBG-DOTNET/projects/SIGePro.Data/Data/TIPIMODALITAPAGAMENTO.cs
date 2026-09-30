using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System;
using System.Data;
using System.Runtime.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("TIPIMODALITAPAGAMENTO")]
    [Serializable]
    [DataContract]
    public class TipiModalitaPagamento : BaseDataClass
    {

        #region Key Fields
        [useSequence]
        [KeyField("MP_ID", Type = DbType.Decimal)]
        [DataMember]
        public string MP_ID { get; set; } = null;
        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        [DataMember]
        public string IDCOMUNE { get; set; } = null;

        #endregion
        [DataField("MP_DESCRESTESA", Size = 100, Type = DbType.String, CaseSensitive = false)]
        [DataMember]
        public string MP_DESCRESTESA { get; set; } = null;

        [DataField("FLAG_DISABILITATO", Type = DbType.Decimal)]
        [DataMember]
        public int? FLAG_DISABILITATO { get; set; } = null;
    }
}