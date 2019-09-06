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
using Init.Utils;
using Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione;
using System.Collections.Generic;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliDettaglio : BasePage
{
	public override string Software
	{
		get
		{
			return Istanza.SOFTWARE;
		}
	}


	CCICalcoli m_calcolo = null;
	Istanze m_istanza = null;


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

	private Istanze Istanza
	{
		get
		{
			if (m_istanza == null)
                m_istanza = new IstanzeMgr(Database).GetById(Calcolo.Idcomune, Calcolo.Codiceistanza.Value);

			return m_istanza;
		}
	}


	protected void Page_Load(object sender, EventArgs e)
	{
		ImpostaScriptEliminazione(cmdElimina);
		ImpostaScriptEliminazione(cmdEliminaDettaglio);

		if (!IsPostBack)
		{
			DataBind(-1);
		}
	}

	protected void DataBind(int headerId)
	{
		int selIdx = 0;

		gvTestata.DataBind();

		multiView.ActiveViewIndex = 0;

		if (gvTestata.Rows.Count > 0)
		{
			if (headerId > 0)
			{
				for (int i = 0; i < gvTestata.DataKeys.Count; i++)
				{
					if (headerId.Equals( (int)gvTestata.DataKeys[i][0]))
						selIdx = i;
				}
			}

			gvTestata.SelectedIndex = selIdx;
			gvTestata_SelectedIndexChanged(this, EventArgs.Empty);
		}
	}

	protected void multiView_ActiveViewChanged(object sender, EventArgs e)
	{
		switch (multiView.ActiveViewIndex)
		{
			case (0):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
				return;
			default:
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
		}
	}

	protected void ddlTipologiaSuperficie_SelectedIndexChanged(object sender, EventArgs e)
	{
		ddlDettaglioSuperficie.DataSource = new CCDettagliSuperficieMgr(Database).GetList(AuthenticationInfo.IdComune, int.MinValue, Convert.ToInt32(ddlTipologiaSuperficie.SelectedValue), null, null, Istanza.SOFTWARE);
		ddlDettaglioSuperficie.DataBind();
    }

	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindEditTestata(new CCICalcoliDettaglioT());
	}

	private void BindEditTestata(CCICalcoliDettaglioT cls)
	{
		multiView.ActiveViewIndex = 1;

        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblId.Text = IsInserting ? "Nuovo" : cls.Id.ToString();

		ddlTipologiaSuperficie.DataSource = new CCTipiSuperficieMgr(Database).GetList(AuthenticationInfo.IdComune, int.MinValue, null, null, Istanza.SOFTWARE);
		ddlTipologiaSuperficie.DataBind();

		if (!IsInserting)
			ddlTipologiaSuperficie.SelectedValue = cls.FkCctsId.ToString();
		else
			ddlTipologiaSuperficie.SelectedIndex = 0;

		ddlTipologiaSuperficie_SelectedIndexChanged(this, EventArgs.Empty);

        if (!IsInserting && cls.FkCcdsId.GetValueOrDefault(int.MinValue) != int.MinValue)
			ddlDettaglioSuperficie.SelectedValue = cls.FkCcdsId.ToString();
		else
            if (ddlDettaglioSuperficie.Items.Count != 0)
			    ddlDettaglioSuperficie.SelectedIndex = 0;

		txtDescrizione.Text = cls.Descrizione;
        itbAlloggi.Value = cls.Alloggi.GetValueOrDefault(int.MinValue) == int.MinValue ? "1" : cls.Alloggi.ToString();

		cmdElimina.Visible = !IsInserting;

		dtbValoreTestata.Item.ValoreDouble = IsInserting ? 0.0d : cls.Su.GetValueOrDefault(double.MinValue);

		if (!IsInserting)
		{
			bool totaleModificabile = cls.Su <= 0.0d || (cls.Su > 0 && CCICalcoliDettaglioRMgr.Find(Token, cls.Id.GetValueOrDefault(int.MinValue)).Count == 0);

			dtbValoreTestata.Item.ReadOnly = !totaleModificabile;
		}

	}

	protected void gvTestata_RowCommand(object sender, GridViewCommandEventArgs e)
	{
		if (e.CommandName == "EditTestata")
		{
			int id = Convert.ToInt32(e.CommandArgument);

			CCICalcoliDettaglioT cls = new CCICalcoliDettaglioTMgr(Database).GetById(AuthenticationInfo.IdComune, id);
			BindEditTestata(cls);

		}
	}

	protected void gvDettaglio_RowCommand(object sender, GridViewCommandEventArgs e)
	{
		if (e.CommandName == "EditDettaglio")
		{
			int id = Convert.ToInt32(e.CommandArgument);

			CCICalcoliDettaglioR cls = new CCICalcoliDettaglioRMgr(Database).GetById(AuthenticationInfo.IdComune, id);
			BindEditDettaglio(cls);

		}
	}

	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		CCICalcoliDettaglioT cls = new CCICalcoliDettaglioT();
		CCICalcoliDettaglioTMgr mgr = new CCICalcoliDettaglioTMgr(Database);

		int id = IsInserting ? int.MinValue : Convert.ToInt32(lblId.Text);

        if (!IsInserting)
        {
            cls = mgr.GetById(Calcolo.Idcomune, id);
        }
        else
        {
            cls.Su = 0.0f;
        }

		cls.Idcomune = Calcolo.Idcomune;
		cls.Id = id;
		cls.Codiceistanza = Calcolo.Codiceistanza;
		cls.FkCcicId = Calcolo.Id;
		cls.FkCctsId = Convert.ToInt32(ddlTipologiaSuperficie.SelectedValue);
		cls.FkCcdsId = String.IsNullOrEmpty(ddlDettaglioSuperficie.SelectedValue) ? (int?)null : Convert.ToInt32(ddlDettaglioSuperficie.SelectedValue);
		cls.Descrizione = txtDescrizione.Text;
		cls.Alloggi = itbAlloggi.Item.ValoreInt.GetValueOrDefault(1);
		cls.Su = dtbValoreTestata.Item.ValoreDouble;

		try
		{
			if (IsInserting)
				mgr.Insert(cls);
			else
				mgr.Update(cls);

			DataBind(cls.Id.GetValueOrDefault(int.MinValue));
		}
		catch (Exception ex)
		{
			MostraErrore("Errore durante l'inserimento: " + ex.Message, ex);
		}
	}

	protected void cmdChiudi_Click(object sender, EventArgs e)
	{
		DataBind(IsInserting ? -1 : Convert.ToInt32(lblId.Text));
	}

	protected void txtNumero_TextChanged(object sender, EventArgs e)
	{
		double nro = (double)(txtNumero.ValoreInt == int.MinValue ? 0 : txtNumero.ValoreInt);
		double lung = txtLunghezza.ValoreDouble;
		double larg = txtLarghezza.ValoreDouble;

		if ( DoubleChecker.IsDoubleEmpty( lung ) )
			lung = 0.0f;

		if (DoubleChecker.IsDoubleEmpty(larg))
			larg = 0.0f;

		txtSuperficieUtile.ValoreDouble = lung * larg * nro;
	}

	protected void cmdNuovoDettaglio_Click(object sender, EventArgs e)
	{
		BindEditDettaglio(new CCICalcoliDettaglioR());
	}

	private void BindEditDettaglio(CCICalcoliDettaglioR cls)
	{
		multiView.ActiveViewIndex = 2;

        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblIdDettaglio.Text = IsInserting ? "Nuovo" : cls.Id.ToString();
		txtNumero.ValoreInt = IsInserting ? 1 : (int)cls.Qta;
		txtLunghezza.ValoreDouble = IsInserting ? 0.0f : cls.Lung.GetValueOrDefault(double.MinValue);
        txtLarghezza.ValoreDouble = IsInserting ? 0.0f : cls.Larg.GetValueOrDefault(double.MinValue);

        txtSuperficieUtile.ValoreDouble = IsInserting ? 0.0f : cls.Su.GetValueOrDefault(double.MinValue);

		cmdEliminaDettaglio.Visible = !IsInserting;
	}

	protected void cmdSalvaDettaglio_Click(object sender, EventArgs e)
	{
		CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(Database);

		int id = IsInserting ? int.MinValue : Convert.ToInt32(lblIdDettaglio.Text);

		CCICalcoliDettaglioR cls = IsInserting ? new CCICalcoliDettaglioR() : mgr.GetById(AuthenticationInfo.IdComune, id);

		cls.Idcomune = Calcolo.Idcomune;
		cls.Id = id;


		cls.Codiceistanza = Calcolo.Codiceistanza;
		cls.FkCcicdtId = Convert.ToInt32(gvTestata.DataKeys[gvTestata.SelectedIndex][0]);

		cls.Qta = txtNumero.ValoreInt;
		cls.Lung = txtLunghezza.ValoreDouble;
		cls.Larg = txtLarghezza.ValoreDouble;
		cls.Su = txtSuperficieUtile.ValoreDouble;

		if (IsInserting)
			mgr.Insert(cls);
		else
			mgr.Update(cls);

		DataBind(cls.FkCcicdtId.GetValueOrDefault(int.MinValue));
	}

	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		DataBind(Convert.ToInt32(gvTestata.DataKeys[gvTestata.SelectedIndex][0]));
	}

	protected void gvTestata_SelectedIndexChanged(object sender, EventArgs e)
	{
		int idTestata = Convert.ToInt32(gvTestata.DataKeys[gvTestata.SelectedIndex][0]);

		CCICalcoliDettaglioT testata = new CCICalcoliDettaglioTMgr(Database).GetById(IdComune, idTestata);

		pnlDettaglioSuperfici.Visible = testata.Su <= 0.0d || (testata.Su > 0 && CCICalcoliDettaglioRMgr.Find(Token, idTestata).Count > 0);

		if (pnlDettaglioSuperfici.Visible)
			gvDettaglio.DataBind();
	}

	protected void CCICalcoloDettaglioRDataSOurce_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
	{
		if (gvTestata.SelectedIndex < 0) return;

		e.InputParameters[1] = gvTestata.DataKeys[gvTestata.SelectedIndex][0];
	}

	protected void cmdEliminaDettaglio_Click(object sender, EventArgs e)
	{
		CCICalcoliDettaglioRMgr mgr = new CCICalcoliDettaglioRMgr(Database);

		try
		{
			int id = Convert.ToInt32(lblIdDettaglio.Text);

			CCICalcoliDettaglioR cls = mgr.GetById(AuthenticationInfo.IdComune, id);

			mgr.Delete(cls);

			DataBind(Convert.ToInt32(gvTestata.DataKeys[gvTestata.SelectedIndex][0]));
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}
	}

	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		int id = Convert.ToInt32(lblId.Text);

		try
		{
			CCICalcoliDettaglioTMgr mgr = new CCICalcoliDettaglioTMgr(Database);
			CCICalcoliDettaglioT cls = mgr.GetById(AuthenticationInfo.IdComune, id);

			mgr.Delete(cls);

			DataBind(-1);
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}
	}

	protected void gvTestata_RowDataBound(object sender, GridViewRowEventArgs e)
	{
		if (e.Row.RowType == DataControlRowType.DataRow)
		{
			CCICalcoliDettaglioT cls = (CCICalcoliDettaglioT)e.Row.DataItem;
			Label lblTipologiaSuperficie = (Label)e.Row.FindControl("lblTipologiaSuperficie");
			Label lblDettaglioSuperficie = (Label)e.Row.FindControl("lblDettaglioSuperficie");
			ImageButton ibSeleziona = (ImageButton)e.Row.FindControl("ibSeleziona");

            lblTipologiaSuperficie.Text = new CCTipiSuperficieMgr(Database).GetById(AuthenticationInfo.IdComune, cls.FkCctsId.GetValueOrDefault(int.MinValue)).Descrizione;

            if (cls.FkCcdsId.GetValueOrDefault(int.MinValue) == int.MinValue)
			{
				lblDettaglioSuperficie.Text = "";
			}
			else
			{
                CCDettagliSuperficie ds = new CCDettagliSuperficieMgr(Database).GetById(AuthenticationInfo.IdComune, cls.FkCcdsId.GetValueOrDefault(int.MinValue));

				lblDettaglioSuperficie.Text = ds.Descrizione;
			}

            if (cls.Su > 0 && CCICalcoliDettaglioRMgr.Find(Token, cls.Id.GetValueOrDefault(int.MinValue)).Count == 0) // la Su è stata imputata a mano
			{
				ibSeleziona.Visible = false;
			}
		}
	}

	protected void cmdIndietro_Click(object sender, EventArgs e)
	{

        CCICalcoloTot ict = new CCICalcoloTotMgr(Database).GetByIdICalcolo(AuthenticationInfo.IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));

		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

		Response.Redirect(String.Format(url, AuthenticationInfo.Token, Calcolo.Codiceistanza, ict == null ? "" : ict.Id.ToString()));
	}

	protected void cmdProcedi_Click(object sender, EventArgs e)
	{
        ElaboratoreCostoCostruzione elab = new ElaboratoreCostoCostruzione(Database, IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));
		elab.Elabora();


		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";

		Response.Redirect(String.Format(url, AuthenticationInfo.Token, Calcolo.Id));
	}
}
