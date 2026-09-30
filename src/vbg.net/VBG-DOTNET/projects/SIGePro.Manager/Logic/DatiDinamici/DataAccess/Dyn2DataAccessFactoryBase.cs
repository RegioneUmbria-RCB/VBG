using Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess.StrutturaModello;
using PersonalLib2.Data;
using VBG.DatiDinamici.GestioneLocalizzazioni;
using VBG.DatiDinamici.Interfaces;

namespace Init.SIGePro.Manager.Logic.DatiDinamici.DataAccess
{
    public abstract class Dyn2DataAccessFactoryBase : IDyn2DataAccessFactory
    {
        private readonly DataBase _database;
        private readonly string _idComune;

        public Dyn2DataAccessFactoryBase(DataBase database, string idComune)
        {
            this._database = database;
            this._idComune = idComune;
        }

        public IDyn2CampiManager GetCampiManager()
        {
            return new CampiManager(this._database, this._idComune);
        }

        public abstract IClasseContestoLoader GetClassLoader();

        public IDyn2DettagliModelloManager GetDettagliModelloManager()
        {
            return new ModelliDettaglioManager(this._database, this._idComune);
        }

        //public IDyn2QueryDatiDinamiciManager GetDyn2QueryDatiDinamiciManager()
        //{
        //    return new QuerySigepro(this._database);
        //}

        public IDyn2ModelliManager GetModelliManager()
        {
            return new ModelliManager(this._database, this._idComune);
        }

        public IDyn2ProprietaCampiManager GetProprietaCampiManager()
        {
            return new CampiProprietaManager(this._database, this._idComune);
        }

        public IDyn2ScriptCampiManager GetScriptCampiManager()
        {
            return new CampiScriptManager(this._database, this._idComune);
        }

        public IDyn2ScriptModelloManager GetScriptModelliManager()
        {
            return new ModelliScriptManager(this._database, this._idComune);
        }

        public IDyn2TestoModelloManager GetTestoModelloManager()
        {
            return new ModelliTestiManager(this._database, this._idComune);
        }

        public string GetToken()
        {
            return this._database.ConnectionDetails.Token;
        }

        public abstract IDyn2DatiRepository GetRepository();
        public abstract IDyn2DatiStoricoRepository GetStoricoRepository(int idVersioneStorico);
        public abstract IQueryLocalizzazioni GetQueryLocalizzazioni();

    }
}
