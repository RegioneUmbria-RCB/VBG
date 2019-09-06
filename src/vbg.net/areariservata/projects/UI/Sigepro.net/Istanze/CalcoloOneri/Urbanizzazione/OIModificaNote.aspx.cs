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

namespace Sigepro.net.Istanze.CalcoloOneri.Urbanizzazione
{
	public partial class OIModificaNote : BasePage
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

		private int IdTipoOnere
		{
			get
			{
				string idTipoOnere = Request.QueryString["IdTipoOnere"];
				if (String.IsNullOrEmpty(idTipoOnere))
					throw new ArgumentException("Parametro idTipoOnere non impostato");

				return Convert.ToInt32(idTipoOnere);
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
			{
				this.Title = "Modifica note per la variazione di " + new OTipiOneriMgr(Database).GetById(IdComune, IdTipoOnere).Descrizione;

				DataBind();
			}
		}

		public override void DataBind()
		{
			OICalcoloContribRMgr contribRMgr = new OICalcoloContribRMgr(Database);
            OICalcoloContribR ctrbR = contribRMgr.GetByContribTTipoOnereDestinazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), IdTipoOnere, IdDestinazione);

			txtNote.Text = ctrbR.Note;
		}

		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			OICalcoloContribRMgr contribRMgr = new OICalcoloContribRMgr(Database);
            OICalcoloContribR ctrbR = contribRMgr.GetByContribTTipoOnereDestinazione(IdComune, ContribT.Id.GetValueOrDefault(int.MinValue), IdTipoOnere, IdDestinazione);

			ctrbR.Note = txtNote.Text;

			contribRMgr.Update(ctrbR);

			string script = "window.returnValue = true;self.close();";

			this.Page.ClientScript.RegisterStartupScript(this.GetType(), "loadScript", script, true);
			return;
		}
	}
}
