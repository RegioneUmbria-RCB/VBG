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
using Init.Utils.Web.UI;
using SIGePro.Net;
using System.Collections.Generic;
using Init.SIGePro.Exceptions.Anagrafe;

namespace Sigepro.net.Archivi.CalcoloOneri.CostoCostruzione
{
	public partial class CCCausaliRiduzioni : BasePage
	{

		protected void Page_Load(object sender, EventArgs e)
		{
			ImpostaScriptEliminazione(cmdElimina);
		}

		private void BindDettaglio(CcCausaliRiduzioniT cls)
		{
			multiView.ActiveViewIndex = 2;

            this.IsInserting = cls.Id.GetValueOrDefault(int.MinValue) == int.MinValue;

			lblId.Item.Text = IsInserting ? "Nuovo" : cls.Id.ToString();

			// TODO: Inserire qui il codice per popolare i valori dei controlli
			txtDescrizione.Value = cls.Descrizione;

			dgDettagli.EditItemIndex = -1;

            BindDettaglio(cls.Id.GetValueOrDefault(int.MinValue));

			cmdElimina.Visible = !IsInserting;
		}

		private void BindDettaglio(int idTestata)
		{
			pnlDettagli.Visible = !IsInserting;

			if (!IsInserting)
			{
				CcCausaliRiduzioniR filtro = new CcCausaliRiduzioniR();
				filtro.Idcomune = IdComune;
				filtro.FkCccrtId = idTestata;

				dgDettagli.DataSource = new CcCausaliRiduzioniRMgr(Database).GetList(filtro);
			}
			else
			{
				dgDettagli.DataSource = new List<CcCausaliRiduzioniR>();
			}
			dgDettagli.DataBind();
		}




		protected void multiView_ActiveViewChanged(object sender, EventArgs e)
		{
			switch (multiView.ActiveViewIndex)
			{
				case (1):
					Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
					return;
				case (2):
					Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
					return;
				default:
					Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
					return;
			}
		}

		#region Scheda ricerca
		public void cmdCerca_Click(object sender, EventArgs e)
		{
			gvLista.DataBind();

			multiView.ActiveViewIndex = 1;
		}

		public void cmdNuovo_Click(object sender, EventArgs e)
		{
			BindDettaglio(new CcCausaliRiduzioniT());
		}

		public void cmdChiudi_Click(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
		}
		#endregion


		#region Scheda lista
		public void cmdChiudiLista_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}

		protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
		{
			int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

			CcCausaliRiduzioniT cls = new CcCausaliRiduzioniTMgr(Database).GetById(IdComune, id);

			BindDettaglio(cls);
		}
		#endregion


		#region Scheda dettaglio
		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			CcCausaliRiduzioniTMgr mgr = new CcCausaliRiduzioniTMgr(Database);

			CcCausaliRiduzioniT cls = null;

			if (IsInserting)
			{
				cls = new CcCausaliRiduzioniT();
				cls.Idcomune = IdComune;
				cls.Software = Software;
			}
			else
			{
				int id = Convert.ToInt32(lblId.Item.Text);

				cls = mgr.GetById(IdComune, id);
			}

