using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using Init.SIGePro.Sit.Data;

namespace Init.SIGePro.Sit.Ravenna2
{
	class Ravenna2Result : Init.SIGePro.Sit.Data.Sit
	{
		// public string CodiceVia { get; protected set; }
		// public string Civico { get; protected set; }
		// public string Esponente { get; protected set; }
		// public string Sezione { get; protected set; }
		// public string Foglio { get; protected set; }
		// public string Particella { get; protected set; }
		// public string Edificio { get; protected set; }
		// public string Frazione { get; protected set; }
		// public string Cap { get; protected set; }
		public bool ElementoTrovato { get; private set; }
		public bool PiuDiUnElementoTrovato { get; protected set; }

		public Ravenna2Result(bool elementoTrovato)
		{
			this.ElementoTrovato = elementoTrovato;
			this.PiuDiUnElementoTrovato = false;
		}

        protected string GetVal(IDataReader dr, string fieldName)
        {
            var ordinal = dr.GetOrdinal(fieldName);
            if (ordinal >= 0)
            {
                return dr[ordinal].ToString();
            }

            return String.Empty;
        }

    }
}
