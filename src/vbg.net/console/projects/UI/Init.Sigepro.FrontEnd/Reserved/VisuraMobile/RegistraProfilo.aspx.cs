using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.VisuraMobile;

namespace Init.Sigepro.FrontEnd.Reserved.VisuraMobile
{
	public partial class RegistraProfilo : ReservedBasePage
	{
		[Inject]
		protected VisuraMobileProfileService _profileService { get; set; }

		protected void Page_Load(object sender, EventArgs e)
		{

		}

		protected void cmdCollega_Click(object sender, EventArgs e)
		{
			try
			{
				var nome = UserAuthenticationResult.DatiUtente.Nome;
				var cognome = UserAuthenticationResult.DatiUtente.Nominativo;
				var cf = UserAuthenticationResult.DatiUtente.Codicefiscale;
				var uid = txtCodiceProfilo.Text.Trim();

				if (String.IsNullOrEmpty(uid))
					throw new Exception("Inserire il codice a sei cifre che viene mostrato nella schermata \"Collega un nuovo profilo\"");

				if (!_profileService.RegistraProfilo(uid, nome, cognome, cf))
				{
					throw new Exception("Non è stato possibile associare questo profilo all'applicazione mobile. Verificare di aver immesso correttamente il codice a sei cifre che viene mostrato nella schermata \"Collega un nuovo profilo\"");
				}

				multiView.ActiveViewIndex = 1;
				return;
			}
			catch (Exception ex)
			{
				this.Errori.Add(ex.Message);
			}
		}
	}
}