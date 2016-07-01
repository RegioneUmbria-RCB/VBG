using System;
using System.Collections.Generic;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Ninject;


namespace Init.Sigepro.FrontEnd
{
	public class BaseAreaRiservataMaster: Ninject.Web.MasterPageBase, IMostraErroriPage
	{
		[Inject]
		public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }

		[Inject]
		public IIdDomandaResolver _idDomandaResolver{ get; set; }

		[Inject]
		public IAuthenticationDataResolver _authenticationDataResolver { get; set; }


		public string Software{get { return this._aliasSoftwareResolver.Software; }}
		public int IdDomanda { get { return this._idDomandaResolver.IdDomanda; } }
		public UserAuthenticationResult UserAuthenticationResult { get { return this._authenticationDataResolver.DatiAutenticazione; } }
		public string CodiceUtente{ get { return UserAuthenticationResult.DatiUtente.Codicefiscale; } }
		public string IdComune { get { return this._aliasSoftwareResolver.AliasComune; } }
		protected string UserToken{ get { return UserAuthenticationResult.Token; } }




		#region gestione della visualizzazione degli errori

		protected Repeater OutputErrori { get; set; }

		public void MostraErrori(List<string> errori)
		{
			if (OutputErrori != null)
			{
				OutputErrori.Visible = true;
				OutputErrori.DataSource = errori;
				OutputErrori.DataBind();
			}
		}

		protected override void OnLoad(EventArgs e)
		{
			base.OnLoad(e);

			if (OutputErrori != null)
				OutputErrori.Visible = false;
		}
		#endregion
	}
}
