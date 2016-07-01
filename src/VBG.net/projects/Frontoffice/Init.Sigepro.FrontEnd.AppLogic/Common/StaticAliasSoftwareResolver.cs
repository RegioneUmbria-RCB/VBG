using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	internal class StaticAliasSoftwareResolver: IAliasSoftwareResolver
	{
		string _alias;
		string _software;

		public StaticAliasSoftwareResolver(string alias , string software)
		{
			this._alias = alias;
			this._software = software;
		}

		#region IAliasSoftwareResolver Members

		public string AliasComune
		{
			get { return this._alias; }
		}

		public string Software
		{
			get { return this._software; }
		}

		#endregion
	}
}
