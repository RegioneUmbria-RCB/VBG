using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using log4net;

using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneEndo : IstanzeStepPage
	{
		ILog _log = LogManager.GetLogger(typeof(GestioneEndo));

		protected void Page_Load(object sender, EventArgs e)
		{
			
		}

		

		public override bool CanEnterStep()
		{
			Response.Redirect("~/Reserved/InserimentoIstanza/GestioneEndoV2.aspx" + Request.QueryString);

			throw new Exception("Step non supportato, utilizzare GestioneEndoV2");
		}
	}
}
