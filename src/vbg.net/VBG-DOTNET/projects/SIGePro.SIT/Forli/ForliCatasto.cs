using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Sit.Forli
{
    public class ForliCatasto
    {
        private readonly DataBase _db;

        public ForliCatasto(string connectionString)
        {
            this._db = new DataBase(connectionString, ProviderType.OracleClient);
        }

        public IEnumerable<string> GetListaFogli(string tipoCatasto)
        {
            // Al momento il tipo catasto non utilizzato e fogli e particelle sono utilizzati indistintamente.
            // Intanto metto un parametro nel metodo, se poi fosse necessario filtrarli sarà più facile
            var sql = $@"select foglio from ced_v_fogli order by foglio";

            return this._db.ExecuteReader(sql, (_) => { }, dr => dr.GetString("foglio"));
        }

        public bool ValidaFoglio(string tipoCatasto, string foglio)
        {
            // Al momento il tipo catasto non utilizzato e fogli e particelle sono utilizzati indistintamente.
            // Intanto metto un parametro nel metodo, se poi fosse necessario filtrarli sarà più facile
            var sql = $@"select count(*) from ced_v_fogli where foglio = {this._db.QueryParameter("foglio")}";

            var count = this._db.ExecuteScalar(sql, 0, (mp) => mp.Add("foglio", foglio));

            return count != 0;
        }

        public IEnumerable<string> GetListaParticelle(string tipoCatasto, string foglio)
        {
            if (string.IsNullOrEmpty(foglio))
            {
                throw new System.ArgumentException($"'{nameof(foglio)}' non può essere vuoto.", nameof(foglio));
            }
            // Al momento il tipo catasto non utilizzato e fogli e particelle sono utilizzati indistintamente.
            // Intanto metto un parametro nel metodo, se poi fosse necessario filtrarli sarà più facile
            var sql = $@"select particella from ced_v_particelle where foglio = {this._db.QueryParameter("foglio")} order by particella";

            return this._db.ExecuteReader(sql, (mp) => mp.Add("foglio", foglio), dr => dr.GetString("particella"));
        }

        public bool ValidaParticella(string tipoCatasto, string foglio, string particella)
        {
            if (string.IsNullOrEmpty(foglio))
            {
                throw new System.ArgumentException($"'{nameof(foglio)}' non può essere vuoto.", nameof(foglio));
            }

            if (string.IsNullOrEmpty(particella))
            {
                throw new System.ArgumentException($"'{nameof(particella)}' non può essere vuoto.", nameof(particella));
            }

            var sql = $@"select foglio, particella from ced_v_particelle where foglio = {this._db.QueryParameter("foglio")} and particella = {this._db.QueryParameter("particella")}";

            var count = this._db.ExecuteScalar(
                sql,
                0,
                (mp) => mp.Add("foglio", foglio)
                          .Add("particella", particella));

            return count != 0;
        }

        public IEnumerable<string> GetListaSubalterni(string tipoCatasto, string foglio, string particella)
        {
            var sql = $"SELECT subalterno FROM ced_v_subalterni WHERE foglio={this._db.QueryParameter("foglio")} AND particella={this._db.QueryParameter("particella")} order by subalterno";

            return this._db.ExecuteReader(
                sql,
                mp => mp.Add("foglio", foglio)
                        .Add("particella", particella),
                dr => dr.GetString("subalterno"));
        }

        public bool ValidaSubalterno(string tipoCatasto, string foglio, string particella, string subalterno)
        {
            if (string.IsNullOrEmpty(foglio))
            {
                throw new System.ArgumentException($"'{nameof(foglio)}' non può essere vuoto.", nameof(foglio));
            }

            if (string.IsNullOrEmpty(particella))
            {
                throw new System.ArgumentException($"'{nameof(particella)}' non può essere vuoto.", nameof(particella));
            }

            if (string.IsNullOrEmpty(subalterno))
            {
                throw new System.ArgumentException($"'{nameof(subalterno)}' non può essere vuoto.", nameof(subalterno));
            }

            var sql = $@"
                        select 
                            foglio, 
                            particella as particella,
                            subalterno
                        from 
                            ced_v_subalterni 
                        where 
                            foglio = {this._db.QueryParameter("foglio")} and 
                            particella = {this._db.QueryParameter("particella")} and 
                            subalterno = {this._db.QueryParameter("subalterno")}";

            var count = this._db.ExecuteScalar(
                sql,
                0,
                (mp) => mp.Add("foglio", foglio)
                          .Add("particella", particella)
                          .Add("subalterno", subalterno));

            return count != 0;
        }
    }
}
