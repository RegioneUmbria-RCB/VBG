namespace Init.Sigepro.FrontEnd.Pagamenti.MIP.Client
{
    public interface IPayServerClient
    {
        string GeneraUrlRedirect(PaymentRequest request);
        string EstraiBuffer(string buffer);
        string GetStatoPagamento(MIPPaymentStatusRequest request);
    }
}
