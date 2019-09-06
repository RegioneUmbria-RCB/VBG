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
using Init.SIGePro.Protocollo.DocArea.Configurations;
using Init.SIGePro.Protocollo.DocArea.Interfaces;
using Init.SIGePro.Protocollo.DocArea.Builders.MittentiDestinatari;
using Init.SIGePro.Protocollo.DocArea.Builders.ApplicativoProtocolloBuilder;

namespace Init.SIGePro.Protocollo.DocArea.Builders
{
    public class DocAreaSegnaturaBuilder
    {
        private IDatiProtocollo _protoIn;
        private DocAreaSegnaturaParamConfiguration _configuration;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private List<ProtocolloAllegati> _listAllegati;
        private IDocAreaSegnaturaPersoneBuilder _personeBuilder;
        private ITipoFornitoreProtocolloDocArea _tipoFornitore;

        public DocAreaSegnaturaBuilder(IDatiProtocollo protoIn, DocAreaSegnaturaParamConfiguration configuration, ProtocolloLogs logs,
            ProtocolloSerializer serializer, List<ProtocolloAllegati> listAllegati)
        {
            _logs = logs;
            _serializer = serializer;
            _protoIn = protoIn;
            _configuration = configuration;
            _listAllegati = listAllegati;
            _tipoFornitore = TipoFornitoreProtocolloFactory.Create(_configuration);

            var appProtoBuilder = TipoFornitoreProtocolloFactory.Create(_configuration);

            if (configuration.VertParams.MultiMittenteDestinatario)
                _personeBuilder = new DocAreaSegnaturaMultiPersonaBuilder(logs);
            else
                _personeBuilder = new DocAreaSegnaturaPersonaBuilder(logs);
             
             //_personeBuilder = new DocAreaSegnaturaPersonaBuilder(logs);

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
            //string retVal = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura, ProtocolloValidation.TipiValidazione.XSD, ProtocolloLogsConstants.SegnaturaXsdFileName, true);
            string retVal = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);
            return retVal;
        }

        private DocAreaSegnaturaInput CreaSegnatura()
        {
            var segnatura = new DocAreaSegnaturaInput
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
            var parametri = _tipoFornitore.GetParametriApplicativoProtocollo();
            return new ApplicativoProtocollo { nome = _configuration.VertParams.ApplicativoProtocollo, Parametro = parametri };
        }

        private Descrizione CreaAllegati()
        {
            var descrizione = new Descrizione();
            var docBuilder = new DocAreaSegnaturaDocumentiBuilder(_listAllegati, _tipoFornitore.TipoProtocolloDocumentoPrimario, _tipoFornitore.TipoProtocolloAllegati);

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
                var personeList = _personeBuilder.GetMittenteDestinatario(_protoIn, _configuration.VertParams.UsaDenominazionePg, _configuration.DomicilioElettronico);

                if (personeList == null || personeList.Count == 0)
                    throw new Exception("MITTENTE NON PRESENTE");

                if (personeList.Count > 1)
                    _logs.Warn("Si sta cercando di inserire più di un mittente, cosa in teoria non consentita dai web service standard doc area");
                
                /*foreach (var persona in personeList)
                    mittente.Add(new Mittente { Items = new object[] { persona } });*/
                
                //if(!_configuration.VertParams.MultiMittenteDestinatario)
                //    mittente.Add(new Mittente { Items = new object[] { personeList[0] } });
                //else
                //    mittente.Add(new Mittente { Items = personeList.ToArray() });

                mittente.Add(new Mittente { Items = new object[] { personeList[0] } });
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL MITTENTE PER IL PROTOCOLLO IN PARTENZA / USCITA");

                var amministrazioneBuilder = new DocAreaSegnaturaAmministrazioniBuilder(_protoIn.Amministrazione, _configuration.VertParams.CodiceAmministrazione, String.Empty);

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
                //var personaBuilder = new DocAreaSegnaturaPersoneBuilder(_protoIn, _configuration.DomicilioElettronico, _logs);
                var personeList = _personeBuilder.GetMittenteDestinatario(_protoIn, _configuration.VertParams.UsaDenominazionePg, _configuration.DomicilioElettronico);

                if (personeList == null || personeList.Count == 0)
                    throw new Exception("DESTINATARIO NON PRESENTE");

                if (personeList.Count > 1)
                    _logs.Warn("Si sta cercando di inserire più di un destinatario, cosa in teoria non consentita dai web service standard doc area");

                /*foreach (var persona in personeList)
                    listDestinatario.Add(new Destinatario { Items = new object[] { persona } });*/

                //if (!_configuration.VertParams.MultiMittenteDestinatario)
                //    listDestinatario.Add(new Destinatario { Items = new object[] { personeList[0] } });
                //else
                //    listDestinatario.Add(new Destinatario { Items = personeList.ToArray() });

                listDestinatario.Add(new Destinatario { Items = new object[] { personeList[0] } });
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL DESTINATARIO PER IL PROTOCOLLO IN ENTRATA / INTERNO");

                var amm = _configuration.Flusso == ProtocolloConstants.COD_INTERNO_DOCAREA ? _protoIn.AmministrazioniProtocollo[0] : _protoIn.Amministrazione;
                var amministrazioneBuilder = new DocAreaSegnaturaAmministrazioniBuilder(amm, _configuration.VertParams.CodiceAmministrazione, String.Empty);
                var aoo = new AOO { CodiceAOO = _configuration.VertParams.CodiceAoo };
                listDestinatario.Add(new Destinatario { Items = new object[] { amministrazioneBuilder.SegnaturaAmministrazione, aoo } });
            }
            return listDestinatario.ToArray();
        }

        #endregion
    }
}
