using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Commissioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsCommissioniAccessoPIN" in both code and config file together.
    [ServiceContract]
    public interface IWsCommissioniAccessoPIN
    {
        [OperationContract]
        bool VerificaValiditaPIN(string token, string pin);

        [OperationContract]
        int AssociaUtenteACommissioneByPIN(string token, int codiceAnagrafe, string pin);
    }
}
