using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Manager.Logic.GestioneEndoprocedimenti
{
    internal class TerminiRicerca
    {
        public readonly string[] Parti;
        private readonly string Separatore;
        public readonly string PrefissoParametro;

        public TerminiRicerca(TipoRicercaEnum tipoRicerca, string partial, string prefissoParametro)
        {
            var dict = new Dictionary<TipoRicercaEnum, string>{
                { TipoRicercaEnum.AlmenoUnaParola, " OR " },
                { TipoRicercaEnum.TutteLeParole, " AND " },
                { TipoRicercaEnum.FraseCompleta, "" }
            };

            this.Parti = partial.Split(' ').Where(x => x.Length > 0).ToArray();
            this.Separatore = dict[tipoRicerca];
            this.PrefissoParametro = prefissoParametro;

            if (tipoRicerca == TipoRicercaEnum.FraseCompleta)
                this.Parti = new string[] { partial };
        }

        internal string ToSql(string campoRicerca, Func<string, string> paramTransform)
        {
            var sb = new StringBuilder();

            sb.Append(" AND (");

            sb.Append(String.Join(this.Separatore, this.Parti
                                                            .Select((valoreParametro, indice) => String.Format("{0} like {1}", campoRicerca, paramTransform(this.PrefissoParametro + indice)))
                                                            .ToArray()));
            sb.Append(")");

            return sb.ToString();
        }
    }
}
