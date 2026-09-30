using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Logic.GestioneSchedeAttivita;
using System;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.Attivita
{
    public class AttivitaDyn2DataAccessFactory : Dyn2DataAccessFactoryBase
    {
        public class ClassLoader : IClasseContestoLoader
        {
            private readonly AuthenticationInfo _authenticationInfo;
            private readonly int _id;

            internal ClassLoader(AuthenticationInfo authenticationInfo, int codiceAnagrafe)
            {
                this._authenticationInfo = authenticationInfo;
                this._id = codiceAnagrafe;
            }

            public IClasseContestoModelloDinamico LoadClass()
            {
                using (var db = this._authenticationInfo.CreateDatabase())
                {
                    return new IAttivitaMgr(db).GetById(this._authenticationInfo.IdComune, this._id);
                }
            }
        }

        private readonly AuthenticationInfo _authenticationInfo;
        private readonly ISchedeDinamicheAttivitaService _schedeDinamicheAttivitaService;
        private readonly int _idAttivita;

        public AttivitaDyn2DataAccessFactory(AuthenticationInfo authenticationInfo, ISchedeDinamicheAttivitaService schedeDinamicheAttivitaService, int idAttivita)
            : base(authenticationInfo.CreateDatabase(), authenticationInfo.IdComune)
        {
            this._authenticationInfo = authenticationInfo;
            this._schedeDinamicheAttivitaService = schedeDinamicheAttivitaService;
            this._idAttivita = idAttivita;
        }

        public override IClasseContestoLoader GetClassLoader()
        {
            return new ClassLoader(this._authenticationInfo, this._idAttivita);
        }

        public override IQueryLocalizzazioni GetQueryLocalizzazioni()
        {
            throw new NotImplementedException();
        }

        public override IDyn2DatiRepository GetRepository()
        {
            return new AttivitaDyn2DatiRepository(this._schedeDinamicheAttivitaService, this._idAttivita);
        }

        public override IDyn2DatiStoricoRepository GetStoricoRepository(int idVersioneStorico)
        {
            return new AttivitaStoricoDyn2DatiRepository(this._schedeDinamicheAttivitaService, this._idAttivita, idVersioneStorico);
        }
    }
}
