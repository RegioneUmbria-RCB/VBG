namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public interface IWorkflowLoaderService
    {
        IWorkflowDomandaOnline GetMergedWorkflowByIdIntervento(int? idIntervento, bool useCachedDefaultWorkflow = false);
        IWorkflowDomandaOnline? GetBaseWorkflow();
        IWorkflowDomandaOnline? GetWorkflowIntervento(int? idIntervento);
        NodoConWorkflowXml GetNodoInterventoWorkflowXml(int codiceIntervento);
    }
}
