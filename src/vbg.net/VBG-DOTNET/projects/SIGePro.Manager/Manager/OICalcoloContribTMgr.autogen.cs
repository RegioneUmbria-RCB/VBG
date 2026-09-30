

using Init.SIGePro.Data;
using Init.SIGePro.Manager.Validator;
using PersonalLib2.Data;
using System.Collections.Generic;

namespace Init.SIGePro.Manager
{

    ///
    /// File generato automaticamente dalla tabella O_ICALCOLOCONTRIBT per la classe OICalcoloContribT il 27/06/2008 13.01.36
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
    public partial class OICalcoloContribTMgr : BaseManager
    {
        public OICalcoloContribTMgr(DataBase dataBase) : base(dataBase) { }

        public OICalcoloContribT GetById(string idcomune, int id)
        {
            var c = new OICalcoloContribT();


            c.Idcomune = idcomune;
            c.Id = id;

            return (OICalcoloContribT)this.db.GetClass(c);
        }

        public List<OICalcoloContribT> GetList(OICalcoloContribT filtro)
        {
            return this.db.GetClassList(filtro).ToList<OICalcoloContribT>();
        }

        public OICalcoloContribT Insert(OICalcoloContribT cls)
        {
            cls = this.DataIntegrations(cls);

            this.Validate(cls, AmbitoValidazione.Insert);

            this.db.Insert(cls);

            this.ChildInsert(cls);

            return cls;
        }



        private OICalcoloContribT ChildInsert(OICalcoloContribT cls)
        {
            return cls;
        }

        private OICalcoloContribT DataIntegrations(OICalcoloContribT cls)
        {
            return cls;
        }

        public OICalcoloContribT Update(OICalcoloContribT cls)
        {
            this.Validate(cls, AmbitoValidazione.Update);

            this.db.Update(cls);

            return cls;
        }

        public void Delete(OICalcoloContribT cls)
        {
            this.VerificaRecordCollegati(cls);

            this.EffettuaCancellazioneACascata(cls);

            this.db.Delete(cls);
        }

        private void VerificaRecordCollegati(OICalcoloContribT cls)
        {
            // Inserire la logica di verifica di integrità referenziale
            // Sollevare un'eccezione di tipo ReferentialIntegrityException nel caso in cui il record sia usato in foreign key in altre tabelle
        }


        private void Validate(OICalcoloContribT cls, AmbitoValidazione ambitoValidazione)
        {
            this.RequiredFieldValidate(cls, ambitoValidazione);
        }
    }
}


