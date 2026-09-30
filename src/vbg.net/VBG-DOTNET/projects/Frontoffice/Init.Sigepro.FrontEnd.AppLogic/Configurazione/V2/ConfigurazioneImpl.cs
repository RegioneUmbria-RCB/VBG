using System;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ConfigurazioneImpl<T> : IConfigurazione<T> where T : class, IParametriConfigurazione
    {
        private readonly IConfigurazioneBuilder<T> _builder;
        private T _parametri;

        public ConfigurazioneImpl(IConfigurazioneBuilder<T> builder)
        {
            if (builder == null)
                throw new ArgumentNullException(nameof(builder));
            //Condition.Requires(builder, "builder").IsNotNull();

            this._builder = builder;
        }

        #region IConfigurazione<T> Members

        public T Parametri
        {
            get
            {
                if (this._parametri == null)
                    this._parametri = this._builder.Build();

                return this._parametri;
            }
        }

        #endregion
    }
}
