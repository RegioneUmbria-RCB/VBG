using System;
using System.Configuration;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;

using Init.Sigepro.FrontEnd.WebControls.Common;


using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Services.Navigation;

namespace Init.Sigepro.FrontEnd
{
    /// <summary>
    /// Descrizione di riepilogo per Login.
    /// </summary>
    public partial class Login : BasePage
    {
		[Inject]
		public IConfigurazioneVbgRepository _configurazioneVbgRepository { get; set; }

		protected string ReturnTo
		{
			get
			{
				string returnTo = Request.QueryString["ReturnTo"];
				return returnTo;
			}
		}

		public Login()
		{
			//PersistenzaDati.SegnaComePresentata( "KBUMB" , 130 , 61 ); 
		}

        protected void Page_Load(object sender, EventArgs e)
        {
			if (!String.IsNullOrEmpty(ReturnTo))
				Context.Response.Cookies.Add(new HttpCookie("ReturnTo", ReturnTo));

			new RedirectService(HttpContext.Current).RedirectToHomeAreaRiservata(IdComune, Software, Server.UrlEncode(ReturnTo));

        }

		private void InizializzaNomeComune()
		{
			lblNomeComune.Text = _configurazioneVbgRepository.LeggiConfigurazioneComune(IdComune, Software).DENOMINAZIONE;
		}


        protected void cmdEnter_Click(object sender, EventArgs e)
        {
        }

		protected void cmdHome_Click(object sender, ImageClickEventArgs e)
		{
			//Response.Redirect(ReturnTo);
		}
    }
}