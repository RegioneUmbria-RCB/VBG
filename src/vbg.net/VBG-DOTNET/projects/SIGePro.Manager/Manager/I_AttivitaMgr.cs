using Init.SIGePro.Data;
using SIGePro.Manager.Verticalizzazioni;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per CittadinanzaMgr.\n	/// </summary>
    public class IAttivitaMgr : BaseManager//, IIAttivitaManager
    {

        public IAttivitaMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public IAttivita GetById(string idComune, int id)
        {
            IAttivita retVal = new IAttivita();
            retVal.Id = id;
            retVal.IdComune = idComune;

            return this.db.GetClass(retVal);
        }

        #endregion


        #region IIAttivitaManager Members

        public IClasseContestoModelloDinamico LeggiAttivita(string idComune, int idAttivita)
        {
            return this.GetById(idComune, idAttivita);
        }

        #endregion


        public List<Software> GetSoftwareAttiviDaIdAttivita(string idComune, string idComuneAlias, int idAttivita)
        {
            var softwareMgr = new SoftwareMgr(this.db);

            var istanzaUltima = this.GetIstanzaUltima(idComune, idAttivita);

            if (istanzaUltima == null)
                return softwareMgr.GetSoftwareAttivi(idComune);

            var verticalizzazioneIAttivita = new VerticalizzazioneIAttivita(idComuneAlias, istanzaUltima.SOFTWARE);

            if (!verticalizzazioneIAttivita.Attiva || String.IsNullOrEmpty(verticalizzazioneIAttivita.Grupposoftware))
                return softwareMgr.GetSoftwareAttivi(idComune);

            return softwareMgr.GetListaSoftware(verticalizzazioneIAttivita.Grupposoftware.Split(','));

        }

        private Istanze GetIstanzaUltima(string idComune, int idAttivita)
        {

            bool closeCnn = false;

            try
            {
                if (this.db.Connection.State == ConnectionState.Closed)
                {
                    this.db.Connection.Open();
                    closeCnn = true;
                }

                var sql = @"SELECT 
								istanze.* 
							FROM 
								i_attivita, 
								istanze 
							WHERE
								istanze.idcomune = i_attivita.idcomune AND                                              
								istanze.codiceistanza = i_attivita.codiceistanzaultima and
								i_attivita.idcomune = {0} and i_attivita.id = {1}";

                sql = this.PreparaQueryParametrica(sql, "idComune", "idAttivita");

                using (IDbCommand cmd = this.db.CreateCommand(sql))
                {
                    cmd.Parameters.Add(this.db.CreateParameter("idComune", idComune));
                    cmd.Parameters.Add(this.db.CreateParameter("idAttivita", idAttivita));

                    return this.db.GetClassList<Istanze>(cmd).FirstOrDefault();
                }
            }
            finally
            {
                if (closeCnn)
                    this.db.Connection.Close();
            }

        }
    }
}