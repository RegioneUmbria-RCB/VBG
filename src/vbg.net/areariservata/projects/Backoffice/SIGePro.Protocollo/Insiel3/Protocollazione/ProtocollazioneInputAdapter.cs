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
        ProtocolloLogs _logs;

        public ProtocollazioneInputAdapter(InsielVerticalizzazioniConfiguration verticalizzazioneInsiel, IDatiProtocollo datiProto, DocumentoInsProto[] docs, ProtocolloService srv, ProtocolloLogs logs)
        {
            _srv = srv;
            _verticalizzazioneInsiel = verticalizzazioneInsiel;
            _docs = docs;
            _datiProto = datiProto;
            _logs = logs;
        }

        /// <summary>
        /// Il metodo si occupa di adattare i dati della protocollazione alla request che si aspetta il protocollo di Insiel.
        /// codiceUfficio: valorizza la proprietà InserimentoProtocolloRequest.codiceUfficio
        /// valorizzaDataRicezioneSpedizione: se true allora valorizza InserimentoProtocolloRequest.dataRicezioneSpedizione con _datiProto.ProtoIn.DataRegistrazione.GetValueOrDefault(DateTime.Now);
        /// </summary>
        public InserimentoProtocolloRequest Adatta(string codiceUfficio, bool valorizzaDataRicezioneSpedizione)
        {
            var mittentiDestinatari = ProtocollazioneFactory.Create(_datiProto.Flusso, _datiProto, _srv, _verticalizzazioneInsiel, _logs);
            
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
                uffici = mittentiDestinatari.GetUffici(),
            };

            if (valorizzaDataRicezioneSpedizione) {
                request.dataRicezioneSpedizione = _datiProto.ProtoIn.DataRegistrazione.GetValueOrDefault(DateTime.Now);
                request.dataRicezioneSpedizioneSpecified = true;
            }

            if (!_verticalizzazioneInsiel.EscludiClassifica)
            {
                var classificaAdapter = new ClassificaAdapter();
                var classifica = classificaAdapter.Adatta(_verticalizzazioneInsiel.UsaLivelliClassifica, _datiProto.ProtoIn.Classifica);

                request.classifiche = new Classifica[] { classifica };
            }

            return request;
        }
    }
}
