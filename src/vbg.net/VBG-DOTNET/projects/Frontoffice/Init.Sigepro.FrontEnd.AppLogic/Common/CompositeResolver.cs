using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Common
{

    public abstract class CompositeResolver : IResolver
    {
        private readonly IResolver _fallback;

        public CompositeResolver(IResolver fallback)
        {
            this._fallback = fallback;
        }

        public string GetValue()
        {
            var value = this.InternalGetValue();

            if (!String.IsNullOrEmpty(value))
            {
                return value;
            }

            return this._fallback.GetValue();
        }

        protected abstract string InternalGetValue();
    }

}
