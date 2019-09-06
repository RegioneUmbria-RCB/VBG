using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public interface IScrivaniaEntiTerziWsProxy
    {
        ETAmministrazioneCollegata GetAmministrazioneCollegataAdAnagrafica(ETCodiceAnagrafe codiceAnagrafe);
        IEnumerable<ETPratica> GetListaPratiche(ETCodiceAnagrafe codiceAnagrafe, ETFiltriRicerca filtri);
        IEnumerable<ETSoftwareConPratiche> GetListaSoftwareConPratiche(int value);
        bool PraticaElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        void MarcaPraticaComeElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        void MarcaPraticaComeNonElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        bool PuoEffettuareMovimenti(ETCodiceAnagrafe codiceAnagrafe);
    }
}
