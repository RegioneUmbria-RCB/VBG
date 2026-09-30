using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public class WebRequestAttachmentWriter : IDisposable
    {
        public static class Constants
        {
            public const string HeaderTemplate = "Content-Disposition: form-data; name=\"{0}\"; filename=\"{1}\"\r\n Content-Type:{2}\r\n\r\n";
        }

        private bool _isDisposed = false;
        private readonly byte[] _boundaryBytes;
        private readonly MemoryStream _memoryStream;
        private readonly HttpWebRequest _httpWebRequest;
        private string _boundaryString;

        public WebRequestAttachmentWriter(HttpWebRequest httpWebRequest)
            : this(httpWebRequest, $"----------------{DateTime.Now.Ticks.ToString("x")}")
        {
        }

        public WebRequestAttachmentWriter(HttpWebRequest httpWebRequest, string boundary)
        {
            this._httpWebRequest = httpWebRequest;
            this._boundaryString = boundary;
            this._boundaryBytes = System.Text.Encoding.ASCII.GetBytes("\r\n--" + boundary + "\r\n");
            this._memoryStream = new System.IO.MemoryStream();

            WriteBoundary();
        }

        private void WriteFile(UploadingFileData fileData)
        {
            this.WriteFileToStream(fileData);
            this.WriteBoundary();
        }

        private void WriteFileToStream(UploadingFileData fileData)
        {
            string header = string.Format(Constants.HeaderTemplate, fileData.Name, fileData.Name, fileData.Mime);

            byte[] headerbytes = Encoding.UTF8.GetBytes(header);
            this._memoryStream.Write(headerbytes, 0, headerbytes.Length);
            using (var readStream = new MemoryStream(fileData.Data))
            {
                var buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = readStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    this._memoryStream.Write(buffer, 0, bytesRead);
                }
                readStream.Close();
            }
        }


        private void WriteBoundary()
        {
            this._memoryStream.Write(this._boundaryBytes, 0, this._boundaryBytes.Length);
        }

        public long Length => this._memoryStream.Length;

        internal void WriteFiles(UploadingFileData[] files)
        {
            foreach (var file in files)
            {
                this.WriteFile(file);
            }

            OutputToWebRequest();
        }

        internal void OutputToWebRequest()
        {
            this._httpWebRequest.ContentLength = this.Length;
            this._httpWebRequest.ContentType = "multipart/mixed;+ boundary=" + this._boundaryString;

            using (Stream requestStream = this._httpWebRequest.GetRequestStream())
            {
                this._memoryStream.Position = 0;

                var buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = this._memoryStream.Read(buffer, 0, buffer.Length)) != 0)
                {
                    requestStream.Write(buffer, 0, bytesRead);
                }
            }
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool disposing)
        {
            if (this._isDisposed) return;

            if (disposing)
            {
                // free managed resources
                this._memoryStream.Dispose();
            }

            this._isDisposed = true;
        }
    }

}
