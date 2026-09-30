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
using Init.SIGePro.Manager;
using SIGePro.Net;
using Init.SIGePro.Data;
using SIGePro.Net.Navigation;
using System.Collections.Generic;
using Sigepro.net.Istanze.CalcoloOneri;
using System.Text;
using Init.Utils;
using Init.SIGePro.Manager.Logic.GestioneOneri;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliTot : PaginaTotaleOneriBase
{
	public Istanze_CalcoloOneri_CostoCostruzione_CCICalcoliTot()
	{
		//VerificaSoftware = false;
	}

	public override string Software
	{
		get
		{
			return Istanza.SOFTWARE;
		}
	}

	private int CodiceIstanza
	{
		get 
		{
			string codiceIstanza = Request.QueryString["CodiceIstanza"];

			if (String.IsNullOrEmpty(codiceIstanza))
				throw new ArgumentException("Codice istanza non passato");

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

	#region visibilità delle righe della tabella del contributo
	public bool MostraRigaContributoProgetto
	{
		get { object o = this.ViewState["MostraRigaContributoProgetto"]; return o == null ? true : (bool)o; }
		set { this.ViewState["MostraRigaContributoProgetto"] = value;  }
	}

	public bool MostraRigaContributoAttuale
	{
		get { object o = this.ViewState["MostraRigaContributoAttuale"]; return o == null ? true : (bool)o; }
		set { this.ViewState["MostraRigaContributoAttuale"] = value; }
	}
	#endregion

	public bool MostraTabellaContributo
	{
		get { return MostraRigaContributoProgetto || MostraRigaContributoAttuale; }
	}

	protected void Page_Load(object sender, EventArgs e)
	{
        ImpostaScriptEliminazione(cmdEliminaCalcolo);
        
		if (!IsPostBack)
		{
			if (!String.IsNullOrEmpty(Request.QueryString["IdCalcoloTot"]))
			{
				int id = Convert.ToInt32(Request.QueryString["IdCalcoloTot"]);
				BindDettaglio(new CCICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, id));
			}
			else
			{
				DataBind();
			}
		}
	}

    

	public override void DataBind()
	{
		gvLista.DataSource = new CCICalcoloTotMgr(AuthenticationInfo.CreateDatabase()).GetList(AuthenticationInfo.IdComune, CodiceIstanza);
		gvLista.DataBind();

		multiView.ActiveViewIndex = 0;
	}

	protected void cmdNuovo_Click(object sender, EventArgs e)
	{
		BindDettaglio(new CCICalcoloTot());
	}

	private void BindDettaglio(CCICalcoloTot cls)
	{
        IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;
        cmdEliminaCalcolo.Visible = !IsInserting;
		if (IsInserting)
		{
			BindInserimento(cls);
		}
		else
		{
			BindAggiornamento(cls);
		}
	}

	private void BindAggiornamento(CCICalcoloTot cls)
	{
		new CCICalcoloTotMgr( Database ).IntegraForeignKey(cls);

		lblId.Text		= cls.Id.ToString();
        lblData.Text = cls.Data.GetValueOrDefault(DateTime.MinValue).ToString("dd/MM/yyyy");
        lblListino.Text = new CCValiditaCoefficientiMgr(Database).GetById(AuthenticationInfo.IdComune, cls.FkCcvcId.GetValueOrDefault(int.MinValue)).Descrizione;
		lblTipoCalcolo.Text = new CCBaseTipoCalcoloMgr(Database).GetById( cls.FkBcctcId ).Tipocalcolo;
		lblTipoIntervento.Text = new OCCBaseTipoInterventoMgr(Database).GetById(cls.FkOccbtiId).Intervento;
		lblDestinazione.Text = new OCCBaseDestinazioniMgr(Database).GetById(cls.FkOccbdeId).Destinazione;

		txtEditDescrizione.Text = cls.Descrizione;

		this.MostraRigaContributoAttuale = 
		this.MostraRigaContributoProgetto = false;

		double totaleContributo = 0.0d;
		double contrAttuale = 0.0d;
		double contrProgetto = 0.0d;

		if ( cls.StatoAttuale != null )
		{
			this.MostraRigaContributoAttuale = true;
            txtCostoEdificioAttuale.ValoreDouble = cls.StatoAttuale.CostocEdificio.GetValueOrDefault(double.MinValue);
            txtCoefficenteAttuale.ValoreDouble = cls.StatoAttuale.Coefficiente.GetValueOrDefault(double.MinValue);

			if ( DoubleChecker.IsDoubleEmpty( cls.StatoAttuale.Riduzioneperc ) )
			{
				cls.StatoAttuale.Riduzioneperc = 0.0d;
				new CCICalcoloTContributoMgr(Database).Update(cls.StatoAttuale);
			}

			double quotaNoRiduz = cls.StatoAttuale.GetQuotaSenzaRiduzioni();
			double quotaConRiduz =cls.StatoAttuale.GetQuotaConRiduzioni();

			lblQuotaAttuale.Text = quotaNoRiduz.ToString("N2");
			txtQuotaContributoAttuale.ValoreDouble = quotaConRiduz;

			contrAttuale = quotaConRiduz;

            cmdDettagliCostoEdificioAttuale.Visible = cls.StatoAttuale.FkCcicId.GetValueOrDefault(int.MinValue) != int.MinValue;

            if (cls.StatoAttuale.FkCcicId.GetValueOrDefault(int.MinValue) != int.MinValue)
				cmdDettagliCostoEdificioAttuale.CommandArgument = cls.StatoAttuale.FkCcicId.ToString();

			cmdDettagliContributoAttuale.CommandArgument = cls.Id.ToString() + "$A";

            bool haRiduzioni = new CcICalcoloTContributoRiduzMgr(Database).GetListByIdTContributo(IdComune, cls.StatoAttuale.Id.GetValueOrDefault(int.MinValue)).Count > 0;

			txtVariazioneAttuale.ReadOnly = haRiduzioni;
            txtVariazioneAttuale.ValoreDouble = cls.StatoAttuale.Riduzioneperc.GetValueOrDefault(double.MinValue);

			lnkEditVariazioneAttuale.OnClientClick = "return " + (haRiduzioni || cls.StatoAttuale.Riduzioneperc == 0.0d ? "ModificaRiduzioni" : "ModificaNote") + "('" + Token + "'," + cls.StatoAttuale.Id + ");";

			hlpVariazioneAttuale.Text = GeneraTestoRiduzioni(cls.StatoAttuale);
		}

		if (cls.StatoDiProgetto != null)
		{
			this.MostraRigaContributoProgetto = true;
            txtCostoEdificioProgetto.ValoreDouble = cls.StatoDiProgetto.CostocEdificio.GetValueOrDefault(double.MinValue);
            txtCoefficenteProgetto.ValoreDouble = cls.StatoDiProgetto.Coefficiente.GetValueOrDefault(double.MinValue);

			if ( DoubleChecker.IsDoubleEmpty( cls.StatoDiProgetto.Riduzioneperc ))
			{
				cls.StatoDiProgetto.Riduzioneperc = 0.0d;
				new CCICalcoloTContributoMgr(Database).Update(cls.StatoDiProgetto);
			}

			double quotaNoRiduz  = cls.StatoDiProgetto.GetQuotaSenzaRiduzioni();
			double quotaConRiduz = cls.StatoDiProgetto.GetQuotaConRiduzioni();

			lblQuotaProgetto.Text = quotaNoRiduz.ToString("N2");
			txtQuotaContributoProgetto.ValoreDouble = quotaConRiduz;

			contrProgetto = quotaConRiduz;

            cmdDettagliCostoEdificioProgetto.Visible = cls.StatoDiProgetto.FkCcicId.GetValueOrDefault(int.MinValue) != int.MinValue;

            if (cls.StatoDiProgetto.FkCcicId.GetValueOrDefault(int.MinValue) != int.MinValue)
				cmdDettagliCostoEdificioProgetto.CommandArgument = cls.StatoDiProgetto.FkCcicId.ToString();

			cmdDettagliContributoProgetto.CommandArgument = cls.Id.ToString() + "$P";

            txtVariazioneProgetto.ValoreDouble = cls.StatoDiProgetto.Riduzioneperc.GetValueOrDefault(double.MinValue);

            bool haRiduzioni = new CcICalcoloTContributoRiduzMgr(Database).GetListByIdTContributo(IdComune, cls.StatoDiProgetto.Id.GetValueOrDefault(int.MinValue)).Count > 0;
			txtVariazioneProgetto.ReadOnly = haRiduzioni;

			lnkEditVariazioneProgetto.OnClientClick = "return " + (haRiduzioni || cls.StatoDiProgetto.Riduzioneperc == 0.0d ? "ModificaRiduzioni" : "ModificaNote") + "('" + Token + "'," + cls.StatoDiProgetto.Id + ");";

			hlpVariazioneProgetto.Text = GeneraTestoRiduzioni(cls.StatoDiProgetto);
		}

		totaleContributo = contrProgetto - contrAttuale;

		txtQuotaContributoTotale.ValoreDouble = totaleContributo;

		multiView.ActiveViewIndex = 2;

        ImpostaScriptCopia(cmdCopiaOneri, CodiceIstanza, new CCConfigurazioneMgr(Database).GetById(IdComune, Software).FkCoId.GetValueOrDefault(int.MinValue));

		cmdCopiaOneri.Visible = VerificaVisibilitaBottoneRiporta();
	}

	private string GeneraTestoRiduzioni(CCICalcoloTContributo cCICalcoloTContributo)
	{
        List<CcICalcoloTContributoRiduz> riduzioni = new CcICalcoloTContributoRiduzMgr(Database).GetListByIdTContributo(IdComune, cCICalcoloTContributo.Id.GetValueOrDefault(int.MinValue));

		if (riduzioni.Count == 0) return cCICalcoloTContributo.Noteriduzione;	// TODO: Verificare che non sia stato immesso un valore manualmente in tal caso ritornare le note del calcolo

		StringBuilder sb = new StringBuilder();

		sb.Append("<table width='100%'>");

		double totale = 0.0d;

		foreach (CcICalcoloTContributoRiduz r in riduzioni)
		{
			sb.Append("<tr style='color:#000'><td>");
			sb.Append(r.CausaleRiduzione.Descrizione);
			sb.Append("</td><td style='text-align:right;'>");
            sb.Append(r.Riduzioneperc.GetValueOrDefault(double.MinValue).ToString("N2") + "%");
			sb.Append("</td></tr>");

			if (!String.IsNullOrEmpty(r.Note))
			{
				sb.Append("<tr><td>&nbsp;&nbsp;<i>");
				sb.Append(r.Note);
				sb.Append("</i></td><td>&nbsp;</td></tr>");
			}

            totale += r.Riduzioneperc.GetValueOrDefault(0);

		}

		sb.Append("<tr style='color:#000'><td>&nbsp;</td><td style='text-align:right;'>-------------</td></tr>");

		sb.Append("<tr style='color:#000'><td>Totale</td><td style='text-align:right;'>");
		sb.Append( totale.ToString("N2") );
		sb.Append( "%" );
		sb.Append( "</td></tr>");
		
		sb.Append("</table>");

		return sb.ToString();

	}

	private bool VerificaVisibilitaBottoneRiporta()
	{
		CCConfigurazioneMgr mgrConfigurazione = new CCConfigurazioneMgr(Database);
		CCConfigurazione configurazione = mgrConfigurazione.GetById(AuthenticationInfo.IdComune, Istanza.SOFTWARE);

        if (configurazione == null || configurazione.FkCoId.GetValueOrDefault(int.MinValue) == int.MinValue)
			return false;

		return true;
	}

	private void BindInserimento(CCICalcoloTot cls)
	{
		txtData.DateValue = DateTime.Now.Date;
		txtDescrizione.Text = "";

		ddlFkCCVCID.DataSource = new CCValiditaCoefficientiMgr( Database ).GetList(Istanza.IDCOMUNE, Istanza.SOFTWARE);
		ddlFkCCVCID.DataBind();

        SetCoefficienti();

		ddlFkCCVCID.SelectedIndex = 0;

		ddlFfOCCBTIID.DataSource = new OCCBaseTipoInterventoMgr(Database).GetList(null, null);
		ddlFfOCCBTIID.DataBind();

		ddlFfOCCBTIID.SelectedIndex = 0;

		//ddlFkOCCBDEID.DataSource = new OCCBaseDestinazioniMgr(Database).GetList(null, null);
        ddlFkOCCBDEID.DataSource = new CCDestinazioniMgr(Database).GetBaseDestinazioniList(Istanza.IDCOMUNE);
		ddlFkOCCBDEID.DataBind();

		ddlFkOCCBDEID.SelectedIndex = 0;

		RicalcolaTipiCalcolo(this, EventArgs.Empty);

		multiView.ActiveViewIndex = 1;
	}

	protected void RicalcolaTipiCalcolo(object sender, EventArgs e)
	{
		ddlFkBCCTCID.DataSource = new CCICalcoloTotMgr(Database).GetTipiCalcolo(AuthenticationInfo.IdComune, Istanza.SOFTWARE, ddlFfOCCBTIID.SelectedValue, ddlFkOCCBDEID.SelectedValue);
		ddlFkBCCTCID.DataBind();
	}

	protected void cmdInserisci_Click(object sender, EventArgs e)
	{
		CCICalcoloTot cls = new CCICalcoloTot();
		cls.Idcomune = AuthenticationInfo.IdComune;
		cls.Codiceistanza = CodiceIstanza;
		cls.Data = txtData.DateValue;
        cls.Descrizione = string.IsNullOrEmpty(txtDescrizione.Text) ? ddlFkOCCBDEID.SelectedItem.Text + " - " + ddlFfOCCBTIID.SelectedItem.Text : txtDescrizione.Text;
		cls.FkBcctcId = ddlFkBCCTCID.SelectedValue;
		cls.FkCcvcId = int.Parse( ddlFkCCVCID.SelectedValue );
		cls.FkOccbdeId = ddlFkOCCBDEID.SelectedValue;
		cls.FkOccbtiId = ddlFfOCCBTIID.SelectedValue;

		CCICalcoloTotMgr mgr = new CCICalcoloTotMgr(Database);

		try
		{
			cls = mgr.Insert(cls);

			// TODO: redirect alla pagina di riepilogo
			BindDettaglio(cls);
		}
		catch (Exception ex)
		{
			MostraErrore("Errore durante l'inserimento: " + ex.Message, ex);
		}
    }

	protected void multiView_ActiveViewChanged(object sender, EventArgs e)
	{
		switch (multiView.ActiveViewIndex)
		{
			case(0):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
				return;
			case(1):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
			case(2):
				Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
				return;
		}
	}

	protected void cmdChiudiInserimento_Click(object sender, EventArgs e)
	{
		DataBind();
	}

	protected void cmdAggiornaDescrizione_Click(object sender, EventArgs e)
	{
		CCICalcoloTotMgr mgr = new CCICalcoloTotMgr(Database);
		CCICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, Convert.ToInt32(lblId.Text));
		
		cls.Descrizione = txtEditDescrizione.Text;

		try
		{
			mgr.Update( cls );
		}
		catch (Exception ex)
		{
			MostraErrore("Errore durante l'aggiornamento: " + ex.Message, ex);
		}
	}

	protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
	{
		int selId = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex][0]);

		//CCICalcoloTotMgr mgr = new CCICalcoloTotMgr(Database);

		//CCICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, selId);

		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

		url = String.Format(url, Token, CodiceIstanza, selId);

		//BindDettaglio(cls);
		Response.Redirect(url);
	}

	protected void cmdSalvaContributo_Click(object sender, EventArgs e)
	{
		int id = int.Parse(lblId.Text);
		CCICalcoloTotMgr mgrTot =  new CCICalcoloTotMgr(Database);
		CCICalcoloTContributoMgr mgrICalc = new CCICalcoloTContributoMgr(Database);

		CCICalcoloTot cTot = mgrTot.GetById(AuthenticationInfo.IdComune, id);
		mgrTot.IntegraForeignKey(cTot);

		if (cTot.StatoDiProgetto != null)
		{
			cTot.StatoDiProgetto.Coefficiente = txtCoefficenteProgetto.ValoreDouble;
			cTot.StatoDiProgetto.CostocEdificio = txtCostoEdificioProgetto.ValoreDouble;
			cTot.StatoDiProgetto.Riduzioneperc =  String.IsNullOrEmpty(txtVariazioneProgetto.Text) ? 0.0d : txtVariazioneProgetto.ValoreDouble;
			cTot.StatoDiProgetto = mgrICalc.Update(cTot.StatoDiProgetto);
		}

		if (cTot.StatoAttuale != null)
		{
			cTot.StatoAttuale.Coefficiente = txtCoefficenteAttuale.ValoreDouble;
			cTot.StatoAttuale.CostocEdificio = txtCostoEdificioAttuale.ValoreDouble;
			cTot.StatoAttuale.Riduzioneperc = String.IsNullOrEmpty( txtVariazioneAttuale.Text ) ? 0.0d : txtVariazioneAttuale.ValoreDouble;
			cTot.StatoAttuale = mgrICalc.Update(cTot.StatoAttuale);
		}

		cTot = mgrTot.GetById(AuthenticationInfo.IdComune, id);

		BindDettaglio(cTot);

	}

	protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
	{
		if (!String.IsNullOrEmpty(Request.QueryString["IdCalcoloTot"]))
		{
			DataBind();
		}

		multiView.ActiveViewIndex = 0;

	}

	protected void ApriDettagli(object sender, EventArgs e)
	{
        ImageButton lbSender = (ImageButton)sender;

		int idCalcolo = int.Parse(lbSender.CommandArgument);

		CCConfigurazione cfg = new CCConfigurazioneMgr( Database).GetById( IdComune , Istanza.SOFTWARE );

		string fmtUrl = "";
		
		// Utilizza l'immissione dei dati nel modello?
		if (cfg.Usadettagliosup == CCConfigurazione.CALCSUP_MODELLO)
			fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella1.aspx?Token={0}&IdCalcolo={1}";
		else
			fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliDettaglio.aspx?Token={0}&IdCalcolo={1}";

		Response.Redirect( String.Format( fmtUrl , AuthenticationInfo.Token , idCalcolo ) , true );		
	}

	protected void ApriDettagliContributo(object sender, EventArgs e)
	{
        ImageButton lbSender = (ImageButton)sender;

		string[] parts = lbSender.CommandArgument.Split('$');

		int idCalcoloTot = int.Parse(parts[0]);
		string tipoDettaglio = parts[1];

		string fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoloCoeffPercentuale.aspx?Token={0}&IdCalcoloTot={1}&TipoDettaglio={2}";

		Response.Redirect(String.Format(fmtUrl, AuthenticationInfo.Token, idCalcoloTot , tipoDettaglio), true);		

		
	}

    protected void cmdRiportaValore_Click(object sender, EventArgs e)
	{
        var istanzeOneriMgr = new IstanzeOneriMgr(Database);
		var codiceCausale = new CCConfigurazioneMgr(Database).GetById(IdComune, Software).FkCoId;
        var oneriEsistenti = GetOneriFromIstanzaCausale(CodiceIstanza, codiceCausale.GetValueOrDefault(int.MinValue));
		var importoOnere = txtQuotaContributoTotale.ValoreDouble;

        //Verifico se è stato trovato un onere nell'istanza con la stessa causale
        if (oneriEsistenti.Count == 1)
        {
            //E' stato trovato un onere 
            var onere = oneriEsistenti[0];

            var importo = onere.PREZZO.GetValueOrDefault(0.0d) + importoOnere;
            var idComune = onere.IDCOMUNE;
            var idOnere = Convert.ToInt32(onere.ID);

            try
            {
                istanzeOneriMgr.UpdateImporto(idComune, idOnere, importo);
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

				oneriService.Inserisci(CodiceIstanza, codiceCausale.Value, importoOnere);
            }
            catch (Exception ex)
            {
                MostraErrore(AmbitoErroreEnum.Inserimento, ex);
            }
        }

        MostraConfermaCopiaOneri();
		//ImpostaScriptCopia(cmdCopiaOneri, CodiceIstanza, codiceCausale.GetValueOrDefault(int.MinValue));
	}


	protected void txtData_TextChanged(object sender, EventArgs e)
	{
        if (txtData.DateValue.HasValue && txtData.DateValue.GetValueOrDefault(DateTime.MinValue) != DateTime.MinValue)
		{
            SetCoefficienti();
		}
	}

    protected void SetCoefficienti()
    {
        CCValiditaCoefficienti ccvc = new CCValiditaCoefficientiMgr(Database).GetCoefficienteAllaData(Istanza.IDCOMUNE, Istanza.SOFTWARE, txtData.DateValue.Value);

        if (ccvc == null) return;

        ddlFkCCVCID.SelectedValue = ccvc.Id.ToString();
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

        CCICalcoloTotMgr mgr = new CCICalcoloTotMgr(Database);
        CCICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, selId);

		try
		{
			mgr.Delete(cls);

			DataBind();
		}
		catch (Exception ex)
		{
			MostraErrore(ex);
		}
    }
    protected void cmdEliminaCalcolo_Click(object sender, EventArgs e)
    {
        int selId = Convert.ToInt32( lblId.Text);

        CCICalcoloTotMgr mgr = new CCICalcoloTotMgr(Database);
        CCICalcoloTot cls = mgr.GetById(AuthenticationInfo.IdComune, selId);

		try
		{
			mgr.Delete(cls);
		}
		catch (Exception ex)
		{
			MostraErrore(ex);

			return;
		}

        DataBind();

        multiView.ActiveViewIndex = 0;
    }

	protected void lnkEditVariazione_Click(object sender, ImageClickEventArgs e)
	{
		int id = Convert.ToInt32(Request.QueryString["IdCalcoloTot"]);
		BindDettaglio(new CCICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, id));
	}


}
