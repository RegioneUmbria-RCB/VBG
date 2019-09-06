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
using Init.SIGePro.Manager.Logic.CalcoloOneri.CostoCostruzione;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
	public partial class CCITabellaSnSa : BasePage
	{
		CCICalcoli m_calcolo = null;
		Init.SIGePro.Data.Istanze m_istanza = null;

		CCICalcoli Calcolo
		{
			get
			{
				if (m_calcolo == null)
					m_calcolo = new CCICalcoliMgr(Database).GetById(IdComune, Convert.ToInt32(Request.QueryString["IdCalcolo"]));

				return m_calcolo;
			}
		}

		Init.SIGePro.Data.Istanze Istanza
		{
			get
			{
				if (m_istanza == null)
                    m_istanza = new IstanzeMgr(Database).GetById(IdComune, Calcolo.Codiceistanza.Value);

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


		public CCITabellaSnSa()
		{
			//VerificaSoftware = false;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

			if (!IsPostBack)
			{
				DataBind();
			}
		}

		public override void DataBind()
		{
            dtbSuArt9.ValoreDouble = Calcolo.SuArt9.GetValueOrDefault(double.MinValue);
            dtbSa.ValoreDouble = Calcolo.Sa.GetValueOrDefault(double.MinValue);
		}

		protected void cmdProsegui_Click(object sender, EventArgs e)
		{
			Calcolo.SuArt9 = dtbSuArt9.ValoreDouble;
			Calcolo.Sa = dtbSa.ValoreDouble;

			new CCICalcoliMgr(Database).Update(Calcolo);

            ElaboratoreCostoCostruzione elab = new ElaboratoreCostoCostruzione(Database, IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));
			elab.Elabora();

			string url = "~/Istanze/CalcoloOneri/CostoCostruzione/DeterminazioneCCRiepilogo.aspx?Token={0}&IdCalcolo={1}";
			Response.Redirect(String.Format(url, Token, Calcolo.Id));
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			string fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";
			Response.Redirect(String.Format(fmtUrl, Token, Calcolo.Id));
		}


	}
}
