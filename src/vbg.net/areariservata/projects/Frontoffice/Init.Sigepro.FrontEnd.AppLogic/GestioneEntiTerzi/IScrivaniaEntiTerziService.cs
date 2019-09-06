using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneEntiTerzi
{
    public interface IScrivaniaEntiTerziService
    {
        bool UtentePuoAccedere(ETCodiceAnagrafe codiceAnagrafe);
        bool ModuloAttivo(string software);
        IEnumerable<ETPratica> GetPraticheDiCompetenza(ETCodiceAnagrafe codiceAnagrafe, ETFiltriRicerca filtri);
        IEnumerable<ETSoftwareConPratiche> GetListaSoftwareConPratiche(ETCodiceAnagrafe codiceAnagrafe);
        ETAmministrazioneCollegata GetDatiAmministrazioneCollegata(ETCodiceAnagrafe codiceAnagrafe);
        bool PraticaElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        void MarcaPraticaComeElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        void MarcaPraticaComeNonElaborata(ETCodiceIstanza codiceIStanza, ETCodiceAnagrafe codiceAnagrafe);
        bool PuoEffettuareMovimenti(ETCodiceAnagrafe codiceAnagrafe);
    }
}
