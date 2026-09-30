using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    public class EsitoInserimentoPosizioneDebitoriaSingola
    {
        public readonly bool InserimentoRiuscito;
        public readonly int? IdPosizioneDebitoria;
        public readonly IEnumerable<string> Errori;

        protected EsitoInserimentoPosizioneDebitoriaSingola(bool inserimentoRiuscito, int? idPosizioneDebitoria, IEnumerable<string> errori)
        {
            InserimentoRiuscito = inserimentoRiuscito;
            IdPosizioneDebitoria = idPosizioneDebitoria;
            Errori = errori;
        }
    }
}
