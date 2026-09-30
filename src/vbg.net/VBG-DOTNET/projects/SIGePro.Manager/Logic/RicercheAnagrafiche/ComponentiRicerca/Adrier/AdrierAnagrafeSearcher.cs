using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Logic.RicercheAnagrafiche.Adrier;
using SIGePro.Manager.VerticalizzazioniBase;
using System.Collections.Generic;

namespace Init.SIGePro.Manager.Logic.RicercheAnagrafiche.ComponentiRicerca.Adrier
{
    public class AdrierAnagrafeSearcher : AnagrafeSearcherAdrierBase
    {
        private readonly Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher _searcherSigepro;

        public AdrierAnagrafeSearcher(IVerticalizzazioniFactory verticalizzazioniFactory, IBindingFactory bindingFactory) : base(verticalizzazioniFactory, bindingFactory, "ADRIER")
        {
            this._searcherSigepro = new Init.SIGePro.Manager.Logic.RicercheAnagrafiche.AnagrafeSearcher(verticalizzazioniFactory, "ADRIER");
        }

        public override void Init()
        {
            this._searcherSigepro.InitParams(this.IdComune, this.Alias, this.SigeproDb);
        }

        public override Anagrafe ByCodiceFiscaleImp(string codiceFiscale)
        {
            return this._searcherSigepro.ByCodiceFiscaleImp(TipoPersona.PersonaFisica, codiceFiscale);
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
            return this._searcherSigepro.ByNomeCognomeImp(nome, cognome);
        }
    }
}
