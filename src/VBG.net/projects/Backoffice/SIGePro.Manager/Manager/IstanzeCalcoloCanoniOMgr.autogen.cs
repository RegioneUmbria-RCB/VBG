

using System;
using System.Collections.Generic;
using System.Text;
using Init.SIGePro.Manager;
using PersonalLib2.Data;
using Init.SIGePro.Data;
using Init.SIGePro.Validator;
using PersonalLib2.Sql;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_O per la classe IstanzeCalcoloCanoniO il 18/11/2008 11.16.32
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


    public partial class IstanzeCalcoloCanoniOMgrValidatingEventArgs
    {
        AmbitoValidazione m_ambitoValidazione;
        public AmbitoValidazione AmbitoValidazione { get { return m_ambitoValidazione; } }

        IstanzeCalcoloCanoniO m_classe;
        public IstanzeCalcoloCanoniO Classe { get { return m_classe; } }

        public IstanzeCalcoloCanoniOMgrValidatingEventArgs(AmbitoValidazione ambitoValidazione, IstanzeCalcoloCanoniO classe)
        {
            m_ambitoValidazione = ambitoValidazione;
            m_classe = classe;
        }
    }

    public partial class IstanzeCalcoloCanoniOMgr : BaseManager
    {
        #region callbacks per gli eventi relativi alle operazioni sul database

        // delegate invocato prima dell'inserimento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
        public delegate void InsertingDelegate(IstanzeCalcoloCanoniO cls);
        public event InsertingDelegate Inserting;

        // delegate invocato subito dopo l'inserimento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
        public delegate void InsertedDelegate(IstanzeCalcoloCanoniO cls);
        public event InsertedDelegate Inserted;

        // delegate invocato prima dell'aggiornamento nel db e della validazione, effettuare qui l'eventuale integrazione dei dati
        public delegate void UpdatingDelegate(IstanzeCalcoloCanoniO cls);
        public event UpdatingDelegate Updating;

        // delegate invocato subito dopo l'aggiornamento nel db. Effettuare quil'eventuale integrazione e inserimento di classi figlio
        public delegate void UpdatedDelegate(IstanzeCalcoloCanoniO cls);
        public event UpdatedDelegate Updated;

        // delegate invocato prima della cancellazione di un record dal database. Effettuare qui l'eventuale verifica dell'integrità referenziale 
        //(sollevare un eccezione di tipo ReferentialIntegrityException se il controllo fallisce)  e l'eventuale coancellazione a cascata
        public delegate void DeletingDelegate(IstanzeCalcoloCanoniO cls);
        public event DeletingDelegate Deleting;

        // delegate invocato subito dopo la cancellazione di un record dal db.
        public delegate void DeletedDelegate(IstanzeCalcoloCanoniO cls);
        public event DeletedDelegate Deleted;

        // Delegate invocato durante la validazione di una classe (subito dopo la RequiredFieldValidate). Effettuare qui la validazione formale dei dati
        public delegate void ValidatingDelegate(IstanzeCalcoloCanoniOMgrValidatingEventArgs e);
        public event ValidatingDelegate Validating;

        #endregion


        public IstanzeCalcoloCanoniOMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeCalcoloCanoniO GetById( string idcomune, int fkidtestata, int fkidcausale, int? fkidistoneri )
        {
            IstanzeCalcoloCanoniO c = new IstanzeCalcoloCanoniO();
            c.Idcomune = idcomune;
            c.FkIdtestata = fkidtestata;
            c.FkIdCausale = fkidcausale;
            c.FkIdistoneri = fkidistoneri;

            return (IstanzeCalcoloCanoniO)db.GetClass(c);
        }

        public List<IstanzeCalcoloCanoniO> GetList(IstanzeCalcoloCanoniO filtro)
        {
            return db.GetClassList(filtro).ToList<IstanzeCalcoloCanoniO>();
        }


        public IstanzeCalcoloCanoniO Update(IstanzeCalcoloCanoniO cls)
        {
            if (Updating != null)
                Updating(cls);

            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            if (Updated != null)
                Updated(cls);

            return cls;
        }

        public void Delete(IstanzeCalcoloCanoniO cls)
        {
            if (Deleting != null)
                Deleting(cls);

            db.Delete(cls);

            if (Deleted != null)
                Deleted(cls);
        }



        private void Validate(IstanzeCalcoloCanoniO cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);

            if (Validating != null)
                Validating(new IstanzeCalcoloCanoniOMgrValidatingEventArgs(ambitoValidazione, cls));
        }
    }
}


