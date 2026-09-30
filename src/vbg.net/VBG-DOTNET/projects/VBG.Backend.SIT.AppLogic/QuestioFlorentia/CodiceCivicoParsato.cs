using System;
using System.Diagnostics;

namespace VBG.Backend.SIT.AppLogic.QuestioFlorentia
{
    public class CodiceCivicoParsato
    {
        public readonly string CodComune;
        public readonly string CodVia;
        public readonly string Civico;
        public readonly string Esponente;
        public readonly string Colore;
        public readonly string IdentificativoUnivocoCivico;

        internal static CodiceCivicoParsato DaCodiceCivicoEDescrizione(string codiceEDescrizioneCivico)
        {
            return new CodiceCivicoParsato(codiceEDescrizioneCivico.Split('@')[0]);
        }

        internal static CodiceCivicoParsato DaCodiceCivico(string codiceCivico)
        {
            return new CodiceCivicoParsato(codiceCivico);
        }

        protected CodiceCivicoParsato(string stringaSit)
        {
            Debug.WriteLine(stringaSit);

            this.IdentificativoUnivocoCivico = stringaSit.Substring(0, 28);

            this.CodComune = stringaSit.Substring(0, 4);
            this.CodVia = stringaSit.Substring(0, 12);
            this.Civico = stringaSit.Substring(12, 4).TrimStart('0');
            this.Esponente = stringaSit.Substring(16, 3).TrimStart('0');

            var tmpBarrato = stringaSit.Substring(19, 3).TrimStart('0');

            if (!String.IsNullOrEmpty(tmpBarrato))
            {
                this.Esponente = this.Esponente + "/" + tmpBarrato;
            }

            this.Colore = stringaSit.Substring(27, 1);
        }



        public override string ToString()
        {
            return this.IdentificativoUnivocoCivico;
        }
    }
}
