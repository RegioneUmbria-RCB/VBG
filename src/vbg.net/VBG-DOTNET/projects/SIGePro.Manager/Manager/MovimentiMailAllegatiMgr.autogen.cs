using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella MOVIMENTIMAILALLEGATI per la classe MovimentiMailAllegati il 06/07/2010 10.56.53
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
    public partial class MovimentiMailAllegatiMgr : BaseManager
    {
        public MovimentiMailAllegatiMgr(DataBase dataBase) : base(dataBase) { }

        public MovimentiMailAllegati GetById(string idcomune, int? id)
        {
            var c = new MovimentiMailAllegati();


            c.Idcomune = idcomune;
            c.Id = id;

            return (MovimentiMailAllegati)this.db.GetClass(c);
        }

        public List<MovimentiMailAllegati> GetList(MovimentiMailAllegati filtro)
        {
            return this.db.GetClassList(filtro).ToList<MovimentiMailAllegati>();
        }

        public MovimentiMailAllegati Insert(MovimentiMailAllegati cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private MovimentiMailAllegati ChildInsert(MovimentiMailAllegati cls)
        {
            return cls;
        }

        private MovimentiMailAllegati DataIntegrations(MovimentiMailAllegati cls)
        {
            return cls;
        }


        public MovimentiMailAllegati Update(MovimentiMailAllegati cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(MovimentiMailAllegati cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(MovimentiMailAllegati cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }



        private void Validate(MovimentiMailAllegati cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


