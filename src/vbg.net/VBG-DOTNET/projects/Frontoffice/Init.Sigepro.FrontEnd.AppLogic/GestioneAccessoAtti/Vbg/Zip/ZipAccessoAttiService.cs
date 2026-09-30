using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using log4net;
using System;
using System.IO;
using System.IO.Compression;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneAccessoAtti.Vbg.Zip
{
    public class ZipAccessoAttiService : IZipAccessoAttiService
    {
        private readonly IZipAccessoAttiProxy _proxy;
        private readonly IOggettiService _oggettiService;
        private readonly IVbgAccessoAttiService _accessoAttiService;
        private readonly ILog _log = LogManager.GetLogger(typeof(ZipAccessoAttiService));

        public ZipAccessoAttiService(IZipAccessoAttiProxy proxy, IOggettiService oggettiService, IVbgAccessoAttiService accessoAttiService)
        {
            this._proxy = proxy;
            this._oggettiService = oggettiService;
            this._accessoAttiService = accessoAttiService;
        }

        public async Task<BinaryFile> GetZipFileDocumentiAsync(int idAccessoAtti, string uuidPratica)
        {
            try
            {
                var codiciOggettoDocumentiConsultabili = this._accessoAttiService.GetCodiciOggettoScaricabiliComeZip(idAccessoAtti, uuidPratica);

                var nomeFileZip = this._proxy.GetNomeFileZipPerDownloadDocumenti(idAccessoAtti, uuidPratica);
                var tempFilesDirectory = Path.Combine(Path.GetTempPath(), Guid.NewGuid().ToString());
                var tempZipDirectory = Path.Combine(Path.GetTempPath(), Guid.NewGuid().ToString());
                Directory.CreateDirectory(tempFilesDirectory);
                Directory.CreateDirectory(tempZipDirectory);

                try
                {

                    foreach (var codiceOggetto in codiciOggettoDocumentiConsultabili)
                    {

                        var file = await this._oggettiService.GetByIdAsync(codiceOggetto);

                        var fileName = $"{codiceOggetto}_{file.FileName}";
                        var filePath = Path.Combine(tempFilesDirectory, fileName);

                        // Salva il file nella directory temporanea
                        using (FileStream fileStream = new FileStream(filePath, FileMode.Create, FileAccess.Write, FileShare.None, 4096, true))
                        {
                            await fileStream.WriteAsync(file.FileContent, 0, file.Size);
                        }
                    }

                    var zipFilePath = Path.Combine(tempZipDirectory, nomeFileZip);

#pragma warning disable VSTHRD103 // Call async methods when in an async method
                    ZipFile.CreateFromDirectory(tempFilesDirectory, zipFilePath);
#pragma warning restore VSTHRD103 // Call async methods when in an async method

                    // Aggiungi un breve ritardo per garantire che il file ZIP sia completamente chiuso
                    await Task.Delay(500); // Ritardo di 500 millisecondi

                    byte[] zipFileBytes;
                    using (FileStream zipFileStream = new FileStream(zipFilePath, FileMode.Open, FileAccess.Read, FileShare.Read, 4096, true))
                    {
                        zipFileBytes = new byte[zipFileStream.Length];
                        await zipFileStream.ReadAsync(zipFileBytes, 0, (int)zipFileStream.Length);
                    }

                    // Crea e restituisci il BinaryFile con il contenuto del file ZIP
                    return BinaryFile.FromFileData(nomeFileZip, "application/zip", zipFileBytes);
                }
                finally
                {
                    if (Directory.Exists(tempFilesDirectory))
                    {
                        Directory.Delete(tempFilesDirectory, true);
                    }

                    if (Directory.Exists(tempZipDirectory))
                    {
                        Directory.Delete(tempZipDirectory, true);
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore nel download dei file per l'accesso agli atti {0} id pratica {1}: {2}", idAccessoAtti, uuidPratica, ex);

                throw;
            }
        }
    }
}
