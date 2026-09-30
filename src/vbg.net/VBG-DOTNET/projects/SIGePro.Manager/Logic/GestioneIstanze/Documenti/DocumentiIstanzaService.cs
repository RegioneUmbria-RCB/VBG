using log4net;

namespace Init.SIGePro.Manager.Logic.GestioneIstanze.Documenti
{
    public class DocumentiIstanzaService : IDocumentiIstanzaService
    {
        IDocumentiIstanzaUploader _documentiUploader;
        ILog _log = LogManager.GetLogger(typeof(DocumentiIstanzaService));

        public DocumentiIstanzaService(IDocumentiIstanzaUploader uploader)
        {
            this._documentiUploader = uploader;
        }

        public int Allega(int codiceIstanza, DescrittoreFile descrittore, byte[] fileContent)
        {
            _log.Debug($"Upload del documento con descrittore {descrittore.ToJsonString()} sull'istanza {codiceIstanza}");

            try
            {
                return this._documentiUploader.Upload(codiceIstanza, descrittore, fileContent);
            }finally
            {
                _log.Debug("upload riuscito");
            }
        }
    }
}
