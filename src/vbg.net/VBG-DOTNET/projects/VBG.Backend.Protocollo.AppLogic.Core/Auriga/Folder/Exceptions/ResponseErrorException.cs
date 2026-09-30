namespace VBG.Backend.Protocollo.AppLogic.Core.Auriga.Folder.Exceptions
{
    [Serializable]
    public class ResponseErrorException : Exception
    {
        public string Messaggio { get; set; }

        public ResponseErrorException(string message)
        {
            this.Messaggio = message;
        }
    }
}
