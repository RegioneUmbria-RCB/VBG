using Init.SIGePro.Manager.WsPosizioniDebitorie;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    internal class ErroreCreazionePosizioneDebitoriaRateizzata: EsitoInserimentoPosizioneDebitoriaRateizzata
    {
        public readonly ErroreBackofficeType[] ListaErrori;

        public ErroreCreazionePosizioneDebitoriaRateizzata(ErroreBackofficeType[] listaErrori)
            :base(false, Enumerable.Empty<LinkOnerePosizioneDebitoria>(), listaErrori.Select( x => x.descrizione))
        {
            this.ListaErrori = listaErrori;
        }
    }
}