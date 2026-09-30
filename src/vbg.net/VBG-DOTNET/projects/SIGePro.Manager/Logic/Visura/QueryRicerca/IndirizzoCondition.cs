using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class IndirizzoCondition: QueryConditionBase
    {
        public IndirizzoCondition(DataBase db, FiltroIndirizzo indirizzo)
            :base(db, "Indirizzo")
        {
            if (indirizzo == null)
            {
                return;
            }

            var sb = new StringBuilder();
            sb.Append($"ISTANZESTRADARIO.codicestradario = {QueryParameterName("codiceStradario")}");
            AddParameter("codiceStradario", indirizzo.CodiceStradario);

            if (!String.IsNullOrEmpty(indirizzo.Civico))
            {
                sb.Append($" AND ISTANZESTRADARIO.civico = {QueryParameterName("civico")}");
                AddParameter("civico", indirizzo.Civico);
            }

            this.Query = sb.ToString();

        }
    }
}
