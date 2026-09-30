using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Microsis.Protocollazione.TipoPersona
{
    public class TipoPersonaFactory
    {
        public TipoPersonaFactory()
        {

        }

        public ITipoPersona CreateFactory(IAnagraficaAmministrazione anagrafe)
        {
            if (anagrafe.Tipo == "F")
                return new PersonaFisica(anagrafe);
            else if (anagrafe.Tipo == "G")
                return new PersonaGiuridica(anagrafe);
            else
                throw new Exception(String.Format("TIPO PERSONA {0} NON GESTITO", anagrafe.Tipo));
        }

        public void Valida(ITipoPersona tipoPersonaAttribute)
        {
            var t = tipoPersonaAttribute.GetType();
            var propInfo = t.GetProperties();

            foreach (var info in t.GetProperties())
            {
                var attrs = info.GetCustomAttributes(false);

                foreach (var attr in attrs)
                {
                    if (attr is TipoPersonaValidationAttribute)
                    {
                        var testAttr = (TipoPersonaValidationAttribute)attr;
                        var value = info.GetValue(tipoPersonaAttribute, null) != null ? info.GetValue(tipoPersonaAttribute, null) : "";
                        string msg = testAttr.Valida(value.ToString());
                        if (!String.IsNullOrEmpty(msg))
                            throw new Exception(String.Format("ERRORE DI VALIDAZIONE DEI DATI: {0}", msg));
                    }
                }
            }
        }

    }
}
