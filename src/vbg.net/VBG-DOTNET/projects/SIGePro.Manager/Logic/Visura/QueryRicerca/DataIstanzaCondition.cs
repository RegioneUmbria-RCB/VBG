using PersonalLib2.Data;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class DataIstanzaCondition : QueryConditionBase
    {
        public DataIstanzaCondition(DataBase database, FiltroPeriodoPresentazione periodoPresentazione)
            : base(database, "DataIstanza")
        {
            if (!(periodoPresentazione?.ContainsValidValues ?? false))
            {
                return;
            }

            var range = periodoPresentazione.ToDateRange();
            this.Query = $"istanze.DATA BETWEEN {this.QueryParameterName("dallaData")} AND {this.QueryParameterName("allaData")}";

            this.AddParameter("dallaData", range.Min);
            this.AddParameter("allaData", range.Max);
        }
    }
}
