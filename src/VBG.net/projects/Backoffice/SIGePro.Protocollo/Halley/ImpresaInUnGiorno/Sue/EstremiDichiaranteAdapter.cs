using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Manager;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue
{
    public class EstremiDichiaranteAdapter
    {
        public static class Constants
        {
            public const EstremiDichiaranteQualifica QualificaDefault = EstremiDichiaranteQualifica.ALTROPREVISTODALLAVIGENTENORMATIVA;
            public const string SiglaCittadinanzaDefault = "IT";
            public const string CittadinanzaDefault = "ITALIA";
        }

        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public EstremiDichiaranteAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }

        public EstremiDichiarante Adatta()
        {
            var richiedente = _datiProtocollazioneService.Istanza.Richiedente;

            string codiceCittadinanza = Constants.SiglaCittadinanzaDefault;
            string cittadinanza = Constants.CittadinanzaDefault;

            if (_datiProtocollazioneService.Istanza.Richiedente.Cittadinanza != null)
            {
                if (!String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.Richiedente.Cittadinanza.Cf))
                    codiceCittadinanza = _datiProtocollazioneService.Istanza.Richiedente.Cittadinanza.Cf;

                cittadinanza = _datiProtocollazioneService.Istanza.Richiedente.Cittadinanza.Descrizione;

            }

            return new EstremiDichiarante
            {
                codicefiscale = richiedente.CODICEFISCALE,
                cognome = richiedente.NOMINATIVO,
                nazionalita = new Stato { codice = codiceCittadinanza, Value = cittadinanza },
                nome = richiedente.NOME,
                partitaiva = richiedente.PARTITAIVA,
                pec = richiedente.Pec,
                telefono = richiedente.TELEFONO,
                qualifica = GetQualifica(Constants.QualificaDefault)
            };
        }

        private EstremiDichiaranteQualifica GetQualifica(EstremiDichiaranteQualifica qualificaDefault)
        {

            if (String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.FKCODICESOGGETTO))
                return Constants.QualificaDefault;

            var riCaricheMgr = new RiCaricheMgr(_datiProtocollazioneService.Db);
            var riCarica = riCaricheMgr.GetRiCaricaByInQualitaDi(Convert.ToInt32(_datiProtocollazioneService.Istanza.FKCODICESOGGETTO), _datiProtocollazioneService.IdComune);

            if (riCarica == null)
                return Constants.QualificaDefault;

            try
            {
                return (EstremiDichiaranteQualifica)Enum.Parse(typeof(EstremiDichiaranteQualifica), riCarica.Descrizione.Replace(" ", ""));
            }
            catch
            {
                throw new Exception(String.Format("NON E' STATO POSSIBILE FARE IL PARSING DELLA QUALIFICA CODICE {0}, LA QUALIFICA NON E' PREVISTA DALLE SPECIFICHE", riCarica.Codice));
            }
        }
    }
}
