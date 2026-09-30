using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestioneConversioneFiles;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.LogicaRisoluzioneSoggetti;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.InvioDomanda.MessaggiErroreInvio;
using Init.Sigepro.FrontEnd.AppLogic.VerificaFirmaDigitale;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti.PostedFileSpecifications;
using Ninject;
using System;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class RiepilogoDomandaPec : IstanzeStepPage
    {
        private const int ID_VIEW_PREINVIO = 0;
        private const int ID_VIEW_DETTAGLI = 1;
        private const int ID_VIEW_ERRORI = 2;

        [Inject]
        public IComuniService _comuniService { get; set; }
        [Inject]
        public AllegatiInterventoService _allegatiInterventoService { get; set; }

        [Inject]
        public InvioDomandaAreaRiservataService _trasferimentoDomandaService { get; set; }

        [Inject]
        public IMessaggioErroreInvioService _messaggioErroreService { get; set; }

        [Inject]
        public ValidPostedFileSpecification _validPostedFileSpecification { get; set; }

        [Inject]
        public ILogicaRisoluzioneTecnico _logicaRisoluzioneTecnico { get; set; }

        #region Parametri letti dal file xml
        public string TestoIntestazioneInvio
        {
            get { return this.ltrIntestazioneInvio.Text; }
            set { this.ltrIntestazioneInvio.Text = value; }
        }

        public string TestoDownloadDomanda
        {
            get { return this.lblTestoDownloadDomanda.Text; }
            set { this.lblTestoDownloadDomanda.Text = value; }
        }

        public string TestoUploadDomanda
        {
            get { return this.lblTestoUploadDomanda.Text; }
            set { this.lblTestoUploadDomanda.Text = value; }
        }

        public string TestoSelezioneIndirizzoPEC
        {
            get { return this.lblSelezioneIndirizzoPEC.Text; }
            set { this.lblSelezioneIndirizzoPEC.Text = value; }
        }

        public string TestoIndirizzoSportelloComune
        {
            get { return this.lblIndirizzoSportelloComune.Text; }
            set { this.lblIndirizzoSportelloComune.Text = value; }
        }

        public string TestoBottoneInvio
        {
            get { return this.lblTestoBottoneInvio.Text; }
            set { this.lblTestoBottoneInvio.Text = value; }
        }

        public string TestoInvioEffettuato
        {
            get { return this.ltrInvioeffettuato.Text; }
            set { this.ltrInvioeffettuato.Text = value; }
        }
        #endregion




        protected void Page_Load(object sender, EventArgs e)
        {
            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void DataBind()
        {
            this.multiView.ActiveViewIndex = ID_VIEW_PREINVIO;

            // Preparo il link per il download della domanda
            var rigaRiepilogo = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            this.hlModelloDomanda.NavigateUrl = this._urlService.GetUrlDownloadCompilato(rigaRiepilogo.CodiceOggettoModello.Value, this.IdDomanda, FormatoConversioneEnum.PDF);

            // Popolo la combo con gli indirizzi pec dei richiedenti
            this.ddlIndirizzoPec.Items.Clear();

            var listaRichiedenti = this.ReadFacade.Domanda.Anagrafiche.GetRichiedenti();
            var tecnico = this.ReadFacade.Domanda.Anagrafiche.GetTecnico(this._logicaRisoluzioneTecnico);

            var nomeFormatString = "{0} ({1}): {2}";

            foreach (var row in listaRichiedenti)
            {
                if (!String.IsNullOrEmpty(row.Contatti.Pec))
                {
                    var li = new ListItem(String.Format(nomeFormatString, row.ToString(), row.TipoSoggetto.ToString(), row.Contatti.Pec), row.Contatti.Pec);

                    this.ddlIndirizzoPec.Items.Add(li);
                }
            }

            if (tecnico != null)
            {
                if (!String.IsNullOrEmpty(tecnico.Contatti.Pec))
                {
                    var li = new ListItem(String.Format(nomeFormatString, tecnico.ToString(), tecnico.TipoSoggetto.ToString(), tecnico.Contatti.Pec), tecnico.Contatti.Pec);

                    this.ddlIndirizzoPec.Items.Add(li);
                }
            }

            // Sostituisco il segnaposto dell'indirizzo PEC del comune
            const string SEGNAPOSTO_PEC_COMUNE = "{INDIRIZZO_PEC_SPORTELLO}";
            var pecSportello = this._comuniService.GetPecComuneAssociato(this.Software, this.ReadFacade.Domanda.AltriDati.CodiceComune);

            if (!String.IsNullOrEmpty(pecSportello))
                pecSportello = pecSportello.ToUpper();

            this.lblIndirizzoSportelloComune.Text = this.lblIndirizzoSportelloComune.Text.Replace(SEGNAPOSTO_PEC_COMUNE, pecSportello);
            this.ltrEmailDestinatario.Text = pecSportello;
            this.ltrIntestazioneInvio.Text = this.ltrIntestazioneInvio.Text.Replace(SEGNAPOSTO_PEC_COMUNE, pecSportello);
        }

        protected void cmdUploadDomanda_Click(Object sender, EventArgs e)
        {
            var row = this.ReadFacade.Domanda.Documenti.Intervento.GetRiepilogoDomanda();

            try
            {
                var file = new WebFormsBinaryFile(this.fuRiepilogo, this._validPostedFileSpecification);

                try
                {
                    this._allegatiInterventoService.Salva(this.IdDomanda, row.Id, file);
                }
                catch (FirmaDigitaleNonValidaException ex)
                {
                    this.Errori.Add(ex.Message);

                    return;
                }

                this.InviaDomanda();
            }
            catch (Exception ex)
            {
                this.Errori.Add(ex.Message);
            }
        }

        /// <summary>
        /// Effettua la trasmisisone della domanda al backoffice
        /// </summary>
        private void InviaDomanda()
        {
            var esito = this._trasferimentoDomandaService.Invia(this.IdDomanda, this.ddlIndirizzoPec.SelectedValue);

            this.Master.MostraPaginatoreSteps = false;

            // Esito della domanda positivo, 
            if (esito.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscito || esito.Esito == InvioIstanzaResult.TipoEsitoInvio.InvioRiuscitoNoBackend)
            {
                this.MostraViewInvioEffettuato(esito);
            }
            else
            {
                this.MostraViewErrori(esito);
            }

        }

        private void MostraViewErrori(InvioIstanzaResult esito)
        {
            this.multiView.ActiveViewIndex = ID_VIEW_ERRORI;
            this.lblErroreInvio.Text = this._messaggioErroreService.GeneraMessaggioErrore(this.IdDomanda);
        }

        private void MostraViewInvioEffettuato(InvioIstanzaResult esito)
        {
            this.Title = "Invio effettuato con successo";
            this.multiView.ActiveViewIndex = ID_VIEW_DETTAGLI;
        }
    }
}
