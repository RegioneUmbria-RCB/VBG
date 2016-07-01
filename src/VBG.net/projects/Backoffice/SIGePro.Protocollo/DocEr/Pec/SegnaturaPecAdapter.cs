using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.Pec
{
    public class SegnaturaPecAdapter
    {
        public class SegnaturaPec
        {
            public SegnaturaType Segnatura { get; private set; }
            public string SegnaturaSerializzata { get; private set; }

            public SegnaturaPec(SegnaturaType segn, string segnString)
            {
                this.Segnatura = segn;
                this.SegnaturaSerializzata = segnString;
            }
        }

        ProtocolloSerializer _serializer;
        IDatiPec _datiPec;

        public SegnaturaPecAdapter(IDatiPec datiPec, ProtocolloSerializer serializer)
        {
            _serializer = serializer;
            _datiPec = datiPec;
        }

        public SegnaturaPec Adatta()
        {
            var flusso = FlussoPecAdapter.Adatta();

            var segnatura = new SegnaturaType
            {
                Intestazione = new IntestazioneType
                {
                    Destinatari = _datiPec.Destinatari,
                    Flusso = _datiPec.Flusso,
                    Oggetto = _datiPec.Oggetto
                }
            };

            string dati = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, segnatura, Validation.ProtocolloValidation.TipiValidazione.XSD, "DocER/SegnaturaPecRequest.xsd", true);

            return new SegnaturaPec(segnatura, dati);

        }
    }
}
