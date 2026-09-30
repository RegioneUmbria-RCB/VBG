using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public interface ISchedeDinamicheMovimentoSource
    {
        IEnumerable<Dyn2ModelliT> GetSchedeDinamicheDaMovimentoPrecedente(int idMovimento);
        IEnumerable<Dyn2ModelliT> GetSchedeDinamicheDaTipoMovimento(string idTipomovimento);
    }

    public class SchedeDinamicheMovimentoSource : ISchedeDinamicheMovimentoSource
    {
        MovimentiDyn2ModelliTMgr _dyn2ModelliTMgr;
        TipiMovimentoMgr _tipiMovimentoMgr;
        string _idcComune;

        public SchedeDinamicheMovimentoSource(MovimentiDyn2ModelliTMgr dyn2ModelliTMgr, TipiMovimentoMgr tipiMovimentoMgr, string idComune)
        {
            this._dyn2ModelliTMgr = dyn2ModelliTMgr;
            this._tipiMovimentoMgr = tipiMovimentoMgr;
            this._idcComune = idComune;
        }

        public IEnumerable<Dyn2ModelliT> GetSchedeDinamicheDaMovimentoPrecedente(int idMovimento)
        {
            return this._dyn2ModelliTMgr.GetSchedeDelMovimento(this._idcComune, idMovimento);
        }

        public IEnumerable<Dyn2ModelliT> GetSchedeDinamicheDaTipoMovimento(string idTipomovimento)
        {
            return this._tipiMovimentoMgr.GetSchedeDinamicheConfigurate(this._idcComune, idTipomovimento);
        }
    }

}
