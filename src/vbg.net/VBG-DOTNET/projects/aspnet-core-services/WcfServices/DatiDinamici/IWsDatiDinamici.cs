using Init.SIGePro.Data;
using Init.SIGePro.Manager.DTO.DatiDinamici;
using Init.SIGePro.Manager.DTO.DatiDinamici.OnceOnly;
using VBG.DatiDinamici.Standard.Utils.CreazioneModelli.Serializables;
// using System.ServiceModel;

namespace Sigepro.net.WebServices.WsAreaRiservata.WcfServices.DatiDinamici
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the interface name "IWsDatiDinamici" in both code and config file together.
    [ServiceContract]
    public interface IWsDatiDinamici
    {
        [OperationContract]
        AutocompleteSearchResultDto GetCompletionListRicerchePlus(string token, int idCampo, string partial, List<ValoreFiltroRicercaDto> filtri);

        [OperationContract]
        IstanzeDyn2Dati[] GetDyn2DatiByCodiceIstanza(string token, int codiceIstanza);

        [OperationContract]
        IstanzeDyn2Dati[] GetDyn2DatiByIdModello(string token, int codiceIstanza, int idModello, int indiceCampo);

        [OperationContract]
        ListaModelliDinamiciDomandaDto GetModelliDinamiciDaInterventoEEndo(string token, GetModelliDinamiciDaInterventoEEndoRequest request);

        [OperationContract]
        StrutturaModelloDinamicoSerializzabileDto GetStrutturaModelloDinamico(string token, int idModello);

        [OperationContract]
        RisultatoRicercaDatiDinamiciDto InitializeControlRicerchePlus(string token, int idCampo, string valore);

        [OperationContract]
        void RecuperaDocumentiIstanzaCollegata(string token, int codiceIstanzaOrigine, int idDomandaDestinazione);

        [OperationContract]
        DecodificaDto[] GetDecodificheAttive(string token, string tabella);

        [OperationContract]
        FonteDatiOnceOnlyDto[] GetIdentificativiOnceOnlyByIdInterventoEndo(string token, int idIntervento, int[] listaIdEndo);

        [OperationContract]
        int? GetIdCampoDaNome(string token, string software, string nomeCampo);

        [OperationContract]
        int? GetIdModelloDaCodice(string token, string software, string codiceModello);

        [OperationContract]
        bool VerificaEsistenzaModelloDinamico(string token, int idModello);

        [OperationContract]
        IEnumerable<int> GetIdSchedeDaMarcareComeNonCompilate(string token, int idModello, IEnumerable<int> listaSchedeDellaDomanda);
    }
}
