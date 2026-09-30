namespace Init.SIGePro.Manager.Logic.GestioneIntegrazioneLDP
{
    public interface ILdpProxyServiceWrapperFactory
    {
        ILdpAnnullamentoServiceWrapper GetAnnullamentoService(string software);
    }
}