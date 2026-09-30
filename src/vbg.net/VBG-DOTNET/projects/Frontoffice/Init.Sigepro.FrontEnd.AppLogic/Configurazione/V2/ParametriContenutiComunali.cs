namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriContenutiComunali : IParametriConfigurazione
    {
        public string HomePageComune;

        public ParametriContenutiComunali(string homePageComune)
        {
            homePageComune = homePageComune ?? "";

            if (homePageComune.EndsWith("/"))
            {
                homePageComune = homePageComune.Substring(0, homePageComune.Length - 1);
            }

            this.HomePageComune = homePageComune;
        }
    }
}
