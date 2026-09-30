using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.DataGrid
{
    public interface IDataGridColumn
    {
        public string Key { get; set; }
        public string HeaderText { get; set; }
        public bool ShowHeader { get; set; }
        public string Width { get; set; }
        public string CssClass_row { get; set; }
    }
}
