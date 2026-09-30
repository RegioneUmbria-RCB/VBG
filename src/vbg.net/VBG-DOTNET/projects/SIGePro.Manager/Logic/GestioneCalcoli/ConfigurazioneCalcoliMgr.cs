using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.IOC;
using Init.Utils.Sorting;
using PersonalLib2.Data;
using System.Collections.Generic;
using System.ComponentModel;

namespace Init.SIGePro.Manager.Logic.GestioneCalcoli
{
    [DataObject(true)]
    public class ConfigurazioneCalcoliMgr : BaseManager
    {
        public ConfigurazioneCalcoliMgr(DataBase dataBase) : base(dataBase) { }

        public void UpdateDescrizione(string idComune, int id, string descrizione)
        {
            if (string.IsNullOrEmpty(idComune))
            {
                throw new System.ArgumentException($"'{nameof(idComune)}' non può essere null o vuoto.", nameof(idComune));
            }

            if (string.IsNullOrEmpty(descrizione))
            {
                throw new System.ArgumentException($"'{nameof(descrizione)}' non può essere null o vuoto.", nameof(descrizione));
            }

            var sql = $@"update
                              configurazione_calcoli
                         set
                             descrizione = {this.db.Specifics.QueryParameterName("descrizione")}
                        where
                           idcomune = {this.db.Specifics.QueryParameterName("idcomune")} and 
                           id = {this.db.Specifics.QueryParameterName("id")}";

            this.db.ExecuteNonQuery(sql,
                mp =>
                {
                    mp.AddParameter("idcomune", idComune);
                    mp.AddParameter("id", id);
                    mp.AddParameter("descrizione", descrizione);
                });

        }

        public static List<ConfigurazioneCalcoli> Find(string token, string sortExpression)
        {

            var authInfo = StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(token);

            var filtro = new ConfigurazioneCalcoli
            {
                Idcomune = authInfo.IdComune
            };

            List<ConfigurazioneCalcoli> list = authInfo.CreateDatabase()
                                              .GetClassList(filtro)
                                              .ToList();

            ListSortManager<ConfigurazioneCalcoli>.Sort(list, sortExpression);

            return list;
        }
    }
}
