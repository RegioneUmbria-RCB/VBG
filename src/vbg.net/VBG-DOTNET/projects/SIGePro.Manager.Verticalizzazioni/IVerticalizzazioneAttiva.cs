using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SIGePro.Manager.Verticalizzazioni
{
    public interface IVerticalizzazioneAttiva<T> where T:Verticalizzazione
    {
        bool IsAttiva(string software);
    }
}
