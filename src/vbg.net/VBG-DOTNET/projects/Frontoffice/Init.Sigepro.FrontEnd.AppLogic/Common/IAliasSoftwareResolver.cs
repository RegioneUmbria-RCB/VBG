namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
    public interface IAliasResolver
    {
        string AliasComune { get; }
    }

    public interface IAliasSoftwareResolver : IAliasResolver, ISoftwareResolver
    {
    }
}
