using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;
using VBG.BlazorComponentsLibrary.DesignComuni.Utils;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraEndoprocedimenti
    {
        [Inject]
        protected DownloadHelperService _fileDownloadHelper { get; set; } = default!;
        [Inject]
        protected SpinnerService _spinnerService { get; set; } = default!;

        public class VisuraEndoListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public IEnumerable<IstanzeAllegati> Allegati { get; set; }
            public string Endoprocedimento { get; set; }
        }

        [Parameter]
        public bool PermettiDownload { get; set; }

        [Parameter]
        public IEnumerable<VisuraEndoListItem> DataSource { get; set; }

        private IEnumerable<IstanzeAllegati>? _allegatiScaricabili;

        private async Task MostraAllegatiAsync(IEnumerable<IstanzeAllegati> allegati)
        {
            if (allegati.Count() == 1)
            {
                await this.DownloadAttachmentAsync(Convert.ToInt32(allegati.First().CodiceOggetto));
                this._allegatiScaricabili = null;
            }
            else
            {
                this._allegatiScaricabili = allegati;
            }
        }

        private async Task DownloadAttachmentAsync(int codiceOggetto)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                await this._fileDownloadHelper.DownloadFileAsync(codiceOggetto);
            });
        }
    }
}
