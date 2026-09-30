using Init.SIGePro.Manager.Logic.GestioneConsole.UrlAccesso;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Console
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsUrlAccessoConsoleService" in both code and config file together.
    [ServiceContract]
    public interface IWsUrlAccessoConsoleService
    {
        [OperationContract]
        ConfigurazioneUrlConsole GetUrlAccessoConsole(string token, string software);
    }
}
