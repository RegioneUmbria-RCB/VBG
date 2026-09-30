using Init.Sigepro.FrontEnd.AppLogic.GestioneAnagrafiche;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using Init.Sigepro.FrontEnd.QsParameters;
using log4net;
using Ninject;
using System;
using System.Data;

namespace Init.Sigepro.FrontEnd.Reserved
{
    public partial class ModificaDatiUtente : ReservedBasePage
    {
        [Inject]
        public IAnagraficheService _anagrafeRepository { get; set; }
        [Inject]
        public IComuniService _comuniService { get; set; }

        private readonly ILog _log = LogManager.GetLogger(typeof(ModificaDatiUtente));



        protected void Page_Load(object sender, EventArgs e)
        {
            this.cmdCancel_Click(this, EventArgs.Empty);

            return;

            //if (!this.IsPostBack)
            //{
            //    this.InitCombo();
            //    this.DataBind();
            //}


        }

        private void InitCombo()
        {
        }

        public override void DataBind()
        {
            var DataSource = this.UserAuthenticationResult.DatiUtente;

            this.NOMINATIVO.Text = DataSource.Nominativo;
            this.NOME.Text = DataSource.Nome;

            this.TITOLO.DataBind();
            this.TITOLO.SelectedValue = DataSource.Titolo.ToString();
            this.sesso.SelectedValue = DataSource.Sesso;

            this.CodiceCittadinanza.DataBind();
            this.CodiceCittadinanza.SelectedValue = DataSource.Codicecittadinanza.ToString();

            var comuneResidenza = DataSource.Comuneresidenza;

            if (!String.IsNullOrEmpty(comuneResidenza))
            {
                var comuneresidenzaDto = this._comuniService.GetDatiComune(comuneResidenza);

                if (comuneresidenzaDto != null)
                {
                    this.hidComuneResidenza.Value = comuneresidenzaDto.CodiceComune;
                    this.txtComuneResidenza.Text = comuneresidenzaDto.Comune + " (" + comuneresidenzaDto.SiglaProvincia + ")";
                    this.txtComuneResidenza.ReadOnly = true;
                }
            }
            else
            {
                this.hidComuneResidenza.Value = String.Empty;
                this.txtComuneResidenza.Text = String.Empty;
                this.txtComuneResidenza.ReadOnly = false;
            }

            this.Indirizzo.Text = DataSource.Indirizzo;
            this.Citta.Text = DataSource.Citta;
            this.Cap.Text = DataSource.Cap;

            var comuneCorrispondenza = DataSource.Comunecorrispondenza;

            if (!String.IsNullOrEmpty(comuneCorrispondenza))
            {
                var comuneCorrispondenzaDto = this._comuniService.GetDatiComune(comuneCorrispondenza);

                if (comuneCorrispondenzaDto != null)
                {
                    this.hidComuneCorrispondenza.Value = comuneCorrispondenza;
                    this.txtComuneCorrispondenza.Text = comuneCorrispondenzaDto.Comune + " (" + comuneCorrispondenzaDto.SiglaProvincia + ")";
                    this.txtComuneCorrispondenza.ReadOnly = true;
                }
            }
            else
            {
                this.hidComuneCorrispondenza.Value = String.Empty;
                this.txtComuneCorrispondenza.Text = String.Empty;
                this.txtComuneCorrispondenza.ReadOnly = false;
            }


            this.IndirizzoCorrispondenza.Text = DataSource.Indirizzocorrispondenza;

            this.CittaCorrispondenza.Text = DataSource.Cittacorrispondenza;

            this.CapCorrispondenza.Text = DataSource.Capcorrispondenza;

            var comuneNascita = DataSource.Codcomnascita;

            // comune nascita
            if (!String.IsNullOrEmpty(comuneNascita))
            {
                var comuneNascitaDto = this._comuniService.GetDatiComune(comuneNascita);

                if (comuneNascitaDto != null)
                {
                    this.hidComuneNascita.Value = comuneNascita;
                    this.txtComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                    this.txtComuneNascita.ReadOnly = true;
                }
                else
                {
                    this.hidComuneNascita.Value = this.txtComuneNascita.Text = String.Empty;
                    this.txtComuneNascita.ReadOnly = false;
                }
            }
            else
            {
                // provo a ricavare il comune dal codice fiscale
                this.hidComuneNascita.Value = this.txtComuneNascita.Text = String.Empty;

                if (!String.IsNullOrEmpty(this.CodiceFiscale.Text) && this.CodiceFiscale.Text.Length == 16)
                {
                    var codComune = this.CodiceFiscale.Text.Substring(11, 4);

                    var comuneNascitaDto = this._comuniService.GetDatiComune(codComune);

                    if (comuneNascitaDto != null)
                    {
                        this.hidComuneNascita.Value = comuneNascitaDto.CodiceComune;
                        this.txtComuneNascita.Text = comuneNascitaDto.Comune + " (" + comuneNascitaDto.SiglaProvincia + ")";
                        this.txtComuneNascita.ReadOnly = false;
                    }
                }
            }

            this.CodiceFiscale.Text = DataSource.Codicefiscale;
            this.Telefono.Text = DataSource.Telefono;
            this.TelefonoCellulare.Text = DataSource.Telefonocellulare;
            this.Fax.Text = DataSource.Fax;
            this.email.Text = DataSource.Email;

            this.DataNascita.DateValue = (DataSource.Datanascita == DateTime.MinValue) ? (DateTime?)null : DataSource.Datanascita;

            this.Page.Validate();
        }

