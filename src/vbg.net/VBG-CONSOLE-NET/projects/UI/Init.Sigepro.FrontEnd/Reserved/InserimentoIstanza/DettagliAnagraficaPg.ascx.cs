using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using log4net;
using Ninject;
using System;
using System.ComponentModel;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DettagliAnagraficaPg : DettagliAnagraficaControl
    {
        [Inject]
        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(DettagliAnagraficaPg));

        public bool SedeLegaleObbligatoria
        {
            get { return this.GetVs("SedeLegaleObbligatoria", false); }
            set { this.SetVs("SedeLegaleObbligatoria", value); }
        }

        public bool SedeLegaleVisibile
        {
            get { return this.GetVs("SedeLegaleVisibile", true); }
            set { this.SetVs("SedeLegaleVisibile", value); }
        }

        public bool DataCostituzioneObbligatoria
        {
            get { return this.GetVs("DataCostituzioneObbligatoria", false); }
            set { this.SetVs("DataCostituzioneObbligatoria", value); }
        }
        public bool DataCostituzioneVisibile
        {
            get { return this.GetVs("DataCostituzioneVisibile", true); }
            set { this.SetVs("DataCostituzioneVisibile", value); }
        }

        public bool TelefonoObbligatorio
        {
            get { return this.GetVs("TelefonoObbligatoria", false); }
            set { this.SetVs("TelefonoObbligatoria", value); }
        }
        public bool TelefonoVisibile
        {
            get { return this.GetVs("TelefonoVisibile", true); }
            set { this.SetVs("TelefonoVisibile", value); }
        }

        public bool CellulareObbligatorio
        {
            get { return this.GetVs("CellulareObbligatoria", false); }
            set { this.SetVs("CellulareObbligatoria", value); }
        }
        public bool CellulareVisibile
        {
            get { return this.GetVs("CellulareVisibile", true); }
            set { this.SetVs("CellulareVisibile", value); }
        }

        public bool EmailObbligatoria
        {
            get { return this.GetVs("EmailObbligatorio", false); }
            set { this.SetVs("EmailObbligatorio", value); }
        }
        public bool EmailVisibile
        {
            get { return this.GetVs("EmailVisibile", true); }
            set { this.SetVs("EmailVisibile", value); }
        }

        public bool PecObbligatoria
        {
            get { return this.GetVs("PecObbligatorio", false); }
            set { this.SetVs("PecObbligatorio", value); }
        }

        public bool PecVisibile
        {
            get { return this.GetVs("PecVisibile", true); }
            set { this.SetVs("PecVisibile", value); }
        }

        public bool CciaaObbligatoria
        {
            get { return this.GetVs("CciaaObbligatoria", false); }
            set { this.SetVs("CciaaObbligatoria", value); }
        }

        public bool CciaaVisibile
        {
            get { return this.GetVs("CciaaVisibile", true); }
            set { this.SetVs("CciaaVisibile", value); }
        }

        public bool RegTribObbligatorio
        {
            get { return this.GetVs("RegTribObbligatoria", false); }
            set { this.SetVs("RegTribObbligatoria", value); }
        }

        public bool RegTribVisibile
        {
            get { return this.GetVs("RegTribVisibile", true); }
            set { this.SetVs("RegTribVisibile", value); }
        }

        public bool ReaObbligatoria
        {
            get { return this.GetVs("ReaObbligatoria", false); }
            set { this.SetVs("ReaObbligatoria", value); }
        }

        public bool ReaVisibile
        {
            get { return this.GetVs("ReaVisibile", true); }
            set { this.SetVs("ReaVisibile", value); }
        }

        public bool InpsObbligatoria
        {
            get { return this.GetVs("InpsObbligatoria", false); }
            set { this.SetVs("InpsObbligatoria", value); }
        }

        public bool InpsVisibile
        {
            get { return this.GetVs("InpsVisibile", true); }
            set { this.SetVs("InpsVisibile", value); }
        }

        public bool InailObbligatoria
        {
            get { return this.GetVs("InailObbligatoria", false); }
            set { this.SetVs("InailObbligatoria", value); }
        }

        public bool InailVisibile
        {
            get { return this.GetVs("InailVisibile", true); }
            set { this.SetVs("InailVisibile", value); }
        }

        public bool PartitaIvaVisibile
        {
            get { return this.GetVs("PartitaIvaVisibile", true); }
            set { this.SetVs("PartitaIvaVisibile", value); }
        }

        public bool PartitaIvaObbligatoria
        {
            get { return this.GetVs("PartitaIvaObbligatoria", false); }
            set { this.SetVs("PartitaIvaObbligatoria", value); }
        }

        public bool CorrispondenzaVisibile
        {
            set { this.SetVs("CorrispondenzaVisibile", value); }
            get { return this.GetVs("CorrispondenzaVisibile", true); }
        }

        public bool CorrispondenzaObbligatoria
        {
            set { this.SetVs("CorrispondenzaObbligatoria", value); }
            get { return this.GetVs("CorrispondenzaObbligatoria", false); }
        }


        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }


        public delegate DatiComuneCompatto OnGetDatiComune(string codiceComune);
        public delegate DatiProvinciaCompatto OnGetDatiProvincia(string siglaProvincia);
        public delegate AnagraficaDomanda OnGetAnagrafeRow(int idAnagrafica);
        public delegate void OnAcceptEdit(AnagraficaDomanda row);


        public event EventHandler CancelEdit;
        public event OnAcceptEdit AcceptEdit;
        public event OnGetAnagrafeRow GetAnagrafeRow;
        public event OnGetDatiComune GetDatiComune;
        public event OnGetDatiProvincia GetDatiProvincia;

        public event DatiAnagrafici.ErrorDelegate ErroreInserimento;

        /// <summary>
        /// Fonte dati del controllo
        /// </summary>
        [Bindable(BindableSupport.Yes)]
        public virtual PresentazioneIstanzaDbV2.ANAGRAFERow DataSource { get; set; }

        protected string IdComune
        {
            get { return this._aliasSoftwareResolver.AliasComune; }
        }

        protected string Software
        {
            get { return this._aliasSoftwareResolver.Software; }
        }

        public int? CodiceIntervento
        {
            set { this.TipoSoggetto.CodiceIntervento = value; }
        }

        public bool PermettiModificaDatiAnagrafici
        {
            get { object o = this.ViewState["PermettiModificaDatiAnagrafici"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiModificaDatiAnagrafici"] = value; }
        }




        public DettagliAnagraficaPg()
        {
            FoKernelContainer.Inject(this);
        }

        protected override void OnLoad(EventArgs e)
        {
            if (!this.Page.IsPostBack)
            {
                this.BindFormeGiuridiche();
            }
        }

        private void BindFormeGiuridiche()
        {
            var formeGiuridiche = this._formeGiuridicheRepository.GetList(this.IdComune);

            this.CodiceFormaGiuridica.Items.Clear();
            this.CodiceFormaGiuridica.Items.Add(new ListItem("Selezionare...", String.Empty));

            foreach (var fg in formeGiuridiche)
            {
                this.CodiceFormaGiuridica.Items.Add(new ListItem(fg.FORMAGIURIDICA, fg.CODICEFORMAGIURIDICA));
            }
        }



        public override void DataBind()
        {
            this.Nominativo.Text = AnagraficaBinder.SafeValue(this.DataSource, "NOMINATIVO");
            this.Indirizzo.Text = AnagraficaBinder.SafeValue(this.DataSource, "INDIRIZZO");
            this.Citta.Text = AnagraficaBinder.SafeValue(this.DataSource, "CITTA");
            this.Cap.Text = AnagraficaBinder.SafeValue(this.DataSource, "CAP");
            this.email.Text = AnagraficaBinder.SafeValue(this.DataSource, "EMAIL");
            this.emailPec.Text = AnagraficaBinder.SafeValue(this.DataSource, "Pec");

            this.CodiceFiscale.Text = this.DataSource.CODICEFISCALE;
            this.PartitaIva.Text = this.DataSource.PartitaIva;

            /*
            if (String.IsNullOrEmpty(PartitaIva.Text) && Regex.IsMatch(CodiceFiscale.Text, "^\\d{11}$"))
            {
                PartitaIva.Text = CodiceFiscale.Text;
                CodiceFiscale.Text = string.Empty;
            }
            */


            this.RegDitte.Text = AnagraficaBinder.SafeValue(this.DataSource, "REGDITTE");
            this.RegTrib.Text = AnagraficaBinder.SafeValue(this.DataSource, "REGTRIB");
            this.NumIscrREA.Text = AnagraficaBinder.SafeValue(this.DataSource, "NUMISCRREA");
            this.IndirizzoCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "INDIRIZZOCORRISPONDENZA");
            this.CittaCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "CITTACORRISPONDENZA");
            this.CapCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "CAPCORRISPONDENZA");
            this.Telefono.Text = AnagraficaBinder.SafeValue(this.DataSource, "TELEFONO");
            this.TelefonoCellulare.Text = AnagraficaBinder.SafeValue(this.DataSource, "TELEFONOCELLULARE");

            string comuneSedeLegale = AnagraficaBinder.SafeValue(this.DataSource, "COMUNERESIDENZA");

            this.acComuneSedeLegale.Value = "";
            this.acComuneSedeLegale.Text = "";

            if (!String.IsNullOrEmpty(comuneSedeLegale))
            {
                var comuneSLDto = GetDatiComune(comuneSedeLegale);

                if (comuneSLDto != null)
                {
                    this.acComuneSedeLegale.Value = comuneSLDto.CodiceComune;
                    this.acComuneSedeLegale.Text = comuneSLDto.Comune + " (" + comuneSLDto.SiglaProvincia + ")";
                }
            }

            string comuneCorrispondenza = AnagraficaBinder.SafeValue(this.DataSource, "COMUNECORRISPONDENZA");

            /*AnagraficaBinder.BindComboComune(IdComune, provinciaCorrispondenza, comuneCorrispondenza,
                                    lblProvinciaCorrispondenza, lblComuneCorrispondenza,
                                    cddProvinciaCorrispondenza, cddComuneCorrispondenza,
                                    ddlProvinciaCorrispondenza, ddlComuneCorrispondenza,
                                    null, null );*/

            this.acComuneCorrispondenza.Value = String.Empty;
            this.acComuneCorrispondenza.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneCorrispondenza))
            {
                var comuneCorrDto = GetDatiComune(comuneCorrispondenza);

                if (comuneCorrDto != null)
                {
                    this.acComuneCorrispondenza.Value = comuneCorrDto.CodiceComune;
                    this.acComuneCorrispondenza.Text = comuneCorrDto.Comune + " (" + comuneCorrDto.SiglaProvincia + ")";
                }
            }



            this.DataNominativo.DateValue = (this.DataSource.IsDATANOMINATIVONull()) ? (DateTime?)null : this.DataSource.DATANOMINATIVO;
            this.DataRegDitte.DateValue = (this.DataSource.IsDATAREGDITTENull()) ? (DateTime?)null : this.DataSource.DATAREGDITTE;
            this.RegTribData.DateValue = (this.DataSource.IsDATAREGTRIBNull()) ? (DateTime?)null : this.DataSource.DATAREGTRIB;

            var comuneRegDitte = AnagraficaBinder.SafeValue(this.DataSource, "CODCOMREGDITTE");

            this.acProvinciaCCIAA.Value = String.Empty;
            this.acProvinciaCCIAA.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneRegDitte))
            {
                var comuneCciaaDto = GetDatiComune(comuneRegDitte);

                if (comuneCciaaDto != null)
                {
                    this.acProvinciaCCIAA.Value = comuneCciaaDto.CodiceComune;
                    this.acProvinciaCCIAA.Text = comuneCciaaDto.Comune + " (" + comuneCciaaDto.SiglaProvincia + ")";
                }
            }



            string comuneRegTrib = AnagraficaBinder.SafeValue(this.DataSource, "CODCOMREGTRIB");

            this.acComuneRegTrib.Value = this.acComuneRegTrib.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneRegTrib))
            {
                var comuneRegTribDto = GetDatiComune(comuneRegTrib);

                if (comuneRegTribDto != null)
                {
                    this.acComuneRegTrib.Value = comuneRegTribDto.CodiceComune;
                    this.acComuneRegTrib.Text = comuneRegTribDto.Comune + " (" + comuneRegTribDto.SiglaProvincia + ")";
                }
            }




            string provinciaRea = AnagraficaBinder.SafeValue(this.DataSource, "PROVINCIAREA");

            this.acProvinciaREA.Value = this.acProvinciaREA.Text = String.Empty;

            if (!String.IsNullOrEmpty(provinciaRea))
            {
                var provinciaReaDto = GetDatiProvincia(provinciaRea);

                if (provinciaReaDto != null)
                {
                    this.acProvinciaREA.Value = provinciaReaDto.SiglaProvincia;
                    this.acProvinciaREA.Text = provinciaReaDto.Provincia;
                }

            }

            //TipoSoggetto.CodiceIntervento = this.CodiceIntervento;
            this.TipoSoggetto.DataBind();
            this.TipoSoggetto.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "TIPOSOGGETTO");

            if (this.TipoSoggetto.SelectedValue == String.Empty && this.TipoSoggetto.Items.Count == 2)
            {
                this.TipoSoggetto.SelectedValue = this.TipoSoggetto.Items[1].Value;
            }

            this.txtDescrizioneEstesa.Text = AnagraficaBinder.SafeValue(this.DataSource, "DescrizioneTipoSoggetto");


            this.CodiceFormaGiuridica.DataBind();
            this.CodiceFormaGiuridica.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "FORMAGIURIDICA");
            //CodiceFormaGiuridica.Enabled = AnagraficaBinder.EvalEnabled( DataSource ,"FORMAGIURIDICA");

            this.ANAGRAFE_PK.Value = AnagraficaBinder.SafeValue(this.DataSource, "ANAGRAFE_PK");

            this.CodiceFiscale.ReadOnly = false;
            this.PartitaIva.ReadOnly = false;

            if (!this.PermettiModificaDatiAnagrafici)
            {
                if (!String.IsNullOrEmpty(this.CodiceFiscale.Text))
                    this.CodiceFiscale.ReadOnly = true;

                if (!String.IsNullOrEmpty(this.PartitaIva.Text))
                    this.PartitaIva.ReadOnly = true;
            }


            // Dati INPS
            this.txtNumeroInps.Text = this.DataSource.MatricolaInps;
            this.acSedeINPS.Value = this.DataSource.CodSedeIscrizioneInps;
            this.acSedeINPS.Text = this.DataSource.DesSedeIscrizioneInps;

            // Dati INAIL
            this.txtNumeroINAIL.Text = this.DataSource.MatricolaInail;
            this.acSedeINAIL.Value = this.DataSource.CodSedeIscrizioneInail;
            this.acSedeINAIL.Text = this.DataSource.DesSedeIscrizioneInail;

            this.Page.Validate();

            base.DataBind();
        }

        protected void cmdCancel_Click(object sender, EventArgs e)
        {
            if (CancelEdit != null)
                CancelEdit(this, EventArgs.Empty);
        }

        protected void cmdConfirm_Click(object sender, EventArgs e)
        {
            try
            {
                this.Page.Validate();

                if (!this.Page.IsValid)
                    return;

                int rowId = -1;

                int.TryParse(this.ANAGRAFE_PK.Value, out rowId);

                this.ControllaCampo(this.CodiceFiscale.Text, true, "E'obbligatorio specificare un codice fiscale oppure una partita IVA");

                var row = GetAnagrafeRow(rowId).ToAnagrafeRow();

                row.TIPOANAGRAFE = "G";

                if (!String.IsNullOrEmpty(this.TipoSoggetto.SelectedValue))
                {
                    row.TIPOSOGGETTO = Convert.ToInt32(this.TipoSoggetto.SelectedValue);
                }

                row.DescrSoggetto = this.TipoSoggetto.SelectedItem.Text;
                row.DescrizioneTipoSoggetto = this.txtDescrizioneEstesa.Text;

                row.NOMINATIVO = this.Nominativo.Text;
                row.CODICEFISCALE = this.CodiceFiscale.Text;
                row.PartitaIva = this.PartitaIva.Text;

                if (!String.IsNullOrEmpty(this.CodiceFormaGiuridica.SelectedValue))
                    row.FORMAGIURIDICA = Convert.ToInt32(this.CodiceFormaGiuridica.SelectedValue);

                row.DescrSoggetto = this.TipoSoggetto.SelectedItem.Text;

                if (!EmailValidator.ValidaEmail(this.email.Text, fallisciSeVuota: false))
                {
                    ErroreInserimento($"Il campo \"{this.email.Label}\" contiene un indirizzo non valido");
                    return;
                }

                if (!EmailValidator.ValidaEmail(this.emailPec.Text, fallisciSeVuota: false))
                {
                    ErroreInserimento($"Il campo \"{this.emailPec.Label}\" contiene un indirizzo non valido");
                    return;
                }


                row.INDIRIZZO = this.Indirizzo.Text;
                row.CITTA = this.Citta.Text;
                row.CAP = this.Cap.Text;
                row.INDIRIZZOCORRISPONDENZA = this.IndirizzoCorrispondenza.Text;
                row.CITTACORRISPONDENZA = this.CittaCorrispondenza.Text;
                row.CAPCORRISPONDENZA = this.CapCorrispondenza.Text;
                row.TELEFONO = this.Telefono.Text;
                row.TELEFONOCELLULARE = this.TelefonoCellulare.Text;
                row.EMAIL = this.email.Text;
                row.Pec = this.emailPec.Text;
                row.REGDITTE = this.RegDitte.Text;
                row.REGTRIB = this.RegTrib.Text;
                row.NUMISCRREA = this.NumIscrREA.Text;
                row.PROVINCIAREA = this.acProvinciaREA.Value;
                row.DATANOMINATIVO = this.DataNominativo.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAREGDITTE = this.DataRegDitte.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAREGTRIB = this.RegTribData.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAISCRREA = this.DataIscrREA.DateValue.GetValueOrDefault(DateTime.MinValue);

                this.ControllaCampo(this.PartitaIva.Text, this.PartitaIvaObbligatoria, "Specificare una partita IVA");
                this.ControllaCampo(this.DataNominativo.Text, this.DataCostituzioneObbligatoria, "Specificare la data di costituzione");

                this.ControllaCampo(this.acComuneSedeLegale.Value, this.SedeLegaleObbligatoria, "Specificare il comune della sede legale");
                this.ControllaCampo(this.Indirizzo.Text, this.SedeLegaleObbligatoria, "Specificare l'indirizzo della sede legale");
                this.ControllaCampo(this.Cap.Text, this.SedeLegaleObbligatoria, "Specificare il CAP");

                this.ControllaCampo(this.RegDitte.Text, this.CciaaObbligatoria, "Specificare il numero di iscrizione alla camera di commercio");
                this.ControllaCampo(this.DataRegDitte.Text, this.CciaaObbligatoria, "Specificare la data di iscrizione alla camera di commercio");
                this.ControllaCampo(this.acProvinciaCCIAA.Value, this.CciaaObbligatoria, "Specificare la provincia di iscrizione alla camera di commercio");

                this.ControllaCampo(this.RegTrib.Text, this.RegTribObbligatorio, "Specificare il numero di iscrizione al Reg.Trib.");
                this.ControllaCampo(this.RegTribData.Text, this.RegTribObbligatorio, "Specificare la data di iscrizione al Reg.Trib.");
                this.ControllaCampo(this.acComuneRegTrib.Value, this.RegTribObbligatorio, "Specificare il comune di iscrizione al Reg.Trib.");

                this.ControllaCampo(this.NumIscrREA.Text, this.ReaObbligatoria, "Specificare il numero di iscrizione al REA");
                this.ControllaCampo(this.DataIscrREA.Text, this.ReaObbligatoria, "Specificare la data di iscrizione al REA");
                this.ControllaCampo(this.acProvinciaREA.Value, this.ReaObbligatoria, "Specificare la provincia di iscrizione al REA");

                this.ControllaCampo(this.txtNumeroInps.Text, this.InpsObbligatoria, "Specificare la matricola INPS");
                this.ControllaCampo(this.acSedeINPS.Value, this.InpsObbligatoria, "Specificare la sede di iscrizione INPS");

                this.ControllaCampo(this.txtNumeroINAIL.Text, this.InailObbligatoria, "Specificare la matricola INAIL");
                this.ControllaCampo(this.acSedeINAIL.Value, this.InailObbligatoria, "Specificare la sede di iscrizione INAIL");

                this.ControllaCampo(this.Telefono.Text, this.TelefonoObbligatorio, "Specificare il numero di telefono");
                this.ControllaCampo(this.TelefonoCellulare.Text, this.CellulareObbligatorio, "Specificare il numero di cellulare");
                this.ControllaCampo(this.email.Text, this.EmailObbligatoria, "Specificare un indirizzo e-mail");
                this.ControllaCampo(this.emailPec.Text, this.PecObbligatoria, "Specificare un indirizzo PEC");


                // Validazione comuni e provincie
                row.CODPROVREGDITTE = row.CODCOMREGDITTE = String.Empty;

                this.ValidaComune(this.acProvinciaCCIAA, "La provincia CCIAA non è valida", c =>
                {
                    row.CODPROVREGDITTE = c.SiglaProvincia;
                    row.CODCOMREGDITTE = c.CodiceComune;
                });


                row.CODPROVREGTRIB = row.CODCOMREGTRIB = String.Empty;

                this.ValidaComune(this.acComuneRegTrib, "Il comune Reg. Trib. non è valido", c =>
                {
                    row.CODPROVREGTRIB = c.SiglaProvincia;
                    row.CODCOMREGTRIB = c.CodiceComune;
                });


                row.PROVINCIA = row.COMUNERESIDENZA = String.Empty;

                this.ValidaComune(this.acComuneSedeLegale, "Il comune della sede legale non è valido", c =>
                {
                    row.PROVINCIA = c.SiglaProvincia;
                    row.COMUNERESIDENZA = c.CodiceComune;
                });


                row.PROVINCIACORRISPONDENZA = row.COMUNECORRISPONDENZA;

                this.ValidaComune(this.acComuneCorrispondenza, "Il comune per la corrispondenza non è valido", c =>
                {
                    row.PROVINCIACORRISPONDENZA = c.SiglaProvincia;
                    row.COMUNECORRISPONDENZA = c.CodiceComune;
                });

                var numCciaaPopolato = !String.IsNullOrEmpty(row.REGDITTE);
                var dataCciaaPopolata = row.DATAREGDITTE != DateTime.MinValue;
                var comuneCciaaPopolato = !String.IsNullOrEmpty(row.CODCOMREGDITTE);

                if (numCciaaPopolato || dataCciaaPopolata || comuneCciaaPopolato)
                {
                    if (!numCciaaPopolato || !dataCciaaPopolata || !comuneCciaaPopolato)
                    {
                        var errStr = "Errore di validazione: inserire i restanti dati relativi all'iscrizione alla camera di commercio";

                        ErroreInserimento(errStr);
                        return;
                    }
                }

                // Dati INPS
                row.MatricolaInps = this.txtNumeroInps.Text;
                row.CodSedeIscrizioneInps = this.acSedeINPS.Value;
                row.DesSedeIscrizioneInps = this.acSedeINPS.Text;

                // Dati INAIL
                row.MatricolaInail = this.txtNumeroINAIL.Text;
                row.CodSedeIscrizioneInail = this.acSedeINAIL.Value;
                row.DesSedeIscrizioneInail = this.acSedeINAIL.Text;

                AcceptEdit(AnagraficaDomanda.FromAnagrafeRow(row));
            }
            catch (FormValidationException ex)
            {
                ErroreInserimento(ex.Message);
            }
            catch (Exception ex)
            {
                this._log.Error(ex.ToString());

                throw;
            }
        }

        private void ControllaCampo(string valore, bool obbligatorio, string errore)
        {
            if (!obbligatorio)
            {
                return;
            }

            if (String.IsNullOrEmpty(valore))
            {
                throw new FormValidationException(errore);
            }
        }

        private void ValidaComune(Autocomplete field, string erroreComuneNonTrovato, Action<DatiComuneCompatto> comuneTrovatoCallback)
        {
            if (String.IsNullOrEmpty(field.Value))
            {
                return;
            }

            var comune = GetDatiComune(field.Value);

            if (comune == null)
            {
                throw new FormValidationException(erroreComuneNonTrovato);
            }

            comuneTrovatoCallback(comune);
        }

        protected void cmdCopiaResidenza_Click(object sender, EventArgs e)
        {
            //cddProvinciaCorrispondenza.SelectedValue = cddProvinciaSedeLegale.SelectedValue;
            //cddComuneCorrispondenza.SelectedValue= cddComuneSedeLegale.SelectedValue;

            this.acComuneCorrispondenza.Value = this.acComuneSedeLegale.Value;
            this.acComuneCorrispondenza.Text = this.acComuneSedeLegale.Text;


            this.IndirizzoCorrispondenza.Text = this.Indirizzo.Text;
            this.CittaCorrispondenza.Text = this.Citta.Text;
            this.CapCorrispondenza.Text = this.Cap.Text;

            this.Page.Validate();
        }
    }
}