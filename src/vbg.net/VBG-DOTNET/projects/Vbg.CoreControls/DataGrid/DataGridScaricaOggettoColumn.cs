using Microsoft.AspNetCore.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls.DataGrid
{
    public class DataGridScaricaOggettoColumn : IDataGridColumn
    {
        public string Key { get; set; }
        public string Text { get; set; }
        public int CodiceOggetto { get; set; }
        public bool ShowHeader { get; set; }
        public string HeaderText { get; set; }
        public string CssClass_row { get; set; }
        public string Width { get; set; }
        public Func<bool>? IfCanDownload { get; set; }
        public Action? IfCanDownloadFailed { get; set; }

        public DataGridScaricaOggettoColumn() { }

        public DataGridScaricaOggettoColumn(string key, string text, int codiceOggetto, bool showHeader = false, string headerText = "", string cssClass_row = "")
        {
            this.Key = key;
            this.Text = text;
            this.CodiceOggetto = codiceOggetto;
            this.ShowHeader = showHeader;
            this.HeaderText = headerText;
            this.CssClass_row = cssClass_row;
        }
    }
}
