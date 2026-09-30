using System;
using System.Linq;
using System.IO;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo.MittentiDestinatari;
using Init.SIGePro.Manager;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        leggiProtocolloResponseRispostaLeggiProtocollo _response;

        public LeggiProtocolloResponseAdapter(leggiProtocolloResponseRispostaLeggiProtocollo response)
        {
            _response = response;
        }

        public DatiProtocolloLettoResponseType Adatta(DataBase db)
        {
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_response);

            var response = new DatiProtocolloLettoResponseType
            {
                AnnoProtocollo = _response.protocollo.anno,
                NumeroProtocollo = _response.protocollo.numero,
                DataProtocollo = DateTime.ParseExact(_response.protocollo.dataArrivoPartenza, "yyyyMMdd", null).ToString("dd/MM/yyyy"),
                Oggetto = _response.protocollo.oggetto,
                InCaricoA = mittentiDestinatari.InCaricoA,
                InCaricoA_Descrizione = mittentiDestinatari.InCaricoADescrizione,
                Origine = mittentiDestinatari.Flusso,
                MittentiDestinatari = mittentiDestinatari.GetMittenteDestinatario()
            };

            bool seAnnullato = _response.protocollo.annullamento != null && _response.protocollo.annullamento.annullato;

            response.Annullato = seAnnullato ? "SI" : "NO";

            if (seAnnullato)
                response.MotivoAnnullamento = _response.protocollo.annullamento.atto;

            if (_response.protocollo.classificazione != null)
            {
                response.Classifica = _response.protocollo.classificazione.titolario;
                response.Classifica = _response.protocollo.classificazione.descrizione;
            }

            if (_response.protocollo.altriDati != null && _response.protocollo.altriDati.tipoDocumento != null)
            {
                response.TipoDocumento = _response.protocollo.altriDati.tipoDocumento.codice;
                response.TipoDocumento_Descrizione = _response.protocollo.altriDati.tipoDocumento.descrizione;
            }

            if (_response.protocollo.documenti != null)
            {
                response.Allegati = _response.protocollo.documenti.Select(x => new AllegatoResponseType
                {
                    Serial = x.titolo,
                    Commento = x.nomeFile,
                    IDBase = x.progressivo,
                    TipoFile = Path.GetExtension(x.nomeFile),
                    ContentType = new OggettiMgr(db).GetContentType(x.nomeFile)
                }).ToArray();
            }

            return response;
        }
    }
}
