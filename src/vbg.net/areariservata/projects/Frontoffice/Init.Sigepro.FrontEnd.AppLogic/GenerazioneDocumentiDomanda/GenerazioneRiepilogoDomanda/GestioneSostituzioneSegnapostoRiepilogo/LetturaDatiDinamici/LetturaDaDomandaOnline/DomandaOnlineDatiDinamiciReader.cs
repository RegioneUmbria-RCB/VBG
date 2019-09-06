using Init.Sigepro.FrontEnd.AppLogic.Adapters;
using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TokenApplicazione;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.DataAccess;
using Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.StrutturaModelli;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.SIGePro.DatiDinamici.Interfaces;
using Init.SIGePro.DatiDinamici.Interfaces.Istanze;
using Init.SIGePro.DatiDinamici.VisibilitaCampi;
using Init.SIGePro.DatiDinamici.WebControls.MaschereCampiNonVisibili;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaDomandaOnline
{
    public class DomandaOnlineDatiDinamiciReader : IDatiDinamiciRiepilogoReader
    {
        DomandaOnline _domanda;
        IDatiDinamiciRepository _datiDinamiciRepository;


        public bool PuoCaricareSchedeNonPresenti => false;

        public DomandaOnlineDatiDinamiciReader(DomandaOnline domanda, IDatiDinamiciRepository datiDinamiciRepository)
        {
            this._domanda = domanda;
            this._datiDinamiciRepository = datiDinamiciRepository;
        }

        public IDyn2DataAccessProvider CreateDataAccessProvider(int idModello, ITokenApplicazioneService tokenApplicazioneService)
        {
            return new Dyn2DataAccessProvider(this._domanda, idModello, tokenApplicazioneService);
            //return new Dyn2DataAccessProvider(new Dyn2DataAccessProviderImpl(this._domanda, idModello), CreateIstanzeManager(), tokenApplicazioneService);
        }

        public IDyn2DataAccessProvider CreateDataAccessProviderStampaMolteplicita(int idScheda, int indiceMolteplicita, ITokenApplicazioneService tokenApplicazioneService)
        {
            return new Dyn2DataAccessProviderStampaMolteplicita(this._domanda, idScheda, indiceMolteplicita, tokenApplicazioneService);
        }
        /*
        public IIstanzeManager CreateIstanzeManager()
        {
            return new Dyn2IstanzeManager(new IstanzaSigeproAdapter(this._domanda.ReadInterface));
        }
        */
        public CampiNonVisibili GetCampiNonVisibili(int idModello)
        {
            return new CampiNonVisibili(this._domanda.ReadInterface.DatiDinamici.GetCampiNonVisibili(idModello));
        }

        public IValoreDatoDinamicoRiepilogo GetCampoDinamico(int idCampoDinamico, int indiceMolteplicita = 0)
        {
            return this._domanda.ReadInterface
                                .DatiDinamici
                                .DatiDinamici
                                .Where(x => x.IdCampo == idCampoDinamico && x.IndiceMolteplicita == indiceMolteplicita)
                                .FirstOrDefault();
        }

        public int GetCodiceIstanza()
        {
            return -1;
        }

        public string GetIdComune()
        {
            return this._domanda.DataKey.IdComune;
        }

        public IEnumerable<int> GetIndiciSchede(int idModello, IStrutturaModelloReader strutturaReader)
        {
            return this._domanda.ReadInterface.DatiDinamici.GetIndiciSchede(strutturaReader.Read(idModello));
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelli()
        {
            return this._domanda
                        .ReadInterface
                        .DatiDinamici
                        .Modelli
                        //.Where(m => m.Compilato)
                        .Select(x => x.EstraiOrdine(this._domanda.ReadInterface.DatiDinamici))
                        .OrderBy(m => m.Ordine)
                        .Select(m => m.Modello).ToList();
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliEndo(int idEndo)
        {
            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(-1, new[] { idEndo }, Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchedeOrdinate = modelli.SchedeEndoprocedimenti.OrderBy(x => x.Ordine).Select(x => x.Id);

            return GetListaModelli().Where(x => idSchedeOrdinate.Contains(x.IdModello));

            /*
            foreach (var idScheda in idSchedeOrdinate)
            {
                yield return GetListaModelli().Where(x => x.IdModello == idScheda).First();
            }
            */
        }

        public IEnumerable<IModelloDinamicoRiepilogo> GetListaModelliIntervento()
        {
            var idIntervento = this._domanda.ReadInterface.AltriDati.Intervento.Codice;

            var modelli = this._datiDinamiciRepository.GetSchedeDaInterventoEEndo(idIntervento, Enumerable.Empty<int>(), Enumerable.Empty<string>(), UsaTipiLocalizzazioniPerSelezionareSchedeDinamiche.No);

            var idSchedeOrdinate = modelli.SchedeIntervento.OrderBy(x => x.Ordine).Select(x => x.Id);

            foreach(var idScheda in idSchedeOrdinate)
            {
                yield return GetListaModelli().Where(x => x.IdModello == idScheda).First();
            }
        }

        public IModelloDinamicoRiepilogo CaricaSchedaNonPresenteDaId(int idScheda)
        {
            throw new NotImplementedException();
        }
    }
}
