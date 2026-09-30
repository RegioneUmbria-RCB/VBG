using System;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Exceptions
{
    public class UploadFileException : Exception
    {
        public int? StatusCode { get; }
        public string StatusDescription { get; }
        public string ErrorMessageResponse { get; }
        public string ResponseContent { get; }

        public UploadFileException(
            string message,
            int? statusCode,
            string statusDescription,
            string errorMessageResponse,
            string responseContent,
            Exception innerException = null)
            : base(message, innerException)
        {
            StatusCode = statusCode;
            StatusDescription = statusDescription;
            ErrorMessageResponse = errorMessageResponse;
            ResponseContent = responseContent;
        }
    }
}
