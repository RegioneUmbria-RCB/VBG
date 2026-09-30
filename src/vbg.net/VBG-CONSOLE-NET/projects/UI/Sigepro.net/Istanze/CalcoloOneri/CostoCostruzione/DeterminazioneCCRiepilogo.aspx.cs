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
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using SIGePro.Net;


public partial class Istanze_CalcoloOneri_CostoCostruzione_DeterminazioneCCRiepilogo : BasePage
{
	public Istanze_CalcoloOneri_CostoCostruzione_DeterminazioneCCRiepilogo()
	{
		//VerificaSoftware = false;
	}

    public bool Tabella3HaDettagliSuperficie = false;
	CCICalcoli m_calcolo = null;

	private CCICalcoli Calcolo
	{
		get
		{
			int id = int.Parse(Request.QueryString["IdCalcolo"]);

			if (m_calcolo == null)
				m_calcolo = new CCICalcoliMgr(Database).GetById(AuthenticationInfo.IdComune, id);

			return m_calcolo;
		}
	}

	Istanze m_istanza = null;

	protected Istanze Istanza
	{
		get
		{
			if (m_istanza == null)
                m_istanza = new IstanzeMgr(Database).GetById(IdComune, Calcolo.Codiceistanza.Value);

			return m_istanza;
		}
	}

	public override string Software
	{
		get
		{
			return Istanza.SOFTWARE;
		}
	}

	public double GetSu()
	{
        return Calcolo.Su.GetValueOrDefault(0.0d);
	}

	public double GetSuArt9()
	{
        return Calcolo.SuArt9.GetValueOrDefault(0.0d);
	}

	public double GetSa()
	{
        return Calcolo.Sa.GetValueOrDefault(0.0d);
	}

	public double GetSt()
	{
        return Calcolo.St.GetValueOrDefault(0.0d);
	}

	public double GetSnr()
	{
		return Calcolo.Snr.GetValueOrDefault(0.0d);
	}

	public double GetSc()
	{
		return Calcolo.Sc.GetValueOrDefault(0.0d);
	}

	public double GetI1()
	{
		return Calcolo.I1.GetValueOrDefault(0.0d);
	}

	public double GetI2()
	{
		return Calcolo.I2.GetValueOrDefault(0.0d);
	}

	public double GetI3()
	{
		return Calcolo.I3.GetValueOrDefault(0.0d);
	}

	public double GetCostoCostruzioneMq()
	{
		return Calcolo.Costocmq.GetValueOrDefault(0.0d);
	}

	public double GetCostoCostruzioneMaggiorato()
	{
		return Calcolo.CostocmqMaggiorato.GetValueOrDefault(0.0d);
	}

	public double GetCostoEdificio()
	{
		CCICalcoloTContributo tContributo = new CCICalcoloTContributoMgr(Database).GetByIdCalcolo(Calcolo.Idcomune, Calcolo.Id.GetValueOrDefault(int.MinValue));

		return tContributo.CostocEdificio.GetValueOrDefault(0.0d);
	}

	public string GetClasseEdificio()
	{
        return new CCTabellaClassiEdificioMgr(Database).GetById(AuthenticationInfo.IdComune, Calcolo.FkCctceId.GetValueOrDefault(int.MinValue)).Descrizione;

	}

	public double GetMaggiorazione()
	{
		return Calcolo.Maggiorazione.GetValueOrDefault(0.0d);
	}

	
	protected void Page_Load(object sender, EventArgs e)
	{
		Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
	}

	protected override void Render(HtmlTextWriter writer)
	{
		base.Render(writer);
	}

	protected void rptTabella1_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			Label lblClasseSuperficie = (Label)e.Item.FindControl("lblClasseSuperficie");
			CCITabella1 tab1 = (CCITabella1)e.Item.DataItem;

            lblClasseSuperficie.Text = new CCClassiSuperficiMgr(Database).GetById(AuthenticationInfo.IdComune, tab1.FkCccsId.GetValueOrDefault(int.MinValue)).Classe;
		}
	}
	protected void rptTabella2_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			Label lblDestinazione = (Label)e.Item.FindControl("lblDestinazione");
			CCITabella2 tab2 = (CCITabella2)e.Item.DataItem;

            lblDestinazione.Text = new CCDettagliSuperficieMgr(Database).GetById(AuthenticationInfo.IdComune, tab2.FkCcdsId.GetValueOrDefault(int.MinValue)).Descrizione;
		}
	}
	protected void rptTabella3_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			Label lblIntervallo = (Label)e.Item.FindControl("lblIntervallo");
			CCITabella3 tab3 = (CCITabella3)e.Item.DataItem;

            lblIntervallo.Text = new CCTabella3Mgr(Database).GetById(AuthenticationInfo.IdComune, tab3.FkCct3Id.GetValueOrDefault(int.MinValue)).Descrizione;
		}
	}
	protected void rptTabella4_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		

		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			Label lblCaratteristica = (Label)e.Item.FindControl("lblCaratteristica");
			CCITabella4 tab4 = (CCITabella4)e.Item.DataItem;

            CCTabellaCaratterist tabCar = new CCTabellaCaratteristMgr(Database).GetById(tab4.Idcomune, tab4.FkCctcId.GetValueOrDefault(int.MinValue));
			lblCaratteristica.Text = tabCar.Descrizione;
		}
	}
	protected void cmdChiudi_Click(object sender, EventArgs e)
	{
        CCICalcoloTot ict = new CCICalcoloTotMgr(Database).GetByIdICalcolo(AuthenticationInfo.IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));

		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

		Response.Redirect(String.Format(url, AuthenticationInfo.Token, Calcolo.Codiceistanza , ict == null ? "" : ict.Id.ToString()));
	}
    protected void CCITabella3DataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
    {
        CCITabella3Mgr mgr = new CCITabella3Mgr(Database);
        Tabella3HaDettagliSuperficie = mgr.HaTipiSuperficie(IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));
    }
}
