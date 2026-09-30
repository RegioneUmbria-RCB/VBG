namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public interface IWorkflowSection
    {
        string Titolo { get; }

        string Descrizione { get; }
        bool NascondiMenuNavigazione { get; }
        IEnumerable<WorkflowStep> Steps { get; }
    }
}