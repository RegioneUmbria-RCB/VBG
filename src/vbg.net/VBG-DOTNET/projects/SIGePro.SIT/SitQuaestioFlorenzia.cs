using Init.SIGePro.Exceptions.SIT;
using Init.SIGePro.Sit.Data;
using Init.SIGePro.Sit.Manager;
using Init.SIGePro.Sit.QuestioFlorentia;
using Init.SIGePro.Sit.ValidazioneFormale;
using Init.SIGePro.Verticalizzazioni;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;

namespace Init.SIGePro.Sit
{

    /// Avviso per i posteri! Ho iniziato a sistemarla ma è veramente troppo incasinata. Continuerò a sistemarla
    /// Man mano che i bugs vengono fuori ma non posso dedicarle più di un giorno per effettuare la pulizia
    public class SIT_QUAESTIOFLORENZIA : SitBase
    {
        protected QuestioFlorentiaWrapper _sitProxy;

        public SIT_QUAESTIOFLORENZIA()
            : base(new ValidazioneFormaleTramiteCodiceCivicoService())
        {

        }

        #region Utility
        public override void SetupVerticalizzazione()
        {
            this.GetParametriFromVertSITQUAESTIOFLORENZIA();
        }

        /// <summary>
        /// Metodo usato per leggere i parametri della verticalizzazione SIT_CARTECH
        /// </summary>
        protected virtual void GetParametriFromVertSITQUAESTIOFLORENZIA()
        {
            try
            {
                var verticalizzazione = new VerticalizzazioneSitQuaestioflorenzia(this.IdComuneAlias, this.Software);

                if (!verticalizzazione.Attiva)
                    throw new Exception("La verticalizzazione SIT_QUAESTIOFLORENZIA non è attiva.");


                this._sitProxy = new QuestioFlorentiaWrapper(verticalizzazione.Url, this.CodiceComune);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore generato durante la lettura della verticalizzazione SIT_QUAESTIOFLORENZIA. Metodo: GetParametriFromVertSITQUAESTIOFLORENZIA, modulo: SIT_QUAESTIOFLORENZIA. " + ex.Message + "\r\n", ex);
            }
        }

        private RetSit GetElenco(string[] list)
        {
            return this.GetElenco(list, -1);
        }

        private RetSit GetElenco(string[] list, int iIndex)
        {
            var rVal = new RetSit(true);

            if (list == null)
            {
                return rVal;
            }

            foreach (var elem in list)
            {
                if (!string.IsNullOrEmpty(elem))
                {
                    var sElem = iIndex != -1 ?
                        elem.Split('@')[iIndex].TrimStart('0') :
                        elem.TrimStart('0');

                    if (!string.IsNullOrEmpty(sElem) && !rVal.DataCollection.Contains(sElem))
                        rVal.DataCollection.Add(sElem);
                }
            }

            return rVal;
        }

        private RetSit GetElenco(int[] list)
        {
            var rVal = new RetSit(true);

            if (list == null)
                return new RetSit(true);

            foreach (var elem in list)
            {
                var elPaddato = elem.ToString().TrimStart('0');

                if (!rVal.DataCollection.Contains(elPaddato))
                    rVal.DataCollection.Add(elPaddato);
            }

            return rVal;
        }

        private string GetUnicoElemento(int[] list)
        {
            // ??????

            if (list == null || list.Length == 0)
                return String.Empty;

            return string.Empty;

            //var distinctList = list.Distinct();

            //if (distinctList.Count() > 0)
            //	return String.Empty;

            //return distinctList.First().ToString();
        }


        private string GetElemento(string[] list, int idx = -1)
        {
            if (list == null || list.Length == 0)
                return String.Empty;

            var tmpList = list.Where(x => !String.IsNullOrEmpty(x));

            if (idx != -1)
            {
                tmpList = tmpList.Select(x => x.Split('@')[idx]);
            }

            tmpList = tmpList.Select(x => x.TrimStart('0')).Distinct();

            if (tmpList.Count() == 1)
                return tmpList.First();

            return String.Empty;
        }


        #endregion



        #region Metodi per ottenere elenchi di elementi catastali o facenti parte dell'indirizzo

