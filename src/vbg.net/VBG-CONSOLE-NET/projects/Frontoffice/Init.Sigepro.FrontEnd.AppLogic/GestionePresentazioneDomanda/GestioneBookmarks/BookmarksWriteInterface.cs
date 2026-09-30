using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks
{
    public class BookmarksWriteInterface : IBookmarksWriteInterface
    {
        private PresentazioneIstanzaDbV2 _db;

        public BookmarksWriteInterface(PresentazioneIstanzaDbV2 db)
        {
            this._db = db;
        }

        public string Bookmark
        {
            set 
            {
                var chiaveEsistente = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

                if (chiaveEsistente != null)
                {
                    var valore = chiaveEsistente.Valore;

                    if (valore != value)
                    {
                        throw new Exception(String.Format("La domanda proviene da un bookmark diverso: Bookmark di provenienza={0}, nuovo bookmark={1}", chiaveEsistente.Valore, value));
                    }

                    return;
                }

                this._db.DatiExtra.AddDatiExtraRow(Constants.ChiaveDb, value);

                this._db.DatiExtra.AcceptChanges();
            }
        }
    }
}
