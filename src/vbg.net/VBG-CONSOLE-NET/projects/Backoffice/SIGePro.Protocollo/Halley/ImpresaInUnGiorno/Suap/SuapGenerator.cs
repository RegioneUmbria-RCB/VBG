using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class SuapGenerator : ISuapSueGenerator
    {
        public static class Constants
        {
            public const string Versione = "1.0.1";
            public const string Identificativo = "SUAP";
            public const FormaGiuridicaCodice FormaGiuridicaCodiceDefault = FormaGiuridicaCodice.XX;
        }

        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;
        List<ProtocolloAllegati> _allegati;
        ProtocolloSerializer _serializer;
        string _cfPiva;
        string _dataCorrente = DateTime.Now.ToString("ddMMyyyy-HHmm");

        public string NomeFile { get { return CreaNomeFile(); } }

        public SuapGenerator(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs, List<ProtocolloAllegati> allegati, ProtocolloSerializer serializer)
        {

            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
            _allegati = allegati;
            _serializer = serializer;

            if (datiProtocollazioneService.Istanza.AziendaRichiedente == null)
                throw new Exception("AZIENDA NON PRESENTE");

            _cfPiva = String.IsNullOrEmpty(datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE) ? datiProtocollazioneService.Istanza.AziendaRichiedente.PARTITAIVA : datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE;
        }

        private string CreaNomeFile()
        {
            return String.Format("{0}-{1}.{2}.{3}.{4}", _cfPiva, _dataCorrente, _datiProtocollazioneService.CodiceIstanza, Constants.Identificativo, "xml");
        }

        public bool Genera()
        {
            try
            {
                _logs.InfoFormat("Creazione del file Suap.xml dell'istanza numero {0}", _datiProtocollazioneService.NumeroIstanza);
                var anagraficaImpresaAdapter = new AnagraficaImpresaAdapter(_datiProtocollazioneService, _logs);
                var estremiDichiaranteAdapter = new EstremiDichiaranteAdapter(_datiProtocollazioneService, _logs);
                var oggettoComunicazioneAdapter = new OggettoComunicazioneAdapter(_datiProtocollazioneService, _logs);
                var estremiSuap = new EstremiSuapAdapter(_datiProtocollazioneService, _logs);
                var adempimentoSuapAdapter = new AdempimentoSuapAdapter(_datiProtocollazioneService, _logs, _allegati);

                var segnatura = new RiepilogoPraticaSUAP
                {
                    infoschema = new VersioneSchema
                    {
                        versione = Constants.Versione,
                        data = DateTime.Now
                    },
                    intestazione = new Intestazione
                    {
                        codicepratica = String.Format("{0}-{1}.{2}", _cfPiva, _dataCorrente, _datiProtocollazioneService.CodiceIstanza),
                        dichiarante = estremiDichiaranteAdapter.Adatta(),
                        domicilioelettronico = this._datiProtocollazioneService.Istanza.DOMICILIO_ELETTRONICO,
                        impresa = anagraficaImpresaAdapter.Adatta(),
                        oggettocomunicazione = oggettoComunicazioneAdapter.Adatta(),
                        ufficiodestinatario = estremiSuap.Adatta()
                    },
                    struttura = adempimentoSuapAdapter.Adatta()
                };

                _serializer.Serialize(this.NomeFile, segnatura, Validation.ProtocolloValidation.TipiValidazione.XSD, "Halley/Suap.xsd", true);

                return true;
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA CREAZIONE DEL FILE SUAP.XML, ERRORE: {0}", ex.Message);
                return false;
            }
        }
    }
}
