using System;
using System.Collections;
using Init.SIGePro.Data;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{ 	///<summary>
	/// Descrizione di riepilogo per ResponsabiliMgr.\n	/// </summary>
	public class ResponsabiliMgr : BaseManager
	{

		public ResponsabiliMgr(DataBase dataBase) : base(dataBase) { }

		#region Metodi per l'accesso di base al DB
		public Responsabili GetById(string idComune, int codiceResponsabile)
		{
			Responsabili filtro = new Responsabili();
			filtro.IDCOMUNE = idComune;
			filtro.CODICERESPONSABILE = codiceResponsabile.ToString();

			return (Responsabili)db.GetClass(filtro);
		}

        public Responsabili GetByUserId(string idComune, string userId)
        {
            Responsabili filtro = new Responsabili();
            filtro.IDCOMUNE = idComune;
            filtro.USERID = userId;

            return (Responsabili)db.GetClass(filtro);
        }

		[Obsolete("Utilizzare GetById(string idComune, int codiceResponsabile)")]
		public Responsabili GetById(String pCODICERESPONSABILE, String pIDCOMUNE)
		{
			Responsabili retVal = new Responsabili();
			retVal.CODICERESPONSABILE = pCODICERESPONSABILE;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as Responsabili;

			return null;
		}

		public Responsabili GetByResponsabile(String pRESPONSABILE, String pIDCOMUNE)
		{
			Responsabili retVal = new Responsabili();
			retVal.RESPONSABILE = pRESPONSABILE;
			retVal.IDCOMUNE = pIDCOMUNE;

			PersonalLib2.Sql.Collections.DataClassCollection mydc = db.GetClassList(retVal, true, false);
			if (mydc.Count != 0)
				return (mydc[0]) as Responsabili;

			return null;
		}



		/// <summary>
		/// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
		/// </summary>
		/// <param name="p_class">Criteri di ricerca</param>
		/// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
		public ArrayList GetList(Responsabili p_class)
		{
			return this.GetList(p_class, null);
		}

		public ArrayList GetList(Responsabili p_class, Responsabili p_cmpClass)
		{
			return db.GetClassList(p_class, p_cmpClass, false, false);
		}

		#endregion

		/// <summary>
		/// Legge la lista di amministrazioni (con FLAG_AMMINISTRAZIONEINTERNA = 1) collegate al responsabile
		/// </summary>
		/// <param name="idComune">Filtro idcomune</param>
		/// <param name="codiceResponsabile">codice responsabile</param>
		/// <returns>Lista di amministrazioni collegate al responsabile</returns>
		public List<Amministrazioni> GetListaAmministrazioniResponsabile(string idComune, int codiceResponsabile)
		{
			bool closeCnn = false;

			try
			{
				if (db.Connection.State == ConnectionState.Closed)
				{
					db.Connection.Open();
					closeCnn = true;
				}

				string sql = @"SELECT 
									AMMINISTRAZIONI.* 
								FROM 
									AMMINISTRAZIONI, 
									AMMINISTRAZIONIRESPONSABILI 
								WHERE 
									AMMINISTRAZIONIRESPONSABILI.IDCOMUNE = AMMINISTRAZIONI.IDCOMUNE AND 
									AMMINISTRAZIONIRESPONSABILI.CODICEAMMINISTRAZIONE = AMMINISTRAZIONI.CODICEAMMINISTRAZIONE AND 
									AMMINISTRAZIONI.IDCOMUNE = {0} AND 
									AMMINISTRAZIONI.FLAG_AMMINISTRAZIONEINTERNA = 1 AND 
									AMMINISTRAZIONIRESPONSABILI.CODICERESPONSABILE = {1}";

				sql = PreparaQueryParametrica(sql,"IdComune","codiceResponsabile");

				using (IDbCommand cmd = db.CreateCommand(sql))
				{
					cmd.Parameters.Add(db.CreateParameter("IdComune", idComune));
					cmd.Parameters.Add(db.CreateParameter("codiceResponsabile", codiceResponsabile));

					return db.GetClassList<Amministrazioni>(cmd, new GetClassListFlags(PersonalLib2.Sql.useForeignEnum.No, true));
				}
			}
			finally
			{
				if (closeCnn)
					db.Connection.Close();
			}

		}


		public bool VerificaSeAmministratoreSoftware(string idComune, int codiceResponsabile, string software)
		{
			Responsabili responsabile = GetById(idComune, codiceResponsabile);

			if (responsabile == null)
				return false;

			// Verifico se l'operatore è un amministratore
			if (responsabile.AMMINISTRATORE == "1")
				return true;


			// Verifico se l'operatore è un amministratore di software e se tra i software di cui è amministratore 
			// è presente anche il software a cui appartiene l'istanza
			if (responsabile.AMMINISTRATORESOFTWARE == "1")
			{
				if (new ResponsabiliSoftwareMgr(db).GetById(idComune, codiceResponsabile, software) != null)
					return true;
			}

			return false;
		}
	}
}