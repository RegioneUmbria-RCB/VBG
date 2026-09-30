namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriAspetto : IParametriConfigurazione
    {
        public readonly string FileConfigurazioneContenuti;
        public readonly string IntestazioneCertificatoInvio;

        internal ParametriAspetto(string fileConfigurazioneContenuti, string intestazioneCertificatoInvio)
        {
            this.FileConfigurazioneContenuti = fileConfigurazioneContenuti;
            this.IntestazioneCertificatoInvio = intestazioneCertificatoInvio;
        }
    }
}
