using Init.Sigepro.FrontEnd.AppLogic.WsInterventi;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneInterventi
{
    public interface IInterventiV3Service
    {
        WorkflowV3Dto GetWorkflowDomandaOnLineByIdIntervento(int idIntervento);
        Task<WorkflowV3Dto> GetWorkflowDomandaOnLineByIdInterventoAsync(int idIntervento);
    }
}
