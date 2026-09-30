using System;
namespace VBG.Backend.SIT.AppLogic.Data
{
    /// <summary>
    /// </summary>
    public class ValidateSit
    {
        public ValidateSit()
        {
            this.ReturnValue = false;
            this.MessageCode = String.Empty;
            this.Message = String.Empty;
            this.DataSit = null;
        }

        public bool ReturnValue { get; set; }
        public string MessageCode { get; set; }
        public string Message { get; set; }
        public Sit? DataSit { get; set; }
    }
}
