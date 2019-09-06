using System;
using System.Collections.Generic;
using System.Text;

namespace Init.SIGePro.Sit.Data
{
    /// <summary>
    /// </summary>
    public class ValidateSit
    {
        public ValidateSit()
        {
			ReturnValue = false;
			MessageCode = String.Empty;
			Message = String.Empty;
			DataSit = null;
		}

        public bool ReturnValue{get;set;}
        public string MessageCode{get;set;}
        public string Message{get;set;}
        public Sit DataSit{get;set;}
    }
}
