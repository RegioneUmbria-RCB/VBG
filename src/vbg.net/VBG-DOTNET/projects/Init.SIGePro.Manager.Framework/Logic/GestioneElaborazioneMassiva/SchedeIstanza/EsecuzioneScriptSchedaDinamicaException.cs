using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneElaborazioneMassiva.SchedeIstanza
{
    [Serializable]
    public class EsecuzioneScriptSchedaDinamicaException: Exception
    {
        public EsecuzioneScriptSchedaDinamicaException(string messaggio)
            :base(messaggio)
        {

        }

        public EsecuzioneScriptSchedaDinamicaException()
        {
        }

        public EsecuzioneScriptSchedaDinamicaException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected EsecuzioneScriptSchedaDinamicaException(System.Runtime.Serialization.SerializationInfo serializationInfo, System.Runtime.Serialization.StreamingContext streamingContext)
        {
            throw new NotImplementedException();
        }
    }
}
