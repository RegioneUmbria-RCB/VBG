using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;

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
        VerticalizzazioniConfiguration _vert;

        public SegnaturaPecAdapter(IDatiPec datiPec, VerticalizzazioniConfiguration vert, ProtocolloSerializer serializer)
        {
            _serializer = serializer;
            _datiPec = datiPec;
            _vert = vert;
        }

        public SegnaturaPec Adatta()
        {
            var flusso = FlussoPecAdapter.Adatta();

            var segnatura = new SegnaturaType
            {
                Intestazione = new IntestazioneType
                {
                    Destinatari = _datiPec.GetDestinatari(),
                    Flusso = _datiPec.Flusso,
                    Oggetto = _datiPec.Oggetto
                }
            };

            if (_vert.UsaDocumentiPec) 
            {
                segnatura.Documenti = new DocumentiType
                {
                    Documento = _datiPec.GetDocumentoPrincipale(),
                    Annessi = _datiPec.GetAnnessi(),
                    Annotazioni = _datiPec.GetAnnotazioni()
                };
                
                var related = _datiPec.GetRelated();

                if (related != null && related.Count() > 0)
                    segnatura.Documenti.Allegati = related;
            }

            //string dati = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, segnatura, Validation.ProtocolloValidation.TipiValidazione.XSD, "DocER/SegnaturaPecRequest.xsd", true);
            string dati = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaPecRequestFileName, segnatura);

            return new SegnaturaPec(segnatura, dati);

        }
    }
}
