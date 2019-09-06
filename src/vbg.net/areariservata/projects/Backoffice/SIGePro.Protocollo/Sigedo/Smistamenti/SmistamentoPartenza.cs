using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Sigedo.Factories;
using Init.SIGePro.Protocollo.Sigedo.Services;

namespace Init.SIGePro.Protocollo.Sigedo.Smistamenti
{
    public class SmistamentoPartenza : ISmistamentoFlusso
    {
        SmistamentoConfiguration _conf;

        public SmistamentoPartenza(SmistamentoConfiguration conf)
        {
            _conf = conf;
        }

        //public string UoSmistamento
        //{
        //    get { return GetSmistamento(); }
        //}

        public string GetUoSmistamento()
        {
            string uoSmistamento = "";

            if (!String.IsNullOrEmpty(_conf.Url))
            {

                _conf.ProtocolloLogs.InfoFormat("RESPONSABILE PROCEDIMENTO {0}", _conf.DatiProtocollazione.CodiceResponsabileProcedimentoIstanza);
                var smistamento = SigedoSmistamentoProvenienzaFactory.Create(_conf.Provenienza, _conf.Operatore, _conf.DatiProtocollazione);
                var operatoreSmistamento = smistamento.GetOperatoreSmistamento();

                _conf.ProtocolloLogs.InfoFormat("OPERATORE SMISTAMENTO: {0}", operatoreSmistamento);

                var uoSmistamentoSrv = new SigedoUoService(_conf.ProtocolloLogs, _conf.Serializer, _conf.Url, operatoreSmistamento);
                var personaSmistamento = uoSmistamentoSrv.GetPersonaSmistamento();

                if (personaSmistamento != null)
                {
                    uoSmistamento = personaSmistamento.CodiceUnitaOrganizzativa;

                    if (smistamento.IsSmistamentoAutomaticoDaOnline)
                    {
                        _conf.DatiProto.Amministrazione.AMMINISTRAZIONE = "";
                        _conf.DatiProto.Amministrazione.PROT_UO = uoSmistamento;
                        _conf.DatiProto.Amministrazione.EMAIL = "";
                    }
                }
                else
                    throw new Exception(String.Format("LO SMISTAMENTO PER L'OPERATORE {0} NON E' STATO TROVATO", operatoreSmistamento));
            }

            return uoSmistamento;

        }
    }
}
