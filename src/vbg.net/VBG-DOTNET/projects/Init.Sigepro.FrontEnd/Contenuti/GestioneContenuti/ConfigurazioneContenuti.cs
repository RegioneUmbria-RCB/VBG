using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;

namespace Init.Sigepro.FrontEnd.Contenuti.GestioneContenuti
{


    public class ConfigurazioneContenuti
    {
        public readonly BoxDatiComune DatiComune;
        public readonly XmlConfigurazioneContenuti Testi;

        public ConfigurazioneContenuti(IAliasSoftwareResolver aliasSoftwareResolver, IConfigurazione<ParametriAspetto> configurazioneAspetto)
        {
            this.Testi = XmlConfigurazioneContenuti.LoadFrom(configurazioneAspetto.Parametri.FileConfigurazioneContenuti);
            this.DatiComune = BoxDatiComune.Load(aliasSoftwareResolver.AliasComune, aliasSoftwareResolver.Software, this.Testi.LinkRegione, this.Testi.TestoRegione);

        }
    }
}