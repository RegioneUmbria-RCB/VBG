using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
    internal class InterventoPubblicatoNellaDomandaOnLine : InterventoPubblicato
    {
        public InterventoPubblicatoNellaDomandaOnLine(IEnumerable<InterventoReadOnly> interventi) :
            base(new InterventiReverseEnumerator<IIntervento>(interventi), new VerificaPubblicazioneDomandaOnLine())
        {
        }
    }
}