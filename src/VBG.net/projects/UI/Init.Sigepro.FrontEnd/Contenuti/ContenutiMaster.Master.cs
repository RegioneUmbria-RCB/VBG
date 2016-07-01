using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.GestioneContenuti;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
//using Init.Sigepro.FrontEnd.AppLogic.Validation;

namespace Init.Sigepro.FrontEnd.Contenuti
{
	public partial class ContenutiMaster : Ninject.Web.MasterPageBase
	{
		[Inject]
		protected ConfigurazioneContenuti _configurazione { get; set; }


		public bool SoloPagina
		{
			get 
			{
				var qs = Request.QueryString[ "SoloPagina" ];

				if (qs == null)
					return false;

				return qs.ToUpper() == "TRUE";
			}
		}


		public string IdComune
		{
			get { return _configurazione.DatiComune.IdComune; }
		}

		public string Software
		{
			get 
			{
				var sw = Request.QueryString["software"];

				if (String.IsNullOrEmpty(sw))
					return "SS";
				return sw;
			}
		}

		public string AliasComune
		{
			get { return Request.QueryString["alias"]; }
		}

		/// <summary>
		/// Ottiene o imposta quale fase si sta visualizzando
		/// </summary>
		public int StepId{get;set;}


		public bool MostraHelp { get; set; }

		public ContenutiMaster()
		{
			StepId = -1;
			MostraHelp = false;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			mainStyle.Href = ResolveClientUrl("~/css/contenuti/" + this._configurazione.Testi.NomeCss);
		}

		
	}
}
