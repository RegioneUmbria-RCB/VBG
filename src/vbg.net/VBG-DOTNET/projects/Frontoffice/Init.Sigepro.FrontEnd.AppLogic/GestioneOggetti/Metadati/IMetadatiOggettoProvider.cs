using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti.Metadati
{
    public interface IMetadatiOggettoProvider
    {
        IEnumerable<Metadato> Metadati { get; }
    }
}
