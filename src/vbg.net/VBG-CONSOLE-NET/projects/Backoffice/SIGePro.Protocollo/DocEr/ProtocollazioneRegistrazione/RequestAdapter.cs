using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Protocollo.DocEr.GestioneDocumentale;

namespace Init.SIGePro.Protocollo.DocEr.ProtocollazioneRegistrazione
{
    public class RequestAdapter
    {
        IDatiProtocollo _datiProto;
        VerticalizzazioniConfiguration _vert;
        ProtocolloSerializer _serializer;
        GestioneDocumentaleService _wrapperGestDoc;

        public RequestAdapter(IDatiProtocollo datiProto, VerticalizzazioniConfiguration vert, ProtocolloSerializer serializer, GestioneDocumentaleService wrapperGestDoc)
        {
            _datiProto = datiProto;
            _vert = vert;
            _serializer = serializer;
            _wrapperGestDoc = wrapperGestDoc;
        }

        public string Adatta()
        {
            var mittDest = MittentiDestinatariFactory.Create(_datiProto, _vert, _wrapperGestDoc);
            var classificaAdapter = new ClassificaAdapter(_datiProto.ProtoIn.Classifica, _vert);

            var segnatura = new SegnaturaType
            {
                Intestazione = new IntestazioneType
                {
                    Classifica = classificaAdapter.Adatta(),
                    Destinatari = mittDest.GetDestinatari(),
                    Flusso = mittDest.Flusso,
                    Mittenti = mittDest.GetMittenti(),
                    Oggetto = _datiProto.ProtoIn.Oggetto
                }
            };

            var smistamento = mittDest.GetSmistamento();

            if (smistamento != null)
                segnatura.Intestazione.Smistamento = smistamento;

            try
            {
                var segnaturaSerializzata = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura, ProtocolloValidation.TipiValidazione.XSD, "DocEr/SegnaturaProtocollazioneRequest.xsd", true);
                return segnaturaSerializzata;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DEI DATI DI PROTOCOLLO, ERRORE: {0}", ex.Message));
            }
        }
    }
}
