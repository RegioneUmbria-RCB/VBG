using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.PaDoc.LeggiProtocollo
{
    public static class LeggiProtocolloExtensions
    {

        public static string GetValoreMittente(this rispostaRisultatoMittente cls, ItemsChoiceType chiave)
        {
            var indice = cls.GetIndiceDellaChiaveMittente(chiave);

            if (indice == -1)
                return "";

            return cls.Items[indice];
        }

        private static int GetIndiceDellaChiaveMittente(this rispostaRisultatoMittente cls, ItemsChoiceType chiave)
        {
            for (int i = 0; i < cls.ItemsElementName.Count(); i++)
			{
                if (cls.ItemsElementName[i] == chiave)
                {
                    return i;
                }
			}

            return -1;
        }

        public static string GetValoreDestinatario(this rispostaRisultatoDestinatario cls, ItemsChoiceType1 chiave)
        {
            var indice = cls.GetIndiceDellaChiaveDestinatario(chiave);

            if (indice == -1)
                return "";

            return cls.Items[indice];
        }

        private static int GetIndiceDellaChiaveDestinatario(this rispostaRisultatoDestinatario cls, ItemsChoiceType1 chiave)
        {
            for (int i = 0; i < cls.ItemsElementName.Count(); i++)
            {
                if (cls.ItemsElementName[i] == chiave)
                {
                    return i;
                }
            }

            return -1;
        }
    }
}
