using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneRisorseTestuali;
using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.Common;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Init.Sigepro.FrontEnd.WebControls.Visura.Controls;
using log4net;
using Ninject;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Web.UI;

namespace Init.Sigepro.FrontEnd.WebControls.Visura
{
    /// <summary>
    /// Descrizione di riepilogo per FiltriVisuraControl.
    /// </summary>
    [ToolboxData("<{0}:FiltriVisuraControl runat=server></{0}:FiltriVisuraControl>")]
    public abstract class FiltriVisuraControlBase : System.Web.UI.WebControls.WebControl, IDatabaseSoftwareControl
    {
        private readonly ILog m_logger = LogManager.GetLogger(typeof(FiltriVisuraControl));

        [Inject]
        public IConfigurazione<ParametriVisura> _configurazione { get; set; }

        [Inject]
        protected IRisorseTestualiService _risorseTestualiService { get; set; }

        // Filtri di ricerca
        protected TextBox _annoIstanza = new TextBox();
        protected VisuraMeseControl _meseIstanza = new VisuraMeseControl();
        protected TextBox _oggetto = new TextBox();
        protected TextBox _codiceIstanza = new TextBox();
        protected TextBox _civico = new TextBox();
        protected TextBox _numAutorizzazione = new TextBox();
        protected TextBox _numProtocollo = new TextBox();
        protected TextBox _fabbricato = new TextBox();
        protected TextBox _posizioneArchivio = new TextBox();
        protected ComuneLocalizzazioneControl _comuneLocalizzazione = new ComuneLocalizzazioneControl();
        protected Autocomplete _stradario = new Autocomplete();
        protected StatoIstanzaControl _statoIstanza = new StatoIstanzaControl();
        protected DateTextBox _dataProtocollo = new DateTextBox();
        protected DatiCatastaliControl _datiCatasto = new DatiCatastaliControl();
        //protected RichiedenteControl m_richiedente = new RichiedenteControl();
        protected TextBox _richiedente = new TextBox();
        protected Autocomplete _intervento = new Autocomplete();


        private readonly Dictionary<int, System.Web.UI.WebControls.WebControl> m_dictionary = new Dictionary<int, System.Web.UI.WebControls.WebControl>();

        //protected virtual int ID_CODICEISTANZA { get { return 41; } }
        //protected virtual int ID_ANNOISTANZA { get { return 43; } }
        //protected virtual int ID_MESEISTANZA { get { return 44; } }
        //protected virtual int ID_OGGETTO { get { return 48; } }
        //protected virtual int ID_CIVICO { get { return 62; } }
        //protected virtual int ID_NUMEROAUTORIZZAZIONE { get { return 66; } }
        //protected virtual int ID_NUMPROTOCOLLO { get { return 59; } }
        //protected virtual int ID_STRADARIO { get { return 46; } }
        //protected virtual int ID_STATOISTANZA { get { return 45; } }
        //protected virtual int ID_DATAPROTOCOLLO { get { return 60; } }
        //protected virtual int ID_DATICATASTO { get { return 70; } }
        //protected virtual int ID_RICHIEDENTE { get { return 47; } }
        //protected virtual int ID_INTERVENTO { get { return 42; } }

        private readonly IFiltriVisuraControlProvider m_provider;

        public string IdComune
        {
            get
            {
                object o = this.ViewState["IdComune"];
                return o == null ? "" : o.ToString();
            }
            set
            {
                this.EnsureChildControls();
                this.ViewState["IdComune"] = value;
            }
        }

        public string Software
        {
            get
            {
                object o = this.ViewState["Software"];
                return o == null ? "" : o.ToString();
            }
            set
            {
                this.ViewState["Software"] = value;
            }
        }

        public string CodiceComune
        {
            get
            {
                object o = this.ViewState["CodiceComune"];
                return o == null ? "" : o.ToString();
            }
            set { this.ViewState["CodiceComune"] = value; }
        }

        internal FiltriVisuraControlBase(IFiltriVisuraControlProvider provider)
        {
            FoKernelContainer.Inject(this);

            this.m_provider = provider;

            this.FillDictionary();

            this.Init += new EventHandler(this.FiltriVisuraControl_Init);
            this.Load += new EventHandler(this.FiltriVisuraControl_Load);
        }

