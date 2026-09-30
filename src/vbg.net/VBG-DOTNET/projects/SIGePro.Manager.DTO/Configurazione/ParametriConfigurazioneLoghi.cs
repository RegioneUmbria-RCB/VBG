using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO.Configurazione
{
    [Serializable]
    public class ParametriConfigurazioneLoghi
    {
        public int? CodiceOggettoLogoComune { get; set; }
        public int? CodiceOggettoLogoRegione { get; set; }
        public string UrlLogo { get; set; }
    }
}
