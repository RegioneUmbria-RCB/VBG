using Microsoft.AspNetCore.Components;

namespace Vbg.CoreControls.DataGrid
{
    public class DataGridButtonColumn : IDataGridColumn
    {
        public string Key { get; set; }
        public List<GridButton> Buttons { get; set; }
        public bool ShowHeader { get; set; }
        public string HeaderText { get; set; }
        public string CssClass_row { get; set; }
        public string Width { get; set; }

        public DataGridButtonColumn() { }

        public DataGridButtonColumn(string key, List<GridButton> buttons, bool showHeader = false, string headerText = "", string cssClass_row = "")
        {
            this.Key = key;
            this.Buttons = buttons;
            this.ShowHeader = showHeader;
            this.HeaderText = headerText;
            this.CssClass_row = cssClass_row;
        }

        public DataGridButtonColumn(string key, string text, EventCallback onClick, bool showHeader = false, string headerText = "", string cssClass_row = "")
        {
            this.Key = key;
            this.Buttons = new List<GridButton> { new GridButton { Text = text, OnClick = onClick } };
            this.ShowHeader = showHeader;
            this.HeaderText = headerText;
            this.CssClass_row = cssClass_row;
        }
    }

    public class GridButton
    {
        public EventCallback OnClick { get; set; }
        public string Text { get; set; }
    }
}
