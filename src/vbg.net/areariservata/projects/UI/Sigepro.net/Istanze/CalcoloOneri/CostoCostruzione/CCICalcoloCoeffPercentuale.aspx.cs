using System;
using System.Linq;
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
using Init.Utils.Web.UI;
using Init.Utils;
using log4net;

public partial class Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale : BasePage
{


	private class ViewManager
	{
		private static class Constants
		{
			public const int IdViewCoefficientiStandard = 0;
			public const int IdViewCoefficientiClassiSiperficie = 1;
		}

		MultiView _multiView;

		public ViewManager( MultiView multiView )
		{
			this._multiView = multiView;
		}

		public void MostraViewCoefficientiStandard()
		{
			this._multiView.ActiveViewIndex = Constants.IdViewCoefficientiStandard;
		}

		public void MostraViewCoefficientiClassiSiperficie()
		{
			this._multiView.ActiveViewIndex = Constants.IdViewCoefficientiClassiSiperficie;
		}
	}

	ILog _log = LogManager.GetLogger(typeof(Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale));
	ViewManager _viewManager;
	CCICalcoloTContributo m_tcontributo;
	CCICalcoloTot m_calcoloTot;
	Istanze m_istanza;

	string errMsgCampoNonTrovato = "Nell'attuale configurazione del listino coefficienti non è stato trovato il valore ({0}-{1}).<br>Il tasto \"Salva\" riporta un coefficiente uguale a zero.<br>Il tasto \"Chiudi\" non modifica il precedente calcolo.";

	List<string> m_erroriCondizioni = new List<string>();

	private CCICalcoloTContributo TContributo
	{
		get 
		{
			if (m_tcontributo == null)
			{
				if (Request.QueryString["TipoDettaglio"] == "P")
				{
					m_tcontributo = new CCICalcoloTotMgr(Database).GetStatoDiProgetto(CalcoloTot);
				}
				else
				{
					m_tcontributo = new CCICalcoloTotMgr(Database).GetStatoAttuale(CalcoloTot);
				}
			}

			return m_tcontributo;
		}
	}

	private CCICalcoloTot CalcoloTot
	{
		get
		{
			if (m_calcoloTot == null)
			{
				int id = Convert.ToInt32( Request.QueryString["IdCalcoloTot"] );
				m_calcoloTot = new CCICalcoloTotMgr(Database).GetById(AuthenticationInfo.IdComune, id );
			}

			return m_calcoloTot;
		}
	}

