using Init.SIGePro.Data;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;

namespace Init.SIGePro.Manager
{   ///<summary>
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

            return (Responsabili)this.db.GetClass(filtro);
        }

        public Responsabili GetByUserId(string idComune, string userId)
        {
            Responsabili filtro = new Responsabili();
            filtro.IDCOMUNE = idComune;
            filtro.USERID = userId;

            return (Responsabili)this.db.GetClass(filtro);
        }

        [Obsolete("Utilizzare GetById(string idComune, int codiceResponsabile)")]
        public Responsabili GetById(String pCODICERESPONSABILE, String pIDCOMUNE)
        {
            Responsabili retVal = new Responsabili();
            retVal.CODICERESPONSABILE = pCODICERESPONSABILE;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<Responsabili> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Responsabili;

            return null;
        }

        public Responsabili GetByResponsabile(String pRESPONSABILE, String pIDCOMUNE)
        {
            Responsabili retVal = new Responsabili();
            retVal.RESPONSABILE = pRESPONSABILE;
            retVal.IDCOMUNE = pIDCOMUNE;

            List<Responsabili> mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return (mydc[0]) as Responsabili;

            return null;
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
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
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

                sql = this.PreparaQueryParametrica(sql, "IdComune", "codiceResponsabile");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("IdComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("codiceResponsabile", codiceResponsabile));

                    return this.db.GetClassList<Amministrazioni>(cmd, new GetClassListFlags(PersonalLib2.Sql.useForeignEnum.No, true));
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }


        public bool VerificaSeAmministratoreSoftware(string idComune, int codiceResponsabile, string software)
        {
            Responsabili responsabile = this.GetById(idComune, codiceResponsabile);

            if (responsabile == null)
                return false;

            // Verifico se l'operatore è un amministratore
            if (responsabile.AMMINISTRATORE == "1")
                return true;


            // Verifico se l'operatore è un amministratore di software e se tra i software di cui è amministratore 
            // è presente anche il software a cui appartiene l'istanza
            if (responsabile.AMMINISTRATORESOFTWARE == "1")
            {
                if (new ResponsabiliSoftwareMgr(this.db).GetById(idComune, codiceResponsabile, software) != null)
                    return true;
            }

            return false;
        }
    }
}