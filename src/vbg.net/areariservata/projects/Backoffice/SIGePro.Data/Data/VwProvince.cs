using System;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;
using System.Xml.Serialization;

namespace Init.SIGePro.Data
{
    [DataTable("VW_PROVINCE")]
    [Serializable]
    public class VwProvince : BaseDataClass
    {
        #region Key Fields

        string siglaprovincia = null;
        [KeyField("SIGLAPROVINCIA", Size = 2, Type = DbType.String)]
		[XmlElement(Order = 0)]
        public string SiglaProvincia
        {
            get { return siglaprovincia; }
            set { siglaprovincia = value; }
        }

        #endregion

        string provincia = null;
        [DataField("PROVINCIA", Size = 20, Type = DbType.String)]
		[XmlElement(Order = 1)]
        public string PROVINCIA
        {
            get { return provincia; }
            set { provincia = value; }
        }
    }
}
