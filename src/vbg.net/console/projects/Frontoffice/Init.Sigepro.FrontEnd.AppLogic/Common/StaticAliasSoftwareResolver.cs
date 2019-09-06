using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	internal class StaticAliasResolver: IAliasResolver
	{
		string _alias;
		
		public StaticAliasResolver(string alias)
		{
			this._alias = alias;
		}

		#region IAliasResolver Members

		public string AliasComune
		{
			get { return this._alias; }
		}

		#endregion
	}


    //internal class StaticAliasResolver
}
