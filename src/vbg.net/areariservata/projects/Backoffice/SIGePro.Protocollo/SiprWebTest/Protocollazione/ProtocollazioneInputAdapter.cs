using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.SiprWebTest.Classificazione;
using Init.SIGePro.Protocollo.SiprWebTest.Protocollazione.MittentiDestinatari;
using Init.SIGePro.Protocollo.SiprWebTest.Verticalizzazioni;

namespace Init.SIGePro.Protocollo.SiprWebTest.Protocollazione
{
    public class ProtocollazioneInputAdapter
    {
        ProtocolloLogs _logs;
        VerticalizzazioniConfiguration _verticalizzazione;
        DatiProtocolloIn _protoIn;
        IDatiProtocollo _datiProto;
        string _operatore;

        public ProtocollazioneInputAdapter(ProtocolloLogs logs, VerticalizzazioniConfiguration verticalizzazione, DatiProtocolloIn protoIn, IDatiProtocollo datiProto, string operatore)
        {
            _logs = logs;
            _verticalizzazione = verticalizzazione;
            _protoIn = protoIn;
            _datiProto = datiProto;
            _operatore = operatore;
        }

        public protDocumentoRequest Adatta()
        {
            _logs.Debug("CREAZIONE DELLA REQUEST GRAZIE ALL'ADATTATORE");

            _logs.Debug("CREAZIONE DELLA CONFIGURATION NELL'ADATTATORE");
            var classificaConf = new LivelliClassificaConfiguration(_protoIn.Classifica);

            _logs.Debug("CREAZIONE DELLA FACTORY MITTENTI / DESTINATARI NELL'ADATTATORE");
            var mittentiDestinatari = MittentiDestinatariFactory.Create(_datiProto, _logs, _verticalizzazione.CodiceCC);


            var request = new protDocumentoRequest
            {
                CodiceTipoDocumento = _protoIn.TipoDocumento,
                Livello1Classificazione = classificaConf.Livello1,
                Livello2Classificazione = classificaConf.Livello2,
                Livello3Classificazione = classificaConf.Livello3,
                Livello4Classificazione = classificaConf.Livello4,
                Oggetto = ReplaceLettereAccentate(_protoIn.Oggetto),
                Registro = (Registro_Type)Enum.Parse(typeof(Registro_Type), _protoIn.Flusso, true),
                utente = _operatore,
                Mittente = mittentiDestinatari.Mittente,
                Destinatari = mittentiDestinatari.Destinatari
            };

            if (mittentiDestinatari.DestinatariCC != null)
                request.DestinatariCC = mittentiDestinatari.DestinatariCC;

            if (!String.IsNullOrEmpty(_protoIn.NumProtMitt))
                request.NumProtMitt = _protoIn.NumProtMitt;
            if (!String.IsNullOrEmpty(_protoIn.DataProtMitt))
                request.DataProtMitt = _protoIn.DataProtMitt;

            _logs.Debug("FINE CREAZIONE DELLA REQUEST NELL'ADATTATORE");

            return request;

        }

        private string ReplaceLettereAccentate(string text)
        {
            text = text.Replace("ò", "o'");
            text = text.Replace("à", "a'");
            text = text.Replace("ù", "u'");
            text = text.Replace("è", "e'");
            text = text.Replace("ì", "i'");

            return text;
        }
    }
}
