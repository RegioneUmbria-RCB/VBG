using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.GeProt.Protocollazione.Documenti
{
    public class DocumentiFactory
    {
        public static IDocumenti Create(IEnumerable<ProtocolloAllegati> allegati)
        {
            if (allegati.Count() == 0)
                return null;

            if (allegati.Count() == 1)
                return new DocumentoPrincipale(allegati.First());
            else 
                return new DocumentiAllegati(allegati);
        }
    }
}
