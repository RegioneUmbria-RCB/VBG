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
using Init.SIGePro.Manager;
using Init.Utils.Web.UI;
using Init.SIGePro.Data;
using System.Collections.Generic;

namespace Sigepro.net.Archivi.IndividuazioneProcedimenti
{
	public partial class GestioneDomandeFront : BasePage
	{
		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

			ImpostaScriptEliminazione(cmdDelete);

			if (!IsPostBack)
			{
				DataBindAlbero();
			}
		}

		public void DataBindAlbero()
		{
			tvDomande.DataSource = new DomandeFrontAlberoMgr(Database).GetDomandeRicorsivo(IdComune, Software);
			tvDomande.DataBind();
		}

		protected void tvDomande_ItemSelected(SelectableTreeNode sender, EventArgs e)
		{
			SetSelectedItem(Convert.ToInt32(sender.Id));
		}

		private void SetSelectedItem(int idNodoSelezionato)
		{
			if (idNodoSelezionato == -1)
			{
				multiView.ActiveViewIndex = 0;
			}
			else
			{
				multiView.ActiveViewIndex = 1;

				DataBindDettaglio(idNodoSelezionato);
			}
		}

		private void DataBindDettaglio(int idNodoSelezionato)
		{
			DomandeFrontAlberoMgr mgr = new DomandeFrontAlberoMgr(Database);

			DomandeFrontAlbero nodo = mgr.GetById(IdComune, idNodoSelezionato);

			lblId.Value = nodo.Id.ToString();
			txtDescrizione.Value = nodo.Descrizione;
			txtNote.Value = nodo.Note;
			txtOrdine.Value = nodo.Ordine.ToString();
			//lblIdPadre.Value = nodo.Idpadre.ToString() ;
			chkDisattiva.Item.Checked = nodo.Disattiva == 1;

			List<DomandeFrontAlbero> domandeFiglio = mgr.GetSottoaree(IdComune, idNodoSelezionato);

			cmdDelete.Visible = domandeFiglio.Count == 0;

			List<DomandeFront> domande = new DomandeFrontMgr(Database).GetByIdArea(IdComune, idNodoSelezionato);
			dgDomande.DataSource = domande;
			dgDomande.DataBind();
		}

		protected void cmdNew_Click(object sender, EventArgs e)
		{
			DomandeFrontAlbero nodo = new DomandeFrontAlbero();

			nodo.Idcomune = IdComune;
			nodo.Software = Software;
			nodo.Idpadre = tvDomande.SelectedId;
			nodo.Descrizione = txtDescrizioneNew.Value;
			nodo.Note = String.Empty;
			nodo.Ordine = tvDomande.SelectedId == -1 ? 1 : txtOrdine.Item.ValoreInt.GetValueOrDefault(0) + 1;
			nodo.Disattiva = 0;

			try
			{
				nodo = new DomandeFrontAlberoMgr(Database).Insert(nodo);

				txtDescrizioneNew.Value = String.Empty;

                tvDomande.SelectedId = nodo.Id.GetValueOrDefault(int.MinValue);
				DataBindAlbero();

                SetSelectedItem(nodo.Id.GetValueOrDefault(int.MinValue));
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Inserimento, ex);
			}
		}

		protected void cmdSave_Click(object sender, EventArgs e)
		{
			DomandeFrontAlbero nodo = new DomandeFrontAlberoMgr(Database).GetById(IdComune, tvDomande.SelectedId);

			nodo.Descrizione = txtDescrizione.Value;
			nodo.Note = txtNote.Value;
			nodo.Ordine = txtOrdine.Item.ValoreInt.GetValueOrDefault(1);
			nodo.Disattiva = chkDisattiva.Item.Checked ? 1 : 0;

			try
			{
				new DomandeFrontAlberoMgr(Database).Update(nodo);

				DataBindAlbero();

                SetSelectedItem(nodo.Id.GetValueOrDefault(int.MinValue));
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
			}
		}

		protected void cmdDelete_Click(object sender, EventArgs e)
		{
			DomandeFrontAlberoMgr mgr = new DomandeFrontAlberoMgr(Database);

			DomandeFrontAlbero nodo = mgr.GetById(IdComune, tvDomande.SelectedId);

			try
			{
                int idPadre = nodo.Idpadre.GetValueOrDefault(int.MinValue);

				mgr.Delete(nodo);

				tvDomande.SelectedId = idPadre;
				DataBindAlbero();

				SetSelectedItem(idPadre);
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}
		}

		protected void cmdClose_Click(object sender, EventArgs e)
		{

		}

		protected void cmdNuovaDomanda_Click(object sender, EventArgs e)
		{
			DomandeFrontMgr mgr = new DomandeFrontMgr(Database);

			DomandeFront domanda = new DomandeFront();
			domanda.Idcomune = IdComune;
			domanda.Domanda = txtNuovaDomanda.Value;
			domanda.FkDfaId = tvDomande.SelectedId;
			domanda.Software = Software;

			try
			{
				mgr.Insert(domanda);

				txtNuovaDomanda.Value = "";

				DataBindDettaglio(tvDomande.SelectedId);
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Inserimento, ex);
			}
		}

		protected void dgDomande_DeleteCommand(object source, DataGridCommandEventArgs e)
		{
			int codiceDomanda = Convert.ToInt32(e.CommandArgument);
			DomandeFrontMgr mgr = new DomandeFrontMgr(Database);

			try
			{
				DomandeFront domanda = mgr.GetById(IdComune, codiceDomanda);
				mgr.Delete(domanda);

				DataBindDettaglio(tvDomande.SelectedId);
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}
		}
	}
}
