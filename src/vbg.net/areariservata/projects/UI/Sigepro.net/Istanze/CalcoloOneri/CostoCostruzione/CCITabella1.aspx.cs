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
using SIGePro.Net;
using System.Collections.Generic;
using PersonalLib2.Sql;
using Init.Utils.Web.UI;

namespace Sigepro.net.Istanze.CalcoloOneri.CostoCostruzione
{
	public partial class Tabella1 : BasePage
	{
		CCICalcoli m_calcolo = null;
		Init.SIGePro.Data.Istanze m_istanza = null;

		CCICalcoli Calcolo
		{
			get
			{
				if (m_calcolo == null)
					m_calcolo = new CCICalcoliMgr(Database).GetById( IdComune , Convert.ToInt32( Request.QueryString["IdCalcolo"] ));

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

		public Tabella1()
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

		/// <summary>
		/// Verifica che siano state inserite tutte le righe necessarie in tabella1
		/// </summary>
		private void VerificaRighe()
		{
            List<CCITabella1> righeInTabella1 = new CCICalcoliMgr(Database).VerificaEsistenzaRigheInTabella1(IdComune, Istanza.SOFTWARE, Calcolo.Id.GetValueOrDefault(int.MinValue), Calcolo.Codiceistanza.GetValueOrDefault(int.MinValue));
		}

		public override void DataBind()
		{
			CCITabella1 cls = new CCITabella1();
			cls.Idcomune = IdComune;
			cls.FkCcicId = Calcolo.Id;
			cls.UseForeign = useForeignEnum.Yes;
			cls.OrderBy = "Id asc";

			gvDettagli.DataSource = new CCITabella1Mgr(Database).GetList(cls);
			gvDettagli.DataBind();
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			string redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx?Token={0}&CodiceIstanza={1}&IdCalcoloTot={2}";

			CCICalcoloTContributoMgr contribMgr = new CCICalcoloTContributoMgr(Database);
            CCICalcoloTContributo contribt = contribMgr.GetByIdCalcolo(IdComune, Calcolo.Id.GetValueOrDefault(int.MinValue));

			Response.Redirect( String.Format( redirUrl , Token , Istanza.CODICEISTANZA , contribt.FkCcictId ) );
		}

		protected void cmdProsegui_Click(object sender, EventArgs e)
		{
			CCITabella1Mgr mgr = new CCITabella1Mgr(Database);

			foreach (GridViewRow r in gvDettagli.Rows)
			{
				IntTextBox itbAlloggi = (IntTextBox)r.FindControl("itbAlloggi");
				DoubleTextBox dtbSu = (DoubleTextBox)r.FindControl("dtbSu");

				int id = Convert.ToInt32(gvDettagli.DataKeys[r.RowIndex].Value);

				CCITabella1 cls = mgr.GetById( IdComune , id );

				cls.Alloggi = itbAlloggi.ValoreInt;
				cls.Su = dtbSu.ValoreDouble;

				mgr.Update(cls);
			}

			string redirUrl = "~/Istanze/CalcoloOneri/CostoCostruzione/CCITabella2.aspx?Token={0}&IdCalcolo={1}";
			Response.Redirect(String.Format(redirUrl, Token, Calcolo.Id));
		}
	}
}
