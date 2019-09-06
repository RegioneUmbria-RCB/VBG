namespace Init.SIGePro.Sit.Data
{
	using System;
	using System.Linq;
	using System.Collections.Generic;

    /// <summary>
    /// Classe che implementa l'interfaccia IRetSit.
    /// </summary>
    public class RetSit
    {
		public static RetSit Errore(MessageCode codiceErrore, string errore, bool esito)
		{
			return new RetSit
			{
				ReturnValue = esito,
				MessageCode = codiceErrore.ToString("d"),
				Message = errore
			};
		}

		public bool ReturnValue { get; set; }

		public string MessageCode { get; set; }

		public string Message { get; set; }

		public List<string> DataCollection { get; set; }

		public Dictionary<string,string> DataMap { get; set; }

		internal RetSit(): this(false){	}

		internal RetSit(bool returnValue) : this(returnValue, new List<string>()) { }

        internal RetSit(bool returnValue, IEnumerable<string> dataCollection)
        {
			ReturnValue = returnValue;
			MessageCode = String.Empty;
			Message = String.Empty;
			DataCollection = dataCollection.ToList<string>();
			DataMap = new Dictionary<string, string>();
		}
    }
}