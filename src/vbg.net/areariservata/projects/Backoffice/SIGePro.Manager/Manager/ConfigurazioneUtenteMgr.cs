
using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using System.Data;
using System.ComponentModel;
using Init.SIGePro.Authentication;

using PersonalLib2.Sql;
using Init.Utils.Sorting;

namespace Init.SIGePro.Manager
{
    [DataObject(true)]
    public partial class ConfigurazioneUtenteMgr
    {
		public T GetValoreParametro<T>(string idComune, int codiceUtente, string nomeParametro, T valoreDefault)
		{
			ConfigurazioneUtente valore = GetById(codiceUtente, nomeParametro, idComune);

            if (valore == null || String.IsNullOrEmpty(valore.Valore))
            {
                SetValoreParametro(idComune, codiceUtente, nomeParametro, valoreDefault);
                return valoreDefault;
            }

			return (T)Convert.ChangeType(valore.Valore, typeof(T));
		}

        public void SetValoreParametro<T>(string idComune, int codiceUtente, string nomeParametro, T valore)
        {
            ConfigurazioneUtenteMgr confUtMgr = new ConfigurazioneUtenteMgr(db);
            ConfigurazioneUtente confUt = confUtMgr.GetById(codiceUtente, nomeParametro, idComune);

            if (confUt == null)
            {
                confUt = new ConfigurazioneUtente();
                confUt.Idcomune = idComune;
                confUt.Codiceresponsabile = codiceUtente;
                confUt.Nomeparametro = nomeParametro;
                confUt.Valore = valore.ToString();
                confUtMgr.Insert(confUt);
            }
            else
            {
                confUt.Valore = valore.ToString();
                confUtMgr.Update(confUt);
            }
        }

        public int GetValoreNumRecordListe(string idComune, int codiceUtente)
        {
            return GetValoreParametro<int>(idComune, codiceUtente, "NUMRECORDLISTE", 20);
        }
	}
}
				