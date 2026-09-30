using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Infrastructure.StepsDomanda.Attributi;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Modals;

namespace AreaRiservataCore.Pages.InserimentoIstanza.DatiIstanzaCE
{
    public partial class DatiIstanzaCE
    {
        [Inject]
        public DatiDomandaService DatiDomandaService { get; set; } = default!;

        #region dati letti dai parametri del workflow

        private bool _oggettoEditabile = true;
        [StepProperty]
        public bool OggettoEditabile
        {
            get { return _oggettoEditabile; }
            set
            {
                _oggettoEditabile = value;

                oggettoReadOnly = !value;
                oggettoRequired = value;
            }
        }

        private int? _limiteCaratteri = null;
        [StepProperty]
        public int LimiteCaratteri
        {
            get { return Math.Min(_limiteCaratteri == null ? 0 : _limiteCaratteri.Value, 2000); }
            set { _limiteCaratteri = value; }
        }

        [StepProperty]
        public bool NascondiNote
        {
            get { return !noteVisible; }
            set { noteVisible = !value; }
        }

        [StepProperty]
        public string TitoloSezione { get; set; } = "Dati della pratica";

        #endregion

        private bool oggettoReadOnly { get; set; } = false;
        private bool oggettoRequired { get; set; } = true;
        private bool noteVisible { get; set; } = true;

        private Model model = new();

        private string? _editingOggetto;
        private string? _editingNote;
        private string _titoloModal = "";
        private bool _showModalModifiche;

        public class Model
        {
            public string Oggetto { get; set; }
            public string Note { get; set; }
        }

        protected override void OnInitializeStep()
        {
            DataBind();
        }

        protected override void OnBeforeExitStep()
        {
            base.OnBeforeExitStep();

            var note = this.NascondiNote ? String.Empty : model.Note;

            this.DatiDomandaService.ImpostaDatiIstanza(this.IdDomanda.Value, note, model.Oggetto, String.Empty);
        }

        protected override bool CanExitStep()
        {
            MessageContainer.ClearErrors();

            if (String.IsNullOrEmpty(DomandaCorrente.AltriDati.DescrizioneLavori))
            {
                MessageContainer.AddError("Compilare tutti i campi obbligatori");
                return false;
            }

            if (this.LimiteCaratteri > 0 && DomandaCorrente.AltriDati.DescrizioneLavori.Length > this.LimiteCaratteri)
            {
                MessageContainer.AddError("La lunghezza del testo del campo Oggetto non può superare " + this.LimiteCaratteri + " caratteri");
                return false;
            }

            return true;
        }

        private void DataBind()
        {
            model.Note = DomandaCorrente.AltriDati.Note;

            if (LimiteCaratteri > 0)
                model.Oggetto = !string.IsNullOrEmpty(DomandaCorrente.AltriDati.DescrizioneLavori) && DomandaCorrente.AltriDati.DescrizioneLavori.Length > LimiteCaratteri ?
                    DomandaCorrente.AltriDati.DescrizioneLavori.Substring(0, LimiteCaratteri) :
                    DomandaCorrente.AltriDati.DescrizioneLavori;
            else
                model.Oggetto = DomandaCorrente.AltriDati.DescrizioneLavori;
        }

        private void SalvaModifiche()
        {
            if (_editingOggetto is not null)
                model.Oggetto = _editingOggetto;

            if (_editingNote is not null)
                model.Note = _editingNote;

            _showModalModifiche = false;
        }

        private void CloseModalModifiche()
        {
            _editingNote = null;
            _editingOggetto = null;

            _showModalModifiche = false;
        }

        private void OpenModalModifiche()
        {
            _titoloModal = "Modifica";
            _editingNote = model.Note;
            _editingOggetto = model.Oggetto;

            _showModalModifiche = true;
        }
    }
}