using Init.SIGePro.Manager.WsPosizioniDebitorie;
using System;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    internal class RiferimentoPosizioneDebitoriaSingola: EsitoInserimentoPosizioneDebitoriaSingola
    {
        public readonly PosizioneInseritaPerOnereType idDettaglioPosizioneDebitoria;

        public RiferimentoPosizioneDebitoriaSingola(PosizioneInseritaPerOnereType idDettaglioPosizioneDebitoria)
            :base(true, Convert.ToInt32(idDettaglioPosizioneDebitoria.riferimentoPosizioneDebitoria), Enumerable.Empty<string>())
        {
            this.idDettaglioPosizioneDebitoria = idDettaglioPosizioneDebitoria;
        }
    }
}