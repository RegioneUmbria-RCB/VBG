using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for DownloadHandler
    /// </summary>
    public class DownloadHandler : MovimentiFileUploadHandler
    {

        [Inject]
        public IOggettiRepository OggettiRepository { get; set; }

        private int CodiceOggetto
        {
            get
            {
                return Convert.ToInt32(this.Context.Request.QueryString["CodiceOggetto"]);
            }
        }


        public override void DoProcessRequestInternal()
        {
            try
            {
                var file = this.OggettiRepository.GetOggetto(this.CodiceOggetto);

                if (file == null)
                    throw new Exception("File " + this.CodiceOggetto + " non trovato");

                this.Context.Response.Clear();
                this.Context.Response.ContentType = file.MimeType;
                this.Context.Response.AddHeader("Content-Disposition", "attachment; filename=\"" + file.FileName + "\"");
                this.Context.Response.BinaryWrite(file.FileContent);
            }
            catch (Exception ex)
            {
                this.Context.Response.Clear();
                this.Context.Response.ContentType = "text/plain";
                this.Context.Response.Write("Errore durante la lettura del file specificato: " + ex.Message);
            }

        }
    }
}