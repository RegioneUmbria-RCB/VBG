using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

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
