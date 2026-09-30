namespace Init.Sigepro.FrontEnd.Pagamenti
{
    public class IniziaPagamentoRequest
    {
        public readonly RiferimentiDomanda RiferimentiDomanda;
        public readonly RiferimentiUtente RiferimentiUtente;
        public readonly RiferimentiOperazione RiferimentiOperazione;
        public readonly string ClientType;

        public IniziaPagamentoRequest(RiferimentiDomanda riferimentiDomanda, RiferimentiUtente riferimentiUtente, RiferimentiOperazione riferimentiOperazione, string clientType)
        {
            this.RiferimentiDomanda = riferimentiDomanda;
            this.RiferimentiUtente = riferimentiUtente;
            this.RiferimentiOperazione = riferimentiOperazione;
            this.ClientType = clientType;
        }
    }
}
