using PersonalLib2.Data;
using System;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class OggettoPraticaCondition : QueryConditionBase
    {
        public OggettoPraticaCondition(DataBase db, string partial)
            : base(db, "OggettoPratica")
        {
            if (String.IsNullOrEmpty(partial))
            {
                return;
            }

            this.Query = $"{db.Specifics.UCaseFunction("istanze.LAVORI")} LIKE {this.QueryParameterName("partial")}";

            this.AddParameter("partial", "%" + partial.ToUpper() + "%");
        }
    }
}
