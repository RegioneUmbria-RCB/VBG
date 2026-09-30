using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class NumeroAutorizzazioneCondition: QueryConditionBase
    {
        public NumeroAutorizzazioneCondition(DataBase db, string numeroAutorizzazione)
            :base(db, "numAut")
        {
            if(String.IsNullOrEmpty(numeroAutorizzazione))
            {
                return;
            }

            this.Query = $@"EXISTS(				
	SELECT fkidistanza FROM 
		autorizzazioni 
	WHERE 
		autorizzazioni.idcomune = istanze.idcomune AND
		autorizzazioni.fkidistanza = istanze.codiceistanza AND 
		autorizzazioni.autoriznumero = {QueryParameterName("numeroAutorizzazione")} 
)";

            AddParameter("numeroAutorizzazione", numeroAutorizzazione);
        }
    }
}
