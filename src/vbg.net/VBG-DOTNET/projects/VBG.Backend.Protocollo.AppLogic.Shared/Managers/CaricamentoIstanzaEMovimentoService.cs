using Init.SIGePro.Data;
using Init.SIGePro.Manager;
using PersonalLib2.Data;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Managers
{



    public class CaricamentoIstanzaEMovimentoService
    {
        public class CaricamentoIstanzaResult
        {
            public Istanze Istanza { get; }
            public string Software => this.Istanza.SOFTWARE ?? throw new InvalidOperationException($"L'istanza con codiceIstanza {this.Istanza.CODICEISTANZA} è nulla o non ha un software impostato");
            public string CodiceComune => this.Istanza.CODICECOMUNE ?? throw new InvalidOperationException($"L'istanza con codiceIstanza {this.Istanza.CODICEISTANZA} è nulla o non ha un codice comune impostato");
            public string IdComune => this.Istanza.IDCOMUNE ?? throw new InvalidOperationException($"L'istanza con codiceIstanza {this.Istanza.CODICEISTANZA} è nulla o non ha un IdComune impostato");

            public CaricamentoIstanzaResult(Istanze istanza)
            {
                this.Istanza = istanza ?? throw new ArgumentException("Istanza non può essere null", nameof(istanza));
            }
        }

        public class CaricamentoMovimentoResult : CaricamentoIstanzaResult
        {
            public Movimenti Movimento { get; }

            public CaricamentoMovimentoResult(Movimenti movimento, Istanze istanza) : base(istanza)
            {
                this.Movimento = movimento;
            }
        }


        private readonly DataBase _db;
        private readonly string _idComune;

        public CaricamentoIstanzaEMovimentoService(DataBase db, string idComune)
        {
            this._db = db;
            this._idComune = idComune;
        }

        public CaricamentoIstanzaResult GetIstanzaById(int? codiceIstanza)
        {
            if (!codiceIstanza.HasValue)
            {
                throw new ArgumentException("CodiceIstanza non può essere null", nameof(codiceIstanza));
            }

            var mgr = new IstanzeMgr(this._db);
            var istanza = mgr.GetById(this._idComune, codiceIstanza.Value, PersonalLib2.Sql.useForeignEnum.Yes);

            return new CaricamentoIstanzaResult(istanza);
        }

        public CaricamentoIstanzaResult GetIstanzaById(string codiceIstanza)
        {
            if (string.IsNullOrEmpty(codiceIstanza))
            {
                throw new ArgumentException("Codice istanza non impostato", nameof(codiceIstanza));
            }

            return this.GetIstanzaById(Convert.ToInt32(codiceIstanza));
        }

        public CaricamentoMovimentoResult GetMovimentoEIstanzaDaIdMovimento(string codiceMovimento)
        {
            if (string.IsNullOrEmpty(codiceMovimento))
            {
                throw new ArgumentException("CodiceMovimento non impostato", nameof(codiceMovimento));
            }

            return this.GetMovimentoEIstanzaDaIdMovimento(Convert.ToInt32(codiceMovimento));
        }

        public CaricamentoMovimentoResult GetMovimentoEIstanzaDaIdMovimento(int? codiceMovimento)
        {
            if (!codiceMovimento.HasValue)
            {
                throw new ArgumentException("CodiceMovimento non impostato", nameof(codiceMovimento));
            }

            var mgr = new MovimentiMgr(this._db);
            var movimento = mgr.GetById(this._idComune, codiceMovimento.Value);

            if (movimento == null)
            {
                throw new InvalidOperationException($"Il movimento con codicemovimento {codiceMovimento} non è stato trovato");
            }

            if (String.IsNullOrEmpty(movimento.CODICEISTANZA))
            {
                throw new InvalidOperationException($"Il movimento con codicemovimento {codiceMovimento} non ha un codice istanza");
            }

            var istanza = this.GetIstanzaById(Convert.ToInt32(movimento.CODICEISTANZA));

            return new CaricamentoMovimentoResult(movimento, istanza.Istanza);
        }
    }
}
