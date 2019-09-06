using Init.Sigepro.FrontEnd.Infrastructure.Dispatching;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.GestioneMovimenti.Commands
{
    public class RimuoviAllegatoDellaSchedaDinamica : Command
    {
        public readonly string IdComune;
        public readonly int IdMovimento;
        public readonly int IdCampoDinamico;
        public readonly int IndiceMolteplicita;
        public readonly string VecchioValore;
     
        public RimuoviAllegatoDellaSchedaDinamica(string idComune, int idMovimento, int idCampoDinamico, 
                                                   int indiceMolteplicita, string vecchioValore)
        {
            this.IdComune = idComune;
            this.IdMovimento = idMovimento;
            this.IdCampoDinamico = idCampoDinamico;
            this.IndiceMolteplicita = indiceMolteplicita;
            this.VecchioValore = vecchioValore;
        }
    }
}
