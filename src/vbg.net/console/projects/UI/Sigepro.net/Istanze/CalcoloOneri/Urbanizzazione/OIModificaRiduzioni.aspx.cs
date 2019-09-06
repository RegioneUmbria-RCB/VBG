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
using PersonalLib2.Sql;
using System.Collections.Generic;

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
	public partial class OIModificaRiduzioni : BasePage
	{
		public override string Software
		{
			get
			{
                Init.SIGePro.Data.Istanze ist = new IstanzeMgr(Database).GetById(IdComune, ContribT.Codiceistanza.Value);
				return ist.SOFTWARE;
			}
		}

		private int IdDestinazione
		{
			get
			{
				string idDestinazione = Request.QueryString["IdDestinazione"];
				if (String.IsNullOrEmpty(idDestinazione))
					throw new ArgumentException("Parametro IdDestinazione non impostato");

				return Convert.ToInt32(idDestinazione);
			}
		}


		OICalcoloContribT m_contribT = null;
		private OICalcoloContribT ContribT
		{
			get
			{
				if (m_contribT == null)
				{
					int id = Convert.ToInt32(Request.QueryString["IdContribT"]);
					m_contribT = new OICalcoloContribTMgr(Database).GetById(IdComune, id);
				}

				return m_contribT;
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			OCausaliRiduzioniT filtro = new OCausaliRiduzioniT();
			filtro.Idcomune = IdComune;
			filtro.Software = Software;
			//filtro.UseForeign = useForeignEnum.Recoursive;

			rptTipiCausali.DataSource = new OCausaliRiduzioniTMgr(Database).GetList(filtro);
			rptTipiCausali.DataBind();
		}

		protected void rptTipiCausali_ItemDataBound(object sender, RepeaterItemEventArgs e)
		{
			if (e.Item.ItemType == ListItemType.Item || e.Item.ItemType == ListItemType.AlternatingItem)
			{
				OCausaliRiduzioniT tipoCausale = (OCausaliRiduzioniT)e.Item.DataItem;
				OIModificariduzioniCtrl modificariduzioniCtrl = (OIModificariduzioniCtrl)e.Item.FindControl("ModificariduzioniCtrl");

                modificariduzioniCtrl.DataSource = new OICalcoloContribRRiduzMgr(Database).GetRiduzioniPerTipoCausale(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), IdDestinazione, tipoCausale.Id.GetValueOrDefault(int.MinValue));
				modificariduzioniCtrl.DataBind();
			}
		}

		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			try
			{
				Database.BeginTransaction();

				OICalcoloContribRMgr contribRMgr = new OICalcoloContribRMgr(Database);
				OICalcoloContribRRiduzMgr contribRRiduzMgr = new OICalcoloContribRRiduzMgr(Database);
				//contribRMgr.GetByContribTTipoOnereDestinazione(
				
				// Elimino tutte le righe di o_icalcolocontribr_riduz collegate al ContribTAttuale
                List<OICalcoloContribR> listaContribR = contribRMgr.GetListDaContribT(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue));

				foreach (OICalcoloContribR contribR in listaContribR)
				{
					List<OICalcoloContribRRiduz> listaRiduzioni = contribRRiduzMgr.GetListaRiduzioniDaContribR( contribR );

					foreach (OICalcoloContribRRiduz riduzione in listaRiduzioni)
						contribRRiduzMgr.Delete(riduzione);
				}


				foreach(RepeaterItem rptItm in rptTipiCausali.Items)
				{
					OIModificariduzioniCtrl modificariduzioniCtrl = (OIModificariduzioniCtrl)rptItm.FindControl("ModificariduzioniCtrl");
					List<OIModificariduzioniCtrl.OIModificariduzioniCtrlReturnValue> valoriModificati = modificariduzioniCtrl.GetValoriModificati();

					foreach (OIModificariduzioniCtrl.OIModificariduzioniCtrlReturnValue campoModificato in valoriModificati)
					{
                        OICalcoloContribR contribR = contribRMgr.GetByContribTTipoOnereDestinazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), campoModificato.IdTipoOnere, IdDestinazione);

						OICalcoloContribRRiduz riduz = new OICalcoloContribRRiduz();
						riduz.Idcomune = IdComune;
						riduz.Codiceistanza = ContribT.Codiceistanza;
						riduz.FkOiccrId = contribR.Id;
						riduz.FkOcrrId = campoModificato.IdCausale;
						riduz.Riduzioneperc = campoModificato.Importo;
						riduz.Note = campoModificato.Note;

						contribR.Note = String.Empty;
						contribRMgr.Update(contribR);

						contribRRiduzMgr.Insert(riduz);						
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
	}
}
