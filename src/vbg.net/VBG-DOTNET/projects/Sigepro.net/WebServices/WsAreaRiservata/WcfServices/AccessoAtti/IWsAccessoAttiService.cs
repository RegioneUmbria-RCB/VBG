using Init.SIGePro.Manager.Logic.GestioneAccessoAtti;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AccessoAtti
{
    [ServiceContract]
    public interface IWsAccessoAttiService
    {
        [OperationContract]
        PraticaAccessoAtti[] GetListaAtti(string token, int codiceAnagrafe, string software);

        [OperationContract]
        void LogAccessoAtti(string token, int idAccessoAtti, int codiceAnagrafe, string uuidIstanza);

        [OperationContract]
        int GetLivelloAccessoDocumenti(string token, int idAccessoAtti, string uuidIstanza);

        [OperationContract]
        string GetNomeFileZipPerDownloadDocumenti(string token, int idAccessoAtti, string uuidPratica);
    }
}
