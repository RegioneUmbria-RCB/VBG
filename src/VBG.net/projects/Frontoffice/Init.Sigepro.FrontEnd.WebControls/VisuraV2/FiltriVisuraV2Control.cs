using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.AppLogic;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.StcService;
using Init.Sigepro.FrontEnd.WebControls.Visura.Controls;
using Init.Utils.Web.UI;
using Ninject;

namespace Init.Sigepro.FrontEnd.WebControls.VisuraV2
{
	[ToolboxData("<{0}:FiltriVisuraV2ControlBase runat=server></{0}:FiltriVisuraV2ControlBase>")]
	public class FiltriVisuraV2Control : WebControl
	{
		const string CODICE_ISTANZA = "CODICE_ISTANZA";//Codice Istanza               
		const string DATA_ISTANZA = "DATA_ISTANZA";             //Data Istanza                 
		const string NUMERO_PROTOCOLLO = "NUMERO_PROTOCOLLO";         //Numero protocollo            
		const string DATA_PROTOCOLLO = "DATA_PROTOCOLLO";           //Data protocollo              
		const string OGGETTO = "OGGETTO";                   //Oggetto dell'istanza         
		const string CIVICO = "CIVICO";                   //Civico                       
		const string NUMERO_AUTORIZZAZIONE = "NUMERO_AUTORIZZAZIONE";     //Numero autorizzazione        
		const string INDIRIZZO = "INDIRIZZO";                 //Indirizzo                    
		const string STATO_ISTANZA = "STATO_ISTANZA";             //Stato istanza                
		const string DATI_CATASTALI = "DATI_CATASTALI";            //Dati catastali               
		const string RICHIEDENTE = "RICHIEDENTE";               //Richiedente                  
		const string INTERVENTO = "INTERVENTO";                //Intervento                   
		const string RESPONSABILE_PROCEDIMENTO = "RESPONSABILE_PROCEDIMENTO"; //Responsabile del procedimento

		const int LIMITE_RECORDS = 200;

		[Inject]
		public IIstanzePresentateRepository _istanzePresentateRepository { get; set; }

		[Inject]
		public IConfigurazione<ParametriVisura> _configurazione { get; set; }


		Dictionary<string, List<Control>> _dizionarioControlli = new Dictionary<string, List<Control>>();

		LabeledTextBox _txtCivico = new LabeledTextBox();
		LabeledTextBox _txtCodiceIstanza = new LabeledTextBox();
		LabeledDateTextBox _txtDataIstanzaDa = new LabeledDateTextBox();
		LabeledDateTextBox _txtDataIstanzaA = new LabeledDateTextBox();
		LabeledDateTextBox _txtDataProtocollo = new LabeledDateTextBox();
		LabeledDropDownList _ddlTipoCatasto = new LabeledDropDownList();
		LabeledTextBox _txtFoglio = new LabeledTextBox();
		LabeledTextBox _txtParticella = new LabeledTextBox();
		LabeledTextBox _txtSubalterno = new LabeledTextBox();
		LabeledTextBox _txtIndirizzo = new LabeledTextBox();
		AlberoProcControl _fndTipoIntervento = new AlberoProcControl();
		LabeledTextBox _txtNumeroAutorizzazione = new LabeledTextBox();
		LabeledTextBox _txtNumeroProtocollo = new LabeledTextBox()		;
		LabeledTextBox _txtOggetto = new LabeledTextBox();
		LabeledTextBox _txtResponsabileProcedimento = new LabeledTextBox();
		LabeledTextBox _txtRichiedente = new LabeledTextBox();
		LabeledDropDownList _ddlStatoIstanza = new LabeledDropDownList();

		#region properties
		public string ContestoVisura
		{
			get { object o = this.ViewState["ContestoVisura"]; return o == null ? "FiltriVisura": o.ToString(); }
			set { this.ViewState["ContestoVisura"] = value; }
		}

