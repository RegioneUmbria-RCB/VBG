namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
    using Init.Sigepro.FrontEnd.AppLogic.GestioneEndoprocedimenti;
    using Ninject;
    using System;
    using System.Collections.Generic;
    using System.Linq;

    public partial class GestioneEndoV2 : IstanzeStepPage
    {
        private Lazy<EndoprocedimentiDellaDomandaOnline> _dataSource = null;

        [Inject]
        public EndoprocedimentiService EndoprocedimentiService { get; set; }
        [Inject]
        public IConfigurazione<ParametriVisura> _configVisura { get; set; }

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
            get { return ViewstateGet<bool>("MostraFamiglieEndo", true); }
            set { ViewStateSet("MostraFamiglieEndo", value); }
        }

        public bool ModificaProcedimentiProposti
        {
            get { return ViewstateGet<bool>("ModificaProcedimentiProposti", false); }
            set { ViewStateSet("ModificaProcedimentiProposti", value); }
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
            get { object o = ViewState["IgnoraIncompatibilitaEndoprocedimenti"]; return o == null ? true : (bool)o; }
            set { ViewState["IgnoraIncompatibilitaEndoprocedimenti"] = value; }
        }

        public string MessaggioErroreSelezionareAlmenoUnendo
        {
            get { object o = ViewState["MessaggioErroreSelezionareAlmenoUnendo"]; return o == null ? "Selezionare almeno un elemento della lista" : (string)o; }
            set { ViewState["MessaggioErroreSelezionareAlmenoUnendo"] = value; }
        }

        public bool SelezioneEsclusivaEndoAttivabili
        {
            get { object o = this.ViewState["SelezioneEsclusivaEndoAttivabili"]; return o == null ? false : (bool)o; }
            set { this.ViewState["SelezioneEsclusivaEndoAttivabili"] = value; }
        }


        #endregion


        public List<int> ListaIdSelezionati
        {
            get { return ViewstateGet<List<int>>("ListaIdSelezionati", null); }
            set { ViewStateSet("ListaIdSelezionati", value); }
        }

        public GestioneEndoV2()
        {
            _dataSource = new Lazy<EndoprocedimentiDellaDomandaOnline>(() =>
            {
                var codIntervento = ReadFacade.Domanda.AltriDati.Intervento.Codice;

                return EndoprocedimentiService.LeggiEndoprocedimentiDaCodiceIntervento(IdComune, codIntervento);
            });
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Il salvataggio dei dati viene effettuato dal service
            Master.IgnoraSalvataggioDati = true;

            grigliaEndoPrincipale.MostraFamiglia = grigliaEndoPrincipale.MostraTipoEndo = MostraFamiglieEndo;
            grigliaEndoAttivati.MostraFamiglia = grigliaEndoAttivati.MostraTipoEndo = MostraFamiglieEndo;
            grigliaEndoAttivabili.MostraFamiglia = grigliaEndoAttivabili.MostraTipoEndo = MostraFamiglieEndo;
            grigliaAltriEndo.MostraFamiglia = grigliaAltriEndo.MostraTipoEndo = MostraFamiglieEndo;
            grigliaAltriEndoAttivabili.MostraFamiglia = grigliaAltriEndoAttivabili.MostraTipoEndo = MostraFamiglieEndo;

            if (!IsPostBack)
            {
                DataBind();
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
            return _dataSource.Value.FamiglieEndoprocedimentiPrincipali.Count > 0 ||
                _dataSource.Value.FamiglieEndoprocedimentiFacoltativi.Count > 0 ||
                _dataSource.Value.FamiglieEndoprocedimentiAttivati.Count > 0 ||
                _dataSource.Value.FamiglieEndoprocedimentiAttivabili.Count > 0;
        }

        public override void OnBeforeExitStep()
        {



            // recupero gli id selezionati dall'utente
            SincronizzaListaIdSelezionati();

            EndoprocedimentiService.ImpostaEndoSelezionati(IdDomanda, ListaIdSelezionati);
        }

        public override bool CanExitStep()
        {
            if (SelezioneEsclusivaEndoAttivabili && !grigliaEndoAttivabili.GetCodiciFoglieSelezionate().Any())
            {
                Errori.Add(MessaggioErroreSelezionareAlmenoUnendo);

                return false;
            }

            var endoIncompatibili = EndoprocedimentiService.GetEndoprocedimentiIncompatibili(IdDomanda);

            if (endoIncompatibili.Count() == 0 || IgnoraIncompatibilitaEndoprocedimenti)
            {
                return true;
            }

            Errori.AddRange(endoIncompatibili.Select(x => x.ToString()));

            return false;
        }

        #endregion

        protected void cmdAttivaAltriEndo_click(object sender, EventArgs e)
        {
            SincronizzaListaIdSelezionati();

            multiView.ActiveViewIndex = 1;
            Master.MostraPaginatoreSteps = false;
        }

        protected void cmdTornaAllaLista_click(object sender, EventArgs e)
        {
            SincronizzaListaIdSelezionati();

            multiView.ActiveViewIndex = 0;
            Master.MostraPaginatoreSteps = true;

            DataBind();
        }

        private void SincronizzaListaIdSelezionati()
        {
            ListaIdSelezionati = grigliaEndoPrincipale.GetCodiciFoglieSelezionate()
                                                      .Union(grigliaEndoAttivati.GetCodiciFoglieSelezionate())
                                                      .Union(grigliaEndoAttivabili.GetCodiciFoglieSelezionate())
                                                      .Union(grigliaAltriEndoAttivabili.GetCodiciFoglieSelezionate())
                                                      .ToList();
        }

        public override void DataBind()
        {
            if (ListaIdSelezionati == null)
            {
                ListaIdSelezionati = ReadFacade.Domanda
                                                .Endoprocedimenti
                                                .Endoprocedimenti
                                                .Select(endo => endo.Codice)
                                                .ToList();
            }

            // Se non è stato ancora selezionato nessun endo aggiungo l'endo principale e gli endo attivati
            // Se non è possibile modificare la lista degli endo proposti 
            // aggiungo alla lista l'endo principale e gli endo attivati (se la lista non li contiene già)
            if (ListaIdSelezionati.Count == 0 || !ModificaProcedimentiProposti)
            {
                ListaIdSelezionati = ListaIdSelezionati.Union(_dataSource.Value.IdSelezionatiDefault).ToList();
            }

            PopolaGriglieDegliEndoDellIntervento();

            PopolaGrigliaEndoFacoltativiSelezionati();

            PopolaGrigliaAltriEndoAttivabili();

            // Se non esistono endo principali o attivabili ma esistono altri endo attivabili 
            // apro direttamente la view di visualizzazione degli altri endo
            if (grigliaEndoPrincipale.DataSource.Length == 0 &&
                grigliaEndoAttivati.DataSource.Length == 0 &&
                grigliaEndoAttivabili.DataSource.Length == 0 &&
                grigliaAltriEndoAttivabili.DataSource.Length > 0)
            {
                cmdAttivaAltriEndo_click(this, EventArgs.Empty);

                Master.MostraBottoneAvanti = true;
                Master.MostraPaginatoreSteps = true;

                cmdTornaAllaLista.Visible = false;
            }
        }

        private void PopolaGrigliaAltriEndoAttivabili()
        {
            grigliaAltriEndoAttivabili.ListaIdSelezionati = ListaIdSelezionati;
            grigliaAltriEndoAttivabili.DataSource = _dataSource.Value.FamiglieEndoprocedimentiFacoltativi.ToArray();
            grigliaAltriEndoAttivabili.DataBind();

            if (_dataSource.Value.FamiglieEndoprocedimentiFacoltativi.Count == 0)
            {
                pnlAltriEndo.Visible = cmdAttivaAltriEndo.Visible = false;
            }
        }

        private void PopolaGrigliaEndoFacoltativiSelezionati()
        {
            var famiglieEndoFacoltativiSelezionate = _dataSource.Value.GetEndoprocedimentiFacoltativiSelezionatiDaListaId(ListaIdSelezionati).ToArray();

            grigliaAltriEndo.ListaIdSelezionati = ListaIdSelezionati;
            grigliaAltriEndo.DataSource = famiglieEndoFacoltativiSelezionate;
            grigliaAltriEndo.DataBind();

            pnlAltriEndo.Visible = famiglieEndoFacoltativiSelezionate.Length > 0;
        }

        private void PopolaGriglieDegliEndoDellIntervento()
        {
            // é possibile modificare gli endo preselezionati tra gli endo attivati
            // o quelli principali?
            grigliaEndoPrincipale.ModificaProcedimentiProposti = ModificaProcedimentiProposti;
            grigliaEndoAttivati.ModificaProcedimentiProposti = ModificaProcedimentiProposti;

            // Se non esistono endoprocedimenti principali collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (_dataSource.Value.FamiglieEndoprocedimentiPrincipali.Count == 0)
            {
                pnlEndoPrincipale.Visible = false;
            }

            grigliaEndoPrincipale.ListaIdSelezionati = ListaIdSelezionati;
            grigliaEndoPrincipale.DataSource = _dataSource.Value.FamiglieEndoprocedimentiPrincipali.ToArray();
            grigliaEndoPrincipale.DataBind();

            // Se non esistono endoprocedimenti attivati collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (_dataSource.Value.FamiglieEndoprocedimentiAttivati.Count == 0)
            {
                pnlEndoAttivati.Visible = false;
            }

            grigliaEndoAttivati.ListaIdSelezionati = ListaIdSelezionati;
            grigliaEndoAttivati.DataSource = _dataSource.Value.FamiglieEndoprocedimentiAttivati.ToArray();
            grigliaEndoAttivati.DataBind();

            // Se non esistono endoprocedimenti attivabili collegati all'intervento selezionato
            // nascondo l'intero pannello
            if (_dataSource.Value.FamiglieEndoprocedimentiAttivabili.Count == 0)
            {
                pnlEndoAttivabili.Visible = false;
            }

            grigliaEndoAttivabili.ListaIdSelezionati = ListaIdSelezionati;
            grigliaEndoAttivabili.DataSource = _dataSource.Value.FamiglieEndoprocedimentiAttivabili.ToArray();
            grigliaEndoAttivabili.DataBind();
        }
    }
}
