namespace VBG.Backend.Protocollo.AppLogic.Legacy.Acaris.Document.CSIPiemonte.Annotazioni
{
    internal class DocumentoConvertito : IAnnotazione
    {
        private readonly string _convertitoDa;
        private readonly string _convertitoIn;
        public DocumentoConvertito(string convertitoDa, string convertitoIn)
        {
            this._convertitoDa = convertitoDa;
            this._convertitoIn = convertitoIn;
        }
        public Annotazione ToAnnotazione()
        {
            return new Annotazione
            {
                Testo = $"Conversione effettuata da {this._convertitoDa} in {this._convertitoIn}",
                Formale = true,
                AnnotaInteroDocumento = true,
                AnnotaClassificazioneCorrente = false
            };
        }
    }
}
