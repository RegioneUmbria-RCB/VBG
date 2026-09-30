using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Logs;

namespace Init.SIGePro.Protocollo.Halley.Builders
{
    public class HalleySegnaturaPersoneBuilder
    {
        IDatiProtocollo _protoIn;

        public HalleySegnaturaPersoneBuilder(IDatiProtocollo protoIn)
        {
            _protoIn = protoIn;
        }

        public Mittente[] GetMittenti()
        {
            if (_protoIn.AnagraficheProtocollo.Count == 0 && _protoIn.AmministrazioniEsterne.Count == 0)
                throw new Exception("NON SONO PRESENTI MITTENTI");

            var mittente = new Mittente();

            if (_protoIn.AnagraficheProtocollo.Count > 0)
                mittente = _protoIn.AnagraficheProtocollo[0].ToMittenteFromAnagrafica();

            if (mittente.Items == null || mittente.Items.Count() == 0)
                mittente = _protoIn.AmministrazioniEsterne[0].ToMittenteFromAmministrazione();

            return new Mittente[] { mittente };
        }

        public Mittente[] GetMittentiMulti()
        {
            var mittentiAnagrafiche = _protoIn.AnagraficheProtocollo.Select(x => x.ToMittenteFromAnagrafica());
            var mittentiAmministrazioni = _protoIn.AmministrazioniEsterne.Select(x => x.ToMittenteFromAmministrazione());

            var mittenti = mittentiAnagrafiche.Union(mittentiAmministrazioni);

            return mittenti.ToArray();
        }

        public Destinatario[] GetDestinatari()
        {
            if (_protoIn.AnagraficheProtocollo.Count == 0 && _protoIn.AmministrazioniEsterne.Count == 0)
                throw new Exception("NON SONO PRESENTI DESTINATARI");

            var destinatari = new Destinatario();

            if(_protoIn.AnagraficheProtocollo.Count() > 0)
                destinatari = _protoIn.AnagraficheProtocollo[0].ToDestinatarioFromAnagrafica();

            if (destinatari.Items == null || destinatari.Items.Count() == 0)
                destinatari = _protoIn.AmministrazioniEsterne[0].ToDestinatarioFromAmministrazione();
                        
            return new Destinatario[] { destinatari };
        }

        public Destinatario[] GetDestinatariMulti()
        {
            var destinatariAnagrafiche = _protoIn.AnagraficheProtocollo.Select(x => x.ToDestinatarioFromAnagrafica());
            var destinatariAmministrazioni = _protoIn.AmministrazioniEsterne.Select(x => x.ToDestinatarioFromAmministrazione());

            var mittenti = destinatariAnagrafiche.Union(destinatariAmministrazioni);

            return mittenti.ToArray();
        }
    }
}
