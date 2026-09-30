using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Annullamento;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica;
using System.Collections.Generic;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public interface INodoPagamentiPaymentService
    {
        bool SupportaPagoDopo { get; }
        bool SupportaPagamentoOnTheFly { get; }

        EsitoAnnullamentoPosizioneDebitoria AnnullaPosizioneDebitoria(IEnumerable<IEstremiPosizioneDebitoria> posizioniDaAnnullare);
        IEsitoAttivazionePagamento AttivaPagamentoOnTheFly(RichiestaDiPagamento riferimenti);
        IEsitoAttivazionePagamento InserisciPosizioniDebitorie(RichiestaDiPagamento request);
        DatiOperazioneSuNodoPagamenti GetDettagliPosizione(IEstremiPosizioneDebitoria estremiPosizione);
        IEsitoVerificaPosizioni VerificaPosizioni(IEnumerable<IEstremiPosizioneDebitoria> richiestaVerifica);
        AvvisoDiPagamento GetAvvisoPagamento(IEstremiPosizioneDebitoria estremiPosizioneDebitoria);
    }
}