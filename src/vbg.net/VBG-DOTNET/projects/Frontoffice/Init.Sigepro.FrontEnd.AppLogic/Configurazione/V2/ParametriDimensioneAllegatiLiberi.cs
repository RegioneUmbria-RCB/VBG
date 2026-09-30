using Init.SIGePro.Manager.DTO.Configurazione;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriDimensioneAllegatiLiberi : IParametriConfigurazione
    {
        public readonly FormatoAllegatoLiberoDto[] FormatiAllegatiLiberi;

        public bool FunzionalitaConfigurata => this.FormatiAllegatiLiberi != null && this.FormatiAllegatiLiberi.Length > 0;

        public ParametriDimensioneAllegatiLiberi(FormatoAllegatoLiberoDto[] formatiAllegatiLiberi)
        {
            this.FormatiAllegatiLiberi = formatiAllegatiLiberi;
        }
    }
}
