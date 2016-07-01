using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	internal class QuerystringAliasResolver: IAliasResolver
	{
		private static class Constants
		{
			public const string IdComuneParameterName = "IdComune";
			public const string AliasParameterName = "alias";
		}

		public string AliasComune
		{
			get
			{
				// Usato nella sezione riservata
				var alias = HttpContext.Current.Request.QueryString[Constants.IdComuneParameterName];

				// Usato nella sezione "Contenuti"
				if (String.IsNullOrEmpty(alias))
					alias = HttpContext.Current.Request.QueryString[Constants.AliasParameterName];

				if (String.IsNullOrEmpty(alias))
					throw new InvalidOperationException("Parametro Idcomune non valido");

				return alias;
			}
		}
	}

	internal class QuerystringAliasSoftwareResolver : QuerystringAliasResolver, IAliasSoftwareResolver
	{
		private static class Constants
		{
			public const string SoftwareParameterName = "Software";
		}

		#region IAliasSoftwareResolver Members
		
		public string Software
		{
			get
			{
				var software = HttpContext.Current.Request.QueryString[Constants.SoftwareParameterName];

				if (String.IsNullOrEmpty(software) && HttpContext.Current.Request.Path.ToUpperInvariant().Contains("/CONTENUTI/"))
					software = "SS";

				if (String.IsNullOrEmpty(software))
					throw new InvalidOperationException("Parametro Software non valido");

				return software;
			}
		}

		#endregion
	}
}
