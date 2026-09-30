using Init.SIGePro.Sit.Data;
using log4net;
using System;
using System.Collections.Generic;
using System.Text.Json;

namespace Init.SIGePro.Sit.Ravenna2
{
    public class Ravenna3DbClient
    {
        public static class Constants
        {
            public const string Particella = "PARTICELLE";

            public static class TabellaRA012
            {
                public const string CampoCivico = "NUMERO";
                public const string CampoEsponente = "PARTE";
                public const string CampoCodiceVia = "COD_VIA";
                public const string CampoSezione = "SEZIONE";
                public const string CampoCodiceSezione = "COD_SEZ";
                public const string CampoFoglio = "FOGLIO";
                public const string CampoParticella = "PARTICELLA";
                public const string CampoEdificio = "COD_EDIF";
                public const string CodCivico = "INDIRIZZO";
                public const string CampoCAP = "CAP";
                public const string CampoToponimo = "TOPONIMO";
            }

            public static class TabellaRA147
            {
                public const string CampoObjectId = "OBJECTID";
                public const string CampoCodiceSezione = "COD_SEZ";
                public const string CampoAnno = "ANNO";
                public const string CampoCodFraz = "COD_FRAZ";
                public const string CampoTipSez = "TIP_SEZ";
                public const string CampoCodiceCircoscrizione = "COD_CIRC";
                public const string CampoDescrizioneFrazione = "DESCRIZION";
                public const string CampoDescrizi_1 = "DESCRIZI_1";
            }

            public static class TabellaParicelleCatastali
            {
                public const string CampoObjectid = "OBJECTID";
                public const string CampoComune = "COMUNE";
                public const string CampoSezione = "SEZIONE";
                public const string CampoFoglio = "FOGLIO";
                public const string CampoAllegato = "ALLEGATO";
                public const string CampoSviluppo = "SVILUPPO";
                public const string CampoOrigine = "ORIGINE";
                public const string CampoNumero = "NUMERO";
                public const string CampoLivello = "LIVELLO";
                public const string CampoChiave = "CHIAVE";
                public const string CampoSez = "SEZ";
                public const string CampoGiskey = "GISKEY";
            }
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(Ravenna3DbClient));
        private readonly string _codViarioNonDefinito = "0";
        private readonly Ravenna3RestClient _ravenna3RestClient;

        public Ravenna3DbClient(VerticalizzazioneSitRavenna2 verticalizzazione)
        {
            this._ravenna3RestClient = new Ravenna3RestClient(verticalizzazione.UrlWsVbgRA147, verticalizzazione.UrlWsVbgRA012, verticalizzazione.UrlWsVbgParticelleCatastali);
        }

        internal Ravenna3ResultSet GetListaCivici(string codVia)
        {
            return this.GetMultipleResults(_ravenna3RestClient.GetListaCivici(codVia), Ravenna3DbClient.Constants.TabellaRA012.CampoCivico, "NUMBER");
        }

        internal DettagliVia[] GetListaVie()
        {
            var retVal = new List<DettagliVia>();

            var result = _ravenna3RestClient.GetListaVie();

            return this.ListaVieArray(retVal, result);
        }

        private DettagliVia[] ListaVieArray(List<DettagliVia> retVal, string jsonString)
        {
            JsonDocument doc = JsonDocument.Parse(jsonString);

            int length = 0;
            foreach (var _ in doc.RootElement.GetProperty("features").EnumerateArray())
            {
                length++;
            }

            if (length == 0)
            {
                this._log.DebugFormat("GetSingleResult: La ricerca effettuata non ha restituito risultati");
                return new DettagliVia[] { };
            }

            foreach (var item in doc.RootElement.GetProperty("features").EnumerateArray())
            {
                var attributes = item.GetProperty("attributes");
                var dettagliVia = new DettagliVia
                {
                    Toponimo = attributes.GetProperty("TOPONIMO").GetString(),
                    Denominazione = attributes.GetProperty("INDIRIZZO").GetString(),
                    CodiceViario = attributes.GetProperty("COD_VIA").GetInt32().ToString()
                };
                retVal.Add(dettagliVia);
            }

            return retVal.ToArray();
        }

        internal Ravenna3ResultSet GetListaEsponenti(string codVia, string civico)
        {
            return _ravenna3RestClient.GetListaEsponenti(codVia, civico);
        }

        internal Ravenna3ResultSet GetListaSezioni()
        {
            return _ravenna3RestClient.GetListaSezioni();
        }

