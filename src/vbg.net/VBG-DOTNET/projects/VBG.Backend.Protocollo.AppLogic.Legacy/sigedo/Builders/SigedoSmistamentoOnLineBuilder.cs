using SIGePro.Manager.VerticalizzazioniBase;
using System;
using VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Interfacce;
using VBG.Backend.Protocollo.AppLogic.Shared.Factories;
using VBG.Backend.Protocollo.AppLogic.Shared.Services;
using VBG.Backend.Protocollo.Verticalizzazioni.Shared;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Builders
{
    public class SigedoSmistamentoOnLineBuilder : ISmistamentoProvenienza
    {
        ResolveDatiProtocollazioneService _datiProtocollazione;

        public SigedoSmistamentoOnLineBuilder(ResolveDatiProtocollazioneService datiProtocollazione)
        {
            _datiProtocollazione = datiProtocollazione;
        }

        public string GetOperatoreSmistamento(IVerticalizzazioniFactory verticalizzazioniFactory)
        {
            if (!_datiProtocollazione.CodiceResponsabileProcedimentoIstanza.HasValue)
                throw new Exception("CODICE RESPONSABILE ISTANZA NON VALORIZZATO, NON E' STATO QUINDI POSSIBILE RECUPERARE LO SMISTAMENTO");


            var vertAttivo = verticalizzazioniFactory.Create<VerticalizzazioneProtocolloAttivo>(_datiProtocollazione.IdComuneAlias, _datiProtocollazione.Software, _datiProtocollazione.CodiceComune);
            //int result;
            //var codiceOperatoreFoIsParsable = Int32.TryParse(vertAttivo.Codoperatorefo, out result);
            //if (!codiceOperatoreFoIsParsable)
            //{
            //    throw new Exception($"IL PARAMETRO CODOPERATOREFO, VALORIZZATO A {vertAttivo.Codoperatorefo} DEVE ESSERE UN NUMERO");
            //}

            var operatore = OperatoreProtocolloFactory.Create(_datiProtocollazione.Db, vertAttivo.Operatore, Convert.ToInt32(_datiProtocollazione.CodiceResponsabileProcedimentoIstanza), _datiProtocollazione.IdComune);
            //var operatore = OperatoreProtocolloFactory.Create(_datiProtocollazione.Db, vertAttivo.Operatore, Convert.ToInt32(vertAttivo.Codoperatorefo), _datiProtocollazione.IdComune);

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
