using Init.Sigepro.FrontEnd.AppLogic;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneVisuraIstanza;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using VBG.Shared.Infrastructure.Caching;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.Visura.Controls;
using Init.SIGePro.Manager.DTO.Visura.V2;
using Init.Utils.Web.UI;
using Ninject;
using System;
using System.Collections.Generic;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.WebControls.VisuraV2
{
    [ToolboxData("<{0}:FiltriVisuraV2ControlBase runat=server></{0}:FiltriVisuraV2ControlBase>")]
    public class FiltriVisuraV2Control : WebControl
    {
        private const string CODICE_ISTANZA = "CODICE_ISTANZA";//Codice Istanza               
        private const string DATA_ISTANZA = "DATA_ISTANZA";             //Data Istanza                 
        private const string NUMERO_PROTOCOLLO = "NUMERO_PROTOCOLLO";         //Numero protocollo            
        private const string DATA_PROTOCOLLO = "DATA_PROTOCOLLO";           //Data protocollo              
        private const string OGGETTO = "OGGETTO";                   //Oggetto dell'istanza         
        private const string CIVICO = "CIVICO";                   //Civico                       
        private const string NUMERO_AUTORIZZAZIONE = "NUMERO_AUTORIZZAZIONE";     //Numero autorizzazione        
        private const string INDIRIZZO = "INDIRIZZO";                 //Indirizzo                    
        private const string STATO_ISTANZA = "STATO_ISTANZA";             //Stato istanza                
        private const string DATI_CATASTALI = "DATI_CATASTALI";            //Dati catastali               
        private const string RICHIEDENTE = "RICHIEDENTE";               //Richiedente                  
        private const string INTERVENTO = "INTERVENTO";                //Intervento                   
        private const string RESPONSABILE_PROCEDIMENTO = "RESPONSABILE_PROCEDIMENTO"; //Responsabile del procedimento

        private const int LIMITE_RECORDS = 200;

        [Inject]
        public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }

        [Inject]
        public ISessionCache _sessionCache { get; set; }

        private readonly Dictionary<string, List<Control>> _dizionarioControlli = new Dictionary<string, List<Control>>();
        private readonly LabeledTextBox _txtCivico = new LabeledTextBox();
        private readonly LabeledTextBox _txtCodiceIstanza = new LabeledTextBox();
        private readonly LabeledDateTextBox _txtDataIstanzaDa = new LabeledDateTextBox();
        private readonly LabeledDateTextBox _txtDataIstanzaA = new LabeledDateTextBox();
        private readonly LabeledDateTextBox _txtDataProtocollo = new LabeledDateTextBox();
        private readonly LabeledDropDownList _ddlTipoCatasto = new LabeledDropDownList();
        private readonly LabeledTextBox _txtFoglio = new LabeledTextBox();
        private readonly LabeledTextBox _txtParticella = new LabeledTextBox();
        private readonly LabeledTextBox _txtSubalterno = new LabeledTextBox();
        private readonly LabeledTextBox _txtIndirizzo = new LabeledTextBox();
        private readonly AlberoProcControl _fndTipoIntervento = new AlberoProcControl();
        private readonly LabeledTextBox _txtNumeroAutorizzazione = new LabeledTextBox();
        private readonly LabeledTextBox _txtNumeroProtocollo = new LabeledTextBox();
        private readonly LabeledTextBox _txtOggetto = new LabeledTextBox();
        private readonly LabeledTextBox _txtResponsabileProcedimento = new LabeledTextBox();
        private readonly LabeledTextBox _txtRichiedente = new LabeledTextBox();
        private readonly LabeledDropDownList _ddlStatoIstanza = new LabeledDropDownList();

        #region properties
        public string ContestoVisura
        {
            get { object o = this.ViewState["ContestoVisura"]; return o == null ? "FiltriVisura" : o.ToString(); }
            set { this.ViewState["ContestoVisura"] = value; }
        }

        public string IdComune
        {
            get { object o = this.ViewState["IdComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["IdComune"] = value; }
        }

        public string Software
        {
            get { object o = this.ViewState["Software"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["Software"] = value; }
        }

        private const string SESSION_KEY = "FiltriVisuraV2ControlBase_DATASOURCE";
        private List<FoVisuraCampiDto> DataSource
        {
            get { return this._sessionCache.Get<List<FoVisuraCampiDto>>(SESSION_KEY); }
            set { this._sessionCache.Set(SESSION_KEY, value); }
        }

        #endregion


        public FiltriVisuraV2Control()
        {
            FoKernelContainer.Inject(this);

            this.InizializzaDictionary();
            this.InizializzaControlli();
        }

        private void InizializzaControlli()
        {
            //_txtCivico 
            this._txtCivico.Descrizione = "Civico";
            this._txtCivico.Item.Columns = 6;
            this._txtCivico.ID = "_txtCivico";

            //_txtCodiceIstanza 
            this._txtCodiceIstanza.Descrizione = "Numero istanza";
            this._txtCodiceIstanza.Item.Columns = 8;
            this._txtCodiceIstanza.ID = "_txtCodiceIstanza";

            //_txtDataIstanzaDa 
            this._txtDataIstanzaDa.Descrizione = "Data istanza (dalla data)";
            this._txtDataIstanzaDa.ID = "_txtDataIstanzaDa";
            this._txtDataIstanzaDa.Item.DateValue = DateTime.Now.AddMonths(-1);

            //_txtDataIstanzaA 
            this._txtDataIstanzaA.Descrizione = "Data istanza (alla data)";
            this._txtDataIstanzaA.ID = "_txtDataIstanzaA";
            this._txtDataIstanzaA.Item.DateValue = DateTime.Now;

            //_txtDataProtocollo 
            this._txtDataProtocollo.Descrizione = "Data protocollo";
            this._txtDataProtocollo.ID = "_txtDataProtocollo";

            //_ddlTipoCatasto 
            this._ddlTipoCatasto.Descrizione = "Tipo catasto";
            this._ddlTipoCatasto.Item.Items.Add(new ListItem { Value = "", Text = "Tutti" });
            this._ddlTipoCatasto.Item.Items.Add(new ListItem { Value = "F", Text = "Fabbricati" });
            this._ddlTipoCatasto.Item.Items.Add(new ListItem { Value = "T", Text = "Terreni" });
            this._ddlTipoCatasto.ID = "_ddlTipoCatasto";

            //_txtFoglio 
            this._txtFoglio.Descrizione = "Foglio";
            this._txtFoglio.Item.Columns = 6;
            this._txtFoglio.ID = "_txtFoglio";

            //_txtParticella 
            this._txtParticella.Descrizione = "Particella";
            this._txtParticella.Item.Columns = 6;
            this._txtParticella.ID = "_txtParticella";

            //_txtSubalterno 
            this._txtSubalterno.Descrizione = "Subalterno";
            this._txtSubalterno.Item.Columns = 6;
            this._txtSubalterno.ID = "_txtSubalterno";

            //_txtIndirizzo 
            this._txtIndirizzo.Descrizione = "Indirizzo";
            this._txtIndirizzo.Item.Columns = 40;
            this._txtIndirizzo.ID = "_txtIndirizzo";

            //_fndTipoIntervento 
            this._fndTipoIntervento.ID = "_fndTipoIntervento";

            //_txtNumeroAutorizzazione 
            this._txtNumeroAutorizzazione.Descrizione = "Numero autorizzazione";
            this._txtNumeroAutorizzazione.Item.Columns = 8;
            this._txtNumeroAutorizzazione.ID = "_txtNumeroAutorizzazione";

            //_txtNumeroProtocollo 
            this._txtNumeroProtocollo.Descrizione = "Numero protocollo";
            this._txtNumeroProtocollo.Item.Columns = 8;
            this._txtNumeroProtocollo.ID = "_txtNumeroProtocollo";

            //_txtOggetto 
            this._txtOggetto.Descrizione = "Oggetto";
            this._txtOggetto.Item.Columns = 40;
            this._txtOggetto.ID = "_txtOggetto";

            //_txtResponsabileProcedimento 
            this._txtResponsabileProcedimento.Descrizione = "Responsabile procedimento";
            this._txtResponsabileProcedimento.Item.Columns = 10;
            this._txtResponsabileProcedimento.ID = "_txtResponsabileProcedimento";

            //_txtRichiedente 
            this._txtRichiedente.Descrizione = "Codice fiscale richiedente";
            this._txtRichiedente.Item.Columns = 18;
            this._txtRichiedente.Item.MaxLength = 16;
            this._txtRichiedente.ID = "_txtRichiedente";

            //_ddlStatoIstanza 
            this._ddlStatoIstanza.Descrizione = "Stato istanza";
            this._ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "", Text = "Tutti" });
            this._ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "Attiva", Text = "Attiva" });
            this._ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "ChiusaPositivamente", Text = "Chiusa positivamente" });
            this._ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "ChiusaNegativamente", Text = "Chiusa negativamente" });
            this._ddlStatoIstanza.ID = "_ddlStatoIstanza";

        }

        private void InizializzaDictionary()
        {
            this._dizionarioControlli.Add(CIVICO, new List<Control>());
            this._dizionarioControlli.Add(CODICE_ISTANZA, new List<Control>());
            this._dizionarioControlli.Add(DATA_ISTANZA, new List<Control>());
            this._dizionarioControlli.Add(DATA_PROTOCOLLO, new List<Control>());
            this._dizionarioControlli.Add(DATI_CATASTALI, new List<Control>());
            this._dizionarioControlli.Add(INDIRIZZO, new List<Control>());
            this._dizionarioControlli.Add(INTERVENTO, new List<Control>());
            this._dizionarioControlli.Add(NUMERO_AUTORIZZAZIONE, new List<Control>());
            this._dizionarioControlli.Add(NUMERO_PROTOCOLLO, new List<Control>());
            this._dizionarioControlli.Add(OGGETTO, new List<Control>());
            this._dizionarioControlli.Add(RESPONSABILE_PROCEDIMENTO, new List<Control>());
            this._dizionarioControlli.Add(RICHIEDENTE, new List<Control>());
            this._dizionarioControlli.Add(STATO_ISTANZA, new List<Control>());

            this._dizionarioControlli[CIVICO].Add(this._txtCivico);
            this._dizionarioControlli[CODICE_ISTANZA].Add(this._txtCodiceIstanza);
            this._dizionarioControlli[DATA_ISTANZA].Add(this._txtDataIstanzaDa);
            this._dizionarioControlli[DATA_ISTANZA].Add(this._txtDataIstanzaA);
            this._dizionarioControlli[DATA_PROTOCOLLO].Add(this._txtDataProtocollo);
            this._dizionarioControlli[DATI_CATASTALI].Add(this._ddlTipoCatasto);
            this._dizionarioControlli[DATI_CATASTALI].Add(this._txtFoglio);
            this._dizionarioControlli[DATI_CATASTALI].Add(this._txtParticella);
            this._dizionarioControlli[DATI_CATASTALI].Add(this._txtSubalterno);
            this._dizionarioControlli[INDIRIZZO].Add(this._txtIndirizzo);
            this._dizionarioControlli[INTERVENTO].Add(this._fndTipoIntervento);
            this._dizionarioControlli[NUMERO_AUTORIZZAZIONE].Add(this._txtNumeroAutorizzazione);
            this._dizionarioControlli[NUMERO_PROTOCOLLO].Add(this._txtNumeroProtocollo);
            this._dizionarioControlli[OGGETTO].Add(this._txtOggetto);
            this._dizionarioControlli[RESPONSABILE_PROCEDIMENTO].Add(this._txtResponsabileProcedimento);
            this._dizionarioControlli[RICHIEDENTE].Add(this._txtRichiedente);
            this._dizionarioControlli[STATO_ISTANZA].Add(this._ddlStatoIstanza);
        }

        public override void DataBind()
        {
            var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), this.ContestoVisura, true);

            this.DataSource = this._istanzePresentateRepository.GetFiltri(this.IdComune, this.Software, contesto);

            this.RenderControlli();
        }

        public RichiestaPraticheListaRequest GetRichiestaLista(AnagraficaUtente dettagliUtente)
        {
            var rVal = new RichiestaPraticheListaRequest
            {
                sportelloDestinatario = new SportelloType(),
                sportelloMittente = new SportelloType(),
                filtriPratica = new FiltriPraticaType
                {
                    codiceFiscaleRichiedente = this._txtRichiedente.Value,
                    codiceIntervento = this._fndTipoIntervento.Value,
                    dataPresentazionePraticaDa = this._txtDataIstanzaDa.Item.DateValue.GetValueOrDefault(DateTime.MinValue),
                    dataPresentazionePraticaDaSpecified = this._txtDataIstanzaDa.Item.DateValue.HasValue,
                    dataPresentazionePraticaA = this._txtDataIstanzaA.Item.DateValue.GetValueOrDefault(DateTime.MaxValue),
                    dataPresentazionePraticaASpecified = this._txtDataIstanzaA.Item.DateValue.HasValue,
                    dataProtocolloGeneraleDa = this._txtDataProtocollo.Item.DateValue.GetValueOrDefault(DateTime.MinValue),
                    dataProtocolloGeneraleDaSpecified = this._txtDataProtocollo.Item.DateValue.HasValue,
                    dataProtocolloGeneraleA = this._txtDataProtocollo.Item.DateValue.GetValueOrDefault(DateTime.MaxValue),
                    dataProtocolloGeneraleASpecified = this._txtDataProtocollo.Item.DateValue.HasValue,
                    //idPratica = _txtCodiceIstanza.Value,
                    limiteRecords = LIMITE_RECORDS,
                    limiteRecordsSpecified = true,
                    localizzazioneCivico = this._txtCivico.Value,
                    localizzazioneIndirizzo = this._txtIndirizzo.Value,
                    numeroAtto = this._txtNumeroAutorizzazione.Value,
                    numeroPratica = this._txtCodiceIstanza.Value,
                    numeroProtocolloGenerale = this._txtNumeroProtocollo.Value,
                    oggetto = this._txtOggetto.Value,
                    rifCatastaliFoglio = this._txtFoglio.Value,
                    rifCatastaliParticella = this._txtParticella.Value,
                    rifCatastaliSub = this._txtSubalterno.Value,
                    rifCatastaliTipoCatasto = this._ddlTipoCatasto.Value == "T" ? FiltriPraticaTypeRifCatastaliTipoCatasto.Terreni : FiltriPraticaTypeRifCatastaliTipoCatasto.Fabbricati,
                    rifCatastaliTipoCatastoSpecified = !String.IsNullOrEmpty(this._ddlTipoCatasto.Value),
                    statoPratica = this.DecodeStatoPratica(),
                    statoPraticaSpecified = this.StatoPraticaSpecified()

                },
            };

            // Parametri di ricerca utente
            var utenteTecnico = dettagliUtente.Tipologia.GetValueOrDefault(0) != 0;

            var parametriConfigurazione = this._configurazione.Parametri;
            var parametriRicercaUtente = utenteTecnico ? parametriConfigurazione.RicercaTecnico : parametriConfigurazione.RicercaNonTecnico;

            rVal.filtriUtenteConnesso = new FiltriUtenteType
            {
                cercaComeAziendaRichiedente = parametriRicercaUtente.CercaComeAzienda,
                cercaComeAziendaRichiedenteSpecified = true,
                cercaComeIntermediario = parametriRicercaUtente.CercaComeTecnico,
                cercaComeIntermediarioSpecified = true,
                cercaComeRichiedente = parametriRicercaUtente.CercaComeRichiedente,
                cercaComeRichiedenteSpecified = true,
                cercaNeiSoggettiCollegati = parametriRicercaUtente.CercaSoggettiCollegati,
                cercaNeiSoggettiCollegatiSpecified = true,
                codiceFiscale = dettagliUtente.Codicefiscale
            };

            return rVal;
        }

        private bool StatoPraticaSpecified()
        {
            return !String.IsNullOrEmpty(this._ddlStatoIstanza.Value);
        }

        private StatoPraticaType DecodeStatoPratica()
        {
            if (!this.StatoPraticaSpecified())
                return StatoPraticaType.Attiva;

            try
            {
                return (StatoPraticaType)Enum.Parse(typeof(StatoPraticaType), this._ddlStatoIstanza.Value);
            }
            catch (Exception)
            {
                return StatoPraticaType.Attiva;
            }
        }

        private void RenderControlli()
        {
            this.Controls.Clear();

            foreach (var filtro in this.DataSource)
            {
                var items = this._dizionarioControlli[filtro.Fkidcampo];

                for (int i = 0; i < items.Count; i++)
                {
                    var divElement = new HtmlGenericControl("div");
                    divElement.Controls.Add(items[i]);
                    this.Controls.Add(divElement);
                }
            }
        }

        protected override void LoadViewState(object savedState)
        {
            this.RenderControlli();

            base.LoadViewState(savedState);
        }

    }
}
