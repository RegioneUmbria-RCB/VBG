using System;
using System.Linq;
using System.Collections.Generic;
using System.Text;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.SIGePro.Data;
using Init.SIGePro.DatiDinamici;
using Init.Utils;
using SIGePro.Net;
using Init.SIGePro.DatiDinamici.WebControls;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;


namespace Sigepro.net.Istanze.DatiDinamici
{
	public partial class DatiDinamiciCtrl : System.Web.UI.UserControl
	{
		private static class Constants
		{
			public const int DettaglioSchedaViewId = 0;
			public const int AggiungiSchedaViewId = 1;
			public const int NessunaSchedaView = 2;
			public const int AggiungiSchedaAttivitaViewId = 3;
		}


		bool g_RegistraScript = true;	// Se impostato a false non registra gli script di verifica modello

		public bool UsaFormAggiungiNuovaSchedaAttivita
		{
			get { object o = this.ViewState["UsaFormAggiungiNuvaSchedaAttivita"]; return o == null ? false : (bool)o; }
			set { this.ViewState["UsaFormAggiungiNuvaSchedaAttivita"] = value; }
		}


		public delegate List<int> GetListaIndiciSchedaDelegate(int modello);
		public event GetListaIndiciSchedaDelegate GetListaIndiciScheda;

		public delegate List<ElementoListaModelli> GetListaModelliDelegate();
		public event GetListaModelliDelegate GetListaModelli;

		public delegate ModelloDinamicoBase GetModelloDinamicoDaIdDelegate(int idModello, int indice);
		public event GetModelloDinamicoDaIdDelegate GetModelloDinamicoDaId;

		public delegate void AggiungiSchedaDelegate(int idModello);
		public event AggiungiSchedaDelegate AggiungiScheda;

		public delegate void EliminaSchedaDelegate(int idModello);
		public event EliminaSchedaDelegate EliminaScheda;

		public delegate string GetUrlPaginaStoricoDelegate(int idModello);
		public event GetUrlPaginaStoricoDelegate GetUrlPaginaStorico;

		public delegate bool VerificaEsistenzaStoricoDelegate(int idModello);
		public event VerificaEsistenzaStoricoDelegate VerificaEsistenzaStorico;

		public delegate IEnumerable<KeyValuePair<string,string>> GetListaSoftwareAttivitaDelegate();
		public event GetListaSoftwareAttivitaDelegate GetListaSoftwareAttivita;

		public delegate IMascheraSolaLettura GetMascheraSolaLetturaDelegate(int idModello);
		public event GetMascheraSolaLetturaDelegate GetMascheraSolaLettura;

		public event EventHandler Close;

		public bool SolaLettura
		{
			get { object o = this.ViewState["SolaLettura"]; return o == null ? false : (bool)o; }
			set { this.ViewState["SolaLettura"] = value; }
		}

		public Func<string> ExtraQueryStringFunction { get; set; }

		/// <summary>
		/// Ottiene l'id della scheda selezionata
		/// </summary>
		protected int IdModelloSelezionato
		{
			get
			{
				string idModello = Request.QueryString["Modello"];
				if (String.IsNullOrEmpty(idModello))
					return GetPrimoModello();

				return Convert.ToInt32(idModello);
			}
		}

		/// <summary>
		/// Ottiene l'indice della scheda selezionata
		/// </summary>
		protected int IndiceModello
		{
			get
			{
				string indice = Request.QueryString["Idx"];

				if (String.IsNullOrEmpty(indice))
					return 0;

				return Convert.ToInt32(indice);
			}
		}


