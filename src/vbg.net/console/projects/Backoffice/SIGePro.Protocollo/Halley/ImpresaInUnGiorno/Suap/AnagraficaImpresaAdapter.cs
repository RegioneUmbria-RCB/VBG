using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap
{
    public class AnagraficaImpresaAdapter
    {
        public static class Constants
        {
            public const FormaGiuridicaCodice FormaGiuridicaCodiceDefault = FormaGiuridicaCodice.XX;
        }
        ResolveDatiProtocollazioneService _datiProtocollazioneService;
        ProtocolloLogs _logs;

        public AnagraficaImpresaAdapter(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs logs)
        {
            if (datiProtocollazioneService.Istanza.AziendaRichiedente == null)
                throw new Exception("AZIENDA RICHIEDENTE NON VALORIZZATA");

            _datiProtocollazioneService = datiProtocollazioneService;
            _logs = logs;
        }

        public AnagraficaImpresa Adatta()
        {
            var indirizzi = new IndirizziConRecapitiAdapter(_datiProtocollazioneService, _logs);

            var res = new AnagraficaImpresa
            {
                ragionesociale = _datiProtocollazioneService.Istanza.AziendaRichiedente.NOMINATIVO,
                formagiuridica = new FormaGiuridica { codice = this.GetFormaGiuridica(Constants.FormaGiuridicaCodiceDefault) },
                codicefiscale = _datiProtocollazioneService.Istanza.AziendaRichiedente.CODICEFISCALE,
                partitaiva = _datiProtocollazioneService.Istanza.AziendaRichiedente.PARTITAIVA,
                indirizzo = indirizzi.Adatta(),
                legalerappresentante = new AnagraficaRappresentante
                {
                    carica = new Carica { codice = GetCarica() },
                    nome = _datiProtocollazioneService.Istanza.Richiedente.NOME,
                    cognome = _datiProtocollazioneService.Istanza.Richiedente.NOMINATIVO,
                    codicefiscale = _datiProtocollazioneService.Istanza.Richiedente.CODICEFISCALE
                }
            };

            if (!String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.AziendaRichiedente.NUMISCRREA))
                res.codiceREA = new CodiceREA { provincia = _datiProtocollazioneService.Istanza.AziendaRichiedente.PROVINCIAREA, Value = _datiProtocollazioneService.Istanza.AziendaRichiedente.NUMISCRREA };
            
            return res;
        }



        private CaricaCodice GetCarica()
        {
            if(String.IsNullOrEmpty(_datiProtocollazioneService.Istanza.FKCODICESOGGETTO))
                throw new Exception("CAMPO IN QUALITA' DI NON VALORIZZATO");

            var riCaricheMgr = new RiCaricheMgr(_datiProtocollazioneService.Db);
            var riCarica = riCaricheMgr.GetRiCaricaByInQualitaDi(Convert.ToInt32(_datiProtocollazioneService.Istanza.FKCODICESOGGETTO), _datiProtocollazioneService.IdComune);

            if (riCarica == null)
                throw new Exception("CONFIGURAZIONE NON CORRETTA, IL CAMPO IN QUALITA' DI NON E' STATO MAPPATO CORRETTAMENTE CON LE CARICHE");

            try
            {
                return (CaricaCodice)Enum.Parse(typeof(CaricaCodice), riCarica.Codice);
            }
            catch
            {
                throw new Exception(String.Format("NON E' STATO POSSIBILE FARE IL PARSING DELLA CARICA CODICE {0}, LA CARICA NON E' PREVISTA DALLE SPECIFICHE", riCarica.Codice));
            }

        }

        private FormaGiuridicaCodice GetFormaGiuridica(FormaGiuridicaCodice formaGiuridicaDefault)
        {
            var azienda = _datiProtocollazioneService.Istanza.AziendaRichiedente;

            if (String.IsNullOrEmpty(azienda.FORMAGIURIDICA))
                return formaGiuridicaDefault;

            var fgMgr = new FormeGiuridicheMgr(_datiProtocollazioneService.Db);
            var fg = fgMgr.GetById(Convert.ToInt32(azienda.FORMAGIURIDICA), _datiProtocollazioneService.IdComune);

            if (fg == null)
                return formaGiuridicaDefault;

            if (String.IsNullOrEmpty(fg.CODICECCIAA))
                return formaGiuridicaDefault;

            var riFgMgr = new RiFormeGiuridicheMgr(_datiProtocollazioneService.Db);
            var riFg = riFgMgr.GetById(fg.CODICECCIAA);

            if (riFg == null)
                return formaGiuridicaDefault;

            try
            {
                return (FormaGiuridicaCodice)Enum.Parse(typeof(FormaGiuridicaCodice), riFg.Codice);
            }
            catch
            {
                _logs.WarnFormat("Conversione della forma giuridica non avvenuta correttamente");
                return formaGiuridicaDefault;
            }
        }

    }
}
