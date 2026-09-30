using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class ArgomentoSegnapostoNonValidoException : Exception
    {
        private static class Constants
        {
            public static string EccezioneFmtString = @"L'identificativo {0} impostato in un segnaposto non è un numero valido, testo del segnaposto: {1}";
        }

        public enum TipoSegnaposto
        {
            Campo,
            Scheda
        }

        public ArgomentoSegnapostoNonValidoException(TipoSegnaposto tipo, string testoSegnaposto)
            : base(String.Format(Constants.EccezioneFmtString, tipo == TipoSegnaposto.Campo ? "campo" : "scheda", testoSegnaposto))
        {

        }
    }
}
