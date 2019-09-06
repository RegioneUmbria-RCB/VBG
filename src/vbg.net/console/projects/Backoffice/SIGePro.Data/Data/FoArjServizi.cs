using PersonalLib2.Sql.Attributes;
using System;
using System.Data;

namespace Init.SIGePro.Data
{
    [DataTable("FO_ARJ_SERVIZI")]
    [Serializable]
    public class FoArjServizi : BaseDataClass
    {
        [KeyField("ID", Type = DbType.Decimal)]
        public int? Id
        {
            get;
            set;
        }

        [KeyField("IDCOMUNE", Size = 6, Type = DbType.String)]
        public string IdComune
        {
            get;
            set;
        }

        [DataField("URL_SERVIZIO", Size = 200, Type = DbType.String, Compare = "=", CaseSensitive = false)]
        public string UrlServizio
        {
            get;
            set;
        }

        [DataField("ANONIMO", Type = DbType.Decimal)]
        public int? Anonimo
        {
            get;
            set;
        }

        [DataField("FK_ALBEROPROCSCID", Type = DbType.Decimal)]
        public int? FkAlberoprocScId
        {
            get;
            set;
        }

        [DataField("FK_NLASERVIZIID", Type = DbType.Decimal)]
        public int? FkNlaServiziId
        {
            get;
            set;
        }
    }
}
