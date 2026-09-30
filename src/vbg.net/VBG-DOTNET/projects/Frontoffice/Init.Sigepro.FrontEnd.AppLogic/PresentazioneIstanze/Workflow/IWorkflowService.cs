using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow
{
    public interface IWorkflowService
    {
        void ClearCacheDomanda(int idDomanda);
        IWorkflowDomandaOnline GetWorkflowByIdDomanda(int idDomandaOnline);
    }
}
