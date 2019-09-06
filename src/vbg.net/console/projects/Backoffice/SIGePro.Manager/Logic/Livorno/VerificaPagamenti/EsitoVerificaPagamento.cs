using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.Livorno.VerificaPagamenti
{
    public class EsitoVerificaPagamento
    {
        public bool Esito { get; set; }
        public float Importo { get; set; }
        public string DescrizioneErrore { get; set; }
    }
}
