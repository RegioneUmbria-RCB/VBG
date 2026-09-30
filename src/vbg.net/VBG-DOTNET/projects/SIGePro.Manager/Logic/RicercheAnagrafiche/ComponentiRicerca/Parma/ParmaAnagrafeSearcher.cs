using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using SIGePro.Manager.VerticalizzazioniBase;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Parma
{
    public class ParmaAnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        public ParmaAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory) : base(verticalizzazioniFactory, bindingFactory, "PARMA")
        {

        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            var url = this.Configuration["URLWS_BASE"];
            var username = this.Configuration["USERNAME"];
            var password = this.Configuration["PASSWORD"];

            var service = new AnagrafeServiceWrapper(username, password, url);
            var response = service.GetAnagrafica(codiceFiscale);

            var adapt = new AnagrafeResponseAdapter(this.SigeproDb);
            return adapt.Adatta(response);
        }

        public override Anagrafe ByCodiceFiscaleImp(TipoPersona tipoPersona, string codiceFiscale)
        {
            if (tipoPersona == TipoPersona.PersonaFisica)
            {
                return this.ByCodiceFiscaleImp(codiceFiscale);
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
            var url = this.Configuration["URLWS_BASE"];
            var username = this.Configuration["USERNAME"];
            var password = this.Configuration["PASSWORD"];

            var service = new AnagrafeServiceWrapper(username, password, url);
            var response = service.GetVariazioni(from, to);

            var adapt = new AnagrafeResponseAdapter(this.SigeproDb);
            return response.Select(x => adapt.Adatta(x));

        }
    }
}
