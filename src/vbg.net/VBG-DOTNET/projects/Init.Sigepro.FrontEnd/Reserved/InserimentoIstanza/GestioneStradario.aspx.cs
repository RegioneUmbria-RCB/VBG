using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni;
using Init.Sigepro.FrontEnd.WebControls.FormControls;
using Init.SIGePro.Manager.DTO.StradarioComune;
using Ninject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class GestioneStradario : IstanzeStepPage
    {
        private static class Constants
        {
            public const int IdColonnaCivico = 1;
            public const int IdColonnaEsponente = 2;
            public const int IdColonnaColore = 3;
            public const int IdColonnaScala = 4;
            public const int IdColonnaPiano = 5;
            public const int IdColonnaInterno = 6;
            public const int IdColonnaEsponenteInterno = 7;
            public const int IdColonnaFabbricato = 8;
            public const int IdColonnaKm = 9;
        }

        [Inject]
        protected LocalizzazioniService _localizzazioniService { get; set; }

        [Inject]
        protected CivicoValidoSpecification _civicoValidoSpecification { get; set; }

        [Inject]
        protected EsponenteValidoSpecification _esponenteValidoSpecification { get; set; }

        [Inject]
        protected IConfigurazione<ParametriLocalizzazioni> _configurazione { get; set; }

        protected bool CivicoNumerico { get { return this._configurazione.Parametri.UsaCiviciNumerici; } }

        protected bool EsponenteNumerico { get { return this._configurazione.Parametri.UsaEsponentiNumerici; } }

        #region dati letti dai parametri del workflow
        public bool CivicoVisibile
        {
            get { object o = this.ViewState["CivicoVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["CivicoVisibile"] = value; }
        }

        public string CivicoEtichetta
        {
            get { object o = this.ViewState["CivicoEtichetta"]; return o == null ? "Civico" : o.ToString(); }
            set { this.ViewState["CivicoEtichetta"] = value; }
        }

        public bool CivicoObbligatorio
        {
            get { object o = this.ViewState["CivicoObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["CivicoObbligatorio"] = value; }
        }
        //---------------------------------------

        public bool EsponenteVisibile
        {
            get { object o = this.ViewState["EsponenteVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["EsponenteVisibile"] = value; }
        }

        public string EsponenteEtichetta
        {
            get { object o = this.ViewState["EsponenteEtichetta"]; return o == null ? "Esponente" : o.ToString(); }
            set { this.ViewState["EsponenteEtichetta"] = value; }
        }

        public bool EsponenteObbligatorio
        {
            get { object o = this.ViewState["EsponenteObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["EsponenteObbligatorio"] = value; }
        }

        //-------------------------------------------

        public bool ColoreVisibile
        {
            get { object o = this.ViewState["ColoreVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ColoreVisibile"] = value; }
        }

        public string ColoreEtichetta
        {
            get { object o = this.ViewState["ColoreEtichetta"]; return o == null ? "Colore" : o.ToString(); }
            set { this.ViewState["ColoreEtichetta"] = value; }
        }

        public bool ColoreObbligatorio
        {
            get { object o = this.ViewState["ColoreObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["ColoreObbligatorio"] = value; }
        }

        //-------------------------------------------------

        public bool ScalaVisibile
        {
            get { object o = this.ViewState["ScalaVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["ScalaVisibile"] = value; }
        }

        public string ScalaEtichetta
        {
            get { object o = this.ViewState["ScalaEtichetta"]; return o == null ? "Scala" : o.ToString(); }
            set { this.ViewState["ScalaEtichetta"] = value; }
        }

        public bool ScalaObbligatorio
        {
            get { object o = this.ViewState["ScalaObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["ScalaObbligatorio"] = value; }
        }

        //--------------------------------------------------

        public bool PianoVisibile
        {
            get { object o = this.ViewState["PianoVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["PianoVisibile"] = value; }
        }

        public string PianoEtichetta
        {
            get { object o = this.ViewState["PianoEtichetta"]; return o == null ? "Piano" : o.ToString(); }
            set { this.ViewState["PianoEtichetta"] = value; }
        }

        public bool PianoObbligatorio
        {
            get { object o = this.ViewState["PianoObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["PianoObbligatorio"] = value; }
        }

        //--------------------------------------------------

        public bool InternoVisibile
        {
            get { object o = this.ViewState["InternoVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["InternoVisibile"] = value; }
        }

        public string InternoEtichetta
        {
            get { object o = this.ViewState["InternoEtichetta"]; return o == null ? "Interno" : o.ToString(); }
            set { this.ViewState["InternoEtichetta"] = value; }
        }

        public bool InternoObbligatorio
        {
            get { object o = this.ViewState["InternoObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["InternoObbligatorio"] = value; }
        }

        //--------------------------------------------------

        public bool EsponenteInternoVisibile
        {
            get { object o = this.ViewState["EsponenteInternoVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["EsponenteInternoVisibile"] = value; }
        }

        public string EsponenteInternoEtichetta
        {
            get { object o = this.ViewState["EsponenteInternoEtichetta"]; return o == null ? "EsponenteInterno" : o.ToString(); }
            set { this.ViewState["EsponenteInternoEtichetta"] = value; }
        }

        public bool EsponenteInternoObbligatorio
        {
            get { object o = this.ViewState["EsponenteInternoObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["EsponenteInternoObbligatorio"] = value; }
        }

        //--------------------------------------------------


        public bool FabbricatoVisibile
        {
            get { object o = this.ViewState["FabbricatoVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["FabbricatoVisibile"] = value; }
        }

        public string FabbricatoEtichetta
        {
            get { object o = this.ViewState["FabbricatoEtichetta"]; return o == null ? "Fabbricato" : o.ToString(); }
            set { this.ViewState["FabbricatoEtichetta"] = value; }
        }

        public bool FabbricatoObbligatorio
        {
            get { object o = this.ViewState["FabbricatoObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["FabbricatoObbligatorio"] = value; }
        }

        //--------------------------------------------------

        public bool KmVisibile
        {
            get { object o = this.ViewState["KmVisibile"]; return o == null ? true : (bool)o; }
            set { this.ViewState["KmVisibile"] = value; }
        }

        public string KmEtichetta
        {
            get { object o = this.ViewState["KmEtichetta"]; return o == null ? "Km" : o.ToString(); }
            set { this.ViewState["KmEtichetta"] = value; }
        }

        public bool KmObbligatorio
        {
            get { object o = this.ViewState["KmObbligatorio"]; return o == null ? false : (bool)o; }
            set { this.ViewState["KmObbligatorio"] = value; }
        }


        #endregion

        #region gestione della validazione e delle etichette dei campi di editing
        private IEnumerable<ConfigurazioneCampiStradario> _configurazioneCampi;


        protected class ConfigurazioneCampiStradario
        {
            private readonly IBootstrapFormControl _controlloInput;
            private readonly DataControlField _colonna;
            private readonly bool _obbligatorio;
            private readonly bool _visibile;

            public string Etichetta { get; }

            public ConfigurazioneCampiStradario(IBootstrapFormControl controlloInput, DataControlField colonna, bool obbligatorio, string etichetta, bool visibile)
            {
                this._controlloInput = controlloInput;
                this._colonna = colonna;
                this._obbligatorio = obbligatorio;
                this.Etichetta = etichetta;
                this._visibile = visibile;
            }

            public void ApplicaCriteriDiVisibilita()
            {
                this._controlloInput.Visible = this._colonna.Visible = this._visibile;
                this._controlloInput.Label = this._colonna.HeaderText = this.Etichetta;

                if (this._obbligatorio)
                {
                    this._controlloInput.Required = true;
                }

            }

            public bool VerificaCompilazione()
            {
                if (!this._visibile || !this._obbligatorio)
                    return true;

                return !String.IsNullOrEmpty(this._controlloInput.Value);
            }
        }

        private void InizializzaConfigurazioneCampi()
        {
            this._configurazioneCampi = new List<ConfigurazioneCampiStradario>
            {
                new ConfigurazioneCampiStradario( this.txtCivico, this.dgStradario.Columns[ Constants.IdColonnaCivico], this.CivicoObbligatorio , this.CivicoEtichetta , this.CivicoVisibile ),
                new ConfigurazioneCampiStradario( this.txtEsponente, this.dgStradario.Columns[ Constants.IdColonnaEsponente], this.EsponenteObbligatorio , this.EsponenteEtichetta , this.EsponenteVisibile ),
                new ConfigurazioneCampiStradario( this.ddlColore, this.dgStradario.Columns[ Constants.IdColonnaColore], this.ColoreObbligatorio , this.ColoreEtichetta, this.ColoreVisibile),
                new ConfigurazioneCampiStradario( this.txtScala, this.dgStradario.Columns[ Constants.IdColonnaScala], this.ScalaObbligatorio , this.ScalaEtichetta, this.ScalaVisibile),
                new ConfigurazioneCampiStradario( this.txtPiano, this.dgStradario.Columns[ Constants.IdColonnaPiano], this.PianoObbligatorio , this.PianoEtichetta, this.PianoVisibile),
                new ConfigurazioneCampiStradario( this.txtInterno, this.dgStradario.Columns[ Constants.IdColonnaInterno], this.InternoObbligatorio , this.InternoEtichetta, this.InternoVisibile),
                new ConfigurazioneCampiStradario( this.txtEsponenteInterno, this.dgStradario.Columns[ Constants.IdColonnaEsponenteInterno], this.EsponenteInternoObbligatorio , this.EsponenteInternoEtichetta, this.EsponenteInternoVisibile),
                new ConfigurazioneCampiStradario( this.txtFabbricato, this.dgStradario.Columns[ Constants.IdColonnaFabbricato], this.FabbricatoObbligatorio , this.FabbricatoEtichetta, this.FabbricatoVisibile),
                new ConfigurazioneCampiStradario( this.txtKm, this.dgStradario.Columns[ Constants.IdColonnaKm], this.KmObbligatorio , this.KmEtichetta, this.KmVisibile),

            };
        }

        private void ApplicaCriteriVisibilita()
        {
            foreach (var campo in this._configurazioneCampi)
                campo.ApplicaCriteriDiVisibilita();
        }

        private bool VerificaCompilazione()
        {
            var errori = false;

            if (String.IsNullOrEmpty(this.Indirizzo.Text))
            {
                this.Errori.Add("Il campo \"Indirizzo\" è obbligatorio");
                errori = true;
            }

            foreach (var campo in this._configurazioneCampi)
            {
                if (!campo.VerificaCompilazione())
                {
                    errori = true;
                    this.Errori.Add(String.Format("Il campo \"{0}\" è obbligatorio", campo.Etichetta));
                }
            }

            return !errori;
        }


        #endregion

        public string CodiceComune
        {
            get { object o = this.ViewState["CodiceComune"]; return o == null ? String.Empty : (string)o; }
            set { this.ViewState["CodiceComune"] = value; }
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // il service si occupa del salvataggio dei dati
            this.Master.IgnoraSalvataggioDati = true;

            this.InizializzaConfigurazioneCampi();
            this.ApplicaCriteriVisibilita();


            if (!this.IsPostBack)
            {
                var listaColori = this.ReadFacade.Stradario.GetListaColori(this.IdComune).ToList();

                listaColori.Insert(0, new ColoreStradarioDto { CodiceColore = String.Empty, Colore = String.Empty });

                this.ddlColore.DataSource = listaColori;
                this.ddlColore.DataBind();

                this.DataBind();
            }

            this.CodiceComune = this.ReadFacade.Domanda.AltriDati.CodiceComune;


        }

        #region Ciclo della vita dello step

        public override bool CanEnterStep()
        {
            return true;
        }

        public override bool CanExitStep()
        {
            if (this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() == 0)
            {
                this.Errori.Add("Inserire almeno un indirizzo dello stradario");
                return false;
            }

            return true;
        }

        #endregion

        public override void DataBind()
        {
            this.multiView.ActiveViewIndex = 0;

            this.dgStradario.DataSource = this.ReadFacade.Domanda.Localizzazioni.Indirizzi;
            this.dgStradario.DataBind();

            this.Master.MostraPaginatoreSteps = true;
            this.Master.MostraBottoneAvanti = this.ReadFacade.Domanda.Localizzazioni.Indirizzi.Count() > 0;
        }

        /// <summary>
        /// Svuota tutti i controlli del panel di inserimento
        /// </summary>
        private void ClearDettaglio()
        {
            this.Indirizzo.Value = String.Empty;
            this.Indirizzo.Text = String.Empty;
            this.Note.Text = String.Empty;

            this.txtCivico.Value = String.Empty;
            this.txtEsponente.Value = String.Empty;
            this.ddlColore.SelectedIndex = 0;
            this.txtScala.Value = String.Empty;
            this.txtInterno.Value = String.Empty;
            this.txtEsponenteInterno.Value = String.Empty;
            this.txtPiano.Value = String.Empty;
            this.txtFabbricato.Value = String.Empty;
            this.txtKm.Value = String.Empty;

            this.dgIndirizzi.Visible = false;

        }

        protected void Edit()
        {
            this.multiView.ActiveViewIndex = 1;
            this.Master.MostraPaginatoreSteps = false;
        }

        protected void EditNew()
        {
            this.ClearDettaglio();
            this.Edit();
        }

        /// <summary>
        /// Delegate della selezione di un elemento della dataGrid dei risultati della ricerca nello stradario
        /// </summary>
        public void dgIndirizzi_SelectedIndexChanged(object sender, EventArgs e)
        {
            var codiceStradario = Convert.ToInt32(this.dgIndirizzi.DataKeys[this.dgIndirizzi.SelectedIndex].Value);

            this.InserisciVoceStradario(this.ReadFacade.Stradario.GetByCodiceStradario(this.IdComune, codiceStradario));
        }

        /// <summary>
        /// Aggiunge la voce di stradario trovata alla lista degli indirizzi dell'istanza
        /// </summary>
        /// <param name="stradarioTrovato">Voce dello stradario trovata o null se si deve effettuare una ricerca per match parziale</param>
        private void InserisciVoceStradario(StradarioEstesoDto stradarioTrovato)
        {
            if (stradarioTrovato != null)
            {
                var civicoValido = this._civicoValidoSpecification.IsSatisfiedBy(this.txtCivico.Value);
                var esponenteValido = this._esponenteValidoSpecification.IsSatisfiedBy(this.txtEsponente.Value);

                if (!civicoValido)
                {
                    this.Errori.Add("Il civico immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!esponenteValido)
                {
                    this.Errori.Add("L'esponente immesso non è valido. Il campo può contenere solamente valori numerici");
                }

                if (!(civicoValido && esponenteValido))
                {
                    return;
                }

                var nomeVia = stradarioTrovato.Prefisso + " " + stradarioTrovato.Descrizione;

                if (!String.IsNullOrEmpty(stradarioTrovato.LocFraz))
                    nomeVia += " (" + stradarioTrovato.LocFraz + ")";

                var nuovoStradario = new NuovaLocalizzazione(Convert.ToInt32(stradarioTrovato.CodiceStradario), nomeVia, this.txtCivico.Value)
                {
                    Colore = this.ddlColore.Value,
                    Esponente = this.txtEsponente.Value,
                    EsponenteInterno = this.txtEsponenteInterno.Value,
                    Interno = this.txtInterno.Value,
                    Note = this.Note.Text,
                    Scala = this.txtScala.Value,
                    Piano = this.txtPiano.Value,
                    Fabbricato = this.txtFabbricato.Value,
                    Km = this.txtKm.Value
                };

                this._localizzazioniService.AggiungiLocalizzazione(this.IdDomanda, nuovoStradario);

                this.DataBind();

                return;
            }

            var listaIndirizzi = this.ReadFacade.Stradario.GetByMatchParziale(this.IdComune, this.CodiceComune, String.Empty, this.Indirizzo.Text);

            if (listaIndirizzi.Count > 0)
            {
                this.Errori.Add("Indirizzo non trovato. Sono però stati trovati i seguenti record simili");

                this.dgIndirizzi.DataSource = listaIndirizzi;
                this.dgIndirizzi.DataBind();

                this.dgIndirizzi.Visible = true;
            }
            else
            {
                this.Errori.Add("Indirizzo non trovato. Verificare i dati immessi");
                this.Indirizzo.Text = String.Empty;
            }

        }

        /// <summary>
        /// Handler dell'evento click sul bottone di eliminazione riga della datagrid di riepilogo
        /// </summary>
        public void dgStradario_DeleteCommand(object source, GridViewDeleteEventArgs e)
        {
            int key = Convert.ToInt32(this.dgStradario.DataKeys[e.RowIndex].Value);

            this._localizzazioniService.EliminaLocalizzazione(this.IdDomanda, key);

            this.DataBind();
        }


        public void cmdAddNew_Click(object sender, EventArgs e)
        {
            this.EditNew();
        }
        public void cmdConfirm_Click(object sender, EventArgs e)
        {
            this.dgIndirizzi.Visible = false;

            if (!this.VerificaCompilazione())
                return;

            StradarioEstesoDto stradarioTrovato = this.CodiceStradarioTrovato() ? this.TrovaStradarioDaCodiceStradario() : this.TrovaStradarioDaIndirizzo();

            this.InserisciVoceStradario(stradarioTrovato);
        }

        private StradarioEstesoDto TrovaStradarioDaCodiceStradario()
        {
            return this.ReadFacade.Stradario.GetByCodiceStradario(this.IdComune, Convert.ToInt32(this.Indirizzo.Value));
        }

        private StradarioEstesoDto TrovaStradarioDaIndirizzo()
        {
            return this.ReadFacade.Stradario.GetByIndirizzo(this.IdComune, this.CodiceComune, this.Indirizzo.Text);
        }

        private bool CodiceStradarioTrovato()
        {
            return !String.IsNullOrEmpty(this.Indirizzo.Value);
        }
        public void cmdCancel_Click(object sender, EventArgs e)
        {
            this.DataBind();
        }
    }
}
