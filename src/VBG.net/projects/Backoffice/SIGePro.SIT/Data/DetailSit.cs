namespace Init.SIGePro.Sit.Data
{
	using System;
	using System.Collections.Generic;
	using System.Text;

    public class DetailSit
    {
        public DetailSit()
        { 
			this.MessageCode = String.Empty;
			this.Message = String.Empty;
		}

        public bool ReturnValue { get; set; }

        public string MessageCode { get; set; }

        public string Message { get; set; }

        public DetailField[] Field { get; set; }
    }
}