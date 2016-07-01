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
using System.Collections.Generic;
using Init.SIGePro.Manager.Logic.Ricerche;
using Init.SIGePro.Data;
using SIGePro.WebControls.Ajax;
using Init.SIGePro.Manager;
using PersonalLib2.Sql;
using Init.SIGePro.Manager.Logic.DatiDinamici.Statistiche;
using System.Text;

namespace Sigepro.net.Istanze.Statistiche
{
	public partial class StatisticheIstanze : BasePage
	{
		protected void Page_Load(object sender, EventArgs e)
		{
			if (!IsPostBack)
			{
				this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;

				rpTipologiaIntervento.QuerystringArguments["Software"] = Software;
				rpTecnico.InitParams["tipologia"] = "-1";
				rpDettaglioInformazione.InitParams["codiceSettore"] = "";

				DataBind();
			}
		}

		public override void DataBind()
		{
			// Registri
			TipologiaRegistri filtroRegistri = new TipologiaRegistri();
			filtroRegistri.IDCOMUNE = IdComune;
			filtroRegistri.SOFTWARE = Software;
			filtroRegistri.OrderBy = "TR_DESCRIZIONE asc";

			List<TipologiaRegistri> listaRegistri = new TipologiaRegistriMgr( Database ).GetList( filtroRegistri );
			listaRegistri.Insert( 0 , new TipologiaRegistri() );

			ddlTipologiaRegistro.Item.DataSource = listaRegistri;
			ddlTipologiaRegistro.Item.DataBind();

			// Stati istanza
			StatiIstanza filtroStati = new StatiIstanza();
			filtroStati.Idcomune = IdComune;
			filtroStati.Software = Software;
			filtroStati.OrderBy = "STATO asc";

			List<StatiIstanza> listaStati = new StatiIstanzaMgr(Database).GetList(filtroStati);
			listaStati.Insert(0, new StatiIstanza());
			listaStati[0].Codicestato = "%";

			ddlStatiIstanza.Item.DataSource = listaStati;
			ddlStatiIstanza.Item.DataBind();
		}

