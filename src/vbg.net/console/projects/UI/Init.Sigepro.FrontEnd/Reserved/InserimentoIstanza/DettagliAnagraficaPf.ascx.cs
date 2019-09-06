using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.IoC;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
using Ninject;
using System;
using System.Linq;
using System.Collections.Generic;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DettagliAnagraficaPf : DettagliAnagraficaControl
    {
        [Inject]
        public IAliasSoftwareResolver _aliasSoftwareResolver { get; set; }

        [Inject]
        public ICittadinanzeService _cittadinanzeService { get; set; }

        [Inject]
        public ITitoliRepository _titoliRepository { get; set; }

        [Inject]
        public IElenchiProfessionaliRepository _elenchiProfessionaliRepository { get; set; }

        public delegate TipoSoggetto OnGetTipoSoggetto(int idTipoSoggetto);
        public delegate IEnumerable<TipoSoggetto> OnGetTipiSoggettoPfDelegate();
        public delegate DatiComuneCompatto OnGetDatiComune(string codiceComune);
        public delegate DatiProvinciaCompatto OnGetDatiProvincia(string siglaProvincia);
        public delegate AnagraficaDomanda OnGetAnagrafeRow(int idAnagrafica);
        public delegate Cittadinanza OnGetDatiCittadinanza(string idCittadinanza);
        public delegate void OnAcceptEdit(AnagraficaDomanda row);


        public event EventHandler CancelEdit;
        public event OnAcceptEdit AcceptEdit;
        public event OnGetAnagrafeRow GetAnagrafeRow;
        public event OnGetTipiSoggettoPfDelegate GetTipiSoggetto;
        public event OnGetTipoSoggetto GetTipoSoggetto;
        public event OnGetDatiComune GetDatiComune;
        public event OnGetDatiProvincia GetDatiProvincia;
        public event OnGetDatiCittadinanza GetDatiCittadinanza;

        public event DatiAnagrafici.ErrorDelegate ErroreInserimento;

        /// <summary>
        /// Fonte dati del controllo
        /// </summary>
        [Bindable(BindableSupport.Yes)]
        public virtual PresentazioneIstanzaDbV2.ANAGRAFERow DataSource { get; set; }

        public string MessaggioVerificaPec
        {
            get { object o = this.ViewState["MessaggioVerificaPec"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["MessaggioVerificaPec"] = value; }
        }


        protected string IdComune
        {
            get { return _aliasSoftwareResolver.AliasComune; }
        }

        protected string Software
        {
            get { return _aliasSoftwareResolver.Software; }
        }

        public bool PermettiModificaDatiAnagrafici
        {
            get { object o = this.ViewState["PermettiModificaDatiAnagrafici"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiModificaDatiAnagrafici"] = value; }
        }

        public int? CodiceIntervento
        {
            set { this.TipoSoggetto.CodiceIntervento = value; }
        }

        public string LimitaDatiAlbo
        {
            get { object o = this.ViewState["LimitaDatiAlbo"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["LimitaDatiAlbo"] = value; BindElenchiProfessionali(); }
        }

        protected override void OnLoad(EventArgs e)
        {
            // Deve sempre essere bindato altrimenti gli attributi non persistono
            BindElenchiProfessionali();
        }


        protected override void OnInit(EventArgs e)
        {
            

            if (!this.Page.IsPostBack)
            {
                BindCittadinanze();
                BindTitoli();
            }

            base.OnInit(e);
        }


        private void BindElenchiProfessionali()
        {
            var oldSelectedValue = this.ddlAlbo.SelectedValue;

            this.ddlAlbo.Items.Clear();

            this.ddlAlbo.Items.Add(new ListItem(String.Empty, String.Empty));

            var limitaDatiAlbo = new int[0];

            if (!String.IsNullOrEmpty(LimitaDatiAlbo))
            {
                limitaDatiAlbo = LimitaDatiAlbo.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var dati = _elenchiProfessionaliRepository.GetList(IdComune)
                        .Where(x =>
                        {
                            return limitaDatiAlbo.Length == 0 ? true : limitaDatiAlbo.Contains(x.EpId.Value);
                        })
                        .ToList();

            for (int i = 0; i < dati.Count; i++)
            {
                var item = new ListItem(dati[i].EpDescrizione, dati[i].EpId.ToString());

                item.Attributes.Add("data-regionale", dati[i].EpRegionale.ToString());

                this.ddlAlbo.Items.Add(item);
            }

            if (!String.IsNullOrEmpty(oldSelectedValue) && dati.Select(x => x.EpId).Contains(Convert.ToInt32(oldSelectedValue)))
            {
                this.ddlAlbo.SelectedValue = oldSelectedValue;
            }


        }

        private void BindCittadinanze()
        {
            this.CodiceCittadinanza.Items.Add(new ListItem(String.Empty, String.Empty));

            foreach (var c in this._cittadinanzeService.GetListaCittadinanze(true))
            {
                this.CodiceCittadinanza.Items.Add(new ListItem(c.Descrizione, c.Codice.ToString()));
            }
        }

        private void BindTitoli()
        {
            var titoli = _titoliRepository.GetList(IdComune);

            this.TITOLO.Items.Clear();
            this.TITOLO.Items.Add(new ListItem(string.Empty, String.Empty));

            foreach (var t in titoli)
            {
                this.TITOLO.Items.Add(new ListItem(t.TITOLO, t.CODICETITOLO));
            }
        }

        public DettagliAnagraficaPf()
        {
            FoKernelContainer.Inject(this);
        }

        public override void DataBind()
        {
            if (!String.IsNullOrEmpty(MessaggioVerificaPec))
            {
                //pnlMessaggioPec.Visible = true;

                var listaTipiSoggetto = new List<string>();
                var tipiSoggettoPf = GetTipiSoggetto();

                foreach (var tipoSoggettoPf in tipiSoggettoPf)
                {
                    if (tipoSoggettoPf.IsRichiedente() || tipoSoggettoPf.IsTecnico())
                        listaTipiSoggetto.Add(tipoSoggettoPf.Descrizione);
                }

                //ltrMessaggioVerificaPec.Text = String.Format(MessaggioVerificaPec, String.Join(", ", listaTipiSoggetto.ToArray()));
            }


            NOMINATIVO.Text = AnagraficaBinder.SafeValue(DataSource, "NOMINATIVO");
            NOME.Text = AnagraficaBinder.SafeValue(DataSource, "NOME");

            TITOLO.DataBind();
            TITOLO.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "TITOLO");

            sesso.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "SESSO");

            CodiceCittadinanza.DataBind();
            CodiceCittadinanza.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "CODICECITTADINANZA");

            // Residenza
            string comuneResidenza = AnagraficaBinder.SafeValue(DataSource, "COMUNERESIDENZA");

            if (!String.IsNullOrEmpty(comuneResidenza))
            {
                var comuneresidenzaDto = this.GetDatiComune(comuneResidenza);

                if (comuneresidenzaDto != null)
                {
                    acComuneResidenza.Value = comuneresidenzaDto.CodiceComune;
                    acComuneResidenza.Text = comuneresidenzaDto.Comune + " (" + comuneresidenzaDto.SiglaProvincia + ")";
                }
            }
            else
            {
                acComuneResidenza.Value = String.Empty;
                acComuneResidenza.Text = String.Empty;
            }


            Indirizzo.Text = AnagraficaBinder.SafeValue(DataSource, "INDIRIZZO");
            Citta.Text = AnagraficaBinder.SafeValue(DataSource, "CITTA");
            Cap.Text = AnagraficaBinder.SafeValue(DataSource, "CAP");


            // Corrispondenza
            string comuneCorrispondenza = AnagraficaBinder.SafeValue(DataSource, "COMUNECORRISPONDENZA");

            if (!String.IsNullOrEmpty(comuneCorrispondenza))
            {
                var comuneCorrispondenzaDto = GetDatiComune(comuneCorrispondenza);

                if (comuneCorrispondenzaDto != null)
                {
                    acComuneCorrispondenza.Value = comuneCorrispondenza;
                    acComuneCorrispondenza.Text = comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")";
                }
            }
            else
            {
                acComuneCorrispondenza.Value = String.Empty;
                acComuneCorrispondenza.Text = String.Empty;
            }

            IndirizzoCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "INDIRIZZOCORRISPONDENZA");
            CittaCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "CITTACORRISPONDENZA");
            CapCorrispondenza.Text = AnagraficaBinder.SafeValue(DataSource, "CAPCORRISPONDENZA");
            CodiceFiscale.Text = AnagraficaBinder.SafeValue(DataSource, "CODICEFISCALE");


            string comuneNascita = AnagraficaBinder.SafeValue(DataSource, "CODCOMNASCITA");

            // comune nascita
            if (!String.IsNullOrEmpty(comuneNascita))
            {
                var comuneNascitaDto = GetDatiComune(comuneNascita);

                if (comuneNascitaDto != null)
                {
                    acComuneNascita.Value = comuneNascita;
                    acComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                }
                else
                {
                    acComuneNascita.Value = acComuneNascita.Text = String.Empty;
                }
            }
            else
            {
                // provo a ricavare il comune dal codice fiscale
                acComuneNascita.Value = acComuneNascita.Text = String.Empty;

                if (!String.IsNullOrEmpty(CodiceFiscale.Text) && CodiceFiscale.Text.Length == 16)
                {
                    var codComune = CodiceFiscale.Text.Substring(11, 4);

                    var comuneNascitaDto = GetDatiComune(codComune);

                    if (comuneNascitaDto != null)
                    {
                        acComuneNascita.Value = comuneNascitaDto.CodiceComune;
                        acComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                    }
                }
            }

            Telefono.Text = AnagraficaBinder.SafeValue(DataSource, "TELEFONO");
            TelefonoCellulare.Text = AnagraficaBinder.SafeValue(DataSource, "TELEFONOCELLULARE");
            Fax.Text = AnagraficaBinder.SafeValue(DataSource, "FAX");
            email.Text = AnagraficaBinder.SafeValue(DataSource, "EMAIL");
            emailPec.Text = AnagraficaBinder.SafeValue(DataSource, "Pec");

            TipoSoggetto.DataBind();
            TipoSoggetto.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "TIPOSOGGETTO");

            if (TipoSoggetto.SelectedValue == String.Empty && TipoSoggetto.Items.Count == 2)
            {
                TipoSoggetto.SelectedValue = TipoSoggetto.Items[1].Value;
            }

            DataNascita.DateValue = (DataSource.IsDATANASCITANull()) ? (DateTime?)null : DataSource.DATANASCITA;

            ANAGRAFE_PK.Value = AnagraficaBinder.SafeValue(DataSource, "ANAGRAFE_PK");


            ddlAlbo.DataBind();
            ddlAlbo.SelectedValue = AnagraficaBinder.SafeValue(DataSource, "IdAlbo");

            txtNumeroAlbo.Text = AnagraficaBinder.SafeValue(DataSource, "NumeroAlbo");

            string provinciaAlbo = AnagraficaBinder.SafeValue(DataSource, "ProvinciaAlbo");

            acProvinciaAlbo.Value = acProvinciaAlbo.Text = String.Empty;

            if (!String.IsNullOrEmpty(provinciaAlbo))
            {
                var provinciaDto = GetDatiProvincia(provinciaAlbo);

                if (provinciaDto != null)
                {
                    acProvinciaAlbo.Value = provinciaDto.SiglaProvincia;
                    acProvinciaAlbo.Text = provinciaDto.Provincia;
                }
                else
                {
                    acProvinciaAlbo.Value = provinciaAlbo;
                    acProvinciaAlbo.Text = provinciaAlbo;                    
                }
            }

            txtDescrizioneEstesa.Text = AnagraficaBinder.SafeValue(DataSource, "DescrizioneTipoSoggetto");

            NOME.ReadOnly = false;
            NOMINATIVO.ReadOnly = false;
            DataNascita.ReadOnly = false;
            acComuneNascita.ReadOnly = false;

            if (!PermettiModificaDatiAnagrafici)
            {
                NOME.ReadOnly = true;
                NOMINATIVO.ReadOnly = true;
                // DataNascita.ReadOnly = true;
                acComuneNascita.ReadOnly = true;
            }

            Page.Validate();

            // Altrimenti i dati dei validatori non vengono bindati :P
            // base.DataBind();
        }

        protected void cmdCopiaResidenza_Click(object sender, EventArgs e)
        {
            var codComuneResidenza = acComuneResidenza.Value;
            var valComuneResidenza = acComuneResidenza.Text;

            acComuneCorrispondenza.Value = codComuneResidenza;
            acComuneCorrispondenza.Text = valComuneResidenza;

            IndirizzoCorrispondenza.Text = Indirizzo.Text;
            CittaCorrispondenza.Text = Citta.Text;
            CapCorrispondenza.Text = Cap.Text;
        }

        protected void cmdCancel_Click(object sender, EventArgs e)
        {
            if (CancelEdit != null)
                CancelEdit(this, EventArgs.Empty);
        }

        protected void cmdConfirm_Click(object sender, EventArgs e)
        {
            Page.Validate();
            if (!Page.IsValid) return;

            if (String.IsNullOrEmpty(TipoSoggetto.SelectedValue))
            {
                ErroreInserimento("Specificare il tipo soggetto");
                return;
            }

            if (String.IsNullOrEmpty(NOMINATIVO.Text))
            {
                ErroreInserimento("Specificare il cognome");
                return;
            }

            if (String.IsNullOrEmpty(NOME.Text))
            {
                ErroreInserimento("Specificare il nome");
                return;
            }

            if (String.IsNullOrEmpty(sesso.SelectedValue))
            {
                ErroreInserimento("Specificare il sesso");
                return;
            }

            if (!DataNascita.DateValue.HasValue)
            {
                ErroreInserimento("Specificare la data di nascita");
                return;
            }

            if (String.IsNullOrEmpty(acComuneNascita.Value))
            {
                ErroreInserimento("Specificare il comune di nascita");
                return;
            }

            if (String.IsNullOrEmpty(CodiceFiscale.Text))
            {
                ErroreInserimento("Specificare il codice fiscale");
                return;
            }

            if (this.CittadinanzaObbligatoria && String.IsNullOrEmpty(CodiceCittadinanza.SelectedValue))
            {
                ErroreInserimento("Specificare la cittadinanza");
                return;
            }

            if (this.ResidenzaObbligatoria)
            {
                if (String.IsNullOrEmpty(acComuneResidenza.Value))
                {
                    ErroreInserimento("Specificare il comune di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(Indirizzo.Text))
                {
                    ErroreInserimento("Specificare l'indirizzo di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(Cap.Text))
                {
                    ErroreInserimento("Specificare il cap di residenza");
                    return;
                }
            }

            if (this.TelefonoObbligatorio && String.IsNullOrEmpty(Telefono.Text))
            {
                ErroreInserimento("Specificare il numero di telefono");
                return;
            }

            if (this.CellulareObbligatorio && String.IsNullOrEmpty(TelefonoCellulare.Text))
            {
                ErroreInserimento("Specificare il numero di cellulare");
                return;
            }

            if (this.FaxObbligatorio && String.IsNullOrEmpty(Fax.Text))
            {
                ErroreInserimento("Specificare il numero di fax");
                return;
            }

            if (this.EmailObbligatoria && String.IsNullOrEmpty(email.Text))
            {
                ErroreInserimento("Specificare l'indirizzo email");
                return;
            }

            if (this.PecObbligatoria && String.IsNullOrEmpty(emailPec.Text))
            {
                ErroreInserimento("Specificare l'indirizzo PEC");
                return;
            }


            int idAnagrafica = -1;

            int.TryParse(ANAGRAFE_PK.Value, out idAnagrafica);

            var row = GetAnagrafeRow(idAnagrafica).ToAnagrafeRow();

            var datiComuneNascita = GetDatiComune(acComuneNascita.Value);

            if (datiComuneNascita == null)
            {
                ErroreInserimento("Il comune di nascita specificato non è valido");
                return;
            }

            row.TIPOANAGRAFE = "F";

            if (!String.IsNullOrEmpty(TITOLO.SelectedValue))
            {
                row.TITOLO = TITOLO.SelectedValue;
            }

            row.NOMINATIVO = NOMINATIVO.Text;
            row.NOME = NOME.Text;

            row.SESSO = sesso.SelectedValue;


            row.PROVINCIANASCITA = datiComuneNascita.SiglaProvincia;

            row.INDIRIZZO = Indirizzo.Text;
            row.CITTA = Citta.Text;
            row.CAP = Cap.Text;

            row.INDIRIZZOCORRISPONDENZA = IndirizzoCorrispondenza.Text;
            row.CITTACORRISPONDENZA = CittaCorrispondenza.Text;
            row.CAPCORRISPONDENZA = CapCorrispondenza.Text;

            row.CODICEFISCALE = CodiceFiscale.Text.ToUpper();
            row.TELEFONO = Telefono.Text;
            row.TELEFONOCELLULARE = TelefonoCellulare.Text;
            row.FAX = Fax.Text;
            row.EMAIL = email.Text;
            row.Pec = emailPec.Text;

            row.DATANASCITA = DataNascita.DateValue.GetValueOrDefault(DateTime.MinValue);

            // Cittadinanza
            var datiCittadinanza = GetDatiCittadinanza(CodiceCittadinanza.SelectedValue);

            if (datiCittadinanza != null)
            {
                row.CODICECITTADINANZA = datiCittadinanza.Codice.Value;
                row.IsCittadinoExtracomunitario = datiCittadinanza.FlgPaeseComunitario.GetValueOrDefault(0) == 0;
            }

            // Residenza
            var datiComuneResidenza = GetDatiComune(acComuneResidenza.Value);

            if (datiComuneResidenza != null)
            {
                row.COMUNERESIDENZA = datiComuneResidenza.CodiceComune;
                row.PROVINCIA = datiComuneResidenza.SiglaProvincia;
            }

            if (!String.IsNullOrEmpty(acComuneCorrispondenza.Value))
            {
                datiComuneNascita = GetDatiComune(acComuneCorrispondenza.Value);

                if (datiComuneNascita != null)
                {
                    row.PROVINCIACORRISPONDENZA = datiComuneNascita.SiglaProvincia;
                    row.COMUNECORRISPONDENZA = datiComuneNascita.CodiceComune;
                }
            }

            row.CODCOMNASCITA = acComuneNascita.Value;

            row.TIPOSOGGETTO = Convert.ToInt32(TipoSoggetto.SelectedValue);

            var tipoSoggetto = GetTipoSoggetto(row.TIPOSOGGETTO);

            if (tipoSoggetto != null && tipoSoggetto.RichiedeDatiAlbo)
            {
                if (String.IsNullOrEmpty(ddlAlbo.SelectedValue))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare l'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(txtNumeroAlbo.Text.Trim()))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare il numero di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(acProvinciaAlbo.Value.Trim()))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare la provincia di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                row.IdAlbo = ddlAlbo.SelectedValue;

                if (!String.IsNullOrEmpty(ddlAlbo.SelectedValue))
                    row.DescrizioneAlbo = ddlAlbo.SelectedItem.Text;
                else
                    row.DescrizioneAlbo = "";

                row.NumeroAlbo = txtNumeroAlbo.Text;
                row.ProvinciaAlbo = acProvinciaAlbo.Value;
            }
            else
            {
                row.IdAlbo = String.Empty;
                row.DescrizioneAlbo = String.Empty;
                row.NumeroAlbo = String.Empty;
                row.ProvinciaAlbo = String.Empty;
            }

            row.DescrSoggetto = TipoSoggetto.SelectedItem.Text;
            row.DescrizioneTipoSoggetto = txtDescrizioneEstesa.Text;

            AcceptEdit(AnagraficaDomanda.FromAnagrafeRow(row));
        }

        public bool CittadinanzaVisible
        {
            set { this.CodiceCittadinanza.Visible = value; }
            get { return this.CodiceCittadinanza.Visible; }
        }

        public bool CittadinanzaObbligatoria
        {
            set { this.CodiceCittadinanza.Required = value; }
            get { return this.CodiceCittadinanza.Required; }
        }

        public bool TitoloVisibile
        {
            set { this.TITOLO.Visible = value; }
            get { return this.TITOLO.Visible; }
        }

        public bool TitoloObbligatorio
        {
            set { this.TITOLO.Required = value; }
            get { return this.TITOLO.Required; }
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

        public bool ResidenzaVisible
        {
            set { SetVs("ResidenzaVisible", value); }
            get { return GetVs("ResidenzaVisible", true); }
        }

        public bool ResidenzaObbligatoria
        {
            set
            {
                SetVs("ResidenzaObbligatoria", value);

                this.acComuneResidenza.Required = value;
                this.Indirizzo.Required = value;
                // this.Citta.Required = value;
                this.Cap.Required = value;
            }
            get { return GetVs("ResidenzaObbligatoria", false); }
        }

        public bool TelefonoVisible
        {
            set { this.Telefono.Visible = value; }
            get { return this.Telefono.Visible; }
        }

        public bool TelefonoObbligatorio
        {
            set { this.Telefono.Required = value; }
            get { return this.Telefono.Required; }
        }

        public bool CellulareVisible
        {
            set { this.TelefonoCellulare.Visible = value; }
            get { return this.TelefonoCellulare.Visible; }
        }

        public bool CellulareObbligatorio
        {
            set { this.TelefonoCellulare.Required = value; }
            get { return this.TelefonoCellulare.Required; }
        }

        public bool FaxVisible
        {
            set { this.Fax.Visible = value; }
            get { return this.Fax.Visible; }
        }

        public bool FaxObbligatorio
        {
            set { this.Fax.Required = value; }
            get { return this.Fax.Required; }
        }

        public bool EmailVisible
        {
            set { this.email.Visible = value; }
            get { return this.email.Visible; }
        }

        public bool EmailObbligatoria
        {
            set { this.email.Required = value; }
            get { return this.email.Required; }
        }

        public bool PecVisible
        {
            set { this.emailPec.Visible = value; }
            get { return this.emailPec.Visible; }
        }

        public bool PecObbligatoria
        {
            set { this.emailPec.Required = value; }
            get { return this.emailPec.Required; }
        }

    }
}