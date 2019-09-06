using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneAnagrafiche.NominativoCongruente
{
	internal static class NominativoCongruenteSpecificationFactory
	{
		public static INominativoCongruenteSpecification Create(IEnumerable<AnagraficaDomanda> anagraficheEsistenti, TipoPersonaEnum tipoPersona)
		{
			return tipoPersona == TipoPersonaEnum.Fisica ?
				(INominativoCongruenteSpecification)new NominativoPersonaFisicaCongruenteSpecification(anagraficheEsistenti) :
				(INominativoCongruenteSpecification)new NominativoPersonaGiuridicaCongruenteSpecification(anagraficheEsistenti);
		}
	}

	internal class NominativoPersonaFisicaCongruenteSpecification : INominativoCongruenteSpecification
	{
		IEnumerable<AnagraficaDomanda> _anagraficheEsistenti;
		string _testoUltimoErrore = String.Empty;

		internal NominativoPersonaFisicaCongruenteSpecification(IEnumerable<AnagraficaDomanda> anagraficheEsistenti)
		{
			this._anagraficheEsistenti = anagraficheEsistenti;
		}

		#region INominativoCongruenteSpecification Members

		public bool IsVerified(AnagraficaDomanda anagraficaDomanda)
		{
			if (anagraficaDomanda.TipoPersona != TipoPersonaEnum.Fisica)
				throw new InvalidOperationException("Si sta cercando di verificare la congruenza del nominativo di una persona giuridica utilizzando NominativoPersonaFisicaCongruenteSpecification");

			_testoUltimoErrore = String.Empty;

			var codiceFiscale = anagraficaDomanda.Codicefiscale.ToUpperInvariant();

			var refrow = (from AnagraficaDomanda r in this._anagraficheEsistenti
						  where r.Codicefiscale.ToUpperInvariant() == codiceFiscale &&
								r.TipoPersona == TipoPersonaEnum.Fisica
						  select r).FirstOrDefault();

			//	Non esiste ancora un'anagrafica con il codice fiscale immesso
			if (refrow == null)
				return true;

			// Esiste già un'anagrafica con lo stesso codice fiscale ma sia il nome che il cognome coincidono
			if (anagraficaDomanda.Nominativo.ToUpperInvariant() == refrow.Nominativo.ToUpperInvariant() &&
				anagraficaDomanda.Nome.ToUpperInvariant() == refrow.Nome.ToUpperInvariant())
				return true;


			if (anagraficaDomanda.Nominativo.ToUpper() != refrow.Nominativo.ToUpper())
			{
				var erroreFmtStr = "Esiste già un anagrafica con il codice fiscale {0} ma il cognome immesso non coincide (cognome esistente: {1}, cognome immesso: {2})";

				_testoUltimoErrore = String.Format(erroreFmtStr, codiceFiscale, refrow.Nominativo, anagraficaDomanda.Nominativo);
			}
			else if (anagraficaDomanda.Nome.ToUpper() != refrow.Nome.ToUpper())
			{
				var erroreFmtStr = "Esiste già un anagrafica con il codice fiscale {0} ma il nome immesso non coincide (nome esistente: {1}, nome immesso: {2})";

				_testoUltimoErrore = String.Format(erroreFmtStr, codiceFiscale, refrow.Nome, anagraficaDomanda.Nome);
			}

			return false;
		}


		public string GetTestoUltimoErrore()
		{
			return _testoUltimoErrore;
		}

		#endregion
	}


	internal class NominativoPersonaGiuridicaCongruenteSpecification : INominativoCongruenteSpecification
	{
		IEnumerable<AnagraficaDomanda> _anagraficheEsistenti;
		string _testoUltimoErrore;


		internal NominativoPersonaGiuridicaCongruenteSpecification(IEnumerable<AnagraficaDomanda> anagraficheEsistenti)
		{
			this._anagraficheEsistenti = anagraficheEsistenti;
		}

		#region INominativoCongruenteSpecification Members

		public bool IsVerified(AnagraficaDomanda anagraficaDomanda)
		{
			if (anagraficaDomanda.TipoPersona != TipoPersonaEnum.Giuridica)
				throw new InvalidOperationException("Si sta cercando di verificare la congruenza del nominativo di una persona fisica utilizzando NominativoPersonaGiuridicaCongruenteSpecification");

			_testoUltimoErrore = String.Empty;

			var codiceFiscale = anagraficaDomanda.Codicefiscale.ToUpperInvariant();
			var partitaIva = anagraficaDomanda.PartitaIva.ToUpperInvariant();

			var confrontaCodiceFiscale = String.IsNullOrEmpty(partitaIva.Trim());

			AnagraficaDomanda refrow;

			if (confrontaCodiceFiscale)
			{
				refrow = (from AnagraficaDomanda r in this._anagraficheEsistenti
						  where r.Codicefiscale.ToUpperInvariant() == codiceFiscale &&
								r.TipoPersona == TipoPersonaEnum.Giuridica
						  select r).FirstOrDefault();
			}
			else
			{
				refrow = (from AnagraficaDomanda r in this._anagraficheEsistenti
						  where r.PartitaIva.ToUpperInvariant() == partitaIva &&
								r.TipoPersona == TipoPersonaEnum.Giuridica
						  select r).FirstOrDefault();
			}

			//	Non esiste ancora un'anagrafica con il codice fiscale/partita iva immesso
			if (refrow == null)
				return true;

			// Esiste già un'anagrafica con lo stesso codice fiscale/partita iva ma il nominativo coincide
			if (anagraficaDomanda.Nominativo.ToUpperInvariant() == refrow.Nominativo.ToUpperInvariant())
				return true;

			var erroreFmtStr = "Esiste già un anagrafica con {0} ma la ragione sociale non coincide (ragione sociale esistente: {1}, ragione sociale immessa: {2})";

			_testoUltimoErrore = String.Format(erroreFmtStr,
												confrontaCodiceFiscale ? "codice fiscale " + codiceFiscale :
																		 "partita iva " + partitaIva,
												refrow.Nominativo,
												anagraficaDomanda.Nominativo);

			return false;
		}


		public string GetTestoUltimoErrore()
		{
			return _testoUltimoErrore;
		}

		#endregion
	}
}
