using Init.SIGePro.Data;
using Init.SIGePro.Exceptions;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{   ///<summary>
    /// Descrizione di riepilogo per IstanzeStradarioMgr.
    /// </summary>
    public class Mercati_UsoMgr : BaseManager
    {

        public Mercati_UsoMgr(DataBase dataBase) : base(dataBase) { }

        #region Metodi per l'accesso di base al DB

        public Mercati_Uso GetById(String idComune, int IdMercatiUso)
        {
            Mercati_Uso retVal = new Mercati_Uso();
            retVal.Id = IdMercatiUso;
            retVal.IdComune = idComune;

            var mydc = this.db.GetClassList(retVal, true);
            if (mydc.Count != 0)
                return mydc[0];

            return null;
        }



        /// <summary>
        /// Ritorna una lista di classi corrispondenti ai criteri di ricerca passati
        /// </summary>
        /// <param name="p_class">Criteri di ricerca</param>
        /// <returns>ArrayList di oggetti corrispondenti ai criteri di ricerca passati</returns>
        public List<Mercati_Uso> GetList(Mercati_Uso p_class)
        {
            return this.db.GetClassList(p_class);
        }

        public void Delete(Mercati_Uso p_class)
        {
            this.db.Delete(p_class);
        }

        public Mercati_Uso Insert(Mercati_Uso p_class)
        {

            p_class = this.DataIntegrations(p_class);

            this.Validate(p_class, AmbitoValidazione.Insert);

            this.db.Insert(p_class);

            return p_class;
        }

        public Mercati_Uso Update(Mercati_Uso p_class)
        {
            this.db.Update(p_class);
            return p_class;
        }


        private Mercati_Uso DataIntegrations(Mercati_Uso p_class)
        {
            //VUOTO PERCHé I DATI DEVONO ESSERE PASSATI INIZIALMENTE
            //IN QUANTO NON E' POSSIBILE FARE INSERIMENTI NELLE TABELLE
            //COLLEGATE PERCHE' PRIVE DI IDCOMUNE 
            //(GIORNISETTIMANA)
            return p_class;
        }

        private void Validate(Mercati_Uso p_class, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(p_class, ambitoValidazione);

            this.ForeignValidate(p_class);
        }

        private void ForeignValidate(Mercati_Uso p_class)
        {
            #region MERCATI_USO.FKCODICEMERCATO
            if (p_class.FkCodiceMercato.GetValueOrDefault(int.MinValue) > int.MinValue)
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICEMERCATO", p_class.FkCodiceMercato.ToString()),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IdComune)
                };

                if (this.recordCount("MERCATI", "CODICEMERCATO", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_USO.FKCODICEMERCATO " + p_class.FkCodiceMercato.ToString() + " non trovato nella tabella MERCATI"));
                }
            }
            #endregion

            #region MERCATI_USO.FKCODICEUSO
            if (!String.IsNullOrEmpty(p_class.FkCodiceUso))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("CODICE", p_class.FkCodiceUso),
                    new KeyValuePair<string, string>("IDCOMUNE", p_class.IdComune)
                };

                if (this.recordCount("CONCESSIONIUSO", "CODICE", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERCATI_USO.FKCODICEUSO non trovato nella tabella CONCESSIONIUSO"));
                }
            }
            #endregion

            #region MERCATI_USO.FKGSID
            if (!String.IsNullOrEmpty(p_class.FkGsId))
            {
                var conditions = new List<KeyValuePair<string, string>>
                {
                    new KeyValuePair<string, string>("GS_ID", p_class.FkGsId)
                };

                if (this.recordCount("GIORNISETTIMANA", "GS_ID", conditions) == 0)
                {
                    throw (new RecordNotfoundException("MERATI_USO.FKGSID non trovato nella tabella GIORNISETTIMANA"));
                }
            }
            #endregion
        }

        #endregion
    }
}