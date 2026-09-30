using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class NumeroPraticaCondition : QueryConditionBase
    {
        public NumeroPraticaCondition(DataBase database, string numeroPratica)
            :base(database, "IST")
        {
            if (!string.IsNullOrEmpty(numeroPratica))
            {
                this.Query = $"( istanze.numeroistanza = {QueryParameterName("numeroPratica")} OR istanze.numeroistanza LIKE {QueryParameterName("numeroPraticaLike")})";

                AddParameter("numeroPratica", numeroPratica);
                AddParameter("numeroPraticaLike", numeroPratica + "%");
            }
        }
        
    }
}
