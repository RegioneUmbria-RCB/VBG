using Init.Sigepro.FrontEnd.AppLogic.WebServiceReferences.IstanzeService;
using Microsoft.AspNetCore.Components;
using VBG.BlazorComponentsLibrary.DesignComuni.Spinner;
using VBG.BlazorComponentsLibrary.DesignComuni.Utils;

namespace AreaRiservataCore.Pages.Visura
{
    public partial class VisuraMovimenti
    {
        [Inject]
        protected DownloadHelperService _fileDownloadHelper { get; set; } = default!;
        [Inject]
        protected SpinnerService _spinnerService { get; set; } = default!;

        public class VisuraMovimentiListItem
        {
            public int Id { get; set; }
            public int CodiceIstanza { get; set; }
            public string Descrizione { get; set; }
            public DateTime? Data { get; set; }
            public string Parere { get; set; }
            public string NumeroProtocollo { get; set; }
            public DateTime? DataProtocollo { get; set; }
            public string UuidPraticaCollegata { get; set; }
            public bool HaPraticaCollegata { get { return !string.IsNullOrEmpty(this.UuidPraticaCollegata); } }
            public IEnumerable<MovimentiAllegati> Allegati { get; set; } = Enumerable.Empty<MovimentiAllegati>();
        }


        [Parameter]
        public IEnumerable<VisuraMovimentiListItem> DataSource { get; set; }

        [Parameter]
        public bool MostraPraticheCollegate { get; set; } = true;

        [Parameter]
        public bool PermettiDownload { get; set; } = true;
        public string UrlPopupVisura
        {
            get
            {
                //var pagina = this.Page as ReservedBasePage;

                //var url = UrlBuilder.Url("~/Reserved/sub-visura.aspx", qs =>
                //{
                //    qs.Add(new QsAliasComune(pagina.IdComune));
                //    qs.Add(new QsSoftware(pagina.Software));
                //});

                //return this.ResolveClientUrl(url);

                return "~/Reserved/sub-visura.aspx";
            }
        }

        private IEnumerable<MovimentiAllegati>? _allegatiScaricabili;

        private async Task MostraAllegatiAsync(IEnumerable<MovimentiAllegati> allegati)
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
