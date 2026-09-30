using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class StatoPraticaCondition: QueryConditionBase
    {
        public StatoPraticaCondition(DataBase db, string stato)
            :base(db, "StatoPratica")
        {
            if (String.IsNullOrEmpty(stato))
            {
                return;
            }

            this.Query = $"ISTANZE.CHIUSURA = {QueryParameterName("statoPratica")}";
            this.AddParameter("statoPratica", stato);
        }
    }
}
