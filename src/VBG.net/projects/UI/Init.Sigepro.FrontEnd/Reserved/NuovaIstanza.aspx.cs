using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Ninject;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class NuovaIstanza : ReservedBasePage
	{
		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{
			string url =  _configurazione.Parametri.DefaultWorkflow.GetStepUrl(0);
			Redirect(url, qs => qs.Add("StepId","0"));
		}
	}
}
