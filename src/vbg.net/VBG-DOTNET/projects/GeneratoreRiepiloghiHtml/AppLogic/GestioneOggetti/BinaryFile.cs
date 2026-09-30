namespace GeneratoreRiepiloghiHtml.AppLogic.GestioneOggetti
{
    public class BinaryFile
    {
        public static BinaryFile FromFileData(string nomeFile, string mimeType, byte[] bytes)
        {
            return new BinaryFile(nomeFile, mimeType, bytes);
        }

        public string FileName { get; }

        public string MimeType { get; }

        public byte[] FileContent { get; }

        public string Estensione
        {
            get
            {
                return Path.GetExtension(this.FileName);
            }
        }

        public virtual int Size { get { return this.FileContent.Length; } }

        private BinaryFile(string nomeFile, string mimeType, byte[] bytes)
        {
            this.FileName = nomeFile;
            this.MimeType = mimeType;
            this.FileContent = bytes;
        }
    }
}