		/// <summary>
		/// Nome della chiave in querystring che contiene il codice univoco dell'oggetto
		/// a cui il modello è collegato
		/// </summary>
		public string NomeChiaveCodice
		{
			get { object o = this.ViewState["CodiceChiave"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["CodiceChiave"] = value; }
		}


		#region ciclo di vita della pagina

		protected void Page_Load(object sender, EventArgs e)
		{
			if (String.IsNullOrEmpty(NomeChiaveCodice))
				throw new ArgumentException("Parametro NomeChiaveCodice non impostato per il controllo " + this.ID);

			BasePage.ImpostaScriptEliminazione(this.Page, cmdEliminaScheda);

			if (!IsPostBack)
			{
				renderer.DataSource = null;
				DataBind();
			}

		}

		public override void DataBind()
		{
			using (var cp = new CodeProfiler("Databind del modello dinamico"))
			{
				BindListaModelliAttivi();

				if (IdModelloSelezionato == -1)
				{
					// Se non ho un modello selezionato mostro la sezione "nuova scheda"
					if (!SolaLettura)
						VisualizzaFormNuovaScheda();
					else
						VisualizzaNessunaScheda();
				}
				else
				{
					// Effettua il binding del modello attivo
					ModelloDinamicoBase modello = GetModelloDinamicoDaId(IdModelloSelezionato, IndiceModello);

					// TODO: spostare la chiamata nel modello
					modello.EseguiScriptCaricamento();

					if (this.GetMascheraSolaLettura != null)
						renderer.ImpostaMascheraSolaLettura(this.GetMascheraSolaLettura(IdModelloSelezionato));
					else
						renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());

					renderer.DataSource = modello;
					renderer.DataBind();

					List<int> listaIndici = GetListaIndiciScheda(IdModelloSelezionato);

					if (!listaIndici.Contains(IndiceModello))
						listaIndici.Add(IndiceModello);

					rptMolteplicita.DataSource = listaIndici;
					rptMolteplicita.DataBind();

					rptMolteplicita.Visible = modello.ModelloMultiplo;
				}


				cmdSalva.Visible = cmdEliminaScheda.Visible = cmdStorico.Visible = (IdModelloSelezionato != -1);
				cmdAggiungiScheda.Visible = !cmdEliminaScheda.Visible;

				bool storicoPresente = StoricoVisibile();

				if (cmdStorico.Visible && !storicoPresente)
					cmdStorico.Visible = false;

				if (cmdEliminaScheda.Visible && storicoPresente)
					cmdEliminaScheda.Visible = false;
			}
		}

		private bool StoricoVisibile()
		{
			if (VerificaEsistenzaStorico == null)
				return false;

			return VerificaEsistenzaStorico(IdModelloSelezionato);
		}




		protected override void OnPreRender(EventArgs e)
		{
			if (SolaLettura)
			{
				cmdSalva.Visible = false;
				cmdEliminaScheda.Visible = false;
				cmdAggiungiScheda.Visible = false;
			}

			if (ModelloDinamicoRenderer.DataSourceAttuale != null && ModelloDinamicoRenderer.DataSourceAttuale.ReadOnlyWeb)
			{
				cmdSalva.Visible = false;
				cmdEliminaScheda.Visible = false;
			}

			if (g_RegistraScript)
			{
				// Registra i clientscript di tutti i controlli del modello
				foreach (CampoDinamicoBase campo in renderer.DataSource.Campi)
				{
					if (String.IsNullOrEmpty(campo.ClientScript)) continue;

					string scriptFmtString = @"function Fn{0}_ClientScript( el ){{{1}}}";
					string script = String.Format(scriptFmtString, campo.Id, campo.ClientScript);

					Page.ClientScript.RegisterClientScriptBlock(campo.GetType(), campo.Id + "_ClientScript", script, true);
				}


			}

			base.OnPreRender(e);




		}

		protected override void Render(HtmlTextWriter writer)
		{
			if (g_RegistraScript)
			{
				// Registra i clientscript di tutti i controlli del modello
				foreach (CampoDinamicoBase campo in renderer.DataSource.Campi)
				{
					if (String.IsNullOrEmpty(campo.ClientScript)) continue;

					string startScriptFmtString = "Fn{0}_ClientScript( document.getElementById( g_datiDinamiciExtender.GetClientIdCampo('{0}') ) );"; ;
					string startScript = String.Format(startScriptFmtString, campo.Id);

					campo.ClientScript = "";

					Page.ClientScript.RegisterStartupScript(campo.GetType(), campo.Id + "_ClientScriptStartup", startScript, true);
				}
			}



			base.Render(writer);
		}

		#endregion


		/// <summary>
		/// Ottiene il codice del primo modello disponibile
		/// </summary>
		/// <returns></returns>
		protected int GetPrimoModello()
		{
			List<ElementoListaModelli> modelliAttivi = GetListaModelli();

			if (modelliAttivi.Count > 0)
				return modelliAttivi[0].Id;

			return -1;
		}


		#region gestione dell'intestazione delle schede
		/// <summary>
		/// Visualizza la lista delle schede dell'istanza/anagrafe/attività corrente
		/// </summary>
		protected void BindListaModelliAttivi()
		{
			multiView.ActiveViewIndex = Constants.DettaglioSchedaViewId;

			List<ElementoListaModelli> modelliAttivi = GetListaModelli();//new IstanzeDyn2ModelliTMgr(Database).GetModelliIstanza(IdComune, Convert.ToInt32(CodiceIstanza), CodiceMovimento);

			if (!SolaLettura)
			{
				ElementoListaModelli nuovaScheda = new ElementoListaModelli(-1, "Nuova scheda");
				modelliAttivi.Add(nuovaScheda);
			}

			rptListaSchede.DataSource = modelliAttivi;
			rptListaSchede.DataBind();
		}

