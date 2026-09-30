using log4net;
using System.Text.Json;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class ParicelleCatastaliResult : Ravenna3Result
    {
        public ParicelleCatastaliResult(JsonElement jsonElement, ILog log) : base(true, log)
        {
            this.Sezione = GetVal(jsonElement, Ravenna3DbClient.Constants.TabellaParicelleCatastali.CampoSezione, "string");
            this.Foglio = GetVal(jsonElement, Ravenna3DbClient.Constants.TabellaParicelleCatastali.CampoFoglio, "string");
            this.Particella = GetVal(jsonElement, Ravenna3DbClient.Constants.TabellaParicelleCatastali.CampoNumero, "number");
        }
    }
}
