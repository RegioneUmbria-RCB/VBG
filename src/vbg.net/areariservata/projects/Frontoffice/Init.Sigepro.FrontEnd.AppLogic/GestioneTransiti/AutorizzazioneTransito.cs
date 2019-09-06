using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.AutorizzazioniTransitiService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneTransiti
{
    public class AutorizzazioneTransito
    {
        public enum TipoOperazione
        {
            NonDefinita,
            Preavviso,
            Proroga,
            Rinnovo,
            Modifica
        }

        public class RiferimentoPratica
        {
            public string Numero { get; }
            public DateTime Data { get; }

            public RiferimentoPratica(string numero, DateTime data)
            {
                this.Numero = numero;
                this.Data = data;
            }

            public override string ToString()
            {
                return $"N.{this.Numero} del {this.Data.ToString("dd/MM/yyyy")}";
            }
        }

        public class OperazioneTransito
        {
            private readonly OperazioneAutorizzazioneType _op;
            public int CodiceIstanza => Convert.ToInt32(this._op.codiceIstanza);
            public RiferimentoPratica Istanza => new RiferimentoPratica(this._op.numeroIstanza, this._op.dataIstanza);
            public RiferimentoPratica Protocollo => new RiferimentoPratica(this._op.numeroProtocollo, this._op.dataProtocollo);

            public string Tipo => this._op.tipo;
            public string Stato => this._op.stato;
            public DateTime DataAutorizzazione => this._op.dataAutorizzazione;

            internal OperazioneTransito(OperazioneAutorizzazioneType op)
            {
                this._op = op;
            }
        }

        public class FlagsAutorizzazione
        {
            private OperazioniPermesseType _operazioniPermesse;

            public bool Preavviso => this._operazioniPermesse.preavviso;
            public bool Proroga => this._operazioniPermesse.proroga;
            public bool Rinnovo => this._operazioniPermesse.rinnovo;

            public FlagsAutorizzazione(OperazioniPermesseType operazioniPermesse)
            {
                this._operazioniPermesse = operazioniPermesse;
            }
        }


        public int Id { get; }
        public RiferimentoPratica Riferimenti { get; }
        public DateTime? DataValidita { get; }
        public DateTime? DataScadenza { get; }
        public int CodiceIstanza { get;  }
        public int TransitiRimanenti { get; }
        public IEnumerable<OperazioneTransito> Operazioni { get; }
        public FlagsAutorizzazione OperazioniPermesse { get; }
        public int TransitiConsentiti { get;  }

        internal AutorizzazioneTransito(RicercaAutorizzazioniAccessiResponseType autorizzazioneWs)
        {
            var aut = autorizzazioneWs.autorizzazione;

            this.Id = Convert.ToInt32(aut.id);
            this.Riferimenti = new RiferimentoPratica(aut.numero, aut.data);
            this.DataValidita = aut.dataValiditaSpecified ? aut.dataValidita : (DateTime?)null;
            this.DataScadenza = aut.dataScadenzaSpecified ? aut.dataScadenza : (DateTime?)null;
            this.CodiceIstanza = Convert.ToInt32(aut.codiceIstanza);
            this.TransitiRimanenti = Convert.ToInt32(autorizzazioneWs.numeroTransitiRimanenti);
            this.TransitiConsentiti = String.IsNullOrEmpty(autorizzazioneWs.numeroTransitiConsentiti) ? -1 : Convert.ToInt32(autorizzazioneWs.numeroTransitiConsentiti);

            this.Operazioni = autorizzazioneWs.operazioni.Select(x => new OperazioneTransito(x));
            this.OperazioniPermesse = new FlagsAutorizzazione(autorizzazioneWs.operazioniPermesse);
        }

        public bool PermetteOperazione(TipoOperazione tipoOperazione)
        {
            if (tipoOperazione == TipoOperazione.Modifica)
            {
                return true;
            }

            return (tipoOperazione == TipoOperazione.Preavviso && this.OperazioniPermesse.Preavviso) ||
                    (tipoOperazione == TipoOperazione.Proroga && this.OperazioniPermesse.Proroga) ||
                    (tipoOperazione == TipoOperazione.Rinnovo && this.OperazioniPermesse.Rinnovo);
        }
    }
}
