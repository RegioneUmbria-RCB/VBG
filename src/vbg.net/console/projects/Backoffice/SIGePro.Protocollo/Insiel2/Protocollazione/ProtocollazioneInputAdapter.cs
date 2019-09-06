using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Insiel2.Allegati;
using Init.SIGePro.Protocollo.Insiel2.Verticalizzazioni;
using Init.SIGePro.Protocollo.Insiel2.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.Protocollazione
{
    public class ProtocollazioneInputAdapter
    {
        InsielVerticalizzazioniConfiguration _verticalizzazioneInsiel;
        IDatiProtocollo _datiProto;
        ProtocolloLogs _logs;
        documentoInsProto[] _docs;

        public ProtocollazioneInputAdapter(InsielVerticalizzazioniConfiguration verticalizzazioneInsiel, IDatiProtocollo datiProto, ProtocolloLogs logs, documentoInsProto[] docs)
        {
            _logs = logs;
            _verticalizzazioneInsiel = verticalizzazioneInsiel;
            _docs = docs;

            if (String.IsNullOrEmpty(datiProto.Amministrazione.PROT_UO))
                throw new Exception(String.Format("NON E' STATA SPECIFICATA L'UO (CODICE UFFICIO) NELL'AMMINISTRAZIONE ({0}) {1}", datiProto.Amministrazione.CODICEAMMINISTRAZIONE, datiProto.Amministrazione.AMMINISTRAZIONE));

            if (String.IsNullOrEmpty(datiProto.Amministrazione.PROT_RUOLO))
                throw new Exception(String.Format("NON E' STATO SPECIFICATO IL REGISTRO (RUOLO) NELL'AMMINISTRAZIONE ({0}) {1}", datiProto.Amministrazione.CODICEAMMINISTRAZIONE, datiProto.Amministrazione.AMMINISTRAZIONE));

            _datiProto = datiProto;
        }

        public InserimentoProtocolloRequest Adatta(string codiceUfficio)
        {
            var mittentiDestinatari = ProtocollazioneMittentiDestinatariFactory.Create(_datiProto, _logs);

            var request = new InserimentoProtocolloRequest
            {
                codice_ufficio = codiceUfficio,
                codice_registro = _verticalizzazioneInsiel.CodiceRegistro,
                codice_ufficio_operante = _verticalizzazioneInsiel.CodiceUfficioOperante,
                mittenti = mittentiDestinatari.GetMittenti(),
                destinatari = mittentiDestinatari.GetDestinatari(),
                oggetto = _datiProto.ProtoIn.Oggetto,
                estremi_documento = new EstremiDocumento { tipo = _datiProto.ProtoIn.TipoDocumento },
                verso = mittentiDestinatari.Flusso,
                documenti = _docs,
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
