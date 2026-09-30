using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Interfacce;

namespace Init.SIGePro.Protocollo.Sigedo.Builders
{
    public class SigedoSmistamentoBackofficeBuilder : ISmistamentoProvenienza
    {
        string _operatore;
        public SigedoSmistamentoBackofficeBuilder(string operatore)
        {
            _operatore = operatore;
        }

        #region ISmistamentoSigedo Members

        public string GetOperatoreSmistamento()
        {
            if (String.IsNullOrEmpty(_operatore))
                throw new Exception("OPERATORE NON VALORIZZATO, NON E' POSSIBILE RECUPERARE LO SMISTAMENTO");

            return _operatore.Substring(1);
        }

        public bool IsSmistamentoAutomaticoDaOnline
        {
            get { return false; }
        }

        #endregion
    }
}
