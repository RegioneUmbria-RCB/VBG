using ProtocolloDocErGestioneDocumentaleService;
using System;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.GestioneDocumentale.Fascicolazione
{
    public class IsFascicolatoResponseAdapter
    {
        public static class Constants
        {
            public const string NumeroFascicolo = "NUM_FASCICOLO";
            public const string AnnoFascicolo = "ANNO_FASCICOLO";
            public const string ProgressivoFascicolo = "PROGR_FASCICOLO";
            public const string DescrizioneFascicolo = "DES_FASCICOLO";
            public const string CodiceEnte = "COD_ENTE";
            public const string CodiceAoo = "COD_AOO";
            public const string CodiceClassifica = "CLASSIFICA";
        }

        GestioneDocumentaleService _wrapper;
        string _idUnitaDocumentale;

        public IsFascicolatoResponseAdapter(GestioneDocumentaleService wrapper, string idUnitaDocumentale)
        {
            _wrapper = wrapper;
            _idUnitaDocumentale = idUnitaDocumentale;
        }

        public DatiProtocolloFascicolatoResponseType Adatta()
        {
            var response = _wrapper.LeggiDocumento(_idUnitaDocumentale);

            var dic = response.ToDictionary(x => x.key, y => y.value);

            if (String.IsNullOrEmpty(dic[Constants.AnnoFascicolo]) || String.IsNullOrEmpty(dic[Constants.ProgressivoFascicolo]))
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };

            var fascicolo = new Fascicolo
            {
                AnnoFascicolo = Convert.ToInt32(dic[Constants.AnnoFascicolo]),
                NumeroFascicolo = dic[Constants.ProgressivoFascicolo],
                Classifica = dic[Constants.CodiceClassifica],
                Oggetto = dic[Constants.DescrizioneFascicolo]
            };

            var metadataAdapter = new GestioneDocumentaleFascicoloMetadataAdapter(fascicolo, dic[Constants.CodiceEnte], dic[Constants.CodiceAoo]);
            var metadataRequest = metadataAdapter.Adatta();

            var responseFascicolo = _wrapper.GetFascicolo(metadataRequest);
            if (responseFascicolo == null)
                return new DatiProtocolloFascicolatoResponseType { Fascicolato = EnumFascicolatoType.no };

            return responseFascicolo.ToDatiProtocolloFascicolato();

        }
    }
}
