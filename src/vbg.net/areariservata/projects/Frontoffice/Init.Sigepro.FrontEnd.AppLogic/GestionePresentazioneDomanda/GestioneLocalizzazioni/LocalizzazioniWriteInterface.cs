using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using CuttingEdge.Conditions;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneLocalizzazioni
{
	public class LocalizzazioniWriteInterface : ILocalizzazioniWriteInterface
	{
		PresentazioneIstanzaDbV2 _database;

		public LocalizzazioniWriteInterface(PresentazioneIstanzaDbV2 database)
		{
			this._database = database;
		}

		#region ILocalizzazioniWriteInterface Members

		public void EliminaRiferimentoCatastale(int idCatasto)
		{
			var row = this._database.DATICATASTALI.FindById(idCatasto);

            if (row != null)
            {
                row.Delete();
                this._database.AcceptChanges();
            }
        }

		public void EliminaLocalizzazione(int idIndirizzo)
		{
			var riferimentiCatastaliDaEliminare = this._database.DATICATASTALI.Where(x => x.IdLocalizzazione == idIndirizzo).ToArray();

			for (int i = 0; i < riferimentiCatastaliDaEliminare.Length; i++)
				riferimentiCatastaliDaEliminare[i].Delete();

			var row = this._database.ISTANZESTRADARIO.FindByID(idIndirizzo);

			if (row == null)
				return;

			row.Delete();
            this._database.AcceptChanges();

        }
		
		public void AggiungiLocalizzazione(NuovaLocalizzazione loc)
		{
			AggiungiLocalizzazioneConRiferimentiCatastali(loc);
		}

		public void AssegnaRiferimentiCatastaliALocalizzazione(int idLocalizzazione, NuovoRiferimentoCatastale riferimentoCatastale)
		{
			this._database.DATICATASTALI.AddDATICATASTALIRow(riferimentoCatastale.CodiceTipoCatasto, riferimentoCatastale.TipoCatasto, riferimentoCatastale.Foglio, riferimentoCatastale.Particella, riferimentoCatastale.Sub, idLocalizzazione, riferimentoCatastale.Sezione);
		}

		public void AggiungiLocalizzazioneConRiferimentiCatastali(NuovaLocalizzazione localizzazione, NuovoRiferimentoCatastale riferimentoCatastale = null)
		{
			var row = this._database.ISTANZESTRADARIO.NewISTANZESTRADARIORow();

			object nextId = this._database.ISTANZESTRADARIO.Compute("max(ID)", null);

			if (nextId == DBNull.Value)
				nextId = 1;

			row.ID = Convert.ToInt32(nextId) + 1;

			row.CODICESTRADARIO = localizzazione.CodiceStradario;
			row.STRADARIO = localizzazione.Indirizzo;
			row.CIVICO = localizzazione.Civico;
			row.Esponente = localizzazione.Esponente;
			row.COLORE = localizzazione.Colore;
			row.Scala = localizzazione.Scala;
			row.Interno = localizzazione.Interno;
			row.EsponenteInterno = localizzazione.EsponenteInterno;
			row.NOTE = localizzazione.Note;
			row.Piano = localizzazione.Piano;
			row.Fabbricato = localizzazione.Fabbricato;
			row.Km = localizzazione.Km;
			row.Circoscrizione = localizzazione.Circoscrizione;
			row.Cap = localizzazione.Cap;
			row.Uuid = Guid.NewGuid().ToString();
			row.Latitudine = localizzazione.Latitudine;
			row.Longitudine = localizzazione.Longitudine;
			row.TipoLocalizzazione = localizzazione.TipoLocalizzazione;
			row.CodCivico = localizzazione.CodiceCivico;
			row.CodViario = localizzazione.CodiceViario;
            row.AccessoTipo = localizzazione.AccessoTipo;
            row.AccessoNumero = localizzazione.AccessoNumero;
            row.AccessoDescrizione = localizzazione.AccessoDescrizione;

			if (this._database.ISTANZE.Count == 0)
				throw new Exception("Non è stato specificato il codice comune per cui la domanda viene presentata, verificare i dati dell'istanza");

			row.CODICECOMUNE = this._database.ISTANZE[0].CODICECOMUNE;

			this._database.ISTANZESTRADARIO.AddISTANZESTRADARIORow(row);

			if (riferimentoCatastale != null)
				AssegnaRiferimentiCatastaliALocalizzazione(row.ID, riferimentoCatastale);
		}
		#endregion

	}
}
