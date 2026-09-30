using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.DocArea;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Protocollo.DocPro.Configurations;

namespace Init.SIGePro.Protocollo.DocPro.Builders
{
    public class DocProSegnaturaBuilder
    {
        private IDatiProtocollo _protoIn;
        private DocProSegnaturaParamConfiguration _configuration;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private List<ProtocolloAllegati> _listAllegati;

        public DocProSegnaturaBuilder(IDatiProtocollo protoIn, DocProSegnaturaParamConfiguration configuration, ProtocolloLogs logs,
            ProtocolloSerializer serializer, List<ProtocolloAllegati> listAllegati)
        {
            _logs = logs;
            _serializer = serializer;
            _protoIn = protoIn;
            _configuration = configuration;
            _listAllegati = listAllegati;
        }

        public void CreaSegnaturaFittizia()
        {
            var segnatura = CreaSegnatura();
            _serializer.Serialize(ProtocolloLogsConstants.ProfileFileName, segnatura);
            var profilePath = Path.Combine(_logs.Folder, ProtocolloLogsConstants.ProfileFileName);
            _listAllegati.Add(new ProtocolloAllegati
            {
                NOMEFILE = ProtocolloLogsConstants.ProfileFileName,
                Descrizione = ProtocolloLogsConstants.ProfileFileName,
                MimeType = "text/xml",
                OGGETTO = File.ReadAllBytes(profilePath)
            });
        }

        public string SerializzaSegnatura()
        {
            var segnatura = CreaSegnatura();
            string retVal = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura, ProtocolloValidation.TipiValidazione.XSD, ProtocolloLogsConstants.SegnaturaXsdFileName, true);
            return retVal;
        }

        private DocProSegnaturaInput CreaSegnatura()
        {
            var segnatura = new DocProSegnaturaInput
            {
                Intestazione = CreaIntestazione(),
                Descrizione = CreaAllegati(),
                ApplicativoProtocollo = CreaApplicativoProtocollo()
            };

            return segnatura;
        }

        #region BUILDER SEZIONI SEGNATURA

        private ApplicativoProtocollo CreaApplicativoProtocollo()
        {
            var appProto = new ApplicativoProtocollo();
            appProto.nome = _configuration.VertParams.ApplicativoProtocollo;

            var appProtoBuilder = new DocProSegnaturaApplicativoProtocolloBuilder(_configuration);
            var listKvp = appProtoBuilder.DatiApplicativoProtocollo;

            var protoParams = new List<Parametro>();
            listKvp.ForEach(x => protoParams.Add(new Parametro { nome = x.Key, valore = x.Value }));

            appProto.Parametro = protoParams.ToArray();

            return appProto;
        }

        private Descrizione CreaAllegati()
        {
            var descrizione = new Descrizione();
            var docBuilder = new DocProSegnaturaDocumentiBuilder(_listAllegati, _configuration.VertParams.TipoDocumentoPrincipale, _configuration.VertParams.TipoDocumentoAllegato);

            descrizione = docBuilder.SegnaturaDescrizione;

            return descrizione;
        }

        private Intestazione CreaIntestazione()
        {
            var intestazione = new Intestazione();

            intestazione.Oggetto = _configuration.Oggetto;

            intestazione.Identificatore = new Identificatore
            {
                CodiceAmministrazione = _configuration.VertParams.CodiceAmministrazione,
                CodiceAOO = _configuration.VertParams.CodiceAoo,
                DataRegistrazione = "0",
                Flusso = _configuration.Flusso,
                NumeroRegistrazione = "0"
            };

            if (!String.IsNullOrEmpty(_configuration.Classifica))
            {
                intestazione.Classifica = new Classifica
                {
                    CodiceTitolario = _configuration.Classifica,
                    CodiceAmministrazione = _configuration.VertParams.CodiceAmministrazione,
                    CodiceAOO = _configuration.VertParams.CodiceAoo
                };
            }

            intestazione.Mittente = CreaMittente();
            intestazione.Destinatario = CreaDestinatario();


            return intestazione;
        }

        private Mittente[] CreaMittente()
        {
            var mittente = new List<Mittente>();

            if (_configuration.Flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
            {
                var personaBuilder = new DocProSegnaturaPersoneBuilder(_protoIn/*, _configuration.DomicilioElettronico*/);

                if (personaBuilder.SegnaturaPersona == null || personaBuilder.SegnaturaPersona.Count == 0)
                    throw new Exception("MITTENTE NON PRESENTE");

                if (personaBuilder.SegnaturaPersona.Count > 1)
                    _logs.Warn("Si sta cercando di inserire più di un mittente, cosa in teoria non consentita dai web service standard doc pro");

                mittente.Add(new Mittente { Items = new object[] { personaBuilder.SegnaturaPersona[0] } });

                /*foreach (var persona in personaBuilder.SegnaturaPersona)
                    mittente.Add(new Mittente { Items = new object[] { persona } });*/
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL MITTENTE PER IL PROTOCOLLO IN PARTENZA / USCITA");

                var amministrazioneBuilder = new DocProSegnaturaAmministrazioniBuilder(_protoIn.Amministrazione, _configuration.VertParams.CodiceAmministrazione, String.Empty);

                var aoo = new AOO { CodiceAOO = _configuration.VertParams.CodiceAoo };

                mittente.Add(new Mittente { Items = new object[] { amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }

            return mittente.ToArray();
        }

        private Destinatario[] CreaDestinatario()
        {
            var listDestinatario = new List<Destinatario>();

            if (_configuration.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
            {
                var personaBuilder = new DocProSegnaturaPersoneBuilder(_protoIn/*, _configuration.DomicilioElettronico*/);

                if (personaBuilder.SegnaturaPersona == null || personaBuilder.SegnaturaPersona.Count == 0)
                    throw new Exception("DESTINATARIO NON PRESENTE");

                if (personaBuilder.SegnaturaPersona.Count > 1)
                    _logs.Warn("Si sta cercando di inserire più di un destinatario, cosa in teoria non consentita dai web service standard doc pro");

                listDestinatario.Add(new Destinatario { Items = new object[] { personaBuilder.SegnaturaPersona[0] } });

                /*foreach (var persona in personaBuilder.SegnaturaPersona)
                    listDestinatario.Add(new Destinatario { Items = new object[] { persona } });*/
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL DESTINATARIO PER IL PROTOCOLLO IN ENTRATA / INTERNO");

                var amm = _configuration.Flusso == ProtocolloConstants.COD_INTERNO_DOCAREA ? _protoIn.AmministrazioniProtocollo[0] : _protoIn.Amministrazione;
                var amministrazioneBuilder = new DocProSegnaturaAmministrazioniBuilder(amm, _configuration.VertParams.CodiceAmministrazione, String.Empty);
                var aoo = new AOO { CodiceAOO = _configuration.VertParams.CodiceAoo };
                listDestinatario.Add(new Destinatario { Items = new object[] { amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }
            return listDestinatario.ToArray();
        }

        #endregion
    }
}
