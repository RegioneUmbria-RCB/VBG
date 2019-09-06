namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
	using Ninject;

	public partial class GestioneEndoV2 : IstanzeStepPage
	{
		private EndoprocedimentiDellaDomandaOnline _dataSource = null;

		[Inject]
		public EndoprocedimentiService EndoprocedimentiService { get; set; }
		
		#region Testi e proprietà letti dall'xml

		public string TitoloEndoPrincipale 
		{ 
			get 
			{ 
				return ltrTitoloEndoPrincipale.Text; 
			}

			set 
			{ 
				ltrTitoloEndoPrincipale.Text = value; 
			} 
		}

		public string TitoloEndoAttivati 
		{ 
			get 
			{ 
				return ltrTitoloEndoAttivati.Text; 
			}

			set 
			{ 
				ltrTitoloEndoAttivati.Text = value; 
			} 
		}

		public string TitoloEndoAttivabili 
		{ 
			get 
			{ 
				return ltrTitoloEndoAttivabili.Text; 
			}

			set 
			{ 
				ltrTitoloEndoAttivabili.Text = value; 
			} 
		}

		public bool MostraFamiglieEndo
		{
			get { return this.ViewstateGet<bool>("MostraFamiglieEndo", true); }
			set { this.ViewStateSet("MostraFamiglieEndo", value); }
		}

		public bool ModificaProcedimentiProposti
		{
			get { return this.ViewstateGet<bool>("ModificaProcedimentiProposti", false); }
			set { this.ViewStateSet("ModificaProcedimentiProposti", value); }
		}

		public string TestoSezioneAltriEndo
		{
			get { return ltrTitoloAltriEndo.Text; }
			set { ltrTitoloAltriEndo.Text = value; }
		}

		public string TestoBottoneAltriEndo
		{
			get { return cmdAttivaAltriEndo.Text; }
			set { cmdAttivaAltriEndo.Text = value; }
		}

		public string TestoBottoneTornaAListaEndoEndo
		{
			get { return cmdTornaAllaLista.Text; }
			set { cmdTornaAllaLista.Text = value; }
		}

		public string TestoSezioneEditAltriEndo
		{
			get { return ltrTitoloAltriEndoAttivabili.Text; }
			set { ltrTitoloAltriEndoAttivabili.Text = value; }
		}

        public bool IgnoraIncompatibilitaEndoprocedimenti
        {
            get { object o = this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"] = value; }
        }

        #endregion

        protected EndoprocedimentiDellaDomandaOnline DataSource
		{
			get
			{
				if (this._dataSource == null)
				{
					var codIntervento = ReadFacade.Domanda.AltriDati.Intervento.Codice;
                    var codiceComune = ReadFacade.Domanda.AltriDati.CodiceComune;
                    
                    this._dataSource = this.EndoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(codIntervento, codiceComune);
				}

				return this._dataSource;
			}
		}
		
		public List<int> ListaIdSelezionati
		{
			get { return ViewstateGet<List<int>>("ListaIdSelezionati", null); }
			set { ViewStateSet("ListaIdSelezionati", value); }
		}

		protected void Page_Load(object sender, EventArgs e)
		{
			// Il salvataggio dei dati viene effettuato dal service
			Master.IgnoraSalvataggioDati = true;

			grigliaEndoPrincipale.MostraFamiglia = grigliaEndoPrincipale.MostraTipoEndo = this.MostraFamiglieEndo;
			grigliaEndoAttivati.MostraFamiglia = grigliaEndoAttivati.MostraTipoEndo = this.MostraFamiglieEndo;
			grigliaEndoAttivabili.MostraFamiglia = grigliaEndoAttivabili.MostraTipoEndo = this.MostraFamiglieEndo;
			grigliaAltriEndo.MostraFamiglia = grigliaAltriEndo.MostraTipoEndo = this.MostraFamiglieEndo;
			grigliaAltriEndoAttivabili.MostraFamiglia = grigliaAltriEndoAttivabili.MostraTipoEndo = this.MostraFamiglieEndo;

			if (!this.IsPostBack)
			{
				this.DataBind();
			}
		}

		#region ciclo di vita dello step

		public override void OnInitializeStep()
		{
			/*
			if (DataSource.IdSelezionatiDefault.Count == 0)
				EndoprocedimentiService.EliminaEndoprocedimenti(IdDomanda);
			*/
		}

		public override bool CanEnterStep()
		{
			return this.DataSource.FamiglieEndoprocedimentiPrincipali.Count > 0 ||
				this.DataSource.FamiglieEndoprocedimentiFacoltativi.Count > 0 ||
				this.DataSource.FamiglieEndoprocedimentiAttivati.Count > 0 ||
				this.DataSource.FamiglieEndoprocedimentiAttivabili.Count > 0;
		}

		public override void OnBeforeExitStep()
		{
			// recupero gli id selezionati dall'utente
			this.SincronizzaListaIdSelezionati();

			this.EndoprocedimentiService.ImpostaEndoSelezionati(this.IdDomanda, this.ListaIdSelezionati);
		}
		
		public override bool CanExitStep()
		{
			var endoIncompatibili = EndoprocedimentiService.GetEndoprocedimentiIncompatibili(IdDomanda);

			if (endoIncompatibili.Count() == 0 || this.IgnoraIncompatibilitaEndoprocedimenti)
			{
				return true;
			}

			this.Errori.AddRange(endoIncompatibili.Select(x => x.ToString()));

			return false;
		}

		#endregion
		
		protected void cmdAttivaAltriEndo_click(object sender, EventArgs e)
		{
			this.SincronizzaListaIdSelezionati();

			multiView.ActiveViewIndex = 1;
			Master.MostraPaginatoreSteps = false;
		}
		
		protected void cmdTornaAllaLista_click(object sender, EventArgs e)
		{
			this.SincronizzaListaIdSelezionati();

			multiView.ActiveViewIndex = 0;
			Master.MostraPaginatoreSteps = true;

			this.DataBind();
		}

		private void SincronizzaListaIdSelezionati()
		{
			this.ListaIdSelezionati = grigliaEndoPrincipale.GetCodiciFoglieSelezionate()
													  .Union(grigliaEndoAttivati.GetCodiciFoglieSelezionate())
													  .Union(grigliaEndoAttivabili.GetCodiciFoglieSelezionate())
													  .Union(grigliaAltriEndoAttivabili.GetCodiciFoglieSelezionate())
													  .ToList();
		}

		public override void DataBind()
		{
			if (this.ListaIdSelezionati == null)
			{
				this.ListaIdSelezionati = ReadFacade.Domanda
												.Endoprocedimenti
												.Endoprocedimenti
												.Select(endo => endo.Codice)
												.ToList();
			}

			// Se non è stato ancora selezionato nessun endo aggiungo l'endo principale e gli endo attivati
			// Se non è possibile modificare la lista degli endo proposti 
			// aggiungo alla lista l'endo principale e gli endo attivati (se la lista non li contiene già)
			if (this.ListaIdSelezionati.Count == 0 || !this.ModificaProcedimentiProposti)
			{
				this.ListaIdSelezionati = this.ListaIdSelezionati.Union(this.DataSource.IdSelezionatiDefault).ToList();
			}

			this.PopolaGriglieDegliEndoDellIntervento();

			this.PopolaGrigliaEndoFacoltativiSelezionati();

			this.PopolaGrigliaAltriEndoAttivabili();

			// Se non esistono endo principali o attivabili ma esistono altri endo attivabili 
			// apro direttamente la view di visualizzazione degli altri endo
			if (grigliaEndoPrincipale.DataSource.Length == 0 &&
				grigliaEndoAttivati.DataSource.Length == 0 &&
				grigliaEndoAttivabili.DataSource.Length == 0 &&
				grigliaAltriEndoAttivabili.DataSource.Length > 0)
			{
				this.cmdAttivaAltriEndo_click(this, EventArgs.Empty);

				Master.MostraBottoneAvanti = true;
				Master.MostraPaginatoreSteps = true;

				cmdTornaAllaLista.Visible = false;
			}
		}

		private void PopolaGrigliaAltriEndoAttivabili()
		{
			grigliaAltriEndoAttivabili.ListaIdSelezionati = this.ListaIdSelezionati;
			grigliaAltriEndoAttivabili.DataSource = this.DataSource.FamiglieEndoprocedimentiFacoltativi.ToArray();
			grigliaAltriEndoAttivabili.DataBind();

			if (this.DataSource.FamiglieEndoprocedimentiFacoltativi.Count == 0)
			{
				pnlAltriEndo.Visible = cmdAttivaAltriEndo.Visible = false;
			}
		}

		private void PopolaGrigliaEndoFacoltativiSelezionati()
		{
			var famiglieEndoFacoltativiSelezionate = this.DataSource.GetEndoprocedimentiFacoltativiSelezionatiDaListaId(this.ListaIdSelezionati).ToArray();

			grigliaAltriEndo.ListaIdSelezionati = this.ListaIdSelezionati;
			grigliaAltriEndo.DataSource = famiglieEndoFacoltativiSelezionate;
			grigliaAltriEndo.DataBind();

			pnlAltriEndo.Visible = famiglieEndoFacoltativiSelezionate.Length > 0;
		}

		private void PopolaGriglieDegliEndoDellIntervento()
		{
			// é possibile modificare gli endo preselezionati tra gli endo attivati
			// o quelli principali?
			grigliaEndoPrincipale.ModificaProcedimentiProposti = this.ModificaProcedimentiProposti;
			grigliaEndoAttivati.ModificaProcedimentiProposti = this.ModificaProcedimentiProposti;

			// Se non esistono endoprocedimenti principali collegati all'intervento selezionato
			// nascondo l'intero pannello
			if (this.DataSource.FamiglieEndoprocedimentiPrincipali.Count == 0)
			{
				pnlEndoPrincipale.Visible = false;
			}

			grigliaEndoPrincipale.ListaIdSelezionati = this.ListaIdSelezionati;
			grigliaEndoPrincipale.DataSource = this.DataSource.FamiglieEndoprocedimentiPrincipali.ToArray();
			grigliaEndoPrincipale.DataBind();

			// Se non esistono endoprocedimenti attivati collegati all'intervento selezionato
			// nascondo l'intero pannello
			if (this.DataSource.FamiglieEndoprocedimentiAttivati.Count == 0)
			{
				pnlEndoAttivati.Visible = false;
			}

			grigliaEndoAttivati.ListaIdSelezionati = this.ListaIdSelezionati;
			grigliaEndoAttivati.DataSource = this.DataSource.FamiglieEndoprocedimentiAttivati.ToArray();
			grigliaEndoAttivati.DataBind();

			// Se non esistono endoprocedimenti attivabili collegati all'intervento selezionato
			// nascondo l'intero pannello
			if (this.DataSource.FamiglieEndoprocedimentiAttivabili.Count == 0)
			{
				pnlEndoAttivabili.Visible = false;
			}

			grigliaEndoAttivabili.ListaIdSelezionati = this.ListaIdSelezionati;
			grigliaEndoAttivabili.DataSource = this.DataSource.FamiglieEndoprocedimentiAttivabili.ToArray();
			grigliaEndoAttivabili.DataBind();
		}
	}
}
