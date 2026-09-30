using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    [DataTable("NLA_SERVIZI_ALTRI_DATI")]
    [Serializable]
    public class NlaServiziAltriDati : BaseDataClass
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

        [DataField("FK_NLASERVIZIID", Type = DbType.Decimal)]
        public int FkNlaServiziId
        {
            get;
            set;
        }

        [DataField("NOMEPARAMETRO", Size = 100, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string NomeParametro
        {
            get;
            set;
        }

        [DataField("VALORE", Size = 200, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string Valore
        {
            get;
            set;
        }
    }
}
