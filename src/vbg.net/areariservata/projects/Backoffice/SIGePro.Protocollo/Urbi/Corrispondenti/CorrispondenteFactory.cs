using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.Corrispondenti
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
