using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Ninject;
using System;

namespace Init.Sigepro.FrontEnd.Reserved.GestioneMovimenti.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for ReadHandler
    /// </summary>
    public class ReadHandler : MovimentiFileUploadHandler
    {
        [Inject]
        protected IOggettiRepository OggettiRepository { get; set; }

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
                    throw new Exception("L'oggetto identificato dal codiceoggetto " + this.CodiceOggetto + " non è stato trovato");

                var obj = new
                {
                    codiceOggetto = this.CodiceOggetto,
                    nomeFile = file.FileName,
                    size = file.FileContent.Length,
                    mime = file.MimeType
                };

                this.SerializeResponse(obj);
            }
            catch (Exception ex)
            {
                this.SerializeResponse(new { Errori = ex.Message });
            }
        }
    }
}