using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Insiel3.Allegati;
using Init.SIGePro.Protocollo.Insiel3.Verticalizzazioni;
using Init.SIGePro.Protocollo.Insiel3.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.ProtocolloInsielService3;
using Init.SIGePro.Protocollo.Insiel3.Services;

namespace Init.SIGePro.Protocollo.Insiel3.Protocollazione
{
    public class ProtocollazioneInputAdapter
    {
        InsielVerticalizzazioniConfiguration _verticalizzazioneInsiel;
        IDatiProtocollo _datiProto;
        DocumentoInsProto[] _docs;
        ProtocolloService _srv;

        public ProtocollazioneInputAdapter(InsielVerticalizzazioniConfiguration verticalizzazioneInsiel, IDatiProtocollo datiProto, DocumentoInsProto[] docs, ProtocolloService srv)
        {
            _srv = srv;
            _verticalizzazioneInsiel = verticalizzazioneInsiel;
            _docs = docs;
            _datiProto = datiProto;
        }

        public InserimentoProtocolloRequest Adatta(string codiceUfficio)
        {
            var mittentiDestinatari = ProtocollazioneFactory.Create(_datiProto.Flusso, _datiProto, _srv, _verticalizzazioneInsiel.TipoGestionePec);

            var request = new InserimentoProtocolloRequest
            {
                utente = new Utente
                {
                    Item = _verticalizzazioneInsiel.CodiceUtente,
                    ItemElementName = ItemChoiceType.codice
                },
                codiceUfficio = codiceUfficio,
                codiceRegistro = _verticalizzazioneInsiel.CodiceRegistro,
                codiceUfficioOperante = _verticalizzazioneInsiel.CodiceUfficioOperante,
                mittenti = mittentiDestinatari.GetMittenti(),
                destinatari = mittentiDestinatari.GetDestinatari(),
                oggetto = _datiProto.ProtoIn.Oggetto,
                estremiDocumento = new EstremiDocumento { tipo = _datiProto.ProtoIn.TipoDocumento },
                verso = mittentiDestinatari.Flusso,
                documenti = _docs,
                attivaInvioTelematico = mittentiDestinatari.InvioTelematicoAttivo,
                attivaInvioTelematicoSpecified = true,
                uffici = mittentiDestinatari.GetUffici()
            };

            if (!_verticalizzazioneInsiel.EscludiClassifica)
            {
                var classifica = _datiProto.ProtoIn.Classifica;

                if (!String.IsNullOrEmpty(_datiProto.ProtoIn.Classifica))
                    classifica = _datiProto.ProtoIn.Classifica.Replace("x", " ");

                request.classifiche = new Classifica[] { new Classifica { Item = classifica } };
            }

            return request;
        }
    }
}