	private Istanze Istanza
	{
		get
		{
			if (m_istanza == null)
                m_istanza = new IstanzeMgr(Database).GetById(CalcoloTot.Idcomune, CalcoloTot.Codiceistanza.Value);

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

	public bool BloccoCoefficienteVisibile
	{
		get { return pnlTipoIntervento.Visible || pnlUbicazione.Visible; }
	}

	public string TitoloBloccoCoefficiente
	{
		get
		{
			string ttl = String.Empty;

			if (pnlTipoIntervento.Visible)
				ttl += " tipo intervento";

			if (pnlUbicazione.Visible)
			{
				if (ttl.Length > 0)
					ttl += " e";

				ttl += " ubicazione";
			}
				
				
			return "Coefficiente calcolato in base a:" + ttl;
		
		}
	}


	public Istanze_CalcoloOneri_CostoCostruzione_CCICalcoloCoeffPercentuale()
	{
	}

	protected void Page_Load(object sender, EventArgs e)
	{
		this._viewManager = new ViewManager(this.multiView);

		if (!IsPostBack)
		{
			this._viewManager.MostraViewCoefficientiStandard();
			DataBindCoefficientiStandard();
		}

		Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
	}

	private void BindComboAttivitaIntervento()
	{
		// ddl Attività è visibile e bindato solamente se CC_FK_SE_CODICESETTORE è valorizzato
		pnlAttivita.Visible = false;

		int tipoIntervento = String.IsNullOrEmpty(ddlTipoIntervento.SelectedValue) ? int.MinValue : Convert.ToInt32(ddlTipoIntervento.SelectedValue);
		int areeCodiceArea = int.MinValue;

		if (pnlUbicazione.Visible && !String.IsNullOrEmpty(ddlUbicazione.SelectedValue))
			areeCodiceArea = Convert.ToInt32(ddlUbicazione.SelectedValue);

		List<CCCondizioniAttivita> att = new CCCoeffContributoMgr(Database).GetAttivitaSelezionabili(IdComune, Software, tipoIntervento , areeCodiceArea);

		if (att.Count > 0)
		{
			pnlAttivita.Visible = true;

			CCConfigurazione cfg = new CCConfigurazioneMgr(Database).GetById(IdComune, Software);

			lblAttivita.Text = new SettoriMgr(Database).GetById(cfg.FkSeCodicesettore, IdComune).ToString();
		
			ddlAttivita.DataSource = att;
			ddlAttivita.DataBind();

			ddlAttivita.Items.Insert(0, "");

			CCCondizioniAttivitaMgr condAttMgr = new CCCondizioniAttivitaMgr(Database);

			for (int i = 0; i < att.Count; i++)
			{
                if (condAttMgr.VerificaCondizioneAttivita(IdComune, TContributo.FkCcicId.GetValueOrDefault(int.MinValue), att[i].FkAtCodiceistat))
				{
					ddlAttivita.SelectedValue = att[i].Id.ToString();
					break;
				}
			}
		}
	}


	public void DataBindCoefficientiStandard()
	{
		var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(IdComune, CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

		if (aliquotePerClassi.Aliquote.Count() == 0)
			cmdAliquotaClassiSuperfici.Visible = false;

		ddlDestinazione.DataBind();
		ddlTipoIntervento.DataBind();
		ddlUbicazione.DataBind();

		BindComboAttivitaIntervento();
		
		rptCoefficienti.DataBind();
		
		RicalcolaCoefficienteUbicazione();
		RicalcolaCoefficienteTotale(null , EventArgs.Empty);

		CCICalcoloDContributoMgr mgrDContributo = new CCICalcoloDContributoMgr(Database);
        CCICalcoloDContributo dContributo = mgrDContributo.GetByIdTContributo(TContributo.Idcomune, TContributo.Id.GetValueOrDefault(int.MinValue));

        if (TContributo.FkCcdeId.GetValueOrDefault(int.MinValue) != int.MinValue)
		{
			ddlDestinazione.SelectedValue = TContributo.FkCcdeId.ToString();
			ddlDestinazione_SelectedIndexChanged(ddlDestinazione, EventArgs.Empty);
		}

        if (!DoubleChecker.IsDoubleEmpty(TContributo.Coefficiente))
		{
            txtTotaleCoefficiente.ValoreDouble = TContributo.Coefficiente.GetValueOrDefault(double.MinValue);
		}

		if (dContributo != null)
		{
            if (dContributo.FkCctiId.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				ddlTipoIntervento.SelectedValue = dContributo.FkCctiId.ToString();
				ddlTipoIntervento_SelectedIndexChanged(ddlTipoIntervento, EventArgs.Empty);
			}

			// Bindare la combo delle attivita

            if (dContributo.FkAreeCodicearea.GetValueOrDefault(int.MinValue) != int.MinValue)
			{
				ddlUbicazione.SelectedValue = dContributo.FkAreeCodicearea.ToString();
				ddlUbicazione_SelectedIndexChanged(ddlUbicazione, EventArgs.Empty);
			}

            if (dContributo.FkCccaId.GetValueOrDefault(int.MinValue) != int.MinValue && pnlAttivita.Visible)
			{
				if (dContributo.FkCccaId == -1)
					ddlAttivita.SelectedValue = "";
				else
					ddlAttivita.SelectedValue = dContributo.FkCccaId.ToString();

				ddlAttivita_SelectedIndexChanged(ddlAttivita, EventArgs.Empty);
			}

            txtCoefficiente.ValoreDouble = dContributo.Coefficiente.GetValueOrDefault(double.MinValue);
		}


	}

	protected void cmdSalva_Click(object sender, EventArgs e)
	{
		// Aggiornamento di CC_ICALCOLO_TCONTRIBUTO
		TContributo.FkCcdeId = pnlDestinazioni.Visible ? Convert.ToInt32( ddlDestinazione.SelectedValue ) : (int?)null;
		TContributo.Coefficiente = txtTotaleCoefficiente.ValoreDouble;

		new CCICalcoloTContributoMgr(Database).Update( TContributo );

		// Aggiornamento/Inserimento di CC_ICALCOLO_DCONTRIBUTO
		CCICalcoloDContributoMgr mgrDContributo = new CCICalcoloDContributoMgr(Database);
        CCICalcoloDContributo dContributo = mgrDContributo.GetByIdTContributo(TContributo.Idcomune, TContributo.Id.GetValueOrDefault(int.MinValue));

		bool isInsertingDContributo = dContributo == null;

		if (isInsertingDContributo)
			dContributo = new CCICalcoloDContributo();

		dContributo.Idcomune		= TContributo.Idcomune;
		dContributo.FkCcictcId		= TContributo.Id;
		dContributo.Codiceistanza	= TContributo.Codiceistanza;

		dContributo.FkCctiId		= pnlTipoIntervento.Visible ? Convert.ToInt32(ddlTipoIntervento.SelectedValue) : (int?)null;
        dContributo.FkAreeCodicearea = pnlUbicazione.Visible ? Convert.ToInt32(ddlUbicazione.SelectedValue) : (int?)null;
		dContributo.Coefficiente	= txtCoefficiente.ValoreDouble;

		dContributo.FkCccaId = null;
		if (pnlAttivita.Visible)
			dContributo.FkCccaId = String.IsNullOrEmpty(ddlAttivita.SelectedValue) ? -1 : Convert.ToInt32(ddlAttivita.SelectedValue);

		if (isInsertingDContributo)
			mgrDContributo.Insert(dContributo);
		else
			mgrDContributo.Update(dContributo);

		// Aggiornamento/Inserimento delle righe di CC_ICALCOLO_DCONTRIBATTIV
		CCICalcoloDContribAttivMgr mgrContribAttiv = new CCICalcoloDContribAttivMgr(Database);

        mgrContribAttiv.DeleteByIdTContributo(TContributo.Idcomune, TContributo.Id.GetValueOrDefault(int.MinValue));

		foreach (RepeaterItem ri in rptCoefficienti.Items)
		{
			if (!ri.Visible) continue;


			DropDownList ddlAttivitaGrid = (DropDownList)ri.FindControl("ddlAttivita");
			DoubleTextBox txtCoefficienteAttivita = (DoubleTextBox)ri.FindControl("txtCoefficienteAttivita");

			CCCondizioniAttivitaMgr mgrCondAtt = new CCCondizioniAttivitaMgr(Database);

			CCCondizioniAttivita ccca = mgrCondAtt.GetByCodiceIstat(AuthenticationInfo.IdComune, ddlAttivitaGrid.SelectedValue);

			CCICalcoloDContribAttiv dca = new CCICalcoloDContribAttiv();
			dca.Idcomune = TContributo.Idcomune;
			dca.Codiceistanza = TContributo.Codiceistanza;
			dca.FkCcictcId = TContributo.Id;
			dca.FkCcccaId = ccca.Id;
			dca.Coefficiente = txtCoefficienteAttivita.ValoreDouble;

			mgrContribAttiv.Insert(dca);
		}

	}

	protected void cmdChiudi_Click(object sender, EventArgs e)
	{
		string url = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

		Response.Redirect(String.Format(url, AuthenticationInfo.Token, CalcoloTot.Codiceistanza , CalcoloTot.Id ));
	}


	
	protected void ddlDestinazione_SelectedIndexChanged(object sender, EventArgs e)
	{
		ddlTipoIntervento.DataBind();
		RicalcolaCoefficienteUbicazione();
	}

	protected void ddlUbicazione_SelectedIndexChanged(object sender, EventArgs e)
	{
		RicalcolaCoefficienteUbicazione();

		BindComboAttivitaIntervento();
	}

	private void RicalcolaCoefficienteUbicazione()
	{
		string idComune = AuthenticationInfo.IdComune;

        int idCalcolo = CalcoloTot.Id.GetValueOrDefault(int.MinValue);
		int idDest = pnlDestinazioni.Visible ?  Convert.ToInt32( ddlDestinazione.SelectedValue ) : int.MinValue;
		int idTipoInt = pnlTipoIntervento.Visible ?  Convert.ToInt32( ddlTipoIntervento.SelectedValue ) : int.MinValue;
		int idUbic = pnlUbicazione.Visible ?  Convert.ToInt32( ddlUbicazione.SelectedValue ) : int.MinValue;
		int idAttivita = pnlAttivita.Visible && !String.IsNullOrEmpty(ddlAttivita.SelectedValue) ? Convert.ToInt32(ddlAttivita.SelectedValue) : int.MinValue;


		txtCoefficiente.ValoreDouble = new CCICalcoloTotMgr(Database).GetCoefficienteDContributo(idComune, idCalcolo, idDest, idTipoInt, idUbic , idAttivita);
	}


	protected void ddlTipoIntervento_SelectedIndexChanged(object sender, EventArgs e)
	{
		BindComboAttivitaIntervento();
		ddlUbicazione.DataBind();
		RicalcolaCoefficienteUbicazione();
	}

	protected void ddlUbicazione_DataBound(object sender, EventArgs e)
	{
		pnlUbicazione.Visible = ddlUbicazione.Items.Count > 0;
	}

	protected void ddlTipoIntervento_DataBound(object sender, EventArgs e)
	{
		pnlTipoIntervento.Visible = ddlTipoIntervento.Items.Count > 0;
	}

	protected void ddlDestinazione_DataBound(object sender, EventArgs e)
	{
		pnlDestinazioni.Visible = ddlDestinazione.Items.Count > 0;
	}

	protected void ConfigSettoriDataSource_Selecting(object sender, ObjectDataSourceSelectingEventArgs e)
	{
		e.InputParameters[1] = Istanza.SOFTWARE;
	}

	protected void rptCoefficienti_ItemDataBound(object sender, RepeaterItemEventArgs e)
	{
		if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
		{
			DropDownList ddlAttivita = (DropDownList)e.Item.FindControl("ddlAttivita");
			DoubleTextBox txtCoefficienteAttivita = (DoubleTextBox)e.Item.FindControl("txtCoefficienteAttivita");
			Panel pnlErroreAttivita = (Panel)e.Item.FindControl("pnlErroreAttivita");

			CCConfigurazioneSettori cs = (CCConfigurazioneSettori)e.Item.DataItem;

            DataTable attivitaPerSettore = new CCConfigurazioneSettoriMgr(Database).GetAttivitaPerSettore(CalcoloTot.Idcomune, CalcoloTot.Id.GetValueOrDefault(int.MinValue), cs.FkSeCodicesettore);

			if (attivitaPerSettore.Rows.Count == 0)
			{
				e.Item.Visible = false;
				return;
			}

			ddlAttivita.DataSource = attivitaPerSettore;
			ddlAttivita.DataBind();

			CCICalcoloDContribAttiv attivitaSelezionata = TrovaAttivitaSelezionata(cs.FkSeCodicesettore);

			if (attivitaSelezionata == null)
			{
				string idAttivitaPreselezionata = TrovaAttivitaPreselezionabile(attivitaPerSettore,m_erroriCondizioni);

				if (String.IsNullOrEmpty(idAttivitaPreselezionata))
					ddlAttivita.SelectedIndex = 0;
				else
					ddlAttivita.SelectedValue = idAttivitaPreselezionata;

				// Ricalcolo il coefficiente
				AttivitaSelectedIndexChanged(ddlAttivita, EventArgs.Empty);
			}
			else
			{
                string codiceIstatAttivita = new CCCondizioniAttivitaMgr(Database).GetById(IdComune, attivitaSelezionata.FkCcccaId.GetValueOrDefault(int.MinValue)).FkAtCodiceistat;

				DataRow[] els = attivitaPerSettore.Select("id='" + codiceIstatAttivita + "'");

				pnlErroreAttivita.Visible = false;

				if (els == null || els.Length == 0)
				{
					Attivita att = new AttivitaMgr(Database).GetById(codiceIstatAttivita, IdComune);
					ddlAttivita.Items.Add(new ListItem(att.ISTAT, att.CodiceIstat));

					pnlErroreAttivita.Visible = true;

					Literal l = new Literal();
					l.Text = String.Format(errMsgCampoNonTrovato, att.CodiceIstat, att.ISTAT);

					pnlErroreAttivita.Controls.Add(l);

					txtCoefficienteAttivita.ValoreDouble = 0.0f;
				}
				else
				{
                    txtCoefficienteAttivita.ValoreDouble = attivitaSelezionata.Coefficiente.GetValueOrDefault(double.MinValue);
				}
				
				ddlAttivita.SelectedValue = codiceIstatAttivita;
			}

			/*

			
			double coefficiente = -1;

			bool result = CalcolaValoreSelezionatoAttivita(attivitaPerSettore, out idAttivita, out coefficiente, m_erroriCondizioni);

			if (!result)
			{
				ddlAttivita.SelectedIndex = 0;
			}
			else
			{
				ddlAttivita.SelectedValue = idAttivita;
				txtCoefficienteAttivita.ValoreDouble = coefficiente;
			}

			if (coefficiente < 0)
				*/
		}
	}

	private string TrovaAttivitaPreselezionabile(DataTable attivitaPerSettore, List<string> errori)
	{
		CCCondizioniAttivitaMgr mgrCondAtt = new CCCondizioniAttivitaMgr(Database);

		foreach (DataRow dr in attivitaPerSettore.Rows)
		{
			try
			{
				// Verifico nella tabella CC_CondizioniAttivita se esiste una condizione che mi permette di preselezionare un valore
                if (mgrCondAtt.VerificaCondizioneAttivita(AuthenticationInfo.IdComune, TContributo.FkCcicId.GetValueOrDefault(int.MinValue), dr["Id"].ToString()))
					return dr["id"].ToString();
			}
			catch (Exception ex)
			{
				errori.Add("Errore nella condizione per il codice " + dr["id"].ToString() + ": " + ex.Message);
			}
		}

		return String.Empty;
	}

	private CCICalcoloDContribAttiv TrovaAttivitaSelezionata(string codiceSettore)
	{
		CCICalcoloDContribAttivMgr mgrContrAttiv = new CCICalcoloDContribAttivMgr(Database);
        return mgrContrAttiv.GetByIdSettore(IdComune, TContributo.Id.GetValueOrDefault(int.MinValue), codiceSettore);
	}
	
	protected void AttivitaSelectedIndexChanged(object sender, EventArgs e)
	{
		DropDownList ddlAttivita = (DropDownList)sender;
		DoubleTextBox txtCoefficienteAttivita = (DoubleTextBox)ddlAttivita.NamingContainer.FindControl("txtCoefficienteAttivita");

		string idComune = AuthenticationInfo.IdComune;

        int idCalcolo = CalcoloTot.Id.GetValueOrDefault(int.MinValue);
		string idAttivita = ddlAttivita.SelectedValue;

		txtCoefficienteAttivita.ValoreDouble = new CCConfigurazioneSettoriMgr(Database).GetCoefficienteDContributoAttiv(idComune, idCalcolo, idAttivita);
	}


	public string GetNomeSettore(object idSettore)
	{
		SettoriMgr mgr = new SettoriMgr(Database);
		return mgr.GetById(idSettore.ToString(), AuthenticationInfo.IdComune).SETTORE;
	}

	protected override void OnPreRender(EventArgs e)
	{
		if (m_erroriCondizioni.Count > 0)
		{
			rptErrori.DataSource = m_erroriCondizioni;
			rptErrori.DataBind();
		}
		
		base.OnPreRender(e);
	}


	protected void RicalcolaCoefficienteTotale(object sender, EventArgs e)
	{
		double val = 0.0f;

		if (txtCoefficiente.ValoreDouble > 0)
			val += txtCoefficiente.ValoreDouble;

		foreach (RepeaterItem ri in rptCoefficienti.Items)
		{
			if (!ri.Visible) continue;

			DoubleTextBox txtCoefficienteAttivita = (DoubleTextBox)ri.FindControl("txtCoefficienteAttivita");

			val += txtCoefficienteAttivita.ValoreDouble;
		}

		txtTotaleCoefficiente.ValoreDouble = val;
	}

	protected void ddlAttivita_SelectedIndexChanged(object sender, EventArgs e)
	{
		//ddlTipoIntervento.DataBind();
		RicalcolaCoefficienteUbicazione();
	}


	#region gestione delle aliquote per classi di superfici

	protected void cmdAliquotaClassiSuperfici_Click(object sender, EventArgs e)
	{
		this._viewManager.MostraViewCoefficientiClassiSiperficie();

		BindAliquotePerClassiSuperfici();
	}

	protected void BindAliquotePerClassiSuperfici()
	{
		var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(IdComune, CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

		rptAliquotePerClassiSuperfici.DataSource = aliquotePerClassi.Aliquote;
		rptAliquotePerClassiSuperfici.DataBind();

		lblTotaleAliquota.Text = aliquotePerClassi.AliquotaTotale + " %";
		lblTotaleCostoCostruzione.Text = aliquotePerClassi.CostoEdificioTotale + " €";
		lblTotaleContributo.Text = aliquotePerClassi.ContributoTotale + " €";

	}

	protected void cmdSalvaAliquotePerClassi_Click(object sender, EventArgs e)
	{
		var aliquotePerClassi = new CCICalcoloTotMgr(this.Database).GetAliquotePerClassiSuperficie(IdComune, CalcoloTot.Id.Value, this.TContributo.Stato == "P" ? CCICalcoloTotMgr.StatoCalcoloEnum.Progetto : CCICalcoloTotMgr.StatoCalcoloEnum.Attuale);

		try
		{
			TContributo.FkCcdeId = (int?)null;
			TContributo.Coefficiente = Double.Parse(aliquotePerClassi.AliquotaTotale);

			new CCICalcoloTContributoMgr(Database).Update(TContributo);

			cmdChiudi_Click(sender, e);
		}
		catch (Exception ex)
		{
			Errori.Add("Errore durante il salvataggio dei dati delle aliquote per classi: " + ex.Message);
			_log.ErrorFormat("Chiamata: {0}, querystring: {1} \r\n Errore: {1}", Request.Url, Request.QueryString, ex.ToString());
		}
	}

	#endregion
}
