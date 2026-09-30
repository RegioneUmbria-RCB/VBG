using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Endoprocedimenti;
using Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Endoprocedimenti
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsEndoprocedimenti" in both code and config file together.
    [ServiceContract]
    public interface IWsEndoprocedimenti
    {
        
        [OperationContract]
        EndoprocedimentoIncompatibileDto[] GetEndoprocedimentiIncompatibili(string token, int[] listaIdEndoAttivati);
        
        /*
        [OperationContract]
        List<EndoprocedimentoDto> GetEndoprocedimentiList(string token, List<int> listaId);
        */
        /*
        [OperationContract]
        List<EndoBreveFrontoffice> GetEndoprocedimentiPropostiDaCodiceIntervento(string token, int codiceIntervento);
        */
        [OperationContract]
        EndoprocedimentoDto GetEndoprocedimentoById(string token, int codiceEndo, AmbitoRicerca ambitoRicercaDocumenti);

        [OperationContract]
        EndoprocedimentoMappatoDto GetEndoprocedimentoDaIdMappatura(string token, string codiceEndoRemoto);

        [OperationContract]
        EndoprocedimentiAreaRiservataDto GetListaEndoDaIdIntervento(string token, string codiceComune, int codiceIntervento, bool utenteTester);

        [OperationContract]
        NaturaBaseEndoprocedimentoDto GetNaturaBaseDaidEndoprocedimento(string token, int idEndoprocedimento);

        /*
        [OperationContract]
        List<TipiTitoloDto> GetTipiTitoloEndoDaCodiceInventario(string token, int codiceInventario);
        */

        [OperationContract]
        List<TipiTitoloPerCodiceInventario> GetTipiTitoloEndoDaListaCodiciInventario(string token, List<int> listaCodiciInventario);

        [OperationContract]
        TipiTitoloDto GetTipoTitoloById(string token, int codiceTipoTitolo);

        [OperationContract]
        List<AllegatiPerEndoprocedimentoDto> GetAllegatiEndoprocedimentiAreaRiservata(string token, List<int> codiciEndoSelezionati);
    }
}
