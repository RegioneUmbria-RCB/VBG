namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Annotazioni
{
    public class FirmaOmessa : IAnnotazione
    {
        public Annotazione ToAnnotazione()
        {
            return new Annotazione
            {
                Testo = "Documento con firma omessa ai sensi dell’art. 3, comma 2, del D.Lgs 39/1993",
                Formale = true,
                AnnotaInteroDocumento = true,
                AnnotaClassificazioneCorrente = false
            };
        }
    }
}
