using Init.SIGePro.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.OperatoreProtocollo
{
    public class OperatoreProtocolloOnline : IOperatoreProtocollo
    {
        string _codiceOperatoreFo;
        IIstanzaDaProtocollare _istanza;

        public OperatoreProtocolloOnline(string codiceOperatoreFo, IIstanzaDaProtocollare istanza)
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
