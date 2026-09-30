using System.Collections.Generic;

namespace VBG.Backend.Protocollo.AppLogic.Core.Elios
{
    public class InsertRequest
    {
        public IEnumerable<Anagrafica> Anagrafiche { get; internal set; }
        public Titolario Titolario { get; internal set; }
        public string Oggetto { get; internal set; }
        public string Tipo { get; internal set; }
        public IEnumerable<Ufficio> Uffici { get; internal set; }
    }
}
