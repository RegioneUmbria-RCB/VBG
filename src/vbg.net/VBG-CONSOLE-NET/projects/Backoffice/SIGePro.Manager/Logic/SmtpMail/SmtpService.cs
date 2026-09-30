// -----------------------------------------------------------------------
// <copyright file="SmtpService.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.SIGePro.Manager.Logic.SmtpMail
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
	using PersonalLib2.Data;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class SmtpService : ISmtpService
	{
		DataBase _db;
		string _alias;
		string _software;

		public SmtpService(DataBase db, string alias, string software)
		{
			this._db = db;
			this._alias = alias;
			this._software = software;
		}

		public void Send(SIGeProMailMessage msg)
		{
			new SmtpSender().InviaEmail(this._db, this._alias, this._software, msg);
		}
	}
}
