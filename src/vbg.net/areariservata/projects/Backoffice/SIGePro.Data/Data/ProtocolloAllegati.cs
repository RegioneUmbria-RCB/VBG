using System;
using System.Linq;
using System.Data;
using Init.SIGePro.Attributes;
using PersonalLib2.Sql.Attributes;

namespace Init.SIGePro.Data
{
	/// <summary>
	/// Descrizione di riepilogo per ProtocolloAllegati.
	/// </summary>
	public class ProtocolloAllegati
	{
		public ProtocolloAllegati()
		{
		}

        #region Key Fields
        string codiceoggetto = null;
        public string CODICEOGGETTO
        {
            get { return codiceoggetto; }
            set { codiceoggetto = value; }
        }

        string idcomune = null;
        public string IDCOMUNE
        {
            get { return idcomune; }
            set { idcomune = value; }
        }

        #endregion

        byte[] oggetto = null;
        public byte[] OGGETTO
        {
            get { return oggetto; }
            set { oggetto = value; }
        }

        string nomefile = null;
        public string NOMEFILE
        {
            get { return nomefile; }
            set { nomefile = value; }
        }

		private string _Extension = "";
		public string Extension
		{
			get { return _Extension; }
			set { _Extension = value; }
		}

		private string _MimeType = "";
		public string MimeType
		{
			get { return _MimeType; }
			set { _MimeType = value; }
		}

		private string _Descrizione = "";
		public string Descrizione
		{
			get { return _Descrizione; }
			set { _Descrizione = value; }
		}

        public string Percorso { get; set; }

		private long _ID = -1;
		public long ID
		{
			get { return _ID; }
			set { _ID = value; }
		}

        

		public void RimuoviCaratteriNonValidiDaNomeFile(string caratteriNonValidi)
		{
			if (String.IsNullOrEmpty(caratteriNonValidi))
				return;

			this.NOMEFILE = new String(this.NOMEFILE.Where(c => !caratteriNonValidi.Contains(c)).ToArray());
		}

        public string GetPathOggetti()
        {
            string padding = this.CODICEOGGETTO.PadLeft(10, '0');
            string directory = padding.Substring(0, 4) + "\\" + padding.Substring(4, 2) + "\\" + padding.Substring(6, 2);

            return directory;
        }
    }
}
