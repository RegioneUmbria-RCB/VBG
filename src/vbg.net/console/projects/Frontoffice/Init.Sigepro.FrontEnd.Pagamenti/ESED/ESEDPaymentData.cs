using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.ESED
{
    [DataContract(Namespace = "", Name = "PaymentData")]
    public class ESEDPaymentData
    {
        private class Constants
        {
            public const string OK = "OK";
            public const string NOK = "NOK";
        }

        [DataMember(Order = 1)]
        public string PortaleID { get; set; }
        [DataMember(Order = 2)]
        public string NumeroOperazione { get; set; }
        [DataMember(Order = 3)]
        public string IDOrdine { get; set; }
        [DataMember(Order = 4)]
        public string DataOraOrdine { get; set; }
        [DataMember(Order = 5)]
        public string IDTransazione { get; set; }
        [DataMember(Order = 6)]
        public string DataOraTransazione { get; set; }
        [DataMember(Order = 7)]
        public string SistemaPagamento { get; set; }
        [DataMember(Order = 8)]
        public string SistemaPagamentoD { get; set; }
        [DataMember(Order = 9)]
        public string CircuitoAutorizzativo { get; set; }
        [DataMember(Order = 10)]
        public string CircuitoAutorizzativoD { get; set; }
        [DataMember(Order = 11)]
        public string ImportoTransato { get; set; }
        [DataMember(Order = 12)]
        public string ImportoCommissioni { get; set; }
        [DataMember(Order = 13)]
        public string ImportoCommissioniEnte { get; set; }
        [DataMember(Order = 14)]
        public string CodiceIUV { get; set; }
        [DataMember(Order = 15)]
        public string Esito { get; set; }
        [DataMember(Order = 16)]
        public string EsitoD { get; set; }
        [DataMember(Order = 17)]
        public string Autorizzazione { get; set; }
        [DataMember(Order = 18)]
        public string DatiSpecifici { get; set; }


        public ESEDCommitMessage ToCommitMessage()
        {
            return new ESEDCommitMessage
            {
                IDOrdine = this.IDOrdine,
                NumeroOperazione = this.NumeroOperazione,
                PortaleID = this.PortaleID,
                Commit = Constants.OK
            };
        }

        public ESEDCommitMessage ToCommitMessageError()
        {
            return new ESEDCommitMessage
            {
                IDOrdine = this.IDOrdine,
                NumeroOperazione = this.NumeroOperazione,
                PortaleID = this.PortaleID,
                Commit = Constants.NOK
            };
        }

    }
}
