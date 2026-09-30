using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Movimenti
{
    public class InsertMovimentoRequest
    {
        public int? CodiceAmministrazione { get; set; }
        public int CodiceResponsabile { get; set; }
        public int? DataScadenza { get; set; }
        public string DescrizioneMovimento { get; set; }
        public bool PubblicaMovimento { get; set; }
        public string TipoMovimento { get; set; }
    }
}
