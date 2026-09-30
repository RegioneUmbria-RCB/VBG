using System.Linq;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public class NullWorkflowService2 : IWorkflowService
    {
        public void ClearCacheDomanda(int idDomanda)
        {
        }

        public IWorkflowDomandaOnline GetWorkflowByIdDomanda(int idDomandaOnline)
        {
            return new WorkflowDomandaOnline(new StepCollectionType(Enumerable.Empty<StepType>()));
        }

        public string GetBaseWorkflowXml()
        {
            return string.Empty;
        }

        public string GetInterventoWorkflowXml(int codiceIntervento)
        {
            return string.Empty;
        }

        public string GetBaseWorkflowMergedWithInterventoXml(int codiceIntervento)
        {
            return string.Empty;
        }

        public void AggiornaXmlWorkflowBase(string xml)
        {

        }

        public async Task AggiornaXmlWorkflowInterventoAsync(string xml, int codiceIntervento)
        {
            await Task.CompletedTask;
        }

        public NodoConWorkflowXml GetNodoInterventoWorkflowXml(int codiceIntervento)
        {
            return new NodoConWorkflowXml();
        }

        public StepCollectionType DeserializeXmlWithCData(string xml) 
        {
            return new StepCollectionType();
        }
    }
}
