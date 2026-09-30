using System;
using VBG.Backend.Protocollo.AppLogic.Core.DocEr.Verticalizzazioni;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Core.DocEr.Fascicolazione
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
                var segnaturaSerializzata = _serializer.Serialize(ProtocolloLogsConstants.FascicolazioneRequestFileName, segnaturaType, Shared.Validation.ProtocolloValidation.TipiValidazione.XSD, "DocER/SegnaturaFascicolazioneRequest.xsd", true);

                return new SegnaturaFascicolazione(segnaturaType, segnaturaSerializzata);
            }
            catch (System.Exception ex)
            {
                _serializer.LogAndValidate(ProtocolloLogsConstants.FascicolazioneRequestFileName, segnaturaType);
                throw new System.Exception(String.Format("ERRORE GENERATO DURANTE LA VALIDAZIONE DELLA SEGNATURA DI FASCICOLAZIONE, ERRORE {0}, ", ex.Message));
            }
        }
    }
}
