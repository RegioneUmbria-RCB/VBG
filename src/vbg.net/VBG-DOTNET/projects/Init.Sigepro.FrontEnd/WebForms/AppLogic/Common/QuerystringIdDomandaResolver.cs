using Init.Sigepro.FrontEnd.AppLogic.Common;
using System;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.Common
{
    internal class QuerystringIdDomandaResolver : IIdDomandaResolver
    {
        protected static class Constants
        {
            public const string IdDomandaParameterName = "IdPresentazione";
        }

        private int? _idDomanda = null;


        #region IIdDomandaResolver Members

        public int IdDomanda
        {
            get
            {
                if (this._idDomanda.HasValue)
                    return this._idDomanda.Value;

                string idDomanda = HttpContext.Current.Request.QueryString[Constants.IdDomandaParameterName];

                if (String.IsNullOrEmpty(idDomanda))
                    return -1;

                this._idDomanda = Convert.ToInt32(idDomanda);

                return this._idDomanda.Value;
            }
        }

        #endregion
    }
}