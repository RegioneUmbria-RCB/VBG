using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Verticalizzazioni
{
    public class VerticalizzazioneArpaCalabria : Verticalizzazione
    {
        private const string NOME_VERTICALIZZAZIONE = "ARPA_CALABRIA";

        public VerticalizzazioneArpaCalabria()
        {

        }

        public VerticalizzazioneArpaCalabria(string idComuneAlias, string software) : base(idComuneAlias, NOME_VERTICALIZZAZIONE, software) { }

    }
}
