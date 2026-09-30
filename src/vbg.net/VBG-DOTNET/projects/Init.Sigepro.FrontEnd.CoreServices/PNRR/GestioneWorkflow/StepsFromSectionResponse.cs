namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public class StepsFromSectionResponse
    {
        public string Titolo { get; }
        public string Descrizione { get; }
        public bool NascondiMenuNavigazione { get; }
        public IEnumerable<DescrittoreStep> Steps { get; }
        public int TotalSectionsCount { get; }

        public StepsFromSectionResponse(string titolo, string descrizione, bool nascondiMenuNavigazione, IEnumerable<DescrittoreStep> steps, int totalSectionsCount)
        {
            this.Titolo = titolo;
            this.Descrizione = descrizione;
            this.NascondiMenuNavigazione = nascondiMenuNavigazione;
            this.Steps = steps;
            this.TotalSectionsCount = totalSectionsCount;
        }
    }
}
