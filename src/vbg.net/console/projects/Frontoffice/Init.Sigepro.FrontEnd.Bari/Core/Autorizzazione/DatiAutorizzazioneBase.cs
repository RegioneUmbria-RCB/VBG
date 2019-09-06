// -----------------------------------------------------------------------
// <copyright file="DatiAutorizzazioneBase.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Bari.Core.Autorizzazione
{
	using System;
	using System.Collections.Generic;
	using System.Linq;
	using System.Text;
using System.Security.Cryptography;
using System.Globalization;

	/// <summary>
	/// TODO: Update summary.
	/// </summary>
	public class DatiAutorizzazioneBase
	{
		private static class Constants
		{
			public const string DateFormatString = "dd/MM/yyyy";
			public const string TimeFormatString = "HH:mm:ss.fff";
			public const string PasswordFormatString = "{0}#{1}@{2} {3}";
		}

		protected readonly string _identificativoUtente;
		private readonly string _passphrase;
		private readonly string _nomeServizio;
		private readonly DateTime _dataRichiesta;

		public DatiAutorizzazioneBase(string identificativoUtente, string passphrase, string nomeServizio, DateTime? dataRichiesta = null)
		{
			this._identificativoUtente = identificativoUtente;
			this._passphrase = passphrase;
			this._nomeServizio = nomeServizio;
			this._dataRichiesta = dataRichiesta.GetValueOrDefault(DateTime.Now);
		}


		protected string GetDateString()
		{
			return this._dataRichiesta.ToString(Constants.DateFormatString, CultureInfo.InvariantCulture);
		}

		protected string GetTimeString()
		{
			return this._dataRichiesta.ToString(Constants.TimeFormatString, CultureInfo.InvariantCulture);
		}


		private string Sha256(string password)
		{
			var crypt = new SHA256Managed();
			var hash = String.Empty;
			var crypto = crypt.ComputeHash(Encoding.UTF8.GetBytes(password), 0, Encoding.UTF8.GetByteCount(password));
			foreach (byte bit in crypto)
			{
				hash += bit.ToString("x2");
			}
			return hash;
		}

		protected string GetIdRichiesta()
		{
			var str = String.Format("{0}#{1}@{2} {3}", this._identificativoUtente, this._nomeServizio, this.GetDateString(), this.GetTimeString());

			return Sha256(str);
		}

		protected string GetPassword()
		{
			var str = String.Format("{0}#{1}@{2} {3}", this._identificativoUtente, this._passphrase, this.GetDateString(), this.GetTimeString());

			return Sha256(str);
		}
	}
}
