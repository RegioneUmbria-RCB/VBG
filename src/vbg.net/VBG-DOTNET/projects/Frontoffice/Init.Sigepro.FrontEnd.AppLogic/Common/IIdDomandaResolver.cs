namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public interface IIdDomandaResolver
    {
        int IdDomanda { get; }
    }

    public class StaticIdDomandaResolver : IIdDomandaResolver
    {
        public StaticIdDomandaResolver(int id)
        {
            this.IdDomanda = id;
        }
        public int IdDomanda { get; }
    }
}
