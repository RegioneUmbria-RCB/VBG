using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd
{
	public partial class LogoutCompleted : BasePage
	{
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
		[Inject]
		public IConfigurazione<ParametriAspetto> _configurazioneAspetto { get; set; }
		

		protected void Page_Load(object sender, EventArgs e)
		{
			lblNomeComune2.Text = lblNomeComune.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(IdComune, Software).DENOMINAZIONE;

			if (_configurazioneAspetto.Parametri.FileConfigurazioneContenuti.ToUpper() != "VBG")
			{
				//cssLink.Href = "~/css/less/stili.less";
				//cssLink.Attributes["rel"] = "stylesheet/less";
				//cssLink.Attributes["type"] = "text/css";

				cssIncludeLink.Href = "~/css/less/less.include.css";

				cssLink.Href = "~/css/stili2.css";
				cssIncludeLink.Href = "~/css/less/less.include.css";
			}
		}
	}
}
