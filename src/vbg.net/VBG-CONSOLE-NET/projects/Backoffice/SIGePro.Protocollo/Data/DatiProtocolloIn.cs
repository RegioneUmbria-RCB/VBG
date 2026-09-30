using System.Collections;
using System.Collections.Generic;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.WsDataClass;
using System;

namespace Init.SIGePro.Protocollo.Data
{
	public class DatiProtocolloIn
	{
		public DatiProtocolloIn()
		{

		}

        public string Classifica { get; set; }
        public string TipoDocumento { get; set; }
        public string TipoSmistamento { get; set; }
        public string Oggetto { get; set; }
        public string Corpo { get; set; }
		/// <summary>
		/// A=Arrivo, P=Partenza, I=Interno.
		/// </summary>
        public string Flusso { get; set; }
        public string NumProtMitt { get; set; }
        public string DataProtMitt { get; set; }
        public ListaMittDest Mittenti { get; set; }
        public ListaMittDest Destinatari { get; set; }
        public List<ProtocolloAllegati> Allegati { get; set; }
        /// <summary>
        /// Se la protocollazione è in ambito istanza allora prende la data dell'istanza, se è in ambito movimento allora prende la data del movimento, 
        /// se l'ambito è pec allora prende la data della pec, altrimenti la data corrente.
        /// </summary>
        public DateTime? DataRegistrazione { get; set; }
	}
}

