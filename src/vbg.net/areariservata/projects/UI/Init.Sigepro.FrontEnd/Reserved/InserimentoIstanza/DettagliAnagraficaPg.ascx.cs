using System;
using System.ComponentModel;
using System.Web.UI;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Ninject;
using System.Text.RegularExpressions;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using System.Web.UI.WebControls;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DettagliAnagraficaPg : DettagliAnagraficaControl
    {
        [Inject]
        public IFormeGiuridicheRepository _formeGiuridicheRepository { get; set; }

        ILog _log = LogManager.GetLogger(typeof(DettagliAnagraficaPg));

        public bool SedeLegaleObbligatoria
        {
            get { return GetVs("SedeLegaleObbligatoria", false); }
            set { SetVs("SedeLegaleObbligatoria", value); }
        }

        public bool SedeLegaleVisibile
        {
            get { return GetVs("SedeLegaleVisibile", true); }
            set { SetVs("SedeLegaleVisibile", value); }
        }

        public bool DataCostituzioneObbligatoria
        {
            get { return GetVs("DataCostituzioneObbligatoria", false); }
            set { SetVs("DataCostituzioneObbligatoria", value); }
        }
        public bool DataCostituzioneVisibile
        {
            get { return GetVs("DataCostituzioneVisibile", true); }
            set { SetVs("DataCostituzioneVisibile", value); }
        }

        public bool TelefonoObbligatorio
        {
            get { return GetVs("TelefonoObbligatoria", false); }
            set { SetVs("TelefonoObbligatoria", value); }
        }
        public bool TelefonoVisibile
        {
            get { return GetVs("TelefonoVisibile", true); }
            set { SetVs("TelefonoVisibile", value); }
        }

        public bool CellulareObbligatorio
        {
            get { return GetVs("CellulareObbligatoria", false); }
            set { SetVs("CellulareObbligatoria", value); }
        }
        public bool CellulareVisibile
        {
            get { return GetVs("CellulareVisibile", true); }
            set { SetVs("CellulareVisibile", value); }
        }

        public bool FaxObbligatorio
        {
            get { return GetVs("FaxObbligatoria", false); }
            set { SetVs("FaxObbligatoria", value); }
        }
        public bool FaxVisibile
        {
            get { return GetVs("FaxVisibile", true); }
            set { SetVs("FaxVisibile", value); }
        }

        public bool EmailObbligatoria
        {
            get { return GetVs("EmailObbligatorio", false); }
            set { SetVs("EmailObbligatorio", value); }
        }
        public bool EmailVisibile
        {
            get { return GetVs("EmailVisibile", true); }
            set { SetVs("EmailVisibile", value); }
        }

        public bool PecObbligatoria
        {
            get { return GetVs("PecObbligatorio", false); }
            set { SetVs("PecObbligatorio", value); }
        }

        public bool PecVisibile
        {
            get { return GetVs("PecVisibile", true); }
            set { SetVs("PecVisibile", value); }
        }

        public bool CciaaObbligatoria
        {
            get { return GetVs("CciaaObbligatoria", false); }
            set { SetVs("CciaaObbligatoria", value); }
        }

        public bool CciaaVisibile
        {
            get { return GetVs("CciaaVisibile", true); }
            set { SetVs("CciaaVisibile", value); }
        }

        public bool RegTribObbligatorio
        {
            get { return GetVs("RegTribObbligatoria", false); }
            set { SetVs("RegTribObbligatoria", value); }
        }

        public bool RegTribVisibile
        {
            get { return GetVs("RegTribVisibile", true); }
            set { SetVs("RegTribVisibile", value); }
        }

        public bool ReaObbligatoria
        {
            get { return GetVs("ReaObbligatoria", false); }
            set { SetVs("ReaObbligatoria", value); }
        }

        public bool ReaVisibile
        {
            get { return GetVs("ReaVisibile", true); }
            set { SetVs("ReaVisibile", value); }
        }

        public bool InpsObbligatoria
        {
            get { return GetVs("InpsObbligatoria", false); }
            set { SetVs("InpsObbligatoria", value); }
        }

        public bool InpsVisibile
        {
            get { return GetVs("InpsVisibile", true); }
            set { SetVs("InpsVisibile", value); }
        }

        public bool InailObbligatoria
        {
            get { return GetVs("InailObbligatoria", false); }
            set { SetVs("InailObbligatoria", value); }
        }

        public bool InailVisibile
        {
            get { return GetVs("InailVisibile", true); }
            set { SetVs("InailVisibile", value); }
        }

        public bool PartitaIvaVisibile
        {
            get { return GetVs("PartitaIvaVisibile", true); }
            set { SetVs("PartitaIvaVisibile", value); }
        }

        public bool PartitaIvaObbligatoria
        {
            get { return GetVs("PartitaIvaObbligatoria", false); }
            set { SetVs("PartitaIvaObbligatoria", value); }
        }

        public bool CorrispondenzaVisibile
        {
            set { SetVs("CorrispondenzaVisibile", value); }
            get { return GetVs("CorrispondenzaVisibile", true); }
        }

        public bool CorrispondenzaObbligatoria
        {
            set { SetVs("CorrispondenzaObbligatoria", value); }
            get { return GetVs("CorrispondenzaObbligatoria", false); }
        }

        public bool EmailoPecObbligatori
        {
            set { SetVs("EmailoPecObbligatori", value); }
            get { return GetVs("EmailoPecObbligatori", false); }
        }

        public bool TelefonooCellulareObbligatori
        {
            set { SetVs("TelefonooCellulareObbligatori", value); }
            get { return GetVs("TelefonooCellulareObbligatori", false); }
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
            get { return _aliasSoftwareResolver.AliasComune; }
        }

        protected string Software
        {
            get { return _aliasSoftwareResolver.Software; }
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
                BindFormeGiuridiche();
            }
        }

        private void BindFormeGiuridiche()
        {
            var formeGiuridiche = _formeGiuridicheRepository.GetList(IdComune);

            CodiceFormaGiuridica.Items.Clear();
            CodiceFormaGiuridica.Items.Add(new ListItem("Selezionare...", String.Empty));

            foreach (var fg in formeGiuridiche)
            {
                CodiceFormaGiuridica.Items.Add(new ListItem(fg.FORMAGIURIDICA, fg.CODICEFORMAGIURIDICA));
            }
        }



        public override void DataBind()
        {
            Nominativo.Text = AnagraficaBinder.SafeValue(DataSource, "NOMINATIVO");
            Indirizzo.Text = AnagraficaBinder.SafeValue(DataSource, "INDIRIZZO");
            Citta.Text = AnagraficaBinder.SafeValue(DataSource, "CITTA");
            Cap.Text = AnagraficaBinder.SafeValue(DataSource, "CAP");
            Fax.Text = AnagraficaBinder.SafeValue(DataSource, "FAX");
            email.Text = AnagraficaBinder.SafeValue(DataSource, "EMAIL");
            emailPec.Text = AnagraficaBinder.SafeValue(DataSource, "Pec");

            CodiceFiscale.Text = DataSource.CODICEFISCALE;
            PartitaIva.Text = DataSource.PartitaIva;

            /*
            if (String.IsNullOrEmpty(PartitaIva.Text) && Regex.IsMatch(CodiceFiscale.Text, "^\\d{11}$"))
            {
                PartitaIva.Text = CodiceFiscale.Text;
                CodiceFiscale.Text = string.Empty;
            }
            */


            RegDitte.Text = AnagraficaBinder.SafeValue(DataSource, "REGDITTE");
            RegTrib.Text = AnagraficaBinder.SafeValue(DataSource, "REGTRIB");
            NumIscrREA.Text = AnagraficaBinder.SafeValue(DataSource, "NUMISCRREA");
            IndirizzoCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "INDIRIZZOCORRISPONDENZA");
            CittaCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "CITTACORRISPONDENZA");
            CapCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "CAPCORRISPONDENZA");
            Telefono.Text = AnagraficaBinder.SafeValue(DataSource, "TELEFONO");
            TelefonoCellulare.Text = AnagraficaBinder.SafeValue(DataSource, "TELEFONOCELLULARE");

            string comuneSedeLegale = AnagraficaBinder.SafeValue(DataSource, "COMUNERESIDENZA");

            acComuneSedeLegale.Value = "";
            acComuneSedeLegale.Text = "";

            if (!String.IsNullOrEmpty(comuneSedeLegale))
            {
                var comuneSLDto = GetDatiComune(comuneSedeLegale);

                if (comuneSLDto != null)
                {
                    acComuneSedeLegale.Value = comuneSLDto.CodiceComune;
                    acComuneSedeLegale.Text = comuneSLDto.Comune + " (" + comuneSLDto.SiglaProvincia + ")";
                }
            }

            string comuneCorrispondenza = AnagraficaBinder.SafeValue(DataSource, "COMUNECORRISPONDENZA");

            /*AnagraficaBinder.BindComboComune(IdComune, provinciaCorrispondenza, comuneCorrispondenza,
                                    lblProvinciaCorrispondenza, lblComuneCorrispondenza,
                                    cddProvinciaCorrispondenza, cddComuneCorrispondenza,
                                    ddlProvinciaCorrispondenza, ddlComuneCorrispondenza,
                                    null, null );*/

            acComuneCorrispondenza.Value = String.Empty;
            acComuneCorrispondenza.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneCorrispondenza))
            {
                var comuneCorrDto = GetDatiComune(comuneCorrispondenza);

                if (comuneCorrDto != null)
                {
                    acComuneCorrispondenza.Value = comuneCorrDto.CodiceComune;
                    acComuneCorrispondenza.Text = comuneCorrDto.Comune + " (" + comuneCorrDto.SiglaProvincia + ")";
                }
            }



            DataNominativo.DateValue = (DataSource.IsDATANOMINATIVONull()) ? (DateTime?)null : DataSource.DATANOMINATIVO;
            DataRegDitte.DateValue = (DataSource.IsDATAREGDITTENull()) ? (DateTime?)null : DataSource.DATAREGDITTE;
            RegTribData.DateValue = (DataSource.IsDATAREGTRIBNull()) ? (DateTime?)null : DataSource.DATAREGTRIB;

            var comuneRegDitte = AnagraficaBinder.SafeValue(DataSource, "CODCOMREGDITTE");

            acProvinciaCCIAA.Value = String.Empty;
            acProvinciaCCIAA.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneRegDitte))
            {
                var comuneCciaaDto = GetDatiComune(comuneRegDitte);

                if (comuneCciaaDto != null)
                {
                    acProvinciaCCIAA.Value = comuneCciaaDto.CodiceComune;
                    acProvinciaCCIAA.Text = comuneCciaaDto.Comune + " (" + comuneCciaaDto.SiglaProvincia + ")";
                }
            }



            string comuneRegTrib = AnagraficaBinder.SafeValue(DataSource, "CODCOMREGTRIB");

            acComuneRegTrib.Value = acComuneRegTrib.Text = String.Empty;

            if (!String.IsNullOrEmpty(comuneRegTrib))
            {
                var comuneRegTribDto = GetDatiComune(comuneRegTrib);

                if (comuneRegTribDto != null)
                {
                    acComuneRegTrib.Value = comuneRegTribDto.CodiceComune;
                    acComuneRegTrib.Text = comuneRegTribDto.Comune + " (" + comuneRegTribDto.SiglaProvincia + ")";
                }
            }




            string provinciaRea = AnagraficaBinder.SafeValue(DataSource, "PROVINCIAREA");

            acProvinciaREA.Value = acProvinciaREA.Text = String.Empty;

            if (!String.IsNullOrEmpty(provinciaRea))
            {
                var provinciaReaDto = GetDatiProvincia(provinciaRea);

                if (provinciaReaDto != null)
                {
                    acProvinciaREA.Value = provinciaReaDto.SiglaProvincia;
                    acProvinciaREA.Text = provinciaReaDto.Provincia;
                }

            }

            //TipoSoggetto.CodiceIntervento = this.CodiceIntervento;
            TipoSoggetto.DataBind();
            TipoSoggetto.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "TIPOSOGGETTO");

            if (TipoSoggetto.SelectedValue == String.Empty && TipoSoggetto.Items.Count == 2)
            {
                TipoSoggetto.SelectedValue = TipoSoggetto.Items[1].Value;
            }

            txtDescrizioneEstesa.Text = AnagraficaBinder.SafeValue(DataSource, "DescrizioneTipoSoggetto");


            CodiceFormaGiuridica.DataBind();
            CodiceFormaGiuridica.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "FORMAGIURIDICA");
            //CodiceFormaGiuridica.Enabled = AnagraficaBinder.EvalEnabled( DataSource ,"FORMAGIURIDICA");

            ANAGRAFE_PK.Value = AnagraficaBinder.SafeValue(DataSource, "ANAGRAFE_PK");

            CodiceFiscale.ReadOnly = false;
            PartitaIva.ReadOnly = false;

            if (!PermettiModificaDatiAnagrafici)
            {
                if (!String.IsNullOrEmpty(CodiceFiscale.Text))
                    CodiceFiscale.ReadOnly = true;

                if (!String.IsNullOrEmpty(PartitaIva.Text))
                    PartitaIva.ReadOnly = true;
            }


            // Dati INPS
            txtNumeroInps.Text = DataSource.MatricolaInps;
            acSedeINPS.Value = DataSource.CodSedeIscrizioneInps;
            acSedeINPS.Text = DataSource.DesSedeIscrizioneInps;

            // Dati INAIL
            txtNumeroINAIL.Text = DataSource.MatricolaInail;
            acSedeINAIL.Value = DataSource.CodSedeIscrizioneInail;
            acSedeINAIL.Text = DataSource.DesSedeIscrizioneInail;

            Page.Validate();

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
                Page.Validate();

                if (!Page.IsValid)
                    return;

                int rowId = -1;

                int.TryParse(ANAGRAFE_PK.Value, out rowId);

                ControllaCampo(CodiceFiscale.Text, true, "E'obbligatorio specificare un codice fiscale oppure una partita IVA");

                var row = GetAnagrafeRow(rowId).ToAnagrafeRow();

                row.TIPOANAGRAFE = "G";

                if (!String.IsNullOrEmpty(TipoSoggetto.SelectedValue))
                {
                    row.TIPOSOGGETTO = Convert.ToInt32(TipoSoggetto.SelectedValue);
                }

                row.DescrSoggetto = TipoSoggetto.SelectedItem.Text;
                row.DescrizioneTipoSoggetto = txtDescrizioneEstesa.Text;

                row.NOMINATIVO = Nominativo.Text;
                row.CODICEFISCALE = CodiceFiscale.Text;
                row.PartitaIva = PartitaIva.Text;

                if (!String.IsNullOrEmpty(CodiceFormaGiuridica.SelectedValue))
                    row.FORMAGIURIDICA = Convert.ToInt32(CodiceFormaGiuridica.SelectedValue);

                row.DescrSoggetto = TipoSoggetto.SelectedItem.Text;



                row.INDIRIZZO = Indirizzo.Text;
                row.CITTA = Citta.Text;
                row.CAP = Cap.Text;
                row.INDIRIZZOCORRISPONDENZA = IndirizzoCorrispondenza.Text;
                row.CITTACORRISPONDENZA = CittaCorrispondenza.Text;
                row.CAPCORRISPONDENZA = CapCorrispondenza.Text;
                row.TELEFONO = Telefono.Text;
                row.TELEFONOCELLULARE = TelefonoCellulare.Text;
                row.FAX = Fax.Text;
                row.EMAIL = email.Text;
                row.Pec = emailPec.Text;
                row.REGDITTE = RegDitte.Text;
                row.REGTRIB = RegTrib.Text;
                row.NUMISCRREA = NumIscrREA.Text;
                row.PROVINCIAREA = acProvinciaREA.Value;
                row.DATANOMINATIVO = DataNominativo.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAREGDITTE = DataRegDitte.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAREGTRIB = RegTribData.DateValue.GetValueOrDefault(DateTime.MinValue);
                row.DATAISCRREA = DataIscrREA.DateValue.GetValueOrDefault(DateTime.MinValue);

                ControllaCampo(PartitaIva.Text, PartitaIvaObbligatoria, "Specificare una partita IVA");
                ControllaCampo(DataNominativo.Text, DataCostituzioneObbligatoria, "Specificare la data di costituzione");

                ControllaCampo(acComuneSedeLegale.Value, SedeLegaleObbligatoria, "Specificare il comune della sede legale");
                ControllaCampo(Indirizzo.Text, SedeLegaleObbligatoria, "Specificare l'indirizzo della sede legale");
                ControllaCampo(Cap.Text, SedeLegaleObbligatoria, "Specificare il CAP");

                ControllaCampo(RegDitte.Text, CciaaObbligatoria, "Specificare il numero di iscrizione alla camera di commercio");
                ControllaCampo(DataRegDitte.Text, CciaaObbligatoria, "Specificare la data di iscrizione alla camera di commercio");
                ControllaCampo(acProvinciaCCIAA.Value, CciaaObbligatoria, "Specificare la provincia di iscrizione alla camera di commercio");

                ControllaCampo(RegTrib.Text, RegTribObbligatorio, "Specificare il numero di iscrizione al Reg.Trib.");
                ControllaCampo(RegTribData.Text, RegTribObbligatorio, "Specificare la data di iscrizione al Reg.Trib.");
                ControllaCampo(acComuneRegTrib.Value, RegTribObbligatorio, "Specificare il comune di iscrizione al Reg.Trib.");

                ControllaCampo(NumIscrREA.Text, ReaObbligatoria, "Specificare il numero di iscrizione al REA");
                ControllaCampo(DataIscrREA.Text, ReaObbligatoria, "Specificare la data di iscrizione al REA");
                ControllaCampo(acProvinciaREA.Value, ReaObbligatoria, "Specificare la provincia di iscrizione al REA");

                ControllaCampo(txtNumeroInps.Text, InpsObbligatoria, "Specificare la matricola INPS");
                ControllaCampo(acSedeINPS.Value, InpsObbligatoria, "Specificare la sede di iscrizione INPS");

                ControllaCampo(txtNumeroINAIL.Text, InailObbligatoria, "Specificare la matricola INAIL");
                ControllaCampo(acSedeINAIL.Value, InailObbligatoria, "Specificare la sede di iscrizione INAIL");

                ControllaCampo(Telefono.Text, TelefonoObbligatorio, "Specificare il numero di telefono");
                ControllaCampo(TelefonoCellulare.Text, CellulareObbligatorio, "Specificare il numero di cellulare");
                ControllaCampo(Fax.Text, FaxObbligatorio, "Specificare il numero di Fax");
                ControllaCampo(email.Text, EmailObbligatoria, "Specificare un indirizzo e-mail");
                ControllaCampo(emailPec.Text, PecObbligatoria, "Specificare un indirizzo PEC");

                if (this.EmailoPecObbligatori && (String.IsNullOrEmpty(emailPec.Text) && String.IsNullOrEmpty(email.Text)))
                {
                    ErroreInserimento("Specificare l'indirizzo E-Mail o PEC");
                    return;
                }

                if (this.TelefonooCellulareObbligatori && (String.IsNullOrEmpty(Telefono.Text) && String.IsNullOrEmpty(TelefonoCellulare.Text)))
                {
                    ErroreInserimento("Specificare il numero di telefono o cellulare");
                    return;
                }


                // Validazione comuni e provincie
                row.CODPROVREGDITTE = row.CODCOMREGDITTE = String.Empty;

                ValidaComune(acProvinciaCCIAA, "La provincia CCIAA non è valida", c =>
                {
                    row.CODPROVREGDITTE = c.SiglaProvincia;
                    row.CODCOMREGDITTE = c.CodiceComune;
                });


                row.CODPROVREGTRIB = row.CODCOMREGTRIB = String.Empty;

                ValidaComune(acComuneRegTrib, "Il comune Reg. Trib. non è valido", c =>
                {
                    row.CODPROVREGTRIB = c.SiglaProvincia;
                    row.CODCOMREGTRIB = c.CodiceComune;
                });


                row.PROVINCIA = row.COMUNERESIDENZA = String.Empty;

                ValidaComune(acComuneSedeLegale, "Il comune della sede legale non è valido", c =>
                {
                    row.PROVINCIA = c.SiglaProvincia;
                    row.COMUNERESIDENZA = c.CodiceComune;
                });


                row.PROVINCIACORRISPONDENZA = row.COMUNECORRISPONDENZA;

                ValidaComune(acComuneCorrispondenza, "Il comune per la corrispondenza non è valido", c =>
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
                row.MatricolaInps = txtNumeroInps.Text;
                row.CodSedeIscrizioneInps = acSedeINPS.Value;
                row.DesSedeIscrizioneInps = acSedeINPS.Text;

                // Dati INAIL
                row.MatricolaInail = txtNumeroINAIL.Text;
                row.CodSedeIscrizioneInail = acSedeINAIL.Value;
                row.DesSedeIscrizioneInail = acSedeINAIL.Text;

                AcceptEdit(AnagraficaDomanda.FromAnagrafeRow(row));
            }
            catch (FormValidationException ex)
            {
                ErroreInserimento(ex.Message);
            }
            catch (Exception ex)
            {
                _log.Error(ex.ToString());

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

            acComuneCorrispondenza.Value = acComuneSedeLegale.Value;
            acComuneCorrispondenza.Text = acComuneSedeLegale.Text;


            IndirizzoCorrispondenza.Text = Indirizzo.Text;
            CittaCorrispondenza.Text = Citta.Text;
            CapCorrispondenza.Text = Cap.Text;

            Page.Validate();
        }
    }
}