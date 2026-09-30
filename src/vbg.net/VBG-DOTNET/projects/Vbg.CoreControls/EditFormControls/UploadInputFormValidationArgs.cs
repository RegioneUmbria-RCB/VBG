using Microsoft.AspNetCore.Components.Forms;

namespace Vbg.CoreControls.EditFormControls
{
    public class UploadInputFormValidationArgs
    {
        public IBrowserFile FileToValidate { get; }
        public bool Success { get; private set; } = true;
        public string ErrorMessage { get; private set; } = "";

        public UploadInputFormValidationArgs(IBrowserFile fileToValidate)
        {
            this.FileToValidate = fileToValidate;
        }

        public void MarkAsFailed(string message)
        {
            this.Success = false;
            this.ErrorMessage = message;
        }
    }
}
