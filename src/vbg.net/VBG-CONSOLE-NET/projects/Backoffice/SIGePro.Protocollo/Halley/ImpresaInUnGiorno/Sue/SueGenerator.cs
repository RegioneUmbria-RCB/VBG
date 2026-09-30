using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Serialize;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue
{
    public class SueGenerator : ISuapSueGenerator
    {
        public static class Constants
        {
            public const string Versione = "1.0.0";
            public const string Identificativo = "SUE";
        }

        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;
        List<ProtocolloAllegati> _allegati;
        ProtocolloSerializer _serializer;
        string _cfPiva = "";
        string _dataCorrente = DateTime.Now.ToString("ddMMyyyy-HHmm");

        public string NomeFile { get { return CreaNomeFile(); } }

        public SueGenerator(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs, List<ProtocolloAllegati> allegati, ProtocolloSerializer serializer)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
            _serializer = serializer;
            _allegati = allegati;
            _cfPiva = String.IsNullOrEmpty(datiProtocollazioneService.Istanza.Richiedente.CODICEFISCALE) ? datiProtocollazioneService.Istanza.Richiedente.PARTITAIVA : datiProtocollazioneService.Istanza.Richiedente.CODICEFISCALE;
        }

        private string CreaNomeFile()
        {
            return String.Format("{0}-{1}.{2}.{3}.{4}", _cfPiva, _dataCorrente, _datiProtocollazioneService.CodiceIstanza, Constants.Identificativo, "xml");
        }

        public bool Genera()
        {
            try
            {
                _logs.InfoFormat("Creazione del file Sue.xml dell'istanza numero {0}", _datiProtocollazioneService.NumeroIstanza);
                var estremiDichiarante = new EstremiDichiaranteAdapter(_datiProtocollazioneService, _logs);
                var oggettoComunicazioneAdapter = new OggettoComunicazioneAdapter(_datiProtocollazioneService, _logs);
                var anagrafeRichiedenteAdapter = new AnagraficaRichiedenteAdapter(_datiProtocollazioneService, _logs);
                var estremiSuapAdapter = new EstremiSuapAdapter(_datiProtocollazioneService, _logs);
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
                        codicepratica = NomeFile,
                        dichiarante = estremiDichiarante.Adatta(),
                        domicilioelettronico = this._datiProtocollazioneService.Istanza.DOMICILIO_ELETTRONICO,
                        oggettocomunicazione = oggettoComunicazioneAdapter.Adatta(),
                        richiedente = anagrafeRichiedenteAdapter.Adatta(),
                        ufficiodestinatario = estremiSuapAdapter.Adatta()
                    },
                    struttura = adempimentoSuapAdapter.Adatta()
                };

                _serializer.Serialize(this.NomeFile, segnatura, Validation.ProtocolloValidation.TipiValidazione.XSD, "Halley/Sue.xsd", true);

                return true;
            }
            catch (Exception ex)
            {
                _logs.WarnFormat("ERRORE GENERATO DURANTE LA CREAZIONE DEL FILE SUE.XML, ERRORE: {0}", ex.Message);
                return false;
            }
        }
    }
}
