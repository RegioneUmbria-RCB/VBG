using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Configuration;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.WebConfig
{
	public abstract class ConfigurazioneCollectionBase<T>: ConfigurationElementCollection where T:ConfigurationElement,new()
 	{

		public T this[int index]
		{
			get
			{
				return base.BaseGet(index) as T;
			}
			set
			{
				if (base.BaseGet(index) != null)
				{
					base.BaseRemoveAt(index);
				}
				this.BaseAdd(index, value);
			}
		}

		public new  T this[string name]
		{
			get
			{
				return base.BaseGet(name) as T;
			}
			set
			{
				int index = -1;
				if (base.BaseGet(name) != null)
				{
					index = base.BaseIndexOf(base.BaseGet(name));
					base.BaseRemove(name);
				}

				if (index == -1)
				{
					this.BaseAdd(value);
				}
				else
				{
					this.BaseAdd(index, value);
				}
			}
		}

		protected override ConfigurationElement CreateNewElement()
		{
			return new T();
		}

		protected abstract override object GetElementKey(ConfigurationElement element);

	}
}
