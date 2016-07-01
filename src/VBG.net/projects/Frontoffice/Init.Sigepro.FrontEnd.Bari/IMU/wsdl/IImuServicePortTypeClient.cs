// -----------------------------------------------------------------------
// <copyright file="IImuServicePortTypeClient.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.IMU.wsdl
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface IImuServicePortTypeClient : IDisposable
	{
		datiContribuenteResponseType getDatiContribuente(datiAutorizzazioneImuType datiAutorizzazione, datiIdentificativiImuType datiIdentificativi);
		void Abort();
	}
}
