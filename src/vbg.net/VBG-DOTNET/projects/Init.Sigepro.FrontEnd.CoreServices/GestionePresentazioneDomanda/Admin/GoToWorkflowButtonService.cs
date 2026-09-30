namespace Init.Sigepro.FrontEnd.CoreServices.GestionePresentazioneDomanda.Admin
{
    public class GoToWorkflowButtonService
    {
        private const string WORKFLOW_PAGE = "admin/workflow";

        public string GotoUrl { get; private set; } = "";
        public bool InterventoSelezionato => !string.IsNullOrEmpty(this.GotoUrl);
        public string DisabledClass => this.InterventoSelezionato ? "" : "disabled";

        public void SetCodiceIntervento(int? codiceIntervento)
        {
            if (codiceIntervento.HasValue)
            {
                this.GotoUrl = $"{WORKFLOW_PAGE}/{codiceIntervento}";
            }
            else
            {
                this.GotoUrl = string.Empty;
            }
        }
    }
}
