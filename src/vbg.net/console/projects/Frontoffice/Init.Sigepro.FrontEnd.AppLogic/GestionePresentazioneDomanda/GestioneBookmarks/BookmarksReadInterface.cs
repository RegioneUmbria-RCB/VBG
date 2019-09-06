using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneBookmarks
{
    public class BookmarksReadInterface : IBookmarksReadInterface
    {
        private PresentazioneIstanzaDbV2 _db;

        public BookmarksReadInterface(PresentazioneIstanzaDbV2 db)
        {
            this._db = db;
        }

        public string Bookmark
        {
            get 
            { 
                var valore = this._db.DatiExtra.FindByChiave(Constants.ChiaveDb);

                if (valore == null)
                {
                    return String.Empty;
                }

                if (String.IsNullOrEmpty(valore.Valore))
                {
                    return String.Empty;
                }

                return valore.Valore;
            }
        }
    }
}
