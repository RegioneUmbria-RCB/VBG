namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
    using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
    using Init.SIGePro.Manager.DTO.Endoprocedimenti;
    using Ninject;
    using System;
    using System.Linq;

    public partial class GestioneEndoV2 : IstanzeStepPage
    {
        private readonly Lazy<EndoprocedimentiAreaRiservataDto> _dataSource = null;

        [Inject]
        public IEndoprocedimentiService _endoService { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> _configVisura { get; set; }

        #region Testi e proprietà letti dall'xml

        [StepProperty("Titolo della sezione \"Endo principale\"")]
        public string TitoloEndoPrincipale
        {
            get
            {
                return this.ltrTitoloEndoPrincipale.Text;
            }

            set
            {
                this.ltrTitoloEndoPrincipale.Text = value;
            }
        }

        [StepProperty("Titolo della sezione \"Endo attivati\"")]
        public string TitoloEndoAttivati
        {
            get
            {
                return this.ltrTitoloEndoAttivati.Text;
            }

            set
            {
                this.ltrTitoloEndoAttivati.Text = value;
            }
        }

        [StepProperty("Titolo della sezione \"Endo attivabili\"")]
        public string TitoloEndoAttivabili
        {
            get
            {
                return this.ltrTitoloEndoAttivabili.Text;
            }

            set
            {
                this.ltrTitoloEndoAttivabili.Text = value;
            }
        }

        [StepProperty("Se impostato a true mostra l'albero degli endo a partire dalle famiglie", ValoreDefault = "true")]
        public bool MostraFamiglieEndo
        {
            get { return this.ViewstateGet("MostraFamiglieEndo", true); }
            set { this.ViewStateSet("MostraFamiglieEndo", value); }
        }

        [StepProperty("Se impostato a true permette di modificare la lista degli" +
            "endoprocedimenti proposti deselezionando gli endo spuntati di default",
            ValoreDefault = "false")]
        public bool ModificaProcedimentiProposti
        {
            get { return this.ViewstateGet("ModificaProcedimentiProposti", false); }
            set { this.ViewStateSet("ModificaProcedimentiProposti", value); }
        }

        [StepProperty("Titolo della sezione \"Altri endoprocedimenti attivabili\"")]
        public string TestoSezioneAltriEndo
        {
            get { return this.ltrTitoloAltriEndo.Text; }
            set { this.ltrTitoloAltriEndo.Text = value; }
        }

        [StepProperty("Testo del bottone \"Altri endoprocedimenti\"")]
        public string TestoBottoneAltriEndo
        {
            get { return this.cmdAttivaAltriEndo.Text; }
            set { this.cmdAttivaAltriEndo.Text = value; }
        }

        [StepProperty("Testo del bottone \"Torna alla lista degli endoprocedimenti\"")]
        public string TestoBottoneTornaAListaEndoEndo
        {
            get { return this.cmdTornaAllaLista.Text; }
            set { this.cmdTornaAllaLista.Text = value; }
        }

        [StepProperty("Titolo della sezione \"Altri endoprocedimenti attivabili\"")]
        public string TestoSezioneEditAltriEndo
        {
            get { return this.ltrTitoloAltriEndoAttivabili.Text; }
            set { this.ltrTitoloAltriEndoAttivabili.Text = value; }
        }

        [StepProperty("Se impostato a true ignora eventuali incompatibilità tra gli endoprocedimenti", ValoreDefault = "true")]
        public bool IgnoraIncompatibilitaEndoprocedimenti
        {
            get { var o = this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"]; return o == null ? true : (bool)o; }
            set { this.ViewState["IgnoraIncompatibilitaEndoprocedimenti"] = value; }
        }

        [StepProperty("Se impostato a true è obbligatorio selezionare almeno un endoprocedimento della lista per poter proseguire allo step succcessivo", ValoreDefault = "false")]
        public bool SelezionareAlmenoUnEndo
        {
            get { var o = this.ViewState["SelezionareAlmenoUnEndo"]; return o == null ? false : (bool)o; }
            set { this.ViewState["SelezionareAlmenoUnEndo"] = value; }
        }

        [StepProperty("Testo dell'errore da mostrare se nella lista degli endo non è stato selezionato nessun endo. Dipende dal parametro \"SelezionareAlmenoUnEndo\"",
            ValoreDefault = "Selezionare almeno un elemento della lista")]
        public string MessaggioErroreSelezionareAlmenoUnEndo
        {
            get { var o = this.ViewState["MessaggioErroreSelezionareAlmenoUnendo"]; return o == null ? "Selezionare almeno un elemento della lista" : (string)o; }
            set { this.ViewState["MessaggioErroreSelezionareAlmenoUnendo"] = value; }
        }

        [StepProperty("Se impostato a true è possibile selezionare un solo endo per volta. La selezione di un endo deselezionerà altri endo già selezionati")]
        public bool SelezioneEsclusivaEndoAttivabili
        {
            get { var o = this.ViewState["SelezioneEsclusivaEndoAttivabili"]; return o == null ? false : (bool)o; }
            set { this.ViewState["SelezioneEsclusivaEndoAttivabili"] = value; }
        }
        [StepProperty("Se impostato a true mostra le informazioni aggiuntive sugli endoprocedimenti (icona con punto interrogativo). Default: true")]
        public bool MostraInformazioniEndo
        {
            get { var o = this.ViewState["MostraInformazioniEndo"]; return o == null || (bool)o; }
            set { this.ViewState["MostraInformazioniEndo"] = value; }
        }

        #endregion

        public GestioneEndoV2()
        {
            this._dataSource = new Lazy<EndoprocedimentiAreaRiservataDto>(() =>
            {
                var readInterface = this.ReadFacade.Domanda;
                var codIntervento = readInterface.AltriDati.Intervento.Codice;
                var codiceComune = readInterface.AltriDati.CodiceComune;

                return this._endoService.GetListaEndoDaIdIntervento(codiceComune, codIntervento);
            });
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Il salvataggio dei dati viene effettuato dal service
            this.Master.IgnoraSalvataggioDati = true;

            this.grigliaEndoPrincipale.MostraFamiglia = this.grigliaEndoPrincipale.MostraTipoEndo = this.MostraFamiglieEndo;
            this.grigliaEndoPrincipale.MostraInformazioniEndo = this.MostraInformazioniEndo;

            this.grigliaEndoAttivati.MostraFamiglia = this.grigliaEndoAttivati.MostraTipoEndo = this.MostraFamiglieEndo;
            this.grigliaEndoAttivati.MostraInformazioniEndo = this.MostraInformazioniEndo;

            this.grigliaEndoAttivabili.MostraFamiglia = this.grigliaEndoAttivabili.MostraTipoEndo = this.MostraFamiglieEndo;
            this.grigliaEndoAttivabili.MostraInformazioniEndo = this.MostraInformazioniEndo;

            this.grigliaAltriEndo.MostraFamiglia = this.grigliaAltriEndo.MostraTipoEndo = this.MostraFamiglieEndo;
            this.grigliaAltriEndo.MostraInformazioniEndo = this.MostraInformazioniEndo;

            this.grigliaAltriEndoAttivabili.MostraFamiglia = this.grigliaAltriEndoAttivabili.MostraTipoEndo = this.MostraFamiglieEndo;
            this.grigliaAltriEndoAttivabili.MostraInformazioniEndo = this.MostraInformazioniEndo;

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
            return this._dataSource.Value.Principali.Count > 0 ||
                this._dataSource.Value.Altri.Count > 0 ||
                this._dataSource.Value.Richiesti.Count > 0 ||
                this._dataSource.Value.Ricorrenti.Count > 0;
        }

        public override void OnBeforeExitStep()
        {
            this.SincronizzaListaIdSelezionati();
        }

        public override bool CanExitStep()
        {
            if (this.SelezionareAlmenoUnEndo && !this._endoService.AlmenoUnEndoPresente(this.IdDomanda))
            {
                this.Errori.Add(this.MessaggioErroreSelezionareAlmenoUnEndo);

                return false;
            }

            if (this.SelezioneEsclusivaEndoAttivabili && !this.grigliaEndoAttivabili.GetEndoSelezionati().Any())
            {
                this.Errori.Add(this.MessaggioErroreSelezionareAlmenoUnEndo);

                return false;
            }

            var endoIncompatibili = this._endoService.GetEndoprocedimentiIncompatibili(this.IdDomanda);

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

            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;
        }

        protected void cmdTornaAllaLista_click(object sender, EventArgs e)
        {
            // SincronizzaListaIdSelezionati();
            var endoPrecedenti = this._endoService.GetSubEndoSelezionati(this.IdDomanda)
                                                   .Endo
                                                   .Select(x => x.ToSubEndoprocedimentoSelezionato());

            // Rimuovo dalla lista gli endo che sarebbero facoltativi
            var codiciAltriEndo = this._dataSource.Value.Altri.SelectMany(x => x.ListaEndo).Select(x => x.Codice);
            endoPrecedenti = endoPrecedenti.Where(endo => !codiciAltriEndo.Contains(endo.Id));

            // Il totale degli endo selezionati è dato da tutti gli endo non facoltativi già attivati
            // più gli endo facoltativi attivati in questa fase
            var endoSelezionati = endoPrecedenti.Union(this.grigliaAltriEndoAttivabili.GetEndoSelezionati());

            this._endoService.SalvaEndoSelezionati(this.IdDomanda, endoSelezionati);

            this.multiView.ActiveViewIndex = 0;
            this.Master.MostraPaginatoreSteps = true;

            this.DataBind();
        }

        private void SincronizzaListaIdSelezionati()
        {
            // recupero gli id selezionati dall'utente
            var endoSelezionati = this.grigliaEndoPrincipale.GetEndoSelezionati()
                        .Union(this.grigliaEndoAttivati.GetEndoSelezionati())
                        .Union(this.grigliaEndoAttivabili.GetEndoSelezionati())
                        .Union(this.grigliaAltriEndo.GetEndoSelezionati());

            this._endoService.SalvaEndoSelezionati(this.IdDomanda, endoSelezionati);
        }

        public override void DataBind()
        {
            this.PopolaGriglieDegliEndoDellIntervento();

            this.PopolaGrigliaEndoFacoltativiSelezionati();

            this.PopolaGrigliaAltriEndoAttivabili();

            // Se non esistono endo principali o attivabili ma esistono altri endo attivabili 
            // apro direttamente la view di visualizzazione degli altri endo
            if (this.grigliaEndoPrincipale.DataSource.Length == 0 &&
                this.grigliaEndoAttivati.DataSource.Length == 0 &&
                this.grigliaEndoAttivabili.DataSource.Length == 0 &&
                this.grigliaAltriEndoAttivabili.DataSource.Length > 0)
            {
                this.cmdAttivaAltriEndo_click(this, EventArgs.Empty);

                this.Master.MostraBottoneAvanti = true;
                this.Master.MostraPaginatoreSteps = true;

                this.cmdTornaAllaLista.Visible = false;
            }
        }

        private void PopolaGrigliaAltriEndoAttivabili()
        {
            this.grigliaAltriEndoAttivabili.DataSource = this._dataSource.Value.Altri.ToArray();
            this.grigliaAltriEndoAttivabili.DataBind();

            if (this._dataSource.Value.Altri.Count == 0)
            {
                this.pnlAltriEndo.Visible = this.cmdAttivaAltriEndo.Visible = false;
            }
        }

        private void PopolaGrigliaEndoFacoltativiSelezionati()
        {
            var endoSelezionati = this._endoService
                                        .GetSubEndoSelezionati(this.IdDomanda)
                                        .Endo
                                        .Where(x => !x.IdPadre.HasValue)
                                        .Select(x => x.Id);

            var famiglieEndoFacoltativiSelezionate = this._dataSource.Value.GetFamiglieEndoFacoltativiDaListaIdEndo(endoSelezionati).ToArray();

            this.grigliaAltriEndo.DataSource = famiglieEndoFacoltativiSelezionate;
            this.grigliaAltriEndo.DataBind();

            this.pnlAltriEndo.Visible = famiglieEndoFacoltativiSelezionate.Length > 0;
        }

        private void PopolaGriglieDegliEndoDellIntervento()
        {
            // é possibile modificare gli endo preselezionati tra gli endo attivati
            // o quelli principali?
            this.grigliaEndoPrincipale.ModificaProcedimentiProposti = this.ModificaProcedimentiProposti;
            this.grigliaEndoAttivati.ModificaProcedimentiProposti = this.ModificaProcedimentiProposti;

            // Se non esistono endoprocedimenti principali collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (this._dataSource.Value.Principali.Count == 0)
            {
                this.pnlEndoPrincipale.Visible = false;
            }

            this.grigliaEndoPrincipale.DataSource = this._dataSource.Value.Principali.ToArray();
            this.grigliaEndoPrincipale.DataBind();

            // Se non esistono endoprocedimenti attivati collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (this._dataSource.Value.Richiesti.Count == 0)
            {
                this.pnlEndoAttivati.Visible = false;
            }

            this.grigliaEndoAttivati.DataSource = this._dataSource.Value.Richiesti.ToArray();
            this.grigliaEndoAttivati.DataBind();

            // Se non esistono endoprocedimenti attivabili collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (this._dataSource.Value.Ricorrenti.Count == 0)
            {
                this.pnlEndoAttivabili.Visible = false;
            }

            this.grigliaEndoAttivabili.DataSource = this._dataSource.Value.Ricorrenti.ToArray();
            this.grigliaEndoAttivabili.DataBind();
        }

        protected override void OnPreRender(EventArgs e)
        {
            /*
            // Se non è stato ancora selezionato nessun endo aggiungo l'endo principale e gli endo attivati
            // Se non è possibile modificare la lista degli endo proposti 
            // aggiungo alla lista l'endo principale e gli endo attivati (se la lista non li contiene già)
            if (ListaIdSelezionati.Count == 0 || !ModificaProcedimentiProposti)
            {
                ListaIdSelezionati = ListaIdSelezionati.Union(_dataSource.Value.GetEndoSelezionatiDiDefault()).ToList();
            }
             */


            var strutturaSubEndo = this._endoService.GetSubEndoSelezionati(this.IdDomanda);

            var script = "window._endoAttivati = " + strutturaSubEndo.ToJsonString() + ";";

            // var endoAttivati = this.ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Select(x => x.Codice.ToString());
            // var script = "endoAttivati = [" + String.Join(",", endoAttivati) + "];";

            this.Page.ClientScript.RegisterStartupScript(this.GetType(), "endoAttivati", script, true);

            base.OnPreRender(e);
        }
    }
}
