

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
    /// File generato automaticamente dalla tabella ISTANZECALCOLOCANONI_T per la classe IstanzeCalcoloCanoniT il 11/11/2008 9.19.34
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
    public partial class IstanzeCalcoloCanoniTMgr : BaseManager
    {
        public IstanzeCalcoloCanoniTMgr(DataBase dataBase) : base(dataBase) { }

        public IstanzeCalcoloCanoniT GetById(string idcomune, int id)
        {
            IstanzeCalcoloCanoniT c = new IstanzeCalcoloCanoniT();


            c.Idcomune = idcomune;
            c.Id = id;

            return (IstanzeCalcoloCanoniT)db.GetClass(c);
        }

        public List<IstanzeCalcoloCanoniT> GetList(IstanzeCalcoloCanoniT filtro)
        {
            return db.GetClassList(filtro).ToList<IstanzeCalcoloCanoniT>();
        }

        public IstanzeCalcoloCanoniT Insert(IstanzeCalcoloCanoniT cls)
        {
            cls = DataIntegrations(cls);

            Validate(cls, AmbitoValidazione.Insert);

            db.Insert(cls);

            cls = (IstanzeCalcoloCanoniT)ChildDataIntegrations(cls);

            ChildInsert(cls);

            return cls;
        }

        public override DataClass ChildDataIntegrations(DataClass cls)
        {
            return cls;
        }

        private IstanzeCalcoloCanoniT ChildInsert(IstanzeCalcoloCanoniT cls)
        {
            return cls;
        }

        private IstanzeCalcoloCanoniT DataIntegrations(IstanzeCalcoloCanoniT cls)
        {
            return cls;
        }

        public IstanzeCalcoloCanoniT Update(IstanzeCalcoloCanoniT cls)
        {
            Validate(cls, AmbitoValidazione.Update);

            db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeCalcoloCanoniT cls)
        {
            VerificaRecordCollegati(cls);

            EffettuaCancellazioneACascata(cls);

            db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeCalcoloCanoniT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(IstanzeCalcoloCanoniT cls, AmbitoValidazione ambitoValidazione)
        {
            RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


