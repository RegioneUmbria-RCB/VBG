using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using PersonalLib2.Data;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;

namespace VBG.Backend.Protocollo.AppLogic.Legacy.Sigedo.Smistamenti
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
                smistamento = new SmistamentoInterno(conf);
            else
                throw new Exception(String.Format("FLUSSO {0} NON SUPPORTATO", flusso));

            return smistamento;
        }
    }
}
