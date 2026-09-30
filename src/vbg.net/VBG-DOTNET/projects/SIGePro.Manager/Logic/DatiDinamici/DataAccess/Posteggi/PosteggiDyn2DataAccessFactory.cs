using PersonalLib2.Data;
using System;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Posteggi
{
    public class PosteggiDyn2DataAccessFactory : Dyn2DataAccessFactoryBase
    {
        public class ClassLoader : IClasseContestoLoader
        {
            private readonly int _idPosteggio;
            private readonly string _idComune;
            private readonly DataBase _db;

            internal ClassLoader(DataBase db, string idComune, int idPosteggio)
            {
                this._idPosteggio = idPosteggio;
                this._idComune = idComune;
                this._db = db;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                return new Mercati_DMgr(this._db).GetById(this._idComune, this._idPosteggio);
            }
        }

        private readonly DataBase _database;
        private readonly string _idComune;
        private readonly int _idPosteggio;

        public PosteggiDyn2DataAccessFactory(DataBase database, string idComune, int idPosteggio)
            : base(database, idComune)
        {
            this._database = database;
            this._idComune = idComune;
            this._idPosteggio = idPosteggio;
        }


        public override IClasseContestoLoader GetClassLoader()
        {
            return new ClassLoader(this._database, this._idComune, this._idPosteggio);
        }

        public override IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            // return new QueryLocalizzazioni(this._database, this._codiceIstanza, this._idComune);
            return null;
        }

        public override IDyn2DatiRepository GetRepository()
        {
            return new PosteggiDyn2DatiRepository(this._database, this._idPosteggio, this._idComune);
        }

        public override IDyn2DatiStoricoRepository GetStoricoRepository(int idVersioneStorico)
        {
            throw new NotImplementedException();
        }
    }
}
