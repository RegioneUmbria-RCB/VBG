//using System;
//using System.Collections.Generic;
//using System.Linq;
//using Init.Sigepro.FrontEnd.AppLogic.IoC;
//using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
//using Init.Sigepro.FrontEnd.AppLogic.Repositories.Interfaces;
//using Ninject;

//namespace Init.Sigepro.FrontEnd.AppLogic.Adapters
//{
//    public class LogicaEstrazioneSoggetti : Init.Sigepro.FrontEnd.AppLogic.Adapters.ILogicaEstrazioneSoggetti
//    {
//        [Inject]
//        public ITipiSoggettoRepository _tipiSoggettoRepository { get; set; }



//        private PresentazioneIstanzaDataSet.ANAGRAFEDataTable m_tabellaAnagrafe;
//        private PresentazioneIstanzaDataSet.ANAGRAFERow m_richiedente;
//        private PresentazioneIstanzaDataSet.ANAGRAFERow m_tecnico;
//        private PresentazioneIstanzaDataSet.ANAGRAFERow m_azienda;

//        private string m_alias = String.Empty;
//        private string m_software = String.Empty;


//        IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> m_cacheRichiedenti = null;


//        public LogicaEstrazioneSoggetti(string alias , string software, PresentazioneIstanzaDataSet.ANAGRAFEDataTable tabellaAnagrafe)
//        {
//            FoKernelContainer.Inject(this);

//            m_tabellaAnagrafe = tabellaAnagrafe;
//            m_alias = alias;
//            m_software = software;
//        }


//        public PresentazioneIstanzaDataSet.ANAGRAFERow EstraiRichiedente()
//        {
//            if (m_richiedente == null)
//                m_richiedente = EstraiRichiedenteInternal(true);

//            return m_richiedente;
//        }

//        public PresentazioneIstanzaDataSet.ANAGRAFERow EstraiRichiedenteNoException()
//        {
//            if (m_richiedente == null)
//                m_richiedente = EstraiRichiedenteInternal(false);

//            return m_richiedente;
//        }

//        public PresentazioneIstanzaDataSet.ANAGRAFERow EstraiTecnico()
//        {
//            if (m_tecnico == null)
//                m_tecnico = EstraiTecnicoInternal();

//            return m_tecnico;
//        }

//        public PresentazioneIstanzaDataSet.ANAGRAFERow EstraiAzienda()
//        {
//            if (m_azienda == null)
//                m_azienda = EstraiAziendaInternal();

//            return m_azienda;
//        }

//        public IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> EstraiListaRichiedenti()
//        {
//            if (m_cacheRichiedenti == null)
//                m_cacheRichiedenti = GetListaRichiedenti();

//            return m_cacheRichiedenti;
//        }



//        /// <summary>
//        /// Estrae l'azienda dalla lista dei soggetti dell'istanza.
//        /// L'azienda è il primo soggetto il cui tiposoggetto ha il flag TIPODATO == "A"
//        /// </summary>
//        /// <param name="tabellaAnagrafe"></param>
//        /// <returns></returns>
//        private PresentazioneIstanzaDataSet.ANAGRAFERow EstraiAziendaInternal()
//        {
//            var result = from PresentazioneIstanzaDataSet.ANAGRAFERow r in m_tabellaAnagrafe
//                         where GetFlagTipoSoggetto(r.TIPOSOGGETTO) == "A" && r.TIPOANAGRAFE == "G"
//                         select r;

//            if (result.Count() == 0)
//                return null;

//            return result.ElementAt(0);
//        }


//        /// <summary>
//        /// Estrae il tecnico dalla lista dei soggetti dell'istanza.
//        /// Il tecnico è il primo soggetto il cui tiposoggetto ha il flag TIPODATO == "T"
//        /// </summary>
//        /// <param name="tabellaAnagrafe"></param>
//        /// <returns></returns>
//        private PresentazioneIstanzaDataSet.ANAGRAFERow EstraiTecnicoInternal()
//        {
//            var result = from PresentazioneIstanzaDataSet.ANAGRAFERow r in m_tabellaAnagrafe
//                         where GetFlagTipoSoggetto(r.TIPOSOGGETTO) == "T"
//                         select r;

//            if (result.Count() == 0)
//                return null;

//            return result.ElementAt(0);
//        }


