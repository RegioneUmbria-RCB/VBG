using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo.MittentiDestinatari;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.LeggiProtocollo
{
    public class LeggiProtocolloOutputAdapter
    {
        ProtocolloService _wrapper;

        public LeggiProtocolloOutputAdapter(ProtocolloService wrapper)
        {
            _wrapper = wrapper;
        }


        public DatiProtocolloLettoResponseType Adatta(DettagliProtocolloRequest request)
        {
            var response = _wrapper.LeggiProtocollo(request);

            //var mittentiDestinatari = MittentiDestinatariFactory.Create(response.InfoGenerali.protoApProt, response.Mittenti, response.Destinatari, response.Uffici);
            var mittentiDestinatari = MittentiDestinatariFactory.Create(response);

            return new DatiProtocolloLettoResponseType
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
                Annullato = response.InfoGenerali.protoStato.GetValueOrDefault(0) == 1 ? IsAnnullato.si.ToString() : IsAnnullato.no.ToString(),
                Origine = response.InfoGenerali.protoApProt,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario(),
                Allegati = response.Documenti.Select(x => new AllegatoResponseType
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