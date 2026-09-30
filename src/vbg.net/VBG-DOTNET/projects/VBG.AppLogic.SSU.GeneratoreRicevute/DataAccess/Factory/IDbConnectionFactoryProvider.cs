using Init.Sigepro.FrontEnd.AppLogic.DataAccess;

namespace VBG.AppLogic.SSU.GeneratoreRicevute.DataAccess.Factory
{
    public interface IDbConnectionFactoryProvider
    {
        DbConnectionFactory Create(string applicationToken);
        DbConnectionFactory Create();
    }
}
