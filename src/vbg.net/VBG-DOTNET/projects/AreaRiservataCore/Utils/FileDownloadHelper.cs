using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Microsoft.JSInterop;

namespace AreaRiservataCore.Utils
{
    public class FileDownloadHelper
    {
        private readonly IJSRuntime _jsRuntime;

        public FileDownloadHelper(IJSRuntime jsRuntime)
        {
            this._jsRuntime = jsRuntime;
            // _downloadModule = this._jsImportedModuleFactory.CreateJsImportedModule($"{_navManager.BaseUri}js/download-file.js");
        }
        public async Task DownloadFileAsync(BinaryFile file)
        {
            using var fileStream = new MemoryStream(file.FileContent);
            var streamRef = new DotNetStreamReference(fileStream);

            if (this._jsRuntime != null)
                await this._jsRuntime.InvokeVoidAsync("downloadFileFromStream", file.FileName, streamRef);
        }
    }
}
