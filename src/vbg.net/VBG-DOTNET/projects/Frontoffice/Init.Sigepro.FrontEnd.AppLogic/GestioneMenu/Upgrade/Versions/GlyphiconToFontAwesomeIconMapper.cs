using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions
{
    public class GlyphiconToFontAwesomeIconMapper : IGlyphiconToFontAwesomeIconMapper
    {
        private static readonly Dictionary<string, string> relations = new Dictionary<string, string>
        {
            {"glyphicon-open-file"  ,   "fa-upload"         },
            {"glyphicon-time"       ,   "fa-clock-o"        },
            {"glyphicon-folder-open",   "fa-folder-open-o"  },
            {"glyphicon-calendar"   ,   "fa-calendar"       },
            {"glyphicon-search"     ,   "fa-search"         },
            {"glyphicon-star"       ,   "fa-star"           },
            {"glyphicon-off"        ,   "fa-power-off"      },
            {"glyphicon-book"       ,   "fa-book"           }
        };

        public string TryMapIcon(string icon)
        {
            if (relations.TryGetValue(icon.ToLower(), out string newIcon))
            {
                return newIcon;
            }

            return null;
        }
    }
}
