using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliDettaglio : BasePage
{
    public override string Software
    {
        get
        {
            return this.Istanza.SOFTWARE;
        }
    }


    private CCICalcoli m_calcolo = null;
    private Istanze m_istanza = null;


    private CCICalcoli Calcolo
    {
        get
        {
            var id = int.Parse(this.Request.QueryString["IdCalcolo"]);

            if (this.m_calcolo == null)
                this.m_calcolo = new CCICalcoliMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, id);

            return this.m_calcolo;
        }
    }

    private Istanze Istanza
    {
        get
        {
            if (this.m_istanza == null)
                this.m_istanza = new IstanzeMgr(this.Database).GetById(this.Calcolo.Idcomune, this.Calcolo.Codiceistanza.Value);

            return this.m_istanza;
        }
    }


    protected void Page_Load(object sender, EventArgs e)
    {
        this.ImpostaScriptEliminazione(this.cmdElimina);
        this.ImpostaScriptEliminazione(this.cmdEliminaDettaglio);

        if (!this.IsPostBack)
        {
            this.DataBind(-1);
        }
    }

    protected void DataBind(int headerId)
    {
        var selIdx = 0;

        this.gvTestata.DataBind();

        this.multiView.ActiveViewIndex = 0;

        if (this.gvTestata.Rows.Count > 0)
        {
            if (headerId > 0)
            {
                for (var i = 0; i < this.gvTestata.DataKeys.Count; i++)
                {
                    if (headerId.Equals((int)this.gvTestata.DataKeys[i][0]))
                        selIdx = i;
                }
            }

            this.gvTestata.SelectedIndex = selIdx;
            this.gvTestata_SelectedIndexChanged(this, EventArgs.Empty);
        }
    }

    protected void multiView_ActiveViewChanged(object sender, EventArgs e)
    {
        switch (this.multiView.ActiveViewIndex)
        {
            case (0):
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
                return;
            default:
                this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
                return;
        }
    }

    protected void ddlTipologiaSuperficie_SelectedIndexChanged(object sender, EventArgs e)
    {
        this.ddlDettaglioSuperficie.DataSource = new CCDettagliSuperficieMgr(this.Database).GetList(this.AuthenticationInfo.IdComune, int.MinValue, Convert.ToInt32(this.ddlTipologiaSuperficie.SelectedValue), null, null, this.Istanza.SOFTWARE);
        this.ddlDettaglioSuperficie.DataBind();
    }

    protected void cmdNuovo_Click(object sender, EventArgs e)
    {
        this.BindEditTestata(new CCICalcoliDettaglioT());
    }

    private void BindEditTestata(CCICalcoliDettaglioT cls)
    {
        this.multiView.ActiveViewIndex = 1;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblId.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();

        this.ddlTipologiaSuperficie.DataSource = new CCTipiSuperficieMgr(this.Database).GetList(this.AuthenticationInfo.IdComune, int.MinValue, null, null, this.Istanza.SOFTWARE);
        this.ddlTipologiaSuperficie.DataBind();

        if (!this.IsInserting)
            this.ddlTipologiaSuperficie.SelectedValue = cls.FkCctsId.ToString();
        else
            this.ddlTipologiaSuperficie.SelectedIndex = 0;

        this.ddlTipologiaSuperficie_SelectedIndexChanged(this, EventArgs.Empty);

        if (!this.IsInserting && cls.FkCcdsId.GetValueOrDefault(int.MinValue) != int.MinValue)
            this.ddlDettaglioSuperficie.SelectedValue = cls.FkCcdsId.ToString();
        else
            if (this.ddlDettaglioSuperficie.Items.Count != 0)
                this.ddlDettaglioSuperficie.SelectedIndex = 0;

        this.txtDescrizione.Text = cls.Descrizione;
        this.itbAlloggi.Value = cls.Alloggi.GetValueOrDefault(int.MinValue) == int.MinValue ? "1" : cls.Alloggi.ToString();

        this.cmdElimina.Visible = !this.IsInserting;

        this.dtbValoreTestata.Item.ValoreDecimal = this.IsInserting ? 0.0m : cls.Su;

        if (!this.IsInserting)
        {
            var totaleModificabile = cls.Su <= 0.0m || (cls.Su > 0 && CCICalcoliDettaglioRMgr.Find(this.Token, cls.Id.GetValueOrDefault(int.MinValue)).Count == 0);

            this.dtbValoreTestata.Item.ReadOnly = !totaleModificabile;
        }

    }

    protected void gvTestata_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "EditTestata")
        {
            var id = Convert.ToInt32(e.CommandArgument);

            var cls = new CCICalcoliDettaglioTMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, id);
            this.BindEditTestata(cls);

        }
    }

    protected void gvDettaglio_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "EditDettaglio")
        {
            var id = Convert.ToInt32(e.CommandArgument);

            var cls = new CCICalcoliDettaglioRMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, id);
            this.BindEditDettaglio(cls);

        }
    }

    protected void cmdSalva_Click(object sender, EventArgs e)
    {
        var cls = new CCICalcoliDettaglioT();
        var mgr = new CCICalcoliDettaglioTMgr(this.Database);

        var id = this.IsInserting ? int.MinValue : Convert.ToInt32(this.lblId.Text);

        if (!this.IsInserting)
        {
            cls = mgr.GetById(this.Calcolo.Idcomune, id);
        }
        else
        {
            cls.Su = 0.0m;
        }

        cls.Idcomune = this.Calcolo.Idcomune;
        cls.Id = id;
        cls.Codiceistanza = this.Calcolo.Codiceistanza;
        cls.FkCcicId = this.Calcolo.Id;
        cls.FkCctsId = Convert.ToInt32(this.ddlTipologiaSuperficie.SelectedValue);
        cls.FkCcdsId = String.IsNullOrEmpty(this.ddlDettaglioSuperficie.SelectedValue) ? (int?)null : Convert.ToInt32(this.ddlDettaglioSuperficie.SelectedValue);
        cls.Descrizione = this.txtDescrizione.Text;
        cls.Alloggi = this.itbAlloggi.Item.ValoreInt.GetValueOrDefault(1);
        cls.Su = this.dtbValoreTestata.Item.ValoreDecimal;

        try
        {
            if (this.IsInserting)
                mgr.Insert(cls);
            else
                mgr.Update(cls);

            this.DataBind(cls.Id.GetValueOrDefault(int.MinValue));
        }
        catch (Exception ex)
        {
            this.MostraErrore("Errore durante l'inserimento: " + ex.Message, ex);
        }
    }

    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        this.DataBind(this.IsInserting ? -1 : Convert.ToInt32(this.lblId.Text));
    }

    protected void txtNumero_TextChanged(object sender, EventArgs e)
    {
        var nro = (decimal)(this.txtNumero.ValoreInt.GetValueOrDefault(0));
        var lung = this.txtLunghezza.ValoreDecimal ?? 0.0m;
        var larg = this.txtLarghezza.ValoreDecimal ?? 0.0m;

        this.txtSuperficieUtile.ValoreDecimal = lung * larg * nro;
    }

    protected void cmdNuovoDettaglio_Click(object sender, EventArgs e)
    {
        this.BindEditDettaglio(new CCICalcoliDettaglioR());
    }

    private void BindEditDettaglio(CCICalcoliDettaglioR cls)
    {
        this.multiView.ActiveViewIndex = 2;

        this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

        this.lblIdDettaglio.Text = this.IsInserting ? "Nuovo" : cls.Id.ToString();
        this.txtNumero.ValoreInt = this.IsInserting ? 1 : (int)cls.Qta;
        this.txtLunghezza.ValoreDecimal = cls?.Lung ?? 0.0m;
        this.txtLarghezza.ValoreDecimal = cls?.Larg ?? 0.0m;

        this.txtSuperficieUtile.ValoreDecimal = cls?.Su ?? 0.0m;

        this.cmdEliminaDettaglio.Visible = !this.IsInserting;
    }

    protected void cmdSalvaDettaglio_Click(object sender, EventArgs e)
    {
        var mgr = new CCICalcoliDettaglioRMgr(this.Database);

        var id = this.IsInserting ? int.MinValue : Convert.ToInt32(this.lblIdDettaglio.Text);

        var cls = this.IsInserting ? new CCICalcoliDettaglioR() : mgr.GetById(this.AuthenticationInfo.IdComune, id);

        cls.Idcomune = this.Calcolo.Idcomune;
        cls.Id = id;


        cls.Codiceistanza = this.Calcolo.Codiceistanza;
        cls.FkCcicdtId = Convert.ToInt32(this.gvTestata.DataKeys[this.gvTestata.SelectedIndex][0]);

        cls.Qta = this.txtNumero.ValoreInt;
        cls.Lung = this.txtLunghezza.ValoreDecimal;
        cls.Larg = this.txtLarghezza.ValoreDecimal;
        cls.Su = this.txtSuperficieUtile.ValoreDecimal;

        if (this.IsInserting)
            mgr.Insert(cls);
        else
            mgr.Update(cls);

        this.DataBind(cls.FkCcicdtId.GetValueOrDefault(int.MinValue));
    }

    protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
    {
        this.DataBind(Convert.ToInt32(this.gvTestata.DataKeys[this.gvTestata.SelectedIndex][0]));
    }

    protected void gvTestata_SelectedIndexChanged(object sender, EventArgs e)
    {
        var idTestata = Convert.ToInt32(this.gvTestata.DataKeys[this.gvTestata.SelectedIndex][0]);

        var testata = new CCICalcoliDettaglioTMgr(this.Database).GetById(this.IdComune, idTestata);

        this.pnlDettaglioSuperfici.Visible = testata.Su <= 0.0m || (testata.Su > 0 && CCICalcoliDettaglioRMgr.Find(this.Token, idTestata).Count > 0);

        if (this.pnlDettaglioSuperfici.Visible)
            this.gvDettaglio.DataBind();
    }

    protected void CCICalcoloDettaglioRDataSOurce_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
    {
        if (this.gvTestata.SelectedIndex < 0) return;

        e.InputParameters[1] = this.gvTestata.DataKeys[this.gvTestata.SelectedIndex][0];
    }

    protected void cmdEliminaDettaglio_Click(object sender, EventArgs e)
    {
        var mgr = new CCICalcoliDettaglioRMgr(this.Database);

        try
        {
            var id = Convert.ToInt32(this.lblIdDettaglio.Text);

            var cls = mgr.GetById(this.AuthenticationInfo.IdComune, id);

            mgr.Delete(cls);

            this.DataBind(Convert.ToInt32(this.gvTestata.DataKeys[this.gvTestata.SelectedIndex][0]));
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
    }

    protected void cmdElimina_Click(object sender, EventArgs e)
    {
        var id = Convert.ToInt32(this.lblId.Text);

        try
        {
            var mgr = new CCICalcoliDettaglioTMgr(this.Database);
            var cls = mgr.GetById(this.AuthenticationInfo.IdComune, id);

            mgr.Delete(cls);

            this.DataBind(-1);
        }
        catch (Exception ex)
        {
            this.MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
        }
    }

    protected void gvTestata_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        if (e.Row.RowType == DataControlRowType.DataRow)
        {
            var cls = (CCICalcoliDettaglioT)e.Row.DataItem;
            var lblTipologiaSuperficie = (Label)e.Row.FindControl("lblTipologiaSuperficie");
            var lblDettaglioSuperficie = (Label)e.Row.FindControl("lblDettaglioSuperficie");
            var ibSeleziona = (ImageButton)e.Row.FindControl("ibSeleziona");

            lblTipologiaSuperficie.Text = new CCTipiSuperficieMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, cls.FkCctsId.GetValueOrDefault(int.MinValue)).Descrizione;

            if (cls.FkCcdsId.GetValueOrDefault(int.MinValue) == int.MinValue)
            {
                lblDettaglioSuperficie.Text = "";
            }
            else
            {
                var ds = new CCDettagliSuperficieMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, cls.FkCcdsId.GetValueOrDefault(int.MinValue));

                lblDettaglioSuperficie.Text = ds.Descrizione;
            }

            if (cls.Su > 0 && CCICalcoliDettaglioRMgr.Find(this.Token, cls.Id.GetValueOrDefault(int.MinValue)).Count == 0) // la Su è stata imputata a mano
            {
                ibSeleziona.Visible = false;
            }
        }
    }

    protected void cmdIndietro_Click(object sender, EventArgs e)
    {

        var ict = new CCICalcoloTotMgr(this.Database).GetByIdICalcolo(this.AuthenticationInfo.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));

        var url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

        this.Response.Redirect(String.Format(url, this.AuthenticationInfo.Token, this.Calcolo.Codiceistanza, ict == null ? "" : ict.Id.ToString()));
    }

    protected void cmdProcedi_Click(object sender, EventArgs e)
    {
        var elab = new ElaboratoreCostoCostruzione(this.Database, this.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));
        elab.Elabora();


        var url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";

        this.Response.Redirect(String.Format(url, this.AuthenticationInfo.Token, this.Calcolo.Id));
    }
}
