using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd
{
    public partial class RegistrazioneCompletataCie : BasePage
    {
        [Inject]
        public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }
        [Inject]
        public IConfigurazione<ParametriAspetto> _configurazioneAspetto { get; set; }

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
