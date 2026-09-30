using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAllegati
{
	public interface IAllegatiWriteInterface
	{
		void Elimina(int id);
		int Allega(int codiceOggetto, string nomeFile, string md5, bool firmatoDigitalmente, string note);
		void ModificaNomeFileEFlagFirmaDaCodiceOggetto(int codiceOggetto, string nomeFile, bool firmatoDigitalmente);
	}


	public class AllegatiWriteInterface : IAllegatiWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public AllegatiWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region IAllegatiWriteInterface Members

		public void Elimina(int id)
		{
			var row = this._database.Allegati.FindById(id);

			if (row == null)
				return;

			row.Delete();
		}


		public void ModificaNomeFileEFlagFirmaDaCodiceOggetto(int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
		{
			var row = this._database.Allegati.Where(x => x.CodiceOggetto == codiceOggetto).FirstOrDefault();

			if (row == null)
				throw new Exception("Impossibile trovare l'allegato identificato dal codice oggetto " + codiceOggetto);

			row.NomeFile			= nomeFile;
			row.FirmatoDigitalmente = true;
		}

		#endregion


		public int Allega(int codiceOggetto, string nomeFile, string md5, bool firmatoDigitalmente, string note)
		{
			var row = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto,md5, firmatoDigitalmente, note);

			return row.Id;
		}
	}
}
