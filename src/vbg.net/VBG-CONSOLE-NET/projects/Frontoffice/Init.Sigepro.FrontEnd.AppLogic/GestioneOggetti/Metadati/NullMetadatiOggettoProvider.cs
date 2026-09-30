using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati
{
    public class NullMetadatiOggettoProvider : IMetadatiOggettoProvider
    {
        public IEnumerable<Metadato> Metadati => Enumerable.Empty<Metadato>();
    }
}