        public override RetSit ElencoCivici()
        {
            try
            {
                if (String.IsNullOrEmpty(this.DataSit.CodVia) && String.IsNullOrEmpty(this.DataSit.Fabbricato))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista dei civici per insufficienza di dati: la via/fabbricato devono essere forniti", MessageCode.ElencoCivici, false);

                if (!string.IsNullOrEmpty(this.DataSit.TipoCatasto) && this.DataSit.TipoCatasto != "F")
                    return new RetSit(true);

                var list = this.GetDettagliCivicoDaCodFabbricatoOCodVia(x => x.Civico);

                return new RetSit(true, list.ToList());
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoCivici, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei civici. Metodo: ElencoCivici, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }


        public override RetSit ElencoEsponenti()
        {
            try
            {
                if (String.IsNullOrEmpty(this.DataSit.Civico) || (String.IsNullOrEmpty(this.DataSit.Fabbricato) && String.IsNullOrEmpty(this.DataSit.CodVia)))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista degli esponenti per insufficienza di dati: la via/fabbricato ed il civico devono essere forniti", MessageCode.ElencoEsponenti, false);

                if (!string.IsNullOrEmpty(this.DataSit.TipoCatasto) && this.DataSit.TipoCatasto != "F")
                    return new RetSit(true);

                var list = this.GetListaEsponenti(x => x.Esponente);

                return new RetSit(true, list.ToList());
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoEsponenti, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco degli esponenti. Metodo: ElencoEsponenti, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        private IEnumerable<string> GetListaEsponenti(Func<CodiceCivicoParsato, string> funzioneSelezioneCampo)
        {
            IEnumerable<CodiceCivicoParsato> listaCiviciDaWebService = new List<CodiceCivicoParsato>();

            if (!listaCiviciDaWebService.Any() && !String.IsNullOrEmpty(this.DataSit.CodVia))
                listaCiviciDaWebService = this._sitProxy
                                            .ElencoCodiciEDescrizioniDaCodVia(this.DataSit.CodVia)
                                            .Where(x => x != null)
                                            .Select(x => CodiceCivicoParsato.DaCodiceCivicoEDescrizione(x));

            var list = new List<string>();

            foreach (var civicoParsato in listaCiviciDaWebService)
            {
                Debug.WriteLine(civicoParsato.IdentificativoUnivocoCivico);

                // Ma servono veramente questi controlli? In teoria le chiamate agli Web Services dovrebbero sempre restituire la lista dei civici 
                // di una via...
                var codViaVuoto = string.IsNullOrEmpty(this.DataSit.CodVia);
                var esponenteVuoto = string.IsNullOrEmpty(this.DataSit.Esponente);
                var coloreVuoto = string.IsNullOrEmpty(this.DataSit.Colore);
                var codCivicoVuoto = true; // string.IsNullOrEmpty(this.DataSit.CodCivico);
                var civicoVuoto = string.IsNullOrEmpty(this.DataSit.Civico);

                var stringaSitContieneCodViaPassato = civicoParsato.CodVia == this.DataSit.CodVia;
                var stringaSitContieneEsponentePassato = civicoParsato.Esponente == this.DataSit.Esponente;
                var stringaSitContieneColorePassato = civicoParsato.Colore == this.DataSit.Colore;
                var stringaSitConteneCodCivicoPassato = civicoParsato.IdentificativoUnivocoCivico == this.DataSit.CodCivico;
                var stringaSitConteneCivicoPassato = civicoParsato.Civico == this.DataSit.Civico;

                var codViaCoincide = codViaVuoto || stringaSitContieneCodViaPassato;
                var esponenteCoincide = esponenteVuoto || stringaSitContieneEsponentePassato;
                var coloreCoincide = coloreVuoto || stringaSitContieneColorePassato;
                var codCivicoCoincide = codCivicoVuoto || stringaSitConteneCodCivicoPassato;
                var civicoCoincide = civicoVuoto || stringaSitConteneCivicoPassato;

                if (codViaCoincide && esponenteCoincide && coloreCoincide && codCivicoCoincide && civicoCoincide)
                {
                    var valore = funzioneSelezioneCampo(civicoParsato);

                    if (!list.Contains(valore) && !string.IsNullOrEmpty(valore))
                        list.Add(valore);
                }
            }

            return list;
        }

        private IEnumerable<string> GetDettagliCivicoDaCodFabbricatoOCodVia(Func<CodiceCivicoParsato, string> funzioneSelezioneCampo)
        {
            IEnumerable<CodiceCivicoParsato> listaCiviciDaWebService = new List<CodiceCivicoParsato>();

            if (!String.IsNullOrEmpty(this.DataSit.Fabbricato))
                listaCiviciDaWebService = this._sitProxy
                                            .ElencoCodiciDaCodFabbricato(this.DataSit.Fabbricato)
                                            .Where(x => x != null)
                                            .Select(x => CodiceCivicoParsato.DaCodiceCivico(x));

            if (!listaCiviciDaWebService.Any() && !String.IsNullOrEmpty(this.DataSit.CodVia))
                listaCiviciDaWebService = this._sitProxy
                                            .ElencoCodiciEDescrizioniDaCodVia(this.DataSit.CodVia)
                                            .Where(x => x != null)
                                            .Select(x => CodiceCivicoParsato.DaCodiceCivicoEDescrizione(x));

            var list = new List<string>();

            foreach (var civicoParsato in listaCiviciDaWebService)
            {
                Debug.WriteLine(civicoParsato.IdentificativoUnivocoCivico);

                // Ma servono veramente questi controlli? In teoria le chiamate agli Web Services dovrebbero sempre restituire la lista dei civici 
                // di una via...
                var codViaVuoto = string.IsNullOrEmpty(this.DataSit.CodVia);
                var esponenteVuoto = string.IsNullOrEmpty(this.DataSit.Esponente);
                var coloreVuoto = string.IsNullOrEmpty(this.DataSit.Colore);
                var codCivicoVuoto = string.IsNullOrEmpty(this.DataSit.CodCivico);
                var civicoVuoto = string.IsNullOrEmpty(this.DataSit.Civico);

                var stringaSitContieneCodViaPassato = civicoParsato.CodVia == this.DataSit.CodVia;
                var stringaSitContieneEsponentePassato = civicoParsato.Esponente == this.DataSit.Esponente;
                var stringaSitContieneColorePassato = civicoParsato.Colore == this.DataSit.Colore;
                var stringaSitConteneCodCivicoPassato = civicoParsato.IdentificativoUnivocoCivico == this.DataSit.CodCivico;
                var stringaSitConteneCivicoPassato = civicoParsato.Civico == this.DataSit.Civico;

                var codViaCoincide = codViaVuoto || stringaSitContieneCodViaPassato;
                var esponenteCoincide = esponenteVuoto || stringaSitContieneEsponentePassato;
                var coloreCoincide = coloreVuoto || stringaSitContieneColorePassato;
                var codCivicoCoincide = codCivicoVuoto || stringaSitConteneCodCivicoPassato;
                var civicoCoincide = civicoVuoto || stringaSitConteneCivicoPassato;

                if (codViaCoincide && esponenteCoincide && coloreCoincide && codCivicoCoincide && civicoCoincide)
                {
                    var valore = funzioneSelezioneCampo(civicoParsato);

                    if (!list.Contains(valore) && !string.IsNullOrEmpty(valore))
                        list.Add(valore);
                }
            }

            return list;
        }


        public override RetSit ElencoFabbricati()
        {
            try
            {
                if (String.IsNullOrEmpty(this.DataSit.CodVia) && (String.IsNullOrEmpty(this.DataSit.Foglio) || String.IsNullOrEmpty(this.DataSit.Particella)))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista dei fabbricati per insufficienza di dati: la via/foglio,particella devono essere forniti", MessageCode.ElencoFabbricati, false);

                if (!string.IsNullOrEmpty(this.DataSit.TipoCatasto) && (this.DataSit.TipoCatasto != "F"))
                    return new RetSit(true);

                if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                {
                    var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);

                    if (list.Length > 0)
                        return this.GetElenco(list, 0);
                }

                if ((!String.IsNullOrEmpty(this.DataSit.Foglio) && !String.IsNullOrEmpty(this.DataSit.Particella)))
                {
                    var list = this._sitProxy.aciElencoCodFabbricatoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);
                    return this.GetElenco(list);
                }

                return null;
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFabbricati, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fabbricati. Metodo: ElencoFabbricati, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }


        public override RetSit ElencoFogli()
        {
            try
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    throw new CatastoException("Non è possibile ottenere la lista dei fogli per insufficienza di dati: il catasto deve essere fornito");

                if (this.DataSit.TipoCatasto == "F")
                {
                    if (String.IsNullOrEmpty(this.DataSit.CodVia))
                        throw new CatastoException("Non è possibile ottenere la lista dei fogli senza specificare una via");

                    var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);

                    if (list != null && list.Length > 0)
                        return this.GetElenco(list, 1);

                    return new RetSit(true);
                }

                if (this.IsCampiToponomasticaImmobileVuoti())
                {
                    var list = this._sitProxy.ElencoFoglioDaFoglioParziale(0, QuestioFlorentiaWrapper.TipoCatasto.CT);
                    return new RetSit(true, list.ToList());
                }

                return new RetSit(true);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoFogli, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei fogli. Metodo: ElencoFogli, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        public override RetSit ElencoParticelle()
        {
            try
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || String.IsNullOrEmpty(this.DataSit.Foglio))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista delle particelle per insufficienza di dati: il catasto ed il foglio devono essere forniti", MessageCode.ElencoParticelle, false);

                string[] list = null;

                if (this.DataSit.TipoCatasto == "F")
                {
                    if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                    {
                        list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);

                        if (list != null && list.Length > 0)
                            return this.GetElenco(list, 2);
                    }

                    var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CF);
                    return new RetSit(true, particelle.Select(x => x.Particella).ToList());
                }

