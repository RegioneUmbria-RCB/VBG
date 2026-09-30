using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.AllegatiDomanda
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsCommissioni" in both code and config file together.
    [ServiceContract]
    public interface IWsAllegatiDomanda
    {
        [OperationContract]
        int SalvaAllegatoDomanda(string token, int idDomanda, int codiceOggetto);

        [OperationContract]
        void EliminaAllegatoDomanda(string token, int idDomanda, int codiceOggetto);

        [OperationContract]
        bool OggettoAppartieneADomanda(string token, int idDomanda, int codiceOggetto);

        [OperationContract]
        string GetChecksumOggetto(string token, int codiceOggetto);
    }
}
