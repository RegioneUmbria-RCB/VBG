using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.Sigepro.FrontEnd.Infrastructure.Web
{
    public class WebDownloader : IDisposable
    {
        string _fileName;
        Stream _fileStream;
        bool _read = false;

        public WebDownloader()
        {
            this._fileName = Path.GetTempFileName();
        }

        public void Download(string url)
        {
            using (var client = new WebClient())
            {
                client.DownloadFile(url, this._fileName);

                this._read = true;
            }
        }

        public Stream GetStream()
        {
            if (!this._read)
            {
                throw new InvalidOperationException("il file non è ancora stato scaricato");
            }

            this._fileStream = File.OpenRead(this._fileName);

            return this._fileStream;
        }

        public byte[] GetBytes()
        {
            if (!this._read)
            {
                throw new InvalidOperationException("il file non è ancora stato scaricato");
            }

            return File.ReadAllBytes(this._fileName);
        }

        public void DeleteTemporaryFile()
        {
            if (this._fileStream != null)
            {
                this._fileStream.Close();
                this._fileStream.Dispose();
            }

            if (File.Exists(this._fileName))
            {
                File.Delete(this._fileName);
            }
        }

        public void Dispose()
        {
            DeleteTemporaryFile();
        }
    }
}
