using PersonalLib2.Data;
using System;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Mercati
{
    public class MercatiDyn2DataAccessFactory : Dyn2DataAccessFactoryBase
    {
        public class ClassLoader : IClasseContestoLoader
        {
            private readonly int _codiceMercato;
            private readonly string _idComune;
            private readonly DataBase _db;

            internal ClassLoader(DataBase db, string idComune, int codiceMercato)
            {
                this._codiceMercato = codiceMercato;
                this._idComune = idComune;
                this._db = db;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                return new MercatiMgr(this._db).GetById(this._idComune, this._codiceMercato);
            }
        }

        private readonly DataBase _database;
        private readonly string _idComune;
        private readonly int _codiceMercato;

        public MercatiDyn2DataAccessFactory(DataBase database, string idComune, int codiceMercato)
            : base(database, idComune)
        {
            this._database = database;
            this._idComune = idComune;
            this._codiceMercato = codiceMercato;
        }


        public override IClasseContestoLoader GetClassLoader()
        {
            return new ClassLoader(this._database, this._idComune, this._codiceMercato);
        }

        public override IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            // return new QueryLocalizzazioni(this._database, this._codiceIstanza, this._idComune);
            return null;
        }

        public override IDyn2DatiRepository GetRepository()
        {
            return new MercatiDyn2DatiRepository(this._database, this._codiceMercato, this._idComune);
        }

        public override IDyn2DatiStoricoRepository GetStoricoRepository(int idVersioneStorico)
        {
            throw new NotImplementedException();
        }
    }
}
