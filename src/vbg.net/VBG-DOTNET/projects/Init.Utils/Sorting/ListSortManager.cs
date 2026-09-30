using System.Collections.Generic;
using System.ComponentModel;

namespace Init.Utils.Sorting
{
    public partial class ListSortManager<T>
    {
        private readonly PropertyComparer<T> m_comparer = null;

        public ListSortManager(string sortExpression)
        {
            this.m_comparer = new PropertyComparer<T>(SortExpressionParser<T>.ParseProperty(sortExpression),
                                                    SortExpressionParser<T>.ParseDirection(sortExpression));
        }

        public ListSortManager(string sortExpression, ListSortDirection direction)
        {
            this.m_comparer = new PropertyComparer<T>(SortExpressionParser<T>.ParseProperty(sortExpression),
                                                    direction);
        }

        public void Sort(List<T> list)
        {
            list.Sort(this.m_comparer);
        }

        public static void Sort(List<T> list, string sortExpression)
        {
            ListSortManager<T> lsm = new ListSortManager<T>(sortExpression);
            lsm.Sort(list);
        }

        public static void Sort(List<T> list, string sortExpression, ListSortDirection direction)
        {
            ListSortManager<T> lsm = new ListSortManager<T>(sortExpression, direction);
            lsm.Sort(list);
        }

    }
}
