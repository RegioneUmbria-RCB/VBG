using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Interfacce;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using Init.SIGePro.Manager;
using Init.SIGePro.Verticalizzazioni;
using Init.SIGePro.Protocollo.ProtocolloFactories;
using Init.SIGePro.Protocollo.ProtocolloServices.OperatoreProtocollo;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.Sigedo.Builders
{
    public class SigedoSmistamentoOnLineBuilder : ISmistamentoProvenienza
    {
        ResolveDatiProtocollazioneService _datiProtocollazione;

        public SigedoSmistamentoOnLineBuilder(ResolveDatiProtocollazioneService datiProtocollazione)
        {
            _datiProtocollazione = datiProtocollazione;
        }

        public string GetOperatoreSmistamento()
        {
            if (!_datiProtocollazione.CodiceResponsabileProcedimentoIstanza.HasValue)
                throw new Exception("CODICE RESPONSABILE ISTANZA NON VALORIZZATO, NON E' STATO QUINDI POSSIBILE RECUPERARE LO SMISTAMENTO");

            var vertAttivo = new VerticalizzazioneProtocolloAttivo(_datiProtocollazione.IdComuneAlias, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
            var operatore = OperatoreProtocolloFactory.Create(_datiProtocollazione.Db, vertAttivo.Operatore, Convert.ToInt32(_datiProtocollazione.CodiceResponsabileProcedimentoIstanza), _datiProtocollazione.IdComune);

            if (operatore.IsOperatoreDefault)
                throw new Exception("PER UTILIZZARE IL RECUPERO DELLO SMISTAMENTO DAL WEB SERVICE E' NECESSARIO USARE, SUL PARAMETRO OPERATORE DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO, UN SEGNALIBRO");

            if (String.IsNullOrEmpty(operatore.CodiceOperatore))
                throw new Exception("L'OPERATORE NON HA VALORIZZATO IL CODICE INDICATO NEL SEGNALIBRO DEL PARAMETRO OPERATORE DELLA VERTICALIZZAZIONE PROTOCOLLO_ATTIVO");

            var retVal = operatore.CodiceOperatore.Substring(1);

            return retVal;
        }

        #region ISmistamentoSigedo Members


        public bool IsSmistamentoAutomaticoDaOnline
        {
            get { return true; }
        }

        #endregion
    }
}
