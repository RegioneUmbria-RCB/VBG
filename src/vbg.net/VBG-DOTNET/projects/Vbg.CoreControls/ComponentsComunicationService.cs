using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Vbg.CoreControls
{
    public class ComponentsComunicationService
    {
        public event Action RefreshRequested;
        public void CallRequestRefresh()
        {
            RefreshRequested?.Invoke();
        }

        public event Action<object> PassData;

        public void CallPassData(object o)
        {
            if (PassData != null)
                PassData.Invoke(o);
        }
    }
}