		/// <summary>
		/// Ritorna la classe di una cella della lista di schede. Può essere "SchedaAttiva" se idScheda == idModelloSelezionato
		/// oppure "Scheda" se la scheda non è quella selezionata
		/// </summary>
		/// <param name="idScheda">id della scheda per cui deve essere ricavata la classe</param>
		/// <returns>classe css della scheda</returns>
		public string ClasseCella(object idScheda)
		{
			if (Convert.ToInt32(idScheda) == IdModelloSelezionato) return "SchedaAttiva";

			return "Scheda";
		}

		#endregion

		#region gestione del modello attivo

		/// <summary>
		/// Salva il modello corrente
		/// </summary>
		public void SalvaModello(object sender, EventArgs e)
		{

		}

		#endregion

		#region Navigazione di base della pagina

		/// <summary>
		/// Chiude la pagina corrente e ritorna alla pagina chiamante
		/// </summary>
		protected void cmdChiudi_Click(object sender, EventArgs e)
		{
			this.Close(this, EventArgs.Empty);
		}

		#endregion

		#region visualizzazione, inserimento e eliminazione schede
		/// <summary>
		/// Passa da una scheda ad un'altra
		/// </summary>
		protected void CambiaScheda(object sender, EventArgs e)
		{
			int idModello = Convert.ToInt32(((LinkButton)sender).CommandArgument);

			RedirectAllaScheda(idModello.ToString(), "0");
		}

		/// <summary>
		/// Passa alla visualizzazione della sezione "Nuova scheda"
		/// </summary>
		protected void VisualizzaFormNuovaScheda()
		{
			cmdStorico.Visible = false;
			g_RegistraScript = false;

			if (UsaFormAggiungiNuovaSchedaAttivita)
				VisualizzaFormNuovaSchedaAttivita();
			else
				VisualizzaFormNuovaSchedaStandard();
		}

		protected void VisualizzaFormNuovaSchedaStandard()
		{
			multiView.ActiveViewIndex = Constants.AggiungiSchedaViewId;
			
			hidIdNuovaScheda.Value = String.Empty;
			txtNuovaScheda.Text = String.Empty;
		}

		protected void VisualizzaFormNuovaSchedaAttivita()
		{
			multiView.ActiveViewIndex = Constants.AggiungiSchedaAttivitaViewId;

			ddlCercaInModuloAtt.DataSource = GetListaSoftwareAttivita();
			ddlCercaInModuloAtt.DataBind();

			hidIdNuovaSchedaAtt.Value = String.Empty;
			txtNuovaSchedaAtt.Text = String.Empty;
		}

		protected void VisualizzaNessunaScheda()
		{
			multiView.ActiveViewIndex = Constants.NessunaSchedaView;
			g_RegistraScript = false;
		}

		/// <summary>
		/// Elimina la scheda visualizzata correntemente
		/// </summary>
		protected void EliminaSchedaAttiva(object sender, EventArgs e)
		{
			if (IdModelloSelezionato <= 0) return;

			try
			{
				EliminaScheda(IdModelloSelezionato);

				RedirectAllaScheda("", "");
			}
			catch (Exception ex)
			{
				(this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Cancellazione, ex);
			}
			
		}

		/// <summary>
		/// Aggiunge una nuova scheda all'istanza corrente
		/// </summary>
		protected void AggiungiNuovaScheda(object sender, EventArgs e)
		{
			try
			{
				var idNuovaScheda = String.Empty;

				if (UsaFormAggiungiNuovaSchedaAttivita)
					idNuovaScheda = hidIdNuovaSchedaAtt.Value;
				else
					idNuovaScheda = hidIdNuovaScheda.Value;

				if (String.IsNullOrEmpty(idNuovaScheda))
					throw new ArgumentException("Selezionare la nuova scheda da aggiungere");

				AggiungiScheda(Convert.ToInt32(idNuovaScheda));

				RedirectAllaScheda( idNuovaScheda , "0");
			}
			catch (Exception ex)
			{
				(this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Inserimento, ex);
			}
		}

		#endregion