        internal Ravenna3ResultSet GetListaSezioni(string codVia, string civico, string esponente)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente))
            {
                return this.GetListaSezioni();
            }

            return _ravenna3RestClient.GetListaSezioni(codVia, civico, esponente);
        }

        internal Ravenna3ResultSet GetListaFogli(string sezione)
        {
            return _ravenna3RestClient.GetListaFogli(sezione);
        }

        internal Ravenna3ResultSet GetListaFogli(string codVia, string civico, string esponente, string sezione)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione))
            {
                return this.GetListaFogli(sezione);
            }
            return _ravenna3RestClient.GetListaFogli(codVia, civico, esponente, sezione);
        }

        internal Ravenna3ResultSet GetListaParticelle(string sezione, string foglio)
        {
            return this.GetMultipleResults(_ravenna3RestClient.GetListaParticelle(sezione, foglio), "NUMERO", "NUMBER");
        }

        internal Ravenna3ResultSet GetListaParticelle(string codVia, string civico, string esponente, string sezione, string foglio)
        {
            if ((String.IsNullOrEmpty(codVia) || codVia == this._codViarioNonDefinito) && String.IsNullOrEmpty(civico) && String.IsNullOrEmpty(esponente) && !String.IsNullOrEmpty(sezione) && !String.IsNullOrEmpty(foglio))
            {
                return this.GetListaParticelle(sezione, foglio);
            }

            return this.GetMultipleResults(_ravenna3RestClient.GetListaParticelle(codVia, civico, esponente, sezione, foglio), "PARTICELLA", "STRING");
        }

        internal Ravenna3Result GetCivico(string codVia, string civico, string esponente)
        {
            if (String.IsNullOrEmpty(civico))
            {
                return new Ravenna3Result(true, this._log);
            }
            return this.GetSingleRa012Result(_ravenna3RestClient.GetCivico(codVia, civico), esponente, "civicofunction");
        }

        internal Ravenna3Result GetEsponente(string codVia, string civico, string esponente)
        {
           

            return this.GetSingleRa012Result(_ravenna3RestClient.GetEsponente(codVia, civico, esponente), esponente, "esponentefunction");
        }

        internal Ravenna3Result GetSezione(string sezione)
        {
            if (String.IsNullOrEmpty(sezione))
            {
                return new Ravenna3Result(true, this._log);
            }

            return this.GetSingleParicelleCatastaliResult(_ravenna3RestClient.GetSezione(sezione));
        }

        internal Ravenna3Result GetFoglio(string sezione, string foglio)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio))
            {
                return new Ravenna3Result(true, this._log);
            }

            return this.GetSingleParicelleCatastaliResult(_ravenna3RestClient.GetFoglio(sezione, foglio));
        }

        internal Ravenna3Result GetParticella(string sezione, string foglio, string particella)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
            {
                return new Ravenna3Result(true, this._log);
            }

            return this.GetSingleParicelleCatastaliResult(_ravenna3RestClient.GetParticella(sezione, foglio, particella));
        }

        internal Ravenna3Result GetParticella(string codVia, string civico, string esponente, string sezione, string foglio, string particella)
        {
            if (String.IsNullOrEmpty(sezione) || String.IsNullOrEmpty(foglio) || String.IsNullOrEmpty(particella))
            {
                return new Ravenna3Result(true, this._log);
            }

            if (String.IsNullOrEmpty(codVia) || codVia == "0" || String.IsNullOrEmpty(civico))
            {
                return this.GetParticella(sezione, foglio, particella);
            }

            return this.GetSingleRa012Result(_ravenna3RestClient.GetParticella(codVia, civico, esponente, sezione, foglio, particella), esponente, "particellafunction");
        }

        private Ravenna3ResultSet GetMultipleResults(string jsonString, string fieldName, string fieldType)
        {
            return new Ravenna3ResultSet(jsonString, fieldName, fieldType);
        }

        private Ravenna3Result GetSingleRa012Result(string jsonString, string esponente , string function)
        {
            JsonDocument doc = JsonDocument.Parse(jsonString);
            int length = 0;

            try
            {
                foreach (var _ in doc.RootElement.GetProperty("features").EnumerateArray())
                {
                    length++;
                }
            }
            catch (Exception)
            {
                this._log.DebugFormat("GetSingleResult: elemento non trovato nel risultato");
                if (doc.RootElement.GetProperty("error").GetProperty("code").GetInt32() == 400)
                {
                    this._log.DebugFormat(doc.RootElement.GetProperty("error").GetProperty("message").GetString(), this._log);
                    throw new Exception(doc.RootElement.GetProperty("error").GetProperty("message").GetString());
                }
                return new Ravenna3EmptyResult(this._log);
            }

            if (length == 0)
            {
                this._log.DebugFormat("GetSingleResult: La ricerca effettuata non ha restituito risultati");
                return new Ravenna3EmptyResult(this._log);
            }

            if (length > 1)
            {
                this._log.DebugFormat("GetSingleResult: La ricerca effettuata ha restituito più di un risultato");


                if (esponente != null)
                {
                    for (int i = 0; i < length; i++)
                    {

                        var el = doc.RootElement.GetProperty("features")[i];
                        try
                        {
                            if (el.GetProperty("attributes").GetProperty("PARTE").GetString() == esponente)
                            {
                                if (function.Equals("civicofunction"))
                                    return new Ra012Result(el,this, this._log);
                                else if (function.Equals("esponentefunction"))
                                    return new Ra012Result(el, this, this._log);
                            }
                        }
                        catch (Exception)
                        {
                            //nothing
                        }

                    }
                }

                if(function.Equals("civicofunction"))
                    return new Ra012Result(doc.RootElement.GetProperty("features")[0], this._log);
                else if(function.Equals("esponentefunction"))
                    return new Ra012Result(doc.RootElement.GetProperty("features")[0], this,  this._log);

            }

            return new Ra012Result(doc.RootElement.GetProperty("features")[0], this, this._log);
        }

        private Ravenna3Result GetSingleParicelleCatastaliResult(string jsonString)
        {
            JsonDocument doc = JsonDocument.Parse(jsonString);

            int length = 0;
            foreach (var _ in doc.RootElement.GetProperty("features").EnumerateArray())
            {
                length++;
            }

            if (length == 0)
            {
                this._log.DebugFormat("GetSingleResult: La ricerca effettuata non ha restituito risultati");
                return new Ravenna3EmptyResult(this._log);
            }

            if (length > 0)
            {
                this._log.DebugFormat("GetSingleResult: La ricerca effettuata ha restituito più di un risultato");
                return new Ravenna3MultipleResultsFound(this._log);
            }

            return new ParicelleCatastaliResult(doc.RootElement.GetProperty("features")[0].GetProperty("attributes"), this._log);
        }
        internal string GetFrazione(string codiceSezione)
        {
            return _ravenna3RestClient.GetFrazione(codiceSezione);
        }
    }
}
