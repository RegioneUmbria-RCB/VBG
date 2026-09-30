using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	public class FlagsTipoMessaggioNotifica
	{
		public bool InviaMessaggio { get { return BitAnd(TipoInvioMessaggioEnum.MessaggioFrontoffice); } }
		public bool InviaEmail { get { return BitAnd(TipoInvioMessaggioEnum.Email); } }

		int _value;

		public FlagsTipoMessaggioNotifica(int value)
		{
			this._value = value;
		}


		private bool BitAnd(TipoInvioMessaggioEnum cfr)
		{
			return ((TipoInvioMessaggioEnum)this._value & cfr) == cfr;
		}
	}
}
