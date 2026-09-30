using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.DataGrid
{
    public class DataGridRow<T>
    {
        public int Index { get; set; }
        public IEnumerable<IDataGridColumn> Columns { get; set; }
        public bool EditMode { get; set; } = false;
        public T RowData { get; set; }
    }
}
