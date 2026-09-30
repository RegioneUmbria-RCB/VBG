using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi
{
	class InterventiReverseEnumerator<T>: IInterventiEnumerator<T> where T:IIntervento
	{
		List<T> _interventi;
		int _idx = 0;
		T _current = default(T);

		public InterventiReverseEnumerator(List<T> interventi)
		{
			this._interventi = interventi;

			Reset();
		}

        public InterventiReverseEnumerator(IEnumerable<T> interventi)
            :this(interventi.ToList())
        {
        }


		public T Current
		{
			get { return this._current; }
		}

		public void Dispose()
		{
		}

		object System.Collections.IEnumerator.Current
		{
			get { return _current; }
		}

		public bool MoveNext()
		{
			if (this._idx < 0)
			{
				return false;
			}

			this._current = this._interventi[this._idx];

			this._idx--;

			return true;
		}

		public void Reset()
		{
			this._idx = this._interventi.Count-1;
			this._current = default(T);
		}
	}
}
