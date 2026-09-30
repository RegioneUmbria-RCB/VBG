namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public class DescrittoreStep
    {
        public required int Id { get; init; }
        public required Type Type { get; init; }
        public required string Titolo { get; init; } = "Titolo step";
        public required string Descrizione { get; init; } = "Descrizione step";
        public required IDictionary<string, object> Properties { get; init; } = new Dictionary<string, object>();
    }
}
