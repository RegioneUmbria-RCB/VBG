using Microsoft.AspNetCore.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.DataGrid
{
    public class DataGridColumn : IDataGridColumn
    {
        public string Key { get; set; }
        public string HeaderText { get; set; } = "";
        public bool ShowHeader { get; set; } = true;
        public object Value { get; set; }
        public string DataFormatString { get; set; } = "";
        public int IdTemplate { get; set; } = -1;
        public List<object> Parameters { get; set; } = null;
        public bool AllowFiltering { get; set; } = true;
        public string CssClass_row { get; set; } = "";
        public string Width { get; set; }

        public DataGridColumn() { }
        public DataGridColumn(string key, string headerText, object value) : this(key, headerText, value, "", true, -1, null, true, "") { }

        public DataGridColumn(string key, string headerText, object value, string dataFormatString, bool showHeader, int idTemplate, List<object> parameters, bool allowFiltering, string cssClass_row)
        {
            this.Key = key;
            this.HeaderText = headerText;
            this.Value = value;
            this.DataFormatString = dataFormatString;
            this.ShowHeader = showHeader;
            this.IdTemplate = idTemplate;
            this.Parameters = parameters;
            this.AllowFiltering = allowFiltering;
            this.CssClass_row = cssClass_row;
        }
    }
}
