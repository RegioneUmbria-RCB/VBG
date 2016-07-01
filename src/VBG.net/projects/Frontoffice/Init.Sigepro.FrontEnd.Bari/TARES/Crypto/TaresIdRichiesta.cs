using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;

namespace Init.Sigepro.FrontEnd.Bari.TARES.Crypto
{
	internal class TaresIdRichiesta: TaresEncoded
	{
		internal enum NomeServizio
		{
			getUtenze,
			setAgevolazioniCAF,
			getUtenzeStatoPratiche,
			isContribuenteEsistente
		}

		private static class Constants
		{
			public const string FmtStr = "{0}#{1}@{2} {3}";
		}

		string _idUtente;
		string _nomeServizio;
		string _data;
		string _ora;
		ILog _log = LogManager.GetLogger(typeof(TaresServicePassword));

		public TaresIdRichiesta(string idUtente, NomeServizio nomeServizio, TaresDate data)
		{
			this._idUtente = idUtente;
			this._nomeServizio = nomeServizio.ToString();
			this._data = data.ToDateString();
			this._ora = data.ToTimeString();
		}

		public override string ToString()
		{
			var password = String.Format( Constants.FmtStr, this._idUtente, this._nomeServizio, this._data, this._ora );
			var sha256 = Sha256( password );

			_log.DebugFormat("password: {0}", password);
			_log.DebugFormat("sha256 password: {0}", sha256);

			return sha256;
		}
	}
}
