namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Paginatore
{
    public class PaginatoreState
    {
        private class StatoVisibilita
        {
            internal StatoVisibilita() { }

            internal StatoVisibilita(StatoVisibilita copyFrom)
            {
                this.IsVisible = copyFrom.IsVisible;
                this.MostraBottoneAvanti = copyFrom.MostraBottoneAvanti;
                this.MostraBottoneInviaDomanda = copyFrom.MostraBottoneInviaDomanda;
                this.TestoBottoneInviaDomanda = copyFrom.TestoBottoneInviaDomanda;
                this.MostraDescrizioneStep = copyFrom.MostraDescrizioneStep;
            }

            public bool IsVisible { get; internal set; } = true;
            public bool MostraBottoneAvanti { get; internal set; } = true;
            public bool MostraBottoneInviaDomanda { get; internal set; } = false;
            public string TestoBottoneInviaDomanda { get; internal set; } = "Invia domanda";
            public bool MostraDescrizioneStep { get; internal set; } = true;
        }

        private StatoVisibilita _statoVisibilita = new();

        public string TitoloPagina { get; internal set; } = "";
        public string DescrizionePagina { get; internal set; } = "";
        public int CurrentStepId { get; internal set; } = -1;

        public bool IsFirstStep => this.CurrentStepId == 0;
        public bool IsLastStep => this.CurrentStepId == (this.Steps.Count() - 1);
        public IEnumerable<Step> Steps { get; internal set; } = Enumerable.Empty<Step>();
        public int LastCompletedStep { get; internal set; } = 0;
        public bool IsStepDisabilitato { get; internal set; }

        // Visibilita
        public bool IsVisible { get => this._statoVisibilita.IsVisible; internal set => this._statoVisibilita.IsVisible = value; }
        public bool MostraBottoneAvanti { get => this._statoVisibilita.MostraBottoneAvanti; internal set => this._statoVisibilita.MostraBottoneAvanti = value; }
        public bool MostraBottoneInviaDomanda { get => this._statoVisibilita.MostraBottoneInviaDomanda; internal set => this._statoVisibilita.MostraBottoneInviaDomanda = value; }
        public string TestoBottoneInviaDomanda { get => this._statoVisibilita.TestoBottoneInviaDomanda; internal set => this._statoVisibilita.TestoBottoneInviaDomanda = value; }
        public bool MostraDescrizioneStep { get => this._statoVisibilita.MostraDescrizioneStep; internal set => this._statoVisibilita.MostraDescrizioneStep = value; }

        internal void CopiaStatoVisibilitaDa(PaginatoreState? currentState)
        {
            if (currentState == null)
            {
                return;
            }
            this._statoVisibilita = new(currentState._statoVisibilita);
        }

        internal void ResetStatoVisibilita()
        {
            this._statoVisibilita = new();
        }

        public string TitoloStepPrecedente
        {
            get
            {
                if (this.CurrentStepId <= 0)
                {
                    return String.Empty;
                }

                return this.Steps.ElementAt(this.CurrentStepId - 1)?.NomeStep ?? "";
            }
        }

        public string TitoloStepSuccessivo
        {
            get
            {
                if (this.IsLastStep)
                {
                    return String.Empty;
                }

                return this.Steps.ElementAt(this.CurrentStepId + 1)?.NomeStep ?? "";
            }
        }
    }
}
