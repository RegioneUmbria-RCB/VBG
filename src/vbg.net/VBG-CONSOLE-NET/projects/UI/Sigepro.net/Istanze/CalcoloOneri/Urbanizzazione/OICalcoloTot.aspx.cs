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
using SIGePro.Net.Navigation;
using System.Collections.Generic;
using SIGePro.WebControls.UI;
using Init.Utils.Web.UI;
using Sigepro.net.Istanze.CalcoloOneri;
using Init.SIGePro.Manager.Logic.GestioneOneri;

public partial class Istanze_CalcoloOneri_Urbanizzazione_OICalcoloTot : PaginaTotaleOneriBase
{
	private int CodiceIstanza
	{
		get
		{
			string codiceIstanza = Request.QueryString["CodiceIstanza"];

			if (String.IsNullOrEmpty(codiceIstanza))
			{
				if (String.IsNullOrEmpty(IdCalcoloTot))
					throw new ArgumentException("Codice istanza e id calcolo tot non passati");

				OICalcoloTot oict = new OICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, Convert.ToInt32(IdCalcoloTot));

                return oict.Codiceistanza.GetValueOrDefault(int.MinValue);
			}

			return int.Parse(codiceIstanza);
		}
	}

	Istanze m_istanza = null;

	private Istanze Istanza
	{
		get
		{
			if (m_istanza == null)
                m_istanza = new IstanzeMgr(AuthenticationInfo.CreateDatabase()).GetById(AuthenticationInfo.IdComune, Convert.ToInt32(CodiceIstanza));

			return m_istanza;
		}
	}

    protected string ReturnTo
    {
        get { return Request.QueryString["ReturnTo"]; }
    }

	protected string IdCalcoloTot
	{
		get { return Request.QueryString["IdCalcoloTot"]; }
	}

	#region gestione della griglia con il riepilogo calcoli

	protected DataSet GridDataSource
	{
		set { Session["GridDataSource"] = value; }
		get { return (DataSet)Session["GridDataSource"]; }
	}

	protected void BindGrigliaDettaglioCalcoli()
	{
		dgDettagliCalcolo.DataSource = GridDataSource;
		dgDettagliCalcolo.DataBind();
	}

	protected void dgDettagliCalcolo_ItemDataBound(object sender, DataGridItemEventArgs e)
	{
		#region Binding dell'intestazione
		if (e.Item.ItemType == ListItemType.Header)
		{
            e.Item.Cells[ e.Item.Cells.Count - 1 ].Text = "&nbsp;";

			OBaseTipiOnereMgr mgrBaseTo = new OBaseTipiOnereMgr(Database);

			for (int i = 0; i < e.Item.Cells.Count; i++)
			{
				if (i > 0 && i < e.Item.Cells.Count - 2)
				{
					string id = e.Item.Cells[i].Text;

					OBaseTipiOnere bto = mgrBaseTo.GetById(id);

					if (bto != null)
						e.Item.Cells[i].Attributes.Add("title", bto.Descrizione);
				}
			}
		}
		#endregion

		#region Binding degli elementi
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			DataRowView dr = (DataRowView)e.Item.DataItem;

			if (dr == null) return;

			DataTable table = dr.Row.Table;

			// Aggiungo il bottone Dettagli
			TableCell cell = e.Item.Cells[e.Item.Cells.Count - 1];
			cell.Controls.Clear();

            ImageButton ib = new ImageButton();
            ib.ID = "ibDettagli";
            ib.CommandArgument = dr["Comandi"].ToString();
            ib.CommandName = "Select";
            ib.ImageUrl = "~/images/edit.gif";
            ib.AlternateText = "Visualizza i parametri del calcolo degli oneri di urbanizzazione per la destinazione corrente";

            cell.Controls.Add(ib);

			for (int i = 0; i < table.Columns.Count; i++ )
			{
				DataColumn dc = table.Columns[i];

				if (dc.ColumnName != "Destinazione" && dc.ColumnName != "Comandi")
				{
					e.Item.Cells[i].Text = Convert.ToDouble(e.Item.Cells[i].Text).ToString("N2");
					e.Item.Cells[i].Style.Add("text-align", "right");
				}
			}
		}
		#endregion

		#region Binding del footer
		if (e.Item.ItemType == ListItemType.Footer)
		{
			if (GridDataSource == null || string.IsNullOrEmpty(IdCalcoloTot)) return;			

			DataTable dt = GridDataSource.Tables[0];

			OICalcoloTotMgr ctotMgr = new OICalcoloTotMgr(Database);
			OConfigurazioneTipiOnereMgr cfgToMgr = new OConfigurazioneTipiOnereMgr(Database);

			string software = ctotMgr.GetSoftwareDaCalcoloTot(AuthenticationInfo.IdComune, Convert.ToInt32( IdCalcoloTot ));

			for (int i = 0; i < dt.Columns.Count; i++)
			{
				string columnName = dt.Columns[i].ColumnName;

				int? causale = cfgToMgr.GetCausaleDaTipoOnereBase(AuthenticationInfo.IdComune, columnName, software);

				e.Item.Cells[i].Controls.Clear();

				if (columnName != "Destinazione" && columnName != "Comandi")
				{
					Literal lt = new Literal();
					lt.ID = "lt" + i;
                    lt.Text = "<span style=\"float:right\"><b>" + TotaleColonna(dt, columnName).ToString("N2") + "</b></span>";
					e.Item.Cells[i].Controls.Add(lt);
				}

				if (causale.HasValue)
				{
					SigeproButton sbAggiungi = new SigeproButton();
					sbAggiungi.ID = "ibAggiungi" + i;
					sbAggiungi.IdRisorsa = "COPIAONERI";
					sbAggiungi.CommandArgument = causale.Value.ToString() + "$" + TotaleColonna(dt, columnName).ToString("N2");
					sbAggiungi.Text = "Copia oneri";
					sbAggiungi.Style.Add("float", "left");
					sbAggiungi.Click += new EventHandler(ibAggiungi_Click);

					Div div = new Div();
					div.CssClass = "Bottoni";
					div.Style.Add("padding-top", "0px");
					div.Controls.Add(sbAggiungi);

                    ImpostaScriptCopia(sbAggiungi, CodiceIstanza, causale.Value);

					e.Item.Cells[i].Controls.Add(div);
				}

				e.Item.Cells[i].Style.Add("vertical-align", "top");
				e.Item.Cells[i].Style.Add("border", "0px");
			}
		}
		#endregion
	}


    void ibAggiungi_Click(object sender, EventArgs e)
    {
		var ibAggiungi = (SigeproButton)sender;

        var parts = ibAggiungi.CommandArgument.Split('$');

        var idCausale = Convert.ToInt32(parts[0]);
        var totaleOnere = Convert.ToDouble(parts[1]);

        var mgrIstanzeOneri = new IstanzeOneriMgr(Database);
        var al = GetOneriFromIstanzaCausale(CodiceIstanza, idCausale);

        //Verifico se è stato trovato un onere nell'istanza con la stessa causale
        if (al.Count == 1)
        {
            //E' stato trovato un onere 
            var onere   = al[0];
            var importo = onere.PREZZO.GetValueOrDefault(0.0d) + totaleOnere;
            var idComune = onere.IDCOMUNE;
            var idOnere = Convert.ToInt32(onere.ID);

            try
            {
                mgrIstanzeOneri.UpdateImporto(idComune, idOnere, importo);
            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
            }
        }
        else
        {
            //Non è stato trovato nessun onere o più di uno
            try
            {
				var oneriService = new OneriService(Token, IdComune);

				oneriService.Inserisci(CodiceIstanza, idCausale, totaleOnere);
            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Inserimento, ex);
            }
        }

        MostraConfermaCopiaOneri();
        //ImpostaScriptCopia(ibAggiungi, CodiceIstanza, idCausale);
    }
    
	private double TotaleColonna(DataTable dt, string columnName)
	{
		double tot = 0.0d;

		foreach (DataRow dr in dt.Rows)
		{
			tot += Convert.ToDouble( dr[columnName] );
		}

		return tot;
	}

	#endregion


	public override string Software
	{
		get
		{
			return Istanza.SOFTWARE;
		}
	}

	public Istanze_CalcoloOneri_Urbanizzazione_OICalcoloTot()
	{
		//VerificaSoftware = false;
	}

	protected void Page_Load(object sender, EventArgs e)
	{
        ImpostaScriptEliminazione(cmdEliminaCalcolo);

		if (!IsPostBack)
		{
			if (!String.IsNullOrEmpty(IdCalcoloTot))
			{
				int id = Convert.ToInt32(IdCalcoloTot);
				BindDettaglio(new OICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, id));
			}
			else
			{
				DataBind();
			}
		}
	}

	private void BindDettaglio(OICalcoloTot oICalcoloTot)
	{
		IsInserting = oICalcoloTot.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

		if (IsInserting)
			BindInserimento();
		else
			BindModifica(oICalcoloTot);
	}

	protected override void OnInit(EventArgs e)
	{
		base.OnInit(e);

		BindGrigliaDettaglioCalcoli();
	}

	private void BindModifica(OICalcoloTot cls)
	{
		multiView.ActiveViewIndex = 2;

		lblId.Text = cls.Id.ToString();
		lblData.Text = cls.Data.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");

		OValiditaCoefficienti ovc = new OValiditaCoefficientiMgr(Database).GetById(AuthenticationInfo.IdComune, cls.FkOvcId.GetValueOrDefault(int.MinValue));

		lblListino.Text = ovc == null ? string.Empty : ovc.Descrizione;
		
		txtEditDescrizione.Text = cls.Descrizione;

		GridDataSource = new OICalcoloTotMgr(Database).GeneraDataSetOneriUrbanizzazione(AuthenticationInfo.IdComune, cls.Id.GetValueOrDefault(int.MinValue));
		BindGrigliaDettaglioCalcoli();
	}

	private void BindInserimento()
	{
		multiView.ActiveViewIndex = 1;
		
		txtData.DateValue = DateTime.Now;

        SetCoefficienti();
	}

	public override void DataBind()
	{
		ddlCoefficienti.DataSource = new OValiditaCoefficientiMgr(Database).GetList( AuthenticationInfo.IdComune , Istanza.SOFTWARE , null , null  );
		ddlCoefficienti.DataBind();

        gvLista.DataSource = OICalcoloTotMgr.Find(Token, CodiceIstanza);
        gvLista.DataBind();

	}

	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindDettaglio(new OICalcoloTot());
	}
	protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		int id = Convert.ToInt32( gvLista.DataKeys[gvLista.SelectedIndex][0] );

        string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Software={0}&Token={1}&CodiceIstanza={2}&IdCalcoloTot={3}";

        Response.Redirect(String.Format(url, Software, AuthenticationInfo.Token, CodiceIstanza,  id));
	}
	protected void txtData_TextChanged(object sender, EventArgs e)
	{
		if (txtData.DateValue.HasValue && txtData.DateValue.Value.Date != DateTime.MinValue)
		{
            SetCoefficienti();
		}
	}
    protected void SetCoefficienti()
    {
        OValiditaCoefficienti coeff = new OValiditaCoefficientiMgr(Database).GetCoefficienteAllaData(Istanza.IDCOMUNE, Istanza.SOFTWARE, txtData.DateValue.Value);
        if (coeff != null)
            ddlCoefficienti.SelectedValue = coeff.Id.ToString();
    }

	protected void multiView_ActiveViewChanged(object sender, EventArgs e)
	{
		switch (multiView.ActiveViewIndex)
		{
			case (0):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
				return;
			case (1):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
			case (2):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
		}
	}
	protected void cmdInserisci_Click(object sender, EventArgs e)
	{
		try
		{
			OICalcoloTot cls = new OICalcoloTot();
			cls.Idcomune = Istanza.IDCOMUNE;
			cls.Codiceistanza = Convert.ToInt32(Istanza.CODICEISTANZA);
			cls.Data = txtData.DateValue.HasValue ? txtData.DateValue.Value : DateTime.MinValue;
            cls.Descrizione = string.IsNullOrEmpty(txtDescrizione.Value) ? "Calcolo degli oneri di urbanizzazione" : txtDescrizione.Value;
			cls.FkOvcId = Convert.ToInt32(ddlCoefficienti.SelectedValue);

			cls = new OICalcoloTotMgr(Database).Insert(cls);

			BindDettaglio(cls);
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Inserimento, ex);
		}

	}
	protected void cmdChiudiInserimento_Click(object sender, EventArgs e)
	{
		multiView.ActiveViewIndex = 0;
	}
	protected void cmdAggiornaDescrizione_Click(object sender, EventArgs e)
	{
		try
		{
			int id = Convert.ToInt32(lblId.Text);

			OICalcoloTot cls = new OICalcoloTotMgr(Database).GetById( AuthenticationInfo.IdComune , id );
			cls.Descrizione = txtEditDescrizione.Text;

			cls = new OICalcoloTotMgr(Database).Update(cls);

			BindDettaglio(cls);
		}
		catch (Exception ex)
		{
			MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
		}
	}
	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx?Token={0}&CodiceIstanza={1}";

		int id = Convert.ToInt32(lblId.Text);

		OICalcoloTot cTot = new OICalcoloTotMgr( Database ).GetById( AuthenticationInfo.IdComune , id );
		
		Response.Redirect(string.Format(url, AuthenticationInfo.Token, cTot.Codiceistanza ));
	}
	protected void cmdRedirDettagli_Click(object sender, EventArgs e)
	{
		string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OIDettaglio.aspx?Token={0}&IdCalcolo={1}";

		int id = Convert.ToInt32(lblId.Text);

		Response.Redirect(string.Format(url, AuthenticationInfo.Token, id ));
	}

	protected void dgDettagliCalcolo_ItemCommand(object source, DataGridCommandEventArgs e)
	{
		if (e.CommandName == "Select")
		{
			int id = Convert.ToInt32(e.CommandArgument);

			string url = "~/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloContribT.aspx?Token={0}&IdContribT={1}";

			Response.Redirect(string.Format(url, AuthenticationInfo.Token, id));
		}
	}
    protected void cmdChiudi_Click(object sender, EventArgs e)
    {
		base.CloseCurrentPage(); 
    }
    protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
    {
        ImageButton cmdElimina = e.Row.FindControl("cmdElimina") as ImageButton;

        if (cmdElimina != null)
            ImpostaScriptEliminazione(cmdElimina);
    }
    protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        int selId = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][0]);

        OICalcoloTotMgr mgr = new OICalcoloTotMgr(Database);
        OICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, selId);

        mgr.Delete(cls);

        DataBind();
    }
    protected void cmdEliminaCalcolo_Click(object sender, EventArgs e)
    {
        int selId = Convert.ToInt32(lblId.Text);

        OICalcoloTotMgr mgr = new OICalcoloTotMgr(Database);
        OICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, selId);

        string url = "OICalcoloTot.aspx?Software=" + Software + "&Token=" + Token + "&CodiceIstanza=" + cls.Codiceistanza + "&ReturnTo=" + ReturnTo;

        mgr.Delete(cls);

        Response.Redirect( url );
    }
}
