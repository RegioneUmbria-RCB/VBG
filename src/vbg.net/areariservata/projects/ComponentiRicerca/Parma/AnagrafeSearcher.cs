using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Parma
{
    public class AnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        public AnagrafeSearcher() : base("PARMA")
        {

        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            var url = Configuration["URLWS_BASE"];
            var username = Configuration["USERNAME"];
            var password = Configuration["PASSWORD"];

            var service = new AnagrafeServiceWrapper(username, password, url);
            var response = service.GetAnagrafica(codiceFiscale);

            var adapt = new AnagrafeResponseAdapter(this.SigeproDb);
            return adapt.Adatta(response);
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return ByCodiceFiscaleImp(codiceFiscale);
            }
            else
            {
                //Persona giuridica
                return base.ByPartitaIvaImp(codiceFiscale);
            }
        }

        public override List<Anagrafe> ByNomeCognomeImp(string nome, string cognome)
        {
            throw new NotImplementedException();
        }

        public override IEnumerable<Anagrafe> GetVariazioni(DateTime from, DateTime to)
        {
            var url = Configuration["URLWS_BASE"];
            var username = Configuration["USERNAME"];
            var password = Configuration["PASSWORD"];

            var service = new AnagrafeServiceWrapper(username, password, url);
            var response = service.GetVariazioni(from, to);

            var adapt = new AnagrafeResponseAdapter(this.SigeproDb);
            return response.Select(x => adapt.Adatta(x));

        }
    }
}
