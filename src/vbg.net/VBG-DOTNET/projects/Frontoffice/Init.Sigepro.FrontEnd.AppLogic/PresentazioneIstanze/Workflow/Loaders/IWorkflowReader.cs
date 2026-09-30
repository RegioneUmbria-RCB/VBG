using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{
    public interface IWorkflowReader
    {
        Stream? OpenReadStream();
    }
}
