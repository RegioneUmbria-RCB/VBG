using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd
{
	public partial class RegistrazioneCompletata : BasePage
	{
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }

		[Inject]
		public IConfigurazione<ParametriVbg> _configurazioneAspetto { get; set; }
		[Inject]
		public IConfigurazione<ParametriRegistrazione> _configurazioneRegistrazione { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				lblNomeComune2.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(Software).DENOMINAZIONE;
				var msg = _configurazioneRegistrazione.Parametri.MessaggioRegistrazioneCompletata;

				if (!String.IsNullOrEmpty(msg))
					lblTesto.Text = msg;
			}
		}
	}
}
