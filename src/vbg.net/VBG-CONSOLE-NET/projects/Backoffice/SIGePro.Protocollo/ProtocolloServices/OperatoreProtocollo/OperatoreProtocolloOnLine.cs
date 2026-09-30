using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;

namespace Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo
{
    public class OperatoreProtocolloOnline : IOperatoreProtocollo
    {
        string _codiceOperatoreFo;
        Istanze _istanza;

        public OperatoreProtocolloOnline(string codiceOperatoreFo, Istanze istanza)
        {
            _codiceOperatoreFo = codiceOperatoreFo;
            _istanza = istanza;
        }

        #region IOperatoreProtocollo Members

        public string CodiceOperatore
        {
            get 
            {
                string retVal = "";
                if (!String.IsNullOrEmpty(_codiceOperatoreFo))
                    retVal = _codiceOperatoreFo;
                else
                {
                    if (String.IsNullOrEmpty(_istanza.CODICERESPONSABILEPROC))
                        throw new Exception(String.Format("NON E' STATO POSSIBILE PROTOCOLLARE MANCA IL CODICE RESPONSABILE NELL'ISTANZA {0}", _istanza.NUMEROISTANZA));

                    retVal = _istanza.CODICERESPONSABILEPROC;
                }
                int parseOperatoreFo;
                bool parse = Int32.TryParse(retVal, out parseOperatoreFo);
                if (!parse)
                    throw new Exception("IL PARAMETRO CODOPERATOREFO DEVE ESSERE UN VALORE NUMERICO");

                return retVal;
            }
        }
        
        public bool IsOperatoreDefault
        {
            get { return false; }
        }

        #endregion
    }
}
