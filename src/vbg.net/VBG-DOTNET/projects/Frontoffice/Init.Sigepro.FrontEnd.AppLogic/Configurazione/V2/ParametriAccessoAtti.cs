namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriAccessoAtti : IParametriConfigurazione
    {
        public readonly bool MostraDatiMovimenti;

        public ParametriAccessoAtti(bool mostraDatiMovimenti)
        {
            this.MostraDatiMovimenti = mostraDatiMovimenti;
        }
    }
}
