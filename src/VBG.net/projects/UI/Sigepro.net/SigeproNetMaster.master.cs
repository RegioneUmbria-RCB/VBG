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
using Init.SIGePro.Authentication;
using Init.SIGePro.Manager;
using System.Collections.Generic;

public partial class SigeproNetMaster : MasterPage
{
	public List<string> Errori { get; set; }

	public SigeproNetMaster()
	{
		Errori = new List<string>();
	}

	public bool MostraHelp
	{
		get { return !SigeproHelp1.Nascondi; }
		set { SigeproHelp1.Nascondi = !value; }
	}


	public IntestazionePaginaTipiTabEnum TabSelezionato
	{
		get { return intestazionePagina.TabSelezionato; }
		set { intestazionePagina.TabSelezionato = value; }
	}

	protected void Page_Load(object sender, EventArgs e)
	{
		intestazionePagina.TitoloPagina = this.Page.Title;
	}

	protected override void OnPreRender(EventArgs e)
	{
		rptErrori.Visible = Errori.Count > 0;

		if (Errori.Count > 0)
		{
			rptErrori.DataSource = Errori;
			rptErrori.DataBind();
		}


		base.OnPreRender(e);
	}
}


