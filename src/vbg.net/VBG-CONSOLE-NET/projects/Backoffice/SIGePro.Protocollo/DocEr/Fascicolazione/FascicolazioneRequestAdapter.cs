using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.DocEr.Verticalizzazioni;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.DocEr.Fascicolazione
{
    public class FascicolazioneRequestAdapter
    {
        public class SegnaturaFascicolazione
        {
            public SegnaturaType Segnatura { get; private set; }
            public string SegnaturaSerializzata { get; private set; }

            public SegnaturaFascicolazione(SegnaturaType segn, string segnString)
            {
                this.Segnatura = segn;
                this.SegnaturaSerializzata = segnString;
            }
        }

        VerticalizzazioniConfiguration _vert;
        ProtocolloSerializer _serializer;

        public FascicolazioneRequestAdapter(VerticalizzazioniConfiguration vert, ProtocolloSerializer serializer)
        {
            _vert = vert;
            _serializer = serializer;
        }

        public SegnaturaFascicolazione Adatta(Fascicolo fascicolo)
        {
            var segnaturaType = new SegnaturaType
            {
                Intestazione = new IntestazioneType
                {
                    FascicoloPrimario = new FascicoloType
                    {
                        Anno = fascicolo.AnnoFascicolo.Value,
                        Classifica = fascicolo.Classifica,
                        Progressivo = fascicolo.NumeroFascicolo,
                        CodiceAmministrazione = _vert.CodiceAmministrazione,
                        CodiceAOO = _vert.CodiceAoo
                    }
                }
            };

            try
            {
                var segnaturaSerializzata = _serializer.Serialize(ProtocolloLogsConstants.FascicolazioneRequestFileName, segnaturaType, Validation.ProtocolloValidation.TipiValidazione.XSD, "DocER/SegnaturaFascicolazioneRequest.xsd", true);

                return new SegnaturaFascicolazione(segnaturaType, segnaturaSerializzata);
            }
            catch (System.Exception ex)
            {
                _serializer.Serialize(ProtocolloLogsConstants.FascicolazioneRequestFileName, segnaturaType);
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DELLA SEGNATURA DI FASCICOLAZIONE, ERRORE {0}, ", ex.Message));
            }
        }
    }
}
