//using System;
//using System.Collections.Generic;
//using System.Linq;
//using System.Text;
//using Init.Sigepro.FrontEnd.AppLogic.ViewModels.CondizioniIngresso;
//using Init.Sigepro.FrontEnd.AppLogic.ViewModels.CondizioniUscita;

//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
//using Init.Sigepro.FrontEnd.AppLogic.Common;
//using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
//using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
//using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

//namespace Init.Sigepro.FrontEnd.AppLogic.ViewModels
//{
//    public class GestioneAnagraficheViewModel : ViewModelBase
//    {
//        protected override ICondizioneIngressoStep GetCondizioneIngresso()
//        {
//            return new CondizioneIngressoStepSempreVera();
//        }

//        protected override ICondizioneUscitaStep GetCondizioneUscita()
//        {
//            return _condizioniUscitaGestioneAnagrafiche;
//        }


//        CondizioniUscitaGestioneAnagrafiche _condizioniUscitaGestioneAnagrafiche;
//        AnagraficheService _anagraficheService;
//        IComuniRepository _comuniRepository;

//        public GestioneAnagraficheViewModel( IAliasSoftwareResolver aliasSoftwareResolver, 
//                                             IAuthenticationDataResolver authenticationDataResolver, 
//                                             IIdDomandaResolver idDomandaResolver, 
//                                             DomandeOnlineService persistenzaDatiDomandaService, 
//                                             AnagraficheService anagraficheService ,
//                                             IComuniRepository comuniRepository)
//            :base( aliasSoftwareResolver , authenticationDataResolver , idDomandaResolver , persistenzaDatiDomandaService)
//        {
//            _condizioniUscitaGestioneAnagrafiche = new CondizioniUscitaGestioneAnagrafiche(aliasSoftwareResolver, anagraficheService, authenticationDataResolver);
//            _anagraficheService = anagraficheService;
//            _comuniRepository = comuniRepository;
//        }

//        public void ImpostaFlagRichiedePec(bool richiedePec)
//        {
//            _condizioniUscitaGestioneAnagrafiche.FlagVerificaPecObbligatoria = richiedePec;
//        }

//        public void ImpostaMessaggioUtenteNonTrovato(string messaggio)
//        {
//            _condizioniUscitaGestioneAnagrafiche.MessaggioUtenteNonPresente = messaggio;
//        }

//        public IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> GetAnagraficheDomanda()
//        {
//            return _anagraficheService.GetAnagrafiche( IdDomanda );
//        }

//        public void CollegaAziendaAdAnagrafica(int idAnagrafica, string idAziendaCollegata)
//        {
//            _anagraficheService.CollegaAziendaAdAnagrafica(IdDomanda, idAnagrafica, idAziendaCollegata);
//        }

//        public void RimuoviAnagrafica(int idAnagrafica)
//        {
//            _anagraficheService.RimuoviAnagrafica(IdDomanda, idAnagrafica);
//        }

//        public bool VerificaSeTipoSoggettoRichiedeAnagraficaCollegata(int idTipoSoggetto)
//        {
//            return _anagraficheService.TipoSoggettoRichiedeAnagraficaCollegata(idTipoSoggetto);
//        }

//        public string GetNominativoAziendaCollegata(int idAnagrafica)
//        {
//            return _anagraficheService.GetNominativoAziendaCollegata(IdDomanda, idAnagrafica);
//        }

//        public IEnumerable<KeyValuePair<int, string>> GetAnagraficheCollegabili()
//        {
//            return _anagraficheService.GetAnagraficheCollegabili(IdDomanda);
//        }

//        public PresentazioneIstanzaDataSet.ANAGRAFERow TrovaAnagrafica(TipoPersona tipoPersona, string codiceFiscalePartitaIva)
//        {
//            return _anagraficheService.RicercaAnagrafica(IdDomanda, tipoPersona, codiceFiscalePartitaIva);
//        }

//        public IEnumerable<TipiSoggetto> GetTipiSoggettoPersonaFisicaBindingList()
//        {
//            return _anagraficheService.GetTipiSoggettoPersonaFisica();
//        }

//        public void SalvaAnagrafica(PresentazioneIstanzaDataSet.ANAGRAFERow row)
//        {
//            _anagraficheService.SalvaAnagrafica(IdDomanda, row);
//        }

//        public DatiProvinciaCompatto GetDatiProvincia(string siglaProvincia)
//        {
//            return _comuniRepository.GetDatiProvincia( AliasComune, siglaProvincia);
//        }

//        public DatiComuneCompatto GetDatiComune(string codiceComune)
//        {
//            return _comuniRepository.GetDatiComune(AliasComune, codiceComune);
//        }

//        public TipiSoggetto GetTipoSoggettoById(int idTipoSoggetto)
//        {
//            return _anagraficheService.GetTipoSoggettoById(idTipoSoggetto);
//        }

//        public PresentazioneIstanzaDataSet.ANAGRAFERow GetAnagrafeRow(int idAnagrafica)
//        {
//            var row = _anagraficheService.GetAnagraficaDaId(IdDomanda, idAnagrafica);

//            if (row != null)
//                return row;

			
//        }

//        public void SincronizzaFlagsTipiSoggetto()
//        {
//            _anagraficheService.SincronizzaFlagsTipiSoggetto(IdDomanda);
//        }
//    }
//}
