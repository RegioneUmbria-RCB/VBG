using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Bari.TARES
{
	internal class TaresDate
	{
		DateTime _date;

		public TaresDate(DateTime date)
		{
			this._date = date;
		}

		public TaresDate()
			: this(DateTime.Now)
		{

		}

		public string ToDateString()
		{
			return this._date.ToString("dd/MM/yyyy");
		}

		public string ToTimeString()
		{
			var hh = this._date.ToString("HH");
			var mm = this._date.ToString("mm");
			var ss = this._date.ToString("ss");
			var fff = this._date.ToString("fff");

			return String.Format("{0}:{1}:{2}.{3}", hh,mm, ss, fff);//this._date.ToString("HH") + ":" + mm:ss.fff");
		}

	}
}
