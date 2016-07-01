using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.Sincronizzazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.NominativoCongruente;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneProcure;
using Init.Sigepro.FrontEnd.AppLogic.GestioneComuni;
using System.Data;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche
{
	public class AnagraficheWriteInterface : IAnagraficheWriteInterface
	{
		public static class Constants
		{

//
//			public const string TipoSoggettoAzienda = "A";
//			public const string TipoSoggettoRichiedente = "R";
//			public const string TipoSoggettoTecnico = "T";
//
			public const string IdColonnaAnagraficaCollegata = "IdAnagraficaCollegata";
//			public const string IdColonnaTipoSoggetto = "TIPOSOGGETTO";
//			public const string IdColonnaChiavePrimaria = "ANAGRAFE_PK";
		}

		PresentazioneIstanzaDbV2 _database;
		//IProcureWriteInterface _procureWriteInterface;

		public AnagraficheWriteInterface(PresentazioneIstanzaDbV2 database/*, IProcureWriteInterface procureWriteInterface*/)
		{
			this._database = database;
			//this._procureWriteInterface = procureWriteInterface;
		}

		#region IAnagraficheWriteInterface Members

		public void CollegaAziendaAdAnagrafica(int idAnagrafica, int idAziendaCollegata)
		{
			var r = this._database.ANAGRAFE.FindByANAGRAFE_PK(idAnagrafica);
			r.IdAnagraficaCollegata = idAziendaCollegata == -1 ? String.Empty : idAziendaCollegata.ToString();
		}

		public void Elimina(int idAnagrafica)
		{
			var rigaDaEliminare = this._database.ANAGRAFE.FindByANAGRAFE_PK(idAnagrafica);

			// Se esistono altre anagrafiche collegate a quella che sto eliminando allora cancello la relazione
			var rows = this._database.ANAGRAFE.Where(x => x.IdAnagraficaCollegata == idAnagrafica.ToString());

			foreach (var row in rows)
				row[Constants.IdColonnaAnagraficaCollegata] = DBNull.Value;

			rigaDaEliminare.Delete();

			this._database.ANAGRAFE.AcceptChanges();
		}

		public void Crea(TipoPersonaEnum tipoPersona, string codiceFiscale)
		{
			var newRow = this._database.ANAGRAFE.NewANAGRAFERow();

			newRow.TIPOANAGRAFE		= tipoPersona == TipoPersonaEnum.Fisica ? AnagraficheConstants.TipiPersona.Fisica : AnagraficheConstants.TipiPersona.Giuridica;

			if (tipoPersona == TipoPersonaEnum.Fisica || codiceFiscale.Length == 16)
				newRow.CODICEFISCALE = codiceFiscale.ToUpperInvariant();
			else
				newRow.PartitaIva = codiceFiscale.ToUpperInvariant();


			this._database.ANAGRAFE.AddANAGRAFERow(newRow);
		}

		public void AggiungiAnagraficaConSoggettoCollegato(AnagraficaDomanda anagrafica, AnagraficaDomanda anagraficaCollegata, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione)
		{
			var idAnagraficaCollegata = AggiungiOAggiornaInternal(anagraficaCollegata, logicaSincronizzazione);
			var idAnagrafica = AggiungiOAggiornaInternal(anagrafica, logicaSincronizzazione);

			CollegaAziendaAdAnagrafica(idAnagrafica, idAnagraficaCollegata);			
		}


		public void AggiungiOAggiorna(AnagraficaDomanda anagrafica, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione)
		{
			AggiungiOAggiornaInternal(anagrafica, logicaSincronizzazione);
		}

		public int AggiungiOAggiornaInternal(AnagraficaDomanda anagrafica, ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione)
		{
			var newRow = anagrafica.ToAnagrafeRow();

			// Verifico la validità del tipo soggetto
			logicaSincronizzazione.Sincronizza(newRow);

			// Se sto aggiungendo una nuova anagrafica verifico che non esista un anagrafica con lo stesso codice fiscale/
			// partita iva ma con nominativo diverso
			var existingRow = GetById(newRow.ANAGRAFE_PK);
			if (existingRow == null)
			{
				var specification = NominativoCongruenteSpecificationFactory.Create(this._database.ANAGRAFE.Select(x => AnagraficaDomanda.FromAnagrafeRow(x)), anagrafica.TipoPersona);

				if (!specification.IsVerified(anagrafica))
					throw new Exception(specification.GetTestoUltimoErrore());

				existingRow = this._database.ANAGRAFE.NewANAGRAFERow();

				foreach (var col in this._database.ANAGRAFE.Columns.Cast<DataColumn>())
				{
					if (col.ColumnName == "ANAGRAFE_PK")
						continue;

					existingRow[col.ColumnName] = newRow[col.ColumnName];
				}

				this._database.ANAGRAFE.AddANAGRAFERow(existingRow);

			}
			else
			{
				foreach (var col in this._database.ANAGRAFE.Columns.Cast<DataColumn>())
					existingRow[col.ColumnName] = newRow[col.ColumnName];

			}

            if (existingRow.IsFlagRichiedeAnagraficaCollegataNull())
            {
                existingRow.FlagRichiedeAnagraficaCollegata = false;
            }

			if (!existingRow.FlagRichiedeAnagraficaCollegata)
			{
				existingRow.SetIdAnagraficaCollegataNull();
			}

			return existingRow.ANAGRAFE_PK;
		}

		private PresentazioneIstanzaDbV2.ANAGRAFERow GetById(int id)
		{
			return this._database.ANAGRAFE.FindByANAGRAFE_PK(id);
		}

		public void Sincronizza(ILogicaSincronizzazioneTipiSoggetto logicaSincronizzazione)
		{
			 foreach (var anagrafica in this._database.ANAGRAFE)
                logicaSincronizzazione.Sincronizza(anagrafica);
		}


		public void VerificaFlagsCittadiniExtracomunitari(ICittadinanzeService cittadinanzeService)
		{
			var els = this._database.ANAGRAFE.Where(x => x.TIPOANAGRAFE == AnagraficheConstants.TipiPersona.Fisica && x.IsIsCittadinoExtracomunitarioNull());

			foreach (var anagrafica in els)
				anagrafica.IsCittadinoExtracomunitario = cittadinanzeService.IsCittadinanzaExtracomunitaria((int)anagrafica.CODICECITTADINANZA);
		}

		#endregion
	}
}
