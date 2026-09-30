using log4net;
using System;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class Ra012Result : Ravenna3Result
    {

        public Ra012Result(JsonElement jsonElement,  ILog log)
        : base(true, log){
            JsonElement jsonAttributes = jsonElement.GetProperty("attributes");
            this.Civico = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCivico, "number");
            this.CodVia = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCodiceVia, "number");

        }

        public Ra012Result(JsonElement jsonElement, Ravenna3DbClient ravenna3DbClient, ILog log)
            : base(true, log)
        {
            
                JsonElement jsonAttributes = jsonElement.GetProperty("attributes");
                this.CodVia = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCodiceVia, "number");
                try
                {
                    this.Frazione = GetVal(JsonDocument.Parse(ravenna3DbClient.GetFrazione(GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCodiceSezione, "number"))).RootElement.GetProperty("features")[0].GetProperty("attributes"), Ravenna3DbClient.Constants.TabellaRA147.CampoDescrizioneFrazione, "string");
                }
                catch (Exception e)
                {
                    //nothing
                }
                this.CAP = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCAP, "number");
                this.Civico = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoCivico, "number");
                this.CodCivico = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CodCivico, "string");
                this.Esponente = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoEsponente, "string");
                this.Sezione = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoSezione, "string");
                this.Foglio = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoFoglio, "string");
                this.Particella = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoParticella, "string");
                this.Fabbricato = GetVal(jsonAttributes, Ravenna3DbClient.Constants.TabellaRA012.CampoEdificio, "number");
            
        }
    }
}
