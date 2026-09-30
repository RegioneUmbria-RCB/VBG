using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ClassificazioneAdapter
    {
        string _classifica;

        string _titolo = "0";
        string _classe = "0";
        string _sottoClasse = "0";
        string _livello4 = "0";
        string _livello5 = "0";

        public ClassificazioneAdapter(string classifica)
        {
            _classifica = classifica;
            AdattaLivelliClassifica();
        }

        private void AdattaLivelliClassifica()
        {
            var livelliClassifica = _classifica.Split('.');

            if (livelliClassifica.Length == 1)
                _titolo = livelliClassifica[0];

            if (livelliClassifica.Length == 2)
            {
                _titolo = livelliClassifica[0];
                _classe = livelliClassifica[1];
            }

            if (livelliClassifica.Length == 3)
            {
                _titolo = livelliClassifica[0];
                _classe = livelliClassifica[1];
                _sottoClasse = livelliClassifica[2];
            }

            if (livelliClassifica.Length == 4)
            {
                _titolo = livelliClassifica[0];
                _classe = livelliClassifica[1];
                _sottoClasse = livelliClassifica[2];
                _livello4 = livelliClassifica[3];
            }

            if (livelliClassifica.Length > 4)
            {
                _titolo = livelliClassifica[0];
                _classe = livelliClassifica[1];
                _sottoClasse = livelliClassifica[2];
                _livello4 = livelliClassifica[3];
                _livello5 = livelliClassifica[4];
            }
        }

        public Classificazione Adatta()
        {
            return new Classificazione
            {
                ItemsElementName = new ItemsChoiceType[] { ItemsChoiceType.Titolo, ItemsChoiceType.Classe, ItemsChoiceType.Sottoclasse, ItemsChoiceType.Liv4, ItemsChoiceType.Liv5 },
                Items = new string[] { _titolo, _classe, _sottoClasse, _livello4, _livello5 }
            };

        }
    }
}