		#region Metodi di ricerca per ricercheplus
		[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
		public static string[] GetCompletionListTecnico(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  string software,
												  bool ricercaSoftwareTT,
												  Dictionary<string, string> initParams, string tipologia)
		{
			try
			{
				RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

				// Gestione di una ricerca custom
				sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
				{
					Anagrafe anag = (Anagrafe)e.SearchedClass;
					anag.TIPOLOGIA = tipologia;
				};

				return RicerchePlusCtrl.CreateResultList(sc.Find(true));
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}
		}

		[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
		public static string[] GetCompletionListAree(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  string software,
												  bool ricercaSoftwareTT,
												  Dictionary<string, string> initParams)
		{
			try
			{
				RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

				// Gestione di una ricerca custom
				sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
				{
					Aree area = (Aree)e.SearchedClass;
					area.UseForeign = useForeignEnum.Yes;
				};

				return RicerchePlusCtrl.CreateResultList(sc.Find(true));
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}
		}

		[System.Web.Services.WebMethodAttribute(), System.Web.Script.Services.ScriptMethodAttribute()]
		public static string[] GetCompletionListDettaglioInformazioni(string token, string dataClassType,
												  string targetPropertyName,
												  string descriptionPropertyNames,
												  string prefixText,
												  int count,
												  string software,
												  bool ricercaSoftwareTT,
												  Dictionary<string, string> initParams , string codiceSettore)
		{
			try
			{
				RicerchePlusSearchComponent sc = new RicerchePlusSearchComponent(token, dataClassType, targetPropertyName, descriptionPropertyNames, prefixText, count, software, ricercaSoftwareTT, initParams);

				// Gestione di una ricerca custom
				sc.Searching += delegate(object sender, RicerchePlusEventArgs e)
				{
					Attivita attivita = (Attivita)e.SearchedClass;
					attivita.CODICESETTORE = codiceSettore;
				};

				return RicerchePlusCtrl.CreateResultList(sc.Find(true));
			}
			catch (Exception ex)
			{
				return RicerchePlusCtrl.CreateErrorResult(ex);
			}
		}


		#endregion

		/*
		protected void rpTipologiaIntervento_ValueChanged(object sender, EventArgs e)
		{
			if (String.IsNullOrEmpty(rpTipologiaIntervento.Value)) return;

			int codiceAlberoProc = Convert.ToInt32(rpTipologiaIntervento.Value);

			AlberoProc proc = new AlberoProcMgr(Database).GetById(codiceAlberoProc, IdComune);

			if (String.IsNullOrEmpty(proc.FKIDPROCEDURA)) return;

			rpTipologiaProcedura.Class = new TipiProcedureMgr(Database).GetById(proc.FKIDPROCEDURA, IdComune);
		}*/

		

		protected void rpTipoInformazione_ValueChanged(object sender, EventArgs e)
		{
			if (String.IsNullOrEmpty(rpTipoInformazione.Value)) return;

			rpDettaglioInformazione.InitParams["codiceSettore"] = rpTipoInformazione.Value;
			rpDettaglioInformazione.Class = null;
		}

		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			base.CloseCurrentPage();
		}

		protected void cmdCerca_Click(object sender, EventArgs e)
		{
			ParametriStatisticaIstanze filtriRicerca = new ParametriStatisticaIstanze();
			filtriRicerca.Data.Inizio = txtDataDa.DateValue;
			filtriRicerca.Data.Fine = txtDataA.DateValue;
			filtriRicerca.Operatore = String.IsNullOrEmpty(rpOperatore.Value) ? (int?)null : Convert.ToInt32(rpOperatore.Value);
			filtriRicerca.Richiedente = String.IsNullOrEmpty(rpRichiedente.Value) ? (int?)null : Convert.ToInt32(rpRichiedente.Value);
			filtriRicerca.Tecnico = String.IsNullOrEmpty(rpTecnico.Value) ? (int?)null : Convert.ToInt32(rpTecnico.Value);
			filtriRicerca.TipologiaIntervento = String.IsNullOrEmpty(rpTipologiaIntervento.Value) ? (int?)null : Convert.ToInt32(rpTipologiaIntervento.Value);
			filtriRicerca.TipoProcedura = String.IsNullOrEmpty(rpTipologiaProcedura.Value) ? (int?)null : Convert.ToInt32(rpTipologiaProcedura.Value);
			filtriRicerca.Zonizzazione = String.IsNullOrEmpty(rpZonizzazione.Value) ? (int?)null : Convert.ToInt32(rpZonizzazione.Value);
			filtriRicerca.TipoInformazione = rpTipoInformazione.Value;
			filtriRicerca.DettaglioInformazione = rpDettaglioInformazione.Value;
			filtriRicerca.Registro = String.IsNullOrEmpty(ddlTipologiaRegistro.Value) ? (int?)null : Convert.ToInt32(ddlTipologiaRegistro.Value);
			/*filtriRicerca.MetriQuadri.Inizio = txtMqDa.ValoreInt;
			filtriRicerca.MetriQuadri.Fine = txtMqA.ValoreInt;
			filtriRicerca.ConteggioMq = chkConteggioPerMq.Item.Checked;*/
			filtriRicerca.CodiceStato = ddlStatiIstanza.Value;
			filtriRicerca.FiltriDatiDinamici = ctrlFiltriDatiDinamici.GetFiltri();

			multiView.ActiveViewIndex = 1;

			StatisticheIstanzeMgr mgr = new StatisticheIstanzeMgr(IdComune, Software, Database);
			List<Init.SIGePro.Data.Istanze>  istanzeTrovate = mgr.GeneraReport(filtriRicerca);

			lblnumeroIstanze.Text = istanzeTrovate.Count.ToString();

			gvRisultato.DataSource = istanzeTrovate;
			gvRisultato.DataBind();
		}


		#region traduzione dei dati della datagrid
		public string GeneraStringaRichiedente(object objIstanza)
		{
			Init.SIGePro.Data.Istanze istanza = (Init.SIGePro.Data.Istanze)objIstanza;

			StringBuilder str = new StringBuilder();
			str.Append(istanza.Richiedente.ToString("{NOMINATIVO} {NOME}"));

			if (istanza.TipoSoggetto != null)
			{
				str.Append("<br>");
				str.Append(istanza.TipoSoggetto);
			}

			if (istanza.AziendaRichiedente != null)
			{
				str.Append("<br>");
				str.Append(istanza.AziendaRichiedente.ToString("{NOMINATIVO} {NOME}"));
			}

			return str.ToString();
		}

		public string TraduciAltriIndirizzi(object objAltriIndirizzi)
		{
			List<IstanzeStradario> lst = (List<IstanzeStradario>)objAltriIndirizzi;

			if (lst.Count == 0) return String.Empty;

			StringBuilder str = new StringBuilder();

			str.Append("<ul>");

			for (int i = 0; i < lst.Count; i++)
			{
				str.Append("<li>").Append(lst[i]).Append("</li>");
			}

			str.Append("</ul>");

			return str.ToString();
		}

		#endregion

		protected void cmdChiudiLista_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
		}

		protected void multiView_ActiveViewChanged(object sender, EventArgs e)
		{
			switch (multiView.ActiveViewIndex)
			{
				case 0:
					this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Ricerca;
					break;

				case 1:
					this.Master.TabSelezionato = IntestazionePaginaTipiTabEnum.Risultato;
					break;
			}

			
		}
	}
}
