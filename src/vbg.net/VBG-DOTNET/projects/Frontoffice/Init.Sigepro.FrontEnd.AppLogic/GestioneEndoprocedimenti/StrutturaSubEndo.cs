using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti
{
    public class StrutturaSubEndo
    {

        public StrutturaSubEndo()
        {

        }

        public StrutturaSubEndo(IEnumerable<SubEndoprocedimentoSelezionato> subs)
        {
            this.Endo = subs.Select(x => SubEndoSerializable.FromSubEndoprocedimentoSelezionato(x)).ToList();
        }

        [JsonProperty("endo")]
        public List<SubEndoSerializable> Endo { get; set; } = new List<SubEndoSerializable>();

        public string ToJsonString()
        {
            return JsonConvert.SerializeObject(this);
        }
    }
}
