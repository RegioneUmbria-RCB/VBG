using System;

namespace Init.SIGePro.Manager.Logic.Visura.QueryRicerca
{
    [Serializable]
    internal class ParameterIncorrectlySetException : Exception
    {
        public ParameterIncorrectlySetException()
        {
        }

        public ParameterIncorrectlySetException(string message) : base(message)
        {
        }

        public ParameterIncorrectlySetException(string message, Exception innerException) : base(message, innerException)
        {
        }
    }
}