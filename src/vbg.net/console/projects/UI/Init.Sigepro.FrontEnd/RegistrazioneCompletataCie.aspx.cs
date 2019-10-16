﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd
{
	public partial class RegistrazioneCompletataCie : BasePage
	{
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
		[Inject]
		public IConfigurazione<ParametriVbg> _configurazioneAspetto { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{
			if (_configurazioneAspetto.Parametri.FileConfigurazioneContenuti.ToUpper() != "VBG")
			{
				lblNomeComune2.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(Software).DENOMINAZIONE;

			}
		}

		protected void cmdAccedi_Click(object sender, EventArgs e)
		{
			Response.Redirect("~/Reserved/default.aspx?idcomune=" + IdComune + "&Software=" + Software);
		}
	}
}