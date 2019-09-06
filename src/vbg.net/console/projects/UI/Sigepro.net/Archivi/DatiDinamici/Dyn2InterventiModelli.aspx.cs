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
using Init.SIGePro.Data;
using System.Collections.Generic;
using Init.SIGePro.Exceptions;
using PersonalLib2.Exceptions;
using SIGePro.WebControls.Ajax;
using Init.SIGePro.Manager.Logic.Ricerche;

namespace Sigepro.net.Archivi.DatiDinamici
{
	public partial class Dyn2InterventiModelli : BasePage
	{
		protected int? codiceIntervento
		{
			get
			{
				return string.IsNullOrEmpty(Request.QueryString["codiceintervento"]) ? (int?)null : Convert.ToInt32(Request.QueryString["codiceintervento"]);
			}
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			if (!Page.IsPostBack)
			{
				DataBind();
				this.lblDescrizione1.Text = new InterventiMgr(Database).GetById(codiceIntervento.GetValueOrDefault(int.MinValue), IdComune).INTERVENTO;
				this.cmdChiudiLista.Attributes.Add("onClick", "self.close();");
			}
		}

		public override void DataBind()
		{
			this.gvLista.DataSource = InterventiDyn2ModelliTMgr.Find(Token, codiceIntervento.GetValueOrDefault(int.MinValue), "");
			this.gvLista.DataBind();
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
			}
		}

		#region Scheda lista
		protected void cmdNuovo_Click(object sender, EventArgs e)
		{
			this.rplModelloDinamico.Class = null;
			multiView.ActiveViewIndex = 1;

		}

		protected void cmdChiudiLista_Click(object sender, EventArgs e)
		{
		}
		#endregion

		#region Scheda dettaglio
		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			InterventiDyn2ModelliTMgr mgr = new InterventiDyn2ModelliTMgr(Database);
			InterventiDyn2ModelliT cls = null;

			try
			{
				cls = new InterventiDyn2ModelliT();
				cls.Idcomune = IdComune;
				cls.CodiceIntervento = codiceIntervento;
				cls.FkD2mtId = string.IsNullOrEmpty(this.rplModelloDinamico.Value) ? (int?)null : Convert.ToInt32(this.rplModelloDinamico.Value);

				cls = mgr.Insert(cls);

				multiView.ActiveViewIndex = 0;

				DataBind();
			}
			catch (RequiredFieldException rfe)
			{
				MostraErrore("Attenzione, i campi contrassegnati con un asterisco sono obbligatori.", rfe);
			}
			catch (DatabaseException de)
			{
				MostraErrore("Attenzione, si sta tendando di inserire un modello già presente per il procedimento corrente.", de);
			}
			catch (Exception ex)
			{
				MostraErrore(IsInserting ? AmbitoErroreEnum.Inserimento : AmbitoErroreEnum.Aggiornamento, ex);
			}
		}
		protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;

			DataBind();
		}
		#endregion

		protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			InterventiDyn2ModelliTMgr mgr = new InterventiDyn2ModelliTMgr(Database);

			int codiceintervento = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][0]);
			int fkd2mtid = Convert.ToInt32(gvLista.DataKeys[e.RowIndex][1]);

			InterventiDyn2ModelliT cls = mgr.GetById(IdComune, codiceintervento, fkd2mtid);

			try
			{
				mgr.Delete(cls);

				DataBind();

			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}
		}

		protected void gvLista_RowDataBound(object sender, GridViewRowEventArgs e)
		{
			if (e.Row.RowType == DataControlRowType.DataRow)
			{
				ImpostaScriptEliminazione(e.Row.Cells[1].Controls[1] as ImageButton);
			}
		}

	}
}
