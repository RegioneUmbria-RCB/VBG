

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_CAUSALIRIDUZIONIT per la classe OCausaliRiduzioniT il 14/11/2008 16.27.25
    ///
    ///						ELENCARE DI SEGUITO EVENTUALI MODIFICHE APPORTATE MANUALMENTE ALLA CLASSE
    ///				(per tenere traccia dei cambiamenti nel caso in cui la classe debba essere generata di nuovo)
    /// -
    /// -
    /// -
    /// - 
    ///
    ///	Prima di effettuare modifiche al template di MyGeneration in caso di dubbi contattare Nicola Gargagli ;)
    ///


    public partial class OCausaliRiduzioniTMgrValidatingEventArgs
    {
        private readonly AmbitoValidazione m_ambitoValidazione;
        public AmbitoValidazione AmbitoValidazione { get { return this.m_ambitoValidazione; } }

        private readonly OCausaliRiduzioniT m_classe;
        public OCausaliRiduzioniT Classe { get { return this.m_classe; } }

        public OCausaliRiduzioniTMgrValidatingEventArgs(AmbitoValidazione ambitoValidazione, OCausaliRiduzioniT classe)
        {
            this.m_ambitoValidazione = ambitoValidazione;
            this.m_classe = classe;
        }
    }

    public partial class OCausaliRiduzioniTMgr : BaseManager
    {
        #region callbacks per gli eventi relativi alle operazioni sul database

        // delegate invocato prima dell'inserimento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
        public delegate void InsertingDelegate(OCausaliRiduzioniT cls);
        public event InsertingDelegate Inserting;

        // delegate invocato subito dopo l'inserimento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
        public delegate void InsertedDelegate(OCausaliRiduzioniT cls);
        public event InsertedDelegate Inserted;

        // delegate invocato prima dell'aggiornamento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
        public delegate void UpdatingDelegate(OCausaliRiduzioniT cls);
        public event UpdatingDelegate Updating;

        // delegate invocato subito dopo l'aggiornamento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
        public delegate void UpdatedDelegate(OCausaliRiduzioniT cls);
        public event UpdatedDelegate Updated;

        // delegate invocato prima della cancellazione di un record dal database. Effettuare qui l'eventuale verifica dell'integrità referenziale 
        //(sollevare un eccezione di tipo ReferentialIntegrityException se il controllo fallisce)  e l'eventuale coancellazione a cascata
        public delegate void DeletingDelegate(OCausaliRiduzioniT cls);
        public event DeletingDelegate Deleting;

        // delegate invocato subito dopo la cancellazione di un record dal db.
        public delegate void DeletedDelegate(OCausaliRiduzioniT cls);
        public event DeletedDelegate Deleted;

        // Delegate invocato durante la validazione di una classe (subito dopo la RequiredFieldValidate). Effettuare qui la validazione formale dei dati
        public delegate void ValidatingDelegate(OCausaliRiduzioniTMgrValidatingEventArgs e);
        public event ValidatingDelegate Validating;

        #endregion


        public OCausaliRiduzioniTMgr(DataBase dataBase) : base(dataBase) { }

        public OCausaliRiduzioniT? GetById(string idcomune, int id)
        {
            FormattableString sql = $"select * from O_CAUSALIRIDUZIONIT where idcomune = {idcomune} and id = {id}";

            return this.db.GetClassList<OCausaliRiduzioniT>(sql).FirstOrDefault();
        }

        //public List<OCausaliRiduzioniT> GetList(OCausaliRiduzioniT filtro)
        //{
        //	return db.GetClassList( filtro ).ToList< OCausaliRiduzioniT>();
        //}

        public OCausaliRiduzioniT Insert(OCausaliRiduzioniT cls)
        {
            if (Inserting != null)
                Inserting(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            if (Inserted != null)
                Inserted(cls);

            return cls;
        }


        public OCausaliRiduzioniT Update(OCausaliRiduzioniT cls)
        {
            if (Updating != null)
                Updating(cls);

            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            if (Updated != null)
                Updated(cls);

            return cls;
        }

        public void Delete(OCausaliRiduzioniT cls)
        {
            if (Deleting != null)
                Deleting(cls);

            this.db.Delete(cls);

            if (Deleted != null)
                Deleted(cls);
        }



        private void Validate(OCausaliRiduzioniT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);

            if (Validating != null)
                Validating(new OCausaliRiduzioniTMgrValidatingEventArgs(ambitoValidazione, cls));
        }

        public IEnumerable<OCausaliRiduzioniT> GetList(string idComune, string software)
        {
            FormattableString sql = $"select * from O_CAUSALIRIDUZIONIT where idcomune = {idComune} and software = {software} order by descrizione";
            return this.db.GetClassList<OCausaliRiduzioniT>(sql);
        }
    }
}


