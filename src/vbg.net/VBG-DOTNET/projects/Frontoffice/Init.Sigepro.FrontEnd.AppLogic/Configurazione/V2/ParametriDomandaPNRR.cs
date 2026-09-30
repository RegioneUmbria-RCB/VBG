namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public enum ModalitaInvioNotificheEnum
    {
        Nessuna,
        Email,
        IO
    }

    public class ParametriDomandaPNRR : IParametriConfigurazione
    {
        public readonly string UrlHomepageComune;
        public readonly string UrlLayoutConfigService;
        public readonly string UrlTerminiECondizioni;
        public readonly ModalitaInvioNotificheEnum ModalitaInvioNotifiche;

        public ParametriDomandaPNRR(string urlLayoutConfigService, string urlHomepageComune, string urlTerminiECondizioni, string modalitaInvioNotifiche)
        {
            this.UrlLayoutConfigService = urlLayoutConfigService;
            this.UrlHomepageComune = urlHomepageComune;
            this.UrlTerminiECondizioni = urlTerminiECondizioni;
            this.ModalitaInvioNotifiche = this.DecodificaModalitaInvio(modalitaInvioNotifiche);
        }

        private ModalitaInvioNotificheEnum DecodificaModalitaInvio(string modalitaInvioNotifiche)
        {
            var val = modalitaInvioNotifiche.ToLower();

            if (val == "io")
            {
                return ModalitaInvioNotificheEnum.IO;
            }

            if (val == "mail")
            {
                return ModalitaInvioNotificheEnum.Email;
            }

            if (val == "nessuna")
            {
                return ModalitaInvioNotificheEnum.Nessuna;
            }

            return ModalitaInvioNotificheEnum.Email;
        }

    }
}
