using Init.SIGePro.Manager.WsPosizioniDebitorie;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    internal class RiferimentoPosizioneDebitoriaRateizzata : EsitoInserimentoPosizioneDebitoriaRateizzata
    {
        public readonly PosizioneInseritaPerOnereType[] IdDettaglioPosizioneDebitoria;

        public RiferimentoPosizioneDebitoriaRateizzata(PosizioneInseritaPerOnereType[] idDettaglioPosizioneDebitoria)
            :base(true, idDettaglioPosizioneDebitoria.Select(x => new LinkOnerePosizioneDebitoria(x)), Enumerable.Empty<string>())
        {
            this.IdDettaglioPosizioneDebitoria = idDettaglioPosizioneDebitoria;
        }
    }
}