                if (this.IsCampiToponomasticaImmobileVuoti())
                {
                    var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CT);
                    return new RetSit(true, particelle.Select(x => x.Particella).ToList());
                }

                return new RetSit(true);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ElencoParticelle, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle particelle. Metodo: ElencoParticelle, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        public override RetSit ElencoSub()
        {
            try
            {
                if (string.IsNullOrEmpty(this.DataSit.TipoCatasto) || string.IsNullOrEmpty(this.DataSit.Foglio) || string.IsNullOrEmpty(this.DataSit.Particella))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista dei sub per insufficienza di dati: il catasto ed il foglio e la particella devono essere forniti", MessageCode.ElencoSub, false);

                if (this.DataSit.TipoCatasto == "F")
                {
                    var list = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, QuestioFlorentiaWrapper.TipoCatasto.CF);


                    if ((list == null) || ((list.Length == 1) && (list[0] == "0")))
                        return new RetSit(true);

                    return this.GetElenco(list);
                }

                if (this.IsCampiToponomasticaImmobileVuoti())
                {
                    var list = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, QuestioFlorentiaWrapper.TipoCatasto.CT);

                    if ((list == null) || ((list.Length == 1) && (list[0] == "0")))
                        return new RetSit(true);

                    return this.GetElenco(list);
                }

