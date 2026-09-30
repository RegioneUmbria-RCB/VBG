using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{
	public interface IAliasResolver
	{
		string AliasComune { get; }
	}

	public interface IAliasSoftwareResolver : IAliasResolver, ISoftwareResolver
	{
	}
}
