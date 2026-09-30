using Init.SIGePro.Data;
using PersonalLib2.Data;
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
            var filtro = new Responsabili();
            filtro.IDCOMUNE = idComune;
            filtro.CODICERESPONSABILE = codiceResponsabile.ToString();

            return this.db.GetClass(filtro);
        }


        public Responsabili GetByUserId(string idComune, string userId)
        {
            var filtro = new Responsabili();
            filtro.IDCOMUNE = idComune;
            filtro.USERID = userId;

            return this.db.GetClass(filtro);
        }



        //public Responsabili GetByResponsabile(String pRESPONSABILE, String pIDCOMUNE)
        //{
        //    Responsabili retVal = new Responsabili();
        //    retVal.RESPONSABILE = pRESPONSABILE;
        //    retVal.IDCOMUNE = pIDCOMUNE;

        //    var mydc = this.db.GetClassList(retVal, true);
        //    if (mydc.Count != 0)
        //        return mydc[0];

        //    return null;
        //}



        ///// <summary>
        ///// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        ///// </summary>
        ///// <param name="p_class">Criteri di ricerca</param>
        ///// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        //public List<Responsabili> GetList(Responsabili p_class)
        //{
        //    return this.db.GetClassList(p_class).ToList<Responsabili>();
        //}

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

                    return this.db.GetClassList<Amministrazioni>(cmd, new GetClassListFlags(PersonalLib2.Sql.useForeignEnum.No));
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }

        public bool VerificaSeAmministratore(string idComune, int codiceResponsabile)
        {
            Responsabili responsabile = this.GetById(idComune, codiceResponsabile);

            if (responsabile == null)
                return false;

            // Verifico se l'operatore è un amministratore
            return responsabile.AMMINISTRATORE == "1";
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