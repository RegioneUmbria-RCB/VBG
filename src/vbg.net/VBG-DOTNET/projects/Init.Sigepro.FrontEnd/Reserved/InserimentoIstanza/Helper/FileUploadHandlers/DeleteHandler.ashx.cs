namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Helper.FileUploadHandlers
{
    /// <summary>
    /// Summary description for DeleteHandler
    /// </summary>
    public class DeleteHandler : DatiDinamiciFileUploadHandlerBase
    {
        public override void DoProcessRequestInternal()
        {
            //throw new Exception("Errore");
            SerializeResponse(new { result = "OK" });
        }
    }
}