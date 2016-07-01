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
using Init.SIGePro.Exceptions;

public partial class Istanze_CalcoloOneri_Urbanizzazione_OIDettaglio : BasePage
{

	public override string Software
	{
		get
		{
			return Istanza.SOFTWARE;
		}
	}

	public Istanze_CalcoloOneri_Urbanizzazione_OIDettaglio()
	{
		//VerificaSoftware = false;
	}

	OICalcoloTot m_calcolo = null;
	Istanze m_istanza = null;
	OConfigurazione m_configurazione = null;


	private OICalcoloTot Calcolo
	{
		get
		{
			int id = int.Parse(Request.QueryString["IdCalcolo"]);

			if (m_calcolo == null)
				m_calcolo = new OICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, id);

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

	private bool UsaDettaglioSuperfici
	{
		get
		{
			if (m_configurazione == null)
			{
				m_configurazione = new OConfigurazioneMgr(Database).GetById(IdComune, Istanza.SOFTWARE);
			}

			return m_configurazione.Usadettagliosup == 1;
		}
	}

	private int IdTestataSelezionato
	{
		get 
		{
			if (gvTestata.SelectedIndex == -1) return -1;
			return Convert.ToInt32( gvTestata.DataKeys[gvTestata.SelectedIndex][0] );
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

	public void DataBind(int idTestata)
	{
		int selIdx = 0;

		gvTestata.DataBind();

		multiView.ActiveViewIndex = 0;

		if (gvTestata.Rows.Count > 0)
		{
			if (idTestata > 0)
			{
				for (int i = 0; i < gvTestata.DataKeys.Count; i++)
				{
					if (idTestata.Equals((int)gvTestata.DataKeys[i][0]))
						selIdx = i;
				}
			}

			gvTestata.SelectedIndex = selIdx;
			gvTestata_SelectedIndexChanged(this, EventArgs.Empty);
		}

		pnlDettaglioSuperfici.Visible = gvTestata.Rows.Count > 0 && UsaDettaglioSuperfici;

		if (pnlDettaglioSuperfici.Visible)
			gvDettaglio.DataBind();

		if(!UsaDettaglioSuperfici)
			gvTestata.SelectedIndex = -1;
	}

	#region gestione del flusso della pagina

	protected void EditTestata(OICalcoloDettaglioT cls)
	{
		multiView.ActiveViewIndex = 1;

        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		lblId.Text = IsInserting ? "Nuovo" : cls.Id.ToString();

		ddlBaseDestinazione.DataSource = new OCCBaseDestinazioniMgr(Database).GetList(null, null);
		ddlBaseDestinazione.DataBind();

		if (IsInserting)
			ddlBaseDestinazione.SelectedIndex = 0;
		else
			ddlBaseDestinazione.SelectedValue = cls.FkOccbdeId.ToString();

		ddlBaseDestinazione_SelectedIndexChanged(ddlBaseDestinazione, EventArgs.Empty);

        if (ddlDestinazione.Items.Count > 0)
        {
            if (IsInserting)
                ddlDestinazione.SelectedIndex = 0;
            else
                ddlDestinazione.SelectedValue = cls.FkOdeId.ToString();
        }

		cmdElimina.Visible = !IsInserting;

		txtDescrizione.Text = cls.Descrizione;

		pnlEditValoreTestata.Visible = !UsaDettaglioSuperfici;
        txtValoreTestata.ValoreDouble = IsInserting ? 0.0d : cls.Totale.GetValueOrDefault(double.MinValue);
		
	}

	protected void EditDettaglio(OICalcoloDettaglioR cls)
	{
		multiView.ActiveViewIndex = 2;

        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		if (IsInserting)
		{
			lblIdDettaglio.Text = "Nuovo";

            txtNumero.Text = "1";
			txtLarghezza.Text =
			txtAltezza.Text = "0";

			txtTotale.Text = "0";

            txtLunghezza.ValoreDouble = double.MinValue;
            txtLunghezza.Focus();
		}
		else
		{
			lblIdDettaglio.Text = cls.Id.ToString();
			txtNumero.ValoreInt = (int)cls.Qta;
            txtLunghezza.ValoreDouble = cls.Lung.GetValueOrDefault(double.MinValue);
            txtLarghezza.ValoreDouble = cls.Larg.GetValueOrDefault(double.MinValue);
            txtAltezza.ValoreDouble = cls.Alt.GetValueOrDefault(double.MinValue);

            txtTotale.ValoreDouble = cls.Totale.GetValueOrDefault(double.MinValue);

            txtLunghezza.Focus();
		}

		cmdEliminaDettaglio.Visible = !IsInserting;

		pnlAltezza.Visible = new OICalcoloDettaglioTMgr(Database).AltezzaRichiesta(AuthenticationInfo.IdComune, IdTestataSelezionato);


	}

	#endregion

	#region eventi della datagrid di testata
	protected void gvTestata_RowCommand(object sender, GridViewCommandEventArgs e)
	{
		if (e.CommandName == "EditTestata")
		{
			int id = Convert.ToInt32(e.CommandArgument);

			EditTestata( new OICalcoloDettaglioTMgr( Database ).GetById( AuthenticationInfo.IdComune , id ) );
		}
	}
	protected void gvTestata_RowDataBound(object sender, GridViewRowEventArgs e)
	{
		if (e.Row.RowType == DataControlRowType.DataRow)
		{
			OICalcoloDettaglioT dett = (OICalcoloDettaglioT)e.Row.DataItem;
			Label lblBaseDestinazione = (Label)e.Row.FindControl("lblBaseDestinazione");
			Label lblDestinazione = (Label)e.Row.FindControl("lblDestinazione");

			ImageButton ibSelect = (ImageButton)e.Row.FindControl("ibSelect");

			ibSelect.Visible = UsaDettaglioSuperfici;			

			OCCBaseDestinazioni baseDest = new OCCBaseDestinazioniMgr(Database).GetById(dett.FkOccbdeId);

			lblBaseDestinazione.Text = baseDest.Destinazione;

            if (dett.FkOdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
                ODestinazioni dest = new ODestinazioniMgr(Database).GetById(AuthenticationInfo.IdComune, dett.FkOdeId.GetValueOrDefault(int.MinValue));

				lblDestinazione.Text = dest == null ? String.Empty : dest.Destinazione;
			}
		}
	}
	protected void gvTestata_SelectedIndexChanged(object sender, EventArgs e)
	{
		gvDettaglio.Columns[4].Visible = new OICalcoloDettaglioTMgr(Database).AltezzaRichiesta(AuthenticationInfo.IdComune, IdTestataSelezionato);

		gvDettaglio.DataBind();
	}
	#endregion

	#region eventi dei bottoni nella visuale lista
	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		EditTestata(new OICalcoloDettaglioT());
	}
	protected void cmdProcedi_Click(object sender, EventArgs e)
	{
		OICalcoloTotMgr mgr = new OICalcoloTotMgr(Database);

        mgr.Elabora(AuthenticationInfo.IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));

		cmdIndietro_Click(sender, e);
	}
	protected void cmdIndietro_Click(object sender, EventArgs e)
	{
		string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&CodiceIStanza={1}&IdCalcoloTot={2}";

		Response.Redirect(string.Format(url, AuthenticationInfo.Token, Calcolo.Codiceistanza , Calcolo.Id));
	}
	protected void cmdNuovoDettaglio_Click(object sender, EventArgs e)
	{
		EditDettaglio(new OICalcoloDettaglioR());
	}
	#endregion

	#region eventi della datagrid di dettaglio
	protected void gvDettaglio_RowCommand(object sender, GridViewCommandEventArgs e)
	{
		if (e.CommandName == "EditDettaglio")
		{
			int id = Convert.ToInt32(e.CommandArgument);
			OICalcoloDettaglioR r = new OICalcoloDettaglioRMgr(Database).GetById(AuthenticationInfo.IdComune, id);

			EditDettaglio(r);
		}
	}
	#endregion

	#region Eventi del form di edit testata
	protected void ddlBaseDestinazione_SelectedIndexChanged(object sender, EventArgs e)
	{
		ddlDestinazione.DataSource = new ODestinazioniMgr(Database).GetListByBaseDestinazione( AuthenticationInfo.IdComune , ddlBaseDestinazione.SelectedValue );
		ddlDestinazione.DataBind();
	}
	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		OICalcoloDettaglioTMgr mgr = new OICalcoloDettaglioTMgr(Database);

		OICalcoloDettaglioT cls = IsInserting ? new OICalcoloDettaglioT() : mgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(lblId.Text));

