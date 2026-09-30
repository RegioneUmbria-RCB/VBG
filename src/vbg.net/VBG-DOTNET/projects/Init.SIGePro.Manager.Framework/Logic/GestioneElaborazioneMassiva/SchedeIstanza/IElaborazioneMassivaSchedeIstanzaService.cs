using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public interface IElaborazioneMassivaSchedeIstanzaService
    {
        EsitoElaborazione Elabora(int idElaborazione);
    }
}
