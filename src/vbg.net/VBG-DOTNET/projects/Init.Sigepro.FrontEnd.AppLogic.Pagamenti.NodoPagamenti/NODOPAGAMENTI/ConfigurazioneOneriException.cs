using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{
    [Serializable]
    public class ConfigurazioneOneriException : Exception
    {
        public ConfigurazioneOneriException() { }
        public ConfigurazioneOneriException(string message) : base(message) { }
        public ConfigurazioneOneriException(string message, Exception inner) : base(message, inner) { }
    }
}