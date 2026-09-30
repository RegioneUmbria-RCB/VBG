using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class ModelloAttivitàAdapter
    {
        private static class Constants
        {
            public const string EstensioneDocumentoPrincipale = ".pdf.p7m";
            public const string EstensioneDocumentoPrincipaleMda = ".mda.pdf.p7m";
        }

        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;
        ProtocolloAllegati _allegato;

        public ModelloAttivitàAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs, ProtocolloAllegati allegato)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
            _allegato = allegato;
        }

        public ModelloAttivita Adatta()
        {
            var codicePraticaTelematica = _datiProtocollazioneService.Istanza.CODICEPRATICATEL;

            if (String.IsNullOrEmpty(codicePraticaTelematica))
                codicePraticaTelematica = CreaCodicePraticaTelematica();

            if (_allegato.Extension.ToLower() != Constants.EstensioneDocumentoPrincipale)
                throw new Exception(String.Format("L'ESTENSIONE DELL'ALLEGATO PRIMARIO E' {0}, DEVE ESSERE PDF.P7M", _allegato.Extension));

            string nomeFile = String.Format("{0}.{1}.mda{2}", codicePraticaTelematica, _allegato.CODICEOGGETTO, _allegato.Extension);

            return new ModelloAttivita
            {
                nomefile = nomeFile,
                descrizione = _allegato.Descrizione,
                nomefileoriginale = _allegato.NOMEFILE,
                mime = _allegato.MimeType
            };
        }

        private string CreaCodicePraticaTelematica()
        {
            var cfPiva = String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE) ? _datiProtocollazioneService.Istanza.AziendaRichiedente.PARTITAIVA : _datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE;
            if (String.IsNullOrEmpty(cfPiva))
                throw new Exception("L'AZIENDA NON HA VALORIZZATO NE' IL CODICE FISCALE NE' LA PARTITA IVA");

            return String.Format("{0}-{1}", cfPiva, _datiProtocollazioneService.Istanza.DATA.Value.ToString("ddMMyyyy-HHmm"));
        }
    }
}
