using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloDocErGestioneDocumentaleService;
using Init.SIGePro.Protocollo.DocEr.Fascicolazione;

namespace Init.SIGePro.Protocollo.DocEr.GestioneDocumentale.Fascicolazione
{
    public static class CercaFascicoliMetadataExtensions
    {
        public static DatiFasc ToDatiFascicolo(this KeyValuePair[] item)
        {
            var dic = item.ToDictionary(x => x.key, y => y.value);

            return new DatiFasc
            {
                AnnoFascicolo = dic[FascicolazioneMetadataConstants.ANNO_FASCICOLO],
                NumeroFascicolo = dic[FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO],
                ClassificaFascicolo = dic[FascicolazioneMetadataConstants.CLASSIFICA],
                DataFascicolo = "",
                OggettoFascicolo = dic[FascicolazioneMetadataConstants.DES_FASCICOLO]
            };
        }

        public static DatiProtocolloFascicolato ToDatiProtocolloFascicolato(this KeyValuePair[] item)
        {
            var dic = item.ToDictionary(x => x.key, y => y.value);

            return new DatiProtocolloFascicolato
            {
                AnnoFascicolo = dic[FascicolazioneMetadataConstants.ANNO_FASCICOLO],
                NumeroFascicolo = dic[FascicolazioneMetadataConstants.PROGRESSIVO_FASCICOLO],
                Classifica = dic[FascicolazioneMetadataConstants.CLASSIFICA],
                DataFascicolo = "",
                Oggetto = dic[FascicolazioneMetadataConstants.DES_FASCICOLO],
                Fascicolato = EnumFascicolato.si
            };
        }


    }
}
