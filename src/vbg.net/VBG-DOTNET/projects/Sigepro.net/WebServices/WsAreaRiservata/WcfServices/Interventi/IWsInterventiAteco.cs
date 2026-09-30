using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Manager.DTO.Common;
using Init.SIGePro.Manager.DTO.Interventi;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.Interventi
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsInterventiAteco" in both code and config file together.
    [ServiceContract]
    public interface IWsInterventiAteco
    {
        [OperationContract]
        List<int> CaricaListaIdGerarchiaAteco(string token, int id);
        
        
        [OperationContract]
        NodoAlberoInterventiDto GetAlberoProcDaIdAteco(string token, int idAteco, AmbitoRicerca ambitoRicerca);

        [OperationContract]
        Ateco GetDettagliAteco(string token, int id);

        [OperationContract]
        List<Ateco> GetNodiFiglioAteco(string token, int? idPadre);

        [OperationContract]
        List<Ateco> RicercaAteco(string token, string matchParziale, int matchCount, string modoRicerca, string tipoRicerca);

        [OperationContract]
        bool VerificaEsistenzaNodiCollegatiAIdAteco(string token, string software, int idAteco, AmbitoRicerca ambitoRicerca);
    }
}
