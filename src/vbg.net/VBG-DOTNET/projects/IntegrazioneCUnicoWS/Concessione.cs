using System;

namespace IntegrazioneCUnicoWS
{
    public class Concessione
    {
        public string CodiceFiscaleTitolare { get; set; }

        public DateTime DataRilascio { get; set; } 

        public DateTime? DataCessazione { get; set; }
        
        public String Numero { get; set; }

        public String PeriodicitaOccupazione { get; set; }

        public bool Rinnovabile { get; set; } = false;

        public String TipoOccupazione { get; set; }

        public bool PagamentoAnticipato { get; set; }

    }
}