// -----------------------------------------------------------------------
// <copyright file="AggregateRootNotFoundException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.Dispatching
{
    using System;

    [Serializable]
    public class AggregateRootNotFoundException : Exception
    {
        public AggregateRootNotFoundException() { }
        public AggregateRootNotFoundException(string message) : base(message) { }
        public AggregateRootNotFoundException(string message, Exception inner) : base(message, inner) { }
    }
}
