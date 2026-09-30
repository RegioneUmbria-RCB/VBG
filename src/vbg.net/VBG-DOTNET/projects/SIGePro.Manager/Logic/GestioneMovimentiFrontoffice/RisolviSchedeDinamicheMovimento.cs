using Init.SIGePro.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneMovimentiFrontoffice
{
    public class RisolviSchedeDinamicheMovimento : IRisolviSchedeDinamicheMovimento
    {
        ISchedeDinamicheMovimentoSource _source;
        TipiMovimento _tipoMovimento;

        public RisolviSchedeDinamicheMovimento(ISchedeDinamicheMovimentoSource source, TipiMovimento tipoMovimento)
        {
            this._source = source;
            this._tipoMovimento = tipoMovimento;
        }

        public IEnumerable<Data.Dyn2ModelliT> GetSchedeDelMovimento(int codiceMovimento)
        {
            if (this._tipoMovimento.FlagPubblicaSchede.GetValueOrDefault(0) == 0)
            {
                return this._source.GetSchedeDinamicheDaMovimentoPrecedente(codiceMovimento);
            }

            return this._source.GetSchedeDinamicheDaTipoMovimento(this._tipoMovimento.Tipomovimento);
        }
    }
}
