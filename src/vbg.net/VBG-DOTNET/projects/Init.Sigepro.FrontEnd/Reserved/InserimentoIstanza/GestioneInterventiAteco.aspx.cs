using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Init.SIGePro.Manager.DTO.Interventi;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneInterventiAteco : IstanzeStepPage
    {
        public static class Constants
        {

            public const string MessaggioCollegamentoAtecoNonTrovatoDefault = "Non sono stati individuati interventi riconducibili all'attività ATECO selezionata. Verrà mostrata la lista completa degli interventi attivabili online";
            public const int IdVistaPresentazione = 0;
            public const int IdVistaAteco = 1;
            public const int IdVistaInterventi = 2;
            public const int IdVistaDettagli = 3;
            public const int IdVistaErroreAutenticazione = 4;
            public const string MessaggioPulsanteModificaInterventoDefault = "Cambia l’intervento selezionato";
        }

        //[Inject]
        //public DatiDomandaService DatiDomandaService { get; set; }

        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        [Inject]
        public IAnagraficheService _anagrafeService { get; set; }

        #region Parametri letti dalla configurazione dello step
        public string TestoIntroduzione
        {
            get { return this.ltrTestoIntroduzione.Text; }
            set { this.ltrTestoIntroduzione.Text = value; }
        }

        //public string IntestazioneRicercaAteco
        //{
        //    get { return this.ltrIntestazioneRicercaAteco.Text; }
        //    set { this.ltrIntestazioneRicercaAteco.Text = value; }
        //}

        //public string TestoRicercaAteco
        //{
        //    get { return this.ltrTestoRicercaAteco.Text; }
        //    set { this.ltrTestoRicercaAteco.Text = value; }
        //}

        public string IntestazioneRicercaIntervento
        {
            get { var o = this.ViewState["IntestazioneRicercaIntervento"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["IntestazioneRicercaIntervento"] = value; }
        }

        public string TestoRicercaIntervento
        {
            get { return this.ltrTestoRicercaIntervento.Text; }
            set { this.ltrTestoRicercaIntervento.Text = value; }
        }

        public string IntestazioneDettaglio
        {
            get { return this.ltrIntestazioneDettaglio.Text; }
            set { this.ltrIntestazioneDettaglio.Text = value; }
        }

        public string TestoDettaglio
        {
            get { return this.ltrTestoDettaglio.Text; }
            set { this.ltrTestoDettaglio.Text = value; }
        }

        public string TestoPulsanteModificaIntervento
        {
            get { return this.cmdModificaIntervento.Text; }
            set { this.cmdModificaIntervento.Text = value; }
        }

        public string MessaggioCollegamentoAtecoNonTrovato
        {
            get
            {
                var o = this.ViewState["MessaggioCollegamentoAtecoNonTrovato"];
                return o == null ? Constants.MessaggioCollegamentoAtecoNonTrovatoDefault : (string)o;
            }
            set { this.ViewState["MessaggioCollegamentoAtecoNonTrovato"] = value; }
        }


        public bool MostraAlberoAteco
        {
            get { var o = this.ViewState["MostraAlberoAteco"]; return o == null ? false : (bool)o; }
            set { this.ViewState["MostraAlberoAteco"] = value; }
        }

        //public string TestoBottoneSelezioneIntervento
        //{
        //	get { return cmdSelezionaIntervento.Text; }
        //	set { cmdSelezionaIntervento.Text = value; }
        //}

        public string TestoErroreLivelloAutenticazione
        {
            get { return this.ltrErroreAutenticazione.Text; }
            set { this.ltrErroreAutenticazione.Text = value; }
        }

        public bool PopolaDescrizioneLavoriDaIntervento
        {
            get { var o = this.ViewState["PopolaDescrizioneLavoriDaIntervento"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PopolaDescrizioneLavoriDaIntervento"] = value; }
        }

        [StepProperty("Se impostato a true mostra le informazioni aggiuntive sugli interventi (icona con punto interrogativo). Default: true")]
        public bool MostraInformazioniIntervento
        {
            get
            {
                var o = this.ViewState["MostraInformazioniIntervento"]; return o == null || (bool)o;
            }
            set { this.ViewState["MostraInformazioniIntervento"] = value; }
        }

        #endregion

        public int IdInterventoSelezionato
        {
            get { var o = this.Session["IdInterventoSelezionato"]; return o == null ? -1 : (int)o; }
            set { this.Session["IdInterventoSelezionato"] = value; this.SelezioneInterventoAvvenuta = true; }
        }

        public int IdAttivitaAtecoSelezionata
        {
            get { var o = this.Session["IdAttivitaAtecoSelezionata"]; return o == null ? -1 : (int)o; }
            set { this.Session["IdAttivitaAtecoSelezionata"] = value; }
        }

        /// <summary>
        /// Flag che identifica se l'intervento è stato selezionato dall'utente
        /// </summary>
        public bool SelezioneInterventoAvvenuta
        {
            get { var o = this.ViewState["SelezioneInterventoAvvenuta"]; return o == null ? false : (bool)o; }
            set { this.ViewState["SelezioneInterventoAvvenuta"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il salvataggio viene effettuato dal service
            this.Master.IgnoraSalvataggioDati = true;
            this.alberoInterventi.CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;

            if (!this.IsPostBack)
            {
                if (this.ReadFacade.Domanda.AltriDati.Intervento == null)
                {
                    // TODO: mettere in configurazione un parametro per decidere se aprire la pagina
                    // dall'albero ateco o dalla lista interventi
                    this.MostraFormSelezione();
                }
                else
                {
                    this.BindDettaglio();
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
            if (this.ReadFacade.Domanda.AltriDati.Intervento != null)
                this.IdInterventoSelezionato = this.ReadFacade.Domanda.AltriDati.Intervento.Codice;

            return true;
        }


        public override bool CanExitStep()
        {
            if (this.ReadFacade.Domanda.AltriDati.Intervento == null)
            {
                this.Errori.Add("Selezionare un tipo di intervento");
                return false;
            }

            return this.VerificaPermessiInterventoSelezionato();
        }
        #endregion


        protected void multiView_activeViewChanged(object sender, EventArgs e)
        {
            switch (this.multiView.ActiveViewIndex)
            {
                case Constants.IdVistaPresentazione:
                    this.cmdAnnullaRicerca.Visible = this.SelezioneInterventoAvvenuta;
                    break;
                case Constants.IdVistaAteco:
                    break;
                case Constants.IdVistaInterventi:
                    this.cmdAnnullaAlbero.Visible = this.MostraAlberoAteco || this.SelezioneInterventoAvvenuta;
                    break;
            }
        }

        private void MostraFormSelezione()
        {
            if (this.MostraAlberoAteco)
            {
                this.MostraIntroduzione();
            }
            else
            {
                this.BindAlberoInterventi(this, -1);

                this.cmdAnnullaAlbero.Visible = false;
            }
        }

        protected void cmdSelezioneDirettaIntervento_Click(object sender, EventArgs e)
        {
            this.BindAlberoInterventi(this, -1);
        }





        private void MostraIntroduzione()
        {
            this.multiView.ActiveViewIndex = Constants.IdVistaPresentazione;
            this.Master.MostraBottoneAvanti = false;
        }


        //private void BindAlberoAteco()
        //{
        //    multiView.ActiveViewIndex = Constants.IdVistaAteco;
        //    Master.MostraBottoneAvanti = false;
        //}


        private void BindDettaglio()
        {
            this.VerificaPermessiInterventoSelezionato();

            this.multiView.ActiveViewIndex = Constants.IdVistaDettagli;

            this.SelezioneInterventoAvvenuta = true;

            var albero = this.ReadFacade.Interventi.GetAlberaturaNodoDaId(this.IdComune, this.IdInterventoSelezionato);

            var falsoRoot = new NodoAlberoInterventiDto();

            falsoRoot.NodiFiglio = new List<ClassTree<InterventoDto>>();
            falsoRoot.NodiFiglio.Add(albero);

            this.treeRendererDettaglio.DataSource = falsoRoot;
            this.treeRendererDettaglio.DataBind();

            this.Master.MostraBottoneAvanti = true;
        }

        private bool VerificaPermessiInterventoSelezionato()
        {
            var codiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;
            var esitoVerifica = this.ReadFacade.Interventi.VerificaAccessoIntervento(this.IdInterventoSelezionato, codiceComune);

            if (esitoVerifica.Is(TipoAccessibilitaIntervento.InterventoNonAttivo) || esitoVerifica.Is(TipoAccessibilitaIntervento.NonPubblicato))
            {
                this.Errori.Add($"{esitoVerifica.MessaggioErrore}. Selezionare un nuovo intervento.");

                this.MostraFormSelezione();

                return false;
            }

            if (esitoVerifica.Is(TipoAccessibilitaIntervento.LivelloAutenticazioneNonSufficiente))
            {
                this.MostraFormErroreAutenticazione();

                return false;
            }

            return true;
        }

        private void MostraFormErroreAutenticazione()
        {
            this.multiView.ActiveViewIndex = Constants.IdVistaErroreAutenticazione;

            var autenticazioneServizio = this.ReadFacade.Interventi.GetNomeLivelloAutenticazionePerInterventi(this.IdInterventoSelezionato);
            var autenticazioneUtente = DecodeLivelloIntervento.FromLivelloAutenticazione(this.UserAuthenticationResult.LivelloAutenticazione);

            this.ltrErroreAutenticazione.Text = String.Format(this.modelloErroreAutenticazione.Text, autenticazioneServizio, autenticazioneUtente);

            this.Master.MostraBottoneAvanti = false;

        }



        protected void BindAlberoInterventi(object sender, int idAteco)
        {
            this.alberoInterventi.UtenteTester = this.UserAuthenticationResult.DatiUtente.UtenteTester;
            this.alberoInterventi.MostraInformazioniIntervento = this.MostraInformazioniIntervento;

            this.IdAttivitaAtecoSelezionata = -1;

            if (idAteco > 0)    // è stata selezionata una foglia dell'albero ateco
            {
                //this.IdAttivitaAtecoSelezionata = idAteco;

                //if (!this.ReadFacade.Ateco.EsistonoInterventiCollegati(this.IdComune, this.Software, idAteco, new AmbitoRicercaAreaRiservata(false)))
                //    this.Errori.Add(this.MessaggioCollegamentoAtecoNonTrovato);
            }

            this.alberoInterventi.IdAteco = idAteco;
            this.multiView.ActiveViewIndex = Constants.IdVistaInterventi;

            this.Master.MostraBottoneAvanti = false;
        }



        protected void InterventoSelezionato(object sender, int idNodo)
        {
            this.IdInterventoSelezionato = idNodo;

            // BindDettaglio();

            this.DatiDomandaService.ImpostaIdIntervento(this.IdDomanda, idNodo, this.IdAttivitaAtecoSelezionata == -1 ? (int?)null : this.IdAttivitaAtecoSelezionata, this.PopolaDescrizioneLavoriDaIntervento);

            // this.Master.RebindPaginatore();

            if (this.VerificaPermessiInterventoSelezionato())
            {
                this.Master.cmdNextStep_Click(this, EventArgs.Empty);
            }
            else
            {
                this.BindDettaglio();
            }
        }

        public void cmdSelezionaIntervento_Click(object sender, EventArgs e)
        {
            //BindAlberoInterventi(this,alberoInterventi.IdAteco);
            this.MostraFormSelezione();
        }

        //protected void cmdRicercaAteco_Click(object sender, EventArgs e)
        //{
        //    this.BindAlberoAteco();
        //}


        protected void cmdRicercaIntervento_Click(object sender, EventArgs e)
        {
            this.BindAlberoInterventi(this, -1);
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
            if (this.ReadFacade.Domanda.Endoprocedimenti.Endoprocedimenti.Count() > 0)
                return true;

            if (this.ReadFacade.Domanda.Documenti.Count() > 0)
                return true;

            if (this.ReadFacade.Domanda.DatiDinamici.Modelli.Count() > 0)
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
            if (this.SelezioneInterventoAvvenuta)
            {
                // Se l'annulla proviene dalla view che permette di scegliere tra classificazione ateco o amministrativa
                if (sender == this.cmdAnnullaRicerca)
                {
                    // Torno al dettaglio dell'intervento selezionato in precedenza
                    this.BindDettaglio();
                    return;
                }
                else // se l'annulla è stato fatto dalla ricerca ateco o intervento
                {
                    // l'albero ateco va visualizzato?
                    if (this.MostraAlberoAteco)
                    {
                        // Se si mostro il form che permette di scegliere tra classificazione ateco o amministrativa
                        this.MostraFormSelezione();
                        return;
                    }
                    else
                    {
                        // Altrimenti mostro il dettaglio dell'intervento selezionato in precedenza
                        this.BindDettaglio();
                        return;
                    }
                }
            }
            else // Non è ancora stato selezionato un intervento
            {
                // l'albero ateco va visualizzato?
                if (this.MostraAlberoAteco)
                {
                    // Se si mostro il form che permette di scegliere tra classificazione ateco o amministrativa
                    this.MostraFormSelezione();
                    return;
                }
            }
        }

        protected void cmdCambiaIntervento_Click(object sender, EventArgs e)
        {
            this.MostraFormSelezione();
        }
    }
}
