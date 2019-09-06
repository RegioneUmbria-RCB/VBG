using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.StcService;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
	public class NodoStc
	{
		public string Id { get; private set; }
		public string Ente { get; private set; }
		public string Sportello { get; private set; }

		internal NodoStc(string id, string ente, string sportello)
		{
			if (String.IsNullOrEmpty(id))
				throw new ArgumentNullException("id");

			if (String.IsNullOrEmpty(ente))
				throw new ArgumentNullException("ente");

			if (String.IsNullOrEmpty(sportello))
				throw new ArgumentNullException("sportello");

			this.Id = id;
			this.Ente = ente;
			this.Sportello = sportello;
		}

		public SportelloType AsSportelloType()
		{
			return new SportelloType
			{
				idNodo = this.Id,
				idEnte = this.Ente,
				idSportello = this.Sportello
			};
		}

        public override string ToString()
        {
            return String.Format("idNodo={0}, idente={1}, idSportello={2}", this.Id, this.Ente, this.Sportello);
        }
	}
}