//        /// <summary>
//        /// Estrae il richiedente dalla lista dei soggetti della domanda. 
//        /// Il richiedente è il primo soggetto senza procuratore il cui tiposoggetto ha il flag TIPODATO == "R"
//        /// Se non venissero trovati soggetti senza procuratore con TIPODATO == "R" viene preso il primo soggetto con tipodato == "R"
//        /// </summary>
//        /// <param name="tabellaAnagrafe"></param>
//        /// <returns></returns>
//        private PresentazioneIstanzaDataSet.ANAGRAFERow EstraiRichiedenteInternal(bool throwException)
//        {
//            var result = GetListaRichiedenti();

//            if (result.Count() == 0)
//            {
//                if (throwException)
//                    throw new Exception("Impossibile individuare il richiedente nella lista dei soggetti della domanda");

//                return null;
//            }

//            // Leggo la lista dei richiedenti che hanno una PEC valida
//            var listaRichiedentiConPec = new List<PresentazioneIstanzaDataSet.ANAGRAFERow>();

//            foreach (var richiedente in result)
//            {
//                if (String.IsNullOrEmpty(richiedente.Pec))
//                    continue;

//                listaRichiedentiConPec.Add(richiedente);
//            }

//            // Se esistono richiedenti con pec hanno la priorità sui richiedenti senza PEC
//            if (listaRichiedentiConPec.Count > 0)
//            {
//                // Nella lista esistono soggetti senza procura?
//                var soggettoConPecSenzaProcura = EstraiPrimoRichiedenteSenzaProcura(listaRichiedentiConPec);

//                if (soggettoConPecSenzaProcura != null)
//                    return soggettoConPecSenzaProcura;

//                return listaRichiedentiConPec.ElementAt(0);
//            }

//            // Non esistono soggetti con PEC, cerco nella lista di tutti i richiedenti se ne esiste uno senza procura
//            var soggettoSenzaPecSenzaProcura = EstraiPrimoRichiedenteSenzaProcura(result);

//            if (soggettoSenzaPecSenzaProcura != null)
//                return soggettoSenzaPecSenzaProcura;

//            // Nessun soggetto ha la pec e tutti i soggetti hanno la procura. A questo punto un soggetto vale l'altro 
//            // e restituisco il primo soggetto della lista
//            return result.ElementAt(0);
//        }

//        /// <summary>
//        /// Verifica se nella lista passata esiste un soggetto che non ha procura utilizzabile come richiedente
//        /// Restituisce il primo oggetto senza procura trovato oppure null nel caso in cui tutti i soggetti abbiano una procura (o se la lista è vuota)
//        /// </summary>
//        /// <param name="listaRichiedenti">Lista di soggetti nella quale occorre cercare soggetti senza procura</param>
//        /// <returns>Restituisce il primo oggetto senza procura trovato oppure null nel caso in cui tutti i soggetti abbiano una procura (o se la lista è vuota)</returns>
//        private PresentazioneIstanzaDataSet.ANAGRAFERow EstraiPrimoRichiedenteSenzaProcura(IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> listaRichiedenti)
//        {
//            foreach (var richiedente in listaRichiedenti)
//            {
//                var codiceProcuratore = (m_tabellaAnagrafe.DataSet as PresentazioneIstanzaDataSet).IstanzeProcuratori.GetCodiceFiscaleSoggettoAventeProcura(richiedente.CODICEFISCALE);

//                if (String.IsNullOrEmpty(codiceProcuratore))
//                    return richiedente;
//            }

//            return null;
//        }

//        private IEnumerable<PresentazioneIstanzaDataSet.ANAGRAFERow> GetListaRichiedenti()
//        {
//            var result = from PresentazioneIstanzaDataSet.ANAGRAFERow r in m_tabellaAnagrafe
//                         where !r.IsTIPOSOGGETTONull() && GetFlagTipoSoggetto(r.TIPOSOGGETTO) == "R" && r.TIPOANAGRAFE == "F"
//                         select r;
//            return result;
//        }

//        /// <summary>
//        /// Legge il flag TIPODATO per il codice tipo soggetto passato
//        /// </summary>
//        /// <param name="codiceTipoSoggetto"></param>
//        /// <returns></returns>
//        private string GetFlagTipoSoggetto(int codiceTipoSoggetto)
//        {
//            var ts = _tipiSoggettoRepository.GetById(m_alias, m_software, codiceTipoSoggetto);

//            if (ts == null)
//                return String.Empty;

//            return ts.TIPODATO;
//        }
//    }
//}
