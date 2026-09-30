using log4net;
using System;
using System.Text.Json;

namespace VBG.Backend.SIT.AppLogic.Ravenna2
{
    public class Ra012Result : Ravenna3Result
    {

        public Ra012Result(JsonElement jsonElement, ILog log)
        : base(true, log)
        {
            var jsonAttributes = jsonElement.GetProperty("attributes");
            this.Civico = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCivico, "number");
            this.CodVia = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCodiceVia, "number");

        }

        public Ra012Result(JsonElement jsonElement, Ravenna3DbClient ravenna3DbClient, ILog log)
            : base(true, log)
        {

            var jsonAttributes = jsonElement.GetProperty("attributes");
            this.CodVia = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCodiceVia, "number");
            try
            {
                this.Frazione = this.GetVal(JsonDocument.Parse(ravenna3DbClient.GetFrazione(this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCodiceSezione, "number"))).RootElement.GetProperty("features")[0].GetProperty("attributes"), Constants.TabellaRA147.CampoDescrizioneFrazione, "string");
            }
            catch (Exception)
            {
                //nothing
            }
            this.CAP = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCAP, "number");
            this.Civico = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoCivico, "number");
            this.CodCivico = this.GetVal(jsonAttributes, Constants.TabellaRA012.CodCivico, "string");
            this.Esponente = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoEsponente, "string");
            this.Sezione = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoSezione, "string");
            this.Foglio = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoFoglio, "string");
            this.Particella = this.GetVal(jsonAttributes, Constants.TabellaRA012.CampoParticella, "string");
            this.Fabbricato = this.GetVal(jsonAttributes,   Constants.TabellaRA012.CampoEdificio, "number");

        }
    }
}
