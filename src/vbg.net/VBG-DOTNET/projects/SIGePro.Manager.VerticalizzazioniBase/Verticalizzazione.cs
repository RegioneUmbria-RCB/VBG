using Init.SIGePro.Manager.IOC;
using RegoleService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Xml.Serialization;

namespace SIGePro.Manager.VerticalizzazioniBase
{
    /// <summary>
    /// Rappresenta una verticalizzazione del sistema SIGePro
    /// </summary>
    public abstract class Verticalizzazione
    {

        /// <summary>
        /// True se la verticalizzazione è attiva
        /// </summary>
        [XmlIgnore]
        public virtual bool Attiva { get; protected set; }
        /// <summary>
        /// Hashtable di coppie chiave / valore di tutti i parametri della verticalizzazione
        /// <code>string mioValore = Varticalizzazione.Parametri["NOMEPARAMETRO"]</code>
        /// </summary>
        [XmlIgnore]
        public Dictionary<string, string> Parametri { get; private set; } = new Dictionary<string, string>();

        [XmlIgnore]
        public abstract string NomeVerticalizzazione { get; }

        public string Alias
        {
            get => this._alias;
            set
            {
                if (string.IsNullOrEmpty(value))
                {
                    throw new Exception($"Impossibile inizializzare la verticalizzazione {this.NomeVerticalizzazione} con un alias vuoto");
                }
                this._alias = value;
            }
        }

        public string Software
        {
            get => this._software;
            set => this._software = String.IsNullOrEmpty(value) ? "TT" : value.ToUpper();
        }

        public string CodiceComune { get; set; }

        public RegoleServiceClient RegoleServiceClient { get; set; }

        public bool IsInitialized
        {
            get { return this._initialized; }
        }

        private string _alias;
        private string _software = "TT";
        private bool _initialized = false;
        /// <summary>
        /// Istanzia una nuova verticalizzazione. Utilizzando il codice software passato
        /// </summary>
        public Verticalizzazione(string alias, string nomeVeticalizzazone, string software = "TT", string codiceComune = "")
        {
            this.Software = software;
            this.Alias = alias;
            this.CodiceComune = codiceComune;

            this.Initialize();
        }

        protected Verticalizzazione()
        {
        }

        public void Initialize()
        {
            if (this._initialized)
            {
                return;
            }

            if (String.IsNullOrEmpty(this.NomeVerticalizzazione))
            {
                throw new Exception($"{nameof(this.NomeVerticalizzazione)} non inizializzato per la verticalizzazione ????");
            }

            if (String.IsNullOrEmpty(this.Alias))
            {
                throw new Exception($"{nameof(this.Alias)} non inizializzato per la verticalizzazione {this.NomeVerticalizzazione}");
            }

            if (String.IsNullOrEmpty(this.Software))
            {
                throw new Exception($"{nameof(this.Software)} non inizializzato per la verticalizzazione {this.NomeVerticalizzazione}");
            }

            if (this.RegoleServiceClient == null)
            {
                throw new Exception($"{nameof(this.RegoleServiceClient)} non inizializzato per la verticalizzazione {this.NomeVerticalizzazione}");
            }

            var response = this.RegoleServiceClient.GetRegola(this.Alias, new RegolaRequest
            {
                codiceComune = this.CodiceComune,
                software = this.Software,
                nomeRegola = this.NomeVerticalizzazione,
                recuperaParametri = true,
                recuperaParametriSpecified = true
            });

            this.Attiva = response.attiva;

            if (response.listaParametri == null)
                this.Parametri = new Dictionary<string, string>();
            else
                this.Parametri = response.listaParametri.ToDictionary(x => x.descrizione.ToString(), y => y.valore);

            _initialized = true;
        }

        protected void Read()
        {
            //var timedCache = StaticKernelContainer.GetService<ITimedCache>();
            //this.Initialize(new RegoleServiceClient(new AuthenticationManager(timedCache)));
        }


        /// <summary>
        /// Ottiene un valore bool da un parametro. Se il parametro è null o vuoto ritorna false
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public bool GetBool(string paramName, bool defaultValue = false)
        {
            if (!this.Parametri.ContainsKey(paramName)) return defaultValue;
            if (this.Parametri[paramName] == null) return defaultValue;
            if (this.Parametri[paramName] == String.Empty) return defaultValue;

            return (this.Parametri[paramName] == "1");
        }

        /// <summary>
        /// Ottiene un valore string da un parametro. Se il parametro è null ritorna String.Empty
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public string GetString(string paramName)
        {
            if (this.Parametri == null) return String.Empty;
            if (!this.Parametri.ContainsKey(paramName)) return String.Empty;
            if (this.Parametri[paramName] == null) return String.Empty;
            return this.Parametri[paramName];
        }

        public string GetStringOrDefault(string paramName, string defaultValue)
        {
            var str = this.GetString(paramName);

            if (String.IsNullOrEmpty(str))
            {
                return defaultValue;
            }

            return str;
        }

        public void SetString(string paramName, string value)
        {
            if (this.Parametri == null) return;
            this.Parametri[paramName] = value;
        }

        public DateTime? GetDate(string paramName)
        {
            if (this.Parametri == null) return null;
            if (!this.Parametri.ContainsKey(paramName)) return null;
            if (this.Parametri[paramName] == null) return null;
            return DateTime.ParseExact(this.Parametri[paramName], "dd/MM/yyyy", null);
        }

        public void SetDate(string paramName, DateTime? value)
        {
            if (this.Parametri == null) return;
            this.Parametri[paramName] = (value.HasValue) ? value.Value.ToString("dd/MM/yyyy") : null;
        }

        /// <summary>
        /// Ottiene un valore int da un parametro. Se il parametro è null ritorna null
        /// </summary>
        /// <param name="paramName">Nome del parametro</param>
        /// <returns>Valore del parametro</returns>
        public int? GetInt(string paramName)
        {
            if (!this.Parametri.ContainsKey(paramName) || String.IsNullOrEmpty(this.Parametri[paramName]))
            {
                return null;
            }

            return Convert.ToInt32(this.Parametri[paramName]);
        }

        public void SetInt(string paramName, int? value)
        {
            if (this.Parametri == null) return;
            this.Parametri[paramName] = (value.HasValue) ? value.ToString() : null;
        }

    }

    public class VerticalizzazioneGenerica : Verticalizzazione
    {
        public override string NomeVerticalizzazione => this._nomeVerticalizzazione;

        private readonly string _nomeVerticalizzazione = null;

        public VerticalizzazioneGenerica(string alias, string nomeVerticalizzazione)
        //: base(alias, nomeVerticalizzazione)
        {
            this._nomeVerticalizzazione = nomeVerticalizzazione;
            this.Alias = alias;

            //this.Read();
        }
    }
}
