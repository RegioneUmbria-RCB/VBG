using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Manager;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Sigedo.Smistamenti
{
    public class SmistamentoFlussoFactory
    {
        public static ISmistamentoFlusso Create(SmistamentoConfiguration conf, string flusso)
        { 
            ISmistamentoFlusso smistamento;
            if (flusso == ProtocolloConstants.COD_ARRIVO)
                smistamento = new SmistamentoArrivo(conf);
            else if (flusso == ProtocolloConstants.COD_PARTENZA)
                smistamento = new SmistamentoPartenza(conf);
            else if (flusso == ProtocolloConstants.COD_INTERNO)
                smistamento = new SmistamentoInterno(conf.DatiProto.Amministrazione.PROT_UO);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return smistamento;
        }
    }
}
