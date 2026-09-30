using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class FabbricatoCondition  : QueryConditionBase
    {
        public FabbricatoCondition(DataBase database, string fabbricato) : base(database, "FAB")
        {
            if (!string.IsNullOrEmpty(fabbricato))
            {
                this.Query = $"( istanzestradario.fabbricato = {QueryParameterName("fabbricato")} OR istanzestradario.fabbricato LIKE {QueryParameterName("fabbricatoLike")})";

                AddParameter("fabbricato", fabbricato);
                AddParameter("fabbricatoLike", fabbricato + "%");
            }
        }
    }
}
