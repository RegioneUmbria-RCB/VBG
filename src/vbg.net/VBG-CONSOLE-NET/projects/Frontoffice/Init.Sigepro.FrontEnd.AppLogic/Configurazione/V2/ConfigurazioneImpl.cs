using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	internal class ConfigurazioneImpl<T> : IConfigurazione<T> where T:class, IParametriConfigurazione
	{
		private readonly IConfigurazioneBuilder<T> _builder;
		private T _parametri;

		public ConfigurazioneImpl(IConfigurazioneBuilder<T> builder)
		{
			Condition.Requires(builder, "builder").IsNotNull();

			_builder = builder;
		}

		#region IConfigurazione<T> Members

		public T Parametri
		{
			get 
			{
				if (_parametri == null)
					_parametri = _builder.Build();

				return _parametri;
			}
		}

		#endregion
	}
}
