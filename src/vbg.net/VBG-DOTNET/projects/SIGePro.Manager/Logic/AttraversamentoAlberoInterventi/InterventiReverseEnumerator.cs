using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.AttraversamentoAlberoInterventi
{
    internal class InterventiReverseEnumerator<T> : IInterventiEnumerator<T> where T : IIntervento
    {
        private readonly List<T> _interventi;
        private int _idx = 0;
        private T _current = default;

        public InterventiReverseEnumerator(List<T> interventi)
        {
            this._interventi = interventi;

            this.Reset();
        }

        public InterventiReverseEnumerator(IEnumerable<T> interventi)
            : this(interventi.ToList())
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
            get { return this._current; }
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
            this._idx = this._interventi.Count - 1;
            this._current = default;
        }
    }
}
