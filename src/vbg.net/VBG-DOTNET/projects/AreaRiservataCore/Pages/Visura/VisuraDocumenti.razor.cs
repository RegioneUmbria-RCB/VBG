using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;
using VBG.BlazorComponentsLibrary.DesignComuni.Utils;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraDocumenti
    {
        public class VisuraDocumentiListItem
        {
            private string _descrizione = String.Empty;
            public string Descrizione
            {
                get { return this._descrizione; }
                set
                {
                    if (String.IsNullOrEmpty(value))
                    {
                        this._descrizione = String.Empty;
                    }

                    this._descrizione = value.Replace("\n", "<br />");
                }
            }

            public string NomeFile { get; set; }
            private string _md5;
            public string Md5
            {
                get => this._md5;
                set => this._md5 = value?.ToUpper();
            }
            public bool HasMd5 => !String.IsNullOrEmpty(this.Md5);
            public int CodiceOggetto { get; set; }
            public DateTime? Data { get; set; }

            public string UrlDownload { get; set; }
        }

        [Inject]
        protected DownloadHelperService _fileDownloadHelper { get; set; } = default!;
        [Inject]
        protected SpinnerService _spinnerService { get; set; } = default!;

        [Parameter]
        public IEnumerable<VisuraDocumentiListItem>? DataSource { get; set; }

        [Parameter]
        public bool PermettiDownload { get; set; } = true;

        private async Task DownloadAttachmentAsync(int codiceOggetto)
        {
            await this._spinnerService.ShowSpinnerAsync(async () =>
            {
                await this._fileDownloadHelper.DownloadFileAsync(codiceOggetto);
            });
        }
    }
}
