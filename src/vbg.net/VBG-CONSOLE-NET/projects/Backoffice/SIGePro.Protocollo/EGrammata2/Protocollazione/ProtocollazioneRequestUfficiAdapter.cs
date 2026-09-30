using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.Protocollazione.Segnatura.Request;

namespace Init.SIGePro.Protocollo.EGrammata2.Protocollazione
{
    public class ProtocollazioneRequestUfficiAdapter
    {
        public static UO Adatta(string uo)
        {
            var uoArr = uo.Split('-');

            if (uoArr.Length == 5)
            {
                return new UO
                {
                    ItemsElementName = new ItemsChoiceType3[] { ItemsChoiceType3.SettIn, ItemsChoiceType3.ServIn, ItemsChoiceType3.UOCIn, ItemsChoiceType3.UOSIn, ItemsChoiceType3.PostIn },
                    Items = new string[] { uoArr[0], uoArr[1], uoArr[2], uoArr[3], uoArr[4] }
                };
            }
            else
            {
                return new UO
                {
                    ItemsElementName = new ItemsChoiceType3[] { ItemsChoiceType3.IdUo },
                    Items = new string[] { uo }
                };
            }        
        }
    }
}
