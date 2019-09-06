// -----------------------------------------------------------------------
// <copyright file="ISitServiceCreator.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.IntegrazioneSit
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ServiceCreators;
using Init.Sigepro.FrontEnd.AppLogic.SigeproSitWebService;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface ISitServiceCreator
	{
		ServiceInstance<WsSitSoapClient> CreateClient(string aliasComune);
	}
}
