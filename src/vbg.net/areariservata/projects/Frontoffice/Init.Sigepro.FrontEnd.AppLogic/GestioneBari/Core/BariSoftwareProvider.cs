// -----------------------------------------------------------------------
// <copyright file="BariSoftwareProvider.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneBari.Core
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.Bari.Core;
using Init.Sigepro.FrontEnd.AppLogic.Common;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class BariSoftwareProvider: ISoftware
	{
		IAliasSoftwareResolver _aliasSoftwareResolver;


		public BariSoftwareProvider(IAliasSoftwareResolver aliasSoftwareResolver)
		{
			this._aliasSoftwareResolver = aliasSoftwareResolver;
		}

		public string Get()
		{
			return this._aliasSoftwareResolver.Software;
		}
	}
}
