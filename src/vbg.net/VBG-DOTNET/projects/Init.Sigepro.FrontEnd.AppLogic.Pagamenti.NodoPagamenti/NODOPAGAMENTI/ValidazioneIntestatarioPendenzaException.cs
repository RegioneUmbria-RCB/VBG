using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI
{

    [Serializable]
    public class ValidazioneIntestatarioPendenzaException : Exception
    {
        public ValidazioneIntestatarioPendenzaException(string nome, string cognome, string descrizioneErrore) :
            this($"Errore di validazione dei dati dell'intestatario della pendenza ({nome}{(String.IsNullOrEmpty(cognome) ? "" : " ")}{cognome}): {descrizioneErrore}")
        { }
        public ValidazioneIntestatarioPendenzaException(string message) : base(message) { }
        public ValidazioneIntestatarioPendenzaException(string message, Exception inner) : base(message, inner) { }
    }
}
