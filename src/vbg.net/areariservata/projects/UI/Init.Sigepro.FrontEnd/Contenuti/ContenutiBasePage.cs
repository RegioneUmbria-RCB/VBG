using System;
using System.Globalization;
using System.Linq;
using System.Threading;
using System.Web;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;
using Init.Sigepro.FrontEnd.WebControls;
using Ninject;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	public class ContenutiBasePage : BasePage
	{
		[Inject]
		protected ConfigurazioneContenuti Configurazione{get;set;}

		public string AliasComune
		{
			get
			{
				return Request.QueryString["alias"];
			}
		}

		public override string IdComune
		{
			get
			{
				return Configurazione.DatiComune.IdComune;
			}
		}

		public override string Software
		{
			get
			{
				var sw = Request.QueryString["software"];

				if (string.IsNullOrEmpty(sw))
					return "SS";

				return sw;
			}
		}
    }
}
