using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Insiel3.Services;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.Insiel3.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        DettagliProtocollo _response;

        public LeggiProtocolloOutputAdapter(DettagliProtocollo response)
        {
            _response = response;
        }


        public DatiProtocolloLetto Adatta()
        {
            //var mittentiDestinatari = MittentiDestinatariFactory.Create(_response.infoGenerali.protoApProt, _response.mittenti, _response.destinatari);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            string codiceClassifica = _response.classifiche.Length == 0 ? "" : _response.classifiche[_response.classifiche.Length - 1].codice;
            string descrizioneClassifica = _response.classifiche.Length == 0 ? "" : String.Format("[{0}] {1}", _response.classifiche[_response.classifiche.Length - 1].codice, _response.classifiche[_response.classifiche.Length - 1].descrizione);
            string idProtocollo = String.Format("{0};{1}", _response.infoGenerali.protoProgDoc, _response.infoGenerali.protoProgMovi);

            return new DatiProtocolloLetto
            {
                NumeroProtocollo = _response.infoGenerali.protoNumProt.Value.ToString(),
                DataProtocollo = _response.infoGenerali.protoDataOraAgg.Value.ToString("dd/MM/yyyy"),
                AnnoProtocollo = _response.infoGenerali.protoAnnoProt.Value.ToString(),
                IdProtocollo = idProtocollo,
                TipoDocumento = _response.infoGenerali.docCodTipoDoc,
                TipoDocumento_Descrizione = _response.infoGenerali.tipoDocDescTipoDoc,
                Oggetto = _response.infoGenerali.docDescOgge,
                InCaricoA = _response.infoGenerali.uffOpeCodAna,
                InCaricoA_Descrizione = _response.infoGenerali.uffOpeDescAna,
                Classifica = codiceClassifica,
                Classifica_Descrizione = descrizioneClassifica,
                Annullato = _response.infoGenerali.protoStato.GetValueOrDefault(0) == 1 ? ProtocolloEnum.IsAnnullato.si.ToString() : ProtocolloEnum.IsAnnullato.no.ToString(),
                Origine = _response.infoGenerali.protoApProt,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = _response.documenti.Select(x => new AllOut
                {
                    IDBase = x.idDoc.ToString(),
                    Serial = x.nome,
                    TipoFile = x.tipoDoc,
                    Commento = x.nome
                }).ToArray(),
            };
        }
    }
}