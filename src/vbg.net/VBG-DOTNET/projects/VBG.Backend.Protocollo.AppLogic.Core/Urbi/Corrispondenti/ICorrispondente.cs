using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti
{
    public interface ICorrispondente
    {
        xapirestTypeCorrispondenti SearchCorrispondente();
        xapirestTypeInsertCorrispondenti InsertCorrispondente();
    }
}
