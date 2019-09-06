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
using Init.SIGePro.Manager;
using Init.SIGePro.Data;
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.DatiDinamici;
using SIGePro.Net;
using Init.SIGePro.DatiDinamici;
using Init.SIGePro.Manager.Manager;
using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccessProviders;

namespace Sigepro.net.Istanze.DatiDinamici.Storico
{
	public partial class AnagrafeDyn2Storico : BasePage
	{
		/// <summary>
		/// Id dell'anagrafica corrente
		/// </summary>
		protected int CodiceAnagrafe { get { return Convert.ToInt32(Request.QueryString["CodiceAnagrafe"]); } }

		/// <summary>
		/// Id modello 
		/// </summary>
		protected int IdModello { get { return Convert.ToInt32(Request.QueryString["IdModello"]); } }

		public override string Software
		{
			get
			{
				return "TT";
			}
		}

		public int IndiceScheda
		{
			get { object o = this.ViewState["IndiceScheda"]; return o == null ? 0 : (int)o; }
			set { this.ViewState["IndiceScheda"] = value; }
		}

		public int IndiceVersione
		{
			get { object o = this.ViewState["IndiceVersione"]; return o == null ? 0 : (int)o; }
			set { this.ViewState["IndiceVersione"] = value; }
		}




		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
				DataBind();
		}

		public override void DataBind()
		{
			gvLista.DataSource = new AnagrafeDyn2ModelliTStoricoMgr(Database).GetList(IdComune, CodiceAnagrafe, IdModello);
			gvLista.DataBind();

			Anagrafe anagrafe = new AnagrafeMgr(Database).GetById(IdComune, CodiceAnagrafe);
			ltrAnagrafica.Text = anagrafe.NOMINATIVO + " " + anagrafe.NOME;

			Dyn2ModelliT modt = new Dyn2ModelliTMgr(Database).GetById(IdComune, IdModello);
			ltrNomeModello.Text = modt.Descrizione;
		}

		private void BindDettaglio(AnagrafeDyn2ModelliTStorico cls)
		{
			IndiceVersione = cls.Idversione.Value;

			multiView.ActiveViewIndex = 1;

			// bindo la lista degli indici
			List<int> indiciVersione = new AnagrafeDyn2DatiStoricoMgr(Database).LeggiIndiciVersione(IdComune, CodiceAnagrafe, IdModello, IndiceVersione);

			IndiceScheda = indiciVersione[0];

			BindListaIndici();

			// Bindo iil modello
			BindModello();

		}

		private void BindListaIndici()
		{
			List<int> indiciVersione = new AnagrafeDyn2DatiStoricoMgr(Database).LeggiIndiciVersione(IdComune, CodiceAnagrafe, IdModello, IndiceVersione);

			rptMolteplicita.DataSource = indiciVersione;
			rptMolteplicita.DataBind();

			if (indiciVersione.Count < 2)
				rptMolteplicita.Visible = false;
		}

		private void BindModello()
		{
			var dap = new Dyn2DataAccessProvider(Database);
			var loader = new ModelloDinamicoLoader(dap, IdComune, ModelloDinamicoLoader.TipoModelloDinamicoEnum.Backoffice);
			var modello = new ModelloDinamicoAnagrafica(loader, IdModello, CodiceAnagrafe, IndiceScheda, true, IndiceVersione);

			ModelloDinamicoRenderer1.DataSource = modello;
			ModelloDinamicoRenderer1.DataBind();
		}



		protected void multiView_ActiveViewChanged(object sender, EventArgs e)
		{
			switch (multiView.ActiveViewIndex)
			{
				case (1):
					Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Scheda;
					return;
				default:
					Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
					return;
			}
		}


		#region Scheda lista

		protected void gvLista_SelectedIndexChanged(object sender, EventArgs e)
		{
			int id = Convert.ToInt32(gvLista.DataKeys[gvLista.SelectedIndex].Value);

			AnagrafeDyn2ModelliTStorico cls = new AnagrafeDyn2ModelliTStoricoMgr(Database).GetById(IdComune, id, CodiceAnagrafe, IdModello);

			BindDettaglio(cls);
		}

		protected void cmdElimina_Click(object sender, EventArgs e)
		{
			List<int> listaId = new List<int>();

			try
			{
				foreach (GridViewRow gvr in gvLista.Rows)
				{
					CheckBox chkSelezionato = (CheckBox)gvr.FindControl("chkSelezionato");

					if (!chkSelezionato.Checked)
						continue;

					int id = Convert.ToInt32(gvLista.DataKeys[gvr.RowIndex].Value);

					listaId.Add(id);
				}

				Database.BeginTransaction();

				AnagrafeDyn2ModelliTStoricoMgr mgr = new AnagrafeDyn2ModelliTStoricoMgr(Database);

				for (int i = 0; i < listaId.Count; i++)
				{
					AnagrafeDyn2ModelliTStorico cls = mgr.GetById(IdComune, listaId[i], CodiceAnagrafe, IdModello);

					mgr.Delete(cls);
				}

				Database.CommitTransaction();

				DataBind();
			}
			catch (Exception ex)
			{
				Database.RollbackTransaction();

				MostraErrore(ex);
			}
		}
		#endregion


		#region Scheda dettaglio
		protected void CambiaIndice(object sender, EventArgs e)
		{
			LinkButton lb = (LinkButton)sender;

			IndiceScheda = Convert.ToInt32(lb.CommandArgument);

			BindListaIndici();

			BindModello();
		}

		protected bool IndiceCorrente(object indice)
		{
			return (int)indice == IndiceScheda;
		}

		protected string TestoIndice(object indice)
		{
			//return "[" + (Convert.ToInt32(indice) + 1).ToString() + "]&nbsp;";
			return "[" + (Convert.ToInt32(indice)).ToString() + "]&nbsp;";
		}

		protected void cmdChiudiDettaglio_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}
		#endregion

	}
}
