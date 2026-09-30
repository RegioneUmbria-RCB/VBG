namespace AreaRiservataCore.Pages.InserimentoIstanza.RiepilogoDomanda
{
    public class DatiConfigurazioneRiepilogo
    {
        public required string DescrizioneFaseRiepilogo { get; set; } = "";
        public required string TitoloFaseRiepilogo { get; set; } = "Riepilogo dei dati immessi";
        public required string TitoloFaseInvio { get; set; } = "Sottoscrizione e invio dell'istanza";
        public required bool AggiungiSchedeNonFirmateARiepilogoAllegati { get; set; } = true;
        public required bool MostraRiepilogoDomanda { get; set; } = true;
        // publrequired ic string UrlRedirectInvioRiuscito { get; set; } = "inserimento-istanza/certificato-invio";
        public required string SottotitoloFaseInvio { get; set; } = "Scaricare, firmare digitalmente e ricaricare il documento";
        public required string DescrizioneFaseInvio { get; set; } = "Descrizione fase invio";
        public required string TestoDichiarazione { get; set; } = "";
        public required string TestoCheckDichiarazione { get; set; } = "Check dichiarazione";
        public required string TestoBottoneTrasferisciIstanza { get; set; } = "Trasferisci l'istanza al comune";
        public required string TitoloGrigliaSottoscrittori { get; set; } = "L'istanza deve essere firmata da";
        public required string TitoloGrigliaNonSottoscrittori { get; set; } = "I soggetti che non sottoscrivono sono";
        public required bool MostraGrigliaSottoscrittori { get; set; } = true;
        public required bool MostraGrigliaNonSottoscrittori { get; set; } = true;
        public required bool VerificaFirmaSuRiepilogo { get; set; } = true;
        public required bool RichiedeFirmaDigitale { get; init; } = true;
    }
}
