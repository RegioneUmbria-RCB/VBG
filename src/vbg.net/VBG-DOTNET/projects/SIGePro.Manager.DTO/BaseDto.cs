using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.DTO
{
	public class BaseDto<T1,T2>
	{
		public T1 Codice { get; set; }
		public T2 Descrizione { get; set; }

		public BaseDto()
		{
		}

		public BaseDto(T1 codice , T2 descrizione)
		{
			Codice = codice;
			Descrizione = descrizione;
		}
	}
}
