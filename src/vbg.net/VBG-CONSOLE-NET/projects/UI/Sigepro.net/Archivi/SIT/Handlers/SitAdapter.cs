using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Collections.Generic;
using Init.SIGePro.Data;
using System.Collections;
using Init.SIGePro.Sit.Data;

namespace Sigepro.net.Archivi.SIT.Handlers
{
    public class SitAdapter : BaseAdapter
    {
        public static Init.SIGePro.Sit.Data.Sit RequestToDataSit()
        {
            var sit = new Init.SIGePro.Sit.Data.Sit();
            HttpContext context = HttpContext.Current;

            sit.Civico = context.Request.Form["Civico"];
            sit.CodCivico = context.Request.Form["CodCivico"];
            sit.CodVia = context.Request.Form["CodiceStradario"];
            if (!string.IsNullOrEmpty(context.Request.Form["CodViario"]))
                sit.CodVia = context.Request.Form["CodViario"];
            sit.Colore = context.Request.Form["Colore"];
            sit.Esponente = context.Request.Form["Esponente"];
            sit.EsponenteInterno = context.Request.Form["EsponenteInterno"];
            sit.Fabbricato = context.Request.Form["Fabbricato"];
            sit.Foglio = context.Request.Form["Foglio"];
            sit.IdComune = IdComune;
            sit.Interno = context.Request.Form["Interno"];
            sit.Km = context.Request.Form["Km"];
            sit.OggettoTerritoriale = context.Request.Form["OggettoTerritoriale"];
            sit.Particella = context.Request.Form["Particella"];
            sit.Scala = context.Request.Form["Scala"];
            sit.Sezione = context.Request.Form["Sezione"];
            sit.Sub = context.Request.Form["Sub"];
            sit.TipoCatasto = context.Request.Form["TipoCatasto"];
            sit.UI = context.Request.Form["UnitaImmob"];
            sit.CAP = context.Request.Form["Cap"];
            sit.Frazione = context.Request.Form["Frazione"];
            sit.Circoscrizione = context.Request.Form["Circoscrizione"];

            return sit;
        }

        public static Dictionary<string, object> CreateDictionary(ValidateSit validateSit)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();

            dic["ReturnValue"] = validateSit.ReturnValue;
            dic["Error"] = validateSit.Message;
            dic["IdComune"] = validateSit.DataSit.IdComune;
            dic["CodViario"] = validateSit.DataSit.CodVia;
            dic["CodCivico"] = validateSit.DataSit.CodCivico;
            dic["TipoCatasto"] = validateSit.DataSit.TipoCatasto;
            dic["Civico"] = validateSit.DataSit.Civico;
            dic["Colore"] = validateSit.DataSit.Colore;
            dic["EsponenteInterno"] = validateSit.DataSit.EsponenteInterno;
            dic["Esponente"] = validateSit.DataSit.Esponente;
            dic["Fabbricato"] = validateSit.DataSit.Fabbricato;
            dic["Foglio"] = validateSit.DataSit.Foglio;
            dic["Interno"] = validateSit.DataSit.Interno;
            dic["Km"] = validateSit.DataSit.Km;
            dic["OggettoTerritoriale"] = validateSit.DataSit.OggettoTerritoriale;
            dic["Particella"] = validateSit.DataSit.Particella;
            dic["Scala"] = validateSit.DataSit.Scala;
            dic["Sezione"] = validateSit.DataSit.Sezione;
            dic["Sub"] = validateSit.DataSit.Sub;
            dic["UnitaImmob"] = validateSit.DataSit.UI;
            dic["Cap"] = validateSit.DataSit.CAP;
            dic["Frazione"] = validateSit.DataSit.Frazione;
            dic["Circoscrizione"] = validateSit.DataSit.Circoscrizione;
            return dic;
        }

        public static Dictionary<string, object> CreateDictionary(string field, ListSit listSit)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();

            dic["ReturnValue"] = listSit.ReturnValue;
            dic["Error"] = listSit.Message;

            int count = 0;
            foreach (string elem in listSit.Field)
            {
                dic[field+count] = elem;
                count++;
            }

            return dic;
        }

        public static Dictionary<string, object> CreateDictionary(DetailSit detailSit)
        {
            Dictionary<string, object> dic = new Dictionary<string, object>();

            dic["ReturnValue"] = detailSit.ReturnValue;
            dic["Error"] = detailSit.Message;

            foreach (DetailField elem in detailSit.Field)
            {
                dic[elem.Campo] = elem.Valore;
            }

            return dic;
        }
    }
}
