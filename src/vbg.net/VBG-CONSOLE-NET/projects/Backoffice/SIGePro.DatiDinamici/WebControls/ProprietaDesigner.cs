using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.WebControls
{
	public enum TipoControlloEditEnum
	{
		TextBox,
		ListBox
	}

	public partial class ProprietaDesigner
	{
        string _listaValori = "";

        public bool VisibileInDesigner { get; set; }
        public string ValoreDefault { get; set; }
        public string NomeProprieta { get; set; }
        public TipoControlloEditEnum TipoControlloEditing { get; set; }
        public string Descrizione { get; set; }

        public IEnumerable<KeyValuePair<string, string>> ValoriLista
		{
			get
			{
                // I valori contenuti sono nel formato valore1=chiave1, valore2=chiave2 oppure valore1, valore2
                return _listaValori.Split(',').Select(x =>
                {
                    var parts = x.Trim().Split('=');

                    if (parts.Length == 1)
                    {
                        return new KeyValuePair<string, string>(parts[0], parts[0]);
                    }

                    return new KeyValuePair<string, string>(parts[1], parts[0]);
                });
			}
		}
        
        public ProprietaDesigner(string nomeProprieta, string descrizione, string valoreDefault) : 
            this(nomeProprieta, descrizione, valoreDefault, true)
        {
        }

		public ProprietaDesigner(string nomeProprieta, string descrizione, string valoreDefault, bool visibileInDesigner) : 
            this(nomeProprieta, descrizione, TipoControlloEditEnum.TextBox, "", valoreDefault, visibileInDesigner)
        {
        }

		public ProprietaDesigner(string nomeProprieta, string descrizione, TipoControlloEditEnum tipoControllo, string listaValori, string valoreDefault) : 
            this(nomeProprieta, descrizione, tipoControllo, listaValori, valoreDefault, true)
        {
        }

		public ProprietaDesigner(string nomeProprieta, string descrizione, TipoControlloEditEnum tipoControllo, string listaValori, string valoreDefault, bool visibileInDesigner)
		{
			this.NomeProprieta = nomeProprieta;
			this.Descrizione = descrizione;
			this.TipoControlloEditing = tipoControllo;
			this._listaValori = listaValori;
			this.ValoreDefault = valoreDefault;
			this.VisibileInDesigner = visibileInDesigner;
		}

	}
}
