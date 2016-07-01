using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.WebControls.Interventi
{
	[Serializable]
	internal class NodoAlberoInterventi
	{
		internal int Id { get; set; }
		internal string Descrizione { get; set; }
		internal bool NotePresenti { get; set; }
		internal List<NodoAlberoInterventi> NodiFiglio { get; set; }

		public NodoAlberoInterventi()
		{
			NodiFiglio = new List<NodoAlberoInterventi>();
		}

	}

	[Serializable]
	internal class RappresentazioneAlberoInterventi
	{
		internal NodoAlberoInterventi NodoRoot { get; set; }

		public RappresentazioneAlberoInterventi()
		{

		}
	}
}
