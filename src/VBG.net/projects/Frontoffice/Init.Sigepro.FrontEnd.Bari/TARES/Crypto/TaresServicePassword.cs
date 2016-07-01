using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using log4net;

namespace Init.Sigepro.FrontEnd.Bari.TARES.Crypto
{
	// La password è nel formato ID UTENTE#PASSPHRASE@DATA ORA 
	internal class TaresServicePassword : TaresEncoded
	{
		private static class Constants
		{
			public const string FmtStr = "{0}#{1}@{2} {3}";
		}

		string _idUtente;
		string _passphrase;
		string _data;
		string _ora;
		ILog _log = LogManager.GetLogger(typeof(TaresServicePassword));

		public TaresServicePassword(string idUtente, string passPhrase, TaresDate data)
		{
			if (passPhrase.Length != 40)
				throw new ArgumentException("La passphrase deve essere lunga 40 caratteri");

			this._idUtente = idUtente;
			this._passphrase = passPhrase;
			this._data = data.ToDateString();
			this._ora = data.ToTimeString();
		}

		public override string ToString()
		{
			var password = String.Format( Constants.FmtStr, this._idUtente, this._passphrase, this._data, this._ora );
			var sha256 = Sha256( password );

			_log.DebugFormat("password: {0}", password);
			_log.DebugFormat("sha256 password: {0}", sha256);

			return sha256;
		}
	}
}
