namespace VBG.Backend.SIT.AppLogic.Data
{
    using System;
    using System.Collections.Generic;
    using System.Linq;

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

        public Dictionary<string, string> DataMap { get; set; }

        internal RetSit() : this(false) { }

        internal RetSit(bool returnValue) : this(returnValue, new List<string>()) { }

        internal RetSit(bool returnValue, IEnumerable<string> dataCollection)
        {
            this.ReturnValue = returnValue;
            this.MessageCode = String.Empty;
            this.Message = String.Empty;
            this.DataCollection = dataCollection.ToList<string>();
            this.DataMap = new Dictionary<string, string>();
        }

        public RetSit(bool returnValue, MessageCode messageCode, string message) : this(returnValue)
        {
            this.ReturnValue = returnValue;
            this.MessageCode = messageCode.ToString();
            this.Message = message;
        }
    }
}