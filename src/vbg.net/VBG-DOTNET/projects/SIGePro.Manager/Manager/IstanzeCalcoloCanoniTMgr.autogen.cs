using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

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
            var c = new IstanzeCalcoloCanoniT();


            c.Idcomune = idcomune;
            c.Id = id;

            return (IstanzeCalcoloCanoniT)this.db.GetClass(c);
        }

        public List<IstanzeCalcoloCanoniT> GetList(IstanzeCalcoloCanoniT filtro)
        {
            return this.db.GetClassList(filtro).ToList<IstanzeCalcoloCanoniT>();
        }

        public IstanzeCalcoloCanoniT Insert(IstanzeCalcoloCanoniT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

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
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(IstanzeCalcoloCanoniT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(IstanzeCalcoloCanoniT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }

        private void Validate(IstanzeCalcoloCanoniT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


