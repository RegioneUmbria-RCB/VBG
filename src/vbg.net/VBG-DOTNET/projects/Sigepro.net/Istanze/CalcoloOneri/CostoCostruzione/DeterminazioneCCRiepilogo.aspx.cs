using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;
using System;
using System.Web.UI.WebControls;


public partial class Istanze_CalcoloOneri_CostoCostruzione_DeterminazioneCCRiepilogo : BasePage
{
    public Istanze_CalcoloOneri_CostoCostruzione_DeterminazioneCCRiepilogo()
    {
        //VerificaSoftware = false;
    }

    public bool Tabella3HaDettagliSuperficie = false;
    private CCICalcoli m_calcolo = null;

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

    private Istanze m_istanza = null;

    protected Istanze Istanza
    {
        get
        {
            if (this.m_istanza == null)
                this.m_istanza = new IstanzeMgr(this.Database).GetById(this.IdComune, this.Calcolo.Codiceistanza.Value);

            return this.m_istanza;
        }
    }

    public override string Software
    {
        get
        {
            return this.Istanza.SOFTWARE;
        }
    }

    public decimal GetSu()
    {
        return this.Calcolo.Su.GetValueOrDefault(0.0m);
    }

    public decimal GetSuArt9()
    {
        return this.Calcolo.SuArt9.GetValueOrDefault(0.0m);
    }

    public decimal GetSa()
    {
        return this.Calcolo.Sa.GetValueOrDefault(0.0m);
    }

    public decimal GetSt()
    {
        return this.Calcolo.St.GetValueOrDefault(0.0m);
    }

    public decimal GetSnr()
    {
        return this.Calcolo.Snr.GetValueOrDefault(0.0m);
    }

    public decimal GetSc()
    {
        return this.Calcolo.Sc.GetValueOrDefault(0.0m);
    }

    public decimal GetI1()
    {
        return this.Calcolo.I1.GetValueOrDefault(0.0m);
    }

    public decimal GetI2()
    {
        return this.Calcolo.I2.GetValueOrDefault(0.0m);
    }

    public decimal GetI3()
    {
        return this.Calcolo.I3.GetValueOrDefault(0.0m);
    }

    public decimal GetCostoCostruzioneMq()
    {
        return this.Calcolo.Costocmq.GetValueOrDefault(0.0m);
    }

    public decimal GetCostoCostruzioneMaggiorato()
    {
        return this.Calcolo.CostocmqMaggiorato.GetValueOrDefault(0.0m);
    }

    public decimal GetCostoEdificio()
    {
        var tContributo = new CCICalcoloTContributoMgr(this.Database).GetByIdCalcolo(this.Calcolo.Idcomune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));

        return tContributo.CostocEdificio.GetValueOrDefault(0.0m);
    }

    public string GetClasseEdificio()
    {
        return new CCTabellaClassiEdificioMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, this.Calcolo.FkCctceId.GetValueOrDefault(int.MinValue)).Descrizione;

    }

    public decimal GetMaggiorazione()
    {
        return this.Calcolo.Maggiorazione.GetValueOrDefault(0.0m);
    }


    protected void Page_Load(object sender, EventArgs e)
    {
        this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
    }

    protected void rptTabella1_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var lblClasseSuperficie = (Label)e.Item.FindControl("lblClasseSuperficie");
            var tab1 = (CCITabella1)e.Item.DataItem;

            lblClasseSuperficie.Text = new CCClassiSuperficiMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, tab1.FkCccsId.GetValueOrDefault(int.MinValue)).Classe;
        }
    }
    protected void rptTabella2_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var lblDestinazione = (Label)e.Item.FindControl("lblDestinazione");
            var tab2 = (CCITabella2)e.Item.DataItem;

            lblDestinazione.Text = new CCDettagliSuperficieMgr(this.Database).GetById(this.AuthenticationInfo.IdComune, tab2.FkCcdsId.GetValueOrDefault(int.MinValue)).Descrizione;
        }
    }
    protected void rptTabella3_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {
        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var lblIntervallo = (Label)e.Item.FindControl("lblIntervallo");
            var tab3 = (CCITabella3)e.Item.DataItem;

            lblIntervallo.Text = new CCTabella3Mgr(this.Database).GetById(this.AuthenticationInfo.IdComune, tab3.FkCct3Id.GetValueOrDefault(int.MinValue)).Descrizione;
        }
    }
    protected void rptTabella4_ItemDataBound(object sender, RepeaterItemEventArgs e)
    {


        if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
        {
            var lblCaratteristica = (Label)e.Item.FindControl("lblCaratteristica");
            var tab4 = (CCITabella4)e.Item.DataItem;

            var tabCar = new CCTabellaCaratteristMgr(this.Database).GetById(tab4.Idcomune, tab4.FkCctcId.GetValueOrDefault(int.MinValue));
            lblCaratteristica.Text = tabCar.Descrizione;
        }
    }
    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
        var ict = new CCICalcoloTotMgr(this.Database).GetByIdICalcolo(this.AuthenticationInfo.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));

        var url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

        this.Response.Redirect(String.Format(url, this.AuthenticationInfo.Token, this.Calcolo.Codiceistanza, ict == null ? "" : ict.Id.ToString()));
    }
    protected void CCITabella3DataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
    {
        var mgr = new CCITabella3Mgr(this.Database);
        this.Tabella3HaDettagliSuperficie = mgr.HaTipiSuperficie(this.IdComune, this.Calcolo.Id.GetValueOrDefault(int.MinValue));
    }
}
