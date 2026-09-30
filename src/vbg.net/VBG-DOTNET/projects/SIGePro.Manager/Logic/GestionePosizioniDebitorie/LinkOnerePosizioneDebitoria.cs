using Init.SIGePro.Manager.WsPosizioniDebitorie;
using System;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    public class LinkOnerePosizioneDebitoria
    {
        public readonly int OnereId;
        public readonly int PosizioneDebitoriaId;


        public LinkOnerePosizioneDebitoria(PosizioneInseritaPerOnereType posizioneInseritaPerOnereType)
            :this(posizioneInseritaPerOnereType.riferimentoOnere, posizioneInseritaPerOnereType.riferimentoPosizioneDebitoria)
        {

        }
        public LinkOnerePosizioneDebitoria(string onereId, string posizioneDebitoriaId)
            :this(Convert.ToInt32(onereId), Convert.ToInt32(posizioneDebitoriaId))
        {
        }

        public LinkOnerePosizioneDebitoria(int onereId, int posizioneDebitoriaId)
        {
            OnereId = onereId;
            PosizioneDebitoriaId = posizioneDebitoriaId;
        }
    }
}