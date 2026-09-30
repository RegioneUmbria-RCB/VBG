namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class ArgomentoSegnapostoNonValidoException : Exception
    {
        private static class Constants
        {
            public const string EccezioneFmtString = @"L'identificativo {0} impostato in un segnaposto non è un numero valido, testo del segnaposto: {1}";
        }

        public enum TipoSegnaposto
        {
            Campo,
            Scheda
        }

        public ArgomentoSegnapostoNonValidoException(TipoSegnaposto tipo, string testoSegnaposto)
            : this(String.Format(Constants.EccezioneFmtString, tipo == TipoSegnaposto.Campo ? "campo" : "scheda", testoSegnaposto))
        {

        }

        private ArgomentoSegnapostoNonValidoException() : base()
        {
        }

        private ArgomentoSegnapostoNonValidoException(string? message) : base(message)
        {
        }

        public ArgomentoSegnapostoNonValidoException(string? message, Exception? innerException) : base(message, innerException)
        {
        }
    }
}