        private void FillDictionary()
        {
            // annoIstanza
            this._annoIstanza.ID = "anno";
            this._annoIstanza.IdRisorsa = "visura.anno";
            this._annoIstanza.CssClass = "campo-input-anno";
            this._annoIstanza.BtSize = BootstrapSize.Col3;

            // meseIstanza
            this._meseIstanza.ID = "mese";
            this._meseIstanza.IdRisorsa = "visura.mese";
            this._meseIstanza.BtSize = BootstrapSize.Col3;

            // oggetto
            this._oggetto.ID = "oggetto";
            this._oggetto.IdRisorsa = "visura.oggetto";
            this._oggetto.MaxLength = 80;
            this._oggetto.BtSize = BootstrapSize.Col4;

            // civico
            this._civico.ID = "civico";
            this._civico.IdRisorsa = "visura.civico";
            this._civico.MaxLength = 8;
            this._civico.BtSize = BootstrapSize.Col3;

            // codiceIstanza
            this._codiceIstanza.ID = "codiceIstanza";
            this._codiceIstanza.IdRisorsa = "visura.codice_istanza";
            this._codiceIstanza.MaxLength = 15;
            this._codiceIstanza.BtSize = BootstrapSize.Col3;

            // numAutorizzazione
            this._numAutorizzazione.ID = "numAutorizzazione";
            this._numAutorizzazione.IdRisorsa = "visura.numero_autorizzazione";
            this._numAutorizzazione.MaxLength = 8;
            this._numAutorizzazione.BtSize = BootstrapSize.Col3;

            // numProtocollo
            this._numProtocollo.ID = "numProtocollo";
            this._numProtocollo.IdRisorsa = "visura.numero_protocollo";
            this._numProtocollo.MaxLength = 30;
            this._numProtocollo.BtSize = BootstrapSize.Col3;

            // stradario
            this._stradario.ID = "stradario";
            this._stradario.IdRisorsa = "visura.localizzazione";
            this._stradario.CssClass = "ricerca-stradario";
            this._stradario.BtSize = BootstrapSize.Col4;

            //comune della localizzazione
            this._comuneLocalizzazione.ID = "comuneLocalizzazione";
            this._comuneLocalizzazione.BtSize = BootstrapSize.Col3;
            this._comuneLocalizzazione.Inner.CssClass += " codice-comune";

            //fabbricato
            this._fabbricato.ID = "fabbricato";
            this._fabbricato.IdRisorsa = "visura.fabbricato";
            this._fabbricato.MaxLength = 10;
            this._fabbricato.BtSize = BootstrapSize.Col3;

            // statoIstanza
            this._statoIstanza.ID = "statoIstanza";
            this._statoIstanza.IdRisorsa = "visura.stati_istanze";
            this._statoIstanza.BtSize = BootstrapSize.Col3;

            // dataProtocollo
            this._dataProtocollo.ID = "dataProtocollo";
            this._dataProtocollo.IdRisorsa = "visura.data_protocollo";
            this._dataProtocollo.BtSize = BootstrapSize.Col3;

            // datiCatasto
            this._datiCatasto.ID = "datiCatasto";

            // richiedente
            this._richiedente.ID = "richiedente";
            this._richiedente.IdRisorsa = "visura.richiedente";
            this._richiedente.BtSize = BootstrapSize.Col3;

            // tipoIntervento
            this._intervento.ID = "tipoIntervento";
            this._intervento.IdRisorsa = "visura.tipo_intervento";
            this._intervento.CssClass = "ricerca-intervento";
            this._intervento.BtSize = BootstrapSize.Col12;

            // posizione archivio
            this._posizioneArchivio.ID = "posizioneArchivio";
            this._posizioneArchivio.MaxLength = 100;
            this._posizioneArchivio.IdRisorsa = "visura.posizione_in_archivio";
            this._posizioneArchivio.BtSize = BootstrapSize.Col3;

            this.m_dictionary.Add(this.m_provider.IdCodiceIstanza, this._codiceIstanza);
            this.m_dictionary.Add(this.m_provider.IdAnnoIstanza, this._annoIstanza);
            this.m_dictionary.Add(this.m_provider.IdMeseIstanza, this._meseIstanza);
            this.m_dictionary.Add(this.m_provider.IdOggetto, this._oggetto);
            this.m_dictionary.Add(this.m_provider.IdCivico, this._civico);
            this.m_dictionary.Add(this.m_provider.IdNumeroAutorizzazione, this._numAutorizzazione);
            this.m_dictionary.Add(this.m_provider.IdNumProtocollo, this._numProtocollo);
            this.m_dictionary.Add(this.m_provider.IdStradario, this._stradario);
            this.m_dictionary.Add(this.m_provider.IdStatoIstanza, this._statoIstanza);
            this.m_dictionary.Add(this.m_provider.IdDataProtocollo, this._dataProtocollo);
            this.m_dictionary.Add(this.m_provider.IdDatiCatasto, this._datiCatasto);
            this.m_dictionary.Add(this.m_provider.IdRichiedente, this._richiedente);
            this.m_dictionary.Add(this.m_provider.IdIntervento, this._intervento);
            this.m_dictionary.Add(this.m_provider.IdFabbricato, this._fabbricato);
            this.m_dictionary.Add(this.m_provider.IdPoszioneArchivio, this._posizioneArchivio);
        }


