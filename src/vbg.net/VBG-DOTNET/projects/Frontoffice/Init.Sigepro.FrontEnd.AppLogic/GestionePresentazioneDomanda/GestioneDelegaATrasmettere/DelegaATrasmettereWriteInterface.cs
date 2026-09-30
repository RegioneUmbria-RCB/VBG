using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneDelegaATrasmettere
{
    public class DelegaATrasmettereWriteInterface : IDelegaATrasmettereWriteInterface
    {
        PresentazioneIstanzaDbV2 _database;

        public DelegaATrasmettereWriteInterface(PresentazioneIstanzaDbV2 database)
        {
            this._database = database;
        }

        #region IDelegaATrasmettereWriteInterface Members

        public void EliminaDelegaATrasmettere()
        {
            EnsureRowExists();

            this._database.DelegaATrasmettere[0].SetIdAllegatoNull();
        }

        public void EliminaDocumentoIdentita()
        {
            EnsureRowExists();

            this._database.DelegaATrasmettere[0].SetIdDocumentoIdentitaNull();
        }

        public void SalvaAllegato(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente)
        {
            var allegatiRow = this._database.Allegati.AddAllegatiRow(nomefile, codiceoggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

            EnsureRowExists();

            this._database.DelegaATrasmettere[0].IdAllegato = allegatiRow.Id;
        }

        public void SalvaDocumentoIdentita(int codiceoggetto, string nomefile, bool isFirmatoDigitalmente)
        {
            var allegatiRow = this._database.Allegati.AddAllegatiRow(nomefile, codiceoggetto, String.Empty, isFirmatoDigitalmente, String.Empty);

            EnsureRowExists();

            this._database.DelegaATrasmettere[0].IdDocumentoIdentita = allegatiRow.Id;
        }

        #endregion
        private void EnsureRowExists()
        {
            if (this._database.DelegaATrasmettere.Count == 0)
            {
                this._database.DelegaATrasmettere.AddDelegaATrasmettereRow(this._database.DelegaATrasmettere.NewDelegaATrasmettereRow());
            }
        }
    }
}