			try
			{
				cls.Descrizione = txtDescrizione.Value;


				if (IsInserting)
					cls = mgr.Insert(cls);
				else
					cls = mgr.Update(cls);

				BindDettaglio(cls);
			}
			catch (RequiredFieldException rfe)
			{
				MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
			}
			catch (Exception ex)
			{
				MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
			}
		}

		protected void cmdElimina_Click(object sender, EventArgs e)
		{
			CcCausaliRiduzioniTMgr mgr = new CcCausaliRiduzioniTMgr(Database);

			int id = Convert.ToInt32(lblId.Item.Text);

			CcCausaliRiduzioniT cls = mgr.GetById(IdComune, id);

			try
			{
				Database.BeginTransaction();

				mgr.Delete(cls);

				Database.CommitTransaction();

				multiView.ActiveViewIndex = 0;
			}
			catch (Exception ex)
			{
				Database.RollbackTransaction();

				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}
		}

		protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}
		#endregion

		protected void gvDettagli_RowCommand(object sender, GridViewCommandEventArgs e)
		{

		}

		protected void dgDettagli_ItemCommand(object source, DataGridCommandEventArgs e)
		{
			DataGridItem row = (DataGridItem)((WebControl)e.CommandSource).NamingContainer;

			CcCausaliRiduzioniRMgr mgr = new CcCausaliRiduzioniRMgr(Database);

			int idTestata = Convert.ToInt32(lblId.Value);

			if (e.CommandName == "Elimina")
			{
				try
				{
					int id = Convert.ToInt32(dgDettagli.DataKeys[row.ItemIndex]);
					mgr.Delete(mgr.GetById(IdComune, id));
				}
				catch (Exception ex)
				{
					MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
				}
			}
			else if (e.CommandName == "Inserisci")
			{
				TextBox txtDescrizione = (TextBox)row.FindControl("txtNewDescrizione");
				DropDownList ddlTipologia = (DropDownList)row.FindControl("ddlNewTipologia");
				FloatTextBox txtImporto = (FloatTextBox)row.FindControl("ftxtNewImporto");

				CcCausaliRiduzioniR cls = new CcCausaliRiduzioniR();
				cls.Idcomune = IdComune;
				cls.FkCccrtId = idTestata;
				cls.Descrizione = txtDescrizione.Text;
				cls.Riduzioneperc = Convert.ToInt32(ddlTipologia.SelectedValue) * txtImporto.ValoreFloat;

				try
				{
					mgr.Insert(cls);
				}
				catch (Exception ex)
				{
					MostraErrore(AmbitoErroreEnum.Inserimento, ex);
				}
			}

			dgDettagli_CancelCommand(this, e);
		}

		protected void dgDettagli_DeleteCommand(object source, DataGridCommandEventArgs e)
		{
			int idCausaleR = Convert.ToInt32(dgDettagli.DataKeys[e.Item.ItemIndex]);

			CcCausaliRiduzioniRMgr mgr = new CcCausaliRiduzioniRMgr(Database);
			CcCausaliRiduzioniR riduzioneR = mgr.GetById(IdComune, idCausaleR);

			try
			{
				mgr.Delete(riduzioneR);

				dgDettagli_CancelCommand(this, e);
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}			
		}

		protected void dgDettagli_UpdateCommand(object source, DataGridCommandEventArgs e)
		{
			int idTestata = Convert.ToInt32(lblId.Value);
			int id = Convert.ToInt32(dgDettagli.DataKeys[e.Item.ItemIndex]);

			CcCausaliRiduzioniRMgr mgr = new CcCausaliRiduzioniRMgr(Database);

			TextBox txtDescrizione = (TextBox)e.Item.FindControl("txtDescrizione");
			DropDownList ddlTipologia = (DropDownList)e.Item.FindControl("ddlTipologia");
			FloatTextBox txtImporto = (FloatTextBox)e.Item.FindControl("ftxtImporto");

			CcCausaliRiduzioniR cls = mgr.GetById(IdComune, id);
			cls.Descrizione = txtDescrizione.Text;
			cls.Riduzioneperc = Convert.ToInt32(ddlTipologia.SelectedValue) * txtImporto.ValoreFloat;

			try
			{
				mgr.Update(cls);

				dgDettagli_CancelCommand(this, e);
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Inserimento, ex);
			}
		}

		protected void dgDettagli_EditCommand(object source, DataGridCommandEventArgs e)
		{
			int idTestata = Convert.ToInt32(lblId.Value);
			dgDettagli.EditItemIndex = e.Item.ItemIndex;

			dgDettagli.ShowFooter = false;

			BindDettaglio(idTestata);
		}

		protected void dgDettagli_CancelCommand(object source, DataGridCommandEventArgs e)
		{
			int idTestata = Convert.ToInt32(lblId.Value);
			dgDettagli.EditItemIndex = -1;

			dgDettagli.ShowFooter = true;

			BindDettaglio(idTestata);
		}

		protected void dgDettagli_ItemDataBound(object sender, DataGridItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.EditItem)
			{
				ImpostaScriptEliminazione((e.Item.FindControl("imgElimina") as ImageButton));
			}
		}

	}
}
