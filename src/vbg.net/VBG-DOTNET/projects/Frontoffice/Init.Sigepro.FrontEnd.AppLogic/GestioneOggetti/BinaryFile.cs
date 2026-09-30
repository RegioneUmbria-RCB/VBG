// -----------------------------------------------------------------------
// <copyright file="BinaryFile.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti
{
    using System;
    using System.IO;

    public class BinaryFile
    {
        public static BinaryFile FromFileData(string nomeFile, string mimeType, byte[] bytes)
        {
            return new BinaryFile(nomeFile, mimeType, bytes);
        }

        public string FileName { get; set; }

        public string MimeType { get; set; }

        public byte[] FileContent { get; set; }

        public string Estensione
        {
            get
            {
                return Path.GetExtension(this.FileName);
            }
        }

        public virtual int Size { get { return this.FileContent.Length; } }

        protected BinaryFile() { }

        internal BinaryFile(string nomeFile, string mimeType, byte[] bytes)
        {
            this.Initialize(nomeFile, mimeType, bytes);
        }

        protected void Initialize(string nomeFile, string mimeType, byte[] bytes)
        {
            if (String.IsNullOrEmpty(Path.GetExtension(nomeFile)))
            {
                throw new ArgumentException("Il file caricato è privo di estensione. Verificare che il nome file contenga un'estensione valida");
            }

            this.FileName = Path.GetFileName(nomeFile);
            this.MimeType = mimeType;
            this.FileContent = bytes;
        }

        public void WriteTo(IBinaryFileOutStream stream)
        {
            stream.Write(this);
        }

        public string ToDataUrl()
        {
            return this.FileContent == null || this.FileContent.Length == 0 ?
                null :
                $"data:{this.MimeType};base64,{Convert.ToBase64String(this.FileContent)}";
        }
    }
}
