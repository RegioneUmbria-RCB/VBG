namespace Init.SIGePro.Sit.Data
{
	using System;
	using System.Collections.Generic;
	using System.Text;

    public class ListSit
    {
		// Valore di ritorno
		public bool ReturnValue
		{
			get;
			set;
		}

		// Codice del messaggio
		public string MessageCode
		{
			get;
			set;
		}

		// Messaggio
		public string Message
		{
			get;
			set;
		}

		public List<string> Field
		{
			get;
			set;
		}
		
		public ListSit()
        {
			this.MessageCode = String.Empty;
			this.Message = String.Empty;
		}
    }
}
