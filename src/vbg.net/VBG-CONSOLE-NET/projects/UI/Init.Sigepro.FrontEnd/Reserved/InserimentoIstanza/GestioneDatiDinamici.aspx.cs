using System;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneEndoprocedimenti;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.SIGePro.DatiDinamici;
using Ninject;
using Init.SIGePro.DatiDinamici.WebControls.MaschereSolaLettura;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
	public partial class GestioneDatiDinamici : IstanzeStepPage
	{
		private static class Constants
		{
			public const string CssClassElementoCompilato = "compilato";
			public const string CssClassElementoNonCompilato = "";
		}

		public string TitoloSchedaCittadiniextracomunitari
		{
			get { object o = this.ViewState["TitoloSchedaCittadiniextracomunitari"]; return o == null ? "Modello per cittadini extracomunitari" : (string)o; }
			set { this.ViewState["TitoloSchedaCittadiniextracomunitari"] = value; }
		}

		public bool IsSchedaDinamicaEcCompilata
		{
			get { object o = this.ViewState["IsSchedaDinamicaEcCompilata"]; return o == null ? false : (bool)o; }
			set { this.ViewState["IsSchedaDinamicaEcCompilata"] = value; }
		}

        public bool IgnoraSchedaCittadinoExtracomunitario
        {
            get { object o = this.ViewState["IgnoraSchedaCittadinoExtracomunitario"]; return o == null ? false : (bool)o; }
            set { this.ViewState["IgnoraSchedaCittadinoExtracomunitario"] = value; }
        }
        

		[Inject]
		public IModelliDinamiciService ModelliDinamiciService { get; set; }

		[Inject]
		public IConfigurazione<ParametriWorkflow> _configurazione { get; set; }

        public bool MostraTitoloSchedeIntervento
        {
            get { object o = this.ViewState["MostraTitoloSchedeintervento"]; return o == null ? true : (bool)o; }
            set { this.ViewState["MostraTitoloSchedeintervento"] = value; }
        }


		protected void Page_Load(object sender, EventArgs e)
		{
			// Il master si occupa del salvataggio dei dati
			Master.IgnoraSalvataggioDati = true;

			if (!IsPostBack)
				DataBind();
		}

		#region Ciclo di vita dello step

		public override void OnInitializeStep()
		{
			ModelliDinamiciService.SincronizzaModelliDinamici(IdDomanda, IgnoraSchedaCittadinoExtracomunitario);
		}

		public override bool CanEnterStep()
		{
			return ReadFacade.Domanda.DatiDinamici.Modelli.Count() > 0;
		}

		public override bool CanExitStep()
		{
			if (ReadFacade.Domanda.DatiDinamici.EsistonoModelliObbligatoriNonCompilati())
			{
				Errori.Add("Per proseguire è necessario compilare tutte le schede obbligatorie");

				return false;
			}

			return true;
		}


		#endregion

		public class SchedeBindingItem
		{
			public int Codice { get; set; }
			public string Descrizione { get; set; }
			public bool Facoltativa { get; set; }
			public string Compilata { get; set; }
		}
		

		public override void DataBind()
		{
			var interventoBindingSource = ReadFacade.Domanda
													.DatiDinamici
													.ModelliIntervento
													.OrderBy( x => x.Ordine)
													.Select(x => new SchedeBindingItem
													{
														Codice = x.Modello.IdModello,
														Compilata = x.Modello.Compilato ? Constants.CssClassElementoCompilato : Constants.CssClassElementoNonCompilato,
														Descrizione = x.Modello.Descrizione,
														Facoltativa = x.Modello.Facoltativo
													});

			rptSchedeIntervento.DataSource = interventoBindingSource;
			rptSchedeIntervento.DataBind();

			if (interventoBindingSource.Count() == 0)
				rptSchedeIntervento.Visible = false;


			var endoBindingSource = ReadFacade.Domanda
											  .Endoprocedimenti
											  .NonAcquisiti
											  .OrderByDescending(x => ValoreEndo(x))
											  .ThenBy(x=>x.Descrizione)
											  .Select(x =>
													new
													{
														DescrizioneIntervento = x.Descrizione,
														Schede = ReadFacade.Domanda
																		   .DatiDinamici
																		   .GetModelliEndo(x.Codice)
																		   .OrderBy(y => y.Ordine)
																		   .Select(scheda => new SchedeBindingItem
																		   {
																				Codice = scheda.Modello.IdModello,
																				Compilata = GetClasseCssSchedaDinamicaCompilata(scheda.Modello.Compilato),
																				Descrizione = scheda.Modello.Descrizione,
																				Facoltativa = scheda.Modello.Facoltativo
																		   })
													})
											   .Where(x => x.Schede.Count() > 0);

			rptEndoprocedimenti.DataSource = endoBindingSource;
			rptEndoprocedimenti.DataBind();

			// Scheda cittadini extracomunitari
			var modelloEc = ReadFacade.Domanda.DatiDinamici.ModelloCittadinoExtracomunitario;

			pnlSchedaCittadiniExtracomunitari.Visible = modelloEc != null;

			if (modelloEc != null)
			{
				IsSchedaDinamicaEcCompilata						= modelloEc.Compilato;

				lblTestoSchedaCittadiniExtracomunitari.Text		= this.TitoloSchedaCittadiniextracomunitari;
				lnkEcSelezioneSchedaIntervento.Text				= modelloEc.Descrizione;
				lnkEcSelezioneSchedaIntervento.CommandArgument	= modelloEc.IdModello.ToString();
				ltrEcAsterisco.Text								= modelloEc.Facoltativo ? "" : "*";
			}
		}

		public string GetClasseCssSchedaDinamicaCompilata( bool compilata )
		{
			return compilata ? Constants.CssClassElementoCompilato : Constants.CssClassElementoNonCompilato;
		}

		private int ValoreEndo(Endoprocedimento endo)
		{
			if (endo.Principale)
				return 100;

			if (!endo.Facoltativo)
				return 10;

			return 1;
		}


		public void OnSchedaSelezionata(object sender, EventArgs e)
		{
			int idScheda = Convert.ToInt32(((LinkButton)sender).CommandArgument);

			CaricaSchedaDinamica(idScheda, PrimoIndiceSelezionabileNellaScheda(idScheda));

			this.Master.MostraPaginatoreSteps = false;
		}

		public void OnSchedaEliminata(object sender, EventArgs e)
		{
			try
			{
				var idModello = renderer.DataSource.IdModello;
				var indiceModello = renderer.DataSource.IndiceModello;

				ModelliDinamiciService.EliminaModello(IdDomanda,idModello, indiceModello);

				CaricaSchedaDinamica(IdSchedaSelezionata, PrimoIndiceSelezionabileNellaScheda(IdSchedaSelezionata));
			}
			catch (Exception ex)
			{
				this.Errori.Add( ex.Message );
			}

		}

		private int PrimoIndiceSelezionabileNellaScheda(int idScheda)
		{
			var indici = ModelliDinamiciService.GetIndiciScheda(IdDomanda, idScheda);

			return (indici == null || indici.Count() == 0) ? 0 : indici.First();
		}


		public void cmdChiudi_Click(object sender, EventArgs e)
		{
			multiView.ActiveViewIndex = 0;
			this.Master.MostraPaginatoreSteps = true;
		}


		private bool Salva()
		{
			try
			{
				if (renderer.DataSource == null)
					return false;

				try
				{
					renderer.DataSource.ValidaModello();
				}
				catch (ValidazioneModelloDinamicoException /*ex*/)
				{
					MostraErroreSalvataggio();
					return false;
				}

				ModelliDinamiciService.Salva(IdDomanda, renderer.DataSource);

				return true;
			}
			catch (SalvataggioModelloDinamicoException)
			{
				MostraErroreSalvataggio();
				return false;
			}
		}


		public void cmdSalvaeResta_Click(object sender, EventArgs e)
		{
			if (Salva())
				CaricaSchedaDinamica(IdSchedaSelezionata, paginatoreSchedeDinamiche.IndiceCorrente);
		}

		public void cmdSalva_Click(object sender, EventArgs e)
		{
			if (Salva())
			{
				multiView.ActiveViewIndex = 0;
				this.Master.MostraPaginatoreSteps = true;

				DataBind();
			}
		}


		private void MostraErroreSalvataggio()
		{
			Page.ClientScript.RegisterStartupScript(this.GetType(), "notifica", "alert('Si sono verificati errori durante il salvataggio');", true);
			//DataBind();
		}

		public int IdSchedaSelezionata
		{
			get { object o = this.ViewState["IdSchedaSelezionata"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IdSchedaSelezionata"] = value; }
		}

		public int IndiceSchedaSelezionata
		{
			get { object o = this.ViewState["IndiceSchedaSelezionata"]; return o == null ? -1 : (int)o; }
			set { this.ViewState["IndiceSchedaSelezionata"] = value; }
		}


		protected void OnIndiceSelezionato(object sender, EventArgs e)
		{
			CaricaSchedaDinamica(IdSchedaSelezionata, paginatoreSchedeDinamiche.IndiceCorrente);
		}

		protected void OnNuovaScheda(object sender, EventArgs e)
		{
			CaricaSchedaDinamica(IdSchedaSelezionata, paginatoreSchedeDinamiche.IndiceNuovaScheda);
		}


		private void CaricaSchedaDinamica(int idScheda, int indiceScheda)
		{
			IdSchedaSelezionata		= idScheda;
			IndiceSchedaSelezionata = indiceScheda;

			var scheda = ModelliDinamiciService.GetModelloDinamico(IdDomanda, idScheda, indiceScheda);

			var idCampoAttivitaAteco = _configurazione.Parametri.IdCampoDinamicoPerAttivitaAtecoPrevalente;

			if (idCampoAttivitaAteco.HasValue && ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.HasValue)
			{
				var campo = scheda.TrovaCampoDaId(idCampoAttivitaAteco.Value);

				if (campo != null && String.IsNullOrEmpty(campo.ListaValori[0].Valore))
				{
					// TODO: Leggere il codice ateco del campo invece dell'id
					var idNodo = ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.Value;
					var voceAteco = ReadFacade.Ateco.GetDettagli( IdComune , idNodo );

					campo.ListaValori[0].Valore = voceAteco.Codicebreve;
				}
			}

			scheda.ModelloFrontoffice = true;

			lblTitoloModello.Text = scheda.NomeModello;

			multiView.ActiveViewIndex = 1;

			scheda.EseguiScriptCaricamento();

			renderer.ImpostaMascheraSolaLettura(new MascheraSolaLetturaVuota());
			renderer.RicaricaModelloDinamico += new SIGePro.DatiDinamici.WebControls.ModelloDinamicoRenderer.RicaricaModelloDinamicoDelegate(renderer_RicaricaModelloDinamico);
			renderer.DataSource = scheda;
			renderer.DataBind();


			paginatoreSchedeDinamiche.Visible = scheda.ModelloMultiplo;
			paginatoreSchedeDinamiche.IndiciSchede = ModelliDinamiciService.GetIndiciScheda(IdDomanda, idScheda);
			paginatoreSchedeDinamiche.IndiceCorrente = IndiceSchedaSelezionata;
			paginatoreSchedeDinamiche.DataBind();

			cmdSalvaEResta.Visible = scheda.ModelloMultiplo;
		}

		ModelloDinamicoBase renderer_RicaricaModelloDinamico(object sender, EventArgs e)
		{
			var scheda = ModelliDinamiciService.GetModelloDinamico(IdDomanda, IdSchedaSelezionata, IndiceSchedaSelezionata);			

			var idCampoAttivitaAteco = _configurazione.Parametri.IdCampoDinamicoPerAttivitaAtecoPrevalente;

			if (idCampoAttivitaAteco.HasValue && ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.HasValue)
			{
				var campo = scheda.TrovaCampoDaId(idCampoAttivitaAteco.Value);

				if (campo != null && String.IsNullOrEmpty(campo.ListaValori[0].Valore))
				{
					// TODO: Leggere il codice ateco del campo invece dell'id
					var idNodo = ReadFacade.Domanda.AltriDati.AttivitaAtecoPrimaria.Value;
					var voceAteco = ReadFacade.Ateco.GetDettagli(IdComune, idNodo);

					campo.ListaValori[0].Valore = voceAteco.Codicebreve;
				}
			}

			scheda.ModelloFrontoffice = true;

			multiView.ActiveViewIndex = 1;

			scheda.EseguiScriptCaricamento();

			return scheda;
		}
	}
}
