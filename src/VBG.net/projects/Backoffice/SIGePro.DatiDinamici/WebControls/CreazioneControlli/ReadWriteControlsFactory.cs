using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Reflection;
using System.ComponentModel;
using Init.SIGePro.DatiDinamici.Interfaces.WebControls;

namespace Init.SIGePro.DatiDinamici.WebControls.CreazioneControlli
{
	public partial class ReadWriteControlsFactory : IDatiDinamiciControlsFactory
	{
		#region IDatiDinamiciControlsFactory Members

		public IDatiDinamiciControl CreaControllo( CampoDinamicoBase campo )
		{
			Type tipoControllo = ControlliDatiDinamiciDictionary.Items[campo.TipoCampo].TipoElemento;

			IDatiDinamiciControl ctrl = (IDatiDinamiciControl)Activator.CreateInstance(tipoControllo, campo);


			// Carico le proprietà con i valori di default
/*			ProprietaDesigner[] proprieta = null;
			MethodInfo mi = ctrl.GetType().GetMethod("GetProprietaDesigner", BindingFlags.Static | BindingFlags.Public);

			proprieta = (ProprietaDesigner[])mi.Invoke(null, null);

			// Salvo i valori di default in un dictionary
			Dictionary<string, object> propDictionary = new Dictionary<string, object>();

			for (int i = 0; i < proprieta.Length; i++)
				propDictionary.Add(proprieta[i].NomeProprieta, proprieta[i].ValoreDefault);

			// Assegno al dictionary delle proprietà i valori letti dal db
			foreach (KeyValuePair<string, string> it in campo.ProprietaControlloWeb)
			{
				if (!propDictionary.ContainsKey(it.Key))
					propDictionary.Add(it.Key, it.Value);
				else
					propDictionary[it.Key] = it.Value;
			}


			// Assegno i valori al controllo
			PropertyDescriptorCollection propCollection = TypeDescriptor.GetProperties(ctrl);

			foreach (string key in propDictionary.Keys)
			{
				try
				{
					string nomeProperty = key;
					object value = propDictionary[key];

					// HACK: data la separazione dei progetti non è possibile assegnarla nel campo dinamico (anche se sarebbe più corretto)
					if (nomeProperty == "TipoNumerico" && campo is CampoDinamico)
					{
						(campo as CampoDinamico).TipoNumerico = Convert.ToBoolean(value);
						continue;
					}

					PropertyDescriptor pd = propCollection[nomeProperty];

					if (pd != null)
					{
						pd.SetValue(ctrl, Convert.ChangeType(value, pd.PropertyType));
					}
				}
				catch (Exception ex)
				{
					if (errHandler != null)
						errHandler(ex.Message);
				}
			}
*/
			return ctrl;
		}

		#endregion
	}
}
