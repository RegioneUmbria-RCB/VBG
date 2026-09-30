using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Exceptions.Token;
using System.Configuration;
using log4net;

namespace Sigepro.net.WebServices.WsSIGePro
{
	/// <summary>
	/// Summary description for CARTService
	/// </summary>
	[WebService(Namespace = "http://tempuri.org/")]
	[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
	[System.ComponentModel.ToolboxItem(false)]
	// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
	// [System.Web.Script.Services.ScriptService]
	public class CARTService : System.Web.Services.WebService
	{
        ILog _log = LogManager.GetLogger(typeof(CARTService));

        private static class Constants
        {
            public const string ConfigKeyUrlSchedaCartIntervento = "UrlSchedaCartIntervento";
            public const string ConfigKeyUrlSchedaCartEndo = "UrlSchedaCartEndo";

            public const string DefaultUrlSchedaIntervento = "{javaBaseUrl}/stp/ajaxSchedaSpiegazioneEndo2.htm?Token={token}&Software={software}&codice={stpIntervento}";
            public const string DefaultUrlSchedaEndo =       "{javaBaseUrl}/stp/ajaxSchedaSpiegazioneEndo1.htm?Token={token}&Software={software}&codice={stpEndo}";

            public const string SegnapostoJavaBaseUrl = "{javaBaseUrl}";
            public const string SegnapostoToken = "{token}";
            public const string SegnapostoSoftware = "{software}";
            public const string SegnapostoIntervento = "{intervento}";
            public const string SegnapostoEndo = "{endo}";
            public const string SegnapostoStpIntervento = "{stpIntervento}";
            public const string SegnapostoStpEndo = "{stpEndo}";
            public const string SegnapostoAlias = "{alias}";    
        }

        private class SostituzioniUrlSchedaCart
        {
            protected readonly Dictionary<string, string> Sostituzioni = new Dictionary<string, string>();

            public string EseguiSostituzioni(string url)
            {
                foreach(var key in this.Sostituzioni.Keys)
                {
                    url = url.Replace(key, this.Sostituzioni[key]);
                }

                return url;
            }
        }

        private class SostituzioniUrlSchedaCartIntervento: SostituzioniUrlSchedaCart
        {
            public SostituzioniUrlSchedaCartIntervento(string alias, string javaBaseUrl, string token, string software, int codiceIntervento, int codiceStpIntervento)
            {
                this.Sostituzioni.Add(Constants.SegnapostoJavaBaseUrl, javaBaseUrl);
                this.Sostituzioni.Add(Constants.SegnapostoToken, token);
                this.Sostituzioni.Add(Constants.SegnapostoSoftware, software);
                this.Sostituzioni.Add(Constants.SegnapostoIntervento, codiceIntervento.ToString());
                this.Sostituzioni.Add(Constants.SegnapostoStpIntervento, codiceStpIntervento.ToString());
                this.Sostituzioni.Add(Constants.SegnapostoAlias, alias);
            }
        }

        private class SostituzioniUrlSchedaCartEndo : SostituzioniUrlSchedaCart
        {
            public SostituzioniUrlSchedaCartEndo(string alias, string javaBaseUrl, string token, string software, int codiceEndo, int codiceStpEndo)
            {
                this.Sostituzioni.Add(Constants.SegnapostoJavaBaseUrl, javaBaseUrl);
                this.Sostituzioni.Add(Constants.SegnapostoToken, token);
                this.Sostituzioni.Add(Constants.SegnapostoSoftware, software);
                this.Sostituzioni.Add(Constants.SegnapostoEndo, codiceEndo.ToString());
                this.Sostituzioni.Add(Constants.SegnapostoStpEndo, codiceStpEndo.ToString());
                this.Sostituzioni.Add(Constants.SegnapostoAlias, alias);
            }
        }

        private string ConfigValueOrDefault(string configKey, string defaultValue)
        {
            var configVal = ConfigurationManager.AppSettings[configKey];

            if (String.IsNullOrEmpty(configVal))
            {
                return defaultValue;
            }

            return configVal;
        }


		[WebMethod]
		public string GetUrlSchedaCARTIntervento(string token, int codIntervento)
		{
			var ai = CheckToken(token);

			using(var db = ai.CreateDatabase())
			{
				var intervento = new AlberoProcMgr(db).GetById(codIntervento, ai.IdComune);

				if (!new VerticalizzazioneCart(ai.Alias, intervento.SOFTWARE).Attiva)
					return String.Empty;

				var stpIntervento = new StpEndoTipo2Mgr(db).GetByCodiceIntervento(ai.IdComune, codIntervento).FirstOrDefault();

				if (stpIntervento == null || !stpIntervento.CodiceStp.HasValue /*|| !stpIntervento.Codiceoggetto.HasValue*/)
					return String.Empty;

                var software = intervento.SOFTWARE;
				var javaBaseUrl = SigeproSecurityProxy.GetValoreParametro("WSHOSTURL_JAVA");
                var urlScheda = ConfigValueOrDefault(Constants.ConfigKeyUrlSchedaCartIntervento, Constants.DefaultUrlSchedaIntervento);
                var sost = new SostituzioniUrlSchedaCartIntervento(ai.Alias, javaBaseUrl, token, software, codIntervento, stpIntervento.CodiceStp.Value);

                var cartUrl = sost.EseguiSostituzioni(urlScheda);

                this._log.DebugFormat("GetUrlSchedaCARTIntervento = {0}", cartUrl);

                return cartUrl;
			}
		}

		[WebMethod]
		public string GetCodiceAttivitaBdrDaIdIntervento(string token, string software, int codIntervento)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
				if (!new VerticalizzazioneCart(ai.Alias, software).Attiva)
					return String.Empty;

				var stpIntervento = new StpEndoTipo2Mgr(db).GetByCodiceIntervento(ai.IdComune, codIntervento).FirstOrDefault();

				if (stpIntervento == null || String.IsNullOrEmpty(stpIntervento.CodiceEndoRegionale))
					return String.Empty;

				return stpIntervento.CodiceEndoRegionale;
			}
		}

		[WebMethod]
		public string GetUrlSchedaCARTEndo(string token, int codEndo)
		{
			var ai = CheckToken(token);

			using (var db = ai.CreateDatabase())
			{
				var endo = new InventarioProcedimentiMgr(db).GetById(ai.IdComune,codEndo);

				if (!new VerticalizzazioneCart(ai.Alias, endo.Software).Attiva)
					return String.Empty;

				var stpEndo = new StpEndoTipo1Mgr(db).GetByCodiceEndo(ai.IdComune, codEndo);

				if (stpEndo == null || !stpEndo.Codiceoggetto.HasValue)
					return String.Empty;

				var javaBaseUrl = SigeproSecurityProxy.GetValoreParametro("WSHOSTURL_JAVA");
                var software = endo.Software;
                var urlScheda = ConfigValueOrDefault(Constants.ConfigKeyUrlSchedaCartEndo, Constants.DefaultUrlSchedaEndo);
                var sost = new SostituzioniUrlSchedaCartEndo(ai.Alias, javaBaseUrl, token, software, codEndo, stpEndo.CodiceStp.Value);

                var cartUrl = sost.EseguiSostituzioni(urlScheda);

                this._log.DebugFormat("GetUrlSchedaCARTEndo = {0}", cartUrl);

                return cartUrl;
			}
		}

		

		private AuthenticationInfo CheckToken(string token)
		{
			AuthenticationInfo ai = AuthenticationManager.CheckToken(token);

			if (ai == null)
				throw new InvalidTokenException(token);

			return ai;
		}
	}
}
