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

using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCITabella4 : BasePage
{
	CCICalcoli m_calcolo = null;
	Init.SIGePro.Data.Istanze m_istanza = null;

	CCICalcoli Calcolo
	{
		get
		{
			if (m_calcolo == null)
				m_calcolo = new CCICalcoliMgr(Database).GetById(IdComune, Convert.ToInt32(Request.QueryString["IdCalcolo"]));

			return m_calcolo;
		}
	}

	Init.SIGePro.Data.Istanze Istanza
	{
		get
		{
			if (m_istanza == null)
                m_istanza = new IstanzeMgr(Database).GetById(IdComune, Convert.ToInt32(Calcolo.Codiceistanza));

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

	public Istanze_CalcoloOneri_CostoCostruzione_CCITabella4()
	{
		//VerificaSoftware = false;
	}

	protected void Page_Load(object sender, EventArgs e)
	{
		Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

		if (!IsPostBack)
		{
			VerificaRighe();
			VerificaBottoneChiudi();
		}
	}

	private void VerificaBottoneChiudi()
	{
		CCConfigurazione config = new CCConfigurazioneMgr(Database).GetById(IdComune, Istanza.SOFTWARE);
		cmdChiudi.Visible = config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO;
	}

	private void VerificaRighe()
	{
        List<CCITabella4> righeInTabella4 = new CCICalcoliMgr(Database).VerificaEsistenzaRigheInTabella4(IdComune, Istanza.SOFTWARE, Calcolo.Id.GetValueOrDefault(int.MinValue), Calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));
		GridView1.DataBind();
	}
	protected void GridView1_RowDataBound(object sender, GridViewRowEventArgs e)
	{
		if (e.Row.RowType == DataControlRowType.DataRow)
		{
			Label lblDescrizioneCaratteristica = (Label)e.Row.FindControl("lblDescrizioneCaratteristica");
			CCITabella4 tab4 = (CCITabella4)e.Row.DataItem;

            CCTabellaCaratterist tabCar = new CCTabellaCaratteristMgr(Database).GetById(tab4.Idcomune, tab4.FkCctcId.GetValueOrDefault(int.MinValue));
			lblDescrizioneCaratteristica.Text = tabCar.Descrizione;
		}
	}

	protected void cmdProcedi_Click(object sender, EventArgs e)
	{
		CCITabella4Mgr mgr = new CCITabella4Mgr(Database);

		for (int i = 0; i < GridView1.Rows.Count; i++)
		{
			GridViewRow row = GridView1.Rows[i];
			CheckBox chkSelezionata = (CheckBox)row.FindControl("chkSelezionata");


			int id = Convert.ToInt32( GridView1.DataKeys[i][0] );

			CCITabella4 t4 = mgr.GetById(AuthenticationInfo.IdComune, id);

			t4.Selezionata = chkSelezionata.Checked ? 1 : 0;

			mgr.Update(t4);
		}

		CCConfigurazione config = new CCConfigurazioneMgr(Database).GetById(IdComune, Istanza.SOFTWARE);

		string url = "";

		if (config.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
		{
			url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabellaSnSa.aspx?Token={0}&IdCalcolo={1}";
		}
		else
		{
            ElaboratoreCostoCostruzione elab = new ElaboratoreCostoCostruzione(Database, IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));
			elab.Elabora();

			url = "~/Istanze/CalcoloOneri/CostoCostruzione/DeterminazioneCCRiepilogo.aspx?Token={0}&IdCalcolo={1}";
		}


		Response.Redirect(String.Format(url, AuthenticationInfo.Token, Calcolo.Id));
	}

	protected void cmdChiudi_Click(object sender, EventArgs e)
	{
		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella2.aspx?Token={0}&IdCalcolo={1}";
		Response.Redirect(String.Format(url, AuthenticationInfo.Token, Calcolo.Id));
	}
}
