using Init.SIGePro.Manager.Logic.GestioneMercati;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Autorizzazioni
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsAutorizzazioniService" in both code and config file together.
    [ServiceContract]
    public interface IWsAutorizzazioniService
    {
        [OperationContract]
        DettagliAutorizzazione GetAutorizzazione(string token, int idAutorizzazione, int codiceManifestazione, int? codiceUso);
        [OperationContract]
        DettagliAutorizzazione GetAutorizzazioneConCodiceIntervento(string token, int idAutorizzazione, int codiceIntervento);
        [OperationContract]
        List<ListaAutorizzazioniItem> GetAutorizzazioni(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceManifestazione, int? codiceUso);
        [OperationContract]
        List<ListaAutorizzazioniItem> GetAutorizzazioniConCodiceIntervento(string token, string[] registri, int codiceAnagrafe, string espressioneFormattazioneDati, int codiceIntervento);
        [OperationContract]
        List<EnteAutorizzazione> GetEnti(string token);
    }
}
