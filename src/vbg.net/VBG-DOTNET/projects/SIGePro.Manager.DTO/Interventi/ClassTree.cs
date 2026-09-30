using System.Collections.Generic;

namespace Init.SIGePro.Manager.DTO.Interventi
{
    public class ClassTree<T>
    {
        public T Elemento { get; set; }
        public List<ClassTree<T>> NodiFiglio { get; set; }

        public ClassTree()
        {
            this.NodiFiglio = new List<ClassTree<T>>();
        }

        public ClassTree(T el) : this()
        {
            this.Elemento = el;
        }
    }
}