                return new RetSit(true);

            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco dei sub. Metodo: ElencoSub, modulo: SitQuaestioFlorenzia. " + ex.Message, ex);
            }
        }

        public override RetSit ElencoUI()
        {
            try
            {
                var tipoCatastoVuoto = string.IsNullOrEmpty(this.DataSit.TipoCatasto);
                var fabbricatoVuoto = String.IsNullOrEmpty(this.DataSit.Fabbricato);
                var foglioVuoto = String.IsNullOrEmpty(this.DataSit.Foglio);
                var particellaVuota = String.IsNullOrEmpty(this.DataSit.Particella);

                if (tipoCatastoVuoto || (fabbricatoVuoto && (foglioVuoto || particellaVuota)))
                    return this.RestituisciErroreSit("Non è possibile ottenere la lista delle unità immobiliari per insufficienza di dati: il catasto ed il fabbricato/foglio,particella devono essere forniti", MessageCode.ElencoUI, false);

                if (this.DataSit.TipoCatasto != "F")    // non esiste unità immobiliare nel catasto terreni
                    return new RetSit(true);

                if (!fabbricatoVuoto)
                {
                    var list = this._sitProxy.aciElencoIdImmobileDaCodFabbricato(this.DataSit.Fabbricato);

                    if (list != null && list.Length > 0)
                        return this.GetElenco(list);
                }


                if (!foglioVuoto && !particellaVuota)
                {
                    var list = this._sitProxy.aciElencoIdImmobileDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);
                    return this.GetElenco(list);
                }

                return null;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle unità immobiliari. Metodo: ElencoUI, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }

            //return pRetSit;
        }



        public override RetSit ElencoDatiUrbanistici()
        {
            RetSit pRetSit;

            try
            {
                if (!String.IsNullOrEmpty(this.DataSit.CodCivico))
                {
                    string[] listUTOE = null;
                    listUTOE = this._sitProxy.tpnCodNumNomeUTOEDaCodCivico(this.DataSit.CodCivico);

                    pRetSit = this.GetElenco(listUTOE);
                }
                else
                {
                    pRetSit = this.RestituisciErroreSit("Non è possibile ottenere la lista delle zone UTOE per insufficienza di dati: il codice civico deve essere fornito", MessageCode.ElencoDatiUrbanistici, false);
                }
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione dell'elenco delle zone UTOE. Metodo: ElencoDatiUrbanistici, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }

            return pRetSit;
        }
        #endregion

        #region Metodi per la verifica e la restituzione di un singolo elemento catastale o facente parte dell'indirizzo

        protected override string GetEsponente()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return string.Empty;

                var listaCodCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                if (listaCodCivici != null && listaCodCivici.Length > 0)
                {
                    var esponenti = listaCodCivici
                                    .Where(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaCivicoColoreCodCivico(x))
                                    .Select(x => x.Substring(16, 3))
                                    .Distinct();

                    return this.GetElemento(esponenti.ToArray());
                }
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un esponente. Metodo: GetEsponente, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }

        }



        protected override RetSit VerificaEsponente()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return this.RestituisciErroreSit("VerificaEsponente: Tipo catasto \"" + this.DataSit.TipoCatasto + "\" non valido", MessageCode.EsponenteValidazione, false);

                var listaCodCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                var esponenteTrovato = listaCodCivici
                                            .Where(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaCivicoEsponenteColoreCodCivico(x))
                                            .Count();

                if (esponenteTrovato > 0)
                    return new RetSit(true);

                return this.RestituisciErroreSit("L'esponente " + this.DataSit.Esponente + " non è valido per i dati inseriti", MessageCode.EsponenteValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un esponente. Metodo: VerificaEsponente, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        private string[] GetElencoCodiciCivici(string codFabbricato, string codVia, string foglio, string particella)
        {
            string[] listCodCivico = null;

            if (!String.IsNullOrEmpty(codFabbricato))
                listCodCivico = this._sitProxy.aciElencoCodCivicoDaCodFabbricato(codFabbricato);

            if (listCodCivico != null && listCodCivico.Length > 0)
                return listCodCivico;

            if (!String.IsNullOrEmpty(codVia))
                listCodCivico = this._sitProxy.ElencoCodiciEDescrizioniDaCodVia(codVia);

            if (listCodCivico != null && listCodCivico.Length > 0)
                return listCodCivico;

            if (!String.IsNullOrEmpty(foglio) && !string.IsNullOrEmpty(particella))
                listCodCivico = this._sitProxy.aciElencoCodCivicoEtNomeStradaEtDescrCivicoDaFoglioParticella(foglio, particella);

            return listCodCivico;
        }

        protected override string GetColore()
        {
            try
            {
                if ((this.DataSit.TipoCatasto == "F") || (string.IsNullOrEmpty(this.DataSit.TipoCatasto)))
                {
                    var listCodCivico = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                    if (listCodCivico != null)
                    {
                        var elementi = listCodCivico
                                        .Where(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaCivicoEsponenteCodCivico(x))
                                        .Select(x => x.Substring(27, 1))
                                        .Distinct();

                        var rVal = this.GetElemento(elementi.ToArray());

                        if (!String.IsNullOrEmpty(rVal))
                            return rVal;
                    }
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un colore. Metodo: GetColore, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        private bool VerificaCivicoEsponenteColoreCodCivico(string valoreCodCivico)
        {
            var civico = valoreCodCivico.Substring(12, 4).TrimStart('0');
            var esponente = valoreCodCivico.Substring(16, 3).TrimStart('0');
            var colore = valoreCodCivico.Substring(27, 1).TrimStart('0');
            var codCivico = valoreCodCivico.Substring(0, 28);

            return (string.IsNullOrEmpty(this.DataSit.Civico) ? true : civico == this.DataSit.Civico) &&
                    (string.IsNullOrEmpty(this.DataSit.Esponente) ? true : esponente == this.DataSit.Esponente) &&
                    (string.IsNullOrEmpty(this.DataSit.Colore) ? true : colore == this.DataSit.Colore) &&
                    (string.IsNullOrEmpty(this.DataSit.CodCivico) ? true : codCivico == this.DataSit.CodCivico);
        }


        private bool VerificaCodViaEsponenteColoreCodCivico(string valoreCodCivico)
        {
            var c = CodiceCivicoParsato.DaCodiceCivico(valoreCodCivico);

            return (string.IsNullOrEmpty(this.DataSit.CodVia) ? true : c.CodVia == this.DataSit.CodVia) &&
                (string.IsNullOrEmpty(this.DataSit.Esponente) ? true : c.Esponente == this.DataSit.Esponente) &&
                (string.IsNullOrEmpty(this.DataSit.Colore) ? true : c.Colore == this.DataSit.Colore) &&
                (string.IsNullOrEmpty(this.DataSit.CodCivico) ? true : c.IdentificativoUnivocoCivico == this.DataSit.CodCivico);
        }

        private bool VerificaCodViaCivicoEsponenteColoreCodCivico(string valoreCodCivico)
        {
            var c = CodiceCivicoParsato.DaCodiceCivico(valoreCodCivico);

            return (string.IsNullOrEmpty(this.DataSit.CodVia) ? true : c.CodVia == this.DataSit.CodVia) &&
                    (string.IsNullOrEmpty(this.DataSit.Civico) ? true : c.Civico == this.DataSit.Civico) &&
                    (string.IsNullOrEmpty(this.DataSit.Esponente) ? true : c.Esponente == this.DataSit.Esponente) &&
                    (string.IsNullOrEmpty(this.DataSit.Colore) ? true : c.Colore == this.DataSit.Colore) &&
                    (string.IsNullOrEmpty(this.DataSit.CodCivico) ? true : c.IdentificativoUnivocoCivico == this.DataSit.CodCivico);
        }

        private bool VerificaCodViaCivicoColoreCodCivico(string valoreCodCivico)
        {
            var codiceVia = valoreCodCivico.Substring(4, 8).TrimStart('0');
            var civico = valoreCodCivico.Substring(12, 4).TrimStart('0');
            var colore = valoreCodCivico.Substring(27, 1).TrimStart('0');
            var codCivico = valoreCodCivico.Substring(0, 28).TrimStart('0');


            return (string.IsNullOrEmpty(this.DataSit.CodVia) ? true : codiceVia == this.DataSit.CodVia) &&
                   (string.IsNullOrEmpty(this.DataSit.Civico) ? true : civico == this.DataSit.Civico) &&
                   (string.IsNullOrEmpty(this.DataSit.Colore) ? true : colore == this.DataSit.Colore) &&
                   (string.IsNullOrEmpty(this.DataSit.CodCivico) ? true : codCivico == this.DataSit.CodCivico);
        }

        private bool VerificaCodViaCivicoEsponenteCodCivico(string codiceCivicoSit)
        {
            var codiceVia = codiceCivicoSit.Substring(4, 8).TrimStart('0');
            var civico = codiceCivicoSit.Substring(12, 4).TrimStart('0');
            var esponente = codiceCivicoSit.Substring(16, 3).TrimStart('0');
            var codCivico = codiceCivicoSit.Substring(0, 28).TrimStart('0');

            return (string.IsNullOrEmpty(this.DataSit.CodVia) ? true : codiceVia == this.DataSit.CodVia) &&
                    (string.IsNullOrEmpty(this.DataSit.Civico) ? true : civico == this.DataSit.Civico) &&
                    (string.IsNullOrEmpty(this.DataSit.Esponente) ? true : esponente == this.DataSit.Esponente) &&
                    (string.IsNullOrEmpty(this.DataSit.CodCivico) ? true : codCivico == this.DataSit.CodCivico);
        }


        protected override RetSit VerificaColore()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return this.RestituisciErroreSit("VerificaColore: Tipo catasto \"" + this.DataSit.TipoCatasto + "\" non valido", MessageCode.ColoreValidazione, false);

                var listaCodCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                var coloreTrovato = listaCodCivici
                                            .Any(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaCivicoEsponenteColoreCodCivico(x));

                if (coloreTrovato)
                    return new RetSit(true);

                return this.RestituisciErroreSit("Il colore " + this.DataSit.Colore + " non è valido per i dati inseriti", MessageCode.ColoreValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione del colore. Metodo: VerificaColore, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override RetSit VerificaFabbricato()
        {
            RetSit pRetSit;

            try
            {
                if ((this.DataSit.TipoCatasto == "F") || (string.IsNullOrEmpty(this.DataSit.TipoCatasto)))
                {
                    var sElem = string.Empty;

                    if (!string.IsNullOrEmpty(this.DataSit.CodCivico))
                        if (this._sitProxy.aciCodFabbricatoDaCodCivico(this.DataSit.CodCivico) == this.DataSit.Fabbricato)
                            sElem = this.DataSit.Fabbricato;

                    string[] list = null;
                    if (string.IsNullOrEmpty(sElem))
                    {
                        if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                        {
                            list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                            if (this.GetElemento(list, 0) == this.DataSit.Fabbricato)
                                sElem = this.DataSit.Fabbricato;
                        }
                    }

                    if (string.IsNullOrEmpty(sElem))
                    {
                        if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                        {
                            list = this._sitProxy.aciElencoCodFabbricatoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);
                            if (this.GetElemento(list) == this.DataSit.Fabbricato)
                                sElem = this.DataSit.Fabbricato;
                        }
                    }

                    if (!String.IsNullOrEmpty(sElem))
                    {
                        pRetSit = new RetSit(true);
                        this.DataSit.Fabbricato = sElem;
                    }
                    else
                    {
                        pRetSit = this.RestituisciErroreSit("Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti", MessageCode.FabbricatoValidazione, false);
                    }
                }
                else
                    throw new CatastoException("Il fabbricato " + this.DataSit.Fabbricato + " non è valido per i dati inseriti");

            }
            catch (CatastoException ex)
            {
                pRetSit = this.RestituisciErroreSit(ex.Message, MessageCode.FabbricatoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un fabbricato. Metodo: VerificaFabbricato, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }

            return pRetSit;
        }

        protected override RetSit VerificaTipoCatasto()
        {
            RetSit pRetSit = null;

            if (this.IsCampiToponomasticaImmobileVuoti())
                pRetSit = new RetSit(true);
            else
            {
                if (this.DataSit.TipoCatasto == "T")
                    pRetSit = this.RestituisciErroreSit("Il tipocatasto " + this.DataSit.TipoCatasto + " non è valido per i dati inseriti", MessageCode.TipoCatastoValidazione, false);
                else
                    pRetSit = new RetSit(true);
            }

            return pRetSit;
        }

        protected override string GetFoglio()
        {
            try
            {
                if (this.DataSit.TipoCatasto == "F" || string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    if (!string.IsNullOrEmpty(this.DataSit.Fabbricato))
                    {
                        var riferimento = this._sitProxy.aciFoglioParticellaDaCodFabbricato(this.DataSit.Fabbricato);

                        if (riferimento != null)
                            return riferimento.Foglio;
                    }


                    if (!string.IsNullOrEmpty(this.DataSit.CodVia))
                    {
                        var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                        return this.GetElemento(list, 1);
                    }

                }

                return String.Empty;
            }
            catch (CatastoException)
            {
                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un foglio. Metodo: GetFoglio, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override RetSit VerificaFoglio()
        {
            try
            {
                if ((this.DataSit.TipoCatasto == "F") || (string.IsNullOrEmpty(this.DataSit.TipoCatasto)))
                {
                    if (!string.IsNullOrEmpty(this.DataSit.Fabbricato))
                    {
                        var riferimento = this._sitProxy.aciFoglioParticellaDaCodFabbricato(this.DataSit.Fabbricato);

                        if (riferimento != null && riferimento.Foglio == this.DataSit.Foglio)
                            return new RetSit(true);
                    }

                    if (!string.IsNullOrEmpty(this.DataSit.CodVia))
                    {
                        var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                        if (this.GetElemento(list, 1) == this.DataSit.Foglio)
                            return new RetSit(true);
                    }


                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        var listaFogli = this._sitProxy.ElencoFoglioDaFoglioParziale(Convert.ToInt32(this.DataSit.Foglio), QuestioFlorentiaWrapper.TipoCatasto.CF);

                        if (listaFogli.Where(x => x == this.DataSit.Foglio).Count() > 0)
                            return new RetSit(true);
                    }
                }
                else
                {
                    if (!this.IsCampiToponomasticaImmobileVuoti())
                        throw new CatastoException("Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti");

                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        var listaFogli = this._sitProxy.ElencoFoglioDaFoglioParziale(Convert.ToInt32(this.DataSit.Foglio), QuestioFlorentiaWrapper.TipoCatasto.CT);

                        if (listaFogli.Where(x => x == this.DataSit.Foglio).Count() > 0)
                            return new RetSit(true);
                    }
                }

                return this.RestituisciErroreSit("Il foglio " + this.DataSit.Foglio + " non è valido per i dati inseriti", MessageCode.FoglioValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.FoglioValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un foglio. Metodo: VerificaFoglio, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override string GetParticella()
        {
            try
            {
                if (this.DataSit.TipoCatasto == "F" || string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    if (!string.IsNullOrEmpty(this.DataSit.Fabbricato))
                    {
                        var riferimento = this._sitProxy.aciFoglioParticellaDaCodFabbricato(this.DataSit.Fabbricato);

                        if (riferimento != null)
                            return riferimento.Particella;
                    }


                    if (string.IsNullOrEmpty(this.DataSit.CodVia))
                    {
                        var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                        var res = this.GetElemento(list, 2);

                        if (!string.IsNullOrEmpty(res))
                            return res;
                    }

                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CF);

                        if (particelle.Count() == 1)
                            return particelle.First().Particella;
                    }
                }
                else
                {
                    if (!this.IsCampiToponomasticaImmobileVuoti())
                        throw new CatastoException("La particella " + this.DataSit.Particella + " non è valida per i dati inseriti");

                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CT);

                        if (particelle.Count() == 1)
                            return particelle.First().Particella;
                    }
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una particella. Metodo: GetParticella, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override RetSit VerificaParticella()
        {
            try
            {
                string[] list = null;
                if (this.DataSit.TipoCatasto == "F" || string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        if (this._sitProxy.ctsEsisteFoglioParticellaSubalterno(this.DataSit.Foglio, this.DataSit.Particella, "0", QuestioFlorentiaWrapper.TipoCatasto.CF))
                            return new RetSit(true);
                    }

                    if (!string.IsNullOrEmpty(this.DataSit.Fabbricato))
                    {
                        var riferimento = this._sitProxy.aciFoglioParticellaDaCodFabbricato(this.DataSit.Fabbricato);

                        if (riferimento?.Particella == this.DataSit.Particella)
                            return new RetSit(true);
                    }

                    if (!string.IsNullOrEmpty(this.DataSit.CodVia))
                    {
                        list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                        if (this.GetElemento(list, 2) == this.DataSit.Particella)
                            return new RetSit(true);
                    }

                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CF);

                        if (particelle.Any(x => x.Particella == this.DataSit.Particella))
                            return new RetSit(true);
                    }
                }
                else
                {
                    if (!this.IsCampiToponomasticaImmobileVuoti())
                        throw new CatastoException("La particella " + this.DataSit.Particella + " non è valida per i dati inseriti");

                    if (!string.IsNullOrEmpty(this.DataSit.Foglio))
                    {
                        if (this._sitProxy.ctsEsisteFoglioParticellaSubalterno(this.DataSit.Foglio, this.DataSit.Particella, "0", QuestioFlorentiaWrapper.TipoCatasto.CT))
                            return new RetSit(true);

                        var particelle = this._sitProxy.ctsElencoParticellaDaFoglio(this.DataSit.Foglio, QuestioFlorentiaWrapper.TipoCatasto.CT);

                        if (particelle.Any(x => x.Particella == this.DataSit.Particella))
                            return new RetSit(true);
                    }

                }

                return this.RestituisciErroreSit("La particella " + this.DataSit.Particella + " non è valida per i dati inseriti", MessageCode.ParticellaValidazione, false);
            }
            catch (CatastoException ex)
            {
                return this.RestituisciErroreSit(ex.Message, MessageCode.ParticellaValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una particella. Metodo: VerificaParticella, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override string GetSub()
        {
            var sRetVal = "";

            try
            {
                if ((this.DataSit.TipoCatasto == "F") || (string.IsNullOrEmpty(this.DataSit.TipoCatasto)))
                {
                    if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                    {
                        string[] list = null;

                        list = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, QuestioFlorentiaWrapper.TipoCatasto.CF);

                        if (list != null)
                        {
                            if ((list.Length == 1) && (list[0] == "0"))
                                return String.Empty;

                            sRetVal = this.GetElemento(list);
                        }
                    }

                    if (string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    {
                        var sRetValCT = "";
                        if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                        {
                            string[] list = null;

                            list = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, QuestioFlorentiaWrapper.TipoCatasto.CT);

                            if (list != null)
                            {
                                if ((list.Length == 1) && (list[0] == "0"))
                                    sRetValCT = "";
                                else
                                    sRetValCT = this.GetElemento(list);
                            }
                        }

                        if (!string.IsNullOrEmpty(sRetVal))
                        {
                            if (!string.IsNullOrEmpty(sRetValCT))
                                if (sRetVal != sRetValCT)
                                    sRetVal = string.Empty;
                        }
                        else
                            if (!string.IsNullOrEmpty(sRetValCT))
                                sRetVal = sRetValCT;
                    }
                }
                else
                {
                    if (this.IsCampiToponomasticaImmobileVuoti() && !string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                    {
                        string[] list = null;

                        list = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, QuestioFlorentiaWrapper.TipoCatasto.CT);

                        if (list != null)
                        {
                            if ((list.Length == 1) && (list[0] == "0"))
                                return sRetVal;
                            else
                                sRetVal = this.GetElemento(list);
                        }
                    }
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un sub. Metodo: GetSub, modulo: SitQuaestioFlorenzia. " + ex.Message, ex);
            }
        }

        protected override RetSit VerificaQuartiere()
        {
            if (string.IsNullOrEmpty(this.DataSit.CodCivico))
            {
                return new RetSit(false);
            }

            var quartiere = this._sitProxy.CodiceQuartiereDaCodiceCivico(this.DataSit.CodCivico);

            return new RetSit(this.DataSit.Quartiere == quartiere?.CodiceQuartiere);
        }

        public override RetSit ElencoQuartieri()
        {
            if (string.IsNullOrEmpty(this.DataSit.CodCivico))
            {
                return new RetSit(false);
            }

            var quartiere = this._sitProxy.CodiceQuartiereDaCodiceCivico(this.DataSit.CodCivico);

            if (quartiere == null)
            {
                return new RetSit(false);
            }

            return new RetSit(true, new[] { quartiere.CodiceQuartiere });
        }

        protected override string GetQuartiere()
        {
            if (string.IsNullOrEmpty(this.DataSit.CodCivico))
            {
                return String.Empty;
            }

            return this._sitProxy.CodiceQuartiereDaCodiceCivico(this.DataSit.CodCivico)?.CodiceQuartiere;
        }


        protected override RetSit VerificaSub()
        {
            try
            {
                if (this.DataSit.TipoCatasto == "F" || string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                {
                    if (this.VerificaEsistenzaSub(QuestioFlorentiaWrapper.TipoCatasto.CF))
                        return new RetSit(true);
                }
                else
                {
                    if (this.VerificaEsistenzaSub(QuestioFlorentiaWrapper.TipoCatasto.CT))
                        return new RetSit(true);
                }

                return this.RestituisciErroreSit("Il sub " + this.DataSit.Sub + " non è valido per i dati inseriti", MessageCode.SubValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un sub. Metodo: VerificaSub, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        private bool VerificaEsistenzaSub(QuestioFlorentiaWrapper.TipoCatasto catasto)
        {
            if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
            {
                if (this._sitProxy.ctsEsisteFoglioParticellaSubalterno(this.DataSit.Foglio, this.DataSit.Particella, this.DataSit.Sub, catasto))
                    return true;

                var listaSub = this._sitProxy.ctsElencoSubalternoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella, catasto);

                if (listaSub != null)
                {
                    var subEsiste = listaSub
                                        .Where(x => !string.IsNullOrEmpty(x))
                                        .Select(x => x.TrimStart('0'))
                                        .Where(x => x == this.DataSit.Sub)
                                        .Count() > 0;

                    if (subEsiste)
                        return true;
                }
            }

            return false;
        }

        protected override string GetUI()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return String.Empty;

                if (!string.IsNullOrEmpty(this.DataSit.CodCivico))
                {
                    var idImmobile = this._sitProxy.aciIdImmobileDaCodCivico(this.DataSit.CodCivico).ToString();

                    if (!string.IsNullOrEmpty(idImmobile))
                        return idImmobile;
                }


                if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                {
                    var list = this._sitProxy.aciElencoIdImmobileDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);
                    return this.GetUnicoElemento(list);
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di una unità immobiliare. Metodo: GetUI, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override RetSit VerificaUI()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return this.RestituisciErroreSit("L'unità immobiliare " + this.DataSit.UI + " non è valida per i dati inseriti", MessageCode.UIValidazione, false);

                if (!string.IsNullOrEmpty(this.DataSit.CodCivico))
                {
                    if (this._sitProxy.aciIdImmobileDaCodCivico(this.DataSit.CodCivico).ToString() == this.DataSit.UI)
                        return new RetSit(true);
                }

                if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                {
                    var list = this._sitProxy.aciElencoIdImmobileDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);

                    if (this.GetUnicoElemento(list) == this.DataSit.UI)
                        return new RetSit(true);
                }

                return this.RestituisciErroreSit("L'unità immobiliare " + this.DataSit.UI + " non è valida per i dati inseriti", MessageCode.UIValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di una unità immobiliare. Metodo: VerificaUI, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override string GetCivico()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return String.Empty;

                var listaCodCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                if (listaCodCivici == null)
                    return String.Empty;

                var civiciTrovati = listaCodCivici
                                        .Where(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaEsponenteColoreCodCivico(x))
                                        .Select(x => x.Substring(12, 4))
                                        .Distinct();

                if (civiciTrovati.Count() == 0)
                    return String.Empty;

                return this.GetElemento(civiciTrovati.ToArray());
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un civico. Metodo: GetCivico, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }


        protected override RetSit VerificaCivico()
        {
            if (!Init.Utils.StringChecker.IsNumeric(this.DataSit.Civico))
                return this.RestituisciErroreSit("Non è possibile validare il civico " + this.DataSit.Civico + " perchè non è un numero", MessageCode.CivicoValidazioneNumero, false);

            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return this.RestituisciErroreSit("Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti", MessageCode.CivicoValidazione, false);

                var listaCodCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                if (listaCodCivici == null)
                    return this.RestituisciErroreSit("Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti", MessageCode.CivicoValidazione, false);

                var civiciCount = listaCodCivici
                                    .Any(x => !String.IsNullOrEmpty(x) && this.VerificaCodViaCivicoEsponenteColoreCodCivico(x));

                if (civiciCount)
                    return new RetSit(true);

                return this.RestituisciErroreSit("Il civico " + this.DataSit.Civico + " non è valido per i dati inseriti", MessageCode.CivicoValidazione, false);
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la validazione di un civico. Metodo: VerificaCivico, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override string GetCodCivico()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return String.Empty;

                var civiciDaWebService = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, this.DataSit.CodVia, this.DataSit.Foglio, this.DataSit.Particella);

                if (civiciDaWebService != null)
                {
                    var civiciTrovati = civiciDaWebService
                                            .Distinct()
                                            .Where(x => !String.IsNullOrEmpty(x))
                                            .Select(x => CodiceCivicoParsato.DaCodiceCivicoEDescrizione(x))
                                            .Where(x => this.DataSit.CodVia == x.CodVia &&
                                                            this.DataSit.Civico == x.Civico &&
                                                            this.DataSit.Esponente == x.Esponente &&
                                                            this.DataSit.Colore == x.Colore);

                    if (civiciTrovati.Count() == 1)
                        return civiciTrovati.First().IdentificativoUnivocoCivico;
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice civico. Metodo: GetCodiceCivico, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }


        }

        protected override string GetCodFabbricato()
        {
            try
            {
                if (this.DataSit.TipoCatasto != "F" && !string.IsNullOrEmpty(this.DataSit.TipoCatasto))
                    return String.Empty;

                if (!string.IsNullOrEmpty(this.DataSit.CodCivico))
                {
                    var sRetVal = this._sitProxy.aciCodFabbricatoDaCodCivico(this.DataSit.CodCivico);

                    if (!string.IsNullOrEmpty(sRetVal))
                        return sRetVal;
                }

                if (!String.IsNullOrEmpty(this.DataSit.CodVia))
                {
                    var list = this._sitProxy.aciElencoCodFabbricatoEtFoglioEtParticellaDaCodStrada(this.DataSit.CodVia);
                    var sRetVal = this.GetElemento(list, 0);

                    if (!string.IsNullOrEmpty(sRetVal))
                        return sRetVal;
                }

                if (!string.IsNullOrEmpty(this.DataSit.Foglio) && !string.IsNullOrEmpty(this.DataSit.Particella))
                {
                    var list = this._sitProxy.aciElencoCodFabbricatoDaFoglioParticella(this.DataSit.Foglio, this.DataSit.Particella);
                    return this.GetElemento(list);
                }

                return String.Empty;
            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un fabbricato. Metodo: GetCodFabbricato, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }

        protected override RetSit VerificaCodiceVia()
        {
            return this.RestituisciErroreSit(MessageCode.CodiceViaValidazione, true);
        }

        protected override string GetCodVia()
        {
            try
            {
                var listaCodiciCivici = this.GetElencoCodiciCivici(this.DataSit.Fabbricato, String.Empty, this.DataSit.Foglio, this.DataSit.Particella);

                if (listaCodiciCivici == null)
                    return String.Empty;

                var listaCodiciVie = listaCodiciCivici
                                        .Where(x => !String.IsNullOrEmpty(x) && this.VerificaCivicoEsponenteColoreCodCivico(x))
                                        .Select(x => x.Substring(4, 8))
                                        .Distinct();

                return this.GetElemento(listaCodiciVie.ToArray());


            }
            catch (Exception ex)
            {
                throw new Exception("Errore durante la restituzione di un codice via. Metodo: GetCodVia, modulo: SitQuaestioFlorenzia. " + ex.Message);
            }
        }



        #endregion

        public override string[] GetListaCampiGestiti()
        {
            return new string[]{
                SitIntegrationService.NomiCampiSit.Esponente,
                SitIntegrationService.NomiCampiSit.Colore,
                SitIntegrationService.NomiCampiSit.Fabbricato,
                SitIntegrationService.NomiCampiSit.TipoCatasto,
                SitIntegrationService.NomiCampiSit.Foglio,
                SitIntegrationService.NomiCampiSit.Particella,
                SitIntegrationService.NomiCampiSit.Sub,
                SitIntegrationService.NomiCampiSit.UnitaImmobiliare,
                SitIntegrationService.NomiCampiSit.Civico,
                SitIntegrationService.NomiCampiSit.CodiceCivico,
                SitIntegrationService.NomiCampiSit.CodiceVia,
                SitIntegrationService.NomiCampiSit.Quartiere,
            };
        }
    }
}