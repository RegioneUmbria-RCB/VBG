// -----------------------------------------------------------------------
// <copyright file="ModelValidationException.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace Init.Sigepro.FrontEnd.Infrastructure.ModelBase
{
    using System;

    [Serializable]
    public class ModelValidationException : Exception
    {
        public ModelValidationException() { }
        public ModelValidationException(string message) : base(message) { }
        public ModelValidationException(string message, Exception inner) : base(message, inner) { }
    }
}