		/// <summary>
		/// Effettua il redirect ad una nuova scheda
		/// </summary>
		/// <param name="idNuovaScheda">Id della scheda da aprire</param>
		/// <param name="indice">Indice della scheda da aprire</param>
		protected void RedirectAllaScheda(string idNuovaScheda, string indice)
		{
			string url = Request.Url.AbsoluteUri;

			// Elimino la querystring dall'url
			if (!String.IsNullOrEmpty(Request.Url.Query))
			{
				int idx = url.IndexOf("?");
				url = url.Substring(0, idx);
			}


			string software = Request.QueryString["Software"];
			string token = Request.QueryString["Token"];
			string valoreChiave = Request.QueryString[NomeChiaveCodice];
			string baseUrl = Server.UrlEncode(Request.QueryString["BaseUrl"]);
			string returnTo = Server.UrlEncode(Request.QueryString["ReturnTo"]);

			string fmtUrl = "{0}?Modello={1}&Idx={2}&Software={3}&Token={4}&{5}={6}&BaseUrl={7}&ReturnTo={8}";

			string redirUrl = String.Format(fmtUrl, url,
													  idNuovaScheda,
													  indice,
													  software,
													  token,
													  NomeChiaveCodice,
													  valoreChiave,
													  baseUrl,
													  returnTo);

			if (ExtraQueryStringFunction != null)
			{
				var val = ExtraQueryStringFunction();

				redirUrl += val.StartsWith("&") ? val : "&" + val;
			}

			Response.Redirect(redirUrl);
		}


		/// <summary>
		/// Salvataggio dei dati del modello corrente
		/// </summary>
		/// <param name="sender"></param>
		/// <param name="e"></param>
		protected void cmdSalva_Click(object sender, EventArgs e)
		{
			using (var cp = new CodeProfiler("Salvataggio del modello dinamico"))
			{
				if (renderer.DataSource == null)
					return;

				try
				{
					renderer.DataSource.ValidaModello();
				}
				catch (ValidazioneModelloDinamicoException ex)
				{
					MostraErroreSalvataggio();
					return;
				}

				renderer.DataSource.Salva();

				if (renderer.DataSource.ErroriScript.Count()> 0)
				{
					MostraErroreSalvataggio();
					return;
				}

				DataBind();

				Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "$('#salvataggioEffettuato').fadeIn();setTimeout('$(\\'#salvataggioEffettuato\\').fadeOut()',2000);", true);
			}
		}

		private void MostraErroreSalvataggio()
		{
			Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
			//DataBind();
		}

		protected void CambiaIndice(object sender, EventArgs e)
		{
			LinkButton lb = (LinkButton)sender;

			string nuovoIndice = lb.CommandArgument;

			RedirectAllaScheda(IdModelloSelezionato.ToString(), nuovoIndice);
		}

		protected void NuovaSchedaMultipla(object sender, ImageClickEventArgs e)
		{
			int nuovoIndice = CalcolaNuovoIndice();

			RedirectAllaScheda(IdModelloSelezionato.ToString(), nuovoIndice.ToString());
		}

		private int CalcolaNuovoIndice()
		{
			List<int> indici = GetListaIndiciScheda(IdModelloSelezionato);

			int nuovoIndice = 0;

			if (indici.Count > 0)
				nuovoIndice = indici[indici.Count - 1] + 1;

			return nuovoIndice;
		}

		protected void DuplicaSchedaMultipla(object sender, ImageClickEventArgs e)
		{
			int nuovoIndice = CalcolaNuovoIndice();

			ModelloDinamicoBase nuovaScheda = GetModelloDinamicoDaId(IdModelloSelezionato, nuovoIndice);
			ModelloDinamicoBase schedaAttuiale = GetModelloDinamicoDaId(IdModelloSelezionato, IndiceModello);

			nuovaScheda.CopiaDa(schedaAttuiale);

			nuovaScheda.Salva();

			RedirectAllaScheda(IdModelloSelezionato.ToString(), nuovoIndice.ToString());


		}

		protected void EliminaSchedaMultipla(object sender, ImageClickEventArgs e)
		{
			ModelloDinamicoBase schedaAttuale = GetModelloDinamicoDaId(IdModelloSelezionato, IndiceModello);

			try
			{
				schedaAttuale.Elimina();

				List<int> indici = GetListaIndiciScheda(IdModelloSelezionato);

				if (indici.Count == 0)
					indici.Add(0);

				RedirectAllaScheda(IdModelloSelezionato.ToString(), indici[0].ToString());
			}
			catch (Exception ex)
			{
				(this.Page as BasePage).MostraErrore(BasePage.AmbitoErroreEnum.Cancellazione, ex);
			}

		}

		protected bool IndiceCorrente(object indice)
		{
			return (int)indice == IndiceModello;
		}

		protected string TestoIndice(object indice)
		{
			//return "[" + (Convert.ToInt32(indice) + 1).ToString() + "]&nbsp;";
			return "[" + (Convert.ToInt32(indice)).ToString() + "]&nbsp;";
		}

		protected string GetUrlStorico()
		{
			if (this.GetUrlPaginaStorico == null)
				return "";

			return this.GetUrlPaginaStorico(IdModelloSelezionato);
		}

		protected string StileSpanSoftwareTT()
		{
			if (NomeChiaveCodice.ToUpperInvariant() == "CODICEANAGRAFE")
				return "display:none";

			return "";
		}
	}
}