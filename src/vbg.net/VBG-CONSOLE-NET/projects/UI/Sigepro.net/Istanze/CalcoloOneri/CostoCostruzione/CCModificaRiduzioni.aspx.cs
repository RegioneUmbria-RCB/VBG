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
using Init.Utils.Web.UI;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
	public partial class CCModificaRiduzioni : BasePage
	{

		CCICalcoloTContributo TContributo
		{
			get
			{
				int idTContributo = Convert.ToInt32(Request.QueryString["tcontributo"]);
				return new CCICalcoloTContributoMgr(Database).GetById(IdComune, idTContributo );
			}
		}

		private Init.SIGePro.Data.Istanze Istanza
		{
			get
			{
                return new IstanzeMgr(Database).GetById(IdComune, TContributo.Codiceistanza.GetValueOrDefault(int.MinValue));
			}
		}

		public override string Software
		{
			get
			{
				return Istanza.SOFTWARE;
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			List<CcCausaliRiduzioniT> causali = new CcCausaliRiduzioniTMgr(Database).GetListByIdcomuneSoftware(IdComune, Software);

			rptTipiCausali.DataSource = causali;
			rptTipiCausali.DataBind();
		}

		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			try
			{
				Database.BeginTransaction();

				CcICalcoloTContributoRiduzMgr contribRiduzMgr = new CcICalcoloTContributoRiduzMgr(Database);
				//contribRMgr.GetByContribTTipoOnereDestinazione(

				// Elimino tutte le righe di o_icalcolocontribr_riduz collegate al ContribTAttuale
                List<CcICalcoloTContributoRiduz> listaRiduzioni = contribRiduzMgr.GetListByIdTContributo(IdComune, TContributo.Id.GetValueOrDefault(int.MinValue));

				foreach (CcICalcoloTContributoRiduz riduzione in listaRiduzioni)
					contribRiduzMgr.Delete(riduzione);


				foreach (RepeaterItem rptItm in rptTipiCausali.Items)
				{
					DataGrid dgCausali = (DataGrid)rptItm.FindControl("dgCausali");

					foreach (DataGridItem dgi in dgCausali.Items)
					{
						CheckBox chkSelezionato = (CheckBox)dgi.FindControl("chkSelezionato");
						DoubleTextBox dtbImporto = (DoubleTextBox)dgi.FindControl("dtbImporto");
						TextBox txtNoteImporto = (TextBox)dgi.FindControl("txtNoteImporto");

						if (!chkSelezionato.Checked) continue;

						CcICalcoloTContributoRiduz riduzione = new CcICalcoloTContributoRiduz();
						riduzione.Idcomune = IdComune;
						riduzione.Codiceistanza = Convert.ToInt32(Istanza.CODICEISTANZA);
						riduzione.FkCccrrId = Convert.ToInt32(dgCausali.DataKeys[dgi.ItemIndex]);
						riduzione.FkCcictcId = TContributo.Id;
						riduzione.Riduzioneperc = dtbImporto.ValoreDouble;
						riduzione.Note = txtNoteImporto.Text;

						contribRiduzMgr.Insert(riduzione);
					}
				}

				Database.CommitTransaction();
			}
			catch (Exception ex)
			{
				Database.RollbackTransaction();

				MostraErrore(AmbitoErroreEnum.Aggiornamento, ex);
			}

			ScriptManager.RegisterStartupScript(cmdChiudi, cmdChiudi.GetType(), "close", "window.returnValue = true;self.close();", true);
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			ScriptManager.RegisterStartupScript(cmdChiudi, cmdChiudi.GetType(), "close", "window.returnValue = false;self.close();", true);
		}


		protected void cmdSalvaNote_Click(object sender, EventArgs e)
		{

		}
		

		protected void rptTipiCausali_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				DataGrid dgCausali = (DataGrid)e.Item.FindControl("dgCausali");

				CcCausaliRiduzioniT tipoCausale = (CcCausaliRiduzioniT)e.Item.DataItem;

                dgCausali.DataSource = new CcICalcoloTContributoRiduzMgr(Database).GetImportiRiduzioni(IdComune, tipoCausale.Id.GetValueOrDefault(int.MinValue), TContributo.Id.GetValueOrDefault(int.MinValue));
				dgCausali.DataBind();
			}
		}
	}
}
