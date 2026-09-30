namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Annotazioni
{
    public class DocumentoArt65 : IAnnotazione
    {
        public Annotazione ToAnnotazione()
        {
            return new Annotazione
            {
                Testo = "Documentazione presentata ai sensi dell'art.65 comma 1, lettera b) del Codice dell'Amministrazione Digitale e s.m.i.",
                Formale = true,
                AnnotaInteroDocumento = true,
                AnnotaClassificazioneCorrente = false
            };
        }
    }
}
