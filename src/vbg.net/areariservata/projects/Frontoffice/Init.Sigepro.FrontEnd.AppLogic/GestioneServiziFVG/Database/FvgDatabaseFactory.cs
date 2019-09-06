//using System;
//using System.Collections.Generic;
//using System.Configuration;
//using System.Linq;
//using System.Text;

//namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
//{
//    public class FvgDatabaseFactory
//    {
//        private readonly IFVGWebServiceProxy _webServiceProxy;

//        public FvgDatabaseFactory(IFVGWebServiceProxy webServiceProxy)
//        {
//            this._webServiceProxy = webServiceProxy;
//        }
        
//        private IFvgDatabasePersistenceMedium CreatePersistenceMedium(long codiceIstanza, string idModulo)
//        {
//            if(!string.IsNullOrEmpty(ConfigurationManager.AppSettings["FvgDatabasePersistenceMediumFactory.debugMode"]))
//            {
//                return new FvgFileSystemPersistenceMedium(codiceIstanza, idModulo);
//            }

//            return new FvgWebServicePersistenceMedium(codiceIstanza, idModulo, this._webServiceProxy);

//        }

//        public FvgDatabase Create(long codiceIstanza, string idModulo)
//        {
//            var medium = CreatePersistenceMedium(codiceIstanza, idModulo);

//            return new FvgDatabase(medium);
//        }
//    }
//}