        try
        {
            cls.Idcomune = AuthenticationInfo.IdComune;
            cls.FkOccbdeId = ddlBaseDestinazione.SelectedValue;
            cls.FkOdeId = string.IsNullOrEmpty(ddlDestinazione.SelectedValue) ? (int?)null : Convert.ToInt32(ddlDestinazione.SelectedValue);
            cls.Descrizione = txtDescrizione.Text;
            cls.Codiceistanza = Calcolo.Codiceistanza;
            cls.FkOicId = Calcolo.Id;


            if (IsInserting)
                cls.Totale = 0.0d;

            if (pnlEditValoreTestata.Visible)
                cls.Totale = txtValoreTestata.ValoreDouble;

            if (IsInserting)
                cls = mgr.Insert(cls);
            else
                cls = mgr.Update(cls);

            DataBind(cls.Id.GetValueOrDefault(int.MinValue));
        }
        catch (RequiredFieldException rfe)
        {
            MostraErrore("Attenzione, i campi contrassegnati con l'asterisco sono obbligatori.", rfe);
        }
        catch (Exception ex)
        {
            MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
        }
	}
	protected void cmdElimina_Click(object sender, EventArgs e)
	{
		int id = Convert.ToInt32(lblId.Text);

		try
		{
			OICalcoloDettaglioTMgr mgr = new OICalcoloDettaglioTMgr( Database );

			OICalcoloDettaglioT t = mgr.GetById( AuthenticationInfo.IdComune , id );

			new OICalcoloDettaglioTMgr(Database).Delete(t);
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}

		DataBind(-1);
	}
	protected void cmdChiudi_Click(object sender, EventArgs e)
	{
		DataBind( IdTestataSelezionato );
	}
	#endregion

	#region Eventi del form di edit dettaglio
	protected void cmdSalvaDettaglio_Click(object sender, EventArgs e)
	{
		OICalcoloDettaglioR cls = null;
		OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(Database);

		RicalcolaTotaleDettaglio();

		try
		{
			if (IsInserting)
			{
				cls = new OICalcoloDettaglioR();
				cls.Idcomune = AuthenticationInfo.IdComune;
				cls.Codiceistanza = Calcolo.Codiceistanza;
				cls.FkOicdtId = IdTestataSelezionato;
			}
			else
			{
				int id = Convert.ToInt32(lblIdDettaglio.Text);
				cls = mgr.GetById(AuthenticationInfo.IdComune, id);
			}

			cls.Qta = txtNumero.ValoreInt;
			cls.Lung = txtLunghezza.ValoreDouble;
			cls.Larg = txtLarghezza.ValoreDouble;
			cls.Alt = txtAltezza.ValoreDouble;
			cls.Totale = txtTotale.ValoreDouble;


			if (IsInserting)
				mgr.Insert(cls);
			else
				mgr.Update(cls);

			DataBind( IdTestataSelezionato );
		}
		catch (Exception ex)
		{
			MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
		}
	}
	private void RicalcolaTotaleDettaglio()
	{
		double qta = (double)txtNumero.ValoreInt;

		double lung = txtLunghezza.ValoreDouble;
		double larg = txtLarghezza.ValoreDouble;
		double alt = pnlAltezza.Visible ? txtAltezza.ValoreDouble : 1.0d;

		txtTotale.ValoreDouble = lung * larg * alt * qta;
	}
	protected void cmdEliminaDettaglio_Click(object sender, EventArgs e)
	{
		int id = Convert.ToInt32(lblIdDettaglio.Text);
		OICalcoloDettaglioRMgr mgr = new OICalcoloDettaglioRMgr(Database);

		try
		{
			OICalcoloDettaglioR cr = mgr.GetById( AuthenticationInfo.IdComune , id );

			mgr.Delete( cr );
		}
		catch( Exception ex )
		{
			MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
		}

		DataBind( IdTestataSelezionato );
	}
	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		DataBind( IdTestataSelezionato );
	}
	#endregion

	protected void CCICalcoloDettaglioRDataSOurce_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
	{
		if (gvTestata.SelectedIndex < 0) return;

		e.InputParameters[1] = gvTestata.DataKeys[gvTestata.SelectedIndex][0];
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
}
