namespace Init.Sigepro.FrontEnd.Infrastructure.Adapters.Abstractions
{
    public interface IAdapter<TSrc, TDst>
    {
        TDst Adapt(TSrc src);
    }
}
