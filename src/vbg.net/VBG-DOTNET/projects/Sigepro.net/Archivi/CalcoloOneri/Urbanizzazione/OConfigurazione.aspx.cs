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
using SIGePro.Net;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net.Navigation;
using Init.SIGePro.Exceptions;

public partial class Archivi_CalcoloOneri_Urbanizzazione_OConfigurazione : BasePage
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!Page.IsPostBack)
        {
            BindCombo();
            OConfigurazione oc = new OConfigurazioneMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Software);
            BindDettaglio(oc);
        }
    }

    private void BindCombo()
    {
        TipiAree ta = new TipiAree();
        ta.Idcomune = AuthenticationInfo.IdComune;
        ta.Software = Software;

        TipiUnitaMisura tum = new TipiUnitaMisura();
        tum.Idcomune = AuthenticationInfo.IdComune;

        ddlTipiAreeCodicePrg.Item.DataSource = new TipiAreeMgr(AuthenticationInfo.CreateDatabase()).GetList(ta);
        ddlTipiAreeCodicePrg.Item.DataBind();
        ddlTipiAreeCodicePrg.Item.Items.Insert(0, "");

        ddlTipiAreeCodiceZto.Item.DataSource = new TipiAreeMgr(AuthenticationInfo.CreateDatabase()).GetList(ta);
        ddlTipiAreeCodiceZto.DataBind();
        ddlTipiAreeCodiceZto.Item.Items.Insert(0, "");

        ddlUnitaMisuraMc.Item.DataSource = new TipiUnitaMisuraMgr(AuthenticationInfo.CreateDatabase()).GetList(tum);
        ddlUnitaMisuraMc.Item.DataBind();
        ddlUnitaMisuraMc.Item.Items.Insert(0, "");

        ddlUnitaMisuraMq.Item.DataSource = new TipiUnitaMisuraMgr(AuthenticationInfo.CreateDatabase()).GetList(tum);
        ddlUnitaMisuraMq.Item.DataBind();
        ddlUnitaMisuraMq.Item.Items.Insert(0, "");
    }

    private void BindDettaglio(OConfigurazione oc)
    {
        if (oc != null)
        {
            if (ddlTipiAreeCodicePrg.Item.Items.Count > 0)
                ddlTipiAreeCodicePrg.Item.SelectedValue = (oc.FkTipiareeCodicePrg.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : oc.FkTipiareeCodicePrg.ToString();

            if (ddlTipiAreeCodiceZto.Item.Items.Count > 0)
                ddlTipiAreeCodiceZto.Item.SelectedValue = (oc.FkTipiareeCodiceZto.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : oc.FkTipiareeCodiceZto.ToString();
            
            if (ddlUnitaMisuraMq.Item.Items.Count > 0)
                ddlUnitaMisuraMq.Item.SelectedValue = (oc.FkTumUmidMq.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : oc.FkTumUmidMq.ToString();

            if (ddlUnitaMisuraMc.Item.Items.Count > 0)
                ddlUnitaMisuraMc.Item.SelectedValue = (oc.FkTumUmidMc.GetValueOrDefault(int.MinValue) == int.MinValue) ? "" : oc.FkTumUmidMc.ToString();

			chkUsaDettaglioSup.Item.Checked = oc.Usadettagliosup == 1;
        }

    }

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        OConfigurazione oc = new OConfigurazione();

        try
        {
            oc.Idcomune = AuthenticationInfo.IdComune;
            oc.Software = Software;

            oc.FkTipiareeCodicePrg =  null;
            if(!string.IsNullOrEmpty(ddlTipiAreeCodicePrg.Item.SelectedValue))
                oc.FkTipiareeCodicePrg =  Convert.ToInt32(ddlTipiAreeCodicePrg.Item.SelectedValue);

            oc.FkTipiareeCodiceZto = null;
            if(!string.IsNullOrEmpty(ddlTipiAreeCodiceZto.Item.SelectedValue))
                oc.FkTipiareeCodiceZto = Convert.ToInt32(ddlTipiAreeCodiceZto.Item.SelectedValue);

            oc.FkTumUmidMq = null;
            if(!string.IsNullOrEmpty(ddlUnitaMisuraMq.Item.SelectedValue))
                oc.FkTumUmidMq = Convert.ToInt32(ddlUnitaMisuraMq.Item.SelectedValue);

            oc.FkTumUmidMc = null;
            if(!string.IsNullOrEmpty(ddlUnitaMisuraMc.Item.SelectedValue))
                oc.FkTumUmidMc = Convert.ToInt32(ddlUnitaMisuraMc.Item.SelectedValue);
            
			oc.Usadettagliosup = chkUsaDettaglioSup.Item.Checked ? 1 : 0;

            OConfigurazioneMgr mgr = new OConfigurazioneMgr(AuthenticationInfo.CreateDatabase());

            mgr.Save(oc);

            BindDettaglio(oc);
        }
        catch (RequiredFieldException rfe)
        {
            MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            MostraErrore("Errore durante il salvataggio: " + ex.Message, ex);
        }
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage();
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
    }

    protected void cmdAltriDati_Click(object sender, EventArgs e)
    {
        Response.Redirect("OConfigurazioneTipiOnere.aspx?software=" + Software + "&token=" + Token);
    }
}
