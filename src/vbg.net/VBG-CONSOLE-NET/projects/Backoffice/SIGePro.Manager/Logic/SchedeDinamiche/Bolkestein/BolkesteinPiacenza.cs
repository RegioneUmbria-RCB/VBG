using Init.SIGePro.Authentication;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.SchedeDinamiche.Bolkestein
{
    public class BolkesteinPiacenza
    {

        private string _campoAdeguamentoContStorico;
        private int _codiceIstanza;
        private string _idMercato;
        private string _idUso;
        private string _idPosteggio;


        public BolkestainResponse CalcolaPunteggi(AuthenticationInfo authInfo)
        {

            var req = new BolkestainRequest();
            req.adeguamentoAlContestoStorico = CalcolaAdeguamentoContestoStorico();
            req.codiceIstanza = _codiceIstanza;
            req.datiCommercioAreaPubblica = ElencoSoggetti();
            req.idMercato = _idMercato;
            req.idPosteggio = _idUso;
            req.idUso = _idPosteggio;

            var bs = new BolkesteinService();

            return bs.CalcolaPunteggiPosteggio(authInfo, req);
        }

        protected bool CalcolaAdeguamentoContestoStorico() {
            return false;
        }

        protected List<DatiCommercioAreaPubblica> ElencoSoggetti()
        {
            return null;
        }
    }
}
