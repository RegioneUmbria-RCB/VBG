// -----------------------------------------------------------------------
// <copyright file="ISmtpService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public interface ISmtpService
	{
		void Send(SIGeProMailMessage msg);
	}
}
