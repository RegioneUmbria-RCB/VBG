using System;
using System.Runtime.Serialization;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI
{
    [Serializable]
    public class ConfigurazioneOneriException : Exception
    {
        public ConfigurazioneOneriException() { }
        public ConfigurazioneOneriException(string message) : base(message) { }
        public ConfigurazioneOneriException(string message, Exception inner) : base(message, inner) { }
        protected ConfigurazioneOneriException(
          System.Runtime.Serialization.SerializationInfo info,
          System.Runtime.Serialization.StreamingContext context) : base(info, context) { }
    }
}