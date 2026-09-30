using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.StradarioComune;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Stradario
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IComuniService" in both code and config file together.
    [ServiceContract]
    public interface IWsStradarioService
    {
        [OperationContract]
        StradarioEstesoDto GetByCodiceStradario(string token, int codiceStradario);
        [OperationContract]
        StradarioEstesoDto GetByIndirizzo(string token, string codiceComune, string indirizzo);
        [OperationContract]
        List<StradarioDto> GetByMatchParziale(string token, string codiceComune, string comuneLocalizzazione, string indirizzo);
        [OperationContract]
        List<StradarioDto> GetByMatchParzialeIncludiDisabilitate(string token, string codiceComune, string comuneLocalizzazione, string indirizzo);
        [OperationContract]
        DatiComuneCompatto[] GetComuniLocalizzazioni(string token, string codiceComune);

        [OperationContract]
        List<ColoreStradarioDto> GetListaColori(string token);

        [OperationContract]
        StradarioDto GetStradarioByCodViario(string token, string codViario);
    }
}
