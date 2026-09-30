namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public interface IFrameworkToCoreWorkflowUrlMapper
    {
        // string MapUrlOrDefault(string url);
        void AssicuraEsistenzaStepId(StepType url);
    }
}