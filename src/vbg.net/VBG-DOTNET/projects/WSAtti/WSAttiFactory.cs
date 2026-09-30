using System;
using WSAtti.Sicraweb;

namespace WSAtti
{
    public class WSAttiFactory
    {
        public WSAttiFactory()
        {

        }

        public IWSAttiService Build(WSAttiConfigurazione configurazione)
        {
            if (configurazione is null)
            {
                throw new ArgumentNullException(nameof(configurazione));
            }

            if (String.IsNullOrEmpty(configurazione.TipoConnettore))
            {
                throw new ArgumentNullException($"Non è stato configurato il connettore da utilizzare per integrarsi con il WS Atti");
            }

            switch (configurazione.TipoConnettore)
            {
                case "SICRAWEB":
                    {
                        return new SicraWebService(configurazione);
                    }
                default:
                    {
                        throw new ArgumentNullException($"Nessun connettore implementato per il ws atti di tipo {configurazione.TipoConnettore}");
                    }
            }
        }
    }
}
