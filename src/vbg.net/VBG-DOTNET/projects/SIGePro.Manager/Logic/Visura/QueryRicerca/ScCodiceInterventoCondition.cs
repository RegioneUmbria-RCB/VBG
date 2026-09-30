using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class ScCodiceInterventoCondition: QueryConditionBase
    {
        public ScCodiceInterventoCondition(DataBase db, string scCodice) : base(db, "INT")
        {
            if (String.IsNullOrEmpty(scCodice))
            {
                return;
            }

            this.Query = $"alberoproc.sc_codice like {QueryParameterName("scCodice")}";
            AddParameter("scCodice", scCodice + "%");
        }
    }
}
