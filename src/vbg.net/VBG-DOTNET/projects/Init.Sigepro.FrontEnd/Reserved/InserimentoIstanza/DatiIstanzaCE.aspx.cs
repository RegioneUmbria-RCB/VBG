using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza
{
    public partial class DatiIstanzaCE : IstanzeStepPage
    {
        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; }

        public bool OggettoEditabile
        {
            get { object o = this.ViewState["OggettoEditabile"]; return o == null ? true : (bool)o; }
            set
            {
                this.ViewState["OggettoEditabile"] = value;

                this.Oggetto.ReadOnly = !value;
                this.Oggetto.Required = value;
            }
        }

        public int LimiteCaratteri
        {
            get { object o = this.ViewState["LimiteCaratteri"]; return o == null ? 2000 : Math.Min((int)o, 2000); }
            set { this.ViewState["LimiteCaratteri"] = value; }
        }

        public bool NascondiNote
        {
            get { return !this.Note.Visible; }
            set { this.Note.Visible = !value; }
        }



        protected void Page_Load(object sender, EventArgs e)
        {
            this.Master.IgnoraSalvataggioDati = true;

            if (!this.IsPostBack)
                this.DataBind();
        }

        public override void OnBeforeExitStep()
        {
            var note = this.NascondiNote ? String.Empty : this.Note.Text;

            this.DatiDomandaService.ImpostaDatiIstanza(this.IdDomanda, note, this.Oggetto.Text, String.Empty);
        }

        public override bool CanExitStep()
        {
            if (String.IsNullOrEmpty(this.ReadFacade.Domanda.AltriDati.DescrizioneLavori))
            {
                this.Errori.Add("Compilare tutti i campi obbligatori");
                return false;
            }

            if (this.LimiteCaratteri > 0 && this.ReadFacade.Domanda.AltriDati.DescrizioneLavori.Length > this.LimiteCaratteri)
            {
                this.Errori.Add("La lunghezza del testo del campo Oggetto non può superare " + this.LimiteCaratteri + " caratteri");
                return false;
            }

            return true;
        }

        public override void DataBind()
        {
            this.Note.Text = this.ReadFacade.Domanda.AltriDati.Note;
            this.Oggetto.Text = this.ReadFacade.Domanda.AltriDati.DescrizioneLavori;

            this.Oggetto.HelpText = String.Empty;

            if (this.LimiteCaratteri > 0)
            {
                this.Oggetto.HelpText = $"<div id='caratteri-rimanenti'><span></span> / {this.LimiteCaratteri}</div>";
            }
        }
    }
}
