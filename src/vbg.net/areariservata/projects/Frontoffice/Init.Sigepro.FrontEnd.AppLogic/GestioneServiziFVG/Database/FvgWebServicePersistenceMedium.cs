using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneServiziFVG.Database
{
    public class FvgWebServicePersistenceMedium : IFvgDatabasePersistenceMedium
    {
        long _codiceIstanza;
        string _idModulo;
        IFVGWebServiceProxy _wsProxy;

        public long CodiceIstanza => this._codiceIstanza;

        public FvgWebServicePersistenceMedium(long codiceIstanza, string idModulo, IFVGWebServiceProxy wsProxy)
        {
            this._codiceIstanza = codiceIstanza;
            this._idModulo = idModulo;
            this._wsProxy = wsProxy;
        }


        public FvgDatabase.Flyweight Load()
        {
            var xmlBytes = this._wsProxy.CaricaFileXml(this._codiceIstanza, this._idModulo);

            if (xmlBytes == null)
            {
                return new FvgDatabase.Flyweight();
            }

            return xmlBytes.DeserializeXML<FvgDatabase.Flyweight>();
        }

        public void Save(FvgDatabase.Flyweight statoInterno)
        {
            var serialized = statoInterno.ToXmlByteArray();

            this._wsProxy.SalvaFileXml(this._codiceIstanza, this._idModulo, serialized);
        }
    }
}
