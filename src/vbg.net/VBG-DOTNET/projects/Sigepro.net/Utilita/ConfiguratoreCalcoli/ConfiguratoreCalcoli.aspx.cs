
using SIGePro.Net;
using System;
using System.Web;

public partial class Utilita_ConfiguratoreCalcoli_ConfiguratoreCalcoli : BasePage
{
    public string Id
    {
        get { return this.Request.QueryString["Id"]; }
    }

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!this.IsPostBack)
        {
            if (!String.IsNullOrEmpty(this.Id))
            {
                this.MostraDettaglio();
            }
            else
            {
                this.gvLista.DataBind();
            }
        }
    }

    private void MostraDettaglio()
    {
        this.multiView.ActiveViewIndex = 1;
    }

    protected string UrlApiConfigurazioneCalcoli => ResolveClientUrl($"~/web-api/configuratorecalcoli/{this.Token}");


    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (1):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
            default:
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
        }
    }

    protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
    {
        string id = this.gvLista.DataKeys[this.gvLista.SelectedIndex].Values[0].ToString();
        string versione = this.gvLista.DataKeys[this.gvLista.SelectedIndex].Values[1].ToString();

        string returnTo = HttpUtility.UrlEncode(HttpContext.Current.Request.Url.AbsoluteUri);
        string urlCalcolatore = $"../../../ConfiguratoreCalcoli/IndexV{versione}.html?Token={this.Token}&Id={id}&ReturnTo={returnTo}";

        this.Response.Redirect(ResolveClientUrl(urlCalcolatore));
    }
    public void cmdNuovo_Click(object sender, EventArgs e)
    {
        string returnTo = HttpUtility.UrlEncode(HttpContext.Current.Request.Url.AbsoluteUri);
        string urlCalcolatore = $"../../../ConfiguratoreCalcoli/IndexV2.html?Token={this.Token}&ReturnTo={returnTo}";

        this.Response.Redirect(ResolveClientUrl(urlCalcolatore));
    }

    public void cmdChiudiLista_Click(object sender, EventArgs e)
    {
        base.CloseCurrentPage();
    }
}