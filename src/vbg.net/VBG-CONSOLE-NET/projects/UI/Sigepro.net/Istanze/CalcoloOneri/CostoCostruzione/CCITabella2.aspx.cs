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
using PersonalLib2.Sql;
using Init.Utils.Web.UI;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
	public partial class Tabella2 : BasePage
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

		public Tabella2()
		{
			//VerificaSoftware = false;
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;

			if (!IsPostBack)
			{
				VerificaRighe();
				DataBind();
			}
		}

		private void VerificaRighe()
		{
            List<CCITabella2> righeInTabella2 = new CCICalcoliMgr(Database).VerificaEsistenzaRigheInTabella2(IdComune, Istanza.SOFTWARE, Calcolo.Id.GetValueOrDefault(int.MinValue), Calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));
		}

		public override void DataBind()
		{
			CCITabella2 cls = new CCITabella2();
			cls.Idcomune = IdComune;
			cls.FkCcicId = Calcolo.Id;
			cls.UseForeign = useForeignEnum.Yes;
			cls.OrderBy = "Id asc";

			gvDettagli.DataSource = new CCITabella2Mgr(Database).GetList(cls);
			gvDettagli.DataBind();
		}

		protected void cmdProsegui_Click(object sender, EventArgs e)
		{
			CCITabella2Mgr mgr = new CCITabella2Mgr(Database);

			foreach (GridViewRow r in gvDettagli.Rows)
			{
				DoubleTextBox dtbSuperficie = (DoubleTextBox)r.FindControl("dtbSuperficie");

				int id = Convert.ToInt32(gvDettagli.DataKeys[r.RowIndex].Value);

				CCITabella2 cls = mgr.GetById(IdComune, id);

				cls.Superficie = dtbSuperficie.ValoreDouble;

				mgr.Update(cls);
			}

			string redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella4.aspx?Token={0}&IdCalcolo={1}";
			Response.Redirect(String.Format(redirUrl, Token, Calcolo.Id));
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			string fmtUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella1.aspx?Token={0}&IdCalcolo={1}";
			Response.Redirect(String.Format(fmtUrl, Token, Calcolo.Id), true);	
		}
	}
}
