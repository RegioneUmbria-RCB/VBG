using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
    public interface IAnagraficaDaCopiare
    {
        int Id { get; }
        int TipoSoggetto { get; }
        string DescrizioneTipoSoggetto { get; }
        bool RichiedeAnagraficaCollegata { get; }
    }
}
