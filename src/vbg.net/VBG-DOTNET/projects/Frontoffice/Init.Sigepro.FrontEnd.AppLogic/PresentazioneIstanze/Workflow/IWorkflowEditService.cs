using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public interface IWorkflowEditService
    {
        void AggiornaXmlWorkflowBase(string xml);
        Task AggiornaXmlWorkflowInterventoAsync(string xml, int codiceIntervento);
        StepCollectionType DeserializeXmlWithCData(string xml);
    }
}
