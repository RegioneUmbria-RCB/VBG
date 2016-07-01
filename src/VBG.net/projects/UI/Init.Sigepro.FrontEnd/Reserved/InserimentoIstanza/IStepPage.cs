using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public interface IStepPage
	{
		string IdComune { get;}
		string Software{get;}
		int IdDomanda{get;}
		UserAuthenticationResult UserAuthenticationResult{get;}
		bool CheckIfCanEnterPage();
	}
}
