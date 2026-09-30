using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;

namespace AreaRiservataCore.Pages.MiePratiche.Componenti
{
    public class FiltroIstanzePresentate
    {
        public RichiestaListaPraticheV3 ParametriFiltri { get; set; } = new();

        public bool FiltroPerCodiceIstanzaImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.NumeroIstanza);
        }

        public void RimuoviFiltroPerCodiceIstanza()
        {
            this.ParametriFiltri.NumeroIstanza = null;
        }

        public bool FiltroPerRichiedenteImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.NomeOCfRichiedente);
        }

        public void RimuoviFiltroPerRichiedente()
        {
            this.ParametriFiltri.NomeOCfRichiedente = null;
        }

        public bool FiltroPerNumeroProtocolloImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.DatiProtocollo?.Numero);
        }

        public void RimuoviFiltroPerNumeroProtocollo()
        {
            this.ParametriFiltri.DatiProtocollo.Numero = null;
        }

        public bool FiltroPerDataProtocolloImpostato
        {
            get => this.ParametriFiltri?.DatiProtocollo?.Data != null;
        }

        public void RimuoviFiltroPerDataProtocollo()
        {
            this.ParametriFiltri.DatiProtocollo.Data = null;
        }

        public bool FiltroPerNumeroAutorizzazioneImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.NumeroAutorizzazione);
        }

        public void RimuoviFiltroPerNumeroAutorizzazione()
        {
            this.ParametriFiltri.NumeroAutorizzazione = null;
        }

        public bool FiltroPerAnnoImpostato
        {
            get => this.ParametriFiltri?.PeriodoPresentazione != null &&
                this.ParametriFiltri.PeriodoPresentazione.Anno != 0;
        }

        public void RimuoviFiltroPerAnno()
        {
            this.ParametriFiltri.PeriodoPresentazione = null;
        }

        public bool FiltroPerMeseImpostato
        {
            get => this.ParametriFiltri?.PeriodoPresentazione?.Mese != null &&
                this.ParametriFiltri.PeriodoPresentazione.Mese != 0;
        }

        public void RimuoviFiltroPerMese()
        {
            this.ParametriFiltri.PeriodoPresentazione.Mese = null;
        }

        public bool FiltroPerStatoPraticaImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.StatoPratica);
        }

        public void RimuoviFiltroPerStatoPratica()
        {
            this.ParametriFiltri.StatoPratica = null;
        }

        public bool FiltroPerTipoInterventoImpostato
        {
            get => this.ParametriFiltri?.CodiceIntervento != null;
        }

        public void RimuoviFiltroPerTipoIntervento()
        {
            this.ParametriFiltri.CodiceIntervento = null;
        }

        public bool FiltroPerIndirizzoImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.Indirizzo?.Civico) ||
                (this.ParametriFiltri?.Indirizzo != null &&
                this.ParametriFiltri.Indirizzo.CodiceStradario != 0);
        }

        public void RimuoviFiltroPerIndirizzo()
        {
            this.ParametriFiltri.Indirizzo = null;
        }

        public bool FiltroPerFabbricatoImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.Fabbricato);
        }

        public void RimuoviFiltroPerFabbricato()
        {
            this.ParametriFiltri.Fabbricato = null;
        }

        public bool FiltroPerOggettoImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.Oggetto);
        }

        public void RimuoviFiltroPerOggetto()
        {
            this.ParametriFiltri.Oggetto = null;
        }

        public bool FiltroPerPosizioneArchivioImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.PosizioneArchivio);
        }

        public void RimuoviFiltroPerPosizioneArchivio()
        {
            this.ParametriFiltri.PosizioneArchivio = null;
        }

        public bool FiltroPerTipoCatastoImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.DatiCatastali?.TipoCatasto);
        }

        public void RimuoviFiltroPerTipoCatasto()
        {
            this.ParametriFiltri.DatiCatastali.TipoCatasto = null;
        }

        public bool FiltroPerDatiCatastaliImpostato
        {
            get => !string.IsNullOrEmpty(this.ParametriFiltri?.DatiCatastali?.Foglio) ||
                !string.IsNullOrEmpty(this.ParametriFiltri?.DatiCatastali?.Particella) ||
                !string.IsNullOrEmpty(this.ParametriFiltri?.DatiCatastali?.Subalterno);
        }

        public void RimuoviFiltroPerDatiCatastali()
        {
            this.ParametriFiltri.DatiCatastali.Foglio = null;
            this.ParametriFiltri.DatiCatastali.Particella = null;
            this.ParametriFiltri.DatiCatastali.Subalterno = null;
        }
    }

    public enum TipoFiltroIstanzePresentate
    {
        CodiceIstanza,
        Anno,
        Mese,
        NumeroProtocollo,
        DataProtocollo,
        Richiedente,
        NumeroAutorizzazione,
        StatoPratica,
        TipoIntervento,
        Indirizzo,
        Fabbricato,
        Oggetto,
        PosizioneArchivio,
        TipoCatasto,
        DatiCatastali
    }
}
