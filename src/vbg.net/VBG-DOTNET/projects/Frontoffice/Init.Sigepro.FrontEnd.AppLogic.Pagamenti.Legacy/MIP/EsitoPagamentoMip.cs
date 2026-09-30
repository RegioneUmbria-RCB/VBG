using System;
using VBG.Pagamenti.Legacy.MIP;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public class EsitoPagamentoMip
    {
        public enum StatoPagamentoMipEnum
        {
            OK, // Successo nell'operazione            
            KO, // Autorizzazione negata dal circuito
            OP, // Operazione in pagamento
            UK, // Operazione sconosciuta
            ER  // Operazione andata in errore per problemi di comunicazione con il sistema di pagamento
        }

        public readonly bool PagamentoRiuscito;
        public readonly string DescrizioneEsito;
        public readonly StatoPagamentoMipEnum Stato;
        public readonly MIPEsitoPagamento EsitoWs;

        public EsitoPagamentoMip(MIPEsitoPagamento esitoWs)
        {
            this.EsitoWs = esitoWs;
            this.PagamentoRiuscito = this.EsitoWs.Esito == StatoPagamentoMipEnum.OK.ToString();
            this.DescrizioneEsito = this.EsitoWs.EsitoD;
            this.Stato = (StatoPagamentoMipEnum)Enum.Parse(typeof(StatoPagamentoMipEnum), this.EsitoWs.Esito);
        }
    }
}
