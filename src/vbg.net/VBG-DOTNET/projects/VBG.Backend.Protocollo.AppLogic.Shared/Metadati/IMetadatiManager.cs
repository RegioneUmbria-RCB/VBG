using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Metadati
{
    public interface IMetadatiManager
    {
        bool IsProtocolloEsitato(string idComune, int fkIdAutorizzazione);
        void SetProtocolloEsitato(string idComune, int fkIdAutorizzazione, bool esitato = true);
    }
}
