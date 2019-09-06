using System;
using System.Collections.Generic;
using System.Linq;
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

        public string LoadScripts(string[] scripts)
        {
            var s = scripts.Select(x => $"<script type='text/javascript' src='{ResolveClientUrl(x)}'></script>");

            return String.Join(Environment.NewLine, s.ToArray());
        }

        protected string LoadScript(string script)
        {
            return $"<script type='text/javascript' src='{ResolveClientUrl(script)}'></script>";
        }


        #region gestione della visualizzazione degli errori

        protected Repeater OutputErrori { get; set; }

		public void MostraErrori(IEnumerable<string> errori)
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
