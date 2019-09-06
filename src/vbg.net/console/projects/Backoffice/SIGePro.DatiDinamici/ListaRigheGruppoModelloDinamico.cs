using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.DatiDinamici
{
    internal class ListaRigheGruppoModelloDinamico : IList<RigaModelloDinamico>
    {
        List<RigaModelloDinamico> _listaRighe = new List<RigaModelloDinamico>();

        private int IdGruppo { get; set; }

        internal ListaRigheGruppoModelloDinamico(int idGruppo)
        {
            IdGruppo = idGruppo;
        }

        /// <summary>
        /// Restituisce la molteplicità del gruppo.
        /// La molteplicità del gruppo è l'indice di molteplicità più alto tra tutte le righe del gruppo
        /// </summary>
        /// <returns>Molteplicità del gruppo</returns>
        internal int CalcolaMolteplicita()
        {
            var molteplicita = 0;

            for (int i = 0; i < _listaRighe.Count; i++)
                molteplicita = Math.Max(_listaRighe[i].CalcolaMolteplicita(), molteplicita);

            return molteplicita;
        }

        /// <summary>
        /// Incrementa la molteplicità del gruppo verificando che essa sia consistente su tutti i campi.
        /// </summary>
        internal void IncrementaMolteplicita()
        {
            for (int i = 0; i < _listaRighe.Count; i++)
            {
                try
                {
                    _listaRighe[i].SospendiNotificaModifiche();

                    _listaRighe[i].IncrementaMolteplicita();
                }
                finally
                {
                    _listaRighe[i].RipristinaNotificaModifiche();
                }
            }

            VerificaConsistenzaMolteplicita();

            for (int i = 0; i < _listaRighe.Count; i++)
            {
                _listaRighe[i].NotificaModifiche();
            }
        }


        /// <summary>
        /// Verifica che la molteplicità sia uguale per tutti i campi del gruppo
        /// </summary>
        internal void VerificaConsistenzaMolteplicita()
        {
            int molteplicita = CalcolaMolteplicita();

            for (int i = 0; i < _listaRighe.Count; i++)
            {
                while (_listaRighe[i].CalcolaMolteplicita() < molteplicita)
                    _listaRighe[i].IncrementaMolteplicita();
            }
        }

        internal void EliminaValoriAllIndice(int indice)
        {
            for (int i = 0; i < _listaRighe.Count; i++)
                _listaRighe[i].EliminaValoriAllIndice(indice);

            VerificaConsistenzaMolteplicita();
        }

        public bool IsVisibile()
        {
            foreach (var riga in _listaRighe)
            {
                foreach (var campo in riga.Campi)
                {
                    if (campo == null)
                    {
                        continue;
                    }


                    foreach (var valore in campo.ListaValori.Cast<ValoreDatiDinamici>())
                    {
                        if (valore.Visibile)
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        #region IList<RigaModelloDinamico> Members

        public int IndexOf(RigaModelloDinamico item)
        {
            return _listaRighe.IndexOf(item);
        }

        public void Insert(int index, RigaModelloDinamico item)
        {
            item.IdGruppo = IdGruppo;

            _listaRighe.Insert(index, item);
        }

        public void RemoveAt(int index)
        {
            var obj = _listaRighe[index];

            if (obj != null)
                obj.IdGruppo = -1;

            _listaRighe.RemoveAt(index);
        }

        public RigaModelloDinamico this[int index]
        {
            get
            {
                return _listaRighe[index];
            }
            set
            {
                value.IdGruppo = IdGruppo;
                _listaRighe[index] = value;
            }
        }

        #endregion

        #region ICollection<RigaModelloDinamico> Members

        public void Add(RigaModelloDinamico item)
        {
            item.IdGruppo = IdGruppo;

            _listaRighe.Add(item);
        }

        public void Clear()
        {
            for (int i = 0; i < Count; i++)
                this[i].IdGruppo = -1;

            _listaRighe.Clear();
        }

        public bool Contains(RigaModelloDinamico item)
        {
            return _listaRighe.Contains(item);
        }

        public void CopyTo(RigaModelloDinamico[] array, int arrayIndex)
        {
            _listaRighe.CopyTo(array, arrayIndex);
        }

        public int Count
        {
            get { return _listaRighe.Count; }
        }

        public bool IsReadOnly
        {
            get { return false; }
        }

        public bool Remove(RigaModelloDinamico item)
        {
            if (Contains(item))
                item.IdGruppo = -1;

            return _listaRighe.Remove(item);
        }

        #endregion

        #region IEnumerable<RigaModelloDinamico> Members

        public IEnumerator<RigaModelloDinamico> GetEnumerator()
        {
            return _listaRighe.GetEnumerator();
        }

        #endregion

        #region IEnumerable Members

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return _listaRighe.GetEnumerator();
        }

        #endregion
    }
}
