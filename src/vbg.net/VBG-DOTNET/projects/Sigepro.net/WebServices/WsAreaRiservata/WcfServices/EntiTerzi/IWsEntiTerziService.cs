using Init.SIGePro.Manager.Logic.GestioneEntiTerzi;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EntiTerzi
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsEntiTerziService" in both code and config file together.
    [ServiceContract]
    public interface IWsEntiTerziService
    {
        [OperationContract]
        ETDatiAmministrazione GetDatiAmministrazione(string token, int codiceAnagrafe);
        [OperationContract]
        ETPraticaEnteTerzo[] GetListaPratiche(string token, ETFiltriPraticheEntiTerzi filtri);
        [OperationContract]
        ETSoftware[] GetListaSoftwareConPratiche(string token, int codiceAnagrafe);
        [OperationContract]
        void MarcaPraticaComeElaborata(string token, int codiceIstanza, int codiceAnagrafe);
        [OperationContract]
        void MarcaPraticaComeNonElaborata(string token, int codiceIstanza, int codiceAnagrafe);
        [OperationContract]
        bool PraticaElaborata(string token, int codiceIstanza, int codiceAnagrafe);
        [OperationContract]
        bool PuoEffettuareMovimenti(string token, int codiceAnagrafe);
    }
}
