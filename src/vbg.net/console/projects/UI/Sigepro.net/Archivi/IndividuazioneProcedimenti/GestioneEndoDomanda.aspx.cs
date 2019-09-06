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

namespace Sigepro.net.Archivi.IndividuazioneProcedimenti
{
	public partial class GestioneEndoDomanda : BasePage
	{
		protected int IdDomanda
		{
			get { return Convert.ToInt32(Request.QueryString["IdDomanda"]); }
		}

		DomandeFront m_domanda = null;

		protected DomandeFront Domanda
		{
			get
			{
				if (m_domanda == null)
					m_domanda = new DomandeFrontMgr(Database).GetById(IdComune, IdDomanda);

				return m_domanda;
			}
		}


		protected void Page_Load(object sender, EventArgs e)
		{
			this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;

			if (!IsPostBack)
			{
				InizializzaComboEndo();
			}
		}

		public void InizializzaComboEndo()
		{
			DataBindComboFamiglie();

			ddlFamiglia.Value = "";

			ddlFamiglia_ValueChanged(this, EventArgs.Empty);
		}

		public void DataBindComboFamiglie()
		{
			TipiFamiglieEndoMgr mgr = new TipiFamiglieEndoMgr(Database);

			TipiFamiglieEndo filtro= new TipiFamiglieEndo();
			filtro.Idcomune = IdComune;
			filtro.OthersWhereClause.Add( "(software='" + Software + "' or software='TT')" );
			filtro.OrderBy = "TIPO ASC";

			ddlFamiglia.Item.DataSource = mgr.GetList(filtro);
			ddlFamiglia.Item.DataBind();
			ddlFamiglia.Item.Items.Insert(0, "");
		}

		protected void gvLista_RowDeleting(object sender, GridViewDeleteEventArgs e)
		{
			int codiceInventario = Convert.ToInt32(gvLista.DataKeys[e.RowIndex].Value);

			DomandeFrontEndoMgr mgr = new DomandeFrontEndoMgr(Database);

			DomandeFrontEndo dfe = mgr.GetById(IdComune, IdDomanda, codiceInventario);

			try
			{
				mgr.Delete(dfe);

				e.Cancel = true;
				gvLista.DataBind();
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Cancellazione, ex);
			}


		}

		protected void ddlFamiglia_ValueChanged(object sender, EventArgs e)
		{
			int? idFamigliaEndo = ddlFamiglia.Value == String.Empty ? (int?)null : (int?)Convert.ToInt32( ddlFamiglia.Value );

			ddlTipologia.Item.DataSource = GetListaTipiEndo(idFamigliaEndo);
			ddlTipologia.Item.DataBind();

			ddlTipologia.Item.Items.Insert(0, new ListItem(""));
			ddlTipologia.Value = "";

			RefreshComboEndo();
		}

		private void RefreshComboEndo()
		{
			ddlNuovoEndo.Item.DataSource = new InventarioProcedimentiMgr(Database).GetListByTipo(IdComune, Software, ddlFamiglia.Value, ddlTipologia.Value);
			ddlNuovoEndo.Item.DataBind();

			ddlNuovoEndo.Item.Items.Insert(0, "");
			ddlNuovoEndo.Item.SelectedIndex = 0;
		}

		private List<TipiEndo> GetListaTipiEndo(int? codiceFamiglia)
		{
			TipiEndo filtro = new TipiEndo();
			filtro.Idcomune = IdComune;
			filtro.OthersWhereClause.Add( "(software='" + Software + "' or software='TT')" );
			filtro.OrderBy = "TIPO ASC";

			if (codiceFamiglia.HasValue)
				filtro.Codicefamigliaendo = codiceFamiglia.Value;
/*			else
				filtro.OthersWhereClause.Add("Codicefamigliaendo is null");*/

			return new TipiEndoMgr(Database).GetList(filtro);
		}

		protected void ddlTipologia_ValueChanged(object sender, EventArgs e)
		{
			RefreshComboEndo();
		}

		protected void cmdAggiungi_Click(object sender, EventArgs e)
		{
			try
			{
				DomandeFrontEndo dfe = new DomandeFrontEndo();
				dfe.Idcomune = IdComune;
				dfe.Codicedomanda = IdDomanda;
				dfe.Codiceinventario = Convert.ToInt32(ddlNuovoEndo.Value);

				new DomandeFrontEndoMgr(Database).Insert(dfe);

				InizializzaComboEndo();

				gvLista.DataBind();
			}
			catch (Exception ex)
			{
				MostraErrore(AmbitoErroreEnum.Inserimento, ex);
			}
		}
	}
}
