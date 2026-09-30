using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;

namespace VBG.Backend.Protocollo.AppLogic.Core.Urbi.Corrispondenti
{
    public class CorrispondenteFactory
    {
        public static ICorrispondente Create(IAnagraficaAmministrazione mittDest, CorrispondentiServiceWrapper wrapper)
        {
            if (mittDest.Tipo == "F")
                return new CorrispondenteFisico(wrapper, mittDest);
            else if (mittDest.Tipo == "G")
                return new CorrispondenteGiuridico(wrapper, mittDest);
            else
                throw new Exception(String.Format("TIPO ANAGRAFE {0} NON SUPPORTATO, PREVISTO 'F' PER PERSONA FISICA E 'G' PER PERSONA GIURIDICA"));
        }
    }
}
