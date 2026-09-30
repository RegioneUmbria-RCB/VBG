using System;
using System.Collections.Generic;
using System.Web.UI;

public partial class SigeproNetMaster : MasterPage
{
    public List<string> Errori { get; set; }

    public SigeproNetMaster()
    {
        this.Errori = new List<string>();
    }

    public IntestazionePaginaTipiTabEnum TabSelezionato
    {
        get { return this.intestazionePagina.TabSelezionato; }
        set { this.intestazionePagina.TabSelezionato = value; }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        this.intestazionePagina.TitoloPagina = this.Page.Title;
    }

    protected override void OnPreRender(EventArgs e)
    {
        this.rptErrori.Visible = this.Errori.Count > 0;

        if (this.Errori.Count > 0)
        {
            this.rptErrori.DataSource = this.Errori;
            this.rptErrori.DataBind();
        }


        base.OnPreRender(e);
    }
}


