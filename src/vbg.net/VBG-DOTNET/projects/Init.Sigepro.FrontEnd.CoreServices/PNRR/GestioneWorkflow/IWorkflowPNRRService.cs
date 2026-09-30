namespace Init.Sigepro.FrontEnd.CoreServices.PNRR.GestioneWorkflow
{

    public interface IWorkflowPNRRService
    {
        Task<IWorkflowInstance> GetWorkflowByIdInterventoAsync(int idIntervento);
        Task<IWorkflowInstance?> GetWorkflowByIdDomandaAsync(int idDomanda);
        Task<IEnumerable<IWorkflowSection>> GetSezioniAsync(int idIntervento);
        Task<StepsFromSectionResponse> GetStepsPerSezioneAsync(int idIntervento, int sectionIndex);
        Task<IEnumerable<DescrittoreStep>> GetStepsPerRiepilogoAsync(int idIntervento);
    }
}
