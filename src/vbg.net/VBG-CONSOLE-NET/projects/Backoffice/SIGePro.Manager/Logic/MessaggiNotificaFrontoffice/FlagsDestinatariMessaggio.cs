using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.MessaggiNotificaFrontoffice
{
	public class FlagsDestinatariMessaggio
	{
		int _value;

		public FlagsDestinatariMessaggio(int value)
		{
			this._value = value;
		}

		public bool InviaACreatoreIstanza
		{
			get
			{
				return BitAnd(DestinatariMessaggioEnum.CittadinoRichiedente);
			}
		}

		public bool InviaAResponsabileSportello
		{
			get
			{
				return BitAnd(DestinatariMessaggioEnum.ResponsabileSportello);
			}
		}

		public bool InviaAResponsabileProcedimento
		{
			get
			{
				return BitAnd(DestinatariMessaggioEnum.ResponsabileProcedimento);
			}
		}

		public bool InviaAResponsabileIstruttoria
		{
			get
			{
				return BitAnd(DestinatariMessaggioEnum.ResponsabileIstruttoria);
			}
		}

		public bool InviaAOperatore
		{
			get
			{
				return BitAnd(DestinatariMessaggioEnum.Operatore);
			}
		}

		private bool BitAnd(DestinatariMessaggioEnum cfr)
		{
			return ((DestinatariMessaggioEnum)this._value & cfr) == cfr;
		}
	}
}
