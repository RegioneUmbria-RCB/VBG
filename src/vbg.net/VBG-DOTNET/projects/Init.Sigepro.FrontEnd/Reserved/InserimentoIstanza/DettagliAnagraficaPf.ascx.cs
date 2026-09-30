using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTabelleDiBase;
using Init.Sigepro.FrontEnd.AppLogic.GestioneTipiSoggetto;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.Utils;
using Init.Sigepro.FrontEnd.Infrastructure.IOC;
using Init.SIGePro.Manager.DTO.Comuni;
using Ninject;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web.UI.WebControls;

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
        public delegate CittadinanzaCompatto OnGetDatiCittadinanza(string idCittadinanza);
        public delegate void OnAcceptEdit(AnagraficaDomanda row);


        public event EventHandler CancelEdit;
        public event OnAcceptEdit AcceptEdit;
        public event OnGetAnagrafeRow GetAnagrafeRow;
        public event OnGetTipiSoggettoPfDelegate GetTipiSoggetto;
        public event OnGetTipoSoggetto GetTipoSoggetto;
        public event OnGetDatiComune GetDatiComune;
        public event OnGetDatiProvincia GetDatiProvincia;
        public event OnGetDatiCittadinanza GetDatiCittadinanza;

        public event GestioneAnagraficheStepPage.ErrorDelegate ErroreInserimento;

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
            get { return this._aliasSoftwareResolver.AliasComune; }
        }

        protected string Software
        {
            get { return this._aliasSoftwareResolver.Software; }
        }

        public bool PermettiModificaDatiAnagrafici
        {
            get { object o = this.ViewState["PermettiModificaDatiAnagrafici"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiModificaDatiAnagrafici"] = value; }
        }

        public bool PermettiModificaTipoSoggetto
        {
            get { object o = this.ViewState["PermettiModificaTipoSoggetto"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PermettiModificaTipoSoggetto"] = value; }
        }

        public int? CodiceIntervento
        {
            set { this.TipoSoggetto.CodiceIntervento = value; }
        }

        public string LimitaDatiAlbo
        {
            get { object o = this.ViewState["LimitaDatiAlbo"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["LimitaDatiAlbo"] = value; this.BindElenchiProfessionali(); }
        }

        public string TitoloBloccoIndirizzoCorrispondenza
        {
            get { object o = this.ViewState["TitoloBloccoIndirizzoCorrispondenza"]; return o == null ? "Indirizzo per la corrispondenza" : (string)o; }
            set { this.ViewState["TitoloBloccoIndirizzoCorrispondenza"] = value; }
        }


        protected override void OnLoad(EventArgs e)
        {
            // Deve sempre essere bindato altrimenti gli attributi non persistono
            this.BindElenchiProfessionali();
        }


        protected override void OnInit(EventArgs e)
        {


            if (!this.Page.IsPostBack)
            {
                this.BindCittadinanze();
                this.BindTitoli();
            }

            base.OnInit(e);
        }


        private void BindElenchiProfessionali()
        {
            var oldSelectedValue = this.ddlAlbo.SelectedValue;

            this.ddlAlbo.Items.Clear();

            this.ddlAlbo.Items.Add(new ListItem(String.Empty, String.Empty));

            var limitaDatiAlbo = new int[0];

            if (!String.IsNullOrEmpty(this.LimitaDatiAlbo))
            {
                limitaDatiAlbo = this.LimitaDatiAlbo.Split(',').Select(x => Convert.ToInt32(x.Trim())).ToArray();
            }

            var dati = this._elenchiProfessionaliRepository.GetList()
                        .Where(x =>
                        {
                            return limitaDatiAlbo.Length == 0 || limitaDatiAlbo.Contains(x.EpId.Value);
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
            var titoli = this._titoliRepository.GetList();

            this.TITOLO.Items.Clear();
            this.TITOLO.Items.Add(new ListItem(string.Empty, String.Empty));

            foreach (var t in titoli)
            {
                this.TITOLO.Items.Add(new ListItem(t.Titolo, t.CodiceTitolo));
            }
        }

        public DettagliAnagraficaPf()
        {
            FoKernelContainer.Inject(this);
        }

        public override void DataBind()
        {
            this.acComuneResidenza.Required = this.ResidenzaObbligatoria;
            this.Indirizzo.Required = this.ResidenzaObbligatoria;
            //this.Citta.Required = this.ResidenzaObbligatoria;
            this.Cap.Required = this.ResidenzaObbligatoria;

            if (!String.IsNullOrEmpty(this.MessaggioVerificaPec))
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


            this.NOMINATIVO.Text = AnagraficaBinder.SafeValue(this.DataSource, "NOMINATIVO");
            this.NOME.Text = AnagraficaBinder.SafeValue(this.DataSource, "NOME");

            this.TITOLO.DataBind();
            this.TITOLO.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "TITOLO");

            this.sesso.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "SESSO");

            this.CodiceCittadinanza.DataBind();
            this.CodiceCittadinanza.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "CODICECITTADINANZA");

            // Residenza
            string comuneResidenza = AnagraficaBinder.SafeValue(this.DataSource, "COMUNERESIDENZA");

            if (!String.IsNullOrEmpty(comuneResidenza))
            {
                var comuneresidenzaDto = this.GetDatiComune(comuneResidenza);

                if (comuneresidenzaDto != null)
                {
                    this.acComuneResidenza.Value = comuneresidenzaDto.CodiceComune;
                    this.acComuneResidenza.Text = comuneresidenzaDto.Comune + " (" + comuneresidenzaDto.SiglaProvincia + ")";
                }
            }
            else
            {
                this.acComuneResidenza.Value = String.Empty;
                this.acComuneResidenza.Text = String.Empty;
            }


            this.Indirizzo.Text = AnagraficaBinder.SafeValue(this.DataSource, "INDIRIZZO");
            this.Citta.Text = AnagraficaBinder.SafeValue(this.DataSource, "CITTA");
            this.Cap.Text = AnagraficaBinder.SafeValue(this.DataSource, "CAP");


            // Corrispondenza
            string comuneCorrispondenza = AnagraficaBinder.SafeValue(this.DataSource, "COMUNECORRISPONDENZA");

            if (!String.IsNullOrEmpty(comuneCorrispondenza))
            {
                var comuneCorrispondenzaDto = GetDatiComune(comuneCorrispondenza);

                if (comuneCorrispondenzaDto != null)
                {
                    this.acComuneCorrispondenza.Value = comuneCorrispondenza;
                    this.acComuneCorrispondenza.Text = comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")";
                }
            }
            else
            {
                this.acComuneCorrispondenza.Value = String.Empty;
                this.acComuneCorrispondenza.Text = String.Empty;
            }

            this.IndirizzoCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "INDIRIZZOCORRISPONDENZA");
            this.CittaCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "CITTACORRISPONDENZA");
            this.CapCorrispondenza.Text = AnagraficaBinder.SafeValue(this.DataSource, "CAPCORRISPONDENZA");
            this.CodiceFiscale.Text = AnagraficaBinder.SafeValue(this.DataSource, "CODICEFISCALE");


            string comuneNascita = AnagraficaBinder.SafeValue(this.DataSource, "CODCOMNASCITA");

            // comune nascita
            if (!String.IsNullOrEmpty(comuneNascita))
            {
                var comuneNascitaDto = GetDatiComune(comuneNascita);

                if (comuneNascitaDto != null)
                {
                    this.acComuneNascita.Value = comuneNascita;
                    this.acComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                }
                else
                {
                    this.acComuneNascita.Value = this.acComuneNascita.Text = String.Empty;
                }
            }
            else
            {
                // provo a ricavare il comune dal codice fiscale
                this.acComuneNascita.Value = this.acComuneNascita.Text = String.Empty;

                if (!String.IsNullOrEmpty(this.CodiceFiscale.Text) && this.CodiceFiscale.Text.Length == 16)
                {
                    var codComune = this.CodiceFiscale.Text.Substring(11, 4);

                    var comuneNascitaDto = GetDatiComune(codComune);

                    if (comuneNascitaDto != null)
                    {
                        this.acComuneNascita.Value = comuneNascitaDto.CodiceComune;
                        this.acComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                    }
                }
            }

            this.Telefono.Text = AnagraficaBinder.SafeValue(this.DataSource, "TELEFONO");
            this.TelefonoCellulare.Text = AnagraficaBinder.SafeValue(this.DataSource, "TELEFONOCELLULARE");
            this.email.Text = AnagraficaBinder.SafeValue(this.DataSource, "EMAIL");
            this.emailPec.Text = AnagraficaBinder.SafeValue(this.DataSource, "Pec");

            this.TipoSoggetto.DataBind();
            this.TipoSoggetto.SelectedValue = AnagraficaBinder.SafeValue(this.DataSource, "TIPOSOGGETTO");

            if (this.TipoSoggetto.SelectedValue == String.Empty && this.TipoSoggetto.Items.Count == 2)
            {
                this.TipoSoggetto.SelectedValue = this.TipoSoggetto.Items[1].Value;
            }
            this.TipoSoggetto.Enabled = this.PermettiModificaTipoSoggetto;

            this.DataNascita.DateValue = (this.DataSource.IsDATANASCITANull()) ? (DateTime?)null : this.DataSource.DATANASCITA;

            this.ANAGRAFE_PK.Value = AnagraficaBinder.SafeValue(this.DataSource, "ANAGRAFE_PK");

            this.InizializzaDatiAlbo(
                AnagraficaBinder.SafeValue(this.DataSource, "IdAlbo"),
                AnagraficaBinder.SafeValue(this.DataSource, "NumeroAlbo"),
                AnagraficaBinder.SafeValue(this.DataSource, "ProvinciaAlbo")
            );

            this.txtDescrizioneEstesa.Text = AnagraficaBinder.SafeValue(this.DataSource, "DescrizioneTipoSoggetto");

            this.NOME.ReadOnly = false;
            this.NOMINATIVO.ReadOnly = false;
            this.DataNascita.ReadOnly = false;
            this.acComuneNascita.ReadOnly = false;

            if (!this.PermettiModificaDatiAnagrafici)
            {
                this.NOME.ReadOnly = true;
                this.NOMINATIVO.ReadOnly = true;
                // DataNascita.ReadOnly = true;
                this.acComuneNascita.ReadOnly = true;
            }

            this.Page.Validate();

            // Altrimenti i dati dei validatori non vengono bindati :P
            // base.DataBind();
        }

        private void InizializzaDatiAlbo(string idAlbo, string numeroAlbo, string provinciaAlbo)
        {
            this.ddlAlbo.DataBind();

            var valoriAttualiAlbo = this.ddlAlbo.Inner.Items.Cast<ListItem>().Select(x => x.Value);

            this.acProvinciaAlbo.Value = this.acProvinciaAlbo.Text = String.Empty;
            this.txtNumeroAlbo.Text = "";
            this.ddlAlbo.SelectedValue = null;

            if (valoriAttualiAlbo.Contains(idAlbo))
            {
                this.ddlAlbo.SelectedValue = idAlbo;
                this.txtNumeroAlbo.Text = numeroAlbo;

                this.acProvinciaAlbo.Value = this.acProvinciaAlbo.Text = String.Empty;

                if (!String.IsNullOrEmpty(provinciaAlbo))
                {
                    var provinciaDto = GetDatiProvincia(provinciaAlbo);

                    if (provinciaDto != null)
                    {
                        this.acProvinciaAlbo.Value = provinciaDto.SiglaProvincia;
                        this.acProvinciaAlbo.Text = provinciaDto.Provincia;
                    }
                    else
                    {
                        this.acProvinciaAlbo.Value = provinciaAlbo;
                        this.acProvinciaAlbo.Text = provinciaAlbo;
                    }
                }
            }


        }

        protected void cmdCopiaResidenza_Click(object sender, EventArgs e)
        {
            var codComuneResidenza = this.acComuneResidenza.Value;
            var valComuneResidenza = this.acComuneResidenza.Text;

            this.acComuneCorrispondenza.Value = codComuneResidenza;
            this.acComuneCorrispondenza.Text = valComuneResidenza;

            this.IndirizzoCorrispondenza.Text = this.Indirizzo.Text;
            this.CittaCorrispondenza.Text = this.Citta.Text;
            this.CapCorrispondenza.Text = this.Cap.Text;
        }

        protected void cmdCancel_Click(object sender, EventArgs e)
        {
            if (CancelEdit != null)
                CancelEdit(this, EventArgs.Empty);
        }

        protected void cmdConfirm_Click(object sender, EventArgs e)
        {
            this.Page.Validate();
            if (!this.Page.IsValid) return;

            if (String.IsNullOrEmpty(this.TipoSoggetto.SelectedValue))
            {
                ErroreInserimento("Specificare il tipo soggetto");
                return;
            }

            if (String.IsNullOrEmpty(this.NOMINATIVO.Text))
            {
                ErroreInserimento("Specificare il cognome");
                return;
            }

            if (String.IsNullOrEmpty(this.NOME.Text))
            {
                ErroreInserimento("Specificare il nome");
                return;
            }

            if (String.IsNullOrEmpty(this.sesso.SelectedValue))
            {
                ErroreInserimento("Specificare il sesso");
                return;
            }

            if (!this.DataNascita.DateValue.HasValue)
            {
                ErroreInserimento("Specificare la data di nascita");
                return;
            }

            if (String.IsNullOrEmpty(this.acComuneNascita.Value))
            {
                ErroreInserimento("Specificare il comune di nascita");
                return;
            }

            if (String.IsNullOrEmpty(this.CodiceFiscale.Text))
            {
                ErroreInserimento("Specificare il codice fiscale");
                return;
            }

            if (this.CittadinanzaObbligatoria && String.IsNullOrEmpty(this.CodiceCittadinanza.SelectedValue))
            {
                ErroreInserimento("Specificare la cittadinanza");
                return;
            }

            if (this.ResidenzaObbligatoria)
            {
                if (String.IsNullOrEmpty(this.acComuneResidenza.Value))
                {
                    ErroreInserimento("Specificare il comune di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.Indirizzo.Text))
                {
                    ErroreInserimento("Specificare l'indirizzo di residenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.Cap.Text))
                {
                    ErroreInserimento("Specificare il cap di residenza");
                    return;
                }
            }

            if (this.TelefonoObbligatorio && String.IsNullOrEmpty(this.Telefono.Text))
            {
                ErroreInserimento("Specificare il numero di telefono");
                return;
            }

            if (this.CellulareObbligatorio && String.IsNullOrEmpty(this.TelefonoCellulare.Text))
            {
                ErroreInserimento("Specificare il numero di cellulare");
                return;
            }

            if (this.EmailObbligatoria && String.IsNullOrEmpty(this.email.Text))
            {
                ErroreInserimento("Specificare l'indirizzo email");
                return;
            }

            if (this.PecObbligatoria && String.IsNullOrEmpty(this.emailPec.Text))
            {
                ErroreInserimento("Specificare l'indirizzo PEC");
                return;
            }

            if (this.EmailoPecObbligatori && (String.IsNullOrEmpty(this.emailPec.Text) && String.IsNullOrEmpty(this.email.Text)))
            {
                ErroreInserimento("Specificare l'indirizzo E-Mail o PEC");
                return;
            }

            if (this.TelefonooCellulareObbligatori && (String.IsNullOrEmpty(this.Telefono.Text) && String.IsNullOrEmpty(this.TelefonoCellulare.Text)))
            {
                ErroreInserimento("Specificare il numero di telefono o cellulare");
                return;
            }

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

            int idAnagrafica = -1;

            int.TryParse(this.ANAGRAFE_PK.Value, out idAnagrafica);

            var row = GetAnagrafeRow(idAnagrafica).ToAnagrafeRow();

            var datiComuneNascita = GetDatiComune(this.acComuneNascita.Value);

            if (datiComuneNascita == null)
            {
                ErroreInserimento("Il comune di nascita specificato non è valido");
                return;
            }

            row.TIPOANAGRAFE = "F";

            if (!String.IsNullOrEmpty(this.TITOLO.SelectedValue))
            {
                row.TITOLO = this.TITOLO.SelectedValue;
            }

            row.NOMINATIVO = this.NOMINATIVO.Text;
            row.NOME = this.NOME.Text;

            row.SESSO = this.sesso.SelectedValue;


            row.PROVINCIANASCITA = datiComuneNascita.SiglaProvincia;

            row.INDIRIZZO = this.Indirizzo.Text;
            row.CITTA = this.Citta.Text;
            row.CAP = this.Cap.Text;

            row.INDIRIZZOCORRISPONDENZA = this.IndirizzoCorrispondenza.Text;
            row.CITTACORRISPONDENZA = this.CittaCorrispondenza.Text;
            row.CAPCORRISPONDENZA = this.CapCorrispondenza.Text;

            row.CODICEFISCALE = this.CodiceFiscale.Text.ToUpper();
            row.TELEFONO = this.Telefono.Text;
            row.TELEFONOCELLULARE = this.TelefonoCellulare.Text;
            row.EMAIL = this.email.Text;
            row.Pec = this.emailPec.Text;

            row.DATANASCITA = this.DataNascita.DateValue.GetValueOrDefault(DateTime.MinValue);

            // Cittadinanza
            var datiCittadinanza = GetDatiCittadinanza(this.CodiceCittadinanza.SelectedValue);

            if (datiCittadinanza != null)
            {
                row.CODICECITTADINANZA = datiCittadinanza.Codice;
                row.IsCittadinoExtracomunitario = !datiCittadinanza.FlgPaeseComunitario;
            }

            // Residenza
            var datiComuneResidenza = GetDatiComune(this.acComuneResidenza.Value);

            if (datiComuneResidenza != null)
            {
                row.COMUNERESIDENZA = datiComuneResidenza.CodiceComune;
                row.PROVINCIA = datiComuneResidenza.SiglaProvincia;
            }

            if (!String.IsNullOrEmpty(this.acComuneCorrispondenza.Value))
            {
                datiComuneNascita = GetDatiComune(this.acComuneCorrispondenza.Value);

                if (datiComuneNascita != null)
                {
                    row.PROVINCIACORRISPONDENZA = datiComuneNascita.SiglaProvincia;
                    row.COMUNECORRISPONDENZA = datiComuneNascita.CodiceComune;
                }
            }

            row.CODCOMNASCITA = this.acComuneNascita.Value;

            row.TIPOSOGGETTO = Convert.ToInt32(this.TipoSoggetto.SelectedValue);

            var tipoSoggetto = GetTipoSoggetto(row.TIPOSOGGETTO.Value);

            if (tipoSoggetto != null && tipoSoggetto.RichiedeDatiAlbo)
            {
                if (String.IsNullOrEmpty(this.ddlAlbo.SelectedValue))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare l'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.txtNumeroAlbo.Text.Trim()))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare il numero di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                if (String.IsNullOrEmpty(this.acProvinciaAlbo.Value.Trim()))
                {
                    ErroreInserimento("Per il tipo soggetto selezionato è necessario specificare la provincia di iscrizione all'albo professionale di appartenenza");
                    return;
                }

                row.IdAlbo = this.ddlAlbo.SelectedValue;

                if (!String.IsNullOrEmpty(this.ddlAlbo.SelectedValue))
                    row.DescrizioneAlbo = this.ddlAlbo.SelectedItem.Text;
                else
                    row.DescrizioneAlbo = "";

                row.NumeroAlbo = this.txtNumeroAlbo.Text;
                row.ProvinciaAlbo = this.acProvinciaAlbo.Value;
            }
            else
            {
                row.IdAlbo = String.Empty;
                row.DescrizioneAlbo = String.Empty;
                row.NumeroAlbo = String.Empty;
                row.ProvinciaAlbo = String.Empty;
            }

            row.DescrSoggetto = this.TipoSoggetto.SelectedItem.Text;
            row.DescrizioneTipoSoggetto = this.txtDescrizioneEstesa.Text;

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
            set { this.SetVs("CorrispondenzaVisibile", value); }
            get { return this.GetVs("CorrispondenzaVisibile", true); }
        }

        public bool CorrispondenzaObbligatoria
        {
            set { this.SetVs("CorrispondenzaObbligatoria", value); }
            get { return this.GetVs("CorrispondenzaObbligatoria", false); }
        }

        public bool ResidenzaVisible
        {
            set { this.SetVs("ResidenzaVisible", value); }
            get { return this.GetVs("ResidenzaVisible", true); }
        }

        public bool ResidenzaObbligatoria
        {
            set { this.SetVs("ResidenzaObbligatoria", value); }
            get { return this.GetVs("ResidenzaObbligatoria", false); }
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

        public bool EmailoPecObbligatori
        {
            set { this.SetVs("EmailoPecObbligatori", value); }
            get { return this.GetVs("EmailoPecObbligatori", false); }
        }

        public bool TelefonooCellulareObbligatori
        {
            set { this.SetVs("TelefonooCellulareObbligatori", value); }
            get { return this.GetVs("TelefonooCellulareObbligatori", false); }
        }

        public bool CellulareObbligatorio
        {
            set { this.TelefonoCellulare.Required = value; }
            get { return this.TelefonoCellulare.Required; }
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

        public bool GestioneSoggettoUnico
        {
            set { this.cmdCancel.Visible = value; }
        }

    }
}