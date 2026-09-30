using log4net;
using System.Text.Json;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class ParicelleCatastaliResult : Ravenna3Result
    {
        public ParicelleCatastaliResult(JsonElement jsonElement, ILog log) : base(true, log)
        {
            this.Sezione = this.GetVal(jsonElement, Constants.TabellaParicelleCatastali.CampoSezione, "string");
            this.Foglio = this.GetVal(jsonElement, Constants.TabellaParicelleCatastali.CampoFoglio, "string");
            this.Particella = this.GetVal(jsonElement, Constants.TabellaParicelleCatastali.CampoNumero, "number");
        }
    }
}
