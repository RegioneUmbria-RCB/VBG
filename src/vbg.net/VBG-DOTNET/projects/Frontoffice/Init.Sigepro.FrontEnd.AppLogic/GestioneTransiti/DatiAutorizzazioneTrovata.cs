using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public class DatiAutorizzazioneTrovata
    {
        public readonly RiferimentiPraticaCercata DatiRicerca;
        public readonly int IdPraticaRiferimento;
        public readonly int IdCampoDinamico;

        public DatiAutorizzazioneTrovata(RiferimentiPraticaCercata datiRicerca, int idPraticaRiferimento, int idCampoDinamico)
        {
            this.DatiRicerca = datiRicerca;
            this.IdPraticaRiferimento = idPraticaRiferimento;
            this.IdCampoDinamico = idCampoDinamico;
        }
    }
}
