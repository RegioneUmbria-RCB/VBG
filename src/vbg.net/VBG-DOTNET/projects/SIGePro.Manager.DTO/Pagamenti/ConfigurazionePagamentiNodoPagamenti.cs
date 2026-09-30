using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Xml.Serialization;

namespace Init.SIGePro.Manager.DTO.Pagamenti
{
    [DataContractAttribute]
    public class ConfigurazionePagamentiNodoPagamenti
    {
        [DataMember(Order = 0)]
        public string UrlWs { get; set; }

        [DataMember(Order = 1)]
        public string UrlBack { get; set; }

        [DataMember(Order = 2)]
        public string UrlRitorno { get; set; }

        [DataMember(Order = 3)]
        public string CodiceFiscaleEnteCreditore { get; set; }

        [DataMember(Order = 4)]
        public int? IdModalitaPagamento { get; set; }

        [DataMember(Order = 5)]
        public bool NodoPagamentiAttivo { get; set; } = false;

        [DataMember(Order = 6)]
        public string SoggettoPendenza { get; set; } = "";

        [DataMember(Order = 7)]
        public bool PagoDopoAttivo { get; set; } = false;

        [DataMember(Order = 8)]
        public int PagoDopoGGScadenza { get; set; } = 30;
    }
}
