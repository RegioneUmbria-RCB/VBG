namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{
    public interface IWorkflowInstance
    {
        public IEnumerable<IWorkflowSection> Sections { get; }
    }
}
