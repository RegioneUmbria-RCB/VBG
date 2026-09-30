using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using VBG.Pagamenti.NodoPagamenti;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.GestioneMessaggiRabbit.PosizioniDebitorie
{
    public interface IEventiPagamentiService
    {
        void DestinatariPendenzaAggiornati(PresentazioneIstanzaDataKey dataKey, string codiceFiscaleEnteCreditore, IEstremiPosizioneDebitoriaServer posizione, EstremiDomandaNodoPagamenti estremiDomanda);
    }
}