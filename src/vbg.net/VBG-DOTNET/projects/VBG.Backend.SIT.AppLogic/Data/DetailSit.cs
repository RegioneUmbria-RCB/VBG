namespace VBG.Backend.SIT.AppLogic.Data
{
    using System;

    public class DetailSit
    {
        public DetailSit()
        {
            this.MessageCode = String.Empty;
            this.Message = String.Empty;
            this.Field = Array.Empty<DetailField>();
        }

        public bool ReturnValue { get; set; }

        public string MessageCode { get; set; }

        public string Message { get; set; }

        public DetailField[] Field { get; set; }
    }
}