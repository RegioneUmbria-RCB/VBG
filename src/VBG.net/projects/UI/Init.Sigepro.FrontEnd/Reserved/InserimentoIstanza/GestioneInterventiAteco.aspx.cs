using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

using System.Text;
using Ninject;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.ReadInterface;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.AmbitoRicercaIntervento;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneInterventiAteco : IstanzeStepPage
	{
		const string MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT = "Non sono stati individuati interventi riconducibili all'attività ATECO selezionata. Verrà mostrata la lista completa degli interventi attivabili online";

		const int ID_VISTA_PRESENTAZIONE = 0;
		const int ID_VISTA_ATECO = 1;
		const int ID_VISTA_INTERVENTI = 2;
		const int ID_VISTA_DETTAGLI = 3;

		[Inject]
		public DatiDomandaService DatiDomandaService { get; set; }


		#region Parametri letti dalla configurazione dello step
		public string TestoIntroduzione
		{
			get { return ltrTestoIntroduzione.Text; }
			set { ltrTestoIntroduzione.Text = value; }
		}

		public string IntestazioneRicercaAteco
		{
			get { return ltrIntestazioneRicercaAteco.Text; }
			set { ltrIntestazioneRicercaAteco.Text = value; }
		}

		public string TestoRicercaAteco
		{
			get { return ltrTestoRicercaAteco.Text; }
			set { ltrTestoRicercaAteco.Text = value; }
		}

		public string IntestazioneRicercaIntervento
		{
			get { return ltrIntestazioneRicercaIntervento.Text; }
			set { ltrIntestazioneRicercaIntervento.Text = value; }
		}

		public string TestoRicercaIntervento
		{
			get { return ltrTestoRicercaIntervento.Text; }
			set { ltrTestoRicercaIntervento.Text = value; }
		}

		public string IntestazioneDettaglio
		{
			get { return ltrIntestazioneDettaglio.Text; }
			set { ltrIntestazioneDettaglio.Text = value; }
		}

		public string TestoDettaglio
		{
			get { return ltrTestoDettaglio.Text; }
			set { ltrTestoDettaglio.Text = value; }
		}

		public string MessaggioCollegamentoAtecoNonTrovato
		{
			get { object o = this.ViewState["MessaggioCollegamentoAtecoNonTrovato"];
			return o == null ? MESSAGGIO_COLLEGAMENTO_ATECO_NON_TROVATO_DEFAULT : (string)o;
			}
			set { this.ViewState["MessaggioCollegamentoAtecoNonTrovato"] = value; }
		}


		public bool MostraAlberoAteco
		{
			get { object o = ViewState["MostraAlberoAteco"]; return o == null ? true: (bool)o; }
			set { ViewState["MostraAlberoAteco"] = value; }
		}

		public string TestoBottoneSelezioneIntervento
		{
			get { return cmdSelezionaIntervento.Text; }
			set { cmdSelezionaIntervento.Text = value; }
		}
		#endregion

		public int IdInterventoSelezionato
		{
			get { object o = Session["IdInterventoSelezionato"]; return o == null ? -1 : (int)o; }
			set { Session["IdInterventoSelezionato"] = value; SelezioneInterventoAvvenuta = true; }
		}

		public int IdAttivitaAtecoSelezionata
		{
			get { object o = Session["IdAttivitaAtecoSelezionata"]; return o == null ? -1 : (int)o; }
			set { Session["IdAttivitaAtecoSelezionata"] = value;  }
		}

		/// <summary>
		/// Flag che identifica se l'intervento è stato selezionato dall'utente
		/// </summary>
		public bool SelezioneInterventoAvvenuta
		{
			get { object o = this.ViewState["SelezioneInterventoAvvenuta"]; return o == null ? false : (bool)o; }
			set { this.ViewState["SelezioneInterventoAvvenuta"] = value; }
		}



		protected void Page_Load(object sender, EventArgs e)
		{
			// il salvataggio viene effettuato dal service
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
			{
				if (ReadFacade.Domanda.AltriDati.Intervento == null)
				{
					// TODO: mettere in configurazione un parametro per decidere se aprire la pagina
					// dall'albero ateco o dalla lista interventi
					MostraFormSelezione();
				}
				else
				{
					BindDettaglio();
				}
			}

		}

		#region Ciclo di vita dello step

		public override void OnInitializeStep()
		{
			base.OnInitializeStep();
		}

		public override bool CanEnterStep()
		{
			if (ReadFacade.Domanda.AltriDati.Intervento != null)
				IdInterventoSelezionato = ReadFacade.Domanda.AltriDati.Intervento.Codice;

			return true;
		}


		public override bool CanExitStep()
		{
			if (ReadFacade.Domanda.AltriDati.Intervento == null)
			{
				Errori.Add("Selezionare un tipo di intervento");
				return false;
			}

			return true;
		}
		#endregion


		protected void multiView_activeViewChanged(object sender, EventArgs e)
		{
			switch (multiView.ActiveViewIndex)
			{
				case ID_VISTA_PRESENTAZIONE:
					cmdAnnullaRicerca.Visible = SelezioneInterventoAvvenuta;
					break;
				case ID_VISTA_ATECO:
					break;
				case ID_VISTA_INTERVENTI:
					cmdAnnullaAlbero.Visible = MostraAlberoAteco || SelezioneInterventoAvvenuta;
					break;
			}
		}

		private void MostraFormSelezione()
		{
			if (MostraAlberoAteco)
			{
				MostraIntroduzione();
			}
			else
			{
				BindAlberoInterventi(this, -1);

				cmdAnnullaAlbero.Visible = false;
			}
		}

		protected void cmdSelezioneDirettaIntervento_Click(object sender, EventArgs e)
		{
			BindAlberoInterventi(this, -1);
		}





		private void MostraIntroduzione()
		{
			multiView.ActiveViewIndex = ID_VISTA_PRESENTAZIONE;
			Master.MostraBottoneAvanti = false;
		}


		private void BindAlberoAteco()
		{
			multiView.ActiveViewIndex = ID_VISTA_ATECO;
			Master.MostraBottoneAvanti = false;
		}


		private void BindDettaglio()
		{
            if (!ReadFacade.Interventi.InterventoAttivo(IdInterventoSelezionato, UserAuthenticationResult.LivelloAutenticazione, UserAuthenticationResult.DatiUtente.UtenteTester))
			{
				this.Errori.Add("L'intervento selezionato non è attivabile tramite domanda online. Selezionare un nuovo intervento.");
				
				MostraFormSelezione();

				return;
			}

			multiView.ActiveViewIndex = ID_VISTA_DETTAGLI;

			SelezioneInterventoAvvenuta = true;

			var albero = ReadFacade.Interventi.GetAlberaturaNodoDaId(IdComune, IdInterventoSelezionato);

			var falsoRoot = new NodoAlberoInterventiDto();

			falsoRoot.NodiFiglio = new ClassTreeOfInterventoDto[1];
			falsoRoot.NodiFiglio[0] = albero;

			treeRendererDettaglio.DataSource = falsoRoot;
			treeRendererDettaglio.DataBind();

			Master.MostraBottoneAvanti = true;
		}



		protected void BindAlberoInterventi(object sender, int idAteco)
		{
			alberoInterventi.UtenteTester = this.UserAuthenticationResult.DatiUtente.UtenteTester;

			IdAttivitaAtecoSelezionata = -1;

			if (idAteco > 0)	// è stata selezionata una foglia dell'albero ateco
			{
				IdAttivitaAtecoSelezionata = idAteco;

				if (!ReadFacade.Ateco.EsistonoInterventiCollegati(IdComune, Software, idAteco, new AmbitoRicercaAreaRiservata(false)))
					Errori.Add(MessaggioCollegamentoAtecoNonTrovato);
			}

			alberoInterventi.IdAteco = idAteco;
			multiView.ActiveViewIndex = ID_VISTA_INTERVENTI;

			Master.MostraBottoneAvanti = false;
		}

		protected void InterventoSelezionato(object sender, int idNodo)
		{
			IdInterventoSelezionato = idNodo;

			BindDettaglio();

			DatiDomandaService.ImpostaIdIntervento(IdDomanda, idNodo, IdAttivitaAtecoSelezionata == -1 ? (int?)null : IdAttivitaAtecoSelezionata);
		}

		public void cmdSelezionaIntervento_Click(object sender, EventArgs e)
		{
			//BindAlberoInterventi(this,alberoInterventi.IdAteco);
			MostraFormSelezione();
		}

		protected void cmdRicercaAteco_Click(object sender, EventArgs e)
		{
			BindAlberoAteco();
		}


		protected void cmdRicercaIntervento_Click(object sender, EventArgs e)
		{
			BindAlberoInterventi(this, -1);
		}

		/// <summary>
		/// Verifica se nella domanda sono presenti dati relativi agli step successivi.
		/// Questo metodo viene utilizzato nella funzione javascript che mostra l'alert nel caso in cui l'utente selezioni 
		/// il bottone "Modifica dell'intervento".
		/// Se non sono presenti dati relativi agli steps successivi l'alert NON viene mostrato
		/// </summary>
		/// <returns></returns>
		protected bool VerificaEsistenzaDatiStepSuccessivi()
		{
			if (ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Count() > 0)
				return true;

			if (ReadFacade.Domanda.Documenti.Count() > 0)
				return true;

			if (ReadFacade.Domanda.DatiDinamici.Modelli.Count() > 0)
				return true;

			return false;
		}


		protected void AnnullaRicercaClick(object sender, EventArgs e)
		{
			// Se è stato selezionato un intervento in precedenza 
			//	- Ritorno al dettaglio dell'intervento
			// Altrimenti
			//	- Se la sezione di ricerca ateco è visualizzata
			//		- Torno alla view di selezione attività ateco/amministrativa
			//	- Altrimenti
			//		- ??? Il bottone non dovrebbe essere visibile

			// E' già stato selezionato un intervento?
			if (SelezioneInterventoAvvenuta)
			{
				// Se l'annulla proviene dalla view che permette di scegliere tra classificazione ateco o amministrativa
				if (sender == cmdAnnullaRicerca)
				{
					// Torno al dettaglio dell'intervento selezionato in precedenza
					BindDettaglio();
					return;
				}
				else // se l'annulla è stato fatto dalla ricerca ateco o intervento
				{
					// l'albero ateco va visualizzato?
					if (MostraAlberoAteco)
					{
						// Se si mostro il form che permette di scegliere tra classificazione ateco o amministrativa
						MostraFormSelezione();
						return;
					}
					else
					{
						// Altrimenti mostro il dettaglio dell'intervento selezionato in precedenza
						BindDettaglio();
						return;
					}
				}
			}
			else // Non è ancora stato selezionato un intervento
			{
				// l'albero ateco va visualizzato?
				if (MostraAlberoAteco)
				{
					// Se si mostro il form che permette di scegliere tra classificazione ateco o amministrativa
					MostraFormSelezione();
					return;
				}
			}
		}
	}
}
