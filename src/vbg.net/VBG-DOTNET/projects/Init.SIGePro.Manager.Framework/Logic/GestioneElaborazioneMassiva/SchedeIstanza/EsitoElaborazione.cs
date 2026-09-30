using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    public class EsitoElaborazione
    {
        public EsitoElaborazioneMassivaSchedeEnum Esito { get; }
        public int ElementiElaborati { get; }
        public int ElementiConErrore { get; }

        public EsitoElaborazione(EsitoElaborazioneMassivaSchedeEnum esito, int elementiElaborati = 0, int elementiConErrore = 0)
        {
            Esito = esito;
            ElementiElaborati = elementiElaborati;
            ElementiConErrore = elementiConErrore;
        }


    }
}
