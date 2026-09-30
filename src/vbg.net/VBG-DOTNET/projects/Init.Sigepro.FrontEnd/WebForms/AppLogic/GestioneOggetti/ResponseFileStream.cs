using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using System.Web;

namespace Init.Sigepro.FrontEnd.WebForms.AppLogic.GestioneOggetti
{
    public class ResponseFileStream : IBinaryFileOutStream
    {
        private readonly HttpResponse _response;

        public ResponseFileStream(HttpResponse response)
        {
            this._response = response;
        }
        public void Write(BinaryFile binaryFile)
        {
            this._response.Clear();
            this._response.AddHeader("content-disposition", "attachment; filename=\"" + binaryFile.FileName.Replace("\"", "_") + "\"");
            this._response.ContentType = binaryFile.MimeType;
            this._response.BinaryWrite(binaryFile.FileContent);
        }
    }
}