// using Init.SIGePro.Manager.DTO;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.EndoFrontoffice
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsEndoFrontofficeService" in both code and config file together.
    [ServiceContract]
    public interface IWsEndoFrontofficeService
    {
        [OperationContract]
        List<FamigliaEndoFrontoffice> GetFamiglieEndoFrontoffice(string token, string software);

        [OperationContract]
        List<CategoriaEndoFrontoffice> GetCategorieEndoFrontoffice(string token, string software, int codiceFamiglia);

        [OperationContract]
        List<EndoBreveFrontoffice> GetListaEndoFrontoffice(string token, string software, int codiceCategoria);

        [OperationContract]
        RisultatoCaricamentoGerarchiaEndo GetGerarchiaEndo(string token, int valore, LivelloCaricamentoGerarchia livelloRicerca);

        [OperationContract]
        RisultatoRicercaTestualeEndo RicercaTestualeEndo(string token, string software, string partial, TipoRicercaEnum tipoRicerca);
    }
}