		public string IdComune
		{
			get { object o = this.ViewState["IdComune"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["IdComune"] = value;  }
		}

		public string Software
		{
			get { object o = this.ViewState["Software"]; return o == null ? String.Empty : (string)o; }
			set { this.ViewState["Software"] = value; }
		}

		const string SESSION_KEY = "FiltriVisuraV2ControlBase_DATASOURCE";
		private List<FoVisuraCampi> DataSource
		{
			get { return (List<FoVisuraCampi>)HttpContext.Current.Session[SESSION_KEY]; }
			set { HttpContext.Current.Session[SESSION_KEY] = value; }
		}

		#endregion


		public FiltriVisuraV2Control()
		{
			FoKernelContainer.Inject(this);

			InizializzaDictionary();
			InizializzaControlli();
		}

		private void InizializzaControlli()
		{
			//_txtCivico 
			_txtCivico.Descrizione = "Civico";
			_txtCivico.Item.Columns = 6;
			_txtCivico.ID = "_txtCivico";

			//_txtCodiceIstanza 
			_txtCodiceIstanza.Descrizione = "Numero istanza";
			_txtCodiceIstanza.Item.Columns = 8;
			_txtCodiceIstanza.ID = "_txtCodiceIstanza";

			//_txtDataIstanzaDa 
			_txtDataIstanzaDa.Descrizione = "Data istanza (dalla data)";
			_txtDataIstanzaDa.ID = "_txtDataIstanzaDa";
			_txtDataIstanzaDa.Item.DateValue = DateTime.Now.AddMonths(-1);

			//_txtDataIstanzaA 
			_txtDataIstanzaA.Descrizione = "Data istanza (alla data)";
			_txtDataIstanzaA.ID = "_txtDataIstanzaA";
			_txtDataIstanzaA.Item.DateValue = DateTime.Now;

			//_txtDataProtocollo 
			_txtDataProtocollo.Descrizione = "Data protocollo";
			_txtDataProtocollo.ID = "_txtDataProtocollo";

			//_ddlTipoCatasto 
			_ddlTipoCatasto.Descrizione = "Tipo catasto";
			_ddlTipoCatasto.Item.Items.Add( new ListItem{ Value = "" , Text = "Tutti"} );
			_ddlTipoCatasto.Item.Items.Add( new ListItem{ Value = "F" , Text = "Fabbricati"} );
			_ddlTipoCatasto.Item.Items.Add( new ListItem{ Value = "T" , Text = "Terreni"} );
			_ddlTipoCatasto.ID = "_ddlTipoCatasto";

			//_txtFoglio 
			_txtFoglio.Descrizione = "Foglio";
			_txtFoglio.Item.Columns = 6;
			_txtFoglio.ID = "_txtFoglio";

			//_txtParticella 
			_txtParticella.Descrizione = "Particella";
			_txtParticella.Item.Columns = 6;
			_txtParticella.ID = "_txtParticella";

			//_txtSubalterno 
			_txtSubalterno.Descrizione = "Subalterno";
			_txtSubalterno.Item.Columns = 6;
			_txtSubalterno.ID = "_txtSubalterno";

			//_txtIndirizzo 
			_txtIndirizzo.Descrizione = "Indirizzo";
			_txtIndirizzo.Item.Columns = 40;
			_txtIndirizzo.ID = "_txtIndirizzo";

			//_fndTipoIntervento 
			_fndTipoIntervento.ID = "_fndTipoIntervento";

			//_txtNumeroAutorizzazione 
			_txtNumeroAutorizzazione.Descrizione = "Numero autorizzazione";
			_txtNumeroAutorizzazione.Item.Columns = 8;
			_txtNumeroAutorizzazione.ID = "_txtNumeroAutorizzazione";

			//_txtNumeroProtocollo 
			_txtNumeroProtocollo.Descrizione = "Numero protocollo";
			_txtNumeroProtocollo.Item.Columns = 8;
			_txtNumeroProtocollo.ID = "_txtNumeroProtocollo";

			//_txtOggetto 
			_txtOggetto.Descrizione = "Oggetto";
			_txtOggetto.Item.Columns = 40;
			_txtOggetto.ID = "_txtOggetto";

			//_txtResponsabileProcedimento 
			_txtResponsabileProcedimento.Descrizione = "Responsabile procedimento";
			_txtResponsabileProcedimento.Item.Columns = 10;
			_txtResponsabileProcedimento.ID = "_txtResponsabileProcedimento";

			//_txtRichiedente 
			_txtRichiedente.Descrizione = "Codice fiscale richiedente";
			_txtRichiedente.Item.Columns = 18;
			_txtRichiedente.Item.MaxLength = 16;
			_txtRichiedente.ID = "_txtRichiedente";

			//_ddlStatoIstanza 
			_ddlStatoIstanza.Descrizione = "Stato istanza";
			_ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "", Text = "Tutti" });
			_ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "Attiva", Text = "Attiva" });
			_ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "ChiusaPositivamente", Text = "Chiusa positivamente" });
			_ddlStatoIstanza.Item.Items.Add(new ListItem { Value = "ChiusaNegativamente", Text = "Chiusa negativamente" });
			_ddlStatoIstanza.ID = "_ddlStatoIstanza";

		}

		private void InizializzaDictionary()
		{
			_dizionarioControlli.Add(CIVICO, new List<Control>());
			_dizionarioControlli.Add(CODICE_ISTANZA, new List<Control>());
			_dizionarioControlli.Add(DATA_ISTANZA, new List<Control>());
			_dizionarioControlli.Add(DATA_PROTOCOLLO, new List<Control>());
			_dizionarioControlli.Add(DATI_CATASTALI, new List<Control>());
			_dizionarioControlli.Add(INDIRIZZO, new List<Control>());
			_dizionarioControlli.Add(INTERVENTO, new List<Control>());
			_dizionarioControlli.Add(NUMERO_AUTORIZZAZIONE, new List<Control>());
			_dizionarioControlli.Add(NUMERO_PROTOCOLLO, new List<Control>());
			_dizionarioControlli.Add(OGGETTO, new List<Control>());
			_dizionarioControlli.Add(RESPONSABILE_PROCEDIMENTO, new List<Control>());
			_dizionarioControlli.Add(RICHIEDENTE, new List<Control>());
			_dizionarioControlli.Add(STATO_ISTANZA, new List<Control>());

			_dizionarioControlli[CIVICO].Add(_txtCivico);
			_dizionarioControlli[CODICE_ISTANZA].Add(_txtCodiceIstanza);
			_dizionarioControlli[DATA_ISTANZA].Add(_txtDataIstanzaDa);
			_dizionarioControlli[DATA_ISTANZA].Add(_txtDataIstanzaA);
			_dizionarioControlli[DATA_PROTOCOLLO].Add(_txtDataProtocollo);
			_dizionarioControlli[DATI_CATASTALI].Add(_ddlTipoCatasto);
			_dizionarioControlli[DATI_CATASTALI].Add(_txtFoglio);
			_dizionarioControlli[DATI_CATASTALI].Add(_txtParticella);
			_dizionarioControlli[DATI_CATASTALI].Add(_txtSubalterno);
			_dizionarioControlli[INDIRIZZO].Add(_txtIndirizzo);
			_dizionarioControlli[INTERVENTO].Add(_fndTipoIntervento);
			_dizionarioControlli[NUMERO_AUTORIZZAZIONE].Add(_txtNumeroAutorizzazione);
			_dizionarioControlli[NUMERO_PROTOCOLLO].Add(_txtNumeroProtocollo);
			_dizionarioControlli[OGGETTO].Add(_txtOggetto);
			_dizionarioControlli[RESPONSABILE_PROCEDIMENTO].Add(_txtResponsabileProcedimento);
			_dizionarioControlli[RICHIEDENTE].Add(_txtRichiedente);
			_dizionarioControlli[STATO_ISTANZA].Add(_ddlStatoIstanza);
		}

		public override void DataBind()
		{
			var contesto = (TipoContestoVisuraEnum)Enum.Parse(typeof(TipoContestoVisuraEnum), ContestoVisura, true);

			DataSource = _istanzePresentateRepository.GetFiltri(IdComune, Software, contesto);

			RenderControlli();
		}

		public RichiestaPraticheListaRequest GetRichiestaLista(AnagraficaUtente dettagliUtente)
		{
			var rVal = new RichiestaPraticheListaRequest
			{
				sportelloDestinatario = new SportelloType(),
				sportelloMittente = new SportelloType(),	
				filtriPratica = new FiltriPraticaType
				{
					codiceFiscaleRichiedente = _txtRichiedente.Value,
					codiceIntervento = _fndTipoIntervento.Value,
					dataPresentazionePraticaDa = _txtDataIstanzaDa.Item.DateValue.GetValueOrDefault( DateTime.MinValue ),
					dataPresentazionePraticaDaSpecified = _txtDataIstanzaDa.Item.DateValue.HasValue,
					dataPresentazionePraticaA = _txtDataIstanzaA.Item.DateValue.GetValueOrDefault( DateTime.MaxValue ),
					dataPresentazionePraticaASpecified = _txtDataIstanzaA.Item.DateValue.HasValue,
					dataProtocolloGeneraleDa = _txtDataProtocollo.Item.DateValue.GetValueOrDefault( DateTime.MinValue ),
					dataProtocolloGeneraleDaSpecified = _txtDataProtocollo.Item.DateValue.HasValue,
					dataProtocolloGeneraleA = _txtDataProtocollo.Item.DateValue.GetValueOrDefault(DateTime.MaxValue),
					dataProtocolloGeneraleASpecified = _txtDataProtocollo.Item.DateValue.HasValue,
					//idPratica = _txtCodiceIstanza.Value,
					limiteRecords = LIMITE_RECORDS,
					limiteRecordsSpecified = true,
					localizzazioneCivico = _txtCivico.Value,
					localizzazioneIndirizzo = _txtIndirizzo.Value,
					numeroAtto = _txtNumeroAutorizzazione.Value,
					numeroPratica = _txtCodiceIstanza.Value,
					numeroProtocolloGenerale = _txtNumeroProtocollo.Value,
					oggetto = _txtOggetto.Value,
					rifCatastaliFoglio = _txtFoglio.Value,
					rifCatastaliParticella = _txtParticella.Value,
					rifCatastaliSub = _txtSubalterno.Value,
					rifCatastaliTipoCatasto = _ddlTipoCatasto.Value == "T" ? FiltriPraticaTypeRifCatastaliTipoCatasto.Terreni: FiltriPraticaTypeRifCatastaliTipoCatasto.Fabbricati,
					rifCatastaliTipoCatastoSpecified = !String.IsNullOrEmpty(_ddlTipoCatasto.Value),
					statoPratica = DecodeStatoPratica(  ),
					statoPraticaSpecified = StatoPraticaSpecified()
					
				},
			};

			// Parametri di ricerca utente
			var utenteTecnico = dettagliUtente.Tipologia.GetValueOrDefault(0) != 0;

			var parametriConfigurazione = _configurazione.Parametri;
			var parametriRicercaUtente = utenteTecnico ? parametriConfigurazione.RicercaTecnico : parametriConfigurazione.RicercaNonTecnico;

			rVal.filtriUtenteConnesso = new FiltriUtenteType{
				cercaComeAziendaRichiedente = parametriRicercaUtente.CercaComeAzienda,
				cercaComeAziendaRichiedenteSpecified= true,
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
			return !String.IsNullOrEmpty(_ddlStatoIstanza.Value);
		}

		private StatoPraticaType DecodeStatoPratica()
		{
			if (!StatoPraticaSpecified())
				return StatoPraticaType.Attiva;

			try
			{
				return (StatoPraticaType)Enum.Parse(typeof(StatoPraticaType), _ddlStatoIstanza.Value);
			}
			catch (Exception)
			{
				return StatoPraticaType.Attiva;
			}
		}

		private void RenderControlli()
		{
			this.Controls.Clear();

			foreach (var filtro in DataSource)
			{
				var items = _dizionarioControlli[filtro.Fkidcampo];

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
			RenderControlli();

			base.LoadViewState(savedState);
		}

	}
}