        private void FiltriVisuraControl_Init(object sender, EventArgs e)
        {
            this.EnsureChildControls();
            //FillDictionary();

        }

        public abstract RichiestaListaPraticheV3 GetRichiestaListaPratiche(AnagraficaUtente dettagliUtente);


        private void FiltriVisuraControl_Load(object sender, EventArgs e)
        {
            this.CostruisciControlli();
        }


        private void CostruisciControlli()
        {
            this.m_logger.Debug("Inizio creazione controlli di ricerca");

            this._comuneLocalizzazione.EnsureInitialized();

            var filtri = this.m_provider.GetCampiFiltro(this.IdComune, this.Software).Where(x => !String.IsNullOrEmpty(x.Valore) && x.Valore != "0").OrderBy(x => x.Valore).ThenBy(x => x.Etichetta).ToArray();

            this.Controls.Clear();

            for (int i = 0; i < filtri.Length; i++)
            {
                var campo = filtri[i];

                if (!this.m_dictionary.ContainsKey(campo.Codice))
                    Debug.WriteLine("Il dizionario non contiene l'id " + campo.Codice);
                else
                {
                    var bvc = (IControlWithLabel)this.m_dictionary[campo.Codice];
                    bvc.Label = this._risorseTestualiService.GetRisorsa(campo.IdRisorsa, campo.Etichetta);

                    // m_logger.DebugFormat("Creato controllo -> Idcomune: {0}, Software: {1}, Descrizione: {2}, Tipo: {3}, Id: {4}",
                    // 						bvc.IdComune, bvc.Software, bvc.Title, bvc.GetType(), campo.Codice);

                    if (bvc.ID == "stradario" && this._comuneLocalizzazione.ContieneComuniAssociati)
                    {
                        this._comuneLocalizzazione.Label = "Comune";
                        this.AddInnerControl(new[]{
                            this._comuneLocalizzazione,
                            bvc as Control
                        });
                    }
                    else
                    {
                        this.AddInnerControl(bvc as Control);
                    }
                }
            }

            this.m_logger.Debug("Fine creazione controlli di ricerca");
        }

        private void AddInnerControl(Control c)
        {
            var div = new System.Web.UI.WebControls.Panel();
            div.CssClass = "row";

            div.Controls.Add(c);

            this.Controls.Add(div);
        }

        private void AddInnerControl(IEnumerable<Control> ctrls)
        {
            var div = new System.Web.UI.WebControls.Panel();
            div.CssClass = "row";

            foreach (var c in ctrls)
                div.Controls.Add(c);

            this.Controls.Add(div);
        }

        public override void RenderBeginTag(HtmlTextWriter writer)
        {
            writer.RenderBeginTag(HtmlTextWriterTag.Fieldset);
        }


    }
}
