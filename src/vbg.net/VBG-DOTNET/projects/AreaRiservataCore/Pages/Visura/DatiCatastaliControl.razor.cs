using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.Visura
{
    public partial class DatiCatastaliControl
    {
        [Parameter]
        public string Id { get; set; }

        [Parameter]
        public DatiCatasto Model { get; set; }

        [Parameter]
        public string Label { get; set; }

        [Parameter]
        public EventCallback<DatiCatasto> OnChanged { get; set; }

        //private DatiCatasto model = new DatiCatasto();

        private readonly List<DropDownItem> _tipiCatasto = new();

        protected override void OnInitialized()
        {
            this._tipiCatasto.Add(new DropDownItem("Terreni", "T") { });
            this._tipiCatasto.Add(new DropDownItem("Fabbricati", "F") { });

            base.OnInitialized();
        }
    }
}
