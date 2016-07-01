using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	internal class QuerystringIdDomandaResolver : IIdDomandaResolver
	{
		protected static class Constants
		{
			public const string IdDomandaParameterName = "IdPresentazione";
		}

		int? _idDomanda = null;


		#region IIdDomandaResolver Members

		public int IdDomanda
		{
			get 
			{
				if (_idDomanda.HasValue)
					return _idDomanda.Value;

				string idDomanda = HttpContext.Current.Request.QueryString[Constants.IdDomandaParameterName];

				if (String.IsNullOrEmpty(idDomanda))
					return -1;

				_idDomanda = Convert.ToInt32(idDomanda);

				return _idDomanda.Value;
			}
		}

		#endregion
	}

	internal class ExplicitIdDomandaResolver : IIdDomandaResolver
	{
		int _idDomanda;

		public ExplicitIdDomandaResolver(int idDomanda)
		{
			this._idDomanda = idDomanda;
		}

		#region IIdDomandaResolver Members

		public int IdDomanda
		{
			get { return this._idDomanda; }
		}

		#endregion
	}
}
