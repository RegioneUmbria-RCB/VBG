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
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;

using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;

namespace Init.Sigepro.FrontEnd.Reserved
{
	public partial class ModificaPassword : ReservedBasePage
	{
		[Inject]
		public IAnagraficheService _anagrafeRepository { get; set; }


		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void cmdConfirm_Click(object sender, EventArgs e)
		{
			try
			{
				string idComune = UserAuthenticationResult.IdComune;
				int codiceAnagrafe = UserAuthenticationResult.DatiUtente.Codiceanagrafe.GetValueOrDefault(-1);
				string vecchiaPassword = txtVecchiaPassword.Text;
				string nuovaPassword = txtNuovaPassword.Text;
				string confermaPassword = txtConfermaNuovaPassword.Text;

				_anagrafeRepository.ModificaPassword(idComune, codiceAnagrafe, vecchiaPassword, nuovaPassword, confermaPassword);

				ClientScript.RegisterStartupScript(this.GetType(), "startup", "alert('Password modificata correttamente')", true);
			}
			catch (Exception ex)
			{
				Errori.Add(ex.Message);
			}
		}
	}
}
