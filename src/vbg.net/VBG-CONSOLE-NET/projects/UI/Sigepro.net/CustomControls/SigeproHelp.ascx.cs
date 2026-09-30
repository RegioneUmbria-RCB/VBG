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
using Init.SIGePro.Verticalizzazioni;
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net.Navigation;

public partial class CustomControls_SigeproHelp : System.Web.UI.UserControl
{

    private BasePage basePage
    {
        get { return this.Page as BasePage; }
    }

	public bool Nascondi
	{
		get { object o = this.ViewState["Nascondi"]; return o == null ? false : (bool)o; }
		set { this.ViewState["Nascondi"] = value; }
	}



	protected void Page_Load(object sender, EventArgs e)
	{

        this.Visible = false;

        //Deve essere visibile se la verticalizzazione è attiva e l'utente loggato è definito nella verticalizzazione
        VerticalizzazioneHelpbase vhb = new VerticalizzazioneHelpbase(basePage.AuthenticationInfo.Alias, basePage.Software);
        if (vhb.Attiva)
        {
            if (vhb.Codiceoperatore == basePage.AuthenticationInfo.CodiceResponsabile.ToString())
            {
                this.Visible = true;
                return;
            }
        }

        //Deve essere visibile se l'utente loggato può modificare l'help
        ResponsabiliMgr respMgr = new ResponsabiliMgr(basePage.Database);
        Responsabili resp = respMgr.GetById(basePage.AuthenticationInfo.CodiceResponsabile.ToString(),basePage.AuthenticationInfo.IdComune);
        
        if (resp!=null)
        {
            if (resp.UPDATEHELP == "1")
            {
                this.Visible = true;
                return;
            }
        }
        

        //Deve essere visibile se c'è l'helpbase già scritto per la pagina corrente
        HelpBaseMgr hbm = new HelpBaseMgr(basePage.Database);
        if (hbm.recordCount("HELPBASE", "CONTENTTYPE", string.Format("Where CONTENTTYPE='{1}'", basePage.AuthenticationInfo.IdComune, basePage.AppRelativeVirtualPath)) >= 1)
        {
            this.Visible = true;
            return;
        }

        //Deve essere visibile se c'è l'help già scritto per la pagina corrente
        HelpMgr hm = new HelpMgr(basePage.Database);
        if (hm.recordCount("HELP", "CONTENTTYPE", string.Format("Where IDCOMUNE='{0}' and CONTENTTYPE='{1}'", basePage.AuthenticationInfo.IdComune, basePage.AppRelativeVirtualPath)) >= 1)
        {
            this.Visible = true;
            return;
        }        
        
	}

	protected override void OnPreRender(EventArgs e)
	{
		base.OnPreRender(e);

		if (this.Nascondi)
		{
			this.Visible = false;
		}
	}

	private string GetScriptName()
	{
        return Request.AppRelativeCurrentExecutionFilePath;
	}

	public string GetHelpPath()
	{
		return basePage.BuildSigeproPath( "cl_help.asp" , "Help=" + GetScriptName() + "&Tab=" + (int) basePage.TabSelezionato );
	}
}
