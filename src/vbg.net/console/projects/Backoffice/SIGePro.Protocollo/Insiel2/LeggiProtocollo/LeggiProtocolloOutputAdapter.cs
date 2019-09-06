using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Insiel2.Services;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;
using Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Protocollo.WsDataClass;
using Init.SIGePro.Protocollo.ProtocolloEnumerators;

namespace Init.SIGePro.Protocollo.Insiel2.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        ProtocolloService _wrapper;

        public LeggiProtocolloOutputAdapter(ProtocolloService wrapper)
        {
            _wrapper = wrapper;
        }


        public DatiProtocolloLetto Adatta(DettagliProtocolloRequest request)
        {
            var response = _wrapper.LeggiProtocollo(request);

            //var mittentiDestinatari = MittentiDestinatariFactory.Create(response.InfoGenerali.protoApProt, response.Mittenti, response.Destinatari, response.Uffici);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(response);

            return new DatiProtocolloLetto
            {
                NumeroProtocollo = response.InfoGenerali.protoNumProt.Value.ToString(),
                DataProtocollo = response.InfoGenerali.protoDataOraAgg.Value.ToString("dd/MM/yyyy"),
                AnnoProtocollo = response.InfoGenerali.protoAnnoProt.Value.ToString(),
                IdProtocollo = String.Format("{0};{1}", response.InfoGenerali.protoProgDoc, response.InfoGenerali.protoProgMovi),
                TipoDocumento = response.InfoGenerali.docCodTipoDoc,
                TipoDocumento_Descrizione = response.InfoGenerali.tipoDocDescTipoDoc,
                Oggetto = response.InfoGenerali.docDescOgge,
                InCaricoA = response.InfoGenerali.regCodAna,
                InCaricoA_Descrizione = response.InfoGenerali.regDescAna,
                Classifica = response.Classifiche.Length == 0 ? "" : response.Classifiche[response.Classifiche.Length - 1].codClas,
                Classifica_Descrizione = response.Classifiche.Length == 0 ? "" : String.Format("[{0}] {1}", response.Classifiche[response.Classifiche.Length - 1].codClas, response.Classifiche[response.Classifiche.Length - 1].descClas),
                Annullato = response.InfoGenerali.protoStato.GetValueOrDefault(0) == 1 ? ProtocolloEnum.IsAnnullato.si.ToString() : ProtocolloEnum.IsAnnullato.no.ToString(),
                Origine = response.InfoGenerali.protoApProt,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = response.Documenti.Select(x => new AllOut
                {
                    IDBase = x.idDoc.Value.ToString(),
                    Serial = x.nome,
                    TipoFile = x.tipoDoc,
                    Commento = x.nome
                }).ToArray(),

            };
        }
    }
}