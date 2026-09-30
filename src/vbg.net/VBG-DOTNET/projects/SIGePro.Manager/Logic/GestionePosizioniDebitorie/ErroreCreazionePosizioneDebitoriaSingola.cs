using System.Collections.Generic;
using System.Linq;
using Init.SIGePro.Manager.WsPosizioniDebitorie;

namespace Init.SIGePro.Manager.Logic.GestionePosizioniDebitorie
{
    internal class ErroreCreazionePosizioneDebitoriaSingola: EsitoInserimentoPosizioneDebitoriaSingola
    {
        public readonly IEnumerable<ErroreBackofficeType> ErroriBackoffice;
        public ErroreCreazionePosizioneDebitoriaSingola(IEnumerable<ErroreBackofficeType> errori)
            :base(false, null, errori.Select( x => x.descrizione))
        {
            this.ErroriBackoffice = errori;
        }
    }
}