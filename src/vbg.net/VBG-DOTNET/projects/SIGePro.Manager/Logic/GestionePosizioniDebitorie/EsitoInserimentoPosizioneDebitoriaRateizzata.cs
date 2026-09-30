using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    public class EsitoInserimentoPosizioneDebitoriaRateizzata
    {
        public readonly bool InserimentoRiuscito;
        public readonly IEnumerable<LinkOnerePosizioneDebitoria> PosizioniDebitorie;
        public readonly IEnumerable<string> Errori;

        protected EsitoInserimentoPosizioneDebitoriaRateizzata(bool inserimentoRiuscito, IEnumerable<LinkOnerePosizioneDebitoria> posizioniDebitorie, IEnumerable<string> errori)
        {
            InserimentoRiuscito = inserimentoRiuscito;
            PosizioniDebitorie = posizioniDebitorie;
            Errori = errori;
        }
    }
}