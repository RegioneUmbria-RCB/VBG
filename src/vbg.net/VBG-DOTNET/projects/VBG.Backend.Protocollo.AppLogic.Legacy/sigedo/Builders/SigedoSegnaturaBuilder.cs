using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Configurations;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SegnaturaResult
    {
        public string SegnaturaSerializzata { get; private set; }
        public SigedoSegnaturaInput Segnatura { get; private set; }

        public SegnaturaResult(SigedoSegnaturaInput segnatura, string segnaturaSerializzata)
        {
            SegnaturaSerializzata = segnaturaSerializzata;
            Segnatura = segnatura;
        }
    }

    public class SigedoSegnaturaBuilder
    {
        private IDatiProtocollo _protoIn;
        private SigedoSegnaturaParamConfiguration _configuration;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;

        public SigedoSegnaturaBuilder(IDatiProtocollo protoIn, SigedoSegnaturaParamConfiguration configuration, ProtocolloLogs logs, 
            ProtocolloSerializer serializer)
        {
            _logs = logs;
            _serializer = serializer;
            _protoIn = protoIn;
            _configuration = configuration;
        }

        public ProtocolloAllegati CreaSegnaturaFittizia()
        {
            var segnatura = CreaSegnatura(Enumerable.Empty<ProtocolloAllegati>());
            _serializer.LogAndValidate(ProtocolloLogsConstants.ProfileFileName, segnatura);
            var profilePath = Path.Combine(_logs.Folder, ProtocolloLogsConstants.ProfileFileName);
            return new ProtocolloAllegati
            {
                NOMEFILE = ProtocolloLogsConstants.ProfileFileName,
                Descrizione = ProtocolloLogsConstants.ProfileFileName,
                MimeType = "text/xml",
                OGGETTO = File.ReadAllBytes(profilePath)
            };
        }

        public SegnaturaResult GetSegnatura(IEnumerable<ProtocolloAllegati> listAllegati)
        {
            var segnatura = CreaSegnatura(listAllegati);
            string segnaturaSerializzata = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);

            return new SegnaturaResult(segnatura, segnaturaSerializzata);
        }
          
        private SigedoSegnaturaInput CreaSegnatura(IEnumerable<ProtocolloAllegati> listAllegati)
        {
            var segnatura = new SigedoSegnaturaInput
            {
                Intestazione = CreaIntestazione(),
                Descrizione = CreaAllegati(listAllegati),
                ApplicativoProtocollo = CreaApplicativoProtocollo()
            };

            return segnatura;
        }

        #region BUILDER SEZIONI SEGNATURA

        private ApplicativoProtocollo CreaApplicativoProtocollo()
        {
            var appProto = new ApplicativoProtocollo();
            appProto.nome = _configuration.ParametriVerticalizzazioneSigedo.ApplicativoProtocollo;
            
            var appProtoBuilder = new SigedoSegnaturaApplicativoProtocolloBuilder(_logs, _serializer, _configuration);
            var listKvp = appProtoBuilder.DatiApplicativoProtocollo;

            var protoParams = new List<Parametro>();
            listKvp.ForEach(x => protoParams.Add(new Parametro { nome = x.Key, valore = x.Value }));

            appProto.Parametro = protoParams.ToArray();

            return appProto;
        }

        private Descrizione CreaAllegati(IEnumerable<ProtocolloAllegati> listAllegati)
        {
            var descrizione = new Descrizione();
            var docBuilder = new SigedoSegnaturaDocumentiBuilder(listAllegati, _configuration.ParametriVerticalizzazioneSigedo.TipoDocumentoPrincipale, _configuration.ParametriVerticalizzazioneSigedo.TipoDocumentoAllegato);
            
            descrizione = docBuilder.SegnaturaDescrizione;
            
            return descrizione;
        }

        private Intestazione CreaIntestazione()
        {
            var intestazione = new Intestazione();
            
            intestazione.Oggetto = _configuration.Oggetto;
            
            intestazione.Identificatore = new Identificatore
            {
                CodiceAmministrazione = _configuration.ParametriVerticalizzazioneSigedo.CodiceAmministrazione,
                CodiceAOO = _configuration.ParametriVerticalizzazioneSigedo.CodiceAoo,
                DataRegistrazione = _protoIn.ProtoIn.DataRegistrazione.HasValue ? _protoIn.ProtoIn.DataRegistrazione.Value.ToString("dd/MM/yyyy") : "0",
                Flusso = _configuration.Flusso,
                NumeroRegistrazione = "0"
            };

            if (!String.IsNullOrEmpty(_configuration.Classifica))
            {
                intestazione.Classifica = new Classifica
                {
                    CodiceTitolario = _configuration.Classifica,
                    CodiceAmministrazione = _configuration.ParametriVerticalizzazioneSigedo.CodiceAmministrazione,
                    CodiceAOO = _configuration.ParametriVerticalizzazioneSigedo.CodiceAoo
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
                var personaBuilder = new SigedoSegnaturaPersoneBuilder(_protoIn, _configuration.ParametriVerticalizzazioneSigedo.InviaCodiceFiscale);

                if (personaBuilder.SegnaturaPersona == null || personaBuilder.SegnaturaPersona.Count == 0)
                    throw new Exception("NON E' PRESENTE NESSUN MITTENTE");

                foreach (var persona in personaBuilder.SegnaturaPersona)
                    mittente.Add(new Mittente { Items = new object[]{ persona } });
            }
            else
            {
                if (_protoIn.Amministrazione == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL MITTENTE PER IL PROTOCOLLO IN PARTENZA / INTERNO");

                var amministrazioneBuilder = new SigedoSegnaturaAmministrazioniBuilder(_protoIn.Amministrazione, _configuration.ParametriVerticalizzazioneSigedo.CodiceAmministrazione);

                var aoo = new AOO { CodiceAOO = _configuration.ParametriVerticalizzazioneSigedo.CodiceAoo };

                var amministrazione = amministrazioneBuilder.SegnaturaAmministrazione;
                amministrazione.Items = new object[] { new UnitaOrganizzativa { id = "" } };

                mittente.Add(new Mittente { Items = new object[]{ amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }

            return mittente.ToArray();
        }

        private Destinatario[] CreaDestinatario()
        {
            var listDestinatario = new List<Destinatario>();

            if (_configuration.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
            {
                var personaBuilder = new SigedoSegnaturaPersoneBuilder(_protoIn, _configuration.ParametriVerticalizzazioneSigedo.InviaCodiceFiscale);

                if (personaBuilder.SegnaturaPersona == null || personaBuilder.SegnaturaPersona.Count == 0)
                    throw new Exception("NON E' PRESENTE NESSUN DESTINATARIO");

                foreach (var persona in personaBuilder.SegnaturaPersona)
                    listDestinatario.Add(new Destinatario { Items = new object[] { persona } });
            }
            else if (_configuration.Flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
            {
                if (_protoIn.Amministrazione == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL DESTINATARIO PER IL PROTOCOLLO IN ENTRATA / INTERNO");
                
                var amministrazioneBuilder = new SigedoSegnaturaAmministrazioniBuilder(_protoIn.Amministrazione, _configuration.ParametriVerticalizzazioneSigedo.CodiceAmministrazione);
                var aoo = new AOO { CodiceAOO = _configuration.ParametriVerticalizzazioneSigedo.CodiceAoo };
                listDestinatario.Add(new Destinatario { Items = new object[] { amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }
            else if (_configuration.Flusso == ProtocolloConstants.COD_INTERNO_DOCAREA)
            {
                if (_protoIn.AmministrazioniProtocollo== null || _protoIn.AmministrazioniProtocollo.Count == 0)
                    throw new Exception("NON E' STATO SELEZIONATO IL DESTINATARIO PER IL PROTOCOLLO INTERNO");

                var amministrazioneBuilder = new SigedoSegnaturaAmministrazioniBuilder(_protoIn.AmministrazioniProtocollo[0], _configuration.ParametriVerticalizzazioneSigedo.CodiceAmministrazione);
                var aoo = new AOO { CodiceAOO = _configuration.ParametriVerticalizzazioneSigedo.CodiceAoo };
                listDestinatario.Add(new Destinatario { Items = new object[] { amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }
            else
            {
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", _configuration.Flusso));
            }


            return listDestinatario.ToArray();
        }

        #endregion
    }
}
