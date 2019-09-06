using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi.VerificaAttivazione
{
	internal class InterventoPubblicato : IInterventoPubblicato
	{
		IInterventiEnumerator<IIntervento> _enumerator;
		IVerificaaAlbero _analizzaAlbero;

		protected InterventoPubblicato(IInterventiEnumerator<IIntervento> enumerator, IVerificaaAlbero analizzaAlbero)
		{
			this._enumerator = enumerator;
			this._analizzaAlbero = analizzaAlbero;
		}

		public bool IsTrue()
		{
			while(this._enumerator.MoveNext())
			{
				var item = this._enumerator.Current;

				if (this._analizzaAlbero.PuoAnalizzare(item))
					return this._analizzaAlbero.GetRisultato(item);
			}

			return GetValoreDefault();
		}

		protected virtual bool GetValoreDefault()
		{
			return false;
		}
	}
}
