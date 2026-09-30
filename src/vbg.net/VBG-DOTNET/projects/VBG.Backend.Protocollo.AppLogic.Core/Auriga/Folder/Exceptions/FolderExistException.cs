namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.Exceptions
{
    [Serializable]
    public class FolderExistException : Exception
    {
        public string Libreria { get; set; }
        public string PathNome { get; set; }

        public FolderExistException(string libreria, string pathNome, string message, Exception ex) : base(message, ex)
        {
            this.Libreria = libreria;
            this.PathNome = pathNome;
        }
    }
}
