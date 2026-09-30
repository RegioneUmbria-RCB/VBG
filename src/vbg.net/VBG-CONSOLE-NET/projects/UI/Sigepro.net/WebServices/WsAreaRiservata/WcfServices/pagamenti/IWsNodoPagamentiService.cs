using Init.SIGePro.Manager.DTO.Pagamenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.pagamenti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsNodoPagamenti" in both code and config file together.
    [ServiceContract]
    public interface IWsNodoPagamentiService
    {
        [OperationContract]
        ConfigurazionePagamentiNodoPagamenti GetConfigurazione(string token, string software, string codiceComune);
    }
}
