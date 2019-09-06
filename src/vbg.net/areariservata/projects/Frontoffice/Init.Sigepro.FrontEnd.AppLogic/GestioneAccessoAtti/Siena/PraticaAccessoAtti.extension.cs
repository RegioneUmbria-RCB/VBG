using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.WsAccessoAtti
{
    public partial class PraticaAccessoAtti
    {
        public string StringaProtocollo => String.IsNullOrEmpty(this.NumeroProtocollo) ? String.Empty : $"{this.NumeroProtocollo} del {this.DataProtocollo.Value.ToString("dd/MM/yyyy")}";
        public string StringaNumeroIstanza => $"{this.NumeroIstanza} del {this.DataPresentazione.ToString("dd/MM/yyyy")}";
    }
}
