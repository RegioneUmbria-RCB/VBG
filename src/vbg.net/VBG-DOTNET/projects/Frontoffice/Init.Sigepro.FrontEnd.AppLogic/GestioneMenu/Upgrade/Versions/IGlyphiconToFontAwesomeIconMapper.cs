using System;
using System.Collections.Generic;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneMenu.Upgrade.Versions
{
    public interface IGlyphiconToFontAwesomeIconMapper
    {
        string TryMapIcon(string icon);
    }
}
