using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    public class PosizioneArchivioCondition : QueryConditionBase
    {
        public PosizioneArchivioCondition(DataBase database, string posizionearchivio) : base(database, "ist")
        {
            if (!string.IsNullOrEmpty(posizionearchivio))
            {
                this.Query = $"( istanze.posizionearchivio = {QueryParameterName("posizionearchivio")} OR istanze.posizionearchivio LIKE {QueryParameterName("posizionearchivioLike")})";

                AddParameter("posizionearchivio", posizionearchivio);
                AddParameter("posizionearchivioLike", posizionearchivio + "%");
            }
        }
    }
}
