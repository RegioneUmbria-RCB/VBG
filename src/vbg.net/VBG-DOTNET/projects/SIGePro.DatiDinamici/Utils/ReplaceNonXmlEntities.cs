using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.DatiDinamici.Utils
{
    public class ReplaceNonXmlEntities
    {
        
        private static Dictionary<string, string> _tabellaDecodifiche = new Dictionary<string, string>
        {
           { "&rsquo;", "&#x2019;" },
           { "&agrave;", "&#224;" },
           { "&aacute;", "&#225;" },
           { "&egrave;", "&#232;" },
           { "&eacute;", "&#233;" },
           { "&ograve;", "&#242;" },
           { "&oacute;", "&#243;" },
           { "&ugrave;", "&#249;" },
           { "&uacute;", "&#250;" },
            { "&nbsp;", "&#160;"}
        };
        

        public static string Escape(string strIn)
        {
            if (String.IsNullOrEmpty(strIn))
            {
                return strIn;
            }

            _tabellaDecodifiche.Select(x => new
                                        {
                                            find = x.Key,
                                            replace = x.Value
                                        })
                                        .ToList()
                                        .ForEach(x => strIn = strIn.Replace(x.find, x.replace));

            return strIn;
        }
    }
}
