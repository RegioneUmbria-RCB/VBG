namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriInvio : IParametriConfigurazione
    {
        public readonly string MessaggioInvioFallito;
        public readonly string MessaggioInvioPec;
        public readonly int? CodiceOggettoInvioConFirmaDigitale;
        // public readonly int? CodiceOggettoInvioConSottoscrizione;

        internal ParametriInvio(string messaggioInvioFallito, string messaggioInvioPec, int? codiceOggettoInvioConFirmaDigitale/*, int? codiceOggettoInvioConSottoscrizione*/)
        {
            this.MessaggioInvioFallito = messaggioInvioFallito;
            this.MessaggioInvioPec = messaggioInvioPec;
            this.CodiceOggettoInvioConFirmaDigitale = codiceOggettoInvioConFirmaDigitale;
            // this.CodiceOggettoInvioConSottoscrizione = codiceOggettoInvioConSottoscrizione;
        }
    }
}
