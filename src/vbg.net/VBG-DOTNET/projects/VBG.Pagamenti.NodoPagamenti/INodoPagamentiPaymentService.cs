
using System.Collections.Generic;
using VBG.Pagamenti.NodoPagamenti.Annullamento;
using VBG.Pagamenti.NodoPagamenti.Attivazione;
using VBG.Pagamenti.NodoPagamenti.Verifica;

namespace VBG.Pagamenti.NodoPagamenti
{
    public class InfoConnettore
    {
        public InfoConnettore(InfoConnettoreType feats)
        {
            this.SupportaPagoDopo = feats.supportaPagamentoOffLine;
            this.SupportaPagamentoOtf = feats.supportaPagamentoOTF;
            this.SupportaDownloadRicevuta = feats.supportaDownloadRicevuta;
        }

        public bool SupportaPagamentoOtf { get; }
        public bool SupportaDownloadRicevuta { get; private set; }
        public bool SupportaPagoDopo { get; }

    }
    public interface INodoPagamentiPaymentService
    {
        //bool SupportaPagoDopo(string codiceComune);
        //bool SupportaPagamentoOnTheFly(string codiceComune);
        InfoConnettore GetInfoConnettore(string codiceComune);
        EsitoAnnullamentoPosizioneDebitoria AnnullaPosizioneDebitoria(IEnumerable<IEstremiPosizioneDebitoriaClient> posizioniDaAnnullare);
        IEsitoAttivazionePagamento AttivaPagamentoOnTheFly(NodoPagamentiSettings settings, string urlRitorno, RichiestaDiPagamento request);
        IEsitoAttivazionePagamento AttivaPagamentoOffline(NodoPagamentiSettings settings, string urlRitorno, RichiestaDiPagamento request);
        DatiOperazioneSuNodoPagamenti GetDettagliPosizione(IEstremiPosizioneDebitoriaClient estremiPosizione);
        IEsitoVerificaPosizioni VerificaPosizioni(IEnumerable<IEstremiPosizioneDebitoriaClient> richiestaVerifica);
        AvvisoDiPagamento ScaricaAvvisoPagamento(IEstremiPosizioneDebitoriaClient estremiPosizioneDebitoria);
        RicevutaTelematica ScaricaRicevutaTelematica(IEstremiPosizioneDebitoriaClient estremiPosizioneDebitori);
    }
}