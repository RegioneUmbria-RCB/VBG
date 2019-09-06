using PersonalLib2.Sql.Attributes;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Data
{
    [DataTable("NLA_SERVIZI")]
    [Serializable]
    public class NlaServizi : BaseDataClass
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

        [DataField("IDNODO", Size = 6, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string IdNodo
        {
            get;
            set;
        }

        [DataField("IDENTE", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string IdEnte
        {
            get;
            set;
        }

        [DataField("IDSPORTELLO", Size = 10, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string IdSportello
        {
            get;
            set;
        }

        [DataField("PEC", Size = 320, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string Pec
        {
            get;
            set;
        }

        [DataField("DESCRIZIONE", Size = 4000, Type = DbType.String, Compare = "like", CaseSensitive = true)]
        public string Descrizione
        {
            get;
            set;
        }
    }
}
