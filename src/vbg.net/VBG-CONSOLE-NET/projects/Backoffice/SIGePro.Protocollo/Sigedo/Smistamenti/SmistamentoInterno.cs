using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Sigedo.Smistamenti
{
    public class SmistamentoInterno : ISmistamentoFlusso
    {
        string _uoDestinatario;

        public SmistamentoInterno(string uoDestinatario)
        {
            _uoDestinatario = uoDestinatario;
        }

        public string UoSmistamento
        {
            get { return _uoDestinatario; }
        }
    }
}
