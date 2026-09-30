using Init.SIGePro.Manager.Logic.RicercaPratiche;
using System;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.RicercaPratiche
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsRicercaPraticheService" in both code and config file together.
    [ServiceContract]
    public interface IWsRicercaPraticheService
    {
        [OperationContract]
        RisultatoRicercaPratiche RicercaPraticaDaNumeroIstanza(string token, string software, string codiceComune, string numeroIstanza, string cfUtenteRicerca);

        [OperationContract]
        RisultatoRicercaPratiche RicercaPraticaDaEstremiProtocollo(string token, string software, string codiceComune, string numeroProtocollo, DateTime? dataProtocollo, string cfUtenteRicerca);
    }
}
