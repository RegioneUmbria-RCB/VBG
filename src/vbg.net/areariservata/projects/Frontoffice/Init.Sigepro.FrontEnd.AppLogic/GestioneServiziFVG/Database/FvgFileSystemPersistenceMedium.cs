//using System;
//using System.Collections.Generic;
//using System.IO;
//using System.Linq;
//using System.Text;
//using System.Web;
//using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

//namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
//{
//    public class FvgFileSystemPersistenceMedium : IFvgDatabasePersistenceMedium
//    {
//        private readonly long _codiceIstanza;
//        private readonly string _idModulo;

//        public long CodiceIstanza => this._codiceIstanza;

//        private static class Constants
//        {
//            public const string PersistencePath = "c:\\temp\\";
//        }

//        private string UniqueSessionId => HttpContext.Current.Session.SessionID;

//        private string FullFilePath => Path.Combine(Constants.PersistencePath, $"{this._codiceIstanza}_{this._idModulo}.xml");


//        public FvgFileSystemPersistenceMedium(long codiceIstanza, string idModulo)
//        {
//            this._codiceIstanza = codiceIstanza;
//            this._idModulo = idModulo;
//        }

//        public FvgDatabase.Flyweight Load()
//        {
//            if (!File.Exists(this.FullFilePath))
//            {
//                return new FvgDatabase.Flyweight();
//            }

//            return File.ReadAllBytes(this.FullFilePath).DeserializeXML<FvgDatabase.Flyweight>();
//        }

//        public void Save(FvgDatabase.Flyweight statoInterno)
//        {
//            File.WriteAllBytes(this.FullFilePath, statoInterno.ToXmlByteArray());
//        }
//    }
//}
