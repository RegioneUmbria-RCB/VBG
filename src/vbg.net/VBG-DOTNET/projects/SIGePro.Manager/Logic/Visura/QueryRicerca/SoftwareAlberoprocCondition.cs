using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class SoftwareAlberoprocCondition : QueryConditionBase
    {
        public SoftwareAlberoprocCondition(DataBase db, string software)
            : base(db, "Software")
        {
            if (String.IsNullOrEmpty(software))
            {
                return;
            }

            this.Query = $"ALBEROPROC.software = {this.QueryParameterName("software")}";

            this.AddParameter("software", software);
        }
    }
}
