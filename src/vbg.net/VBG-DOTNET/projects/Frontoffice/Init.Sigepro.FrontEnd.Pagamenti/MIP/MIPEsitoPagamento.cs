using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;


namespace Init.Sigepro.FrontEnd.Pagamenti.MIP
{
    /*
    <PaymentData>
        <PortaleID>TEST_PAGAMENTI_AR_FI</PortaleID>
        <NumeroOperazione>753602c7-6913-4403-a198-2c3c93bc56da</NumeroOperazione>
        <IDOrdine>508061256533</IDOrdine>
        <DataOraOrdine>20150806125647</DataOraOrdine>
        <IDTransazione>2015-08-06 12:57:16.737961</IDTransazione>
        <DataOraTransazione>20150806125716</DataOraTransazione>
        <SistemaPagamento>PGONL</SistemaPagamento>
        <SistemaPagamentoD>PagOnline</SistemaPagamentoD>
        <CircuitoAutorizzativo>CCANY</CircuitoAutorizzativo>
        <CircuitoAutorizzativoD>Carta di credito</CircuitoAutorizzativoD>
        <CircuitoSelezionato>CCANY</CircuitoSelezionato>
        <CircuitoSelezionatoD>Carta di credito</CircuitoSelezionatoD>
        <ImportoTransato>12518</ImportoTransato>
        <ImportoAutorizzato>12518</ImportoAutorizzato>
        <ImportoCommissioni>173</ImportoCommissioni>
        <ImportoCommissioniEnte>0</ImportoCommissioniEnte>
        <Esito>OK</Esito>
        <EsitoD>Successo</EsitoD>
        <DataOra/>
        <Autorizzazione>IC</Autorizzazione>
        <URLCSS/>
        <DatiSpecifici/>
    </PaymentData>
    */
    [DataContract(Namespace = "", Name = "PaymentData")]
    public class MIPEsitoPagamento
    {
        [DataMember(Order = 0)]
        public string PortaleID { get; set; }

        [DataMember(Order = 1)]
        public string NumeroOperazione { get; set; }

        [DataMember(Order = 2)]
        public string IDOrdine { get; set; }

        [DataMember(Order = 3)]
        public string DataOraOrdine { get; set; }

        [DataMember(Order = 4)]
        public string IDTransazione { get; set; }

        [DataMember(Order = 5)]
        public string DataOraTransazione { get; set; }

        [DataMember(Order = 6)]
        public string SistemaPagamento { get; set; }

        [DataMember(Order = 7)]
        public string SistemaPagamentoD { get; set; }

        [DataMember(Order = 8)]
        public string CircuitoAutorizzativo { get; set; }

        [DataMember(Order = 9)]
        public string CircuitoAutorizzativoD { get; set; }

        [DataMember(Order = 10)]
        public string CircuitoSelezionato { get; set; }

        [DataMember(Order = 11)]
        public string CircuitoSelezionatoD { get; set; }

        [DataMember(Order = 12)]
        public string ImportoTransato { get; set; }

        [DataMember(Order = 13)]
        public string ImportoAutorizzato { get; set; }

        [DataMember(Order = 14)]
        public string ImportoCommissioni { get; set; }

        [DataMember(Order = 15)]
        public string ImportoCommissioniEnte { get; set; }

        [DataMember(Order = 16)]
        public string Esito { get; set; }

        [DataMember(Order = 17)]
        public string EsitoD { get; set; }

        [DataMember(Order = 18)]
        public string DataOra { get; set; }

        [DataMember(Order = 19)]
        public string Autorizzazione { get; set; }

        [DataMember(Order = 20)]
        public string URLCSS { get; set; }

        [DataMember(Order = 21)]
        public string DatiSpecifici { get; set; }

    }
}
