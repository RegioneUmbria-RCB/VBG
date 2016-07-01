using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.Halley;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.Validation;
using Init.SIGePro.Data;
using System.IO;
using Init.SIGePro.Protocollo.Halley.Configurations;
using Init.SIGePro.Protocollo.Halley.Interfaces;
using Init.SIGePro.Protocollo.ProtocolloHalleyDizionarioServiceProxy;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public class HalleySegnaturaBuilder
    {
        public class SegnaturaRequest
        {
            public HalleySegnaturaInput Segnatura { get; private set; }
            public string SegnaturaString { get; private set; }

            public SegnaturaRequest(HalleySegnaturaInput segnatura, string segnaturaString)
            {
                Segnatura = segnatura;
                SegnaturaString = segnaturaString;
            }
        }

        private IDatiProtocollo _protoIn;
        private HalleySegnaturaParamConfiguration _configuration;
        private ProtocolloLogs _logs;
        private ProtocolloSerializer _serializer;
        private List<ProtocolloAllegati> _listAllegati;
        IFascicoloHalleyBuilder _datiFascicolo;

        public HalleySegnaturaBuilder(IDatiProtocollo protoIn, HalleySegnaturaParamConfiguration configuration, ProtocolloLogs logs,
            ProtocolloSerializer serializer, List<ProtocolloAllegati> listAllegati, IFascicoloHalleyBuilder datiFascicolo)
        {
            _logs = logs;
            _serializer = serializer;
            _protoIn = protoIn;
            _configuration = configuration;
            _listAllegati = listAllegati;
            _datiFascicolo = datiFascicolo;
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

        public SegnaturaRequest SerializzaSegnatura()
        {
            var segnatura = CreaSegnatura();
            string retVal = _serializer.Serialize(ProtocolloLogsConstants.SegnaturaXmlFileName, segnatura);

            return new SegnaturaRequest(segnatura, retVal);
        }

        private HalleySegnaturaInput CreaSegnatura()
        {
            var segnatura = new HalleySegnaturaInput
            {
                Intestazione = CreaIntestazione(),
                Descrizione = CreaAllegati(),
                ApplicativoProtocollo = CreaApplicativoProtocollo()
            };

            return segnatura;
        }

        #region BUILDER SEZIONI SEGNATURA

        private FascicoliFascicolo CreaFascicolo()
        {
            if (_datiFascicolo == null)
                return null;

            var retVal = _datiFascicolo.GetDatiFascicolo();
            return retVal;
        }

        private ApplicativoProtocollo CreaApplicativoProtocollo()
        {
            var appProto = new ApplicativoProtocollo();
            appProto.nome = _configuration.VertParams.ApplicativoProtocollo;

            var appProtoBuilder = new HalleySegnaturaApplicativoProtocolloBuilder(_configuration);
            var listKvp = appProtoBuilder.DatiApplicativoProtocollo;

            var protoParams = new List<Parametro>();
            listKvp.ForEach(x => protoParams.Add(new Parametro { nome = x.Key, valore = x.Value }));

            appProto.Parametro = protoParams.ToArray();

            return appProto;
        }

        private Descrizione CreaAllegati()
        {
            var descrizione = new Descrizione();
            var docBuilder = new HalleySegnaturaDocumentiBuilder(_listAllegati, _configuration.VertParams.TipoDocumentoPrincipale, _configuration.VertParams.TipoDocumentoAllegato);

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
                NumeroRegistrazione = "0",
            };

            if (!String.IsNullOrEmpty(_configuration.Classifica))
            {
                intestazione.Classifica = new Classifica
                {
                    CodiceTitolario = _configuration.Classifica,
                    CodiceAmministrazione = _configuration.VertParams.CodiceAmministrazione,
                    CodiceAOO = _configuration.VertParams.CodiceAmministrazione
                };
            }

            intestazione.Mittente = CreaMittente();
            intestazione.Destinatario = CreaDestinatario();
            
            var fascicolo = CreaFascicolo();
            if (fascicolo != null)
            {
                intestazione.Fascicolo = new Fascicolo { anno = fascicolo.anno, numero = fascicolo.id, Text = new string[] { fascicolo.Nome } };
                if (!String.IsNullOrEmpty(fascicolo.CodiceTitolario))
                    intestazione.Classifica = new Classifica
                    {
                        CodiceTitolario = fascicolo.CodiceTitolario,
                        CodiceAmministrazione = _configuration.VertParams.CodiceAmministrazione,
                        CodiceAOO = _configuration.VertParams.CodiceAmministrazione
                    };
            }
                

            return intestazione;
        }

        private Mittente[] CreaMittente()
        {
            var mittente = new List<Mittente>();

            if (_configuration.Flusso == ProtocolloConstants.COD_ARRIVO_DOCAREA)
            {
                var personaBuilder = new HalleySegnaturaPersoneBuilder(_protoIn);

                if (_configuration.VertParams.IsMultiMittDest)
                    return personaBuilder.GetMittentiMulti();
                else
                    return personaBuilder.GetMittenti();
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL MITTENTE PER IL PROTOCOLLO IN PARTENZA / USCITA");

                var amministrazioneBuilder = new HalleySegnaturaAmministrazioniBuilder(_protoIn.Amministrazione, _configuration.VertParams.CodiceAmministrazione, "");
                var amministrazione = amministrazioneBuilder.GetAmministrazione();
                var aoo = new AOO { CodiceAOO = _configuration.VertParams.CodiceAoo };

                return new Mittente[] { new Mittente { Items = new object[] { amministrazione, aoo } } };
            }
        }

        private Destinatario[] CreaDestinatario()
        {
            var destinatario = new List<Destinatario>();

            if (_configuration.Flusso == ProtocolloConstants.COD_PARTENZA_DOCAREA)
            {
                var personaBuilder = new HalleySegnaturaPersoneBuilder(_protoIn);

                if (_configuration.VertParams.IsMultiMittDest)
                    return personaBuilder.GetDestinatariMulti();
                else
                    return personaBuilder.GetDestinatari();
            }
            else
            {
                if (_protoIn.AmministrazioniProtocollo == null)
                    throw new Exception("NON E' STATO SELEZIONATO IL DESTINATARIO PER IL PROTOCOLLO IN ENTRATA / INTERNO");

                var amm = _configuration.Flusso == ProtocolloConstants.COD_INTERNO_DOCAREA ? _protoIn.AmministrazioniProtocollo[0] : _protoIn.Amministrazione;

                var amministrazioneBuilder = new HalleySegnaturaAmministrazioniBuilder(amm, _configuration.VertParams.CodiceAmministrazione, "");
                var amministrazione = amministrazioneBuilder.GetAmministrazione();
                var aoo = new AOO { CodiceAOO = _configuration.VertParams.CodiceAoo };

                return new Destinatario[] { new Destinatario { Items = new object[] { amministrazione, aoo } } };
            }
        }

        #endregion
    }
}
