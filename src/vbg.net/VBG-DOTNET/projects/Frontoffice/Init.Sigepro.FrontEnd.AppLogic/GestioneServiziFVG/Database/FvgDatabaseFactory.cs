namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
{
    public class FvgDatabaseFactory : IFvgDatabaseFactory
    {
        private readonly IFVGWebServiceProxy _webServiceProxy;

        public FvgDatabaseFactory(FVGWebServiceProxyFactory webServiceProxyFactory)
        {
            this._webServiceProxy = webServiceProxyFactory.CreateService();
        }

        public FvgDatabase Create(long codiceIstanza, string idModulo)
        {
            var persistenceMedium = new FvgWebServicePersistenceMedium(codiceIstanza, idModulo, this._webServiceProxy);
            return new FvgDatabase(persistenceMedium);
        }
    }
}