        private string CodiceProvinciaDaComune(string codiceComune)
        {
            var comune = this._comuniService.GetDatiComune(codiceComune);

            if (comune == null) return String.Empty;

            return comune.SiglaProvincia;
        }

        /// <summary>
        /// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
        /// </summary>
        /// <param name="dataRow">DataRow da cui prendere il dato</param>
        /// <param name="columnName">Nome della colonna che contiene il dato</param>
        /// <returns>Valore <see cref="string"/> contenuto nella colonna o String.Empty se DbNull</returns>
        protected string SafeValue(DataRow dataRow, string columnName)
        {
            return this.SafeValue(dataRow, columnName, String.Empty);
        }

        /// <summary>
        /// Ottiene il valore SAFE di una DataColumn di una DataRow dato che i dataset tipizzati hanno qualche problema con i dbNull...
        /// </summary>
        /// <param name="dataRow">DataRow da cui prendere il dato</param>
        /// <param name="columnName">Nome della colonna che contiene il dato</param>
        /// <param name="defaultValue">Valore di default da ritornare se DbNull</param>
        /// <returns>Valore <see cref="String"/> contenuto nella colonna o valore di default impostato se DbNull</returns>
        protected string SafeValue(DataRow dataRow, string columnName, string defaultValue)
        {
            if (dataRow[columnName] == DBNull.Value)
                return defaultValue;
            else
                return dataRow[columnName].ToString();
        }

        protected void backToHome(object sender, EventArgs e)
        {
            var newUrl = UrlBuilder.Url("~/Reserved/Default.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(newUrl);
        }

        protected void cmdBackToHome_Click(object sender, EventArgs e)
        {
            this.backToHome(this, EventArgs.Empty);
        }

        protected void cmdCopiaResidenza_Click(object sender, EventArgs e)
        {
            this.hidComuneCorrispondenza.Value = this.hidComuneResidenza.Value;
            this.txtComuneCorrispondenza.Text = this.txtComuneResidenza.Text;
            this.IndirizzoCorrispondenza.Text = this.Indirizzo.Text;
            this.CittaCorrispondenza.Text = this.Citta.Text;
            this.CapCorrispondenza.Text = this.Cap.Text;
        }

        protected void cmdCancel_Click(object sender, EventArgs e)
        {
            var newUrl = UrlBuilder.Url("~/Reserved/Default.aspx", x =>
            {
                x.Add(new QsAliasComune(this.IdComune));
                x.Add(new QsSoftware(this.Software));
            });

            this.Response.Redirect(newUrl);
        }

        protected void cmdConfirm_Click(object sender, EventArgs e)
        {
            var DataSource = this.UserAuthenticationResult.DatiUtente;

            var datiComuneNascita = this._comuniService.GetDatiComune(this.hidComuneNascita.Value);

            if (datiComuneNascita == null)
            {
                this.Errori.Add("Il comune di nascita specificato non è valido");
                return;
            }

            var datiComuneResidenza = this._comuniService.GetDatiComune(this.hidComuneResidenza.Value);

            if (datiComuneResidenza == null)
            {
                this.Errori.Add("Il comune di residenza specificato non è valido");
                return;
            }

            DataSource.Nominativo = this.NOMINATIVO.Text;
            DataSource.Nome = this.NOME.Text;
            DataSource.Titolo = Convert.ToInt32(this.TITOLO.SelectedValue);
            DataSource.Sesso = this.sesso.SelectedValue;
            DataSource.Codicecittadinanza = Convert.ToInt32(this.CodiceCittadinanza.SelectedValue);

            DataSource.Provincia = datiComuneResidenza.SiglaProvincia;
            DataSource.Comuneresidenza = datiComuneResidenza.CodiceComune;

            DataSource.Indirizzo = this.Indirizzo.Text;
            DataSource.Citta = this.Citta.Text;
            DataSource.Cap = this.Cap.Text;


            if (!String.IsNullOrEmpty(this.hidComuneCorrispondenza.Value))
            {
                var datiComuneCorrispondenza = this._comuniService.GetDatiComune(this.hidComuneCorrispondenza.Value);

                if (datiComuneCorrispondenza != null)
                {
                    DataSource.Provinciacorrispondenza = datiComuneCorrispondenza.SiglaProvincia;
                    DataSource.Comunecorrispondenza = datiComuneCorrispondenza.CodiceComune;
                }
            }

            DataSource.Indirizzocorrispondenza = this.IndirizzoCorrispondenza.Text;

            this.CittaCorrispondenza.Text = DataSource.Cittacorrispondenza;

            DataSource.Capcorrispondenza = this.CapCorrispondenza.Text;

            DataSource.Codcomnascita = datiComuneNascita.CodiceComune;

            DataSource.Codicefiscale = this.CodiceFiscale.Text;
            DataSource.Telefono = this.Telefono.Text;
            DataSource.Telefonocellulare = this.TelefonoCellulare.Text;
            DataSource.Fax = this.Fax.Text;
            DataSource.Email = this.email.Text;

            DataSource.Datanascita = this.DataNascita.DateValue.GetValueOrDefault(DateTime.MinValue);

            try
            {
                //SmtpMailSender.SendRegistrationMessage(IdComune, Software, "Area Riservata - Richiesta modifica dati anagrafici", DataSource);
                // _anagrafeRepository.ModificaDatianagrafici(IdComune, DataSource);
                this.multiView.ActiveViewIndex = 1;
            }
            catch (Exception ex)
            {
                this.Errori.Add("Errore durante l'invio dei dati");
                this._log.ErrorFormat("Errore durante la modifica dei dati dell'utente: {0}", ex.ToString());
            }
        }
    }
}
