using System.Collections.Generic;
using System.IO;

namespace Init.Sigepro.FrontEnd.AppLogic.PresentazioneIstanze.Workflow.Loaders
{

    internal class CompositeWorkflowReader : IWorkflowReader
    {
        private readonly IEnumerable<IWorkflowReader> _readers;
        public CompositeWorkflowReader(IEnumerable<IWorkflowReader> readers)
        {
            this._readers = readers;
        }
        public Stream? OpenReadStream()
        {
            foreach (var reader in this._readers)
            {
                var stream = reader.OpenReadStream();
                if (stream != null)
                    return stream;
            }
            return null;
        }
    }

}
