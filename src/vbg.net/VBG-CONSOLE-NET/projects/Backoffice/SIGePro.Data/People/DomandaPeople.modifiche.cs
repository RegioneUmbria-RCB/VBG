using System;
using System.Collections.Generic;
using System.Text;
using PersonalLib2.Data;
using System.Data;

namespace Init.SIGePro.Data.People
{
	public abstract class AnagraficaPeopleBase
	{
		public abstract Anagrafe ToAnagrafe(DataBase database, string idComune);

		public abstract bool IsRichiedente { get; }
		public abstract bool IsTecnico { get; }
		public abstract bool IsAzienda { get; }

		protected string BelfioreDaNomeComune(DataBase database, string nome)
		{
			string sql = "select CF from COMUNI where " + database.Specifics.UCaseFunction("comune") + " =" + database.Specifics.QueryParameterName("COMUNE");

			bool connectionOpen = false;

			using( IDbCommand cmd = database.CreateCommand( sql ) )
			{
				cmd.Connection = database.Connection;

				if ( cmd.Connection.State == ConnectionState.Closed )
				{
					connectionOpen = true;
					cmd.Connection.Open();
				}

				IDbDataParameter par = cmd.CreateParameter();
				par.ParameterName = database.Specifics.ParameterName("COMUNE");
				par.Value = nome.ToUpper();
				cmd.Parameters.Add(par);

				object obj = cmd.ExecuteScalar();

				if (obj == null ) return null;

				if( connectionOpen )
					cmd.Connection.Close();

				return String.IsNullOrEmpty(obj.ToString()) ? null : obj.ToString();
			}
		}
	}

	public partial class PersonaFisicaType : AnagraficaPeopleBase
	{
		#region IAnagraficaPeople Members

		public override Anagrafe ToAnagrafe(DataBase database,string idComune)
		{
			Anagrafe anagrafe = new Anagrafe();
			anagrafe.TIPOANAGRAFE	= "F";
			anagrafe.IDCOMUNE		= idComune;
			anagrafe.CODICEFISCALE	= this.CodiceFiscale;
			anagrafe.NOMINATIVO		= this.Cognome.ToUpper();
			anagrafe.NOME			= this.Nome.ToUpper();
			anagrafe.SESSO		= this.Sesso == Sesso.Maschio ? "M" : "F";

			if (this.DatiContatto != null)
			{
				anagrafe.EMAIL = this.DatiContatto.Email;
				anagrafe.TELEFONO = this.DatiContatto.Telefono;
				anagrafe.FAX = this.DatiContatto.Fax;
			}
			//TODO: Adattare l'indirizzo Dug + denominazione + civico chilometrico per IndirizzoStrutturatoCompleto

			anagrafe.CODCOMNASCITA = BelfioreDaNomeComune(database , this.LuogoNascita.Citta );

			if (this.LuogoNascita.DataSpecified)
				anagrafe.DATANASCITA = this.LuogoNascita.Data;

			// TODO: Decodificare il comune di nascita

			// Comune di residenza
			if (this.DatiResidenza != null)
			{
				anagrafe.INDIRIZZO		 = this.DatiResidenza.Via;
				anagrafe.COMUNERESIDENZA = BelfioreDaNomeComune(database , this.DatiResidenza.Citta);
				anagrafe.PROVINCIA		 = this.DatiResidenza.Provincia;
			}

			return anagrafe;
		}

		public override bool IsAzienda
		{
			get { return this.Tipo == "A"; }
		}

		public override bool IsRichiedente
		{
			get { return this.Tipo == "R"; }
		}

		public override bool IsTecnico
		{
			get { return this.Tipo == "T"; }
		}

		#endregion

	}


	public partial class PersonaGiuridicaType : AnagraficaPeopleBase
	{
		public override Anagrafe ToAnagrafe(DataBase database, string idComune)
		{
			Anagrafe anagrafe = new Anagrafe();
			anagrafe.TIPOANAGRAFE = "G";
			anagrafe.IDCOMUNE = idComune;
			anagrafe.NOMINATIVO = this.Denominazione.ToUpper();
			anagrafe.PARTITAIVA = !String.IsNullOrEmpty(this.PartitaIva) ? this.PartitaIva : "";
			anagrafe.CODICEFISCALE = !String.IsNullOrEmpty(this.CodiceFiscale) ? this.CodiceFiscale : "";

			if (this.Sede != null)
			{
				anagrafe.INDIRIZZO = this.Sede.Via;
				anagrafe.COMUNERESIDENZA = BelfioreDaNomeComune(database, this.Sede.Citta);
				anagrafe.PROVINCIA = this.Sede.Provincia;
			}

			if (this.DatiContatto != null)
			{
				anagrafe.TELEFONO = this.DatiContatto.Telefono;
				anagrafe.FAX = this.DatiContatto.Fax;
				anagrafe.EMAIL = this.DatiContatto.Email;
			}

			if (this.Tribunale != null)
			{
				anagrafe.REGTRIB = this.Tribunale.Numero;
				anagrafe.CODCOMREGTRIB = BelfioreDaNomeComune(database, this.Tribunale.NomeLuogo);
			}

			if (this.Cciaa != null)
			{
				anagrafe.REGDITTE = this.Cciaa.Numero;
				anagrafe.CODCOMREGDITTE = BelfioreDaNomeComune(database, this.Cciaa.NomeLuogo);
			}

			return anagrafe;
		}

		public override bool IsAzienda
		{
			get { return this.Tipo == "A"; }
		}

		public override bool IsRichiedente
		{
			get { return this.Tipo == "R"; }
		}

		public override bool IsTecnico
		{
			get { return this.Tipo == "T"; }
		}
	}

	public partial class PersonaRappresentataType
	{
		public AnagraficaPeopleBase GetAnagrafica()
		{
			return (AnagraficaPeopleBase)this.Item;
		}
	}
}
