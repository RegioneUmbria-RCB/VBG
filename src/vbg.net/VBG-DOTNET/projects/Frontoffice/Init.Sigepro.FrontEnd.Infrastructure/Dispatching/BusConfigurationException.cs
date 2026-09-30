// -----------------------------------------------------------------------
// <copyright file="BusConfigurationException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
    using System;

    [Serializable]
    public class BusConfigurationException : Exception
    {
        public BusConfigurationException() { }
        public BusConfigurationException(string message) : base(message) { }
        public BusConfigurationException(string message, Exception inner) : base(message, inner) { }
    }
}
