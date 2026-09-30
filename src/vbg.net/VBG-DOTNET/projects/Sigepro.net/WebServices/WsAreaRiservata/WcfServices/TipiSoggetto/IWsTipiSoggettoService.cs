using Init.SIGePro.Manager.DTO.TipiSoggetto;
using System.Collections.Generic;
using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.TipiSoggetto
{


    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsTipiSoggettoService" in both code and config file together.
    [ServiceContract]
    public interface IWsTipiSoggettoService
    {
        [OperationContract]
        TipoSoggettoDto GetById(string token, int id);

        [OperationContract]
        TipoSoggettoDto GetByIdECodiceIntervento(string token, int idTipoSoggetto, int idIntervento);

        [OperationContract]
        IEnumerable<TipoSoggettoDto> GetObbligatori(string token, string software, int? codiceIntervento);

        [OperationContract]
        TipiSoggettoInterventoDto GetTipiSoggettoDaIdIntervento(string token, string software, int? codiceIntervento);

        [OperationContract]
        IEnumerable<TipoSoggettoDto> GetTipiSoggettoPersonaFisica(string token, string software, int? codiceIntervento);

        [OperationContract]
        IEnumerable<TipoSoggettoDto> GetTipiSoggettoPersonaGiurudica(string token, string software, int? codiceIntervento);
        [OperationContract]
        IEnumerable<int> GetIdTipiSoggettoCheRicevonoNotifiche(string token, IEnumerable<int> listaIdTipiSoggetto);
    }
}
