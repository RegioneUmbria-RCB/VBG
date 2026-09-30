using System;
using System.Collections.Generic;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Data
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
        public string CODICEOGGETTO { get; set; } = null;
        public string IDCOMUNE { get; set; } = null;

        #endregion

        public byte[] OGGETTO { get; set; } = null;

        public string NOMEFILE { get; set; } = null;

        public string Extension { get; set; } = "";

        public string MimeType { get; set; } = "";

        public string Descrizione { get; set; } = "";

        public string Percorso { get; set; }
        public long ID { get; set; } = -1;
        public bool? InviaTramitePec { get; set; } = true;

        public Dictionary<String, String> Metadati { get; set; } = new Dictionary<string, string>();

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
